package ui;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ProductCatalogPanel extends JPanel {

    public ProductCatalogPanel(List<Product> products, Consumer<Product> onAdd) {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Catalog", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 3, 12, 12)); // 3 columns
        for (Product p : products) {
            grid.add(createCard(p, onAdd));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JComponent createCard(Product p, java.util.function.Consumer<Product> onAdd) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(12,12,12,12)
        ));

        JLabel name = new JLabel("<html><b>" + p.getName() + "</b></html>");
        JLabel price = new JLabel(String.format("$%.2f", p.getPrice()));

        JPanel top = new JPanel(new GridLayout(0,1));
        top.add(name);
        top.add(price);

        JButton addBtn = new JButton("Add to cart");
        addBtn.addActionListener(e -> onAdd.accept(p));

        // placeholder “image”
        JPanel mockImage = new JPanel();
        mockImage.setPreferredSize(new Dimension(200, 120));
        mockImage.setBackground(new Color(245, 248, 255));
        mockImage.add(new JLabel("Image"));

        card.add(top, BorderLayout.NORTH);
        card.add(mockImage, BorderLayout.CENTER);
        card.add(addBtn, BorderLayout.SOUTH);
        return card;
    }
}
