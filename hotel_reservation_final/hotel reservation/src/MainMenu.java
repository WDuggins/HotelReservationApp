import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    static final HotelResource hotelResource = HotelResource.getInstance();
    static final AdminResource adminResource = AdminResource.getInstance();

    // Date pattern to be used
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    // Switch statement of main choices
    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        do {
            try {
                System.out.println("1. Find and reserve a room\n" +
                        "2. See my reservations\n" +
                        "3. Create an account\n" +
                        "4. Admin Menu\n" +
                        "5. Exit");
                choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        findAndReserveARoom();
                        break;
                    case "2":
                        seeMyReservations();
                        break;
                    case "3":
                        createAnAccount();
                        break;
                    case "4":
                        AdminMenu.adminMenu();
                        break;
                    case "5":
                        // Exits application
                        System.exit(0);
                    default:
                        System.out.println("Please choose a valid option.");
                }
            } catch (NoSuchElementException exception) {
                System.out.println("Invalid input");
            } catch (Exception e) {
                System.out.println("Unknown error");
            }
        } while (!choice.equals("4") && !choice.equals("5"));
    }

    // Creates a new customer account based on scanner input
    public static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email address. Format example: someone@email.com");
        String email = scanner.nextLine();
        if (Customer.validE(email)) {
            if (adminResource.getAllCustomers().containsKey(email)) {
                System.out.println("This account already exists.");
            } else {
                System.out.println("Enter first name.");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name.");
                String lastName = scanner.nextLine();
                hotelResource.createACustomer(email, firstName, lastName);
                if (adminResource.getAllCustomers().containsKey(email)) {
                    System.out.println("Customer added");
                    mainMenu();
                }
            }
        }
    }

    // Finds available rooms and uses input to schedule a reservation
    private static void findAndReserveARoom() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println("If you have an account, please enter your email. Format example: someone@email.com");
        String email = scanner.nextLine();
        if (!adminResource.getAllCustomers().containsKey(email)) {
            System.out.println("Please create an account.");
            createAnAccount();
        }
        Customer customer = hotelResource.getCustomer(email);
        if (!(customer == null)) {
            for (Reservation reservation : adminResource.displayAllReservations()) {
                if (reservation.getCustomer().equals(customer)) {
                    System.out.println("You already have reservations.");
                    seeMyReservations();
                    System.out.println("Do you want to create another reservation? yes or no");
                    String anotherRes = scanner.nextLine();
                    if (anotherRes.equalsIgnoreCase("no")) {
                        System.out.println("Ok, have a great day!");
                        MainMenu.mainMenu();
                    } else if (!anotherRes.equalsIgnoreCase("yes")) {
                        System.out.println("Please enter yes or no.");
                    }
                }
            }
            System.out.println("Enter check-in date in MM/DD/YYYY format.");
            Date checkInDate = format(scanner);
            System.out.println("Enter check-out date in MM/DD/YYYY format.");
            Date checkOutDate = format(scanner);
            if (checkInDate != null && checkOutDate != null) {
                Collection<IRoom> available = hotelResource.findARoom(checkInDate, checkOutDate);
                if(!(checkInDate.before(new Date()) || checkOutDate.before(new Date())
                        || checkInDate.equals(checkOutDate) || checkInDate.after(checkOutDate))){
                    if (available.isEmpty()) {
                        System.out.println("No rooms available for those dates.");
                        Collection<IRoom> altRooms = hotelResource.altRooms(checkInDate, checkOutDate);
                        if (altRooms(checkInDate, checkOutDate).isEmpty()){
                            System.out.println("Sorry, no alternatives are available either.");
                        }else{
                            final Date altCheckIn = hotelResource.addDays(checkInDate);
                            final Date altCheckOut = hotelResource.addDays(checkOutDate);
                            System.out.println("We do have these alternative dates available:\n" +
                                                altCheckIn + " - " + altCheckOut);
                            System.out.print(altRooms);
                            System.out.println("\nWhich room number would you like to reserve?");
                            String roomId = scanner.nextLine();
                            IRoom room = hotelResource.getRoom(roomId);
                            if (altRooms.contains(room)) {
                                hotelResource.bookARoom(customer, room, altCheckIn, altCheckOut);
                            } else {
                                System.out.println("Please enter an available room number.");
                            }
                        }
                    } else {
                        System.out.print(available);
                        System.out.println("\nWhich room number would you like to reserve?");
                        String roomId = scanner.nextLine();
                        IRoom room = hotelResource.getRoom(roomId);
                        if (available.contains(room)) {
                            hotelResource.bookARoom(customer, room, checkInDate, checkOutDate);
                        } else {
                            System.out.println("Please enter an available room number.");
                        }
                    }
                }
            }
        }
    }


    // Parses date format to scanner input
    private static Date format(Scanner scanner) {
        do {
            try {
                return dateFormat.parse(scanner.nextLine());
            } catch (ParseException ex) {
                System.out.println("Please use correct date format.");
            }
        } while (true);
    }

    public static Collection<IRoom> altRooms(Date checkInDate, Date checkOutDate) {
        return hotelResource.altRooms(checkInDate, checkOutDate);
    }

    // Retrieves an individual's previously scheduled reservations at the hotel and enables cancellation
    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter customer's email address. Format example: someone@email.com");
        String email = scanner.nextLine();
        if (Customer.validE(email)) {
            Customer customer = hotelResource.getCustomer(email);
            for (Reservation r : adminResource.displayAllReservations()) {
                if (r.getCustomer().equals(customer)) {
                    hotelResource.getCustomersReservations(email);
                    System.out.println("Do you want to cancel your reservation? yes or no");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        adminResource.displayAllReservations().removeIf(reservation -> reservation.getCustomer().equals(customer));
                        System.out.println("Your reservation has been cancelled.");
                    } else if (response.equalsIgnoreCase("no")) {
                        System.out.println("Very well. We look forward to seeing you.");
                    } else {
                        System.out.println("Invalid input. Please enter yes or no.");
                    }
                }
            }
        }
    }
}