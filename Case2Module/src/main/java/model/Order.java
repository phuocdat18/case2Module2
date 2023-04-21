package model;

import repository.IModel;
import utils.CurrencyFormat;
import utils.DateFormat;

import java.util.Date;

public class Order implements IModel<Order> {
    private int idOder;
    private int idCustomer;
    private String nameCustomer;
    private String nameModel;
    private double priceModel;
    private double totalMoney;
    private Date createDateOder;
    private Status status;

    public Order() {
    }

    public Order(int idOder, int idCustomer, String nameCustomer, String nameModel, double priceModel, double totalMoney, Date createDateOder, Status status) {
        this.idOder = idOder;
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.nameModel = nameModel;
        this.priceModel = priceModel;
        this.totalMoney = totalMoney;
        this.createDateOder = createDateOder;
        this.status = status;
    }

    public int getIdOder() {
        return idOder;
    }

    public void setIdOder(int idOder) {
        this.idOder = idOder;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public double getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(double priceModel) {
        this.priceModel = priceModel;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getCreateDateOder() {
        return createDateOder;
    }

    public void setCreateDateOder(Date createDateOder) {
        this.createDateOder = createDateOder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int getId() {
        return idOder;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void update(Order obj) {
        this.idOder = obj.idOder;
        this.idCustomer = obj.idCustomer;
        this.nameCustomer = obj.nameCustomer;
        this.nameModel = obj.nameModel;
        this.priceModel = obj.priceModel;
        this.totalMoney = obj.totalMoney;
        this.createDateOder = obj.createDateOder;
        this.status = obj.status;
    }

    @Override
    public Order parseData(String line) {
        Order order = new Order();
        String[] strings = line.split(",");
        int idOder = Integer.parseInt(strings[0]);
        int idCustomer = Integer.parseInt(strings[1]);
        String nameCustomer = strings[2];
        String nameFood = strings[3];
        double priceFood = Double.parseDouble(strings[5]);
        double totalMoney = Double.parseDouble(strings[6]);
        Date createDateOder = DateFormat.parseDate2(strings[7]);
        Status Status = model.Status.getStatusByName(strings[8]);
        order.setIdOder(idOder);
        order.setIdCustomer(idCustomer);
        order.setNameCustomer(nameCustomer);
        order.setNameModel(nameFood);
        order.setPriceModel(priceFood);
        order.setTotalMoney(totalMoney);
        order.setCreateDateOder(createDateOder);
        order.setStatus(Status);
        return order;
    }
    public String oderView() {
        return String.format("            ║ %-6s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", this.idOder, this.idCustomer, this.nameCustomer, this.nameModel, CurrencyFormat.covertPriceToString(this.priceModel), CurrencyFormat.covertPriceToString(this.totalMoney), DateFormat.convertDateToString2(this.createDateOder), this.status.getName());
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", this.idOder, this.idCustomer, this.nameCustomer, this.nameModel, CurrencyFormat.parseInteger(this.priceModel), CurrencyFormat.parseInteger(this.totalMoney), DateFormat.convertDateToString2(this.createDateOder), this.status.getName());
    }
}
