import java.util.Scanner;

public class HotelApplication {

    // Choice of menus using try-catch block
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice1 = "";
        do {
            System.out.println("1. Customer Menu\n" +
                    "2. Admin Menu\n");
            try {
                choice1 = String.valueOf(scanner.nextLine());
                if (choice1.equals("1")) {
                    MainMenu.mainMenu();
                } else if (choice1.equals("2")) {
                    AdminMenu.adminMenu();
                }else{
                    System.out.println("Invalid input");
                }
            }catch (NumberFormatException ex) {
                System.out.println("\nInvalid Input\n");
            }
        }
        while (!choice1.equals("1") && !choice1.equals("2"));
    }
}