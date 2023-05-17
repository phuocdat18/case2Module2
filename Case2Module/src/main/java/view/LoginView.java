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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void loginAdmin() throws IOException, ParseException, InterruptedException {
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
                List<User> userList = new ArrayList<>();
                User user = userService.loginAdmin(username, password);
                userList.add(user);
                fileService.writeData(FILE_PATH_USERUSE, userList);
                AdminView adminView = new AdminView();
                adminView.launcherAdmin();
            }
        } while (count != 3);
        if (count == 3) {
            System.out.println("Bạn đã nhập sai quá 3 lần! Vui lòng chọn lại chức năng!");
            menu.login();
        }
    }

    public void loginCustomer() throws IOException, ParseException, InterruptedException {
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
                customerView.launcherCustomer();
            }
        } while (count != 3);
        if (count == 3) {
            System.out.println("Bạn đã nhập sai quá 3 lần! Vui lòng chọn lại chức năng!");
            menu.login();
        }
    }

    public void signUp() throws IOException, ParseException, InterruptedException {
        List<User> userList = userService.getAllUser();
        userList.sort(new SortUserById());
        User user = new User();
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
            System.out.println("Nhập giới tính của bạn: male/female");
            gender = scanner.nextLine();
            switch (gender) {
                case "male":
                    checkGender = true;
                    break;
                case "female":
                    checkGender = true;
                    break;
                case "0":
                    checkGender = true;
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai, xin mời nhập lại (male/female):");
                    break;
            }
        } while (!checkGender);

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
            if (!checkBirthDay) {
                System.out.println("Nhập lỗi, vui lòng nhập lại!");
            }
        } while (!checkBirthDay);
        inputEmail(user);
        noChange();
        System.out.println("Nhập địa chỉ của bạn:");
        String inputAddress = scanner.nextLine();
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


    public void inputEmail(User user) throws IOException, ParseException, InterruptedException {
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
            } else {
                checkEmail = true;
            }
        } while (!checkEmail);
        user.setEmail(email);
    }

    public void inputPhoneNumber(User user) throws IOException, ParseException, InterruptedException {
        String phoneNumber = null;
        boolean checkValid = false;
        boolean checkPhoneNumber = false;
        do {
            do {
                noChange();
                System.out.println("Nhập số điện thoại của bạn:");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.equals("0")) {
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
            } else {
                checkPhoneNumber = true;
            }
        } while (!checkPhoneNumber);
        user.setPhoneNumber(phoneNumber);
    }

    public void inputFullName(User user) throws IOException, ParseException, InterruptedException {
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
                } else {
                    checkFullName = true;
                }
            } while (!checkValid);
        } while (!checkFullName);
        user.setFullName(fullName);
    }

    public void inputUserName(User user) throws IOException, ParseException, InterruptedException {
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
            } else {
                checkUserName = true;
            }
        } while (!checkUserName);
        user.setUsername(username);
    }


    public void editFullName(int idUser) throws IOException, ParseException, InterruptedException {
        List<User> users = userService.getAllUserUse();
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
        String fullName = null;
        boolean checkValid = false;
        boolean checkFullName = false;
        do {
            noChange();
            System.out.println("Nhập họ và tên mới của bạn:");
            fullName = scanner.nextLine();
            if (fullName.equals("0")) {
                checkFullName = true;
                if (UserService.userLoginning.getId() == 1) {
                    adminView.launcherUser();
                } else {
                    customerView.launcherAccount();
                }
            }
            checkValid = ValidateUtils.isFullName(fullName);
            if (!checkValid) {
                System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
            } else {
                checkFullName = true;
            }
        } while (!checkFullName);
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setFullName(fullName);
            }
        }
//        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay họ và tên thành công ✔\n");
    }


    public void editUsername(int idUser) throws IOException, ParseException, InterruptedException {
        Menu menu = new Menu();
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
        String username = null;
        String usernameNew = null;
        String usernameNew1 = null;
        boolean checkUsername = false;
        do {
            noChange();
            System.out.println("Nhập Tên đăng nhập hiện tại của bạn:");
            username = scanner.nextLine();
            if (username.equals("0")) {
                checkUsername = true;
                if (UserService.userLoginning.getId() == 1) {
                    adminView.launcherUser();
                } else {
                    customerView.launcherAccount();
                }
            }
            checkUsername = userService.checkUserName(username);
            if (!checkUsername) {
                System.out.println("Tên đăng nhập không đúng, vui lòng nhập lại!");
            } else if (checkUsername) {
                System.out.println("Nhập Tên đăng nhập mới của bạn:");
                usernameNew = scanner.nextLine();
                System.out.println("Nhập lại Tên đăng nhập mới của bạn:");
                do {
                    usernameNew1 = scanner.nextLine();
                    if (!usernameNew.equals(usernameNew1)) {
                        System.out.println("Tên đăng nhập vừa nhập khác ở trên! Nhập lại Tên đăng nhập mới của bạn:");
                    }
                } while (!usernameNew.equals(usernameNew1));
            }
        } while (!checkUsername);
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setUsername(usernameNew);
            }
        }
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi password thành công ✔\n");

        if (UserService.userLoginning.getId() != 1) {
            Thread.sleep(1000);
            System.out.println("Mời bạn đăng nhập lại\n");
            Thread.sleep(1500);
            menu.login();
            fileService.clearData(FILE_PATH_USERUSE);
        }

    }


    public void editPassWord(int idUser) throws IOException, ParseException, InterruptedException {
        Menu menu = new Menu();
        List<User> users = userService.getAllUserUse();
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
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
                if (UserService.userLoginning.getId() == 1) {
                    adminView.launcherUser();
                } else {
                    customerView.launcherAccount();
                }
            }
            checkPassword = userService.checkPassword(password);
            if (!checkPassword) {
                System.out.println("Password không đúng, vui lòng nhập lại!");
            } else if (checkPassword) {
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
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setPassword(passwordNew);
            }
        }
        fileService.writeData(FILE_PATH_USERUSE, users);
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi password thành công ✔\n");

        if (UserService.userLoginning.getId() != 1) {
            Thread.sleep(1000);
            System.out.println("Mời bạn đăng nhập lại\n");
            Thread.sleep(1500);
            menu.login();
            fileService.clearData(FILE_PATH_USERUSE);
        }
    }

    public void editPhoneNumber(int idUser) throws IOException, ParseException, InterruptedException {
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
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
                    if (UserService.userLoginning.getId() == 1) {
                        adminView.launcherUser();
                    } else {
                        customerView.launcherAccount();
                    }
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
            } else {
                checkPhoneNumber = true;
            }
        } while (!checkPhoneNumber);
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setPhoneNumber(phoneNumber);
            }
        }
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi số điện thoại thành công ✔\n");
    }

    public void editBirthday(int idUser) throws IOException, ParseException, InterruptedException {
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
        Date birthday = null;
        boolean checkValid = false;
        boolean checkBirthday = false;

        do {
            noChange();
            System.out.println("Nhập ngày sinh mới của bạn:");
            String birthdayStr = scanner.nextLine();

            if (birthdayStr.equals("0")) {
                checkBirthday = true;
                if (UserService.userLoginning.getId() == 1) {
                    adminView.launcherUser();
                } else {
                    customerView.launcherAccount();
                }
            }

            checkValid = ValidateUtils.isBirthDay(birthdayStr);

            if (!checkValid) {
                System.out.println("Ngày sinh không hợp lệ. Vui lòng nhập lại!");
            } else {
                try {
                    birthday = new SimpleDateFormat("dd/MM/yyyy").parse(birthdayStr);
                    checkBirthday = true;
                } catch (ParseException e) {
                    System.out.println("Lỗi khi chuyển đổi ngày sinh. Vui lòng nhập lại!");
                }
            }
        } while (!checkBirthday);
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setBirthDay(birthday);
            }
        }
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi ngày sinh thành công ✔\n");
    }


    public void editEmail(int idUser) throws IOException, ParseException, InterruptedException {
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
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
                    if (UserService.userLoginning.getId() == 1) {
                        adminView.launcherUser();
                    } else {
                        customerView.launcherAccount();
                    }
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
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setEmail(email);
            }
        }
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi email thành công ✔\n");
    }

    public void editAddress(int idUser) throws IOException, ParseException, InterruptedException {
        List<User> user = userService.getAllUser();
        AdminView adminView = new AdminView();
        CustomerView customerView = new CustomerView();
        noChange();
        System.out.println("Nhập Email của bạn:");
        String inputAddress = scanner.nextLine();
        if (inputAddress.equals("0")) {
            if (UserService.userLoginning.getId() == 1) {
                adminView.launcherUser();
            } else {
                customerView.launcherAccount();
            }
        }
        String address = ValidateUtils.parseCommaToChar(inputAddress);
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                user.get(i).setEmail(address);
            }
        }
        fileService.writeData(FILE_PATH_USER, user);
        System.out.println("✔ Bạn đã thay đổi địa chỉ thành công ✔\n");
    }

    public void showInfoAccountAdmin(int idUser) throws IOException {
        List<User> user = userService.getAllUser();
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                System.out.println("            ╔═══════╦══════════════════════╦═════════════╦══════════════════════╦═════════════╦═════════════╦═════════════╦════════════════════╦═════════════════════════╗");
                System.out.printf("            ║%-7s║%-22s║%-13s║%-22s║%-13s║%-13s║%-13s║%-20s║%-25s║", "ID", "Tên đăng nhập", "Mật khẩu", "Họ tên", "Số điện thoại", "Giới tính", "Ngày sinh", "Email", "Địa chỉ").println();
                System.out.println("            ╠═══════╬══════════════════════╬═════════════╬══════════════════════╬═════════════╬═════════════╬═════════════╬════════════════════╬═════════════════════════╣");
                System.out.printf(user.get(i).userViewAdmin()).println();
                System.out.println("            ╚═══════╩══════════════════════╩═════════════╩══════════════════════╩═════════════╩═════════════╩═════════════╩════════════════════╩═════════════════════════╝");
            }
        }
    }

    public void showInfoAccount(int idUser) throws IOException {
        List<User> user = userService.getAllUser();
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getId() == idUser) {
                System.out.println("            ╔═══════╦══════════════════════╦══════════════════════╦═════════════╦═════════════╦═════════════╦════════════════════╦═════════════════════════╗");
                System.out.printf("            ║%-7s║%-22s║%-22s║%-13s║%-13s║%-13s║%-20s║%-25s║", "ID", "Tên đăng nhập", "Họ tên", "Số điện thoại", "Giới tính", "Ngày sinh", "Email", "Địa chỉ").println();
                System.out.println("            ╠═══════╬══════════════════════╬══════════════════════╬═════════════╬═════════════╬═════════════╬════════════════════╬═════════════════════════╣");
                System.out.printf(user.get(i).userView()).println();
                System.out.println("            ╚═══════╩══════════════════════╩══════════════════════╩═════════════╩═════════════╩═════════════╩════════════════════╩═════════════════════════╝");
            }
        }
    }


    public void searchUserById() throws IOException, ParseException, InterruptedException {
        CustomerView customerView = new CustomerView();
        AdminView adminView = new AdminView();
        List<User> users = userService.getAllUser();
        boolean checkAction = false;
        noChange();
        int idUser = 0;
        do {
            System.out.println("Nhập ID của tài khoản: ");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                checkAction = true;
                adminView.launcherAdmin();
            }
            try {
                idUser = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Id không hợp lệ vui lòng nhập lại");
                idUser = 0;
                continue;
            }
            if (idUser < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                idUser = 0;
                continue;
            }
            switch (userService.checkIdUser(idUser)) {
                case 1:
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getId() == idUser) {
                            adminView.launcherAccount(idUser);
                        }
                    }
                    checkAction = true;
                    break;
                case -1:
                    System.out.println("Không tìm thấy ID, mời bạn nhập lại:");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
    }


    public void showInfoCustomer() throws IOException {
        List<User> users = userService.getCustomerList();
        AdminView adminView = new AdminView();
        if (users.isEmpty()) {
            System.out.println("Hiện tại chưa có tài khoản nào!");
        } else {
            System.out.println("            ╔═══════╦══════════════════════╦═════════════╦══════════════════════╦═════════════╦═════════════╦═════════════╦════════════════════╦═════════════════════════╗");
            System.out.printf("            ║%-7s║%-22s║%-13s║%-22s║%-13s║%-13s║%-13s║%-20s║%-25s║", "ID", "Tên đăng nhập", "Mật khẩu", "Họ tên", "Số điện thoại", "Giới tính", "Ngày sinh", "Email", "Địa chỉ").println();
            System.out.println("            ╠═══════╬══════════════════════╬═════════════╬══════════════════════╬═════════════╬═════════════╬═════════════╬════════════════════╬═════════════════════════╣");
            for (User user : users) {
                System.out.printf(user.userViewAdmin()).println();
            }
            System.out.println("            ╚═══════╩══════════════════════╩═════════════╩══════════════════════╩═════════════╩═════════════╩═════════════╩════════════════════╩═════════════════════════╝");
        }
    }

    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: 0 ⦿ ");
    }

}
