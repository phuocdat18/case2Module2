package view;

import model.Model;
import model.Rental;
import service.FileService;
import service.ModelService;
import service.RentalService;
import utils.FormatDateModel;
import utils.SortRentalById;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class RentalView {
    private  final String FILE_PATH_MODEL = "Case2Module/src/main/data/rental.csv";
    private  FileService fileService;
    private  RentalService rentalService;
    private  Scanner scanner;

    public RentalView() {
        fileService = new FileService();
        rentalService = new RentalService();
        scanner = new Scanner(System.in);
    }


    public  boolean checkActionContinue() {
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

    public  void showCalendarModel() throws IOException, ParseException {
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
                    CustomerView customerView = new CustomerView();
                    customerView.menuCustomerView();
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


    public  void showRental() throws IOException {
        List<Rental> rentals = rentalService.getAllRental();
        for (Rental rental : rentals) {
            System.out.println(rental.rentalView());

        }
    }




    public  void printMonth(List<Rental> rentals, Date startDate, Date endDate) throws IOException {

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

    public void printMonthFind(List<Rental> rentals) throws IOException {

        CustomerView customerView = new CustomerView();
        List<Rental> results = new ArrayList<>();
        Calendar startCal = Calendar.getInstance();
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
                    if (compareTwoDate(rental.getStartDate(), cal.getTime()) || compareTwoDate(rental.getEndDate(), cal.getTime())) {
                        isRentalDay = true;
                        break;
                    }else {
                        if ((rental.getStartDate().compareTo(cal.getTime()) < 0 && rental.getEndDate().compareTo(cal.getTime()) > 0) ) {
                            isRentalDay = true;
                            break;
                        }
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
    public boolean compareTwoDate(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        return sameDay;
    }

    public void orderModel() throws ParseException, IOException {
        List<Rental> results = new ArrayList<>();
        List<Rental> rentals = rentalService.getAllRental();
        int idModel = 0;
        boolean isInvalidId = true;
        while (isInvalidId) {
            try {
                System.out.println("Vui lòng nhap ID người mẫu");
                idModel = Integer.parseInt(scanner.nextLine());
                if (idModel < 0) {
                    throw new IllegalArgumentException("ID không hợp lệ");
                }
                // Kiểm tra ID có tồn tại trong cơ sở dữ liệu không.
                ModelService modelService = new ModelService();
                Model model = modelService.searchId(idModel);
                if (model == null) {
                    throw new IllegalArgumentException("ID không tồn tại");
                }
                isInvalidId = false;
            } catch (NumberFormatException e) {
                System.out.println("ID phải là số nguyên");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int numberOfDays = 0;
        boolean isInvalidDays = true;
        while (isInvalidDays) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("Vui lòng nhập số ngày thuê: ");
                numberOfDays = Integer.parseInt(scanner.nextLine());
                if (numberOfDays <= 0) {
                    throw new IllegalArgumentException("Số ngày không hợp lệ");
                }
                isInvalidDays = false;
            } catch (NumberFormatException e) {
                System.out.println("Số ngày phải là số nguyên");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String startDateString = "";
        boolean isInvalidDate = true;
        while (isInvalidDate) {
            try {
                System.out.print("Vui lòng nhập ngày bắt đầu thuê (dd/MM/yyyy): ");
                startDateString = scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(startDateString, formatter);
                LocalDate endDate = date.plusDays(numberOfDays);

                // Kiểm tra ngày kết thúc có vượt quá 30 ngày kể từ ngày bắt đầu hay không.
                if (endDate.isAfter(date.plusDays(30))) {
                    throw new IllegalArgumentException("Số ngày thuê không được vượt quá 30 ngày");
                }

                isInvalidDate = false;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate date = LocalDate.parse(startDateString, formatter);
        LocalDate endDate = date.plusDays(numberOfDays - 1);
        String result = formatter.format(endDate);
        Rental rental = new Rental();
        ModelService modelService = new ModelService();
        Model model = modelService.searchId(idModel);
        rentals.sort(new SortRentalById());
//        rental.setIdRental(rentals.get(rentals.size()-1).getId() + 1);
        rental.setIdRental(model.getIdModel());
        rental.setNameModel(model.getNameModel());
        rental.setStartDate(FormatDateModel.parseDate(startDateString));
        rental.setEndDate(FormatDateModel.parseDate(result));
        rental.setPrice(model.getPriceModel());
        double totalPrice = numberOfDays * model.getPriceModel();
        rental.setTotalPrice(totalPrice);
        rental.setCreateBill(new Date());

        rentals.add(rental);
        fileService.writeData(FILE_PATH_MODEL, rentals);
        showModelRental(idModel);
        System.out.println(rental.rentalView());
        printMonth(rentals, rental.getStartDate(), rental.getEndDate());
    }

    public  void showModelRental(int idModel) throws IOException {
        List<Rental> rentals = rentalService.getAllRental();
        for (Rental rental : rentals) {
            if (rental.getIdRental() == idModel) {
                System.out.println(rental.rentalView());
            }
        }
    }

    public static void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}