package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String PHONENUMBER_REGEX = "((84|0)[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String CCCD_REGEX = "^0\\d{11}$";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}$";
    public static final  String DAY_REGEX = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)$"; //01-01-2023
    public static final  String BIRTHDAY_REGEX = "^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$"; //01/01/2023
    public static final String MONTH_REGEX = "^(0?[1-9]|1[012])-((19|20)\\d\\d)$"; // 04-2023
    public static final String FULLNAME_REGEX = "^[\\p{L} \\.'-]+$";
    public static final String NAMEFOOD_REGEX = "^[\\p{L}0-9\\s\\.\\,\\-'()]{1,40}$";
    public static boolean isNameFood (String nameFood) {
        return Pattern.matches(NAMEFOOD_REGEX, nameFood);
    }
    public static boolean isFullName (String fullName) {
        return Pattern.matches(FULLNAME_REGEX, fullName);
    }
    public static boolean isPhoneNumber(String number) {
        return Pattern.matches(PHONENUMBER_REGEX, number);
    }
    public static boolean isEmail(String email){
        return Pattern.matches(EMAIL_REGEX,email);
    }
    public static boolean isCCCD(String cccd){
        return Pattern.matches(CCCD_REGEX,cccd);
    }
    public static boolean isUserName(String username){
        return Pattern.matches(USERNAME_REGEX,username);
    }
    public static boolean isDay(String date) {
        return  Pattern.matches(DAY_REGEX,date);
    }
    public static boolean isBirthDay(String date) {
        return Pattern.matches(BIRTHDAY_REGEX,date);
    }
    public static boolean isMonth(String month) {
        return Pattern.matches(MONTH_REGEX,month);
    }
    public static boolean isPassWord(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }
    public static boolean isValidPrice(double price) {
        if (price > 0 && price < 200000) {
            return true;
        }
        return false;
    }
    public static boolean isQuantity(int quantity, String inputQuantity) {
        try {
            quantity = Integer.parseInt(inputQuantity);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Nhập lỗi, số lượng phải là một số, vui lòng nhập lại!");
            quantity = 0;
            return false;
        }
        if(quantity <= 0 && quantity > 1000) {
            System.out.println("Số lượng phải lớn hơn 0 và nhỏ hơn 1000, vui lòng nhập lại! Số lượng từ 1-1000");
            quantity = 0;
            return false;
        }
        return true;
    }
    public static String parseCommaToChar(String s) {
        String s1 = s.replaceAll(",", "!");
        return s1;
    }
    public static String parseCharToComma(String s) {
        String s1 = s.replaceAll("!", ",");
        return s1;
    }
    public static boolean isId (int id, String input) {
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID không hợp lệ vui lòng nhập lại!");
            id = 0;
            return false;
        }
        if(id < 0) {
            System.out.println("ID phải lớn hơn 0, vui lòng nhập lại!");
            id = 0;
            return false;
        }
        return true;
    }

}
