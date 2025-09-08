package app;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Pretty UI
        FlatMacLightLaf.setup();
        UIManager.put("Component.arc", 16);
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 14);

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Shopping Cart – Starter");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(900, 600);
            f.setLocationRelativeTo(null);


            f.setVisible(true);
        });
    }
}