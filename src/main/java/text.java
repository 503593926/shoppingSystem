import MyFrame.MyPic;
import Oper.Commodity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class text {
    public static void main(String[] args) {
        // 创建一个顶层的窗口
        JFrame frame = new JFrame("多选复选框示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // 创建多个复选框
        JCheckBox checkBox1 = new JCheckBox("选项1");
        JCheckBox checkBox2 = new JCheckBox("选项2");
        JCheckBox checkBox3 = new JCheckBox("选项3");

        // 创建一个标签来显示选择的结果
        JLabel resultLabel = new JLabel("选择的选项：");

        // 创建一个按钮，当点击时显示选择的结果
        JButton showButton = new JButton("显示选择");
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 检查每个复选框的状态并显示选择结果
                String result = "选择的选项：";
                if (checkBox1.isSelected()) {
                    result += checkBox1.getText() + " ";
                }
                if (checkBox2.isSelected()) {
                    result += checkBox2.getText() + " ";
                }
                if (checkBox3.isSelected()) {
                    result += checkBox3.getText() + " ";
                }
                resultLabel.setText(result);
            }
        });

        // 将复选框和按钮添加到窗口中
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(checkBox1);
        panel.add(checkBox2);
        panel.add(checkBox3);
        panel.add(showButton);

        // 将结果标签添加到窗口中
        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);

        // 设置窗口可见
        frame.setVisible(true);
    }

    private class myListCellRenderer extends JCheckBox implements ListCellRenderer<Commodity> {

        private JCheckBox checkBox;

        public myListCellRenderer() {
            setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            add(checkBox, BorderLayout.WEST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Commodity> list, Commodity value, int index, boolean isSelected, boolean cellHasFocus) {
            // 获取每一个单元格的长宽
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();
            // 创建容器
            JPanel panel = new JPanel();
            // 设置容器
            panel.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 0));

            if (value instanceof Commodity) {
                Commodity commodity = (Commodity) value;

                // 创建一个标签来显示商品的图片
                File file = new File(commodity.getImg());
                BufferedImage image;
                try {
                    image = ImageIO.read(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 自定义图片绘制控件
                MyPic pic = new MyPic(image);
                pic.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));

                // 创建三个标签来显示商品的文字说明
                JLabel name = new JLabel(commodity.getName());
                JLabel retailPrice = new JLabel(commodity.getRetailPrice());
                JLabel manufacturer = new JLabel(commodity.getManufacturer());
                // 设置字体
                Font font = new Font("微软雅黑", Font.PLAIN, 20);
                name.setFont(font);
                retailPrice.setFont(font);
                manufacturer.setFont(font);

                // 组装
                JPanel eastPanel = new JPanel();
                eastPanel.setPreferredSize(new Dimension(320, HEIGHT));
                Box vBox = Box.createVerticalBox();
                vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
                eastPanel.setBackground(Color.white);

                vBox.add(name);
                vBox.add(Box.createVerticalStrut(50));
                vBox.add(retailPrice);
                vBox.add(Box.createVerticalStrut(30));
                vBox.add(manufacturer);

                eastPanel.add(vBox);


                checkBox = new JCheckBox();

                // 将图片和文字标签添加到面板
                panel.add(pic, BorderLayout.CENTER);
                panel.add(eastPanel, BorderLayout.EAST);
                panel.add(checkBox, BorderLayout.WEST);



                checkBox.setSelected(isSelected);
                checkBox.setEnabled(list.isEnabled());

            }

            // 选中和非选中时的显示模式
            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                panel.setForeground(list.getSelectionForeground());
            } else {
                panel.setBackground(list.getBackground());
                panel.setForeground(list.getForeground());
            }

            return panel;
        }
    }
}
