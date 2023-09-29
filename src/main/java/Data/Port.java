package Data;

import Oper.Commodity;
import Oper.Order;
import Oper.Person;
import Oper.User;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Port {
    private static String filePath = "shopp.xlsx";  // excel数据文件的地址
    private static PersonInfo personInfo;
    private static UserInfo userInfo;
    private static CommodityInfo commodityInfo;
    private static OrderInfo orderInfo;
    // 从文件中导入数据
    public static void importData() {
        File file = new File(filePath);
        // 若文件不存在就新建一个
        if (!file.exists()) {
            ExcelWriter writer = EasyExcel.write(file).build();
            WriteSheet sheet1 = EasyExcel.writerSheet(0, "Person").head(Person.class).build();
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "User").head(User.class).build();
            WriteSheet sheet3 = EasyExcel.writerSheet(2, "Commodity").head(Commodity.class).build();
            WriteSheet sheet4 = EasyExcel.writerSheet(3, "Order").head(Order.class).build();
            writer.write((Collection<?>) null, sheet1);
            writer.write((Collection<?>) null, sheet2);
            writer.write((Collection<?>) null, sheet3);
            writer.write((Collection<?>) null, sheet4);
            writer.finish();
        }
        // 导入Person数据
        personInfo = PersonInfo.getInstance();
        // 导入User数据
        userInfo = UserInfo.getInstance();
        // 导入Commodity数据
        commodityInfo = CommodityInfo.getInstance();
        // 导入Order数据
        orderInfo = OrderInfo.getInstance();
    }

    // 导出数据到excel
    public static void exportData() {
        File file = new File(filePath);
        // 导入Person数据
        personInfo = PersonInfo.getInstance();
        // 导入User数据
        userInfo = UserInfo.getInstance();
        // 导入Commodity数据
        commodityInfo = CommodityInfo.getInstance();
        // 导入Order数据
        orderInfo = OrderInfo.getInstance();
        ExcelWriter writer = EasyExcel.write(file).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0, "Person").head(Person.class).build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1, "User").head(User.class).build();
        WriteSheet sheet3 = EasyExcel.writerSheet(2, "Commodity").head(Commodity.class).build();
        WriteSheet sheet4 = EasyExcel.writerSheet(3, "Order").head(Order.class).build();
        writer.write((Collection<?>) personInfo.getIdToPeron().values(), sheet1);
        writer.write((Collection<?>) userInfo.getIdToUser().values(), sheet2);
        writer.write((Collection<?>) commodityInfo.getIdToCommodity().values(), sheet3);
        List<Order> orders = new ArrayList<>();
        // 合并两个订单集合
        for (ArrayList<Order> orderList : orderInfo.getUserIdToPayingOrder().values()) {
            orders.addAll(orderList);
        }
        for (ArrayList<Order> orderList : orderInfo.getUserIdToPayedOrder().values()) {
            orders.addAll(orderList);
        }
        writer.write((Collection<?>) orders, sheet4);
        writer.finish();
    }
}
