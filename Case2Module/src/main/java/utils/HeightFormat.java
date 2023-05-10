package utils;

public class HeightFormat {
    public static String formatHeight(double height) {
        String formattedHeight = String.format("%.0f", height);
        int length = formattedHeight.length();
        if (length > 2) {
            int meter = Integer.parseInt(formattedHeight.substring(0, length - 2));
            int centimeter = Integer.parseInt(formattedHeight.substring(length - 2));
            return meter + "m" + centimeter;
        } else if (length == 2) {
            return "0m" + formattedHeight;
        } else {
            return "0m0" + formattedHeight;
        }
    }

}
