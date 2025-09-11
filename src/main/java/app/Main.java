package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        // Core FlatLaf (no extra dependency needed)
        FlatLightLaf.setup();

        // Global UI tweaks
        UIManager.put("Component.arc", 16);
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 14);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Button.innerFocusWidth", 0);
        UIManager.put("Component.arrowType", "chevron");
        UIManager.put("Component.focusColor", new Color(0x5B, 0x9B, 0xF2));

        SwingUtilities.invokeLater(() -> new AppFrame().setVisible(true));
    }
}
