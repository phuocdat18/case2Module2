package utils;

import model.Model;

import java.util.Comparator;

public class SortModelByWeight implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        return Double.compare(o1.getWeight(), o2.getWeight());
    }
}
