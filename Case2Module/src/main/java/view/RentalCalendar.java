package view;

import model.Rental;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RentalCalendar {


    public static void showRental1(List<Rental> rentals, Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startMonth = startCal.get(Calendar.MONTH);
        int startYear = startCal.get(Calendar.YEAR);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endMonth = endCal.get(Calendar.MONTH);
        int endYear = endCal.get(Calendar.YEAR);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Rental calendar from " + dateFormat.format(startDate) + " to " + dateFormat.format(endDate));

        for (int year = startYear; year <= endYear; year++) {
            int startMonthOfYear = (year == startYear) ? startMonth : Calendar.JANUARY;
            int endMonthOfYear = (year == endYear) ? endMonth : Calendar.DECEMBER;
            for (int month = startMonthOfYear; month <= endMonthOfYear; month++) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1);
                System.out.println("----------------------------------------");
                System.out.println(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + " " + year);
                System.out.println("----------------------------------------");
                System.out.println("Sun Mon Tue Wed Thu Fri Sat");
                int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                int dayOfWeek = 1;
                for (int day = 1; day <= daysInMonth; day++) {
                    if (dayOfWeek == 1) {
                        System.out.print(" ");
                        for (int i = 1; i < firstDayOfWeek; i++) {
                            System.out.print("   ");
                        }
                    }
                    cal.set(year, month, day);
                    boolean isRentalDay = false;
                    for (Rental rental : rentals) {
                        if (rental.getStartDate().compareTo(cal.getTime()) <= 0 && rental.getEndDate().compareTo(cal.getTime()) >= 0) {
                            isRentalDay = true;
                            break;
                        }
                    }
                    String dayOfMonth = String.format("%2d", day);
                    if (isRentalDay) {
                        System.out.print("\u001B[31m" + dayOfMonth + "  " + "\u001B[0m");
                    } else {
                        System.out.print(dayOfMonth + "  ");
                    }
                    if (dayOfWeek == 7) {
                        System.out.println();
                        dayOfWeek = 1;
                    } else {
                        dayOfWeek++;
                    }
                }
                if (dayOfWeek != 1) {
                    System.out.println();
                }
            }
        }
    }


}
