package Oper;

/*
用户类:
属性：
    account:用户名
    password:密码
    ID:ID
    status:身份(管理员/普通用户 -> 0/1)
    level、time、email、phone、consumption : 用户级别、注册时间、邮箱、手机号、累计消费
方法
 */
public class User extends Person{
    private String level, time, email, phone;
    private double consumption;

    public User(String account, String password, int ID, String time){
        super(account, password, ID, 1);
        this.time = time;
    }

    public User() {

    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }
}




