package view;

import model.EGender;
import model.Model;
import service.FileService;
import service.ModelService;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModelView {
    private final String FILE_PATH_MODEL = "Case2Module/src/main/data/model.csv";
    private final String FILE_PATH_MODEL_UPDATE = "Case2Module/src/main/data/modelUpdate.csv";
    private FileService fileService;
    private ModelService modelService;
    private Scanner scanner;

    public ModelView() {
        fileService = new FileService();
        modelService = new ModelService();
        scanner = new Scanner(System.in);
    }

    public void menuModelAdminView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                        1. Hiển thị danh sách Model                                ║");
        System.out.println("                               ║                        2. Hiển thị danh sách Model theo danh mục                  ║");
        System.out.println("                               ║                        3. Hiển thị Model theo giá tăng dần                        ║");
        System.out.println("                               ║                        4. Hiển thị Model theo giá giảm dần                        ║");
        System.out.println("                               ║                        5. Thêm Model vào danh sách                                ║");
        System.out.println("                               ║                        6. Chỉnh sửa Model theo id                                 ║");
        System.out.println("                               ║                        7. Tìm kiếm Model theo id                                  ║");
        System.out.println("                               ║                        8. Xóa đồ Model theo id                                    ║");
        System.out.println("                               ║                        9. Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void launcher() throws IOException {
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
                    showModelListStepModel();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    showModelListByType();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    sortByPriceIncrease();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    sortByPriceDecrease();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    addModel();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    editModelById();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    findModelById();
                    checkAction = checkActionContinue();
                    break;
                case 8:
                    deleteModelById();
                    checkAction = checkActionContinue();
                    break;

                case 9:
                    AdminView adminView = new AdminView();
                    adminView.launcher();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, mời nhập lại!");
                    checkAction = false;
                    break;
            }
        } while (!checkAction);
        if(checkAction) {
            AdminView adminView = new AdminView();
            adminView.launcher();
        }
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
    public void menuEditModelView() {
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                               Chọn mục bạn muốn sửa                               ║");
        System.out.println("                               ║                        1. Chỉnh sửa tên người mẫu                                 ║");
        System.out.println("                               ║                        2. Chỉnh sửa tuổi người mẫu                                ║");
        System.out.println("                               ║                        2. Chỉnh sửa chiều cao người mẫu                           ║");
        System.out.println("                               ║                        2. Chỉnh sửa cân nặng người mẫu                            ║");
        System.out.println("                               ║                        3. Chỉnh sửa giá nguời mẫu                                 ║");
        System.out.println("                               ║                        4. Chỉnh sửa giới tính người mẫu                           ║");
        System.out.println("                               ║                        5. Quay lại                                                ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void sortByPriceDecrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByPriceDecrease());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void sortByPriceIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByPriceIncrease());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void findModelById() throws IOException {
        List<Model> models = modelService.getAllModel();
        boolean checkAction = false;
        noChange();
        int id = 0;
        do {
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn tìm");
            String input = scanner.nextLine();
            if(input.equals("0")) {
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
            if(id < 0) {
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

    public void deleteModelById() throws IOException {
        showModelList();
        List<Model> models = modelService.getAllModel();
        List<Model> modelsUpdate = modelService.getAllModelUpdate();

        int id = 0;
        boolean checkID = false;
        do {
            noChange();
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn xóa:");
            String input = scanner.nextLine();
            if(input.equals("0")) {
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
            if(id < 0) {
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

    public void editModelById() throws IOException {
        showModelList();
        List<Model> models = modelService.getAllModel();
        List<Model> modelsUpdate = modelService.getAllModelUpdate();
        ModelView modelView = new ModelView();

        int id = 0;
        boolean checkId = false;
        do {
            noChange();
            boolean checkAction = false;
            System.out.println("Nhập ID đồ uống, thức ăn bạn muốn sửa");
            String input = scanner.nextLine();
            if(input.equals("0")) {
                checkId = true;
                launcher();
            }
            try {
                id = Integer.parseInt(input);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ID phải là một số, vui lòng nhập lại!");
                id = 0;
                continue;
            }
            if(id < 0) {
                System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
                id = 0;
                continue;
            }
            int check = modelService.checkIdModel(id);
            switch (check) {
                case 1:
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).getIdModel() == id) {
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
                                            System.out.println("Nhập tên đồ uống, thức ăn:");
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
                                            if(modelsUpdate.get(j).getIdModel() == id) {
                                                modelsUpdate.get(j).setNameModel(nameModel);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 2:
                                        int quantity = 0;
                                        boolean checkValid = false;
                                        boolean checkValidQuantity = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập số lượng bạn muốn sửa:");
                                            String input1 = scanner.nextLine();
                                            if(input1.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            checkValid = ValidateUtils.isQuantity(quantity,input1);
                                            if (!checkValid) {
                                                checkValidQuantity = false;
                                            }else {
                                                quantity = Integer.parseInt(input1);
                                                models.get(i).setQuantityModel(quantity);
                                                for (int j = 0; j < modelsUpdate.size(); j++) {
                                                    if(modelsUpdate.get(j).getIdModel() == id) {
                                                        modelsUpdate.get(j).setQuantityModel(quantity);
                                                    }
                                                }
                                                checkValidQuantity = true;
                                            }
                                        } while (!checkValidQuantity);
                                        checkAction = true;
                                        break;
                                    case 3:
                                        double price = 0;
                                        boolean checkValidPrice = false;
                                        do {
                                            noChange();
                                            System.out.println("Nhập giá bạn muốn sửa. Giá phải lớn hơn 0đ và nhỏ hơn hoặc bằng 200.000đ!");
                                            String input1 = scanner.nextLine();
                                            if(input1.equals("0")) {
                                                checkId = true;
                                                launcher();
                                            }
                                            try {
                                                price = Double.parseDouble(input1);
                                            } catch (NumberFormatException numberFormatException) {
                                                System.out.println("Nhập lỗi, giá phải là một số, vui lòng nhập lại!");
                                                price = 0;
                                                continue;
                                            }
                                            checkValidPrice = ValidateUtils.isValidPrice(price);
                                            if (checkValidPrice == false) {
                                                System.out.println("Giá phải lớn hơn 0đ và nhỏ hơn hoặc bằng 200.000đ, vui lòng nhập lại!");
                                            }
                                        } while (!checkValidPrice);
                                        models.get(i).setPriceModel(price);
                                        for (int j = 0; j < modelsUpdate.size(); j++) {
                                            if(modelsUpdate.get(j).getIdModel() == id) {
                                                modelsUpdate.get(j).setPriceModel(price);
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 4:
                                        noChange();
                                        System.out.println("Nhập loại bạn muốn sửa:");
                                        String type = scanner.nextLine();
                                        if(type.equals("0")) {
                                            checkId = true;
                                            launcher();
                                        }
                                        if (models.get(i).getType().equals(EGender.male)) {
                                            switch (type) {
                                                case "MALE":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"drink\"! Mời bạn nhập \"bakery\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            models.get(i).setType(EGender.getEGenderByName(type));
                                            for (int j = 0; j < modelsUpdate.size(); j++) {
                                                if(modelsUpdate.get(j).getIdModel() == id) {
                                                    modelsUpdate.get(j).setType(EGender.getEGenderByName(type));
                                                }
                                            }
                                        }else if(models.get(i).getType().equals(EGender.female)){
                                            switch (type) {
                                                case "drink":
                                                    break;
                                                default:
                                                    System.out.println("Nhập sai, kiểu hiện tại là \"bakery\"! Mời bạn nhập \"drink\" hoặc quay lại!");
                                                    type = scanner.nextLine();
                                                    break;
                                            }
                                            models.get(i).setType(EGender.getEGenderByName(type));
                                            for (int j = 0; j < modelsUpdate.size(); j++) {
                                                if(modelsUpdate.get(j).getIdModel() == id) {
                                                    modelsUpdate.get(j).setType(EGender.getEGenderByName(type));
                                                }
                                            }
                                        }
                                        checkAction = true;
                                        break;
                                    case 5:
                                        modelView.editModelById();
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
        fileService.writeData(FILE_PATH_MODEL_UPDATE,modelsUpdate);
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
        System.out.println("✔ Bạn đã cập nhật sản phẩm thành công ✔\n");
    }

    public void addModel() throws IOException {
        List<Model> models = modelService.getAllModel();
        List<Model> models1 = modelService.getAllModelUpdate();
        Model model = new Model();

        String typeOfModel;
        boolean checkType = false;
        do {
            noChange();
            System.out.println("Nhập danh mục cần thêm! \"drink\" or \"bakery\":");
            typeOfModel = scanner.nextLine();
            if(typeOfModel.equals("0")){
                checkType = true;
                launcher();
            }
            switch (typeOfModel) {
                case "drink":
                    checkType = true;
                    break;
                case "bakery":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại! \"drink\" or \"bakery\":");
                    break;
            }
        }while (!checkType);
        boolean checkNameModel = false;
        do {
            String nameModel = null;
            boolean checkValidNameModel = false;
                do {
                    noChange();
                    System.out.println("Nhập tên đồ uống, thức ăn (tối đa 40 kí tự):");
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
                        noChange();
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
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
                                        System.out.println("Số lượng nhập vượt quá số lượng trên menu, vui lòng nhập lại! Nhỏ hơn hoặc bằng " + inputQuantity);
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
                        noChange();
                        System.out.println("Nhập số lượng");
                        String input = scanner.nextLine();
                        if(input.equals("0")) {
                            checkNameModel = true;
                            launcher();
                        }
                        checkValidQuantity2 = ValidateUtils.isQuantity(quantity1,input);
                        if (checkValidQuantity2) {
                           quantity1 = Integer.parseInt(input);
                        }
                    } while (!checkValidQuantity2);

                    double price = 0;
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
                            price = Double.parseDouble(input);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Nhập lỗi, giá phải là một số, vui lòng nhập lại! Giá từ 0đ-200.000đ");
                            price = 0;
                            continue;
                        }
                        checkValidPrice = ValidateUtils.isValidPrice(price);     // false
                        if (checkValidPrice == false) {
                            System.out.println("Giá phải lớn hơn 0đ và nhỏ hơn hoặc bằng 200.000đ vui lòng nhập lại! Giá từ 0đ-200.000đ");
                        }
                    } while (!checkValidPrice);

                    models.sort(new SortModelByIDIncrease());
                    int id = models.get(models.size() - 1).getIdModel() + 1;
                    model.setIdModel(id);
                    model.setNameModel(nameModel);
                    model.setQuantityModel(quantity1);
                    model.setPriceModel(price);
                    model.setType(EGender.getEGenderByName(typeOfModel));
                    models.add(model);
                    models.add(model);
                    checkNameModel = true;
                    break;
            }
        }while (!checkNameModel);

//        fileService.writeData(FILE_PATH_MODEL_UPDATE, modelsUpdate);
        fileService.writeData(FILE_PATH_MODEL_UPDATE, models);
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
        System.out.println("✔ Bạn đã thêm sản phẩm thành công ✔\n");
    }

    public void showModelList() throws IOException {
        List<Model> models = modelService.getAllModel();
//        models.sort(new SortModelByIDIncrease());
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Model's name", "Quantity", "Price", "Type Of Model").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }
    public void showModelListStepModel() throws IOException {
        List<Model> models = modelService.getAllModel();
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Model's name", "Quantity", "Price", "Type Of Model").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (int i = 0; i < 10; i++) {
            System.out.printf(models.get(i).modelView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
        boolean checkShow = false;

        int size = models.size()-10;
        int index = 10;
        while (size >= 10) {
            do {
                noChange();
                System.out.println("Nhấn Enter để xem tiếp!");
                String s = scanner.nextLine();
                if(s.equals("0")){
                    checkShow = true;
                    launcher();
                }
                if (s.equals("")) {
                    System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                    for (int i = index; i < index + 10; i++) {
                        if(size>=10){
                            System.out.printf(models.get(i).modelView()).println();
                        }
                    }
                    System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
                    index += 10;
                    size -= 10;
                    checkShow = true;
                }else {
                    checkShow = false;
                }
            }while (!checkShow);
        }
        boolean checkShow2 = false;
        do {
            System.out.println("Nhấn Enter để xem tiếp!");
            String s = scanner.nextLine();
            if(s.equals("0")){
                checkShow2 = true;
                launcher();
            }
            if (s.equals("")) {
                System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
                for (int i = size; i > 0; i--) {
                    System.out.printf(models.get(models.size() - i).modelView()).println();
                }
                System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
                checkShow2 = true;
            }else {
                checkShow2 = false;
            }
        }while (!checkShow2);
        System.out.println("Finished!");

        modelView();
    }
    public void showModelList(List<Model> models) throws IOException {
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Model's name", "Quantity", "Price", "Type Of Model").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (Model model : models) {
            System.out.printf(model.modelView()).println();
        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }

    public void showModelListByType() throws IOException {
        List<Model> models = modelService.getAllModel();

        String typeOfModel;
        boolean checkType = false;
        do {
            noChange();
            System.out.println("Nhập danh mục cần xem! \"drink\" or \"bakery\":");
            typeOfModel = scanner.nextLine();
            if(typeOfModel.equals("0")){
                checkType = true;
                launcher();
            }
            switch (typeOfModel) {
                case "drink":
                    checkType = true;
                    break;
                case "bakery":
                    checkType = true;
                    break;
                default:
                    checkType = false;
                    System.out.println("Nhập sai, xin mời nhập lại! \"drink\" or \"bakery\":");
                    break;
            }
        }while (!checkType);
        models.sort(new SortModelByIDIncrease());
        System.out.println("            ╔═══════╦══════════════════════════════╦═══════════╦════════════════╦═══════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-10s║ %-15s║ %-18s║", "ID", "Model's name", "Quantity", "Price", "Type Of Model").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬═══════════╬════════════════╬═══════════════════╣");
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i).getType().getName().equals(typeOfModel)) {
                System.out.printf(models.get(i).modelView()).println();
            }

        }
        System.out.println("            ╚═══════╩══════════════════════════════╩═══════════╩════════════════╩═══════════════════╝");
    }







    public void ModelView(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Danh sách Model                                ║");
        System.out.println("                               ║                   [1] Xem danh sách Model theo tên tăng dần                       ║");
        System.out.println("                               ║                   [2] Xem danh sách Model theo tuổi tăng dần                      ║");
        System.out.println("                               ║                   [3] Xem danh sách Model theo chiều cao tăng dần                 ║");
        System.out.println("                               ║                   [4] Xem danh sách Model theo cân nặng tăng dần                  ║");
        System.out.println("                               ║                   [5] Xem danh sách Model theo giá tăng dần                       ║");
        System.out.println("                               ║                   [6] Quay lại Menu                                               ║");
        System.out.println("                               ║                   [7] Đăng xuất                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }
    public void modelView() throws IOException {
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
                    CustomerView.menuCustomerView();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    Menu menu = new Menu();
                    menu.login();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            launcher();
        }
    }

    public void sortByNameIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByName());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void sortByAgeIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByAge());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void sortByHeightIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByHeight());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }
    public void sortByWeightIncrease() throws IOException {
        List<Model> models = modelService.getAllModel();
        models.sort(new SortModelByWeight());
        fileService.writeData(FILE_PATH_MODEL, models);
        showModelList();
//        System.out.println("✔ Bạn đã sắp xếp sản phẩm thành công ✔\n");
    }





    public void searchModelView(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                    Tìm kiếm Model                                ║");
        System.out.println("                               ║                   [1] Tìm kiếm Model theo tên                                     ║");
        System.out.println("                               ║                   [2] Tìm kiếm Model theo tuổi                                    ║");
        System.out.println("                               ║                   [3] Tìm kiếm Model theo chiều cao                               ║");
        System.out.println("                               ║                   [4] Tìm kiếm Model theo cân nặng                                ║");
        System.out.println("                               ║                   [5] Tìm kiếm Model theo giá                                     ║");
        System.out.println("                               ║                   [6] Quay lại Menu                                               ║");
        System.out.println("                               ║                   [7] Đăng xuất                                                   ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════════════════════════════════╝");
    }

    public void searchModel() throws IOException {
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
                    searchModelByKeyword();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    searchModelByAgeRange();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    searchModelByHeightRange();
                    checkAction = checkActionContinue();
                    break;
                case 4:
                    searchModelByWeightRange();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    searchModelByPriceRange();
                    checkAction = checkActionContinue();
                    break;
                case 6:
                    CustomerView.menuCustomerView();
                    checkAction = checkActionContinue();
                    break;
                case 7:
                    Menu menu = new Menu();
                    menu.login();
                    checkAction = checkActionContinue();
                    break;
                default:
                    System.out.println("Nhập sai chức năng, vui lòng nhập lại!");
                    checkAction = false;
                    break;
            }
        }while (!checkAction);
        if(checkAction) {
            launcher();
        }
    }

    public void searchModelByKeyword() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();

        boolean checkKW = false;
        do {
            noChange();
            System.out.println("Nhập keyword bạn muốn tìm kiếm: ");
            String kw = scanner.nextLine();
            if(kw.equals("0")) {
                checkKW = true;
                customerView.launcher();
            }
            boolean checkOut = false;
            for (int i = 0; i < models.size(); i++) {
                if (models.get(i).getNameModel().toUpperCase().contains(kw.toUpperCase())) {
                    results.add(models.get(i));
                    checkOut = true;
                }
            }
            if(!checkOut) {
                System.out.println("Không tìm thấy món, vui lòng nhập lại!");
                checkKW = false;
            }else {
                checkKW = true;
            }
        }while (!checkKW);
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
            int lowerBound = Integer.parseInt(priceRange[0]);
            int upperBound = Integer.parseInt(priceRange[1]);
            if(lowerBound < 0 || upperBound < 0 || lowerBound > upperBound) {
                System.out.println("Khoảng giá không hợp lệ, vui lòng nhập lại!");
                checkRange = false;
            }else {
                boolean checkOut = false;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getPriceModel() >= lowerBound && models.get(i).getPriceModel() <= upperBound) {
                        results.add(models.get(i));
                        checkOut = true;
                    }
                }
                if(!checkOut) {
                    System.out.println("Không tìm thấy món, vui lòng nhập lại!");
                    checkRange = false;
                }else {
                    checkRange = true;
                }
            }
        }while (!checkRange);
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
            int lowerBound = Integer.parseInt(ageRange[0]);
            int upperBound = Integer.parseInt(ageRange[1]);
            if(lowerBound < 0 || upperBound < 0 || lowerBound > upperBound || upperBound > 120) {
                System.out.println("Khoảng tuổi không hợp lệ, vui lòng nhập lại!");
                checkRange = false;
            }else {
                boolean checkOut = false;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getAge() >= lowerBound && models.get(i).getAge() <= upperBound) {
                        results.add(models.get(i));
                        checkOut = true;
                    }
                }
                if(!checkOut) {
                    System.out.println("Không tìm thấy món, vui lòng nhập lại!");
                    checkRange = false;
                }else {
                    checkRange = true;
                }
            }
        }while (!checkRange);
        showModelList(results);
    }

    public void searchModelByHeightRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();
        boolean checkRange = false;
        do {
            noChange();
            System.out.println("Nhập khoảng chiều cao muốn tìm kiếm (ví dụ: 1.55 1.65): ");
            String input = scanner.nextLine();
            String[] heightRange = input.split(" ");
            double lowerBound = Double.parseDouble(heightRange[0]);
            double upperBound = Double.parseDouble(heightRange[1]);
            if(lowerBound < 0 || upperBound < 0 || lowerBound > upperBound || upperBound > 3) {
                System.out.println("Khoảng chiều cao không hợp lệ, vui lòng nhập lại!");
                checkRange = false;
            }else {
                boolean checkOut = false;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).getHeight() >= lowerBound && models.get(i).getHeight() <= upperBound) {
                        results.add(models.get(i));
                        checkOut = true;
                    }
                }
                if(!checkOut) {
                    System.out.println("Không tìm thấy Model, vui lòng nhập lại khoảng chiều cao!");
                    checkRange = false;
                }else {
                    checkRange = true;
                }
            }
        }while (!checkRange);
        showModelList(results);
    }

    public void searchModelByWeightRange() throws IOException {
        CustomerView customerView = new CustomerView();
        List<Model> results = new ArrayList<>();
        List<Model> models = modelService.getAllModel();
        boolean checkWeight = false;
        do {
            noChange();
            System.out.println("Nhập cân nặng tối thiểu: ");
            double minWeight = scanner.nextDouble();
            System.out.println("Nhập cân nặng tối đa: ");
            double maxWeight = scanner.nextDouble();
            if (minWeight > maxWeight) {
                System.out.println("Cân nặng tối thiểu phải nhỏ hơn cân nặng tối đa!");
            } else {
                boolean checkOut = false;
                for (int i = 0; i < models.size(); i++) {
                    double weight = models.get(i).getWeight();
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
        } while (!checkWeight);
        showModelList(results);
    }





























    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }
}
