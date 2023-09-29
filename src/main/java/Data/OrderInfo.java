package Data;

import Oper.Commodity;
import Oper.Order;
import Oper.Person;
import Oper.User;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/*
订单信息类(单例模式):
属性:
    userIdToPayedCommodityId : 存储用户id到已完成商品id的映射
    userIdToPayingCommodityId : 存储用户id到待支付商品id的映射
    filePath : excel数据文件的地址
方法:
    CommodityInfo 读取excel中commodity的信息
 */
public class OrderInfo {
    private static final OrderInfo ORDER_INFO = new OrderInfo();
    private HashMap<Integer, ArrayList<Order>> userIdToPayedOrder;  // 存储用户id到已完成订单的映射
    private HashMap<Integer, ArrayList<Order>> userIdToPayingOrder;  // 存储用户id到待支付订单的映射

    private String filePath = "shopp.xlsx";  // excel数据文件的地址

    public OrderInfo() {
        userIdToPayedOrder = new HashMap<>();
        userIdToPayingOrder = new HashMap<>();
        //  若文件不存在就新建一个
        File file = new File(filePath);
        EasyExcel.read(file, Order.class, new AnalysisEventListener<Order>() {
            @Override
            public void invoke(Order order, AnalysisContext analysisContext) {
                if (order.getState() == 0)
                    if (userIdToPayingOrder.containsKey(order.getUserId()))
                        userIdToPayingOrder.get(order.getUserId()).add(order);
                    else {
                        ArrayList<Order> orderList = new ArrayList<>();
                        orderList.add(order);
                        userIdToPayingOrder.put(order.getUserId(), orderList);
                    }
                else if (order.getState() == 1)
                    if (userIdToPayedOrder.containsKey(order.getUserId()))
                        userIdToPayedOrder.get(order.getUserId()).add(order);
                    else {
                        ArrayList<Order> orderList = new ArrayList<>();
                        orderList.add(order);
                        userIdToPayedOrder.put(order.getUserId(), orderList);
                    }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet(3).doRead();
    }

    public static OrderInfo getInstance() {
        return ORDER_INFO;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HashMap<Integer, ArrayList<Order>> getUserIdToPayedOrder() {
        return userIdToPayedOrder;
    }

    public void setUserIdToPayedOrder(HashMap<Integer, ArrayList<Order>> userIdToPayedOrder) {
        this.userIdToPayedOrder = userIdToPayedOrder;
    }

    public HashMap<Integer, ArrayList<Order>> getUserIdToPayingOrder() {
        return userIdToPayingOrder;
    }

    public void setUserIdToPayingOrder(HashMap<Integer, ArrayList<Order>> userIdToPayingOrder) {
        this.userIdToPayingOrder = userIdToPayingOrder;
    }

}
