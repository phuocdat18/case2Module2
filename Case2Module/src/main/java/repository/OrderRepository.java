package repository;

import model.Model;

public class OrderRepository extends FileContext<Model> {
    public OrderRepository() {
        filePath = "src/main/data/order.csv";
        tClass = Model.class;
    }
}
