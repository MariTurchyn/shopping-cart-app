package ui;

import model.CartItem;
import model.Product;
import service.CartService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class CartPanel extends JPanel {

    private final CartService cartService;
    private final Runnable onModelChanged;
    private final Runnable onCheckout;

    private final CartTableModel model;
    private final JLabel totalsLabel = new JLabel();

    public CartPanel(CartService cartService, Runnable onModelChanged, Runnable onCheckout) {
        this.cartService = cartService;
        this.onModelChanged = onModelChanged;
        this.onCheckout = onCheckout;

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Your Cart");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        add(title, BorderLayout.NORTH);

        model = new CartTableModel(cartService);
        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane sp = new JScrollPane(table);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        add(sp, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removeBtn = new JButton("Remove selected");
        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Product p = cartService.getItems().get(row).getProduct();
                cartService.remove(p);
                model.fireTableDataChanged();
                updateTotals();
                onModelChanged.run();
            }
        });
        left.add(removeBtn);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkoutBtn = new JButton("Proceed to Checkout");
        checkoutBtn.addActionListener(e -> onCheckout.run());

        updateTotals();
        right.add(totalsLabel);
        right.add(checkoutBtn);

        bottom.add(left, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
    }

    public void refresh() {
        model.fireTableDataChanged();  // tell JTable the data changed
        updateTotals();                // update Subtotal/Tax/Total label
    }


    private void updateTotals() {
        totalsLabel.setText(String.format(
                "<html>Subtotal: <b>$%.2f</b> &nbsp; Tax: <b>$%.2f</b> &nbsp; Total: <b>$%.2f</b></html>",
                cartService.getSubtotal(), cartService.getTax(), cartService.getTotal()
        ));
    }


    private class CartTableModel extends AbstractTableModel {
        private final String[] cols = {"Product", "Price", "Qty", "Line total"};
        private final CartService cs;

        CartTableModel(CartService cs) { this.cs = cs; }

        @Override public int getRowCount() { return cs.getItems().size(); }
        @Override public int getColumnCount() { return cols.length; }
        @Override public String getColumnName(int c) { return cols[c]; }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> String.class;
                case 1, 3 -> Double.class;
                case 2 -> Integer.class;
                default -> Object.class;
            };
        }

        @Override public boolean isCellEditable(int r, int c) { return c == 2; } // only Qty

        @Override
        public Object getValueAt(int row, int col) {
            List<CartItem> items = cs.getItems();
            CartItem ci = items.get(row);
            return switch (col) {
                case 0 -> ci.getProduct().getName();
                case 1 -> ci.getProduct().getPrice();
                case 2 -> ci.getQuantity();
                case 3 -> ci.getLineTotal();
                default -> null;
            };
        }

        @Override
        public void setValueAt(Object aValue, int row, int col) {
            if (col == 2) {
                int qty;
                try { qty = Integer.parseInt(aValue.toString()); }
                catch (Exception e) { return; }
                cs.setQuantity(cs.getItems().get(row).getProduct(), qty);
                fireTableDataChanged();
                updateTotals();
                onModelChanged.run();
            }
        }
    }
}
