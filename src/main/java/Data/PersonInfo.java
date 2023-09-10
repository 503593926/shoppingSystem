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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
个人信息类(单例模式):
属性:
    idToPeron ： 存储id到Person对象的映射
    idToAccount : 存储id到账户的映射
    accountToPassword : 存储账户到密码的映射
    cnt : 用于给新用户的id赋值
    defPassword : 默认密码 用于管理员重置用户密码
    filePath : excel数据文件的地址

方法:
    读取excel中的信息
    注册
    登陆验证
    密码修改
    密码重置(admin)
    列出客户信息(admin)
    删除客户信息(admin)
    查询客户信息(admin)
 */
public class PersonInfo {
    private static final PersonInfo PERSON_INFO = new PersonInfo();

    private HashMap<Integer, Person> idToPeron;
    private HashMap<String, String> accountToPassword;
    private HashMap<String, Integer> accountToID;
    private int cnt;

    private String defPassword;
    private final String filePath = "C:\\Users\\50359\\Desktop\\shopp.xlsx";

    // 加载excel表的数据
    private PersonInfo() {
        cnt = 100; // 用户id从100开始
        defPassword = "123456789";
        idToPeron = new HashMap<>();
        accountToPassword = new HashMap<>();
        accountToID = new HashMap<>();
        //  读取excel表中的信息 填充上面的三个映射
        //  若文件不存在就新建一个
        File file = new File(filePath);
        EasyExcel.read(file, Person.class, new AnalysisEventListener<Person>() {
            @Override
            public void invoke(Person person, AnalysisContext analysisContext) {
                idToPeron.put(person.getID(), person);
                accountToPassword.put(person.getAccount(), person.getPassword());
                accountToID.put(person.getAccount(), person.getID());
                // 获取cnt
                cnt = Math.max(cnt, person.getID() + 1);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet(0).doRead();
    }

    // 注册新用户
    // 输入 : 账号 密码 身份
    public void register(String account, String password, int status) {
        //  更新映射
        idToPeron.put(cnt, new Person(account, password, cnt, status));
        accountToPassword.put(account, password);
        accountToID.put(account, cnt);

        // 获取注册时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 使用默认格式化方式
        String defaultFormattedTime = currentTime.toString();

        // 更新UserInfo中的映射
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.getIdToUser().put(cnt, new User(account, password, cnt ++, defaultFormattedTime));
    }

    // 登录验证
    // 输入 : 账号 密码
    // 输出 : 是否登录成功
    public boolean signIn(String account, String password) {
        if (accountToPassword.containsKey(account)) {
            if (accountToPassword.get(account).equals(password)) {
                return true;
            }
        }
        return false;
    }

    // 修改密码
    // 输入 : 账号 新密码
    public void changePassword(String account, String newPassword) {
        accountToPassword.put(account, newPassword);

        int id = accountToID.get(account);
        Person person = idToPeron.get(id);
        if (person != null) {
            person.setPassword(newPassword);
        } else {
            System.out.println("Person with ID " + id + " not found.");
        }
    }

    // 重置密码为defPassword
    // 输入 : 账号
    public void resetPassword(String account) {
        changePassword(account, defPassword);
    }



    public static PersonInfo getInstance() {
        return PERSON_INFO;
    }

    public HashMap<Integer, Person> getIdToPeron() {
        return idToPeron;
    }

    public void setIdToPeron(HashMap<Integer, Person> idToPeron) {
        this.idToPeron = idToPeron;
    }

    public HashMap<String, String> getAccountToPassword() {
        return accountToPassword;
    }

    public void setAccountToPassword(HashMap<String, String> accountToPassword) {
        this.accountToPassword = accountToPassword;
    }

    public HashMap<String, Integer> getAccountToID() {
        return accountToID;
    }

    public void setAccountToID(HashMap<String, Integer> accountToID) {
        this.accountToID = accountToID;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getDefPassword() {
        return defPassword;
    }

    public void setDefPassword(String defPassword) {
        this.defPassword = defPassword;
    }

//    public static void main(String[] args) {
//        PersonInfo personinfo = PersonInfo.getInstance();
//        for (String i : personinfo.accountToPassword.keySet()) {
//            System.out.println("key: " + i + " value: " + personinfo.accountToPassword.get(i));
//        }
//    }

}
