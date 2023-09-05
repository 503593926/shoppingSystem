package MyFrame.userFrame_Jpanels;

import Data.CommodityInfo;
import Data.OrderInfo;
import Data.PersonInfo;
import Data.UserInfo;
import MyFrame.BackgroundPanel;
import MyFrame.MyPic;
import Oper.Commodity;
import Oper.Order;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class commodityPanel extends JPanel {
    private int id; // 用户id
    private DefaultListModel<Commodity> commodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public commodityPanel(int id) {
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
        list.setFixedCellHeight(200); // 设置单元格的高度为150像素


        scrollList.setViewportView(list);
        // 添加点击事件监听器 -> 点击后进入页面详细展示商品信息
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Commodity selectedCommodity = list.getSelectedValue();
                if (selectedCommodity != null) {
                    // 清除该次的选中状态
                    list.clearSelection();
                    // 进入商品的详细展示页面
                    showCommodityDetails(selectedCommodity);
                    this.remove(scrollList);
                    // 重新绘制布局
                    this.revalidate();
                    this.repaint();
                }
            }
        });

        this.add(scrollList, BorderLayout.CENTER);
    }

    private void showCommodityDetails(Commodity commodity) {
        // 创建一个Box竖直容器来布局显示详细的商品信息
        Box vBox = Box.createVerticalBox();
        // 创建一个头部容器
        JPanel headPanel = new JPanel();
        // 设置布局 背景
        headPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headPanel.setBackground(Color.white);
        headPanel.setMaximumSize(new Dimension(this.getWidth(), 30));
        // 创建返回图标
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);
        // 监听backLabel -> 点击后返回商品浏览界面
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commodityPanel.this.add(scrollList);
                commodityPanel.this.remove(vBox);
                // 重新绘制布局
                commodityPanel.this.revalidate();
                commodityPanel.this.repaint();
            }
        });
        // 组装头部容器
        headPanel.add(backLabel);

        // 获取图片的image对象
        File file = new File(commodity.getImg());
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 创建自定义控件BackgroundPanel的对像来展示图片
        BackgroundPanel commodityPic = new BackgroundPanel(image);
        commodityPic.setMaximumSize(new Dimension(image.getWidth(null) * 250 / image.getHeight(null), 250));

        // 创建一个容器来展示商品的文字描述
        JPanel describePanel = new JPanel();
        describePanel.setBackground(new Color(238, 213, 175, 44));
        describePanel.setMaximumSize(new Dimension(this.getWidth() * 4 / 5, 100));

        //创建一个Box竖直容器来布局显示详细的商品信息
        Box describeBox = Box.createVerticalBox();

        // 商品名称标签
        JLabel nameLabel = new JLabel("产品名称:" + commodity.getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 30)); // 设置字体

        //// 商品生产厂商和生产日期标签
        Box hBox1 = Box.createHorizontalBox(); // 创建一个水平容器来布局生产厂商和生产日期标签
        JLabel manufacturerLabel = new JLabel("生产厂家:" + commodity.getManufacturer());
        JLabel dateLabel = new JLabel("生产日期:" + commodity.getDate());

        // 设置字体
        manufacturerLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        dateLabel.setFont(new Font("宋体", Font.PLAIN, 20));

        // 把manufacturerLabel和dateLabel居中显示
        hBox1.add(Box.createHorizontalStrut(250));
        hBox1.add(manufacturerLabel);
        hBox1.add(Box.createHorizontalStrut(250));
        hBox1.add(dateLabel);
        hBox1.add(Box.createHorizontalStrut(250));

        //// 数量和价格标签
        Box hBox2 = Box.createHorizontalBox();  // 创建一个水平容器来布局数量和价格标签
        JLabel quantityLabel = new JLabel("剩余数量:" + commodity.getQuantity());
        JLabel retailPriceLabel = new JLabel("售价:" + commodity.getRetailPrice());

        // 设置字体
        quantityLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        retailPriceLabel.setFont(new Font("宋体", Font.PLAIN, 20));

        // 把quantityLabel和retailPriceLabel居中显示
        hBox2.add(Box.createHorizontalStrut(250));
        hBox2.add(quantityLabel);
        hBox2.add(Box.createHorizontalStrut(250));
        hBox2.add(retailPriceLabel);
        hBox2.add(Box.createHorizontalStrut(250));

        //// 组装商品的文字描述部分的各组件
        describeBox.add(nameLabel);
        hBox1.add(Box.createVerticalStrut(20));
        describeBox.add(hBox1);
        hBox1.add(Box.createVerticalStrut(20));
        describeBox.add(hBox2);
        describePanel.add(describeBox);

        // 实现加入购物车和购买按钮
        JPanel btnPanel = new JPanel(); // 创建一个容器来放置按钮
        btnPanel.setBackground(Color.white);  // 设置背景颜色
        btnPanel.setMaximumSize(new Dimension(this.getWidth(), 50));  // 设置大小

        // 创建一个水平容器来布局按钮
        Box btnBox = Box.createHorizontalBox();
        JButton addCar = new JButton("加入购物车");
        JButton buy = new JButton("购买");

        // addCar和buy按钮居中显示
        btnBox.add(Box.createHorizontalGlue());
        btnBox.add(addCar);
        btnBox.add(Box.createHorizontalStrut(250));
        btnBox.add(buy);
        btnBox.add(Box.createHorizontalGlue());

        btnPanel.add(btnBox);

        // 监听addCar按钮
        addCar.addActionListener(e -> {
            // 弹出一个对话框，询问用户加入购物车的商品的数量
            String quantity = JOptionPane.showInputDialog("请输入加入购物车的商品数量:");
            // 判断用户输入的数量是否合法
            if (quantity != null && quantity.matches("[0-9]+")) {
                // 获取用户输入的数量
                int num = Integer.parseInt(quantity);
                // 判断用户输入的数量是否大于商品的剩余数量
                if (num > commodity.getQuantity()) {
                    JOptionPane.showMessageDialog(this, "该商品只剩" + commodity.getQuantity() + "件了!");
                } else {
                    // 将商品加入购物车1
                    OrderInfo orderInfo = OrderInfo.getInstance();
                    orderInfo.getUserIdToPayingOrder().put(id, new Order(id, commodity.getID(), num, 0));
                    JOptionPane.showMessageDialog(this, "加入购物车成功!");
                }
            }
        });

        // 监听购买按钮
        buy.addActionListener(e -> {
            // 弹出一个对话框,询问用户购买的商品的数量
            String quantity = JOptionPane.showInputDialog("请输入购买的商品数量:");
            // 判断用户输入的数量是否合法
            if (quantity != null && quantity.matches("[0-9]+")) {
                // 获取用户输入的数量
                int num = Integer.parseInt(quantity);
                // 判断用户输入的数量是否大于商品的剩余数量
                if (num > commodity.getQuantity()) {
                    JOptionPane.showMessageDialog(this, "该商品只剩" + commodity.getQuantity() + "件了!");
                } else {
                    // 弹出对话框显示费用并询问是否支付
                    int result = JOptionPane.showConfirmDialog(this, "您需要支付" + commodity.getRetailPrice() * num + "元", "支付", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // 将商品加入历史订单
                        OrderInfo orderInfo = OrderInfo.getInstance();
                        orderInfo.getUserIdToPayedOrder().put(id, new Order(id, commodity.getID(), num, 1));
                        // 修改商品数量
                        commodity.setQuantity(commodity.getQuantity() - num);
                        // 更新用户消费金额
                        UserInfo userInfo = UserInfo.getInstance();
                        userInfo.getIdToUser().get(id).setConsumption(userInfo.getIdToUser().get(id).getConsumption() + commodity.getRetailPrice() * num);
                        JOptionPane.showMessageDialog(this, "购买成功!");
                    }
                }
            }
        });


        // 组装完整页面
        vBox.add(headPanel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(commodityPic);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(describePanel);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnPanel);
        this.add(vBox, BorderLayout.CENTER);
    }


    // 自定义渲染器
    private class myListCellRenderer extends JCheckBox implements ListCellRenderer<Commodity> {
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
                pic.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));

                // 创建三个标签来显示商品的文字说明
                JLabel name = new JLabel(commodity.getName());
                JLabel retailPrice = new JLabel(Double.toString(commodity.getRetailPrice()));
                JLabel manufacturer = new JLabel(commodity.getManufacturer());
                // 设置字体
                Font font = new Font("微软雅黑", Font.PLAIN, 20);
                name.setFont(font);
                retailPrice.setFont(font);
                manufacturer.setFont(font);

                // 组装
                Box vBox = Box.createVerticalBox();

                vBox.add(name);
                vBox.add(Box.createVerticalStrut(50));
                vBox.add(retailPrice);
                vBox.add(Box.createVerticalStrut(30));
                vBox.add(manufacturer);

                // 将图片和文字标签添加到面板
                panel.add(pic, BorderLayout.WEST);
                panel.add(vBox, BorderLayout.CENTER);
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


