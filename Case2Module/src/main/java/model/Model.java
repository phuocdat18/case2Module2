package model;

import utils.CurrencyFormat;
import utils.DateFormat;

import java.util.Date;

public class Model {
    private int idModel;
    private double priceModel;
    private EGender type;
    private String nameModel;
    private Date birthDayModel;
    private double height;
    private double weight;
    private String phoneNumberModel;
    private String addressModel;
    private String description;


    public Model() {
    }

    public Model(int id, double price, EGender type, String name, Date birthDay, double height, double weight, String phoneNumber, String address, String description) {
        this.idModel = id;
        this.priceModel = price;
        this.type = type;
        this.nameModel = name;
        this.birthDayModel = birthDay;
        this.height = height;
        this.weight = weight;
        this.phoneNumberModel = phoneNumber;
        this.addressModel = address;
        this.description = description;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public double getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(double priceModel) {
        this.priceModel = priceModel;
    }

    public EGender getType() {
        return type;
    }

    public void setType(EGender type) {
        this.type = type;
    }

    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
    }

    public Date getBirthDayModel() {
        return birthDayModel;
    }

    public void setBirthDayModel(Date birthDayModel) {
        this.birthDayModel = birthDayModel;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPhoneNumberModel() {
        return phoneNumberModel;
    }

    public void setPhoneNumberModel(String phoneNumberModel) {
        this.phoneNumberModel = phoneNumberModel;
    }

    public String getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(String addressModel) {
        this.addressModel = addressModel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getIdModel() {
        return idModel;
    }


    public String getNameModel() {
        return nameModel;
    }


    public void update(Model obj) {
        this.idModel = obj.idModel;
        this.priceModel = obj.priceModel;
        this.type = obj.type;
        this.nameModel = obj.nameModel;
        this.birthDayModel = obj.birthDayModel;
        this.height = obj.height;
        this.weight = obj.weight;
        this.phoneNumberModel = obj.phoneNumberModel;
        this.addressModel = obj.addressModel;
        this.description = obj.description;
    }


    public Model parseData1(String line) {
        Model model = new Model();
        String[] strings = line.split(",");
        int id = Integer.parseInt(strings[0]);
        Double price = CurrencyFormat.parseDouble(strings[1]);
        EGender type = EGender.getEGenderByName(strings[2]);
        String name = strings[3];
        Date birthDay = DateFormat.parseDate(strings[4]);
        Double height = CurrencyFormat.parseDouble(strings[5]);
        Double weight = CurrencyFormat.parseDouble(strings[6]);
        String phoneNumber = strings[7];
        String address = strings[8];
        String description = strings[9];




        model.setIdModel(id);
        model.setNameModel(name);
        model.setPriceModel(price);
        model.setType(type);
        return model;
    }
    public String foodView() {
        return String.format("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", this.idModel, this.type, this.nameModel, CurrencyFormat.covertPriceToString(this.priceModel), this.type.getName());
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", this.idModel, this.type, this.nameModel, CurrencyFormat.parseInteger(this.priceModel), this.type.getName());
    }
}
