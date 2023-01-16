import api.AdminResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.enums.RoomType;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author joseneto
 *
 */
public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getSingleton();

    public static void adminMenu() {
        String line = "";
        final Scanner scanner = new Scanner(System.in);

        printMenu();

        try {
            do {
                line = scanner.nextLine();

                if (line.length() == 1) {
                    switch (line.charAt(0)) {
                        case '1':
                            displayAllCustomers();
                            break;
                        case '2':
                            displayAllRooms();
                            break;
                        case '3':
                            displayAllReservations();
                            break;
                        case '4':
                            addRoom();
                            break;
                        case '5':
                            MainMenu.printMainMenu();
                            break;
                        default:
                            System.out.println("Inputan tidak dikenal\n");
                            break;
                    }
                } else {
                    System.out.println("Error: Inputan Salah\n");
                }
            } while (line.charAt(0) != '5' || line.length() != 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Inputan yang anda cari tidak ada. menutup program...");
        }
    }

    private static void printMenu() {
        System.out.print("\nAdmin Menu\n" +
                "--------------------------------------------\n" +
                "1. Lihat semua Customer\n" +
                "2. Lihat semua kamar\n" +
                "3. Lihat semua pesanan\n" +
                "4. Tambahkan kamar\n" +
                "5. Kembali ke menu utama\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

    private static void addRoom() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan nomor Kamar:");
        final String roomNumber = scanner.nextLine();

        System.out.println("Masukkan harga per malam");
        final double roomPrice = enterRoomPrice(scanner);

        System.out.println("Masukkan tipe Kamar: 1 for single bed, 2 for double bed:");
        final RoomType roomType = enterRoomType(scanner);

        final Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Kamar berhasil ditambahkan!");

        System.out.println("Apakah ingin menambahkan kamar lagi? Y/N");
        addAnotherRoom();
    }

    private static double enterRoomPrice(final Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exp) {
            System.out.println("harga kamar salah! Please, Masukkan angka yang benar. " +
                    "desimal harus menggunakan (.)");
            return enterRoomPrice(scanner);
        }
    }

    private static RoomType enterRoomType(final Scanner scanner) {
        try {
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException exp) {
            System.out.println("Kamar salah! Please, pilih 1 for single bed atau 2 for double bed:");
            return enterRoomType(scanner);
        }
    }

    private static void addAnotherRoom() {
        final Scanner scanner = new Scanner(System.in);

        try {
            String anotherRoom;

            anotherRoom = scanner.nextLine();

            while ((anotherRoom.charAt(0) != 'Y' && anotherRoom.charAt(0) != 'N')
                    || anotherRoom.length() != 1) {
                System.out.println("Masukkan Y (Yes) or N (No)");
                anotherRoom = scanner.nextLine();
            }

            if (anotherRoom.charAt(0) == 'Y') {
                addRoom();
            } else if (anotherRoom.charAt(0) == 'N') {
                printMenu();
            } else {
                addAnotherRoom();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom();
        }
    }

    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if(rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            adminResource.getAllRooms().forEach(System.out::println);
        }
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("Kamar tidak ditemukan.");
        } else {
            adminResource.getAllCustomers().forEach(System.out::println);
        }
    }

    private static void displayAllReservations() {
        adminResource.displayAllReservations();
    }
}
