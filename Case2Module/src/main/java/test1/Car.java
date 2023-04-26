package test1;

import java.util.Calendar;
import java.util.Date;

public class Car {
    private String customerName;
    private Date startDate;
    private int numberOfDays;
    private boolean[] rentalCalendar = new boolean[30];

    public Car(String name, Date start, int days) {
        this.customerName = name;
        this.startDate = start;
        this.numberOfDays = days;

        // Mark the days in the rental calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        for (int i = 0; i < numberOfDays; i++) {
            rentalCalendar[calendar.get(Calendar.DAY_OF_MONTH) - 1] = true;
            calendar.add(Calendar.DATE, 1);
        }
    }

    public void displayCalendar() {
        // Print the month and year
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        System.out.println("April " + calendar.get(Calendar.YEAR));

        // Print the calendar header
        System.out.println("Su Mo Tu We Th Fr Sa");

        // Print the calendar body
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < dayOfWeek; i++) {
            System.out.print("   ");
        }
        for (int i = 1; i <= 30; i++) {
            if (rentalCalendar[i - 1]) {
                System.out.printf("[%2d]", i);
            } else {
                System.out.printf(" %2d ", i);
            }
            if ((i + dayOfWeek) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public boolean overlapsWith(Car other) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        for (int i = 0; i < numberOfDays; i++) {
            if (other.rentalCalendar[calendar.get(Calendar.DAY_OF_MONTH) - 1]) {
                return true;
            }
            calendar.add(Calendar.DATE, 1);
        }
        return false;
    }

    public static void main(String[] args) {
        // Create two CarRental objects
        Car rental1 = new Car("John", new Date(2023, 3, 10), 3);
        Car rental2 = new Car("Mary", new Date(2023, 3, 16), 3);

        // Display the calendar for rental1
        rental1.displayCalendar();

        // Check if rental2 overlaps with rental1
        if (rental2.overlapsWith(rental1)) {
            System.out.println("Error: Rental dates overlap!");
        } else {
            System.out.println("Rental dates available.");
        }

        // Highlight the rental days in the calendar for rental2
        if (rental1.startDate.compareTo(rental2.startDate) != 0) {
            rental2.displayCalendar();
        } else {
            System.out.println("Rental dates overlap with previous rental.");
        }
    }
}
