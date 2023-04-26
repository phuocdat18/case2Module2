package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static String convertPriceToString(double price) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
        String formattedAmount = currencyFormatter.format(price);
        return formattedAmount;
    }
    public static double parseDouble(String price) {
        String priceNew = price.replaceAll("\\D+", "");
        return Double.parseDouble(priceNew);
    }
    public static int parseInteger(double price) {
        String price1 = String.valueOf(price);
        String priceNew = price1.replaceAll("\\D+\\d", "");
        return Integer.parseInt(priceNew);
    }
}
