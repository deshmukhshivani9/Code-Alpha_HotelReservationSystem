import java.util.*;
import java.io.*;

// Room class
class Room {
    int roomNumber;
    String type;
    boolean isBooked;

    Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isBooked = false;
    }
}

// Booking class
class Booking {
    String customerName;
    int roomNumber;
    String roomType;

    Booking(String customerName, int roomNumber, String roomType) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    public String toString() {
        return customerName + " | Room: " + roomNumber + " | Type: " + roomType;
    }
}

// MAIN CLASS (ONLY ONE PUBLIC CLASS)
public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        initializeRooms();

        int choice;
        do {
            System.out.println("\n======================================");
            System.out.println("       🏨 HOTEL RESERVATION SYSTEM");
            System.out.println("======================================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    saveToFile();
                    System.out.println("💾 Data saved. Thank you!");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }

        } while (choice != 5);

        sc.close();
    }

    static void initializeRooms() {
        for (int i = 1; i <= 3; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 4; i <= 6; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 7; i <= 9; i++) rooms.add(new Room(i, "Suite"));
    }

    static void viewRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println("Room " + r.roomNumber + " (" + r.type + ")");
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        viewRooms();

        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && !r.isBooked) {

                System.out.println("Processing payment...");
                System.out.println("✅ Payment successful!");

                r.isBooked = true;
                bookings.add(new Booking(name, roomNo, r.type));

                System.out.println("🎉 Room booked successfully!");
                return;
            }
        }

        System.out.println("❌ Room not available!");
    }

    static void cancelBooking() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        Iterator<Booking> it = bookings.iterator();

        while (it.hasNext()) {
            Booking b = it.next();

            if (b.customerName.equalsIgnoreCase(name)) {

                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isBooked = false;
                    }
                }

                it.remove();
                System.out.println("✅ Booking cancelled!");
                return;
            }
        }

        System.out.println("❌ Booking not found!");
    }

    static void viewBookings() {
        System.out.println("\n--- Booking Details ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter("bookings.txt");

            for (Booking b : bookings) {
                fw.write(b.toString() + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }
}
