package test1.car3;

public class Payment {
    private final int pricePerDay = 50;

    public int calculateTotalPrice(int rentedDays) {
        return rentedDays * pricePerDay;
    }
}