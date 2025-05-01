package busresv;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int userId = -1;

        while (true) {
            if (userId == -1) {
                System.out.println("\n=== BUS RESERVATION SYSTEM ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> UserService.registerUser();
                    case 2 -> userId = UserService.loginUser();
                    case 3 -> System.exit(0);
                    default -> System.out.println("❌ Invalid choice.");
                }
            } else {
                System.out.println("\n=== MENU ===");
                System.out.println("1. View All Buses");
                System.out.println("2. Add a New Bus");
                System.out.println("3. Book a Seat");
                System.out.println("4. View My Bookings");
                System.out.println("5. Cancel Booking");
                System.out.println("6. Logout");
                System.out.print("Choose: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> BusService.viewAllBuses();
                    case 2 -> BusService.addBus();
                    case 3 -> BookingService.bookSeat(userId);  // Book a seat with passenger details
                    case 4 -> BookingService.viewBookings(userId);
                    case 5 -> BookingService.cancelBooking(userId);
                    case 6 -> userId = -1;
                    default -> System.out.println("❌ Invalid choice.");
                }
            }
        }
    }
}
