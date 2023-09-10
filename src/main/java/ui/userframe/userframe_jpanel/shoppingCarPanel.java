package ui.userframe.userframe_jpanel;

import Data.CommodityInfo;
import Data.OrderInfo;
import Data.PersonInfo;
import Oper.Order;
import Oper.Person;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shoppingCarPanel extends JPanel {
    private int id; // 用户id
    private DefaultListModel<Order> payingCommodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public shoppingCarPanel(int id) {
        this.id = id;

        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
        this.setBackground(Color.white);
        // 获取该id用户购物车的商品信息
        OrderInfo commodityInfo = OrderInfo.getInstance();
        // 将商品信息添加到commodityModel中
        if (commodityInfo.getUserIdToPayingOrder().containsKey(id))
            payingCommodityModel.addAll(commodityInfo.getUserIdToPayingOrder().get(id));
        // 创建 JList
        JList<Order> list = new JList<>(payingCommodityModel);
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
            double totalPrice = 0.0;
            for (Order selectedValue : list.getSelectedValuesList()) {
                // 结算
                // 获取商品信息
                CommodityInfo commodityInfo1 = CommodityInfo.getInstance();
                Commodity commodity = commodityInfo1.getIdToCommodity().get(selectedValue.getCommodityId());
                // 获取商品数量
                int number = selectedValue.getQuantity();
                // 获取商品总价格
                totalPrice += commodity.getRetailPrice() * number;
            }
            // 弹出提示框
            int choice = JOptionPane.showConfirmDialog(null, "您一共需要支付" + totalPrice + "元", "确认支付", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // 用户点击了 "是" 按钮
                for (Order selectedValue : list.getSelectedValuesList()) {
                    // 获取商品信息
                    CommodityInfo commodityInfo1 = CommodityInfo.getInstance();
                    Commodity commodity = commodityInfo1.getIdToCommodity().get(selectedValue.getCommodityId());
                    // 获取商品数量
                    int number = selectedValue.getQuantity();
                    // 获取用户信息
                    PersonInfo personInfo = PersonInfo.getInstance();
                    Person person = personInfo.getIdToPeron().get(id);
                    // 修改商品数量
                    commodity.setQuantity(commodity.getQuantity() - number);
                    // 修改order状态
                    selectedValue.setState(1);
                    // 更新orderInfo中的映射
                    OrderInfo orderInfo = OrderInfo.getInstance();
                    orderInfo.getUserIdToPayingOrder().get(id).remove(selectedValue);
                    if (orderInfo.getUserIdToPayedOrder().containsKey(id))
                        orderInfo.getUserIdToPayedOrder().get(id).add(selectedValue);
                    else {
                        ArrayList<Order> payedCommodityModel = new ArrayList<>();
                        payedCommodityModel.add(selectedValue);
                        orderInfo.getUserIdToPayedOrder().put(id, payedCommodityModel);
                    }
                    // 更新显示
                    payingCommodityModel.removeElement(selectedValue);
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                // 用户点击了 "否" 按钮
            } else {
                // 用户关闭了对话框或者按下了 "取消" 按钮
            }
        });

        // 为list添加滚动条
        scrollList.setViewportView(list);

        this.add(scrollList, BorderLayout.CENTER);
        this.add(btnBox, BorderLayout.SOUTH);
    }

    static class myListCellRenderer implements ListCellRenderer<Order> {
        private JCheckBox checkBox;
        private JPanel panel;
        private MyPic pic;
        private JLabel name;
        private JLabel quantity;
        private JLabel retailPrice;
        private JLabel totalPrice;
        private JLabel manufacturer;
        private Box hBox1;
        private CommodityInfo commodityInfo = CommodityInfo.getInstance();
        // 图像缓存
        private Map<String, BufferedImage> imgCache = new HashMap<>();
        public myListCellRenderer() {
            // 创建容器
            panel = new JPanel();
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

            // 创建一个标签来显示商品的图片
            pic = new MyPic();
            // 创建五个标签来显示商品的文字说明
            name = new JLabel();
            quantity = new JLabel();
            retailPrice = new JLabel();
            totalPrice = new JLabel();
            manufacturer = new JLabel();
            // 设置字体
            Font font = new Font("微软雅黑", Font.PLAIN, 20);
            name.setFont(font);
            quantity.setFont(font);
            retailPrice.setFont(font);
            totalPrice.setFont(font);
            manufacturer.setFont(font);

            // 组装
            JPanel eastPanel = new JPanel();
            eastPanel.setPreferredSize(new Dimension(320, HEIGHT));
            eastPanel.setBackground(new Color(197, 227, 217, 63));

            // 用一个2*3的网格布局来存放商品信息
            GridLayout gridLayout = new GridLayout(3, 2);
            gridLayout.setHgap(10);
            gridLayout.setVgap(10);
            eastPanel.setLayout(gridLayout);

            eastPanel.add(name);
            eastPanel.add(retailPrice);
            eastPanel.add(quantity);
            eastPanel.add(totalPrice);
            eastPanel.add(manufacturer);

            //
            checkBox = new JCheckBox();
            checkBox.setBackground(Color.white);

            // 将图片和文字标签添加到面板
            hBox1 = Box.createHorizontalBox();
            hBox1.add(checkBox);
            hBox1.add(pic);
            hBox1.add(eastPanel);
            panel.add(hBox1);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Order> list, Order value, int index, boolean isSelected, boolean cellHasFocus) {
            // 获取每一个单元格的长宽
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();

            if (value.getState() == 1)
                hBox1.remove(checkBox);

            // 获取商品信息
            Commodity commodity = commodityInfo.getIdToCommodity().get(value.getCommodityId());
            // 获取商品数量
            int number = value.getQuantity();

            // 设置图片
            pic.setImage(loadImage(commodity.getImg()));
            pic.setPreferredSize(new Dimension(WIDTH / 4, HEIGHT));

            // 设置标签内容
            name.setText("商品名称: " + commodity.getName());
            quantity.setText("商品数量: " + number);
            retailPrice.setText("商品单价: " + commodity.getRetailPrice());
            totalPrice.setText("商品总价格: " + commodity.getRetailPrice() * number);
            manufacturer.setText("商品产家: " + commodity.getManufacturer());

            checkBox.setSelected(isSelected);
            checkBox.setEnabled(list.isEnabled());

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

        private BufferedImage loadImage(String imagePath) {
            BufferedImage image = imgCache.get(imagePath);
            if (image == null) {
                // 图像未缓存, 加载并放入缓存
                try {
                    File file = new File(imagePath);
                    image = ImageIO.read(file);
                } catch (IOException e) {
                    // 处理图像加载错误
                    e.printStackTrace();
                }
                imgCache.put(imagePath, image);
            }
            return image;
        }
    }
}
