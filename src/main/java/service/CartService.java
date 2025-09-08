package service;

import model.CartItem;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService {
    private final List<CartItem> items = new ArrayList<>();
    private double taxRate = 0.17; // 17% VAT example

    public List<CartItem> getItems() { return items; }
    public void clear() { items.clear(); }

    public void add(Product p) {
        if (p == null) return;                 // safety

        // look for the item in the cart
        for (CartItem ci : items) {
            if (ci.getProduct().getId() == p.getId()) {
                ci.setQuantity(ci.getQuantity() + 1);  // already in cart → +1
                return;                                // done
            }
        }

        // not found → add as new line
        items.add(new CartItem(p, 1));
    }

    public void remove(Product p) {
        items.removeIf(ci -> ci.getProduct().getId() == p.getId());
    }

    public void setQuantity(Product p, int qty) {
        if (p == null) return;
        if (qty < 0) qty = 0; // safety clamp

        for (int i = 0; i < items.size(); i++) {
            CartItem ci = items.get(i);
            if (ci.getProduct().getId() == p.getId()) {
                if (qty <= 0) {
                    items.remove(i);          // remove the row entirely
                } else {
                    ci.setQuantity(qty);      // update quantity
                }
                break; // done (IDs are unique in cart)
            }
        }
    }


    public int getItemCount() {
        int total = 0;
        for (CartItem ci : items) {
            total += ci.getQuantity();
        }
        return total;
    }

    public double getSubtotal() {
        double sum = 0.0;
        for (CartItem ci : items) {
            sum += ci.getLineTotal();
        }
        return sum;
    }

    public double getTax() {
        return round2(getSubtotal() * taxRate);
    }

    public double getTotal() {
        return round2(getSubtotal() + getTax());
    }

    private double round2(double v) { return Math.round(v * 100.0) / 100.0; }
}
