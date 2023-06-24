import api.AdminResource;

import model.Room;
import model.RoomType;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class AdminMenu {
    static final AdminResource adminResource = AdminResource.getInstance();

    // Switch statement of admin choices
    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        do {
            try {
                System.out.println("1. See all customers\n" +
                        "2. See all rooms\n" +
                        "3. See all reservations\n" +
                        "4. Add a room\n" +
                        "5. Back to Main Menu");
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println(adminResource.getAllCustomers());
                        break;
                    case "2":
                        System.out.println(adminResource.getAllRooms());
                        break;
                    case "3":
                        System.out.println(adminResource.displayAllReservations());
                        break;
                    case "4":
                        addRoom();
                        break;
                    case "5":
                        MainMenu.mainMenu();
                        break;
                    default:
                        System.out.println("Please choose a valid option.");
                }
            } catch (NoSuchElementException exception) {
                System.out.println("Invalid input");
            } catch (Exception e) {
                System.out.println("Unknown error");
            }
        } while (!choice.equals("5"));
    }
    public static void addRoom(){
        Scanner scanner2 = new Scanner(System.in);
        try {
            System.out.println("Enter roomNumber.");
            int number = scanner2.nextInt();
            String roomNumber = String.valueOf(number);
            System.out.println("Enter price per night.");
            Double price = scanner2.nextDouble();
            System.out.println("Room Type: Single or Double?");
            RoomType enumeration = RoomType.valueOf(scanner2.next().toUpperCase());
            final Room room = new Room(roomNumber, price, enumeration);
            adminResource.addRoom(room);
        }catch(NumberFormatException exception){
            System.out.println("Please enter a valid number on next try.");
        }catch(IllegalArgumentException ex){
            System.out.println("Please enter single or double.");
        }
    }
}