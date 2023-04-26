package utils;

import model.Model;

import java.util.Comparator;

public class SortModelByPriceDecrease implements Comparator<Model> {

    @Override
    public int compare(Model o1, Model o2) {
        if(o2.getPriceModel() -  o1.getPriceModel() > 0)
        {
            return 1;
        }else if (o2.getPriceModel() -  o1.getPriceModel() == 0) {
            return 0;
        }else {
            return -1;
        }
    }
}
