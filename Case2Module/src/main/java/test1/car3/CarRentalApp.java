package test1.car3;

public class CarRentalApp {
    public static void main(String[] args) {
        Calendar calendar = new Calendar();

        // Rent a Honda Civic on day 5
        Customer customer1 = new Customer();
        customer1.rentHondaCivic(5);
        int rentalDays1 = 3;
        int rentalDay1 = customer1.getRentalDay();
        for (int i = 0; i < rentalDays1; i++) {
            calendar.rentCar(rentalDay1 + i, 1);
        }

        // Rent a Toyota Corolla on day 10
        Customer customer2 = new Customer();
        int rentalDays2 = 5;
        int rentalDay2 = 10;
        for (int i = 0; i < rentalDays2; i++) {
            calendar.rentCar(rentalDay2 + i, 2);
        }

        // Print the calendar
        calendar.printCalendar();

        // Calculate and print the total price for customer 1
        Payment payment = new Payment();
        int totalPrice1 = payment.calculateTotalPrice(rentalDays1);
        System.out.println("Tổng giá cho khách hàng 1: $" + totalPrice1);

        // Calculate and print the total price for customer 2
        int totalPrice2 = payment.calculateTotalPrice(rentalDays2);
        System.out.println("Tổng giá cho khách hàng 2: $" + totalPrice2);
    }
}
