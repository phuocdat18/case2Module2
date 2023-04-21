package view;

import model.EStatus;
import model.Food;
import model.Model;
import model.Order;
import model.User;
import service.FileService;
import service.FoodService;
import service.OrderService;
import service.UserService;
import utils.CurrencyFormat;
import utils.DateFormat;
import utils.SortOderById;
import utils.ValidateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private static final String FILE_PATH_FOOD = "./src/main/data/food.csv";
    private final String FILE_PATH_ORDER = "./src/main/data/order.csv";
    private final String FILE_PATH_ODERALL = "./src/main/data/orderAll.csv";
    private FoodService foodService;
    private UserService userService;
    private OrderService orderService;
    private FileService fileService;
    private Scanner scanner;

    public OrderView() {
        foodService = new FoodService();
        userService = new UserService();
        orderService = new OrderService();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void addFoodInOderByIdCustomer() throws IOException {
//        int idOder, String nameCustomer, String nameFood, int quantityFood, double priceFood, double totalMoney, Date createDateOder
        CustomerView customerView = new CustomerView();
        FoodView foodView = new FoodView();
        foodView.showFoodList();
        List<Model> foods = foodService.getAllFood();
        List<User> users = userService.getAllUserUse();
        List<Order> orderAll = orderService.getAllOrderAll();
        List<Order> orders = orderService.getAllOrder();
        orderAll.sort(new SortOderById());
        Order order = new Order();
        Order orderNew = new Order();
        int idFood = 0;
        String nameFood = null;
        boolean checkId = false;
        do {
            noChange();
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn oder:");
            String inputID = scanner.nextLine();
            if (inputID.equals("0")) {
                checkId = true;
                customerView.launcher();
            }
            if (ValidateUtils.isId(idFood, inputID)) {
                idFood = Integer.parseInt(inputID);
            } else {
                continue;
            }
            int checkIdFood = foodService.checkIdFood(idFood);
            switch (checkIdFood) {
                case 1:
                    for (int i = 0; i < foods.size(); i++) {
                        if (foods.get(i).getIdFood() == idFood) {
                            nameFood = foods.get(i).getNameFood();
                        }
                    }
                    if (!orders.isEmpty()) {
                        int index = 0;
                        boolean checkNameFoodAvailable = false;
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getNameFood().equals(nameFood) && users.get(0).getId() == orders.get(i).getIdCustomer()) {
                                index = i;
                                checkNameFoodAvailable = true;
                            }
                        }
                        if (checkNameFoodAvailable) {
                            noChange();
                            System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                            int quantity = 0;
                            boolean checkValid = false;
                            boolean checkQuantity = false;
                            do {
                                noChange();
                                System.out.println("Nhập số lượng:");
                                String inputQuantity = scanner.nextLine();
                                if (inputQuantity.equals("0")) {
                                    checkId = true;
                                    customerView.launcher();
                                }
                                checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                                if (!checkValid) {
                                    checkQuantity = false;
                                } else {
                                    quantity = Integer.parseInt(inputQuantity);
                                    for (int j = 0; j < foods.size(); j++) {
                                        if (foods.get(j).getNameFood().equals(nameFood)) {
                                            if (quantity <= foods.get(j).getQuantity()) {
                                                checkQuantity = true;
                                            } else {
                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + foods.get(j).getQuantity());
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } while (!checkQuantity);
                            int quantityNew = orders.get(index).getQuantityFood() + quantity;
                            double total = orders.get(index).getPriceFood() * quantityNew;
                            order.setIdOder(orders.get(index).getIdOrder());
                            order.setIdCustomer(orders.get(index).getIdCustomer());
                            order.setNameCustomer(orders.get(index).getNameCustomer());
                            order.setNameFood(nameFood);
                            order.setQuantityFood(quantityNew);
                            order.setPriceFood(orders.get(index).getPriceFood());
                            order.setTotalMoney(total);
                            order.setCreateDateOder(new Date());
                            order.setStatus(orders.get(index).getStatus());
                            orders.set(index, order);
                            for (int j = 0; j < foods.size(); j++) {
                                if (foods.get(j).getNameFood().equals(nameFood)) {
                                    foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                }
                            }
                            fileService.writeData(FILE_PATH_ORDER, orders);
                            fileService.writeData(FILE_PATH_FOOD, foods);
                            for (int j = 0; j < orderAll.size(); j++) {
                                if (orderAll.get(j).getIdCustomer() == users.get(0).getId() && orderAll.get(j).getNameFood().equals(nameFood) && orderAll.get(j).getStatus().equals(EStatus.UNPAID)) {
                                    orderAll.get(j).setQuantityFood(quantityNew);
                                    fileService.writeData(FILE_PATH_ODERALL, orderAll);
                                }
                            }
                        }
                        else {
                            int quantity = 0;
                            boolean checkValid = false;
                            boolean checkQuantity = false;
                            do {
                                noChange();
                                System.out.println("Nhập số lượng:");
                                String inputQuantity = scanner.nextLine();
                                if (inputQuantity.equals("0")) {
                                    checkId = true;
                                    customerView.launcher();
                                }
                                checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                                if (!checkValid) {
                                    checkQuantity = false;
                                } else {
                                    quantity = Integer.parseInt(inputQuantity);
                                    for (int j = 0; j < foods.size(); j++) {
                                        if (foods.get(j).getIdFood() == idFood) {
                                            if (quantity <= foods.get(j).getQuantity()) {
                                                checkQuantity = true;
                                            } else {
                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + foods.get(j).getQuantity());
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } while (!checkQuantity);
                            double price = 0;
                            for (int j = 0; j < foods.size(); j++) {
                                if (foods.get(j).getIdFood() == idFood) {
                                    price = foods.get(j).getPriceFood();
                                    foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                                }
                            }
                            double totalMoney = quantity * price;
                            order.setIdOder(orders.get(orders.size() - 1).getIdOrder() + 1);
                            order.setIdCustomer(users.get(0).getId());
                            order.setNameCustomer(users.get(0).getFullName());
                            order.setNameFood(nameFood);
                            order.setQuantityFood(quantity);
                            order.setPriceFood(price);
                            order.setTotalMoney(totalMoney);
                            order.setCreateDateOder(new Date());
                            order.setStatus(EStatus.UNPAID);
                            orders.add(order);
                            fileService.writeData(FILE_PATH_ORDER, orders);
                            fileService.writeData(FILE_PATH_FOOD, foods);
                        }
                    } else if (orders.isEmpty()) {

                        int quantity = 0;
                        boolean checkValid = false;
                        boolean checkQuantity = false;
                        do {
                            noChange();
                            System.out.println("Nhập số lượng:");
                            String inputQuantity = scanner.nextLine();
                            if (inputQuantity.equals("exit")) {
                                checkId = true;
                                customerView.launcher();
                            }
                            checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                            if (!checkValid) {
                                checkQuantity = false;
                            } else {
                                quantity = Integer.parseInt(inputQuantity);
                                for (int j = 0; j < foods.size(); j++) {
                                    if (foods.get(j).getIdFood() == idFood) {
                                        if (quantity <= foods.get(j).getQuantity()) {
                                            checkQuantity = true;
                                        } else {
                                            System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + foods.get(j).getQuantity());
                                            checkQuantity = false;
                                        }
                                    }
                                }
                            }
                        } while (!checkQuantity);
                        double price = 0;
                        for (int j = 0; j < foods.size(); j++) {
                            if (foods.get(j).getIdFood() == idFood) {
                                price = foods.get(j).getPriceFood();
                                foods.get(j).setQuantity(foods.get(j).getQuantity() - quantity);
                            }
                        }
                        double totalMoney = quantity * price;
                        order.setIdOder(1);
                        order.setIdCustomer(users.get(0).getId());
                        order.setNameCustomer(users.get(0).getFullName());
                        order.setNameFood(nameFood);
                        order.setQuantityFood(quantity);
                        order.setPriceFood(price);
                        order.setTotalMoney(totalMoney);
                        order.setCreateDateOder(new Date());
                        order.setStatus(EStatus.UNPAID);
                        orders.add(order);
                        fileService.writeData(FILE_PATH_ORDER, orders);
                        fileService.writeData(FILE_PATH_FOOD, foods);
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);
        orderNew.setIdOder(orderAll.get(orderAll.size() - 1).getIdOrder() + 1);
        orderNew.setIdCustomer(order.getIdCustomer());
        orderNew.setNameCustomer(order.getNameCustomer());
        orderNew.setNameFood(order.getNameFood());
        orderNew.setQuantityFood(order.getQuantityFood());
        orderNew.setPriceFood(order.getPriceFood());
        orderNew.setTotalMoney(order.getTotalMoney());
        orderNew.setCreateDateOder(order.getCreateDateOder());
        orderNew.setStatus(order.getStatus());
        orderAll.add(orderNew);
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        showOderNow();
        System.out.println("✔ Bạn đã thêm món thành công ✔\n");
    }

    public void editQuantityFoodInOderByIdOder() throws IOException {

        CustomerView customerView = new CustomerView();
        List<Food> foods = foodService.getAllFood();
        List<Order> orderAll = orderService.getAllOrderAll();
        List<Order> orders = orderService.getAllOrder();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
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
                        addFoodInOderByIdCustomer();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcher();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            showOderNow();
            int idOrder = 0;
            String nameFood = null;
            boolean checkId = false;
            do {
                noChange();
                boolean checkAction = false;
                System.out.println("Nhập ID oder bạn muốn chỉnh sửa:");
                String inputID = scanner.nextLine();
                if (inputID.equals("0")) {
                    checkId = true;
                    customerView.launcher();
                }
                if (ValidateUtils.isId(idOrder, inputID)) {
                    idOrder = Integer.parseInt(inputID);
                } else {
                    continue;
                }
                int checkIdOrder = orderService.checkIdOrder(idOrder);
                switch (checkIdOrder) {
                    case 1:
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getIdOrder() == idOrder) {
                                nameFood = orders.get(i).getNameFood();
                            }
                        }
                        noChange();
                        int quantity = 0;
                        boolean checkValid = false;
                        boolean checkQuantity = false;
                        do {
                            System.out.println("Nhập số lượng bạn muốn sửa: Số lượng từ 1-1000");
                            String inputQuantity = scanner.nextLine();
                            if (inputQuantity.equals("0")) {
                                checkId = true;
                                customerView.launcher();
                            }
                            checkValid = ValidateUtils.isQuantity(quantity, inputQuantity);
                            if (checkValid) {
                                quantity = Integer.parseInt(inputQuantity);
                                for (int i = 0; i < orders.size(); i++) {
                                    for (int j = 0; j < foods.size(); j++) {
                                        if (foods.get(j).getNameFood().equals(nameFood) && orders.get(i).getIdOrder() == idOrder) {
                                            if (orders.get(i).getQuantityFood() <= quantity && quantity <= foods.get(j).getQuantity()) {
                                                orders.get(i).setQuantityFood(quantity);
                                                foods.get(i).setQuantity(foods.get(i).getQuantity() + orders.get(i).getQuantityFood() - quantity);
                                                checkQuantity = true;
                                            } else if (orders.get(i).getQuantityFood() > quantity) {
                                                orders.get(i).setQuantityFood(quantity);
                                                foods.get(i).setQuantity(foods.get(i).getQuantity() + orders.get(i).getQuantityFood() - quantity);
                                                checkQuantity = true;
                                            } else if (quantity > foods.get(j).getQuantity() + orders.get(i).getQuantityFood()) {
                                                System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại!");
                                                checkQuantity = false;
                                            }
                                        }
                                    }
                                }
                            } else {
                                checkQuantity = false;
                            }
                        } while (!checkQuantity);

                        for (int i = 0; i < orderAll.size(); i++) {
                            if (orderAll.get(i).getNameFood().equals(nameFood) && users.get(0).getId() == orderAll.get(i).getIdCustomer() && orderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                                orderAll.get(i).setQuantityFood(quantity);
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

            fileService.writeData(FILE_PATH_ORDER, orders);
            fileService.writeData(FILE_PATH_ODERALL, orderAll);
            fileService.writeData(FILE_PATH_FOOD, foods);
//            showOderNow();
            System.out.println("✔ Bạn đã cập nhật số lượng thành công ✔\n");
        }

    }

    public void deleteFoodOutOderByIdOder() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Food> foods = foodService.getAllFood();
        List<Order> orderAll = orderService.getAllOrderAll();
        List<Order> orders = orderService.getAllOrder();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
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
                        addFoodInOderByIdCustomer();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcher();
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
            String nameFood = null;
            boolean checkId = false;
            do {
                noChange();
                boolean checkAction = false;
                System.out.println("Nhập ID oder bạn muốn xóa:");
                String inputID = scanner.nextLine();
                if (inputID.equals("0")) {
                    checkId = true;
                    customerView.launcher();
                }
                if (ValidateUtils.isId(idOrder, inputID)) {
                    idOrder = Integer.parseInt(inputID);
                } else {
                    continue;
                }
                int checkIdOrder = orderService.checkIdOrder(idOrder);
                switch (checkIdOrder) {
                    case 1:
                        orderService.deleteFoodOutOrderById(idOrder);
                        for (int i = 0; i < orders.size(); i++) {
                            if (orders.get(i).getIdOrder() == idOrder) {
                                nameFood = orders.get(i).getNameFood();
                            }
                        }
                        for (int i = 0; i < orderAll.size(); i++) {
                            if (orderAll.get(i).getNameFood().equals(nameFood) && orderAll.get(i).getIdCustomer() == users.get(0).getId()) {
                                idOderAll = orderAll.get(i).getIdOrder();
                            }
                        }
                        orderService.deleteFoodOutOrderAllById(idOderAll);
                        checkId = true;
                        break;
                    case -1:
                        System.out.println("ID không có trong danh sách, mời bạn nhập lại");
                        checkId = false;
                        break;
                }
            } while (!checkId);
//            showOderNow();
            System.out.println("✔ Bạn đã xóa món thành công ✔\n");
        }
    }

    public void findOderById() throws IOException {
        AdminView adminView = new AdminView();
        List<Order> orderAll = orderService.getAllOrderAll();
        noChange();
        int idOrder = 0;
        boolean checkIdOrder = false;
        do {
            System.out.println("Nhập ID order, thức ăn bạn muốn tìm");
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
            int select = orderService.checkIdOrderAll(idOrder);
            switch (select) {
                case 1:
                    System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
                    System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
                    System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
                    for (int i = 0; i < orderAll.size(); i++) {
                        if (orderAll.get(i).getIdOrder() == idOrder) {
                            System.out.printf(orderAll.get(i).oderView()).println();
                        }
                    }
                    System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
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
        List<Order> orders = orderService.getAllOrder();
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
        System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
        System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
                System.out.printf(orders.get(i).oderView()).println();
            }
        }
        System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");

    }

    public void showHistoryOder() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Order> orders = orderService.getAllOrder();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Chưa có đơn hàng, mời bạn thêm món để thực hiện chức năng này!");
            boolean checkEdit = false;
            do {
                System.out.println("Nhập \"Y\" để thêm món hoăc \"0\" để quay lại! ");
                String input = scanner.nextLine();
                switch (input.toUpperCase()) {
                    case "Y":
                        checkEdit = true;
                        addFoodInOderByIdCustomer();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcher();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
                    System.out.printf(orders.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void showHistoryOderPaid() throws IOException {
        List<Order> orderAll = orderService.getAllOrderAll();
        List<User> users = userService.getAllUserUse();
        int count = 0;
        for (int i = 0; i < orderAll.size(); i++) {
            if (orderAll.get(i).getIdCustomer() == users.get(0).getId() && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                count += 1;
            }
        }
        if (count == 0) {
            System.out.println("Bạn chưa có đơn hàng nào đã thanh toán. Không thể xem lịch sử mua hàng!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getIdCustomer() == users.get(0).getId() && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void payOder() throws IOException {
        FileService fileService = new FileService();
        CustomerView customerView = new CustomerView();
        List<Order> orders = orderService.getAllOrder();
        List<Order> ordersNew = new ArrayList<>();
        List<Order> orderAll = orderService.getAllOrderAll();
        List<User> users = userService.getAllUserUse();
        double totalMoney = 0;
        int count = 0;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
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
                        addFoodInOderByIdCustomer();
                        break;
                    case "0":
                        checkEdit = true;
                        customerView.launcher();
                        break;
                    default:
                        checkEdit = false;
                        break;
                }
            } while (!checkEdit);
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getIdCustomer() == users.get(0).getId()) {
                    totalMoney += orders.get(i).getTotalMoney();
                    orders.get(i).setStatus(EStatus.PAID);
                    System.out.printf(orders.get(i).oderView()).println();

                }
            }
            System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
            System.out.printf("            ║                                                TỔNG TIỀN CẦN THANH TOÁN                                                ║ %-13s ║                                                ║", CurrencyFormat.covertPriceToString(totalMoney)).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getIdCustomer() == users.get(0).getId()) {
                    orderAll.get(i).setStatus(EStatus.PAID);
                }
            }

            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getIdCustomer() != users.get(0).getId()) {
                    ordersNew.add(orders.get(i));
                }
            }
        }
        fileService.writeData(FILE_PATH_ORDER, ordersNew);
        fileService.writeData(FILE_PATH_ODERALL, orderAll);
        System.out.println("✔ Bạn đã thanh toán thành công ✔\n");
    }

    public void showRevenueByDay() throws IOException {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            String date = null;
            boolean checkDate = false;
            do {
                System.out.println("Nhập ngày tháng năm bạn muốn xem doanh thu: dd-MM-yyyy");
                date = scanner.nextLine();
                if (date.equals("0")) {
                    checkDate = true;
                    adminView.launcherRevenue();
                }
                checkDate = ValidateUtils.isDay(date);
                if (!checkDate) {
                    System.out.println("Ngày tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: dd-MM-yyyy");
                }
            } while (!checkDate);
            int count = 0;
            for (int i = 0; i < orderAll.size(); i++) {
                if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(date) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Ngày bạn muốn xem không có doanh thu!");
            } else {
                double totalRevenueByDay = 0;
                System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
                System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
                System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
                for (int i = 0; i < orderAll.size(); i++) {
                    if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(date) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByDay += orderAll.get(i).getTotalMoney();
                        System.out.printf(orderAll.get(i).oderView()).println();
                    }
                }
                System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
                System.out.printf("            ║                                                  TỔNG DOANH THU THEO NGÀY                                              ║ %-13s ║                                                ║", CurrencyFormat.covertPriceToString(totalRevenueByDay)).println();
                System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
            }
        }
    }

    public void showRevenueByMonth() throws IOException {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            String month = null;
            boolean checkMonth = false;
            do {
                System.out.println("Nhập tháng năm bạn muốn xem doanh thu: MM-yyyy");
                month = scanner.nextLine();
                if (month.equals("0")) {
                    checkMonth = true;
                    adminView.launcherRevenue();
                }
                checkMonth = ValidateUtils.isMonth(month);
                if (!checkMonth) {
                    System.out.println("Tháng năm bạn nhập không hợp lệ, vui lòng nhập lại: MM-yyyy");
                }
            } while (!checkMonth);
            int count = 0;
            for (int i = 0; i < orderAll.size(); i++) {
                if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(month) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Tháng bạn muốn xem không có doanh thu!");
            } else {
                double totalRevenueByMonth = 0;
                System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
                System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
                System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
                for (int i = 0; i < orderAll.size(); i++) {
                    if (DateFormat.convertDateToString2(orderAll.get(i).getCreateDateOder()).contains(month) && orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                        totalRevenueByMonth += orderAll.get(i).getTotalMoney();
                        System.out.printf(orderAll.get(i).oderView()).println();
                    }
                }
                System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
                System.out.printf("            ║                                                  TỔNG DOANH THU THEO NGÀY                                              ║ %-13s ║                                                ║", CurrencyFormat.covertPriceToString(totalRevenueByMonth)).println();
                System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
            }
        }
    }

    public void showTotalRevenue() throws IOException {
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Doanh thu hiện tại không có!");
        } else {
            double totalRevenue = 0;
            for (int i = 0; i < orderAll.size(); i++) {
                totalRevenue += orderAll.get(i).getTotalMoney();
            }
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╠═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╬═══════════════╬═══════════════════════════════╩════════════════╣");
            System.out.printf("            ║                                                            TỔNG DOANH THU                                              ║ %-13s ║                                                ║", CurrencyFormat.covertPriceToString(totalRevenue)).println();
            System.out.println("            ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╩═══════════════╩════════════════════════════════════════════════╝");
        }
    }

    public void showOderAll() throws IOException {
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                System.out.printf(orderAll.get(i).oderView()).println();
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void showOderUnPaid() throws IOException {
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getStatus().equals(EStatus.UNPAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }

    public void showOderPaid() throws IOException {
        List<Order> orderAll = orderService.getAllOrderAll();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại chưa có đơn hàng nào!");
        } else {
            System.out.println("            ╔═══════╦═══════════════╦══════════════════════════════╦═══════════════════════════════╦════════════════╦════════════════╦═══════════════╦═══════════════════════════════╦════════════════╗");
            System.out.printf("            ║%7s║ %-14s║ %-29s║ %-30s║ %-15s║ %-15s║ %-14s║ %-30s║ %-15s║", "ID ODER", "ID CUSTOMER", "NAME CUSTOMER", "NAME FOOD", "QUANTITY", "PRICE", "TOTAL MONEY", "CREATE DATE ODER", "STATUS").println();
            System.out.println("            ╠═══════╬═══════════════╬══════════════════════════════╬═══════════════════════════════╬════════════════╬════════════════╬═══════════════╬═══════════════════════════════╬════════════════╣");
            for (int i = 0; i < orderAll.size(); i++) {
                if (orderAll.get(i).getStatus().equals(EStatus.PAID)) {
                    System.out.printf(orderAll.get(i).oderView()).println();
                }
            }
            System.out.println("            ╚═══════╩═══════════════╩══════════════════════════════╩═══════════════════════════════╩════════════════╩════════════════╩═══════════════╩═══════════════════════════════╩════════════════╝");
        }
    }


    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}
