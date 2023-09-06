package ui.customframe;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backImage;

    public BackgroundPanel(Image image) {
        this.backImage = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = this.getHeight();
        int width = this.getWidth();
        g.drawImage(this.backImage, 0, 0, width, height,null);
    }
}
