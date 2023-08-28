package MyFrame.userFrame_Jpanels;

import Data.CommodityInfo;
import MyFrame.BackgroundPanel;
import MyFrame.MyPic;
import Oper.Commodity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class commodityPanel extends JPanel {
    private  DefaultListModel<Commodity> commodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");
    public commodityPanel() {
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
        /* 头部容器的实现 */
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

        /* 实现商品详细介绍面板*/
        // 展示图片
        //// 获取图片的image对象
        File file = new File(commodity.getImg());
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //// 创建自定义控件BackgroundPanel的对像来展示图片
        BackgroundPanel commodityPic = new BackgroundPanel(image);
        commodityPic.setMaximumSize(new Dimension(image.getWidth(null) * 250 / image.getHeight(null), 250));
        // 展示商品的文字描述
        //// 创建一个垂直容器来展示各组件
        JPanel describePanel = new JPanel();
        describePanel.setBackground(Color.red);
        //describePanel.setLayout(new BorderLayout());
        describePanel.setMaximumSize(new Dimension(this.getWidth() * 4 / 5, 100));
        Box describeBox = Box.createVerticalBox();
        //// 显示商品名称
        JLabel nameLabel = new JLabel(commodity.getName());
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20)); // 设置字体
        //// 显示商品生产厂商和生产日期
        Box hBox1 = Box.createHorizontalBox();
        JLabel manufacturerLabel = new JLabel(commodity.getManufacturer());
        manufacturerLabel.setFont(new Font("宋体", Font.PLAIN, 10));
        JLabel dateLabel = new JLabel(commodity.getDate());
        dateLabel.setFont(new Font("宋体", Font.PLAIN, 10));
        hBox1.add(manufacturerLabel);
        hBox1.add(Box.createHorizontalStrut(10));
        hBox1.add(dateLabel);
        //// 显示数量和价格
        Box hBox2 = Box.createHorizontalBox();
        JLabel quantityLabel = new JLabel(commodity.getQuantity());
        manufacturerLabel.setFont(new Font("宋体", Font.PLAIN, 10));
        JLabel retailPriceLabel = new JLabel(commodity.getRetailPrice());
        dateLabel.setFont(new Font("宋体", Font.PLAIN, 10));
        hBox2.add(quantityLabel);
        hBox2.add(retailPriceLabel);
        //// 组装商品的文字描述部分的各组件
        describeBox.add(nameLabel);
        describeBox.add(hBox1);
        describeBox.add(hBox2);
        describePanel.add(describeBox);

        // 实现加入购物车和购买按钮
        Box btnBox = Box.createHorizontalBox();
        JButton addCar = new JButton("加入购物车");
        JButton buy = new JButton("购买");
        btnBox.add(addCar);
        btnBox.add(buy);

        // 组装完整页面
        vBox.add(headPanel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(commodityPic);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(describePanel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);
        this.add(vBox, BorderLayout.CENTER);
    }

    private class myListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // 获取每一个单元格的长宽
            int WIDTH = list.getWidth();
            int HEIGHT = list.getHeight();
            // 创建容器
            JPanel panel = new JPanel();
            // 设置容器
            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 0));

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
