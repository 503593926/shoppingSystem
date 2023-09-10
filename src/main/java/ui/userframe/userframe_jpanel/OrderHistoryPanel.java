package ui.userframe.userframe_jpanel;

import Data.OrderInfo;
import Oper.Order;
import javax.swing.*;
import java.awt.*;


public class OrderHistoryPanel extends JPanel {
    private int id; // 用户id
    private DefaultListModel<Order> PayedCommodityModel = new DefaultListModel<>();
    JScrollPane scrollList = new JScrollPane();
    ImageIcon backIcon = new ImageIcon("D:\\code\\java\\shopp\\src\\main\\resources\\icon\\返回.png");

    public OrderHistoryPanel(int id) {
        this.id = id;

        this.setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器
        this.setBackground(Color.white);
        // 获取购物车信息
        OrderInfo orderInfo = OrderInfo.getInstance();
        // 将商品信息添加到commodityModel中
        if (orderInfo.getUserIdToPayedOrder().containsKey(id))
            PayedCommodityModel.addAll(orderInfo.getUserIdToPayedOrder().get(id));
        // 创建 JList
        JList<Order> list = new JList<>(PayedCommodityModel);
        // 设置list的渲染器
        list.setCellRenderer(new shoppingCarPanel.myListCellRenderer());
        list.setFixedCellHeight(120); // 设置单元格的高度为150像素

        // 为list添加滚动条
        scrollList.setViewportView(list);

        this.add(scrollList, BorderLayout.CENTER);
    }
}