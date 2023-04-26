package test1.car3;

public class Customer {
    private int rentedDays;
    private int rentalDay;
    private boolean hasRentedHondaCivic;

    public Customer() {
        hasRentedHondaCivic = false;
    }

    public void rentHondaCivic(int day) {
        if (hasRentedHondaCivic) {
            System.out.println("Sorry, the Honda Civic is already rented on that day.");
        } else {
            rentalDay = day;
            hasRentedHondaCivic = true;
        }
    }

    public void setRentedDays(int days) {
        rentedDays = days;
    }

    public int getRentedDays() {
        return rentedDays;
    }

    public int getRentalDay() {
        return rentalDay;
    }

    public boolean hasRentedHondaCivic() {
        return hasRentedHondaCivic;
    }
}