package model;

import repository.IModel;
import utils.CurrencyFormat;
import utils.FormatDateModel;

import java.util.Date;

public class Rental implements IModel<Rental> {
    private  int idRental;
    private  int idModel;
    private int idCustomer;
    private String nameCustomer;
    private String nameModel;
    private Date startDate;
    private Date endDate;
    private int quantityModel;
    private int price;
    private int totalPrice;
    private Date createBill;
    private EStatus status;




    public Rental() {
    }

    public Rental(int idRental,int idModel,int idCustomer, String nameCustomer, String nameModel, Date starDate, Date endDate, int quantityModel, int price, int totalPrice, Date createBill, EStatus status) {
        this.idRental = idRental;
        this.idModel = idModel;
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


    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
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
        return nameModel;
    }

    @Override
    public void update(Rental obj) {
        this.idRental = obj.idRental;
        this.idModel = obj.idModel;
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
        int idModel = Integer.parseInt(strings[1]);
        int idCustomer = Integer.parseInt(strings[2]);
        String nameCustomer = strings[3];
        String nameModel = strings[4];
        Date starDate = FormatDateModel.parseDate(strings[5]);
        Date endDate = FormatDateModel.parseDate(strings[6]);
        int quantityModel = Integer.parseInt(strings[7]);
        int priceModel = Integer.parseInt(strings[8]);
        int totalPrice = Integer.parseInt(strings[9]);
        Date createBill = FormatDateModel.parseDate2(strings[10]);
        EStatus status = EStatus.getStatusByName(strings[11]);



        rental.setIdRental(idRental);
        rental.setIdModel(idModel);
        rental.setIdCustomer(idCustomer);
        rental.setNameCustomer(nameCustomer);
        rental.setNameModel(nameModel);
        rental.setStartDate(starDate);
        rental.setEndDate(endDate);
        rental.setQuantityModel(quantityModel);
        rental.setPrice(priceModel);
        rental.setTotalPrice(totalPrice);
        rental.setCreateBill(createBill);
        rental.setStatus(status);
        return rental;
    }

    public String rentalView() {
        return String.format("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", this.idRental, this.idModel, this.idCustomer, this.nameCustomer, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill), this.status);
    }

    public String rentalView1() {
        return String.format("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", this.idModel, this.idCustomer, this.nameCustomer, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), CurrencyFormat.convertPriceToString(this.price), CurrencyFormat.convertPriceToString(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill), this.status);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", this.idRental, this.idModel, this.idCustomer, this.nameCustomer, this.nameModel, FormatDateModel.convertDateToString(this.startDate), FormatDateModel.convertDateToString(this.endDate), this.quantityModel, CurrencyFormat.parseInteger(this.price), CurrencyFormat.parseInteger(this.totalPrice), FormatDateModel.convertDateToString2(this.createBill), this.status);
    }
}
