package utils;

public class WeightFormat {
    public static String weightFormat(double weight) {
        String formattedWeight = String.format("%.0f kg", weight);
        return formattedWeight;
    }
}
