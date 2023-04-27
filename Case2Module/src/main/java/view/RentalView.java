package view;

import model.Model;
import model.Rental;
import service.FileService;
import service.RentalService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class RentalView {
    private final String FILE_PATH_MODEL = "Case2Module/src/main/data/rental.csv";
    private FileService fileService;
    private static RentalService rentalService;
    private static Scanner scanner;

    public RentalView() {
        fileService = new FileService();
        rentalService = new RentalService();
        scanner = new Scanner(System.in);
    }


    public static boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để quay về giao diện trước đó, nhập \"N\" để quay về giao diện Admin!");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return false;
                case "N":
                    return true;
                default:
                    checkActionContinue = false;
            }
        } while (!checkActionContinue);
        return true;
    }

    public static void showCalendarModel() throws IOException {
        int number = 0;
        boolean checkAction = false;
        String type = null;
        do {
            System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("                               ║                        1. Hiển thị lịch trình người mẫu                           ║");
            System.out.println("                               ║                        2. Quay lại Menu                                           ║");
            System.out.println("                               ║                        3. Đăng xuất                                               ║");
            System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
            try {
                String input = scanner.nextLine();
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\t \uD83D\uDC80 Lựa chọn phải là 1 số \uD83D\uDC80");
                continue;
            }
            switch (number) {
                case 1:
                    showRental();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    CustomerView.menuCustomerView();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    Menu menu = new Menu();
                    menu.login();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
    }


    public static void showRental() throws IOException {
        List<Rental> rentals = rentalService.getAllOrder();
        for (Rental rental : rentals) {
            System.out.println(rental.rentalView());
            printMonth(rentals, rental.getStartDate(), rental.getEndDate());
        }
    }

    public static void printMonth(List<Rental> rentals, Date startDate, Date endDate) throws IOException {

        CustomerView customerView = new CustomerView();
        List<Rental> results = new ArrayList<>();
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

        boolean checkMonth = false;
        do {
            System.out.println();
            noChange();
            Scanner input = new Scanner(System.in);
            int m = 0;
            int y = 0;
            try {
                System.out.print("Please enter a month between 1 and 12 (e.g. 5): ");
                m = input.nextInt();
                if (m <= 0 || m > 12) {
                    throw new IllegalArgumentException("Month must be between 1 and 12");
                }
                System.out.print("Please enter a full year (e.g. 2018): ");
                y = input.nextInt();
                if (y <= 0) {
                    throw new IllegalArgumentException("Year must be greater than 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // discard the invalid input
                continue; // start the loop again
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue; // start the loop again
            }

            int year = y;
            int month = m;
            YearMonth ym = YearMonth.of(year, month);
            System.out.println("Sun Mon Tue Wed Thu Fri Sat");
            int counter = 1;

            int dayValue = LocalDate.of(year, month, 1).getDayOfWeek().getValue();
            if (dayValue != 7)
                for (int i = 0; i < dayValue; i++, counter++) {
                    System.out.printf("%-4s", "");
                }
            for (int i = 1; i <= ym.getMonth().length(ym.isLeapYear()); i++, counter++) {
                if ((counter - 1) % 7 == 0) {
                    System.out.println();
                }
                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, i);
                boolean isRentalDay = false;
                for (Rental rental : rentals) {
                    if (rental.getStartDate().compareTo(cal.getTime()) <= 0 && rental.getEndDate().compareTo(cal.getTime()) >= 0) {
                        isRentalDay = true;
                        break;
                    }
                }
                if (isRentalDay) {
                    System.out.print("\u001B[31m");
                }
                System.out.printf("%-4d", i);
                if (isRentalDay) {
                    System.out.print("\u001B[0m");
                }
            }
        }
        while (!checkMonth);
    }


    public static void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}