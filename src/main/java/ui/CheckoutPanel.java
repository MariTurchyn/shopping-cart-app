package ui;

import service.CartService;

import javax.swing.*;
import java.awt.*;

public class CheckoutPanel extends JPanel {
    private final CartService cartService;
    private final JLabel summary = new JLabel();

    public CheckoutPanel(CartService cartService, Runnable onPlaceOrder) {
        this.cartService = cartService;

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Checkout");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0,2,8,8));

        JTextField name = new JTextField();
        JTextField address = new JTextField();
        JTextField city = new JTextField();
        JTextField zip = new JTextField();
        JComboBox<String> payment = new JComboBox<>(new String[]{
                "Credit Card (mock)", "PayPal (mock)"
        });

        form.add(new JLabel("Full name:")); form.add(name);
        form.add(new JLabel("Address:"));   form.add(address);
        form.add(new JLabel("City:"));      form.add(city);
        form.add(new JLabel("ZIP:"));       form.add(zip);
        form.add(new JLabel("Payment:"));   form.add(payment);

        add(form, BorderLayout.CENTER);

        JButton placeOrder = new JButton("Place Order");
        placeOrder.addActionListener(e -> {
            if (name.getText().isBlank() || address.getText().isBlank()
                    || city.getText().isBlank() || zip.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            onPlaceOrder.run();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(summary);
        bottom.add(placeOrder);
        add(bottom, BorderLayout.SOUTH);

        refreshTotals(); // set initial values
    }

    public void refreshTotals() {
        summary.setText(String.format(
                "<html>Subtotal: <b>$%.2f</b> &nbsp; Tax: <b>$%.2f</b> &nbsp; Total: <b>$%.2f</b></html>",
                cartService.getSubtotal(), cartService.getTax(), cartService.getTotal()
        ));
    }
}
