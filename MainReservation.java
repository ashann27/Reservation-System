import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MainReservation {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        Boolean locationSet = false;
        String documents = "";


        do {
            documents = setLocation(documents, locationSet);
            locationSet=true;
            displayMenu();

            int choice = scan.nextInt();
            switch (choice) {
                case 1: // create a new account
                    manager.createAccount(documents);
                    break;

                case 2:  // read in files from existing account user
                    manager.readInAccountListInfo(documents);
                    break;

                case 3:  // make a new reservation
                    manager.makeReservation(documents);
                    break;

                case 4:  // modify an existing reservation
                    manager.updateResv(documents);
                    break;

                case 5:  // delete a new reservation
                    manager.deleteReservation(documents);
                    break;

                case 6:  // delete a new reservation
                    manager.updateAccount(documents);
                    break;

                case 9:
                    scan.close();
                    System.exit(0);
                    break;
            }
        } while (true);

    }


    //=============================================================
    // Display Menu
    //=============================================================
//=============================================================
    // Display Menu
    //=============================================================
    public static void displayMenu() {
        System.out.println("\nWelcome to Rental Reservation System for Hotel, House, and Cabin! ");
        System.out.println("1. Create an Account for New User");
        System.out.println("2. Enter Account Information for Returned User");
        System.out.println("3. Create a Reservation (must already have an Account");
        System.out.println("4. Modify a Reservation (must already have a completed Reservation)");
        System.out.println("5. Cancel a Reservation (must already have a completed Reservation)");
        System.out.println("6. Display all Reservation (must already have a completed Reservation)");
        System.out.println("7. Update an account (must already have a an account)");


        System.out.println("9. Exit .... ");
    }

    public static String setLocation(String documents, boolean entry) {

        while (entry == false) {
            // create a new account directory
            System.out.println("Please select your file location" +
                    "\n" + "A: In the default folder (my Documents folder)" +
                    "\n" + "B: In a custom location" +
                    "\n");
            String pathChoice = Manager.scan.nextLine();
            pathChoice = pathChoice.toUpperCase();


            if (pathChoice.equals("A")) {
                documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
                new File(documents + "\\Reservation System\\Accounts\\").mkdirs();
                entry = true;

                try {
                    FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Accounts\\dummy.txt");
                    myWriter.write("Account Number: " +
                            "\n" + "00" +
                            "\n" + "Mailing Address: " +
                            "\n" + "00" +
                            "\n" + "Phone Number: " +
                            "\n" + "00" +
                            "\n" + "Email Address: " +
                            "\n" + "00");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("Error: Couldn't find your My Documents folder");
                    entry = false;


                }

            } else if (pathChoice.equals("B")) {
                System.out.println("Please enter your desired path");
                String userInput = Manager.scan.nextLine();
                Path documentPath = Paths.get(userInput);
                if (Files.exists(documentPath)) {

                    documents = userInput;
                    new File(documents + "\\Reservation System\\Accounts\\").mkdirs();
                    entry = true;
                    try {
                        FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Accounts\\" + "dummy.txt");
                        myWriter.write("Account Number: " +
                                "\n" + "00" +
                                "\n" + "Mailing Address: " +
                                "\n" + "00" +
                                "\n" + "Phone Number: " +
                                "\n" + "00" +
                                "\n" + "Email Address: " +
                                "\n" + "00");
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("Invalid input, please try again");
                        entry = false;


                    }


                } else {
                    documents = "Invalid input, please try again";
                    System.out.println(documents);
                    entry = false;
                }

            }

        }
        return documents;
    }
}
