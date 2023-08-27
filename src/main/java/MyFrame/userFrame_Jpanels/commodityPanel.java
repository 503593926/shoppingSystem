package MyFrame.userFrame_Jpanels;

import Data.CommodityInfo;
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
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\商品.png");
    public commodityPanel() {
        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
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
        // 新建一个页面来显示详细的上品信息
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // 设置布局
        panel.setBounds(0, 0, 100, 100);

        // 创建一个头部页面
        JPanel headPanel = new JPanel();
        headPanel.setLayout(new FlowLayout()); // 设置布局

        // 创建返回图标
        JLabel backLabel = new JLabel("21");
        backLabel.setIcon(backIcon);
        // 监听backLabel -> 点击后返回商品浏览界面
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commodityPanel.this.add(scrollList);
                commodityPanel.this.remove(panel);
                // 重新绘制布局
                commodityPanel.this.revalidate();
                commodityPanel.this.repaint();
            }
        });

        // 组装头部页面
        headPanel.add(backLabel);

        //
        panel.add(headPanel, BorderLayout.NORTH);
        this.add(panel);
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
