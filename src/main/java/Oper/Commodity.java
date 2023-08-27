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
    private String name, manufacturer, date, type, purCost, retailPrice, quantity;  // 商品名称 生产厂家 生产日期 型号 进货价 零售价 数量
    private String img;

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

    public String getPurCost() {
        return purCost;
    }

    public void setPurCost(String purCost) {
        this.purCost = purCost;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
