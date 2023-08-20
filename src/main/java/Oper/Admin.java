package Oper;

/*
管理员类:
属性：
    account:用户名
    password:密码
    ID:ID
    status:身份(管理员/普通用户 -> 0/1)
方法
 */

public class Admin extends Person{

    public Admin(String account, String password, int ID) {
        super(account, password, ID, 0);
    }
}
