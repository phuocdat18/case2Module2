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

    public void addModelInOderByIdCustomer() throws IOException, ParseException {
//        int idOder, String nameCustomer, String nameModel, int quantityModel, double priceModel, double totalMoney, Date createDateOder
        CustomerView customerView = new CustomerView();
        ModelView modelView = new ModelView();
        modelView.showModelList();
        List<Model> models = modelService.getAllModel();
        List<User> users = userService.getAllUserUse();
        List<Rental> orderAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        orderAll.sort(new SortRentalById());
        Rental rental = new Rental();
        Rental rentalNew = new Rental();
        int idModel = 0;
        String nameModel = null;
        boolean checkId = false;
        do {
            noChange();
            boolean checkAction = false;
            System.out.println("Nhập ID người mẫu bạn muốn oder:");
            String inputID = scanner.nextLine();
            if (inputID.equals("0")) {
                checkId = true;
                customerView.launcherCustomer();
            }
            if (ValidateUtils.isId(idModel, inputID)) {
                idModel = Integer.parseInt(inputID);
            } else {
                continue;
            }
            int checkIdModel = modelService.checkIdModel(idModel);
            switch (checkIdModel) {
                case 1:
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getIdModel() == idModel) {
                            nameModel = models.get(i).getNameModel();
                        }
                    }
                    if (!rentals.isEmpty()) {
                        int index = 0;
                        boolean checkNameModelAvailable = false;
                        for (int i = 0; i < rentals.size(); i++) {
                            if (rentals.get(i).getNameModel().equals(nameModel) && users.get(0).getId() == rentals.get(i).getIdCustomer()) {
                                index = i;
                                checkNameModelAvailable = true;
                            }
                        }
                        if (checkNameModelAvailable) {
                            noChange();
//                            System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                            int quantity = 0;
                            boolean checkValid = false;
                            boolean checkQuantity = false;
                            do {
                                noChange();
//                                System.out.println("Nhập số lượng:");
                                String inputQuantity = "1";
                                if (inputQuantity.equals("0")) {
                                    checkId = true;
                                    customerView.launcherCustomer();
                                }
                                checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                                if (!checkValid) {
                                    checkQuantity = false;
                                } else {
                                    quantity = Integer.parseInt(inputQuantity);
                                    for (int j = 0; j < models.size(); j++) {
                                        if (models.get(j).getNameModel().equals(nameModel)) {
                                            if (quantity <= models.get(j).getQuantityModel()) {
                                                checkQuantity = true;
                                            } else {
//                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + models.get(j).getQuantityModel());
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } while (!checkQuantity);
                            int quantityNew = rentals.get(index).getQuantityModel() + quantity;
                            int total = rentals.get(index).getPrice() * quantityNew;
                            rental.setIdRental(rentals.get(index).getIdRental());
                            rental.setIdCustomer(rentals.get(index).getIdCustomer());
                            rental.setNameCustomer(rentals.get(index).getNameCustomer());
                            rental.setNameModel(nameModel);
                            rental.setQuantityModel(quantityNew);
                            rental.setPrice(rentals.get(index).getPrice());
                            rental.setTotalPrice(total);
                            rental.setCreateBill(new Date());
                            rental.setStatus(rentals.get(index).getStatus());
                            rentals.set(index, rental);
                            for (int j = 0; j < models.size(); j++) {
                                if (models.get(j).getNameModel().equals(nameModel)) {
                                    models.get(j).setQuantityModel(models.get(j).getQuantityModel() - quantity);
                                }
                            }
                            fileService.writeData(FILE_PATH_RENTAL, rentals);
                            fileService.writeData(FILE_PATH_MODEL, models);
                            for (int j = 0; j < orderAll.size(); j++) {
                                if (orderAll.get(j).getIdCustomer() == users.get(0).getId() && orderAll.get(j).getNameModel().equals(nameModel) && orderAll.get(j).getStatus().equals(EStatus.UNPAID)) {
                                    orderAll.get(j).setQuantityModel(quantityNew);
                                    fileService.writeData(FILE_PATH_RENTALALL, orderAll);
                                }
                            }
                        }
                        else {
                            int quantity = 0;
                            boolean checkValid = false;
                            boolean checkQuantity = false;
                            do {
                                noChange();
//                                System.out.println("Nhập số lượng:");
                                String inputQuantity = "1";
                                if (inputQuantity.equals("0")) {
                                    checkId = true;
                                    customerView.launcherCustomer();
                                }
                                checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                                if (!checkValid) {
                                    checkQuantity = false;
                                } else {
                                    quantity = Integer.parseInt(inputQuantity);
                                    for (int j = 0; j < models.size(); j++) {
                                        if (models.get(j).getIdModel() == idModel) {
                                            if (quantity <= models.get(j).getQuantityModel()) {
                                                checkQuantity = true;
                                            } else {
//                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + models.get(j).getQuantityModel());
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } while (!checkQuantity);
                            int price = 0;
                            for (int j = 0; j < models.size(); j++) {
                                if (models.get(j).getIdModel() == idModel) {
                                    price = models.get(j).getPriceModel();
                                    models.get(j).setQuantityModel(models.get(j).getQuantityModel() - quantity);
                                }
                            }


                            int totalMoney = quantity * price;
                            rental.setIdRental(rentals.get(rentals.size() - 1).getIdRental() + 1);
                            rental.setIdCustomer(users.get(0).getId());
                            rental.setNameCustomer(users.get(0).getFullName());
                            rental.setNameModel(nameModel);
                            rental.setQuantityModel(quantity);
                            rental.setPrice(price);
                            rental.setTotalPrice(totalMoney);
                            rental.setCreateBill(new Date());
                            rental.setStatus(EStatus.UNPAID);
                            rentals.add(rental);
                            fileService.writeData(FILE_PATH_RENTAL, rentals);
                            fileService.writeData(FILE_PATH_MODEL, models);
                        }
                    } else if (rentals.isEmpty()) {

                        int quantity = 0;
                        boolean checkValid = false;
                        boolean checkQuantity = false;
                        do {
                            noChange();
//                            System.out.println("Nhập số lượng:");
                            String inputQuantity = "1";
                            if (inputQuantity.equals("exit")) {
                                checkId = true;
                                customerView.launcherCustomer();
                            }
                            checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                            if (!checkValid) {
                                checkQuantity = false;
                            } else {
                                quantity = Integer.parseInt(inputQuantity);
                                for (int j = 0; j < models.size(); j++) {
                                    if (models.get(j).getIdModel() == idModel) {
                                        if (quantity <= models.get(j).getQuantityModel()) {
                                            checkQuantity = true;
                                        } else {
//                                            System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + models.get(j).getQuantityModel());
                                            checkQuantity = false;
                                        }
                                    }
                                }
                            }
                        } while (!checkQuantity);
                        int price = 0;
                        for (int j = 0; j < models.size(); j++) {
                            if (models.get(j).getIdModel() == idModel) {
                                price = models.get(j).getPriceModel();
                                models.get(j).setQuantityModel(models.get(j).getQuantityModel() - quantity);
                            }
                        }
                        int totalMoney = quantity * price;
                        rental.setIdRental(1);
                        rental.setIdCustomer(users.get(0).getId());
                        rental.setNameCustomer(users.get(0).getFullName());
                        rental.setNameModel(nameModel);
                        rental.setQuantityModel(quantity);
                        rental.setPrice(price);
                        rental.setTotalPrice(totalMoney);
                        rental.setCreateBill(new Date());
                        rental.setStatus(EStatus.UNPAID);
                        rentals.add(rental);
                        fileService.writeData(FILE_PATH_RENTAL, rentals);
                        fileService.writeData(FILE_PATH_MODEL, models);
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);
        rentalNew.setIdRental(orderAll.get(orderAll.size() - 1).getIdRental() + 1);
        rentalNew.setIdCustomer(rental.getIdCustomer());
        rentalNew.setNameCustomer(rental.getNameCustomer());
        rentalNew.setNameModel(rental.getNameModel());
        rentalNew.setQuantityModel(rental.getQuantityModel());
        rentalNew.setPrice(rental.getPrice());
        rentalNew.setTotalPrice(rental.getTotalPrice());
        rentalNew.setCreateBill(rental.getCreateBill());
        rentalNew.setStatus(rental.getStatus());
        orderAll.add(rentalNew);
        fileService.writeData(FILE_PATH_RENTALALL, orderAll);
        showOderNow();
        System.out.println("✔ Bạn đã thêm người mẫu thành công ✔\n");
    }

    public void editQuantityModelInOderByIdOder() throws IOException, ParseException {

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
            System.out.println("Bạn chưa Book người mẫu để thực hiện chức năng này!");
            boolean checkEdit = false;
            do {
                System.out.println("Nhập \"Y\" để book người mẫu hoăc \"0\" để quay lại! ");
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "Y":
                        checkEdit = true;
                        addModelInOderByIdCustomer();
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
            String nameModel = null;
            boolean checkId = false;
            do {
                noChange();
                boolean checkAction = false;
                System.out.println("Nhập ID oder bạn muốn chỉnh sửa:");
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
                        for (int i = 0; i < rentals.size(); i++) {
                            if (rentals.get(i).getIdRental() == idOrder) {
                                nameModel = rentals.get(i).getNameModel();
                            }
                        }
                        noChange();
                        int quantity = 0;
                        boolean checkValid = false;
                        boolean checkQuantity = false;
                        do {
//                            System.out.println("Nhập số lượng bạn muốn sửa: Số lượng từ 1-1000");
                            String inputQuantity = "1";
                            if (inputQuantity.equals("0")) {
                                checkId = true;
                                customerView.launcherCustomer();
                            }
                            checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                            if (checkValid) {
                                quantity = Integer.parseInt(inputQuantity);
                                for (int i = 0; i < rentals.size(); i++) {
                                    for (int j = 0; j < models.size(); j++) {
                                        if (models.get(j).getNameModel().equals(nameModel) && rentals.get(i).getIdRental() == idOrder) {
                                            if (rentals.get(i).getQuantityModel() <= quantity && quantity <= models.get(j).getQuantityModel()) {
                                                rentals.get(i).setQuantityModel(quantity);
                                                models.get(i).setQuantityModel(models.get(i).getQuantityModel() + rentals.get(i).getQuantityModel() - quantity);
                                                checkQuantity = true;
                                            } else if (rentals.get(i).getQuantityModel() > quantity) {
                                                rentals.get(i).setQuantityModel(quantity);
                                                models.get(i).setQuantityModel(models.get(i).getQuantityModel() + rentals.get(i).getQuantityModel() - quantity);
                                                checkQuantity = true;
                                            } else if (quantity > models.get(j).getQuantityModel() + rentals.get(i).getQuantityModel()) {
//                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } else {
                                checkQuantity = false;
                            }
                        } while (!checkQuantity);

                        for (int i = 0; i < rentalAll.size(); i++) {
                            if (rentalAll.get(i).getNameModel().equals(nameModel) && users.get(0).getId() == rentalAll.get(i).getIdCustomer() && rentalAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                                rentalAll.get(i).setQuantityModel(quantity);
                            }
                        }
                        checkId = true;
                        break;
                    case -1:
                        System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                        checkId = false;
                        break;
                }
            } while (!checkId);

            fileService.writeData(FILE_PATH_RENTAL, rentals);
            fileService.writeData(FILE_PATH_RENTALALL, rentalAll);
            fileService.writeData(FILE_PATH_MODEL, models);
//            showOderNow();
//            System.out.println("✔ Bạn đã cập nhật số lượng thành công ✔\n");
        }

    }

    public void deleteModelOutOderByIdOder() throws IOException, ParseException {
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
                        addModelInOderByIdCustomer();
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

    public void findOderById() throws IOException, ParseException {
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
                    System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                    System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                    System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                    for (int i = 0; i < rentalAll.size(); i++) {
                        if (rentalAll.get(i).getIdRental() == idOrder) {
                            System.out.printf(rentalAll.get(i).rentalView()).println();
                        }
                    }
                    for (int i = 0; i < rentals.size(); i++) {
                        if (rentals.get(i).getIdRental() == idOrder) {
                            System.out.printf(rentals.get(i).rentalView()).println();
                        }
                    }
                    System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
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
        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                System.out.printf(rentals.get(i).rentalView()).println();
            }
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");

    }

    public void showHistoryOder() throws IOException, ParseException {
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
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                    System.out.printf(rentals.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
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
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void payOder() throws IOException, ParseException {
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
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentals.size(); i++) {
                if (rentals.get(i).getIdCustomer() == users.get(0).getId()) {
                    totalMoney += rentals.get(i).getTotalPrice();
                    rentals.get(i).setStatus(EStatus.PAID);
                    System.out.printf(rentals.get(i).rentalView()).println();
                    rentalAll.add(rentals.get(i));
                }
            }
            System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
            System.out.printf("            ║                                          TỔNG TIỀN CẦN THANH TOÁN                                   ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalMoney)).println();
            System.out.println("            ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
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

    public void showRevenueByDay() throws IOException, ParseException {
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
                System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                for (int i = 0; i < rentalAll.size(); i++) {
                    if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(date) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByDay += rentalAll.get(i).getTotalPrice();
                        System.out.printf(rentalAll.get(i).rentalView()).println();
                    }
                }
                System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
                System.out.printf("            ║                                          TỔNG DOANH THU THEO NGÀY                                   ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenueByDay)).println();
                System.out.println("            ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
            }
        }
    }

    public void showRevenueByMonth() throws IOException, ParseException {
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
                System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
                System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
                System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
                for (int i = 0; i < rentalAll.size(); i++) {
                    if (FormatDateModel.convertDateToString(rentalAll.get(i).getEndDate()).contains(month) && rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByMonth += rentalAll.get(i).getTotalPrice();
                        System.out.printf(rentalAll.get(i).rentalView()).println();
                    }
                }
                System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
                System.out.printf("            ║                                          TỔNG DOANH THU THEO THÁNG                                  ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenueByMonth)).println();
                System.out.println("            ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
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
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╠══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╬═════════════╬═════════════════════════╩══════════╣");
            System.out.printf("            ║                                                TỔNG DOANH THU                                       ║%-13s║                                    ║", CurrencyFormat.convertPriceToString(totalRevenue)).println();
            System.out.println("            ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╩═════════════╩════════════════════════════════════╝");
        }
    }

    public void showOderAll() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                System.out.printf(rentalAll.get(i).rentalView()).println();
            }
            for (int i = 0; i < rentals.size(); i++) {
                System.out.printf(rentals.get(i).rentalView()).println();
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void showOderUnPaid() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        List<Rental> rentals = rentalService.getAllRental();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
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
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }

    public void showOderPaid() throws IOException {
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        if (rentalAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
            System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
            System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
            for (int i = 0; i < rentalAll.size(); i++) {
                if (rentalAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(rentalAll.get(i).rentalView()).println();
                }
            }
            System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        }
    }


    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}
