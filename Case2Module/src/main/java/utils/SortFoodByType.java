package utils;

import model.Model;

import java.util.Comparator;

public class SortFoodByType implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        return o1.getType().getName().hashCode() - o2.getType().getName().hashCode();
    }
}
