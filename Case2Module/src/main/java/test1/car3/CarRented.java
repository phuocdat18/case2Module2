package test1.car3;

public class CarRented {
    private boolean[] rentedDays;

    public CarRented() {
        rentedDays = new boolean[30];
    }

    public void rentCar(int day) {
        rentedDays[day - 1] = true;
    }

    public boolean isDayRented(int day) {
        return rentedDays[day - 1];
    }
}

