package Oper;

import com.alibaba.excel.annotation.ExcelProperty;

/*
人类:
属性：
    account:用户名
    password:密码
    ID:ID
    status:身份(管理员/普通用户 -> 0/1)
方法
 */
public class Person {
    @ExcelProperty(index = 0)
    private int ID;
    @ExcelProperty(index = 1)
    private String account;
    @ExcelProperty(index = 2)
    private String password;
    @ExcelProperty(index = 3)
    private int status;

    public Person(String account, String password, int ID, int status){
        this.account = account;
        this.password = password;
        this.ID = ID;
        this.status = status;
    }
    public Person() {
        // 无参构造函数
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
