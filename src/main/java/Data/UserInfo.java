package Data;

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
用户信息类(单例模式):
属性:
    idToUser ： 存储id到User对象的映射
    filePath : excel数据文件的地址

方法:
    读取excel中的信息
    密码重置(admin)
    列出客户信息(admin)
    删除客户信息(admin)
    查询客户信息(admin)
 */
public class UserInfo {
    private static final UserInfo USER_INFO = new UserInfo();

    private HashMap<Integer, User> idToUser;

    private final String filePath = "C:\\Users\\50359\\Desktop\\shopp.xlsx";
    private UserInfo() {
        idToUser = new HashMap<>();
        //  读取excel表中的信息 填充上面的三个映射
        //  若文件不存在就新建一个
        File file = new File(filePath);
        if (!file.exists()) {
            ExcelWriter writer = EasyExcel.write(file).build();
            WriteSheet sheet1 = EasyExcel.writerSheet(0, "Sheet1").head(Person.class).build();
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "Sheet2").head(User.class).build();
            writer.write((Collection<?>) null, sheet1);
            writer.write((Collection<?>) null, sheet2);
            writer.finish();
        }
        EasyExcel.read(file, User.class, new AnalysisEventListener<User>() {
            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                idToUser.put(user.getID(), user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet(1).doRead();
    }

    // 列出所有客户信息
    void listAll() {
        for (int i : idToUser.keySet()) {

        }
    }


    public static UserInfo getInstance() {
        return USER_INFO;
    }

    public HashMap<Integer, User> getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(HashMap<Integer, User> idToUser) {
        this.idToUser = idToUser;
    }

//    public static void main(String[] args) {
//        UserInfo userInfo = UserInfo.getInstance();
//        for (int i : userInfo.idToUser.keySet()) {
//            System.out.println("key: " + i + " value: " + userInfo.idToUser.get(i).getAccount() + "  " + userInfo.idToUser.get(i).getPassword());
//        }
//    }
}
