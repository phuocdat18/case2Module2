package view;

import model.*;
import service.*;
import utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private static final String FILE_PATH_MODEL = "./Case2Module/src/main/data/model.csv";
    private final String FILE_PATH_RENTALALL = "./Case2Module/src/main/data/rentalAll.csv";
    private final String FILE_PATH_RENTAL = "./Case2Module/src/main/data/rental.csv";
    private ModelService modelService;
    private UserService userService;
    private RentalService rentalService;
    private FileService fileService;
    private Scanner scanner;

    public OrderView() {
        modelService = new ModelService();
        userService = new UserService();
        fileService = new FileService();
        rentalService = new RentalService();
        scanner = new Scanner(System.in);
    }


    public void deleteModelOutOderByIdOder() throws IOException, ParseException, InterruptedException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Bạn chưa book người mẫu để thực hiện chức năng này!");
            boolean checkEdit = false;
            do {
                System.out.println("Nhập \"Y\" để book người mẫu hoăc \"0\" để quay lại! ");
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "Y":
                        checkEdit = true;
                        RentalView rentalView = new RentalView();
                        rentalView.orderModel();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcherCustomer();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            showOderNow();
            int idOrder = 0;
            int idOderAll = 0;
            String nameModel = null;
            boolean checkId = false;
            do {
                noChange();
                boolean checkAction = false;
                System.out.println("Nhập ID oder bạn muốn xóa:");
                String inputID = scanner.nextLine();
                if (inputID.equals("0")) {
                    checkId = true;
                    customerView.launcherCustomer();
                }
                if (ValidateUtils.isId(idOrder, inputID)) {
                    idOrder = Integer.parseInt(inputID);
                } else {
                    continue;
                }
                int checkIdOrder = rentalService.checkIdOrder(idOrder);
                switch (checkIdOrder) {
                    case 1:
                        rentalService.deleteModelOutOrderById(idOrder);
                        for (int i = 0; i < rentals.size(); i++) {
                            if (rentals.get(i).getIdRental() == idOrder) {
                                nameModel = rentals.get(i).getNameModel();
                            }
                        }
                        for (int i = 0; i < rentalAll.size(); i++) {
                            if (rentalAll.get(i).getNameModel().equals(nameModel) && rentalAll.get(i).getIdCustomer() == users.get(0).getId()) {
                                idOderAll = rentalAll.get(i).getIdRental();
                            }
                        }
                        rentalService.deleteModelOutRentalAllById(idOderAll);
                        checkId = true;
                        break;
                    case -1:
                        System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                        checkId = false;
                        break;
                }
            } while (!checkId);
//            showOderNow();
            System.out.println("✔ Bạn đã xóa người mẫu thành công ✔\n");
        }
    }

    public void findOderById() throws IOException, ParseException, InterruptedException {
        AdminView adminView = new AdminView();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        noChange();
        int idOrder = 0;
        boolean checkIdOrder = false;
        do {
            System.out.println("Nhập ID book người mẫu bạn muốn tìm");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                checkIdOrder = true;
                adminView.launcherOder();
            }
            if (ValidateUtils.isId(idOrder, input)) {
                idOrder = Integer.parseInt(input);
            } else {
                continue;
            }
            int select = rentalService.checkIdRentalAll(idOrder);
            switch (select) {
                case 1:
                    System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                    System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                    System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                    for (int i = 0; i < rentalAll.size(); i++) {
                        if (rentalAll.get(i).getIdModel() == idOrder) {
                            System.out.printf(rentalAll.get(i).rentalView()).println();
                        }
                    }
                    for (int i = 0; i < rentals.size(); i++) {
                        if (rentals.get(i).getIdModel() == idOrder) {
                            System.out.printf(rentals.get(i).rentalView()).println();
                        }
                    }
                    System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
                    checkIdOrder = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, vui lòng nhập lại!");
                    checkIdOrder = false;
                    break;
            }
        } while (!checkIdOrder);

    }

    public void showOderNow() throws IOException {
        List<Rental> rentals = rentalService.getAllRental();
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                System.out.printf(rentals.get(i).rentalView()).println();
            }
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");

    }

    public void showHistoryOder() throws IOException, ParseException, InterruptedException {
        CustomerView customerView = new CustomerView();
        List<Rental> rentals = rentalService.getAllRental();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Chưa có đơn hàng, mời bạn thêm Book người mẫu để thực hiện chức năng này!");
            boolean checkEdit = false;
            do {
                System.out.println("Nhập \"Y\" để book hoăc \"0\" để quay lại! ");
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "Y":
                        checkEdit = true;
                        RentalView rentalView = new RentalView();
                        rentalView.orderModel();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcherCustomer();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                    System.out.printf(rentals.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void showHistoryOderPaid() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        User user = users.get(0);
        int count = 0;
        for (int i = 0; i < rentalAll.size(); i++) {
            if (rentalAll.get(i).getIdCustomer() == user.getId() && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Bạn chưa có đơn hàng nào đã thanh toán. Không thể xem book!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getIdCustomer() == user.getId() && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(rentalAll.get(i).rentalView1()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void payOder() throws IOException, ParseException, InterruptedException {
        FileService fileService = new FileService();
        CustomerView customerView = new CustomerView();
        List<Rental> rentals = rentalService.getAllRental();
        List<Rental> rentalsNew = new ArrayList<>();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<User> users = userService.getAllUserUse();
        int totalMoney = 0;
        int count = 0;
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Bạn chưa có món, mời bạn thêm món để thực hiện chức năng này!");
            boolean checkEdit = false;
            do {
                System.out.println("Nhập \"Y\" để thêm món hoăc \"0\" để quay lại! ");
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "Y":
                        checkEdit = true;
                        RentalView rentalView = new RentalView();
                        rentalView.orderModel();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcherCustomer();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                    totalMoney += rentals.get(i).getTotalPrice();
                    rentals.get(i).setStatus(EStatus.PAID);
                    System.out.printf(rentals.get(i).rentalView()).println();
                    rentalAll.add(rentals.get(i));
                }
            }
            System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
            System.out.printf("            ║                                                    TỔNG TIỀN CẦN THANH TOÁN                                        ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalMoney)).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getIdRental() == users.get(0).getId()) {
                    rentalAll.get(i).setStatus(EStatus.PAID);
                }
            }

            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getIdCustomer() != users.get(0).getId()) {
                    rentalsNew.add(rentals.get(i));
                }
            }
        }
        fileService.writeData(FILE_PATH_RENTAL, rentalsNew);
        fileService.writeData(FILE_PATH_RENTALALL, rentalAll);
        System.out.println("✔ Bạn đã thanh toán thành công ✔\n");
    }

    public void showRevenueByDay() throws IOException, ParseException, InterruptedException {
        AdminView adminView = new AdminView();
        noChange();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        if (rentalAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            String date = null;
            boolean checkDate = false;
            do {
                System.out.println("Nhập ngày tháng năm bạn muốn xem doanh thu: dd/MM/yyyy");
                date = scanner.nextLine();
                if (date.equals("0")) {
                    checkDate = true;
                    adminView.launcherRevenue();
                }
                checkDate = ValidateUtils.isDay(date);
                if (!checkDate) {
                    System.out.println("Ngày tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: dd/MM/yyyy");
                }
            } while (!checkDate);
            int count = 0;
            for (int i = 0; i < rentalAll.size(); i++) {
                if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(date) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Ngày bạn muốn xem không có doanh thu!");
            } else {
                int totalRevenueByDay = 0;
                System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                for (int i = 0; i < rentalAll.size(); i++) {
                    if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(date) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByDay += rentalAll.get(i).getTotalPrice();
                        System.out.printf(rentalAll.get(i).rentalView()).println();
                    }
                }
                System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
                System.out.printf("            ║                                                    TỔNG DOANH THU THEO NGÀY                                        ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenueByDay)).println();
                System.out.println("            ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
            }
        }
    }

    public void showRevenueByMonth() throws IOException, ParseException, InterruptedException {
        AdminView adminView = new AdminView();
        noChange();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        if (rentalAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            String month = null;
            boolean checkMonth = false;
            do {
                System.out.println("Nhập tháng năm bạn muốn xem doanh thu: MM/yyyy");
                month = scanner.nextLine();
                if (month.equals("0")) {
                    checkMonth = true;
                    adminView.launcherRevenue();
                }
                checkMonth = ValidateUtils.isMonth(month);
                if (!checkMonth) {
                    System.out.println("Tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: MM/yyyy");
                }
            } while (!checkMonth);
            int count = 0;
            for (int i = 0; i < rentalAll.size(); i++) {
                if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(month) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Tháng bạn muốn xem không có doanh thu!");
            } else {
                int totalRevenueByMonth = 0;
                System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                for (int i = 0; i < rentalAll.size(); i++) {
                    if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(month) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByMonth += rentalAll.get(i).getTotalPrice();
                        System.out.printf(rentalAll.get(i).rentalView()).println();
                    }
                }
                System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
                System.out.printf("            ║                                                    TỔNG DOANH THU THEO THÁNG                                       ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenueByMonth)).println();
                System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
            }
        }
    }

    public void showTotalRevenue() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        if (rentalAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            int totalRevenue = 0;
            for (int i = 0; i < rentalAll.size(); i++) {
                totalRevenue += rentalAll.get(i).getTotalPrice();
            }
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
            System.out.printf("            ║                                                          TỔNG DOANH THU                                            ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenue)).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
        }
    }

    public void showOderAll() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                System.out.printf(rentalAll.get(i).rentalView()).println();
            }
            for (int i = 0; i < rentals.size(); i++) {
                System.out.printf(rentals.get(i).rentalView()).println();
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void showOderUnPaid() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getStatus().equals(EStatus.UNPAID)) {
                    System.out.printf(rentals.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void showOderPaid() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Order", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }


    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}
