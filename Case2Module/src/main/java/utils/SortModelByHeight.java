package utils;

import model.Model;

import java.util.Comparator;

public class SortModelByHeight implements Comparator<Model> {
    @Override
    public int compare(Model o1, Model o2) {
        return o1.getHeight().compareTo(o2.getHeight());
    }
}
