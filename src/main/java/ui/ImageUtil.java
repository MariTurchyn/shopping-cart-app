package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ImageUtil {
    public static Icon load(String imagePath, int w, int h) {
        if (imagePath == null || imagePath.isBlank()) return placeholder(w, h);

        // 1) classpath: src/main/resources/images/<imagePath>
        URL url = ImageUtil.class.getResource("/images/" + imagePath);
        if (url != null) return scale(new ImageIcon(url), w, h);

        // 2) fallback to disk path
        File f = new File(imagePath);
        if (f.exists()) return scale(new ImageIcon(imagePath), w, h);

        return placeholder(w, h);
    }

    private static Icon scale(ImageIcon src, int w, int h) {
        Image img = src.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private static Icon placeholder(int w, int h) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(new Color(245,248,255)); g.fillRect(0,0,w,h);
        g.setColor(new Color(180,190,210)); g.drawRect(0,0,w-1,h-1);
        g.setColor(new Color(100,110,130)); g.drawString("No image", w/2-28, h/2);
        g.dispose();
        return new ImageIcon(bi);
    }
}
