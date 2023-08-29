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
