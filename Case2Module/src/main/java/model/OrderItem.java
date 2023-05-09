package model;

public class OrderItem {
    private long id;
    private double price;
    private int quantity;
    private long orderId;
    private int productId;
    private String productName;
    private double total;

    public OrderItem() {
    }

    public OrderItem(long id, double price, int quantity, long orderId, int productId, String productName, double total) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.total = total;
    }

    public OrderItem(String record) {
        String[] fields = record.split(";");
        this.id = Long.parseLong(fields[0]);
        this.price = Double.parseDouble(fields[1]);
        this.quantity = Integer.parseInt(fields[2]);
        this.orderId = Long.parseLong(fields[3]);
        this.productId = Integer.parseInt(fields[4]);
        this.productName = fields[5];
        this.total = Double.parseDouble(fields[6]);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s", this.id, this.price, this.quantity, this.orderId, this.productId, this.productName, this.total);
    }
}
