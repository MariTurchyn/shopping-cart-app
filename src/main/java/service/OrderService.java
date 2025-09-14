package service;

import db.Db;
import model.CartItem;
import model.Product;

import java.sql.*;
import java.util.List;
import java.math.BigDecimal;
import util.Pricing;



public class OrderService {
    private final CartService cart;
    private final ProductService products;

    public OrderService(CartService cart, ProductService products) {
        this.cart = cart; this.products = products;
    }

    public long placeOrder(String name, String address, String city, String zip) {
        List<CartItem> items = cart.getItems();
        if (items.isEmpty()) throw new IllegalStateException("Cart is empty");

        double subtotal = 0.0;
        for (CartItem ci : items) {
            Product p = products.findById(ci.getProduct().getId());
            if (p == null) throw new IllegalStateException("Missing product " + ci.getProduct().getId());
            subtotal += p.getPrice() * ci.getQuantity();
        }

        double tax = Pricing.tax(subtotal);
        double total = Pricing.total(subtotal);

        try (Connection c = Db.get()) {
            c.setAutoCommit(false);
            long orderId;

            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO orders(customer_name,address,city,zip,subtotal,tax,total) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, city);
                ps.setString(4, zip);
                ps.setBigDecimal(5, bd(subtotal));
                ps.setBigDecimal(6, bd(tax));
                ps.setBigDecimal(7, bd(total));
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { keys.next(); orderId = keys.getLong(1); }
            }

            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO order_items(order_id,product_id,quantity,price_each) VALUES(?,?,?,?)")) {
                for (CartItem ci : items) {
                    Product p = products.findById(ci.getProduct().getId());
                    ps.setLong(1, orderId);
                    ps.setInt(2, p.getId());
                    ps.setInt(3, ci.getQuantity());
                    ps.setBigDecimal(4, bd(p.getPrice()));
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            c.commit();
            return orderId;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to place order", e);
        }
    }

    private static BigDecimal bd(double v) {
        return BigDecimal.valueOf(Math.round(v * 100.0) / 100.0).setScale(2);
    }
}
