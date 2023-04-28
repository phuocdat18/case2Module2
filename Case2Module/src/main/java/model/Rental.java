package model;

import repository.IModel;
import utils.CurrencyFormat;
import utils.FormatDateModel;
import utils.ValidateUtils;

import java.util.Date;

public class Rental implements IModel<Rental> {
    private  int idRental;
    private String nameModel;
    private Date startDate;
    private Date endDate;
    private double price;
    private double totalPrice;
    private Date createBill;

    public Rental() {
    }

    public Rental(int idRental, String nameModel, Date starDate, Date endDate, double price, double totalPrice, Date createBill) {
        this.idRental = idRental;
        this.nameModel = nameModel;
        this.startDate = starDate;
        this.endDate = endDate;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createBill = createBill;
    }

    public int getIdRental() {
        return idRental;
    }

    public void setIdRental(int idRental) {
        this.idRental = idRental;
    }

    public String getNameModel() {
        return nameModel;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreateBill() {
        return createBill;
    }

    public void setCreateBill(Date createBill) {
        this.createBill = createBill;
    }


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void update(Rental obj) {

    }

    @Override
    public Rental parseData(String line) {
        Rental rental = new Rental();
        String[] strings = line.split(";");
        int idRental = Integer.parseInt(strings[0]);
        String nameModel = strings[1];
        Date starDate = FormatDateModel.parseDate(strings[2]);
        Date endDate = FormatDateModel.parseDate(strings[3]);
        Double priceModel = Double.parseDouble(strings[4]);
        Double totalPrice = Double.parseDouble(strings[5]);
        Date createBill = FormatDateModel.parseDate2(strings[6]);


        rental.setIdRental(idRental);
        rental.setNameModel(nameModel);
        rental.setStartDate(starDate);
        rental.setEndDate(endDate);
        rental.setPrice(priceModel);
        rental.setTotalPrice(totalPrice);
        rental.setCreateBill(createBill);
        return rental;
    }

    public String rentalView() {
        return String.format("            ║ %-6s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║", this.idRental, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill));
    }
    @Override
    public String toString() {
        return idRental + ";" + nameModel + ";" + FormatDateModel.convertDateToString(this.startDate) + ";" + FormatDateModel.convertDateToString(this.endDate) + ";" + CurrencyFormat.parseInteger(this.price) + ";" + CurrencyFormat.parseInteger(this.totalPrice) + ";" +  FormatDateModel.convertDateToString2(this.createBill);
    }
}