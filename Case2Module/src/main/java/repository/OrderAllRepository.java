package repository;

import model.Model;

public class OrderAllRepository extends FileContext<Model> {
    public OrderAllRepository() {
        filePath = "./src/main/data/orderAll.csv";
        tClass = Model.class;
    }
}
