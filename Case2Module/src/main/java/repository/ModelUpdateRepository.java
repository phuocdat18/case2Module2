package repository;

import model.Model;

public class ModelUpdateRepository extends FileContext<Model>{
    public ModelUpdateRepository() {
        filePath = "Case2Module/src/main/data/modelUpdate.csv";
        tClass = Model.class;
    }
}
