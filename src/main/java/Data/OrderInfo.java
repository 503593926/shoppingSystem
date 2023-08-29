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
    private HashMap<Integer, Integer> userIdToPayedCommodityId;  // 存储用户id到已完成商品id的映射
    private HashMap<Integer, Integer> userIdToPayingCommodityId;  // 存储用户id到待支付商品id的映射

    private String filePath = "C:\\Users\\50359\\Desktop\\shopp.xlsx";  // excel数据文件的地址

    public OrderInfo() {
        userIdToPayedCommodityId = new HashMap<>();
        userIdToPayingCommodityId = new HashMap<>();
        //  若文件不存在就新建一个
        File file = new File(filePath);
        if (!file.exists()) {
            ExcelWriter writer = EasyExcel.write(file).build();
            WriteSheet sheet1 = EasyExcel.writerSheet(0, "Sheet1").head(Person.class).build();
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "Sheet2").head(User.class).build();
            WriteSheet sheet3 = EasyExcel.writerSheet(2, "Sheet3").head(Commodity.class).build();
            WriteSheet sheet4 = EasyExcel.writerSheet(3, "Sheet4").head(Order.class).build();
            writer.write((Collection<?>) null, sheet1);
            writer.write((Collection<?>) null, sheet2);
            writer.write((Collection<?>) null, sheet3);
            writer.write((Collection<?>) null, sheet4);
            writer.finish();
        }
        EasyExcel.read(file, Order.class, new AnalysisEventListener<Order>() {
            @Override
            public void invoke(Order order, AnalysisContext analysisContext) {
                if (order.getState() == 0)
                    userIdToPayingCommodityId.put(order.getUserId(), order.getCommodityId());
                else if (order.getState() == 1)
                    userIdToPayedCommodityId.put(order.getUserId(), order.getCommodityId());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet(2).doRead();
    }

    public static OrderInfo getInstance() {
        return ORDER_INFO;
    }

    public HashMap<Integer, Integer> getUserIdToPayedCommodityId() {
        return userIdToPayedCommodityId;
    }

    public void setUserIdToPayedCommodityId(HashMap<Integer, Integer> userIdToPayedCommodityId) {
        this.userIdToPayedCommodityId = userIdToPayedCommodityId;
    }

    public HashMap<Integer, Integer> getUserIdToPayingCommodityId() {
        return userIdToPayingCommodityId;
    }

    public void setUserIdToPayingCommodityId(HashMap<Integer, Integer> userIdToPayingCommodityId) {
        this.userIdToPayingCommodityId = userIdToPayingCommodityId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
