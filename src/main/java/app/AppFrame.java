package app;

import model.Product;
import service.CartService;
import service.ProductService;
import ui.CartPanel;
import ui.CheckoutPanel;
import ui.ProductCatalogPanel;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private static final String CARD_CATALOG = "catalog";
    private static final String CARD_CART = "cart";
    private static final String CARD_CHECKOUT = "checkout";

    private CartPanel cartPanel;

    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);

    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService();

    private final JLabel cartBadge = new JLabel("Cart: 0");

    public AppFrame() {
        setTitle("E-Commerce Shopping Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(buildTopBar(), BorderLayout.NORTH);

        ProductCatalogPanel catalog = new ProductCatalogPanel(
                productService.getAll(),
                p -> { cartService.add(p); updateCartBadge();if (cartPanel != null) {
                    cartPanel.refresh();
                } }
        );

         cartPanel = new CartPanel(
                cartService,
                () -> { updateCartBadge(); showCart(); },
                this::showCheckout
        );

        CheckoutPanel checkoutPanel = new CheckoutPanel(
                cartService,
                () -> {
                    JOptionPane.showMessageDialog(this, "Order placed! Thank you ❤️");
                    cartService.clear();
                    updateCartBadge();
                    showCatalog();
                }
        );

        root.setOpaque(false);
        add(root, BorderLayout.CENTER);
        root.add(catalog, CARD_CATALOG);
        root.add(cartPanel, CARD_CART);
        root.add(checkoutPanel, CARD_CHECKOUT);

        showCatalog();
    }

    private JComponent buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel brand = new JLabel("ShopMate");
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 20f));

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton catalogBtn = new JButton("Catalog");
        JButton cartBtn = new JButton("Cart");
        JButton checkoutBtn = new JButton("Checkout");

        catalogBtn.addActionListener(e -> showCatalog());
        cartBtn.addActionListener(e -> showCart());
        checkoutBtn.addActionListener(e -> showCheckout());

        right.add(catalogBtn);
        right.add(cartBtn);
        right.add(checkoutBtn);

        cartBadge.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        right.add(cartBadge);

        bar.add(brand, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private void updateCartBadge() {
        cartBadge.setText("Cart: " + cartService.getItemCount() + " • Total $" + cartService.getTotal());
    }

    private void showCatalog() { layout.show(root, CARD_CATALOG); }
    private void showCart() {
        if (cartPanel != null) cartPanel.refresh(); // make JTable re-read data
        layout.show(root, CARD_CART);
    }    private void showCheckout() { layout.show(root, CARD_CHECKOUT); }
}
