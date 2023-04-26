package test1.car4;

import java.util.Scanner;

public class CarRental {
    // Mảng boolean lưu trữ thông tin về việc thuê xe trên các ngày trong tháng
    static boolean[] rentalStatus = new boolean[30];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chào mừng đến với dịch vụ cho thuê xe!");

        // Lựa chọn xe
        System.out.println("Please select a car:");
        System.out.println("1. Honda Civic");
        System.out.println("2. Toyota Camry");
        System.out.println("3. Mazda CX-5");
        int carChoice = scanner.nextInt();

        // Hiển thị lịch thuê
        System.out.println("Sau đây là lịch thuê tháng 4:");
        System.out.println("Su Mo Tu We Th Fr Sa");
        for (int i = 1; i <= 30; i++) {
            if (rentalStatus[i-1]) {
                System.out.print(" X");
            } else {
                System.out.print("  ");
            }
            if (i % 7 == 0) {
                System.out.println();
            }
        }

        // Chọn ngày thuê
        System.out.println("Bạn muốn thuê xe bao nhiêu ngày?");
        int rentalDays = scanner.nextInt();
        System.out.println("Bạn muốn bắt đầu thuê vào ngày nào? (1-30)");
        int rentalStartDay = scanner.nextInt();

        // Kiểm tra tính khả dụng của ngày thuê
        boolean rentalAvailable = true;
        for (int i = rentalStartDay-1; i < rentalStartDay+rentalDays-1; i++) {
            if (rentalStatus[i]) {
                rentalAvailable = false;
                break;
            }
        }
        if (!rentalAvailable) {
            System.out.println("Xe không có sẵn vào những ngày đã chọn. Vui lòng chọn lại.");
        } else {
            // Đánh dấu các ngày đã được thuê trên lịch
            for (int i = rentalStartDay-1; i < rentalStartDay+rentalDays-1; i++) {
                rentalStatus[i] = true;
            }
            System.out.println("Bạn đã thuê thành công xe từ ngày " + rentalStartDay + " tới ngày " + (rentalStartDay+rentalDays-1) + ".");
            System.out.println("Cảm ơn");
        }
    }
}



///* viết cho tôi 1 chương trình java cho thuê ô tô đơn giản theo hướng dẫn dưới đây. Khách hàng 1 chọn thuê 1 trong nhiều xe,
//sau đó chọn xe ô tô Honda Civic, sau khi chọn xe thì hiện ra bảng lịch tháng 4, tô đỏ những ngày mà chiếc
//ô tô Honda Civic đã được thuê trước đó nếu có. Nhập số ngày khách hàng 1 muốn thuê, nhập ngày để thuê,
//sau đó tô xanh lá các ngày mà khách hàng 1 đã chọn. Xong rồi thì thanh toán.
//Tiếp theo Khách hàng 2 chọn thuê 1 trong nhiều xe, sau đó chọn xe ô tô Honda Civic, sau khi chọn xe
//thì hiện ra bảng lịch tháng 4, tô đỏ những ngày mà chiếc ô tô Honda Civic đã được thuê trước đó nếu có.
//Nhập số ngày khách hàng 2 muốn thuê, nhập ngày để thuê. Nếu ngày khác hàng 2 chọn trùng với các ô màu đỏ
//trước đó, thông báo ô tô đã được thuê trước, yêu cầu khách hàng chọn lại. Nếu khách hàng 2 chọn các ngày
//không bị tô đỏ thì thông báo Ok, sau đó tô xanh lá các ngày mà khách hàng 2 đã chọn. Xong rồi thì thanh toán.
///*
