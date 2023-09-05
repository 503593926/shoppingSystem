package Oper;

/*
管理员类:
属性：
    userId : 用户ID
    commodityId : 商品ID
    state : 状态(购物车/已完成 -> 0/1)
方法
 */
public class Order {
    private int userId;
    private int commodityId;
    private int state;
    private int quantity;

    // 生成含参构造器
    public Order(int userId, int commodityId, int quantity, int state) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.state = state;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
