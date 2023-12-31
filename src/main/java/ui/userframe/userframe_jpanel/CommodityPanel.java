package ui.userframe.userframe_jpanel;

import Data.CommodityInfo;
import Data.OrderInfo;
import Data.UserInfo;
import ui.customframe.BackgroundPanel;
import ui.customframe.MyPic;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
商品界面类: 显示商品界面
属性:
    id : 用户id
    commodityModel : 商品列表
    scrollList : 滚动条
    backIcon : 返回图标
方法:
    CommodityPanel 构造函数
    showCommodityDetails 展示商品详细信息
    myListCellRenderer 自定义渲染器
 */
public class CommodityPanel extends JPanel {
    private int id; // 用户id
    private DefaultListModel<Commodity> commodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public CommodityPanel(int id) {
        this.id = id;
        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
        this.setBackground(Color.white); // 设置背景颜色
        // 获取商品信息
        CommodityInfo commodityInfo = CommodityInfo.getInstance();
        // 将商品信息添加到commodityModel中
        commodityModel.addAll(commodityInfo.getIdToCommodity().values());
        // 创建 JList
        JList<Commodity> list = new JList<>(commodityModel);

        // 设置list
        list.setCellRenderer(new myListCellRenderer()); // 设置list的渲染器
        list.setFixedCellHeight(200); // 设置单元格的高度为150像素
        scrollList.setViewportView(list); // 为list添加滚动条

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

    // 展示商品详细信息
    // 参数: 待展示商品对象
    private void showCommodityDetails(Commodity commodity) {
        // 创建一个Box竖直容器来布局显示详细的商品信息
        Box vBox = Box.createVerticalBox();
        // 创建一个头部容器
        JPanel headPanel = new JPanel();
        // 设置headPanel
        headPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 设置布局管理器
        headPanel.setBackground(Color.white); // 设置背景颜色
        headPanel.setMaximumSize(new Dimension(this.getWidth(), 30)); // 设置大小
        // 创建返回图标
        JLabel backLabel = new JLabel();
        backLabel.setIcon(backIcon);
        // 监听backLabel -> 点击后返回商品浏览界面
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CommodityPanel.this.add(scrollList);
                CommodityPanel.this.remove(vBox);
                // 重新绘制布局
                CommodityPanel.this.revalidate();
                CommodityPanel.this.repaint();
            }
        });
        // 组装头部容器
        headPanel.add(backLabel);

        // 获取商品图片的image对象
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

        // 商品生产厂商和生产日期标签
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

        // 数量和价格标签
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

        // 组装商品的文字描述部分的各组件
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
        // 组装按钮容器
        btnPanel.add(btnBox);
        // 监听addCar按钮
        addCar.addActionListener(e -> {
            // 弹出一个对话框，询问用户加入购物车的商品的数量
            String quantity = JOptionPane.showInputDialog("请输入加入购物车的商品数量:");
            // 判断用户输入的数量是否合法
            // 如果用户输入的不是数字或者小于等于0的数字，弹出提示框
            if (quantity == null) {
                // 用户点击了取消按钮
                return;
            }
            if  (!quantity.matches("[0-9]+") || Integer.parseInt(quantity) == 0) {
                JOptionPane.showMessageDialog(this, "请输入一个大于零的整数!");
            }
            else {
                // 获取用户输入的数量
                int num = Integer.parseInt(quantity);
                // 判断用户输入的数量是否大于商品的剩余数量
                if (num > commodity.getQuantity()) {
                    JOptionPane.showMessageDialog(this, "该商品只剩" + commodity.getQuantity() + "件了!");
                } else {
                    // 将商品加入购物车
                    OrderInfo orderInfo = OrderInfo.getInstance();
                    if (orderInfo.getUserIdToPayingOrder().containsKey(id)) {
                        orderInfo.getUserIdToPayingOrder().get(id).add(new Order(id, commodity.getID(), num, 0));
                    } else {
                        ArrayList<Order> orderList = new ArrayList<>();
                        orderList.add(new Order(id, commodity.getID(), num, 0));
                        orderInfo.getUserIdToPayingOrder().put(id, orderList);
                    }
                    JOptionPane.showMessageDialog(this, "加入购物车成功!");
                }
            }
        });
        // 监听购买按钮
        buy.addActionListener(e -> {
            // 弹出一个对话框,询问用户购买的商品的数量
            String quantity = JOptionPane.showInputDialog("请输入购买的商品数量:");
            // 判断用户输入的数量是否合法
            if (quantity == null) {
                // 用户点击了取消按钮
                return;
            }
            if  (!quantity.matches("[0-9]+") || Integer.parseInt(quantity) == 0) {
                JOptionPane.showMessageDialog(this, "请输入一个大于零的整数!");
            }
            else {
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
                        if (orderInfo.getUserIdToPayedOrder().containsKey(id)) {
                            orderInfo.getUserIdToPayedOrder().get(id).add(new Order(id, commodity.getID(), num, 1));
                        } else {
                            ArrayList<Order> orderList = new ArrayList<>();
                            orderList.add(new Order(id, commodity.getID(), num, 1));
                            orderInfo.getUserIdToPayedOrder().put(id, orderList);
                        }
                        // 修改商品数量
                        commodity.setQuantity(commodity.getQuantity() - num);
                        // 更新用户消费金额
                        UserInfo userInfo = UserInfo.getInstance();
                        userInfo.getIdToUser().get(id).setConsumption(userInfo.getIdToUser().get(id).getConsumption() + commodity.getRetailPrice() * num);
                        JOptionPane.showMessageDialog(this, "购买成功!");
                        // 更新用户等级 铜 -> 银 -> 金 ：100 -> 1000 > 10000
                        if (userInfo.getIdToUser().get(id).getConsumption() >= 100 && userInfo.getIdToUser().get(id).getConsumption() < 1000) {
                            userInfo.getIdToUser().get(id).setLevel("铜牌客户");
                        } else if (userInfo.getIdToUser().get(id).getConsumption() >= 1000 && userInfo.getIdToUser().get(id).getConsumption() < 10000) {
                            userInfo.getIdToUser().get(id).setLevel("银牌客户");
                        } else if (userInfo.getIdToUser().get(id).getConsumption() >= 10000) {
                            userInfo.getIdToUser().get(id).setLevel("金牌客户");
                        }
                        // 更新quantityLabel
                        quantityLabel.setText("剩余数量:" + commodity.getQuantity());
                        // 重新绘制布局
                        this.revalidate();
                        this.repaint();

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

    // 自定义商品list渲染器 显示商品概览
    private class myListCellRenderer implements ListCellRenderer<Commodity> {
        private JPanel panel;
        private MyPic pic;
        private JLabel nameLabel;
        private JLabel retailPriceLabel;
        private JLabel manufacturerLabel;
        // 图像缓存
        private Map<String, BufferedImage> imgCache = new HashMap<>();

        public myListCellRenderer() {
            panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // 设置容器边框
            Border emptyBorder = BorderFactory.createEmptyBorder(5, 2, 5, 0);
            Color borderColor = new Color(0, 0, 0);
            int borderWidth = 1;
            Border lineBorder = new LineBorder(borderColor, borderWidth);
            Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, lineBorder);
            panel.setBorder(compoundBorder);

            // 创建图片标签
            pic = new MyPic();
            panel.add(pic, BorderLayout.WEST);

            // 创建文字标签
            nameLabel = new JLabel();
            // 设置颜色
            nameLabel.setForeground(new Color(0, 195, 255));
            retailPriceLabel = new JLabel();
            manufacturerLabel = new JLabel();
            Font font = new Font("微软雅黑", Font.PLAIN, 20);
            nameLabel.setFont(font);
            retailPriceLabel.setFont(font);
            manufacturerLabel.setFont(font);

            // 创建垂直盒子并添加标签
            Box vBox = Box.createVerticalBox();
            vBox.add(Box.createVerticalStrut(15));
            vBox.add(nameLabel);
            vBox.add(Box.createVerticalStrut(30));
            vBox.add(retailPriceLabel);
            vBox.add(Box.createVerticalStrut(30));
            vBox.add(manufacturerLabel);
            panel.add(vBox, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Commodity> list, Commodity value, int index, boolean isSelected, boolean cellHasFocus) {
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();
            // 更新组件的内容
            Commodity commodity = (Commodity) value;

            // 设置图片
            pic.setImage(loadImage(commodity.getImg()));
            pic.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));

            // 设置标签内容
            nameLabel.setText(commodity.getName());
            retailPriceLabel.setText("售价: " + Double.toString(commodity.getRetailPrice()));
            manufacturerLabel.setText("生产厂家: " + commodity.getManufacturer());

            // 设置选中和非选中时的显示模式
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


