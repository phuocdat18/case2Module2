package test1.car3;

public class Calendar {
    private CarRented[] cars;
    private final int daysInMonth = 30;

    public Calendar() {
        cars = new CarRented[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            cars[i] = new CarRented();
        }
    }

    public void rentCar(int day, int carIndex) {
        cars[day - 1].rentCar(carIndex);
    }

    public boolean isCarRented(int day, int carIndex) {
        return cars[day - 1].isDayRented(carIndex);
    }

    public void printCalendar() {
        System.out.println("Calendar for April 2023");
        System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
        for (int day = 1; day <= daysInMonth; day++) {
            System.out.print(day);
            for (int carIndex = 1; carIndex <= 7; carIndex++) {
                if (isCarRented(day, carIndex)) {
                    System.out.print("  R ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }
}