package model;

public class Payment {
    private int id;
    private int quantity;
    private String name;
    private String phoneNumber;
    private String address;
    private String money;
    private long revenue;

    public Payment() {
    }

    public Payment(int id, int quantity, long revenue) {
        this.id = id;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public Payment(int id, int quantity, String name, String phoneNumber, String address, long revenue) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.revenue = revenue;
    }

    public Payment(String rawPay) {
        String[] strings = rawPay.split(";");
        this.id = Integer.parseInt(strings[0]);
        this.quantity = Integer.parseInt(strings[1]);
        this.name = strings[2];
        this.phoneNumber = strings[3];
        this.address = strings[4];
        this.revenue = Long.parseLong(strings[5]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", money='" + money + '\'' +
                ", revenue=" + revenue +
                '}';
    }
}
