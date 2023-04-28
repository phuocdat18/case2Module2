package utils;


import model.Rental;

import java.util.Comparator;

public class SortRentalById implements Comparator<Rental> {
    @Override
    public int compare(Rental o1, Rental o2) {
        return o1.getIdRental() - o2.getIdRental();
    }
}
