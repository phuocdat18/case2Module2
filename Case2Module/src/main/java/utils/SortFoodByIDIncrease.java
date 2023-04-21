package utils;

import model.Model;

import java.util.Comparator;

public class SortFoodByIDIncrease implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        return o1.getIdModel() - o2.getIdModel();
    }
}
