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

/*
商品信息类(单例模式):
属性:
    idToCommodity ： 存储id到Commodity对象的映射
    filePath : excel数据文件的地址
方法:
    CommodityInfo 读取excel中commodity的信息

 */
public class CommodityInfo {
    private static final CommodityInfo Commodity_INFO = new CommodityInfo();
    private HashMap<Integer, Commodity> idToCommodity;  // 存储id到Commodity对象的映射
    private String filePath = "shopp.xlsx";  // excel数据文件的地址

    public CommodityInfo() {
        idToCommodity = new HashMap<>();
        //  读取excel表中的信息 填充上面的三个映射
        //  若文件不存在就新建一个
        File file = new File(filePath);
        EasyExcel.read(file, Commodity.class, new AnalysisEventListener<Commodity>() {
            @Override
            public void invoke(Commodity commodity, AnalysisContext analysisContext) {
                idToCommodity.put(commodity.getID(), commodity);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet(2).doRead();
    }

    public static CommodityInfo getInstance() {
        return Commodity_INFO;
    }

    public HashMap<Integer, Commodity> getIdToCommodity() {
        return idToCommodity;
    }

    public void setIdToCommodity(HashMap<Integer, Commodity> idToCommodity) {
        this.idToCommodity = idToCommodity;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
