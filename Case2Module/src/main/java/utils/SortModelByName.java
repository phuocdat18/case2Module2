package utils;

import model.Model;

import java.util.Comparator;

public class SortModelByName implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        return o1.getNameModel().compareTo(o2.getNameModel());
    }
}