package Oper;

/*
商品类:
属性:
    商品编号 ID
    商品名称 name
    生产厂家 manufacturer
    生产日期 data
    型号 type
    进货价 purCost
    零售价格 retailPrice
    数量 quantity
方法:
 */
public class Commodity {
    private int ID; // 商品编号
    private int quantity; //数量
    private String name, manufacturer, date, type; // 商品名称 生产厂家 生产日期 型号
    private String img;
    private double purCost, retailPrice; // 进货价 零售价

    // 全参构造函数
    public Commodity(int ID, String name, String manufacturer, String date, String type, double purCost, double retailPrice, int quantity, String img) {
        this.ID = ID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.date = date;
        this.type = type;
        this.purCost = purCost;
        this.retailPrice = retailPrice;
        this.quantity = quantity;
        this.img = img;
    }

    public Commodity() {
        // 无参构造函数
    }

    public double getPurCost() {
        return purCost;
    }

    public void setPurCost(double purCost) {
        this.purCost = purCost;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
