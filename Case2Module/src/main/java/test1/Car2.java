package test1;

import java.util.Scanner;

public class Car2 {
    public static void main(String[] args) {
        int[] calendar = new int[30];
        Scanner scanner = new Scanner(System.in);

        // Nhập số ngày thuê của khách hàng 1 và đánh dấu các ngày đó trên lịch
        System.out.print("Nhap so ngay thue cua khach hang 1: ");
        int numDays1 = scanner.nextInt();
        for (int i = 0; i < numDays1; i++) {
            System.out.print("Nhap ngay thu " + (i + 1) + " muon thue (1-30): ");
            int day = scanner.nextInt();
            if (calendar[day - 1] == 1) {
                System.out.println("Khach hang 1 khong the thue ngay " + day + " vi da co khach hang 2 thue ngay nay");
            } else {
                calendar[day - 1] = 1;
            }
        }

        // Nhập số ngày thuê của khách hàng 2 và đánh dấu các ngày đó trên lịch
        System.out.print("Nhap so ngay thue cua khach hang 2: ");
        int numDays2 = scanner.nextInt();
        for (int i = 0; i < numDays2; i++) {
            System.out.print("Nhap ngay thu " + (i + 1) + " muon thue (1-30): ");
            int day = scanner.nextInt();
            if (calendar[day - 1] == 1) {
                System.out.println("Khach hang 2 khong the thue ngay " + day + " vi da co khach hang 1 thue ngay nay");

            } else {
                calendar[day - 1] = 2;

            }
        }

        // In lịch tháng 4 với các ngày đã được đánh dấu tô đỏ
        System.out.println("Lich thang 4:");
        for (int i = 1; i <= 30; i++) {
            if (calendar[i - 1] == 1) {
                System.out.print("\u001B[31m" + i + "\u001B[0m ");
            } else if (calendar[i - 1] == 2) {
                System.out.print("\u001B[33m" + i + "\u001B[0m ");
            } else {
                System.out.print(i + " ");
            }
            if (i % 7 == 0) {
                System.out.println();
            }
        }
    }
}





