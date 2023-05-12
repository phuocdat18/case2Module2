package view;

import model.EGender;
import model.Model;
import model.Rental;
import service.FileService;
import service.ModelService;
import service.RentalService;
import service.UserService;
import utils.*;


import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class ModelView {
    private final String FILE_PATH_MODEL = "Case2Module/src/main/data/model.csv";
    private final String FILE_PATH_MODEL_UPDATE = "Case2Module/src/main/data/modelUpdate.csv";
    private final FileService fileService;
    private final ModelService modelService;
    private final Scanner scanner;
    private final RentalService rentalService;

    public ModelView() {
        fileService = new FileService();
        modelService = new ModelService();
        scanner = new Scanner(System.in);
        rentalService = new RentalService();
    }


    public boolean checkActionContinueAdmin() {
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


    public void menuModelAdminView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                        1. Hiển thị danh sách Model                                ║");
        System.out.println("                               ║                        2. Hiển thị danh sách Model theo giới tính                 ║");
        System.out.println("                               ║                        3. Tìm kiếm Model                                          ║");
        System.out.println("                               ║                        4. Thêm Model vào danh sách                                ║");
        System.out.println("                               ║                        5. Chỉnh sửa Model                                         ║");
        System.out.println("                               ║                        5. Xóa Model trong danh sách                               ║");
        System.out.println("                               ║                        7. Quay lại Menu                                           ║");
        System.out.println("                               ║                        8. Đăng xuất                                               ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void launcher() throws IOException, ParseException {
        boolean checkAction = false;
        int select = 0;
        do {
            menuModelAdminView();
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
                    showModelListStepModelAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    showModelListByTypeAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    searchModel();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    addModel();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    editModelById();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    deleteModelById();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    AdminView adminView = new AdminView();
                    adminView.launcherAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    Menu menu = new Menu();
                    menu.login();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, mời nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
        if (checkAction) {
            AdminView adminView = new AdminView();
            adminView.launcherAdmin();
        }
    }


    public void menuEditModelView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Chọn thông tin bạn muốn sửa                         ║");
        System.out.println("                               ║                        1.  Chỉnh sửa tên người mẫu                                ║");
        System.out.println("                               ║                        2.  Chỉnh sửa giới tính người mẫu                          ║");
        System.out.println("                               ║                        3.  Chỉnh sửa chiều cao người mẫu                          ║");
        System.out.println("                               ║                        4.  Chỉnh sửa cân nặng người mẫu                           ║");
        System.out.println("                               ║                        5.  Chỉnh sửa số điện thoại nguời mẫu                      ║");
        System.out.println("                               ║                        6.  Chỉnh sửa địa chỉ người mẫu                            ║");
        System.out.println("                               ║                        7.  Chỉnh sửa giá người mẫu                                ║");
        System.out.println("                               ║                        8.  Chỉnh sửa mô tả người mẫu                              ║");
        System.out.println("                               ║                        9.  Quay lại                                               ║");
        System.out.println("                               ║                        10.  Quay lại Menu                                          ║");
        System.out.println("                               ║                        11. Đăng xuất                                              ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }


    public void editModelById() throws IOException, ParseException {
        showModelList();
        Model model = new Model();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();
        List<Model> modelsUpdate = modelService.getAllModelUpdate();
        List<Rental> rentalUpdate = rentalService.getAllRentalUpdate();
        ModelView modelView = new ModelView();

        int searchId = 0;
        boolean checkId = false;
        do {
            noChange();
            boolean checkAction = false;
            System.out.println("Nhập ID người mẫu bạn muốn sửa");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                checkId = true;
                launcher();
            }
            try {
                searchId = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID phải là một số, vui lòng nhập lại!");
                searchId = 0;
                continue;
            }
            if (searchId < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                searchId = 0;
                continue;
            }
            int check = modelService.checkIdModel(searchId);
            switch (check) {

                case 1:
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getIdModel() == searchId) {
                            do {
                                noChange();
                                menuEditModelView();
                                System.out.println("Chọn mục bạn muốn sửa:");
                                int select = 0;
                                try {
                                    select = Integer.parseInt(scanner.nextLine());
                                } catch (NumberFormatException numberFormatException) {
                                    System.out.println("Nhập lỗi, phải là 1 số, vui lòng nhập lại!");
                                    select = 0;
                                    continue;
                                }
                                switch (select) {
                                    case 1:
                                        String nameModel = null;
                                        boolean checkValidFullName = false;
                                        boolean checkFullName = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập tên người mẫu:");
                                            nameModel = scanner.nextLine();
                                            if (nameModel.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            checkValidFullName = ValidateUtils.isNameModel(nameModel);
                                            if (!checkValidFullName) {
                                                System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidFullName);
                                        models.get(i).setNameModel(nameModel);
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                modelsUpdate.get(j).setNameModel(nameModel);
                                            }
                                        }

                                        checkAction = true;
                                        break;
                                    case 2:
                                        noChange();
                                        System.out.println("Nhập loại bạn muốn sửa:");
                                        String type = scanner.nextLine();
                                        if (type.equals("0")) {
                                            checkId = true;
                                            launcher();
                                        }
                                        if (models.get(i).getType().equals(EGender.male)) {
                                            switch (type) {
                                                case "male":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"male\"! Mời bạn nhập \"female\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            models.get(i).setType(EGender.getEGenderByName(type));
                                            for (int j = 0; j < modelsUpdate.size(); j++) {
                                                if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                    modelsUpdate.get(j).setType(EGender.getEGenderByName(type));
                                                }
                                            }
                                        } else if (models.get(i).getType().equals(EGender.female)) {
                                            switch (type) {
                                                case "female":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"female\"! Mời bạn nhập \"male\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            models.get(i).setType(EGender.getEGenderByName(type));
                                            for (int j = 0; j < modelsUpdate.size(); j++) {
                                                if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                    modelsUpdate.get(j).setType(EGender.getEGenderByName(type));
                                                }
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 3:
                                        String height = null;
                                        boolean checkValidHeight = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập chiều cao của người mẫu:");
                                            height = scanner.nextLine();
                                            if (height.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            checkValidHeight = ValidateUtils.isValidHeight(height);
                                            if (!checkValidHeight) {
                                                System.out.println("Chiều cao bạn nhập không hợp lệ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidHeight);
                                        models.get(i).setHeight(Integer.parseInt(height));
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                modelsUpdate.get(j).setHeight(Integer.parseInt(height));
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 4:
                                        int weight = 0;
                                        do {
                                            noChange();
                                            System.out.println("Nhập cân nặng của người mẫu:");
                                            try {
                                                weight = scanner.nextInt();
                                                scanner.nextLine();
                                                if (weight == 0) {
                                                    checkId = true;
                                                    launcher();
                                                }
                                                models.get(i).setWeight(weight);
                                                for (int j = 0; j < modelsUpdate.size(); j++) {
                                                    if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                        modelsUpdate.get(j).setWeight(weight);
                                                    }
                                                }
                                                checkAction = true;
                                                break;
                                            } catch (InputMismatchException e) {
                                                scanner.nextLine();
                                                System.out.println("Giá trị bạn nhập không hợp lệ, vui lòng nhập lại!");
                                            }
                                        } while (true);
                                    case 5:
                                        String phoneNumber = null;
                                        boolean checkValidPhoneNumber = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập số điện thoại của người mẫu:");
                                            phoneNumber = scanner.nextLine();
                                            if (phoneNumber.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            checkValidPhoneNumber = ValidateUtils.isPhoneNumber(phoneNumber);
                                            if (!checkValidPhoneNumber) {
                                                System.out.println("Số điện thoại bạn nhập không hợp lệ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidPhoneNumber);

                                        models.get(i).setPhoneNumberModel(phoneNumber);
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                modelsUpdate.get(j).setPhoneNumberModel(phoneNumber);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 6:
                                    String address = null;
                                    noChange();
                                    System.out.println("Nhập mô tả của người mẫu:");
                                    address = scanner.nextLine();
                                    if (address.equals("0")) {
                                        checkId = true;
                                        launcher();
                                    }
                                    models.get(i).setAddressModel(address);
                                    for (int j = 0; j < modelsUpdate.size(); j++) {
                                        if (modelsUpdate.get(j).getIdModel() == searchId) {
                                            modelsUpdate.get(j).setAddressModel(address);
                                        }
                                    }
                                    checkAction = true;
                                    break;
                                    case 7:
                                        int price = 0;
                                        boolean checkValidPrice = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập giá bạn muốn sửa. Giá phải lớn hơn 0đ!");
                                            String input1 = scanner.nextLine();
                                            if (input1.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            try {
                                                price = Integer.parseInt(input1);
                                            } catch (NumberFormatException numberFormatException) {
                                                System.out.println("Nhập lỗi, giá phải là một số, vui lòng nhập lại!");
                                                price = 0;
                                                continue;
                                            }
                                            checkValidPrice = ValidateUtils.isValidPrice(price);
                                            if (checkValidPrice == false) {
                                                System.out.println("Giá phải lớn hơn 0đ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidPrice);
                                        models.get(i).setPriceModel(price);
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                modelsUpdate.get(j).setPriceModel(price);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 8:
                                        String description = null;
                                        noChange();
                                        System.out.println("Nhập mô tả của người mẫu:");
                                        description = scanner.nextLine();
                                        if (description.equals("0")) {
                                            checkId = true;
                                            launcher();
                                        }
                                        models.get(i).setDescription(description);
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if (modelsUpdate.get(j).getIdModel() == searchId) {
                                                modelsUpdate.get(j).setDescription(description);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 9:
                                        modelView.editModelById();
                                        checkAction = true;
                                        break;
                                    case 10:
                                        AdminView adminView = new AdminView();
                                        adminView.launcherAdmin();
                                        checkAction = true;
                                        break;
                                    case 11:
                                        Menu menu = new Menu();
                                        menu.login();
                                        checkAction = true;
                                        break;
                                    default:
                                        System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                                        checkAction = false;
                                        break;
                                }
                            } while (!checkAction);
                            models.set(i, models.get(i));
                        }
                    }
                    checkId = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, mời bạn nhập lại");
                    checkId = false;
                    break;
            }
        } while (!checkId);
        fileService.writeData(FILE_PATH_MODEL_UPDATE, modelsUpdate);
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
        System.out.println("✔ Bạn đã cập nhật sản phẩm thành công ✔\n");
    }


    public void sortByPriceIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByPriceIncrease());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByPriceIncreaseAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByPriceIncrease());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin(models);
    }

    public void findModelById() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();
        boolean checkAction = false;
        noChange();
        int id = 0;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                checkAction = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại! Số lượng từ 0-1000");
                id = 0;
                continue;
            }
            if (id < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                id = 0;
                continue;
            }
            switch (modelService.checkIdModel(id)) {
                case 1:
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getIdModel() == id) {
                            System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                            System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Model's name", "Quantity", "Price", "Type Of Model").println();
                            System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
                            System.out.printf(models.get(i).modelView()).println();
                            System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
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

    public void deleteModelById() throws IOException, ParseException {
        showModelList();
        List<Model> models = modelService.getAllModel();
        List<Model> modelsUpdate = modelService.getAllModelUpdate();

        int id = 0;
        boolean checkID = false;
        do {
            noChange();
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn xóa:");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                checkID = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID không hợp lệ vui lòng nhập lại!");
                id = 0;
                continue;
            }
            if (id < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                id = 0;
                continue;
            }
            int check = modelService.checkIdModel(id);
            switch (check) {
                case 1:
                    modelService.deleteModelById(id);
                    modelService.deleteModelUpdateById(id);
                    checkID = true;
                    break;
                case -1:
                    System.out.println("ID không tìm thấy, mời bạn nhập lại");
                    checkID = false;
                    break;
            }
        } while (!checkID);
//
//        fileService.writeData(FILE_PATH_MODEL, models);
//        fileService.writeData(FILE_PATH_MODEL_UPDATE,modelsUpdate);
        showModelList();
        System.out.println("✔ Bạn đã xóa món thành công ✔\n");
    }


    public void addModel() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();
        List<Model> modelsUpdate = modelService.getAllModelUpdate();
        Model model = new Model();

        String typeOfModel;
        boolean checkType = false;
        do {
            noChange();
            System.out.println("Nhập danh mục cần thêm! \"male\" or \"female\":");
            typeOfModel = scanner.nextLine();
            if(typeOfModel.equals("0")){
                checkType = true;
                launcher();
            }
            switch (typeOfModel) {
                case "male":
                    checkType = true;
                    break;
                case "female":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại! \"male\" or \"female\":");
                    break;
            }
        }while (!checkType);
        boolean checkNameModel = false;
        do {
            String nameModel = null;
            boolean checkValidNameModel = false;
            do {
                noChange();
                System.out.println("Nhập tên người mẫu:");
                nameModel = scanner.nextLine();
                if (nameModel.equals("0")) {
                    checkNameModel = true;
                    launcher();
                }
                checkValidNameModel = ValidateUtils.isNameModel(nameModel);
                if (!checkValidNameModel) {
                    System.out.println("Tên bạn nhập không hợp lệ, vui lòng nhập lại!");
                }
            } while (!checkValidNameModel);
            int checkName = modelService.checkNameModel(nameModel);
            switch (checkName) {
                case 1:
                    System.out.println("Sản phẩm đã có, mời bạn thêm số lượng");
                    int quantity = 0;
                    boolean checkValidQuantity = false;
                    boolean checkValid = false;
                    do {
//                        noChange();
//                        System.out.println("Nhập số lượng");
                        String input = "1";
                        if(input.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        checkValid = ValidateUtils.isQuantity(quantity,input);
                        if (!checkValid) {
                            checkValidQuantity = false;
                        } else {
                            quantity = Integer.parseInt(input);
                            for (int j = 0; j < models.size(); j++) {
                                if (models.get(j).getNameModel().equals(nameModel)) {
                                    if (quantity + models.get(j).getQuantityModel() <= 1000) {
                                        checkValidQuantity = true;
                                    } else {
                                        int inputQuantity = 1000 - models.get(j).getQuantityModel();
//                                        System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + inputQuantity);
                                        checkValidQuantity = false;
                                    }
                                }
                            }
                        }
                    } while (!checkValidQuantity);
                    int quantityNew = 0;
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getNameModel().equals(nameModel)) {
                            quantityNew = models.get(i).getQuantityModel() + quantity;
                            model.setIdModel(models.get(i).getIdModel());
                            model.setNameModel(models.get(i).getNameModel());
                            model.setQuantityModel(quantityNew);
                            model.setPriceModel(models.get(i).getPriceModel());
                            model.setType(models.get(i).getType());
                            models.set(i, model);
                        }
                    }
                    checkNameModel = true;
                    break;
                case -1:
                    int quantity1 = 0;
                    boolean checkValidQuantity2 = false;
                    do {
//                        noChange();
//                        System.out.println("Nhập số lượng");
                        String input = "1";
                        if(input.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        checkValidQuantity2 = ValidateUtils.isQuantity(quantity1,input);
                        if (checkValidQuantity2) {
                            quantity1 = Integer.parseInt(input);
                        }
                    } while (!checkValidQuantity2);

                    int price = 0;
                    boolean checkValidPrice = false;
                    do {
                        noChange();
                        System.out.println("Nhập giá");
                        String input = scanner.nextLine();
                        if(input.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        try {
                            price = Integer.parseInt(input);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Nhập lỗi, giá phải là một số, vui lòng nhập lại");
                            price = 0;
                            continue;
                        }
                        checkValidPrice = ValidateUtils.isValidPrice(price);     // false
                        if (checkValidPrice == false) {
                            System.out.println("Giá phải lớn hơn 0$, vui lòng nhập lại!");
                        }
                    } while (!checkValidPrice);

                    int age = 0;
                    boolean isValidAge = false;
                    do {
                        noChange();
                        System.out.println("Nhập tuổi:");
                        String input = scanner.nextLine();
                        try {
                            age = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Tuổi không hợp lệ. Vui lòng nhập lại.");
                            continue;
                        }
                        if (age <= 0) {
                            System.out.println("Tuổi phải lớn hơn 0. Vui lòng nhập lại.");
                        } else {
                            isValidAge = true;
                        }
                    } while (!isValidAge);


                    int height = 0;
                    boolean isValidHeight = false;
                    do {
                        noChange();
                        System.out.println("Nhập chiều cao (cm):");
                        String input = scanner.nextLine();
                        try {
                            height = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Chiều cao không hợp lệ. Vui lòng nhập lại.");
                            continue;
                        }
                        if (height <= 0) {
                            System.out.println("Chiều cao phải lớn hơn 0. Vui lòng nhập lại.");
                        } else {
                            isValidHeight = true;
                        }
                    } while (!isValidHeight);



                    int weight = 0;
                    boolean isValidWeight = false;
                    do {
                        noChange();
                        System.out.println("Nhập cân nặng:");
                        String input = scanner.nextLine();
                        try {
                            weight = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Cân nặng không hợp lệ. Vui lòng nhập lại.");
                            continue;
                        }
                        if (weight <= 0) {
                            System.out.println("Cân nặng phải lớn hơn 0. Vui lòng nhập lại.");
                        } else {
                            isValidWeight = true;
                        }
                    } while (!isValidWeight);

                    String address = "";
                    boolean checkValidAddress = false;
                    do {
                        noChange();
                        System.out.println("Nhập địa chỉ");
                        address = scanner.nextLine();
                        if(address.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        checkValidAddress = ValidateUtils.isAddress(address);
                        if (!checkValidAddress) {
                            System.out.println("Địa chỉ không hợp lệ, vui lòng nhập lại!");
                        }
                    } while (!checkValidAddress);

                    String phoneNumber = "";
                    boolean isValidPhoneNumber = false;
                    do {
                        noChange();
                        System.out.println("Nhập số điện thoại:");
                        String input = scanner.nextLine();
                        if (input.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        if (!ValidateUtils.isPhoneNumber(input)) {
                            System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại");
                            continue;
                        }
                        phoneNumber = input;
                        isValidPhoneNumber = true;
                    } while (!isValidPhoneNumber);

                    noChange();
                    System.out.println("Nhập mô tả");
                    String description = scanner.nextLine();



                    models.sort(new SortModelByIDIncrease());
                    int id = models.get(models.size() - 1).getIdModel() + 1;
                    model.setIdModel(id);
                    model.setNameModel(nameModel);
                    model.setQuantityModel(quantity1);
                    model.setAge(age);
                    model.setHeight(height);
                    model.setWeight(weight);
                    model.setPhoneNumberModel(phoneNumber);
                    model.setAddressModel(address);
                    model.setPriceModel(price);
                    model.setDescription(description);
                    model.setType(EGender.getEGenderByName(typeOfModel));
                    models.add(model);
                    modelsUpdate.add(model);
                    checkNameModel = true;
                    break;
            }
        }while (!checkNameModel);

        fileService.writeData(FILE_PATH_MODEL_UPDATE, modelsUpdate);
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin();
        System.out.println("✔ Bạn đã thêm sản phẩm thành công ✔\n");
    }

    public void showModelListAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Số điện thoại", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelViewAdmin()).println();
        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }

    public void showModelList() throws IOException {
        List<Model> models = modelService.getAllModel();
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();
        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }

    public void showModelListStepModelAdmin() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Số điện thoại", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelViewAdmin()).println();

        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
        boolean checkShow = false;
        modelViewAdmin();
    }

    public void showModelListStepModel() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();

        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
        boolean checkShow = false;
        modelView();
    }

    public void showModelList(List<Model> models) throws IOException {
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();
        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }

    public void showModelListAdmin(List<Model> models) throws IOException {
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();
        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }


    public  void showModelRental1(int searchId) throws IOException, ParseException {
        RentalService rentalService = new RentalService();
        List<Rental> rentals = rentalService.getAllRental();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (Rental rental : rentalAll) {
            if (rental.getIdRental() == searchId) {
                System.out.println(rental.rentalView());
            }
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        Rental rental = new Rental();
        RentalView rentalView = new RentalView();
        rentalView.printMonthFind(rentalAll,rentals, searchId);
    }


    public void showModelRental(int searchId) throws IOException, ParseException {
        RentalService rentalService = new RentalService();
        List<Rental> rentals = rentalService.getAllRental();
        List<Rental> rentalAll = rentalService.getAllRentalAll();
        Date currentDate = new Date(); // Ngày hiện tại

        System.out.println("            ╔══════════════╦══════════════╦══════════════╦══════════════╦═════════════╦═════════════╦═════════════╦═════════════╦═════════════════════════╦══════════╗");
        System.out.printf("            ║%-14s║%-14s║%-14s║%-14s║%-13s║%-13s║%-13s║%-13s║%-25s║%-10s║", "ID Người mẫu", "ID Khách hàng", "Tên khách hàng", "Tên người mẫu", "Ngày bắt đầu", "Ngày kết thúc", "Giá", "Tổng giá", "Ngày tạo Bill", "Trạng thái").println();
        System.out.println("            ╠══════════════╬══════════════╬══════════════╬══════════════╬═════════════╬═════════════╬═════════════╬═════════════╬═════════════════════════╬══════════╣");
        for (Rental rental : rentals) {
            Date rentalDate = rental.getEndDate(); // Ngày thuê của rental
            if (rental.getIdRental() == searchId && rentalDate.after(currentDate) && rental.getIdCustomer() == UserService.userLoginning.getId()) {
                System.out.println(rental.rentalView());
            }
        }
        for (Rental rental : rentalAll) {
            Date rentalDate = rental.getEndDate(); // Ngày thuê của rental
            if (rental.getIdRental() == searchId && rentalDate.after(currentDate) && rental.getIdCustomer() == UserService.userLoginning.getId()) {
                System.out.println(rental.rentalView());
            }
        }
        System.out.println("            ╚══════════════╩══════════════╩══════════════╩══════════════╩═════════════╩═════════════╩═════════════╩═════════════╩═════════════════════════╩══════════╝");
        RentalView rentalView = new RentalView();
        rentalView.printMonthFind(rentalAll,rentals, searchId);
    }

    public void showModelListByTypeAdmin() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();

        String typeOfModel;
        boolean checkType = false;
        do {
            noChange();
            System.out.println("Nhập danh mục cần xem! \"male\" or \"female\" :");
            typeOfModel = scanner.nextLine();
            if (typeOfModel.equals("0")) {
                checkType = true;
                launcher();
            }
            switch (typeOfModel) {
                case "male":
                    checkType = true;
                    break;
                case "female":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại! \"male\" or \"female\" or \"other\":");
                    break;
            }
        } while (!checkType);
        models.sort(new SortModelByIDIncrease());
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Số điện thoại", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getType().getName().equals(typeOfModel)) {
                System.out.printf(models.get(i).modelViewAdmin()).println();
            }

        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }

    public void showModelListByType() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();

        String typeOfModel;
        boolean checkType = false;
        do {
            noChange();
            System.out.println("Nhập danh mục cần xem! \"male\" or \"female\" :");
            typeOfModel = scanner.nextLine();
            if (typeOfModel.equals("0")) {
                checkType = true;
                launcher();
            }
            switch (typeOfModel) {
                case "male":
                    checkType = true;
                    break;
                case "female":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại! \"male\" or \"female\" or \"other\":");
                    break;
            }
        } while (!checkType);
        models.sort(new SortModelByIDIncrease());
        System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
        System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
        System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getType().getName().equals(typeOfModel)) {
                System.out.printf(models.get(i).modelView()).println();
            }

        }
        System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
    }


    public void ModelView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Danh sách Model                                ║");
        System.out.println("                               ║                   [1] Xem danh sách Model theo tên A-Z                            ║");
        System.out.println("                               ║                   [2] Xem danh sách Model theo tuổi tăng dần                      ║");
        System.out.println("                               ║                   [3] Xem danh sách Model theo chiều cao tăng dần                 ║");
        System.out.println("                               ║                   [4] Xem danh sách Model theo cân nặng tăng dần                  ║");
        System.out.println("                               ║                   [5] Xem danh sách Model theo giá tăng dần                       ║");
        System.out.println("                               ║                   [6] Quay lại Menu                                               ║");
        System.out.println("                               ║                   [7] Đăng xuất                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void modelViewAdmin() throws IOException, ParseException {
//        LoginView loginView = new LoginView();
        int select = 0;
        boolean checkAction = false;
        do {
            ModelView();
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
                    sortByNameIncreaseAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    sortByAgeIncreaseAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    sortByHeightIncreaseAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    sortByWeightIncreaseAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    sortByPriceIncreaseAdmin();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    AdminView adminView = new AdminView();
                    adminView.launcherAdmin();
                    break;
                case 7:
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
        if (checkAction) {
            launcher();
        }
    }

    public void modelView() throws IOException, ParseException {
//        LoginView loginView = new LoginView();
        int select = 0;
        boolean checkAction = false;
        do {
            ModelView();
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
                    sortByNameIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    sortByAgeIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    sortByWeightIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    sortByHeightIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    sortByPriceIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    CustomerView customerView = new CustomerView();
                    customerView.launcherCustomer();
                    break;
                case 7:
                    Menu menu = new Menu();
                    menu.login();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
        if (checkAction) {
            CustomerView customerView = new CustomerView();
            customerView.launcherCustomer();
        }
    }

    public void sortByNameIncreaseAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByName());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByNameIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByName());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByAgeIncreaseAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByAge());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByAgeIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByAge());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByHeightIncreaseAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByHeight());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByHeightIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByHeight());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByWeightIncreaseAdmin() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByWeight());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelListAdmin(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }

    public void sortByWeightIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByWeight());
//        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList(models);
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }


    public void searchModelView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Tìm kiếm Model                                 ║");
        System.out.println("                               ║                   [1] Tìm kiếm Model theo Id                                      ║");
        System.out.println("                               ║                   [2] Tìm kiếm Model theo tên                                     ║");
        System.out.println("                               ║                   [3] Tìm kiếm Model theo tuổi                                    ║");
        System.out.println("                               ║                   [4] Tìm kiếm Model theo chiều cao                               ║");
        System.out.println("                               ║                   [5] Tìm kiếm Model theo cân nặng                                ║");
        System.out.println("                               ║                   [6] Tìm kiếm Model theo giá                                     ║");
        System.out.println("                               ║                   [7] Tìm kiếm Model theo giới tính                               ║");
        System.out.println("                               ║                   [8] Quay lại Menu                                               ║");
        System.out.println("                               ║                   [9] Đăng xuất                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void searchModel() throws IOException, ParseException {
        List<Model> models = modelService.getAllModel();

        String typeOfModel;
        int select = 0;
        boolean checkAction = false;
        do {
            searchModelView();
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
                    searchModelById();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    searchModelByKeyword();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    searchModelByAgeRange();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    searchModelByHeightRange();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    searchModelByWeightRange();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    searchModelByPriceRange();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    searchModelByGenderType();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    CustomerView customerView = new CustomerView();
                    customerView.launcherCustomer();
                    checkAction = checkActionContinue();
                    break;
                case 9:
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
        if (checkAction) {
            launcher();
        }
    }


    public void searchModelByIdOrder() throws IOException, ParseException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Model> results = new ArrayList<>();

        boolean checkId = false;
        int searchId = 0;
        do {
            System.out.println("Nhập ID của người mẫu: ");
            try {
                searchId = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Vui lòng nhập số!");
                scanner.next();
                continue;
            }
            boolean checkOut = false;
            for (int i = 0; i < models.size(); i++) {
                int modelId = models.get(i).getId();
                if (modelId == searchId) {
                    results.add(models.get(i));
                    checkOut = true;
                }
            }
            if (!checkOut) {
                System.out.println("Không tìm thấy model với ID này!");
            } else {
                checkId = true;
            }
        } while (!checkId);
        showModelRental(searchId);
    }



    public void searchModelById() throws IOException, ParseException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Model> results = new ArrayList<>();
        boolean checkAction = false;
        noChange();
        int id = 0;
        do {
            System.out.println("Nhập ID của người mẫu: ");
            String input = scanner.nextLine();
            if(input.equals("0")) {
                checkAction = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Số lượng không hợp lệ vui lòng nhập lại");
                id = 0;
                continue;
            }
            if(id < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                id = 0;
                continue;
            }
            switch (modelService.checkIdModel(id)) {
                case 1:
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getIdModel() == id) {
                            System.out.println("            ╔═══════╦════════════════════╦═══════════╦══════════╦══════════╦══════════╦═══════════════╦═══════════════╦════════════════════════════════════════╗");
                            System.out.printf("            ║%-7s║%-20s║%-10s ║%-10s║%-10s║%-10s║%-15s║%-15s║%-40s║", "ID", "Tên", "Giới tính", "Tuổi", "Chiều cao", "Cân nặng", "Địa chỉ", "Giá", "Kinh nghiệm").println();
                            System.out.println("            ╠═══════╬════════════════════╬═══════════╬══════════╬══════════╬══════════╬═══════════════╬═══════════════╬════════════════════════════════════════╣");
                            System.out.printf(models.get(i).modelView()).println();
                            System.out.println("            ╚═══════╩════════════════════╩═══════════╩══════════╩══════════╩══════════╩═══════════════╩═══════════════╩════════════════════════════════════════╝");
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


    public void searchModelByKeyword() throws IOException, ParseException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();

        boolean checkKW = false;
        do {
            noChange();
            System.out.println("Nhập tên người mẫu bạn muốn tìm kiếm: ");
//            String kw = scanner.nextLine();
            String kw = scanner.nextLine().toUpperCase();
            if (kw.equals("0")) {
                checkKW = true;
                customerView.launcherCustomer();
            }
            boolean checkOut = false;
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getNameModel().toUpperCase().contains(kw.toUpperCase())) {
                    results.add(models.get(i));
                    checkOut = true;
                }
            }
            if (!checkOut) {
                System.out.println("Không tìm thấy người mẫu phù hợp, vui lòng nhập lại!");
                checkKW = false;
            } else {
                checkKW = true;
            }
        } while (!checkKW);
        showModelList(results);
    }


    public void searchModelByPriceRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();
        boolean checkRange = false;
        do {
            noChange();
            System.out.println("Nhập khoảng giá muốn tìm kiếm (ví dụ: 1000 1500): ");
            String input = scanner.nextLine();
            String[] priceRange = input.split(" ");
            try {
                int lowerBound = Integer.parseInt(priceRange[0]);
                int upperBound = Integer.parseInt(priceRange[1]);
                if (input.equals("0")) {
                    checkRange = true;
                    customerView.launcherCustomer();
                }
                if (lowerBound < 0 || upperBound < 0 || lowerBound > upperBound) {
                    System.out.println("Khoảng giá không hợp lệ, vui lòng nhập lại!");
                    checkRange = false;
                } else {
                    boolean checkOut = false;
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getPriceModel() >= lowerBound && models.get(i).getPriceModel() <= upperBound) {
                            results.add(models.get(i));
                            checkOut = true;
                        }
                    }
                    if (!checkOut) {
                        System.out.println("Không tìm thấy người mẫu phù hợp, vui lòng nhập lại!");
                        checkRange = false;
                    } else {
                        checkRange = true;
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Cú pháp không hợp lệ, vui lòng nhập lại!");
                checkRange = false;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while (!checkRange);
        showModelList(results);
    }


    public void searchModelByAgeRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();
        boolean checkRange = false;
        do {
            noChange();
            System.out.println("Nhập khoảng tuổi muốn tìm kiếm (ví dụ: 16 20): ");
            String input = scanner.nextLine();
            String[] ageRange = input.split(" ");
            try {
                int lowerBound = Integer.parseInt(ageRange[0]);
                int upperBound = Integer.parseInt(ageRange[1]);
                if (input.equals("0")) {
                    checkRange = true;
                    customerView.launcherCustomer();
                }
                if (lowerBound < 0 || upperBound < 0 || lowerBound > upperBound || upperBound > 120) {
                    System.out.println("Khoảng tuổi không hợp lệ, vui lòng nhập lại!");
                    checkRange = false;
                } else {
                    boolean checkOut = false;
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getAge() >= lowerBound && models.get(i).getAge() <= upperBound) {
                            results.add(models.get(i));
                            checkOut = true;
                        }
                    }
                    if (!checkOut) {
                        System.out.println("Không tìm thấy người mẫu phù hợp, vui lòng nhập lại!");
                        checkRange = false;
                    } else {
                        checkRange = true;
                    }
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Cú pháp không hợp lệ, vui lòng nhập lại!");
                checkRange = false;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while (!checkRange);
        showModelList(results);
    }

    public void searchModelByHeightRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Model> results = new ArrayList<>();

        boolean checkHeight = false;
        do {
            try {
                noChange();
                System.out.println("Nhập chiều cao tối thiểu (Cm): ");
                int minHeight = Integer.parseInt(scanner.nextLine());
                String minHeightString = "" + minHeight;
                if (minHeightString.equals("0")) {
                    checkHeight = true;
                    customerView.launcherCustomer();
                }
                System.out.println("Nhập chiều cao tối đa (Cm): ");
                int maxHeight = Integer.parseInt(scanner.nextLine());
                if (minHeight > maxHeight) {
                    System.out.println("Chiều cao tối thiểu phải nhỏ hơn chiều cao tối đa!");
                } else if (minHeight < 0 || minHeight > 200 || maxHeight < 0 || maxHeight > 200) {
                    System.out.println("Chiều cao phải lớn hơn 0 và nhỏ hơn 200, vui lòng nhập lại!");
                } else {
                    boolean checkOut = false;
                    for (int i = 0; i < models.size(); i++) {
                        int height = models.get(i).getHeight();
                        if (height >= minHeight && height <= maxHeight) {
                            results.add(models.get(i));
                            checkOut = true;
                        }
                    }
                    if (!checkOut) {
                        System.out.println("Không tìm thấy model trong khoảng chiều cao này!");
                    }
                    checkHeight = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Chiều cao phải là 1 số, vui lòng nhập lại!");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while (!checkHeight);
        showModelList(results);
    }

    public void searchModelByWeightRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Model> results = new ArrayList<>();

        boolean checkWeight = false;
        do {
            try {
                noChange();
                System.out.println("Nhập cân nặng tối thiểu (Kg): ");
                int minWeight = Integer.parseInt(scanner.nextLine());
                String minHeightString = "" + minWeight;
                if (minHeightString.equals("0")) {
                    checkWeight = true;
                    customerView.launcherCustomer();
                }
                System.out.println("Nhập cân nặng tối đa (Kg): ");
                int maxWeight = Integer.parseInt(scanner.nextLine());
                if (minWeight > maxWeight) {
                    System.out.println("Cân nặng tối thiểu phải nhỏ hơn cân nặng tối đaa!");
                } else if (minWeight < 0 || minWeight > 120 || maxWeight < 0 || maxWeight > 120) {
                    System.out.println("Cân nặng phải lớn hơn 0 và nhỏ hơn 120, vui lòng nhập lại!");
                } else {
                    boolean checkOut = false;
                    for (int i = 0; i < models.size(); i++) {
                        int weight = models.get(i).getWeight();
                        if (weight >= minWeight && weight <= maxWeight) {
                            results.add(models.get(i));
                            checkOut = true;
                        }
                    }
                    if (!checkOut) {
                        System.out.println("Không tìm thấy model trong khoảng cân nặng này!");
                    }
                    checkWeight = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Cân nặng phải là 1 số, vui lòng nhập lại!");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } while (!checkWeight);
        showModelList(results);
    }


    public void searchModelByGenderType() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> models = modelService.getAllModel();
        List<Model> results = new ArrayList<>();

        boolean checkGenderType = false;
        do {
            noChange();
            System.out.println("Nhập loại giới tính (Male/Female/Other): ");
            String searchGenderType = scanner.next();


            boolean checkOut = false;
            for (int i = 0; i < models.size(); i++) {
                String genderType = String.valueOf(models.get(i).getType());
                if (genderType.equalsIgnoreCase(searchGenderType)) {
                    results.add(models.get(i));
                    checkOut = true;
                }
            }
            if (!checkOut) {
                System.out.println("Không tìm thấy model với loại giới tính này!");
            }

            checkGenderType = true;
        } while (!checkGenderType);

        showModelList(results);
    }


    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}
