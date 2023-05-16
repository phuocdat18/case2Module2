package view;

import model.*;
import service.*;
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
    private final String FILE_PATH_RENTAL = "Case2Module/src/main/data/rental.csv";
    private FileService fileService;
    private RentalService rentalService;
    private Scanner scanner;

    public RentalView() {
        fileService = new FileService();
        rentalService = new RentalService();
        scanner = new Scanner(System.in);
    }


    public boolean checkActionContinue() {
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

    public void showCalendarModel() throws IOException, ParseException, InterruptedException {
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


    public void showRental() throws IOException {
        List<Rental> rentals = rentalService.getAllRental();
        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (Rental rental : rentals) {
            System.out.println(rental.rentalView());
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
    }


    public void printMonth(List<Rental> rentals, List<Rental> rentalAll, Date startDate, Date endDate, int idModel) throws IOException, ParseException, InterruptedException {
        CustomerView customerView = new CustomerView();
        UserService userService = new UserService();
        List<Rental> results = new ArrayList<>();
        List<User> users = userService.getAllUserUse();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        int startMonth = startCal.get(Calendar.MONTH);
        int startYear = startCal.get(Calendar.YEAR);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int endMonth = endCal.get(Calendar.MONTH);
        int endYear = endCal.get(Calendar.YEAR);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println();
        System.out.println("Bạn đã book người mẫu từ ngày " + dateFormat.format(startDate) + " đến ngày " + dateFormat.format(endDate));
        System.out.println();
        Thread.sleep(1000);
        System.out.println("Kiểm tra lại lịch trình bằng tháng");

        boolean checkMonth = false;
        do {
            System.out.println();
            Scanner input = new Scanner(System.in);
            int m = 0;
            int y = 0;
            try {
                noChange();
                System.out.print("Vui lòng nhập tháng từ 1 đến 12 (Vd: 5): ");
                m = input.nextInt();
                String number = "" + m;
                if (number.equals("0")) {
                    checkMonth = true;
                    customerView.launcherCustomer();
                }
                if (m <= 0 || m > 12) {
                    throw new IllegalArgumentException("Số tháng phải nằm trong khoảng từ 1 đến 12");
                }
                System.out.print("Vui lòng nhập số năm (Vd: 2023): ");
                y = input.nextInt();
                if (y <= 0) {
                    throw new IllegalArgumentException("Số năm phải lớn hơn 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Dữ liệu nhập vào phải là 1 con số");
                input.nextLine();
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            int year = y;
            int month = m;
            YearMonth ym = YearMonth.of(year, month);
            System.out.println();
            System.out.println();
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
                List<Rental> rentals1 = new ArrayList<>();
                List<Rental> rentalsAll1 = new ArrayList<>();
                for (Rental rental : rentalAll) {
                    if (rental.getIdModel() == idModel) {
                        if (compareTwoDate(rental.getStartDate(), cal.getTime()) || compareTwoDate(rental.getEndDate(), cal.getTime())) {
                            isRentalDay = true;
                            rentalsAll1.add(rental);
                            break;
                        } else {
                            if ((rental.getStartDate().compareTo(cal.getTime()) < 0 && rental.getEndDate().compareTo(cal.getTime()) > 0)) {
                                isRentalDay = true;
                                rentalsAll1.add(rental);
                                break;
                            }
                        }
                    }
                }
                for (Rental rental : rentals) {
                    if (rental.getIdModel() == idModel) {
                        if (compareTwoDate(rental.getStartDate(), cal.getTime()) || compareTwoDate(rental.getEndDate(), cal.getTime())) {
                            isRentalDay = true;
                            rentals1.add(rental);
                            break;
                        } else {
                            if ((rental.getStartDate().compareTo(cal.getTime()) < 0 && rental.getEndDate().compareTo(cal.getTime()) > 0)) {
                                isRentalDay = true;
                                rentals1.add(rental);
                                break;
                            }
                        }
                    }
                }
                if (isRentalDay) {
                    for (Rental rental : rentals1) {
                        if (rental.getIdCustomer() == users.get(0).getId()) {
                            System.out.print("\u001B[34m");
                        } else {
                            System.out.print("\u001B[31m");
                        }
                    }
                    for (Rental rental : rentalsAll1) {
                        if (rental.getIdCustomer() == users.get(0).getId()) {
                            System.out.print("\u001B[34m");
                        } else {
                            System.out.print("\u001B[31m");
                        }
                    }
                }
                System.out.printf("%-4d", i);
                if (isRentalDay) {
                    System.out.print("\u001B[0m");
                }
            }
        }
        while (checkMonth);
        System.out.println();
        System.out.println();
        printMonthFind(rentalAll, rentals, idModel);

    }

    public void printMonthFind(List<Rental> rentalAll, List<Rental> rentals, int searchId) throws IOException, ParseException {
        CustomerView customerView = new CustomerView();
        UserService userService = new UserService();
        List<Rental> results = new ArrayList<>();
        List<User> users = userService.getAllUserUse();
        Calendar startCal = Calendar.getInstance();
        boolean checkMonth = false;
        do {
            System.out.println();
            Scanner input = new Scanner(System.in);
            int m = 0;
            int y = 0;
            try {
                noChange();
                System.out.print("Vui lòng nhập tháng từ 1 đến 12 (Vd: 5): ");
                m = input.nextInt();
                String number = "" + m;
                if (number.equals("0")) {
                    checkMonth = true;
                    customerView.launcherCustomer();
                }
                if (m < 0 || m > 12) {
                    throw new IllegalArgumentException("Số tháng phải nằm trong khoảng từ 1 đến 12");
                }
                System.out.print("Vui lòng nhập số năm (Vd: 2023): ");
                y = input.nextInt();
                if (y <= 0) {
                    throw new IllegalArgumentException("Số năm phải lớn hơn 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Dữ liệu nhập vào phải là 1 con số");
                input.nextLine();
                continue;
            } catch (IllegalArgumentException | InterruptedException e) {
                System.out.println(e.getMessage());
                continue;
            }

            int year = y;
            int month = m;
            YearMonth ym = YearMonth.of(year, month);
            System.out.println();
            System.out.println();
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

                List<Rental> rentals1 = new ArrayList<>();
                List<Rental> rentalsAll1 = new ArrayList<>();
                for (Rental rental : rentalAll) {
                    if (rental.getIdModel() == searchId) {
                        if (compareTwoDate(rental.getStartDate(), cal.getTime()) || compareTwoDate(rental.getEndDate(), cal.getTime())) {
                            isRentalDay = true;
                            rentalsAll1.add(rental);
                            break;
                        } else {
                            if ((rental.getStartDate().compareTo(cal.getTime()) < 0 && rental.getEndDate().compareTo(cal.getTime()) > 0)) {
                                isRentalDay = true;
                                rentalsAll1.add(rental);
                                break;
                            }
                        }
                    }
                }
                for (Rental rental : rentals) {
                    if (rental.getIdModel() == searchId) {
                        if (compareTwoDate(rental.getStartDate(), cal.getTime()) || compareTwoDate(rental.getEndDate(), cal.getTime())) {
                            isRentalDay = true;
                            rentals1.add(rental);
                            break;
                        } else {
                            if ((rental.getStartDate().compareTo(cal.getTime()) < 0 && rental.getEndDate().compareTo(cal.getTime()) > 0)) {
                                isRentalDay = true;
                                rentals1.add(rental);
                                break;
                            }
                        }
                    }
                }
                if (isRentalDay) {
                    for (Rental rental : rentals1) {
                        if (rental.getIdCustomer() == users.get(0).getId()) {
                            System.out.print("\u001B[34m");
                        } else {
                            System.out.print("\u001B[31m");
                        }
                    }
                    for (Rental rental : rentalsAll1) {
                        if (rental.getIdCustomer() == users.get(0).getId()) {
                            System.out.print("\u001B[34m");
                        } else {
                            System.out.print("\u001B[31m");
                        }
                    }
                }
                System.out.printf("%-4d", i);
                if (isRentalDay) {
                    System.out.print("\u001B[0m");
                }
            }
        }
        while (checkMonth);
        System.out.println();
        System.out.println();
        printMonthFind(rentalAll, rentals, searchId);
    }

    public boolean compareTwoDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        return sameDay;
    }

    public void orderModel() throws ParseException, IOException, InterruptedException {
        List<Rental> rentals = rentalService.getAllRental();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        CustomerView customerView = new CustomerView();
        int idModel = 0;
        boolean isInvalidId = true;
        while (isInvalidId) {
            try {
                noChange();
                System.out.println("Vui lòng nhap ID người mẫu");
                idModel = Integer.parseInt(scanner.nextLine());
                String strIdModel = "" + idModel;
                if (strIdModel.equals("0")) {
                    customerView.launcherCustomer();
                }
                if (idModel < 0) {
                    throw new IllegalArgumentException("ID không hợp lệ");

                }
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
        Date startDateInput = null;
        Date endDateInput = null;
        boolean isInvalidDate = true;
        while (isInvalidDate) {
            try {
                System.out.print("Vui lòng nhập ngày bắt đầu thuê (dd/MM/yyyy): ");
                startDateString = scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(startDateString, formatter);
                LocalDate endDate = date.plusDays(numberOfDays - 1);

                startDateInput = FormatDateModel.parseDate(startDateString);
                String endDateString = formatter.format(endDate);
                endDateInput = FormatDateModel.parseDate(endDateString);
                // Kiểm tra ngày kết thúc có vượt quá 30 ngày kể từ ngày bắt đầu hay không.
                if (endDate.isAfter(date.plusDays(30))) {
                    throw new IllegalArgumentException("Số ngày thuê không được vượt quá 30 ngày");
                }
                boolean isModelAvailable = true;
                for (Rental rental : rentalAll) {
                    if (rental.getIdModel() == idModel) {
                        isModelAvailable = false; // idModel trùng với một rental trong danh sách rentalAll
                        if (compareTwoDate(rental.getStartDate(), startDateInput) || compareTwoDate(rental.getEndDate(), endDateInput) || compareTwoDate(rental.getStartDate(), endDateInput) || compareTwoDate(rental.getEndDate(), startDateInput)) {
                            isInvalidDate = true;
                            System.out.println("Ngày thuê đã bị trùng");
                            break;
                        } else if (rental.getStartDate().compareTo(startDateInput) < 0 && rental.getEndDate().compareTo(startDateInput) > 0 || rental.getStartDate().compareTo(endDateInput) < 0 && rental.getEndDate().compareTo(endDateInput) > 0) {
                            isInvalidDate = true;
                            System.out.println("Ngày thuê đã bị trùng");
                            break;
                        } else {
                            isInvalidDate = false;
                        }
                    }
                }
                for (Rental rental : rentals) {
                    if (rental.getIdModel() == idModel) {
                        isModelAvailable = false; // idModel trùng với một rental trong danh sách rentals
                        if (compareTwoDate(rental.getStartDate(), startDateInput) || compareTwoDate(rental.getEndDate(), endDateInput) || compareTwoDate(rental.getStartDate(), endDateInput) || compareTwoDate(rental.getEndDate(), startDateInput)) {
                            isInvalidDate = true;
                            System.out.println("Ngày thuê đã bị trùng");
                            break;
                        } else if (rental.getStartDate().compareTo(startDateInput) < 0 && rental.getEndDate().compareTo(startDateInput) > 0 || rental.getStartDate().compareTo(endDateInput) < 0 && rental.getEndDate().compareTo(endDateInput) > 0) {
                            isInvalidDate = true;
                            System.out.println("Ngày thuê đã bị trùng");
                            break;
                        } else {
                            isInvalidDate = false;
                        }
                    }
                }

                if (isModelAvailable) {
                    isInvalidDate = false;
                }
                if (compareTwoDate(new Date(), startDateInput)) {
                    System.out.println("Ngày thuê đã bị quá hạn");
                    isInvalidDate = true;
                } else if (startDateInput.compareTo(new Date()) < 0) {
                    System.out.println("Ngày thuê đã bị quá");
                    isInvalidDate = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        Rental rental = new Rental();
        ModelService modelService = new ModelService();
        Model model = modelService.searchId(idModel);
        rentals.sort(new SortRentalById());
        int id = rentals.get(rentals.size() - 1).getIdRental() + 1;
        rental.setIdRental(id);
        rental.setIdModel(model.getIdModel());
        rental.setIdCustomer(UserService.userLoginning.getId());
        rental.setNameCustomer(UserService.userLoginning.getName());
        rental.setNameModel(model.getName());
        rental.setStartDate(startDateInput);
        rental.setEndDate(endDateInput);
        rental.setQuantityModel(model.getQuantityModel());
        rental.setPrice(model.getPriceModel());
        int totalPrice = numberOfDays * model.getPriceModel();
        rental.setTotalPrice(totalPrice);
        rental.setCreateBill(new Date());
        rental.setStatus(EStatus.UNPAID);
        rentals.add(rental);
        fileService.writeData(FILE_PATH_RENTAL, rentals);

        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        System.out.println(rental.rentalView());
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");









        int select = 0;
        boolean checkAction = false;
        do {
            orderNext();
            System.out.println("Chọn chức năng:");
            try {
                select = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Nhập lỗi, vui lòng nhập lại!");
                select = 0;
                continue;
            }
            switch (select) {
                case 1:
                    orderModel();
                    break;
                case 2:
                    printMonth(rentals, rentalAll, rental.getStartDate(), rental.getEndDate(), idModel);
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            customerView.menuCustomerView();
        }
    }

    public  void orderNext() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                   [1] Tiếp tục thuê                                               ║");
        System.out.println("                               ║                   [2] Kiểm tra lại bằng tháng                                     ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void showModelRental(int idModel) throws IOException {
        List<Rental> rentals = rentalService.getAllRental();
        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (Rental rental : rentals) {
            if (rental.getIdModel() == idModel) {
                System.out.println(rental.rentalView());
            }
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
    }

    public static void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}