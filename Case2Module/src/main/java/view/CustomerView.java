package view;

import model.User;
import service.FileService;
import service.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private  final String FILE_PATH_RENTAL = "./src/main/data/rental.csv";
    private final String FILE_PATH_USERUSE = "./src/main/data/userUse.csv";
    private UserService userService;
    private OrderView orderView;
    private ModelView modelView;
    private Scanner scanner;
    private FileService fileService;
    private LoginView loginView;
    RentalView rentalView = new RentalView();

    public CustomerView() {
        orderView = new OrderView();
        modelView = new ModelView();
        fileService = new FileService();
        scanner = new Scanner(System.in);
        loginView = new LoginView();

    }

    public  void menuCustomerView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Giao diện Customer                             ║");
        System.out.println("                               ║                   [1]  Xem danh sách người mẫu                                    ║");
        System.out.println("                               ║                   [2]  Tìm kiếm người mẫu                                         ║");
        System.out.println("                               ║                   [3]  Book người mẫu                                             ║");
        System.out.println("                               ║                   [4]  Chỉnh sửa danh sách người mẫu đã order                     ║");
        System.out.println("                               ║                   [5]  Xem lịch trình của người mẫu                               ║");
        System.out.println("                               ║                   [6]  Xem lịch sử Book                                           ║");
        System.out.println("                               ║                   [7]  Thanh toán                                                 ║");
        System.out.println("                               ║                   [8]  Đơn hàng đã thanh toán                                     ║");
        System.out.println("                               ║                   [9]  Quản lý tài khoản                                          ║");
        System.out.println("                               ║                   [10] Đăng xuất                                                  ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherCustomer() throws IOException, ParseException, InterruptedException {
        int select = 0;
        boolean checkAction = false;
        do {
            menuCustomerView();
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
                    modelView.showModelListStepModel();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    modelView.searchModel();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    rentalView.orderModel();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    orderView.deleteModelOutOderByIdOder();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    modelView.searchModelByIdOrder();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    orderView.showHistoryOder();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    orderView.payOder();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    orderView.showHistoryOderPaid();
                    checkAction = checkActionContinue();
                    break;
                case 9:
                    launcherAccount();
                    checkAction = checkActionContinue();
                    break;
                case 10:
                    Menu menu = new Menu();
                    menu.login();
                    fileService.clearData(FILE_PATH_USERUSE);
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            launcherCustomer();
        }
    }

    public void menuAccountManager() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Giao diện quản lý tài khoản                         ║");
        System.out.println("                               ║                         [1] Hiển thị thông tin tài khoản                          ║");
        System.out.println("                               ║                         [2] Thay đổi họ và tên của bạn                            ║");
        System.out.println("                               ║                         [3] Thay đổi Tên đăng nhập                                ║");
        System.out.println("                               ║                         [4] Thay đổi mật khẩu đăng nhập                           ║");
        System.out.println("                               ║                         [5] Thay đổi số điện thoại                                ║");
        System.out.println("                               ║                         [6] Thay đổi ngày sinh                                    ║");
        System.out.println("                               ║                         [7] Thay đổi email                                        ║");
        System.out.println("                               ║                         [8] Thay đổi địa chỉ                                      ║");
        System.out.println("                               ║                         [9] Quay lại                                              ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void launcherAccount() throws IOException, ParseException, InterruptedException {
        CustomerView customerView = new CustomerView();
        int idUser = UserService.userLoginning.getId();
        boolean checkAction = false;
        int select;
        do {
            menuAccountManager();
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
                    loginView.showInfoAccount(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    loginView.editFullName(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    loginView.editUsername(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    loginView.editPassWord(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    loginView.editPhoneNumber(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    loginView.editBirthday(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    loginView.editEmail(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    loginView.editAddress(idUser);
                    checkAction = checkActionContinue();
                    break;
                case 9:
                    customerView.launcherCustomer();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            launcherCustomer();
        }
    }


    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để quay về giao diện trước đó, nhập \"N\" để quay về giao diện Customer!");
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
}
