package MyFrame;

import javax.swing.*;
import java.awt.*;

public class MyPic extends JPanel {

    private Image image;
    public MyPic(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        /* 保持图片的长宽比 */
        // 获取控件的长宽
        int HEIGHT = getHeight();
        int WIDTH = getWidth();
        // 计算图片长宽
        double scale = (double) image.getWidth(null) / image.getHeight(null);
        int height = HEIGHT;
        int width = (int) (height * scale);
        // 绘制图片
        g.drawImage(image, 0, 0, width, height, null);
        setBackground(Color.white);
    }
}
