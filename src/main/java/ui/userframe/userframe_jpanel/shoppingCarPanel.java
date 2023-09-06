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

public class shoppingCarPanel extends JPanel {
    private int id; // 用户id
    private DefaultListModel<Commodity> commodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public shoppingCarPanel(int id) {
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

        // 创建结算和删除按钮
        JButton payButton = new JButton("结算");
        payButton.setPreferredSize(new Dimension(100, 50));  // 设置按钮大小
        payButton.setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置按钮字体

        JButton deleteButton = new JButton("删除");
        deleteButton.setPreferredSize(new Dimension(100, 50));  // 设置按钮大小
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 设置按钮字体

        // 创建一个水平Box容器来存放按钮 居中显示
        Box btnBox = Box.createHorizontalBox();
        btnBox.add(Box.createHorizontalGlue());
        btnBox.add(payButton);
        btnBox.add(Box.createHorizontalStrut(120));
        btnBox.add(deleteButton);
        btnBox.add(Box.createHorizontalGlue());

        payButton.addActionListener(e -> {
            // 获取选中的商品
            for (Commodity selectedValue : list.getSelectedValuesList()) {
                //结算
            }
        });


        // 为list添加滚动条
        scrollList.setViewportView(list);

        this.add(scrollList, BorderLayout.CENTER);
        this.add(btnBox, BorderLayout.SOUTH);
    }

    class myListCellRenderer extends JCheckBox implements ListCellRenderer<Commodity> {
        private JCheckBox checkBox;

        @Override
        public Component getListCellRendererComponent(JList<? extends Commodity> list, Commodity value, int index, boolean isSelected, boolean cellHasFocus) {
            // 获取每一个单元格的长宽
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();
            // 创建容器
            JPanel panel = new JPanel();
            // 设置容器
            panel.setLayout(new BorderLayout());
            // 空边框
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
                eastPanel.setBackground(new Color(197, 227, 217, 63));

                vBox.add(name);
                vBox.add(Box.createVerticalStrut(5));
                vBox.add(retailPrice);
                vBox.add(Box.createVerticalStrut(5));
                vBox.add(manufacturer);

                eastPanel.add(vBox);

                checkBox = new JCheckBox();
                checkBox.setBackground(Color.white);
                checkBox.setSelected(isSelected);
                checkBox.setEnabled(list.isEnabled());


                // 将图片和文字标签添加到面板
                Box hBox1 = Box.createHorizontalBox();
                hBox1.add(checkBox);
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
