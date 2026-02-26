import java.io.*;
import java.util.*;

// Room class
class Room {
    int roomNumber;
    String category;
    boolean isAvailable;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }
}

// Reservation class
class Reservation {
    String customerName;
    int roomNumber;
    String category;
    double payment;

    Reservation(String customerName, int roomNumber, String category, double payment) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.payment = payment;
    }

    public String toString() {
        return customerName + "," + roomNumber + "," + category + "," + payment;
    }
}

// Main Hotel System
public class HotelSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        initializeRooms();
        loadReservations();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelReservation();
                    break;

                case 4:
                    viewReservations();
                    break;

                case 5:
                    saveReservations();
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void initializeRooms() {

        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    static void viewRooms() {

        System.out.println("\nAvailable Rooms:");

        for (Room r : rooms) {
            if (r.isAvailable)
                System.out.println("Room " + r.roomNumber + " - " + r.category);
        }
    }

    static void bookRoom() {

        System.out.print("Enter your name: ");
        sc.nextLine();
        String name = sc.nextLine();

        viewRooms();

        System.out.print("Enter room number: ");
        int roomNo = sc.nextInt();

        for (Room r : rooms) {

            if (r.roomNumber == roomNo && r.isAvailable) {

                double payment = getPayment(r.category);

                System.out.println("Payment successful: ₹" + payment);

                reservations.add(new Reservation(name, roomNo, r.category, payment));

                r.isAvailable = false;

                saveReservations();

                System.out.println("Room booked successfully!");

                return;
            }
        }

        System.out.println("Room not available!");
    }

    static double getPayment(String category) {

        if (category.equals("Standard"))
            return 1000;

        else if (category.equals("Deluxe"))
            return 2000;

        else
            return 3000;
    }

    static void cancelReservation() {

        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();

        for (Reservation res : reservations) {

            if (res.roomNumber == roomNo) {

                reservations.remove(res);

                for (Room r : rooms)
                    if (r.roomNumber == roomNo)
                        r.isAvailable = true;

                saveReservations();

                System.out.println("Reservation cancelled!");

                return;
            }
        }

        System.out.println("Reservation not found!");
    }

    static void viewReservations() {

        System.out.println("\nBooking Details:");

        for (Reservation r : reservations) {

            System.out.println("Name: " + r.customerName +
                    " Room: " + r.roomNumber +
                    " Category: " + r.category +
                    " Payment: ₹" + r.payment);
        }
    }

    static void saveReservations() {

        try {

            FileWriter fw = new FileWriter("reservations.txt");

            for (Reservation r : reservations)
                fw.write(r.toString() + "\n");

            fw.close();

        } catch (Exception e) {
        }
    }

    static void loadReservations() {

        try {

            File file = new File("reservations.txt");

            if (!file.exists())
                return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String data[] = fileScanner.nextLine().split(",");

                reservations.add(new Reservation(
                        data[0],
                        Integer.parseInt(data[1]),
                        data[2],
                        Double.parseDouble(data[3])
                ));

                for (Room r : rooms)
                    if (r.roomNumber == Integer.parseInt(data[1]))
                        r.isAvailable = false;
            }

        } catch (Exception e) {
        }
    }
}
