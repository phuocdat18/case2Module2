package repository;

import model.Model;

public class FoodRepository extends FileContext<Module> {
    public FoodRepository() {
        filePath = "./src/main/data/food.csv";
        tClass = Model.class;
    }
}
