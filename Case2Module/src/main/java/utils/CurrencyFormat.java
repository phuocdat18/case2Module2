package utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormat {

    public static String convertPriceToString(int price) {
        StringBuilder formattedAmount = new StringBuilder();
        formattedAmount.append("$");
        String priceStr = String.valueOf(price);
        int digits = priceStr.length();
        int count = 0;
        for (int i = digits - 1; i >= 0; i--) {
            char digit = priceStr.charAt(i);
            if (count > 0 && count % 3 == 0 && i != 0) {
                formattedAmount.insert(1, ".");
            }
            formattedAmount.insert(1, digit);
            count++;
        }
        return formattedAmount.toString();
    }


    public static int parseDouble(String price) {
        String priceNew = price.replaceAll("\\D+", "");
        return Integer.parseInt(priceNew);
    }
    public static int parseInteger(int price) {
        String price1 = String.valueOf(price);
        String priceNew = price1.replaceAll("\\D+\\d", "");
        return Integer.parseInt(priceNew);
    }
}
