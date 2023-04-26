package repository;

import model.Model;

public class ModelRepository extends FileContext<Model> {
    public ModelRepository() {
        filePath = "Case2Module/src/main/data/model.csv";
        tClass = Model.class;
    }
}
