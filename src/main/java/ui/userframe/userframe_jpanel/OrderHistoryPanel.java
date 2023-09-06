package ui.userframe.userframe_jpanel;

import Data.CommodityInfo;
import ui.customframe.MyPic;
import Oper.Commodity;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OrderHistoryPanel extends JPanel{
    private int id; // 用户id
    private DefaultListModel<Commodity> commodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public OrderHistoryPanel(int id) {
        this.id = id;

        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
        this.setBackground(Color.white);
        // 获取商品信息
        CommodityInfo commodityInfo = CommodityInfo.getInstance();
        // 将商品信息添加到commodityModel中
        commodityModel.addAll(commodityInfo.getIdToCommodity().values());
        // 创建 JList
        JList<Commodity> list = new JList<>(commodityModel);
        // 设置list的渲染器
        list.setCellRenderer(new myListCellRenderer());
        list.setFixedCellHeight(120); // 设置单元格的高度为150像素

        // 为list添加滚动条
        scrollList.setViewportView(list);

        this.add(scrollList, BorderLayout.CENTER);
    }

    private class myListCellRenderer implements ListCellRenderer<Commodity> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Commodity> list, Commodity value, int index, boolean isSelected, boolean cellHasFocus) {
            // 获取每一个单元格的长宽
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();
            // 创建容器
            JPanel panel = new JPanel();
            // 设置容器
            panel.setLayout(new BorderLayout());
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 2, 5, 0);

            // 创建一个带颜色的边框
            Color borderColor = new Color(0, 0, 0); // 指定边框颜色为红色
            int borderWidth = 1; // 指定边框宽度为2像素
            Border lineBorder = new LineBorder(borderColor, borderWidth);

            // 创建一个复合边框，包含空白边框和有颜色的边框
            Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
            panel.setBorder(compoundBorder);

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
                pic.setPreferredSize(new Dimension(WIDTH / 6, HEIGHT));

                // 创建三个标签来显示商品的文字说明
                JLabel name = new JLabel("商品名称:" + commodity.getName());
                JLabel retailPrice = new JLabel("商品价格:" + commodity.getRetailPrice());
                JLabel manufacturer = new JLabel("商品产家:" + commodity.getManufacturer());
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
                eastPanel.setBackground(new Color(197, 227, 217, 63));

                // 居左显示name和retailPrice和manufacturer


                vBox.add(name);
                vBox.add(Box.createVerticalStrut(5));
                vBox.add(retailPrice);
                vBox.add(Box.createVerticalStrut(5));
                vBox.add(manufacturer);

                eastPanel.add(vBox);


                // 将图片和文字标签添加到面板
                Box hBox1 = Box.createHorizontalBox();
                hBox1.add(pic);
                hBox1.add(eastPanel);
                panel.add(hBox1);
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
