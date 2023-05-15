package model;

import utils.CurrencyFormat;
import repository.IModel;
import utils.HeightFormat;
import utils.WeightFormat;


public class Model implements IModel<Model> {
    private int idModel;
    private String nameModel;
    private EGender type;
    private int  age;
    private int height;
    private int weight;
    private int quantityModel;
    private int priceModel;
    private String phoneNumberModel;
    private String addressModel;
    private String description;



    public Model() {
    }

    public Model(int id, int price, EGender type, String name, int age, int height, int weight, int quantity, String phoneNumber, String address, String description) {
        this.idModel = id;
        this.nameModel = name;
        this.type = type;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.quantityModel = quantity;
        this.priceModel = price;
        this.phoneNumberModel = phoneNumber;
        this.addressModel = address;
        this.description = description;
    }

    public Model(int id) {
        this.idModel = id;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public int getPriceModel() {
        return priceModel;
    }

    public void setPriceModel(int priceModel) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQuantityModel() {
        return quantityModel;
    }

    public void setQuantityModel(int quantityModel) {
        this.quantityModel = quantityModel;
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

    @Override
    public int getId() {
        return idModel;
    }


    public String getNameModel() {
        return nameModel;
    }


    @Override
    public String getName() {
        return nameModel;
    }
    @Override
    public void update(Model obj) {
        this.idModel = obj.idModel;
        this.priceModel = obj.priceModel;
        this.type = obj.type;
        this.nameModel = obj.nameModel;
        this.age = obj.age;
        this.height = obj.height;
        this.weight = obj.weight;
        this.quantityModel = obj.quantityModel;
        this.phoneNumberModel = obj.phoneNumberModel;
        this.addressModel = obj.addressModel;
        this.description = obj.description;
    }

    @Override
    public Model parseData(String line) {
        Model model = new Model();
        String[] strings = line.split(";");
        int idModel = Integer.parseInt(strings[0]);
        String nameModel = strings[1];
        EGender type = EGender.getEGenderByName(strings[2]);
        int age = Integer.parseInt(strings[3]);
        int height = Integer.parseInt(strings[4]);
        int weight = Integer.parseInt(strings[5]);
        int quantityModel = Integer.parseInt(strings[6]);
        int priceModel = Integer.parseInt(strings[7]);
        String phoneNumberModel = strings[8];
        String addressModel = strings[9];
        String description = strings[10];



        model.setIdModel(idModel);
        model.setPriceModel(priceModel);
        model.setType(type);
        model.setNameModel(nameModel);
        model.setAge(age);
        model.setHeight(height);
        model.setWeight(weight);
        model.setQuantityModel(quantityModel);
        model.setPhoneNumberModel(phoneNumberModel);
        model.setAddressModel(addressModel);
        model.setDescription(description);
        return model;
    }
    public String modelViewAdmin() {
        return String.format("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-15s║%-40s║", this.idModel, this.nameModel, this.type, this.age, HeightFormat.formatHeight(this.height), WeightFormat.weightFormat(this.weight), this.phoneNumberModel, this.addressModel, CurrencyFormat.convertPriceToString(this.priceModel), this.description);
    }
    public String modelView() {
        return String.format("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", this.idModel, this.nameModel, this.type, this.age, HeightFormat.formatHeight(this.height), WeightFormat.weightFormat(this.weight), this.addressModel, CurrencyFormat.convertPriceToString(this.priceModel), this.description);
    }
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s", this.idModel, this.nameModel, this.type, this.age, this.height, this.weight, this.quantityModel, CurrencyFormat.parseInteger(this.priceModel), this.phoneNumberModel, this.addressModel, this.description);
    }

}
