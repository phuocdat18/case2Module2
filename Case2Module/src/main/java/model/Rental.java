package model;

import repository.IModel;
import utils.CurrencyFormat;
import utils.FormatDateModel;
import utils.ValidateUtils;

import java.util.Date;

public class Rental implements IModel<Rental> {
    private  int idRental;
    private int idCustomer;
    private String nameCustomer;
    private String nameModel;
    private Date startDate;
    private Date endDate;
    private int quantityModel;
    private double price;
    private double totalPrice;
    private Date createBill;
    private Status status;



    public Rental() {
    }

    public Rental(int idRental,int idCustomer, String nameCustomer, String nameModel, Date starDate, Date endDate, int quantityModel, double price, double totalPrice, Date createBill, Status status) {
        this.idRental = idRental;
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.nameModel = nameModel;
        this.startDate = starDate;
        this.endDate = endDate;
        this.quantityModel = quantityModel;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createBill = createBill;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getQuantityModel() {
        return quantityModel;
    }

    public void setQuantityModel(int quantityModel) {
        this.quantityModel = quantityModel;
    }

    @Override
    public int getId() {
        return idRental;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void update(Rental obj) {
        this.idRental = obj.idRental;
        this.idCustomer = obj.idCustomer;
        this.nameCustomer = obj.nameCustomer;
        this.nameModel = obj.nameModel;
        this.startDate = obj.startDate;
        this.endDate = obj.endDate;
        this.quantityModel = obj.quantityModel;
        this.price = obj.price;
        this.totalPrice = obj.totalPrice;
        this.createBill = obj.createBill;
        this.status = obj.status;
    }


    @Override
    public Rental parseData(String line) {
        Rental rental = new Rental();
        String[] strings = line.split(";");
        int idRental = Integer.parseInt(strings[0]);
        int idCustomer = Integer.parseInt(strings[1]);
        String nameCustomer = strings[2];
        String nameModel = strings[3];
        Date starDate = FormatDateModel.parseDate(strings[4]);
        Date endDate = FormatDateModel.parseDate(strings[5]);
        int quantityModel = Integer.parseInt(strings[6]);
        double priceModel = Double.parseDouble(strings[7]);
        double totalPrice = Double.parseDouble(strings[8]);
        Date createBill = FormatDateModel.parseDate2(strings[9]);
        Status Status = model.Status.getStatusByName(strings[10]);


        rental.setIdRental(idRental);
        rental.setIdCustomer(idCustomer);
        rental.setNameCustomer(nameCustomer);
        rental.setNameModel(nameModel);
        rental.setStartDate(starDate);
        rental.setEndDate(endDate);
        rental.setQuantityModel(quantityModel);
        rental.setPrice(priceModel);
        rental.setTotalPrice(totalPrice);
        rental.setCreateBill(createBill);
        rental.setStatus(Status);
        return rental;
    }

    public String rentalView() {
        return String.format("            ║ %-6s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-15s║ %-15s║ %-14s║%-15s║", this.idRental, this.idCustomer, this.nameCustomer, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), this.quantityModel, CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill), this.status);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", this.idRental, this.idCustomer, this.nameCustomer, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), this.quantityModel, CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill), this.status);
    }
}





//public class Rental implements IModel<Rental> {
//    private  int idRental;
//    private String nameModel;
//    private Date startDate;
//    private Date endDate;
//    private double price;
//    private double totalPrice;
//    private Date createBill;
//
//
//    public Rental() {
//    }
//
//    public Rental(int idRental, String nameModel, Date starDate, Date endDate, double price, double totalPrice, Date createBill) {
//        this.idRental = idRental;
//        this.nameModel = nameModel;
//        this.startDate = starDate;
//        this.endDate = endDate;
//        this.price = price;
//        this.totalPrice = totalPrice;
//        this.createBill = createBill;
//    }
//
//
//
//    public int getIdRental() {
//        return idRental;
//    }
//
//    public void setIdRental(int idRental) {
//        this.idRental = idRental;
//    }
//
//    public String getNameModel() {
//        return nameModel;
//    }
//
//    public void setNameModel(String nameModel) {
//        this.nameModel = nameModel;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public double getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }
//
//    public Date getCreateBill() {
//        return createBill;
//    }
//
//    public void setCreateBill(Date createBill) {
//        this.createBill = createBill;
//    }
//
//
//    @Override
//    public int getId() {
//        return 0;
//    }
//
//
//    @Override
//    public String getName() {
//        return nameModel;
//    }
//
//    @Override
//    public void update(Rental obj) {
//
//    }
//
//    @Override
//    public Rental parseData(String line) {
//        Rental rental = new Rental();
//        String[] strings = line.split(";");
//        int idRental = Integer.parseInt(strings[0]);
//        String nameModel = strings[1];
//        Date starDate = FormatDateModel.parseDate(strings[2]);
//        Date endDate = FormatDateModel.parseDate(strings[3]);
//        Double priceModel = Double.parseDouble(strings[4]);
//        Double totalPrice = Double.parseDouble(strings[5]);
//        Date createBill = FormatDateModel.parseDate2(strings[6]);
//
//
//        rental.setIdRental(idRental);
//        rental.setNameModel(nameModel);
//        rental.setStartDate(starDate);
//        rental.setEndDate(endDate);
//        rental.setPrice(priceModel);
//        rental.setTotalPrice(totalPrice);
//        rental.setCreateBill(createBill);
//        return rental;
//    }
//
//    public String rentalView() {
//        return String.format("            ║ %-6s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║", this.idRental, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill));
//    }
//    @Override
//    public String toString() {
//        return String.format("%s;%s;%s;%s;%s;%s;%s", this.idRental, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill));
//    }
//}