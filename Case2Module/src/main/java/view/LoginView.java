package view;

import model.EGender;
import model.Model;
import model.Role;
import model.User;
import service.FileService;
import service.UserService;
import utils.FormatDateModel;
import utils.SortUserById;
import utils.ValidateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginView {
    private final String FILE_PATH_USER = "Case2Module/src/main/data/user.csv";
    private final String FILE_PATH_USERUSE = "Case2Module/src/main/data/userUse.csv";
    private Menu menu;
    private UserService userService;
    private FileService fileService;
    private Scanner scanner;

    public LoginView() {
        menu = new Menu();
        userService = new UserService();
        fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void menuLoginAdmin() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                                  HỆ THỐNG QUẢN LÝ                                 ║");
        System.out.println("                               ║                          BẢNG ĐIỀU KHIỂN ĐĂNG NHẬP ADMIN                          ║");
        System.out.println("                               ║                          Đăng nhập bằng tài khoản admin                           ║");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void menuLoginCustomer() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ║                                  HỆ THỐNG QUẢN LÝ                                 ║");
        System.out.println("                               ║                          BẢNG ĐIỀU KHIỂN ĐĂNG NHẬP CUSTOMER                       ║");
        System.out.println("                               ║                          Đăng nhập bằng tài khoản khách hàng                      ║");
        System.out.println("                               ║                                                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
    }

    public void loginAdmin() throws IOException, ParseException {
        int count = 0;
        menuLoginAdmin();
        do {
            noChange();
            System.out.println("Nhập tên đăng nhập:");
            String username = scanner.nextLine();
            if (username.equals("0")) {
                count = 3;
                menu.login();
            }
            System.out.println("Nhập mật khẩu:");
            String password = scanner.nextLine();
            if (password.equals("0")) {
                count = 3;
                menu.login();
            }
            boolean checkInfoLogin = userService.checkLoginAdmin(username, password);
            if (!checkInfoLogin) {
                System.out.println("Tài khoản hoặc mật khẩu không đúng. Vui lòng nhập lại!");
                count++;
            } else {
                AdminView adminView = new AdminView();
                adminView.launcher();
            }
        } while (count != 3);
        if (count == 3) {
            System.out.println("Bạn đã nhập sai quá 3 lần! Vui lòng chọn lại chức năng!");
            menu.login();
        }
    }

    public void loginCustomer() throws IOException, ParseException {
        int count = 0;
        menuLoginCustomer();
        do {
            noChange();
            System.out.println("Nhập tên đăng nhập:");
            String username = scanner.nextLine();
            if (username.equals("0")) {
                count = 3;
                menu.login();
            }
            System.out.println("Nhập mật khẩu:");
            String password = scanner.nextLine();
            if (password.equals("0")) {
                count = 3;
                menu.login();
            }
            boolean checkInfoLogin = userService.checkLoginCustomer(username, password);
            if (!checkInfoLogin) {
                System.out.println("Tài khoản hoặc mật khẩu không đúng. Vui lòng nhập lại!");
                count++;
            } else {
                List<User> userList = new ArrayList<>();
                User user = userService.loginCustomer(username, password);
                userList.add(user);
                fileService.writeData(FILE_PATH_USERUSE, userList);
                CustomerView customerView = new CustomerView();
                customerView.launcher();
            }
        } while (count != 3);
        if (count == 3) {
            System.out.println("Bạn đã nhập sai quá 3 lần! Vui lòng chọn lại chức năng!");
            menu.login();
        }
    }

    public void signUp() throws IOException, ParseException {
        List<User> userList = userService.getAllUser();
        userList.sort(new SortUserById());
        User user = new User();
//        int id, String username, String password, String fullName, String phoneNumber, EGender gender,
//        long cccd, Date birthDay, String email, String address, ERole eRole
        System.out.println("Nhập các thông tin của bạn!");
        inputUserName(user);
        noChange();
        System.out.println("Nhập password của bạn:");
        String password = scanner.nextLine();
        if (password.equals("0")) {
            menu.login();
        }
        inputFullName(user);
        inputPhoneNumber(user);
        boolean checkGender = false;
        String gender = null;
        do {
            noChange();
            System.out.println("Nhập giới tính của bạn: male/female/other");
            gender = scanner.nextLine();
            switch (gender) {
                case "male":
                    checkGender = true;
                    break;
                case "female":
                    checkGender = true;
                    break;
                case "other":
                    checkGender = true;
                    break;
                case "0":
                    checkGender = true;
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai, xin mời nhập lại (male/female/other):");
                    break;
            }
        } while (!checkGender);

//        inputCCCD(user);
        boolean checkBirthDay = false;
        String date = null;
        do {
            noChange();
            System.out.println("Nhập ngày tháng năm sinh: dd/MM/yyyy");
            date = scanner.nextLine();
            if (date.equals("0")) {
                menu.login();
            }
            checkBirthDay = ValidateUtils.isBirthDay(date);
            if(!checkBirthDay) {
                System.out.println("Nhập lỗi, vui lòng nhập lại!");
            }
        }while (!checkBirthDay);
        inputEmail(user);
        noChange();
        System.out.println("Nhập địa chỉ của bạn:");
        String inputAddress = scanner.nextLine() ;
        if (inputAddress.equals("0")) {
            menu.login();
        }
        String address = ValidateUtils.parseCommaToChar(inputAddress);
        user.setId(userList.get(userList.size() - 1).getId() + 1);
        user.setPassword(password);
        user.setGender(EGender.getEGenderByName(gender));
        user.setBirthDay(FormatDateModel.parseDate(date));
        user.setAddress(address);
        user.setRole(Role.customer);
        userList.add(user);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã tạo tài khoản thành công ✔\n");
    }


    public void inputEmail(User user) throws IOException, ParseException {
        String email;
        boolean checkValid = false;
        boolean checkEmail = false;
        do {
            do {
                noChange();
                System.out.println("Nhập Email của bạn:");
                email = scanner.nextLine();
                if (email.equals("0")) {
                    checkEmail = true;
                    menu.login();
                }
                checkValid = ValidateUtils.isEmail(email);
                if (!checkValid) {
                    System.out.println("Email không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            boolean checkEmailAvailable = userService.checkEmail(email);
            if (checkEmailAvailable) {
                System.out.println("Email đã tồn tại vui lòng nhập lại!");
                checkEmail = false;
            }else {
                checkEmail = true;
            }
        } while (!checkEmail);
        user.setEmail(email);
    }

//    public void inputCCCD(User user) throws IOException {
//        String cccd;
//        boolean checkValid = false;
//        boolean checkCCCD = false;
//        do {
//            do {
//                noChange();
//                System.out.println("Nhập số CCCD của bạn: Bắt đầu bằng 0 và có 12 số!");
//                cccd = scanner.nextLine();
//                if(cccd.equals("0")) {
//                    checkCCCD = true;
//                    menu.login();
//                }
//                checkValid = ValidateUtils.isCCCD(cccd);
//                if (!checkValid) {
//                    System.out.println("Số CCCD không hợp lệ vui lòng nhập lại!");
//                }
//            } while (!checkValid);
//            boolean checkCCCDAvailable = userService.checkCCCD(cccd);
//            if (checkCCCDAvailable) {
//                System.out.println("Số CCCD đã tồn tại vui lòng nhập lại!");
//                checkCCCD = false;
//            }else {
//                checkCCCD = true;
//            }
//        } while (!checkCCCD);
//        user.setCccd(cccd);
//    }

    public void inputPhoneNumber(User user) throws IOException, ParseException {
        String phoneNumber = null;
        boolean checkValid = false;
        boolean checkPhoneNumber = false;
        do {
            do {
                noChange();
                System.out.println("Nhập số điện thoại của bạn:");
                phoneNumber = scanner.nextLine();
                if(phoneNumber.equals("0")) {
                    checkPhoneNumber = true;
                    menu.login();
                }
                checkValid = ValidateUtils.isPhoneNumber(phoneNumber);
                if (checkValid == false) {
                    System.out.println("Số điện thoại không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            boolean checkPhoneNumberAvailable = userService.checkPhoneNumber(phoneNumber);
            if (checkPhoneNumberAvailable) {
                System.out.println("Số điện thoại đã tồn tại vui lòng nhập lại!");
                checkPhoneNumber = false;
            }else {
                checkPhoneNumber = true;
            }
        } while (!checkPhoneNumber);
        user.setPhoneNumber(phoneNumber);
    }
    public void inputFullName(User user) throws IOException, ParseException {
        Menu menu = new Menu();
        String fullName = null;
        boolean checkValid = false;
        boolean checkFullName = false;
        do {
            do {
                noChange();
                System.out.println("Nhập họ và tên:");
                fullName = scanner.nextLine();
                if (fullName.equals("0")) {
                    checkFullName = true;
                    menu.login();
                }
                checkValid = ValidateUtils.isFullName(fullName);
                if (!checkValid) {
                    System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                }else {
                    checkFullName = true;
                }
            } while (!checkValid);
        } while (!checkFullName);
        user.setFullName(fullName);
    }
    public void inputUserName(User user) throws IOException, ParseException {
        Menu menu = new Menu();
        String username = null;
        boolean checkValid = false;
        boolean checkUserName = false;
        do {
            do {
                noChange();
                System.out.println("Nhập username của bạn (5 đến 18 kí tự)");
                username = scanner.nextLine();
                if (username.equals("0")) {
                    checkUserName = true;
                    menu.login();
                }
                checkValid = ValidateUtils.isUserName(username);
                if (!checkValid) {
                    System.out.println("UserName không hợp lệ, vui lòng nhập lại!");
                }
            } while (!checkValid);
            boolean checkUserNameAvailable = userService.checkUserName(username);
            if (checkUserNameAvailable) {
                System.out.println("UserName đã tồn tại vui lòng nhập lại!");
                checkUserName = false;
            }else {
                checkUserName = true;
            }
        } while (!checkUserName);
        user.setUsername(username);
    }
    public void editFullName() throws IOException, ParseException {
        List<User> users = userService.getAllUserUse();
        List<User> userList = userService.getAllUser();
        String fullName = null;
        boolean checkValid = false;
        boolean checkFullName = false;
        do {
            do {
                noChange();
                System.out.println("Nhập họ và tên mới của bạn:");
                fullName = scanner.nextLine();
                if (fullName.equals("0")) {
                    checkFullName = true;
                    menu.login();
                }
                checkValid = ValidateUtils.isFullName(fullName);
                if (!checkValid) {
                    System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                }else {
                    checkFullName = true;
                }
            } while (!checkValid);
        } while (!checkFullName);
        users.get(0).setFullName(fullName);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(users.get(0).getUsername())) {
                userList.get(i).setFullName(fullName);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay họ và tên thành công ✔\n");
    }
    public void editPassWord() throws IOException {
        Menu menu = new Menu();
        List<User> users = userService.getAllUserUse();
        List<User> userList = userService.getAllUser();

        String password = null;
        String passwordNew = null;
        String passwordNew1 = null;
        boolean checkPassword = false;
        do {
            noChange();
            System.out.println("Nhập password hiện tại của bạn:");
            password = scanner.nextLine();
            if (password.equals("0")) {
                checkPassword = true;
                menu.menuLogin();
            }
            checkPassword = userService.checkPassword(password);
            if (checkPassword == false) {
                System.out.println("Password không đúng, vui lòng nhập lại!");
            } else if (checkPassword == true) {
                System.out.println("Nhập password mới của bạn:");
                passwordNew = scanner.nextLine();
                System.out.println("Nhập lại password mới của bạn:");
                do {
                    passwordNew1 = scanner.nextLine();
                    if (!passwordNew.equals(passwordNew1)) {
                        System.out.println("Password vừa nhập khác ở trên! Nhập lại password mới của bạn:");
                    }
                } while (!passwordNew.equals(passwordNew1));
            }
        } while (!checkPassword);
        users.get(0).setPassword(passwordNew);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(users.get(0).getUsername())) {
                userList.get(i).setPassword(passwordNew);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi password thành công ✔\n");
    }

    public void editPhoneNumber() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User> userList = userService.getAllUser();
        String phoneNumber = null;
        boolean checkValid = false;
        boolean checkPhoneNumber = false;
        do {
            do {
                noChange();
                System.out.println("Nhập số điện thoại mới của bạn:");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.equals("0")) {
                    checkPhoneNumber = true;
                    menu.menuLogin();
                }
                checkValid = ValidateUtils.isPhoneNumber(phoneNumber);
                if (!checkValid) {
                    System.out.println("Số điện thoại không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            boolean checkPhoneNumberAvailable = userService.checkPhoneNumber(phoneNumber);
            if (checkPhoneNumberAvailable) {
                System.out.println("Số điện thoại đã tồn tại vui lòng nhập lại!");
                checkPhoneNumber = false;
            }else {
                checkPhoneNumber = true;
            }
        } while (!checkPhoneNumber);
        users.get(0).setPhoneNumber(phoneNumber);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(users.get(0).getUsername())) {
                userList.get(i).setPhoneNumber(phoneNumber);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi số điện thoại thành công ✔\n");
    }

    public void editEmail() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User> userList = userService.getAllUser();
        String email;
        boolean checkValid = false;
        boolean checkEmail = true;
        do {
            do {
                noChange();
                System.out.println("Nhập Email của bạn:");
                email = scanner.nextLine();
                if (email.equals("0")) {
                    checkEmail = false;
                    menu.menuLogin();
                }
                checkValid = ValidateUtils.isEmail(email);
                if (checkValid == false) {
                    System.out.println("Email không hợp lệ vui lòng nhập lại!");
                }
            } while (!checkValid);
            checkEmail = userService.checkEmail(email);
            if (checkEmail) {
                System.out.println("Email đã tồn tại vui lòng nhập lại!");
            }
        } while (checkEmail);
        users.get(0).setEmail(email);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(users.get(0).getUsername())) {
                userList.get(i).setEmail(email);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi email thành công ✔\n");
    }

    public void editAddress() throws IOException {
        List<User> users = userService.getAllUserUse();
        List<User> userList = userService.getAllUser();
        noChange();
        System.out.println("Nhập Email của bạn:");
        String inputAddress = scanner.nextLine();
        if (inputAddress.equals("0")) {
            menu.menuLogin();
        }
        String address = ValidateUtils.parseCommaToChar(inputAddress);
        users.get(0).setAddress(address);
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(users.get(0).getUsername())) {
                userList.get(i).setEmail(address);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, userList);
        System.out.println("✔ Bạn đã thay đổi địa chỉ thành công ✔\n");
    }

    public void showInfoAccount() throws IOException {
        List<User> users = userService.getAllUserUse();
        System.out.println("            ╔═══════╦═══════════════╦════════════════════╦═══════════════╦═══════════════╦═══════════════╦══════════════════════════════╦══════════════════════════════╗");
        System.out.printf("            ║%-7s║%-15s║%-20s║%-15s║%-15s║%-15s║%-30s║%-30s║", "ID", "USERNAME", "FULLNAME", "PHONE NUMBER", "GENDER", "BIRTHDAY", "EMAIL", "ADDRESS").println();
        System.out.println("            ╠═══════╬═══════════════╬════════════════════╬═══════════════╬═══════════════╬═══════════════╬══════════════════════════════╬══════════════════════════════╣");
        System.out.printf(users.get(0).userView()).println();
        System.out.println("            ╚═══════╩═══════════════╩════════════════════╩═══════════════╩═══════════════╩═══════════════╩══════════════════════════════╩══════════════════════════════╝");
    }

    public void showInfoCustomer() throws IOException {
        List<User> users = userService.getCustomerList();
        if(users.isEmpty()) {
            System.out.println("Hiện tại chưa có khách hàng nào đăng kí!");
        }else {
            System.out.println("            ╔═══════╦═══════════════╦════════════════════╦═══════════════╦═══════════════╦═══════════════╦══════════════════════════════╦══════════════════════════════╗");
            System.out.printf("            ║%-7s║%-15s║%-20s║%-15s║%-15s║%-15s║%-30s║%-30s║", "ID", "USERNAME", "FULLNAME", "PHONE NUMBER", "GENDER", "BIRTHDAY", "EMAIL", "ADDRESS").println();
            System.out.println("            ╠═══════╬═══════════════╬════════════════════╬═══════════════╬═══════════════╬═══════════════╬══════════════════════════════╬══════════════════════════════╣");
            for (User user : users){
                System.out.printf(user.userView()).println();
            }
            System.out.println("            ╚═══════╩═══════════════╩════════════════════╩═══════════════╩═══════════════╩═══════════════╩══════════════════════════════╩══════════════════════════════╝");
        }
    }
    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: 0 ⦿ ");
    }

}
