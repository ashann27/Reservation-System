import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Manager {
    static Scanner scan = new Scanner(System.in);
    ArrayList<Account> accountList = new ArrayList<>();

    public Manager() {
    }

    //////////////////////////////////////////////
    ///////////Choice 1: Create an account////////
    //////////////////////////////////////////////
    public void createAccount(String documents) {
        Account accountDefault = new Account();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();

        //All account and reservation numbers will be formatted to 10 and 9 characters, respectively
        String tempAccountNumber = String.format("%09d", (countAccountList.length));

        System.out.println("Your account number is: A" + tempAccountNumber);
        accountDefault.setAccountNumber("A" + tempAccountNumber);
        String newAccountNumber = accountDefault.accountNumber;

        while (accountDefault.mailingAddress==null || accountDefault.mailingAddress.isEmpty()) {
            System.out.println("Please enter account address. Empty addresses aren't accepted");
            accountDefault.setMailingAddress(scan.nextLine());
        }

        System.out.print("Please enter a 10 digit phone number: ");
        accountDefault.setPhoneNumber(String.valueOf(scan.nextLine()));
        System.out.print("Please enter your email address: ");
        accountDefault.setEmailAddress(scan.nextLine());

        Account newAccount = new Account(accountDefault.accountNumber, accountDefault.mailingAddress, accountDefault.phoneNumber, accountDefault.emailAddress);
        // add a new account to ArrayList acct
        accountList.add(newAccount);

        //Create a new account
        try {
            FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Accounts\\" + "acc-" + newAccountNumber + ".txt");
            myWriter.write("Account Number: " +
                    "\n" + newAccountNumber +
                    "\n" + "Mailing Address: " +
                    "\n" + accountDefault.mailingAddress + "\n--" +
                    "\n" + "Phone Number: " +
                    "\n" + accountDefault.phoneNumber + "\n--" +
                    "\n" + "Email Address: " +
                    "\n" + accountDefault.emailAddress + "\n--" +
                    "\n--" + "\n" + "Reservations: \n");
            myWriter.close();
            System.out.println("Account created, welcome!");
        } catch (IOException e) {
            System.out.println("Couldn't write to the file");
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////
    ///////////Choice 2: Load account data////////
    //////////////////////////////////////////////
    public void readInAccountListInfo(String documents) {
        String userPath = documents + "\\Reservation System\\Accounts\\dummy.txt";
        Path path = Paths.get(userPath);
        String accountNumber;
        String mailingAddress;
        String phoneNumber;
        String email;

        //check if dummy account exists, the dummy account proves the existence of a previous registration
        if (Files.exists(path)) {
            //create dummy account in array so dummy isn't included in the total number of accounts
            Account dummyAccount = new Account();
            accountList.add(dummyAccount);

            //ignores dummy account in folder so it doesn't interfere with account count
            File directory = new File(documents + "\\Reservation System\\Accounts\\");
            File countAccountList[] = directory.listFiles();
            int countAccounts = (countAccountList.length - 1);
            try {
                //loops through every account notepad doc in directory and populates List
                for (int i = 1; i < (countAccounts); i++) {
                    ArrayList<String> accountBeingRead = new ArrayList<String>();
                    String formattedCounter = String.format("%09d", (i));
                    FileReader fr = new FileReader(documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt");
                    BufferedReader in = new BufferedReader(fr);
                    try {
                        String currentLine;

                        while ((currentLine = in.readLine()) != null) {
                            accountBeingRead.add(currentLine);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //adds account info to account list
                    Account readDefaultAccount = new Account();
                    readDefaultAccount.setAccountNumber(readHelper(accountBeingRead.indexOf("Account Number: "), documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt"));
                    readDefaultAccount.setMailingAddress(readHelper(accountBeingRead.indexOf("Mailing Address: "), documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt"));
                    readDefaultAccount.setPhoneNumber(readHelper(accountBeingRead.indexOf("Phone Number: "), documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt"));
                    readDefaultAccount.setEmailAddress(readHelper(accountBeingRead.indexOf("Email Address: "), documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt"));


                    //adds reservation info to reservations list
                    int indexOfReservationsLabel = accountBeingRead.indexOf("Reservations: ");
                    //creates a list of of all reservations for an account
                    ArrayList<String> reservationList = new ArrayList<String>();

                    //creates the path to the user's file
                    String accountPathString = documents + "\\Reservation System\\Accounts\\acc-A" + formattedCounter + ".txt";
                    readReservationHelper(reservationList, accountPathString, indexOfReservationsLabel);

                    //goes through each reservation, determines the type of reservation, then adds it to the array
                    for (String reservationNumber : reservationList) {
                        //reads the first 3 characters of the reservation and creates the appropriate reservation type
                        if (reservationNumber.contains("CAB")) {
                            CabinReservation cabinReservationDefault = new CabinReservation();
                            readDefaultAccount.reservation.add(cabinReservationDefault);
                        } else if (reservationNumber.contains("HOT")) {
                            HotelReservation hotelReservationDefault = new HotelReservation();
                            readDefaultAccount.reservation.add(hotelReservationDefault);
                        } else if (reservationNumber.contains("HOU")) {
                            HouseReservation houseReservationDefault = new HouseReservation();
                            readDefaultAccount.reservation.add(houseReservationDefault);
                        }
                    }
                    accountList.add(readDefaultAccount);
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("File Read Error");
            }

        } else {
            System.out.println("It looks like you haven't chosen a directory!" + "\n");
            boolean entry = false;
            MainReservation.setLocation(documents, entry);
        }
    }

    //////////////////////////////////////////////
    ///////////Choice 3: Make a reservation////////
    //////////////////////////////////////////////
    public void makeReservation(String documents) throws IOException {
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);
        String booleanAnswer;

        boolean found = false;
        // check to see if the account list is empty or not.
        if (countAccounts == 0) {
            readInAccountListInfo(documents);
        }

        if (countAccounts == 0) {
            createAccount(documents);
        }

        System.out.print("Please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();

        File f = new File(documents + "\\Reservation System\\Accounts\\" + "acc-" + accountNumber + ".txt");
        if (f.exists() && !f.isDirectory()) {
            found = true;
            System.out.print("Let's make a new reservation! What Kind of reservation would you like to make?\n" +
                    "For a Cabin reservation enter 'Cabin'\n" +
                    "For a Hotel reservation enter 'Hotel'\n" +
                    "For a House reservation enter 'House'\n" +
                    "(not case sensitive)"
            );

            String reservationType = scan.nextLine();
            reservationType = reservationType.toUpperCase();

            //creates appropriate directory if it doesn't already exist
            new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + accountNumber).mkdirs();

            //Creates a temporary list of reservations for reservation numbering
            File reservationDirectory = new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + accountNumber);
            File countReservationList[] = reservationDirectory.listFiles();
            Arrays.sort(countReservationList);
            String newReservationNumberString = "";

            if (countReservationList.length < 1) {
                newReservationNumberString = "0";
            } else {
                newReservationNumberString = countReservationList[countReservationList.length - 1].getName().substring(7, 17);
            }

            //convert to int and add 1, zero leads to a null pointer error
            int newReservationNumberInt = ((Integer.parseInt(newReservationNumberString)) + 1);
            newReservationNumberString = String.format("%010d", (newReservationNumberInt));

            if (reservationType.equals("CABIN")) {
                //creates a cabin reservation
                CabinReservation cabinReservationDefault = new CabinReservation();
                cabinReservationDefault.accountNumber = accountNumber;
                String newReservationNumber = "CAB" + (newReservationNumberString);
                System.out.println("Okay, let's get your Cabin Reservation started");

                System.out.print("What's the cabin's physical address?");
                cabinReservationDefault.setPhysicalAddress(scan.nextLine());

                System.out.print("What's the cabin's mailing address?");
                cabinReservationDefault.setMailingAddress(scan.nextLine());

                System.out.print("When is the reservation start date?");
                cabinReservationDefault.setReservationStartDate(scan.nextLine());

                System.out.print("How many nights will you be staying? (use numbers eg: 123, not one two three)");
                cabinReservationDefault.setNumberOfNights(Integer.parseInt(scan.nextLine()));

                System.out.print("How many beds will there be? (use numbers eg: 123, not one two three)");
                cabinReservationDefault.setNumberOfBeds(Integer.parseInt(scan.nextLine()));

                System.out.print("How many bedrooms will there be? (use numbers eg: 123, not one two three)");
                cabinReservationDefault.setNumberOfBedrooms(Integer.parseInt(scan.nextLine()));

                System.out.print("How many bathrooms will there be? (use numbers eg: 123, not one two three)");
                cabinReservationDefault.setNumberOfBathrooms(Integer.parseInt(scan.nextLine()));

                System.out.print("In square feet, how large will the lodging be? (use numbers eg: 123, not one two three)");
                cabinReservationDefault.setLodgingSize(Integer.parseInt(scan.nextLine()));

                System.out.print("Is there a loft present? Press 'y' for yes and 'n' for no.");
                booleanAnswer = scan.nextLine();
                cabinReservationDefault.setLoftPresent(booleanHelper(booleanAnswer));

                System.out.print("Is there a Full Kitchen present? Press 'y' for yes and 'n' for no.");
                booleanAnswer = scan.nextLine();
                cabinReservationDefault.setFullKitchenPresent(booleanHelper(booleanAnswer));

                cabinReservationDefault.changePrice();
                System.out.println("Here's the price of your reservation: $" + cabinReservationDefault.priceOfReservation);
                cabinReservationDefault.setReservationStatus("Complete");
                cabinReservationDefault.toString(documents, newReservationNumber, cabinReservationDefault);

            } else if (reservationType.equals("HOTEL")) {

                //creates a hotel reservation
                HotelReservation hotelReservationDefault = new HotelReservation();
                hotelReservationDefault.accountNumber = accountNumber;
                String newReservationNumber = "HOT" + (newReservationNumberString);
                System.out.println("Okay, let's get your Hotel Reservation started");

                System.out.print("What's the hotel's physical address?");
                hotelReservationDefault.setPhysicalAddress(scan.nextLine());

                System.out.print("What's the hotel's mailing address?");
                hotelReservationDefault.setMailingAddress(scan.nextLine());

                System.out.print("When is the reservation start date?");
                hotelReservationDefault.setReservationStartDate(scan.nextLine());

                System.out.print("How many nights will you be staying? (use numbers eg: 123, not one two three)");
                hotelReservationDefault.setNumberOfNights(Integer.parseInt(scan.nextLine()));

                hotelReservationDefault.setNumberOfBeds(2);

                hotelReservationDefault.setNumberOfBedrooms(1);

                hotelReservationDefault.setNumberOfBathrooms(1);

                System.out.print("In square feet, how large will the lodging be? (use numbers eg: 123, not one two three)");
                hotelReservationDefault.setLodgingSize(Integer.parseInt(scan.nextLine()));

                System.out.print("Is there a kitchenette present? Press 'y' for yes and 'n' for no.");
                booleanAnswer = scan.nextLine();
                hotelReservationDefault.setKitchenettePresent(booleanHelper(booleanAnswer));

                hotelReservationDefault.changePrice();
                System.out.println("Here's the price of your reservation: $" + hotelReservationDefault.getPriceOfReservation());

                hotelReservationDefault.setReservationStatus("Complete");
                hotelReservationDefault.toString(documents, newReservationNumber, hotelReservationDefault);

            } else if (reservationType.equals("HOUSE")) {

                //creates a house reservation
                HouseReservation houseReservationDefault = new HouseReservation();
                houseReservationDefault.accountNumber = accountNumber;
                String newReservationNumber = "HOU" + (newReservationNumberString);
                System.out.println("Okay, let's get your House Reservation started");

                System.out.print("What's the house's physical address?");
                houseReservationDefault.setPhysicalAddress(scan.nextLine());

                System.out.print("What's the house's mailing address?");
                houseReservationDefault.setMailingAddress(scan.nextLine());

                System.out.print("When is the reservation start date?");
                houseReservationDefault.setReservationStartDate(scan.nextLine());

                System.out.print("How many nights will you be staying? (use numbers eg: 123, not one two three)");
                houseReservationDefault.setNumberOfNights(Integer.parseInt(scan.nextLine()));

                System.out.print("How many beds will there be? (use numbers eg: 123, not one two three)");
                houseReservationDefault.setNumberOfBeds(Integer.parseInt(scan.nextLine()));

                System.out.print("How many bedrooms will there be? (use numbers eg: 123, not one two three)");
                houseReservationDefault.setNumberOfBedrooms(Integer.parseInt(scan.nextLine()));

                System.out.print("How many bathrooms will there be? (use numbers eg: 123, not one two three)");
                houseReservationDefault.setNumberOfBathrooms(Integer.parseInt(scan.nextLine()));

                System.out.print("In square feet, how large will the lodging be? (use numbers eg: 123, not one two three)");
                houseReservationDefault.setLodgingSize(Integer.parseInt(scan.nextLine()));

                System.out.print("How many floors are there?");
                houseReservationDefault.setNumberOfFloors(Integer.parseInt(scan.nextLine()));

                houseReservationDefault.changePrice();
                System.out.println("Here's the price of your reservation: $" + houseReservationDefault.getPriceOfReservation());

                houseReservationDefault.setReservationStatus("Complete");
                houseReservationDefault.toString(documents, newReservationNumber, houseReservationDefault);
                System.out.println("Reservation created, have fun!");

            }

        } else {
            System.out.println("That account number doesn't exist, please try again or Press 'Q' to return to the main menu.");

            System.out.println("Please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
            String answer = scan.next();
            scan.nextLine();
            accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
            accountNumber = "A" + accountNumber.toUpperCase();

            if (answer.toUpperCase() == "Q") {
                return;
            } else {
                makeReservation(documents);
            }
        }
    }

    //////////////////////////////////////////////
    ///////////Update a reservation///////////////
    //////////////////////////////////////////////

    public void updateResv(String documents) throws IOException {
        boolean found = false;
        // check to see if the acctList is empty or not.
        if (accountList.size() == 0) {
            readInAccountListInfo(documents);
        }

        if (accountList.size() == 0)
            createAccount(documents);

        System.out.print("Please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();
        System.out.println("What's the reservation number? Remember to include the first 3 characters. ex: HOU for house");
        String reservationNumber = scan.nextLine();
        String reservationType = reservationNumber.substring(0, 3);
        reservationNumber = String.format("%010d", (Integer.parseInt(reservationNumber.substring(3))));
        reservationNumber = reservationType.toUpperCase() + reservationNumber;

        if (reservationNumber.contains("CAB")) {
            System.out.println("Looks like you're updating a Cabin reservation! What would you like to change?\n" +

                    "To change your physical address press '1'\n" +
                    "To change your mailing address press '2'\n" +
                    "To change the reservation start date press '3'\n" +
                    "To change the number of nights press '4'\n" +
                    "To change the number of beds press '5'\n" +
                    "To change the number of bedrooms press '6'\n" +
                    "To change the number of bathrooms press '7'\n" +
                    "To change the lodging size press '8'\n" +
                    "To change whether there's a full kitchen or not press '9'\n" +
                    "To change whether there's a loft or not press '0'\n");

            String userChoice = scan.nextLine();
            String stringArray[] = new String[15];
            stringArray[1] = "Physical Address";
            stringArray[2] = "Mailing Address";
            stringArray[3] = "Reservation Start Date";
            stringArray[4] = "Number of Nights";
            stringArray[5] = "Number of Beds";
            stringArray[6] = "Number of Bedrooms";
            stringArray[7] = "Number of Bathrooms";
            stringArray[8] = "Lodging Size";
            stringArray[9] = "Full Kitchen Present";
            stringArray[0] = "Loft Present";

            String actualAnswer = stringArray[(Integer.parseInt(userChoice))];

            System.out.println("What would you like to change it to? " +
                    "Hint: for 1-2 you may use letters, for 3-8 you may use numbers, and for 9 and 0 you may use 'true' and 'false'");
            String newAttributeValue = scan.nextLine();

            ArrayList<String> reservationBeingRead = new ArrayList<String>();
            FileReader fr = new FileReader(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");

            BufferedReader in = new BufferedReader(fr);
            try {
                String currentLine;

                while ((currentLine = in.readLine()) != null) {
                    reservationBeingRead.add(currentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            updateHelper(reservationBeingRead.indexOf(actualAnswer), newAttributeValue, documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");
        } else if (reservationNumber.contains("HOT")) {
            System.out.println("Looks like you're update a Hotel reservation! What would you like to change?\n" +

                    "To change your physical address press '1'\n" +
                    "To change your mailing address press '2'\n" +
                    "To change the reservation start date press '3'\n" +
                    "To change the number of nights press '4'\n" +
                    "To change the number of beds press '5'\n" +
                    "To change the number of bedrooms press '6'\n" +
                    "To change the number of bathrooms press '7'\n" +
                    "To change the lodging size press '8'\n" +
                    "To change whether there's a kitchenette or not press '9'\n");

            String userChoice = scan.nextLine();
            String stringArray[] = new String[15];
            stringArray[1] = "Physical Address";
            stringArray[2] = "Mailing Address";
            stringArray[3] = "Reservation Start Date";
            stringArray[4] = "Number of Nights";
            stringArray[5] = "Number of Beds";
            stringArray[6] = "Number of Bedrooms";
            stringArray[7] = "Number of Bathrooms";
            stringArray[8] = "Lodging Size";
            stringArray[9] = "Kitchenette Present";

            String actualAnswer = stringArray[(Integer.parseInt(userChoice))];

            System.out.println("What would you like to change it to? " +
                    "Hint: for 1-2 you may use letters, for 3-8 you may use numbers, and for 9 and 0 you may use 'true' and 'false'");
            String newAttributeValue = scan.nextLine();

            ArrayList<String> reservationBeingRead = new ArrayList<String>();
            FileReader fr = new FileReader(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");

            BufferedReader in = new BufferedReader(fr);
            try {
                String currentLine;

                while ((currentLine = in.readLine()) != null) {
                    reservationBeingRead.add(currentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            updateHelper(reservationBeingRead.indexOf(actualAnswer), newAttributeValue, documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");
        } else if (reservationNumber.contains("HOU")) {
            System.out.println("Looks like you're updating a House reservation! What would you like to change?\n" +

                    "To change your physical address press '1'\n" +
                    "To change your mailing address press '2'\n" +
                    "To change the reservation start date press '3'\n" +
                    "To change the number of nights press '4'\n" +
                    "To change the number of beds press '5'\n" +
                    "To change the number of bedrooms press '6'\n" +
                    "To change the number of bathrooms press '7'\n" +
                    "To change the lodging size press '8'\n" +
                    "To change the number of floors press '9'\n");

            String userChoice = scan.nextLine();
            String stringArray[] = new String[15];
            stringArray[1] = "Physical Address";
            stringArray[2] = "Mailing Address";
            stringArray[3] = "Reservation Start Date";
            stringArray[4] = "Number of Nights";
            stringArray[5] = "Number of Beds";
            stringArray[6] = "Number of Bedrooms";
            stringArray[7] = "Number of Bathrooms";
            stringArray[8] = "Lodging Size";
            stringArray[9] = "Number of Floors";

            String actualAnswer = stringArray[(Integer.parseInt(userChoice))];

            System.out.println("What would you like to change it to? " +
                    "Hint: for 1-2 you may use letters, for 3-8 you may use numbers, and for 9 and 0 you may use 'true' and 'false'");
            String newAttributeValue = scan.nextLine();

            ArrayList<String> reservationBeingRead = new ArrayList<String>();
            FileReader fr = new FileReader(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");

            BufferedReader in = new BufferedReader(fr);
            try {
                String currentLine;

                while ((currentLine = in.readLine()) != null) {
                    reservationBeingRead.add(currentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            updateHelper(reservationBeingRead.indexOf(actualAnswer), newAttributeValue, documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");
        }

    }


    //////////////////////////////////////////////
    ///////////Delete a Reservation///////////////
    //////////////////////////////////////////////
    public void deleteReservation(String documents) throws IOException {
        boolean foundAccount = false;
        boolean foundReservation = false;

        // check to see if the acctList is empty or not.
        if (accountList.size() == 0) {
            readInAccountListInfo(documents);
        }

        if (accountList.size() == 0)
            createAccount(documents);

        System.out.print("Please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();

        ArrayList<String> accountBeingRead = new ArrayList<String>();
        FileReader fr = new FileReader(documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt");
        BufferedReader in = new BufferedReader(fr);
        try {
            String currentLine;

            while ((currentLine = in.readLine()) != null) {
                accountBeingRead.add(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String accountPathString = documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt";
        ArrayList<String> optionReservationList = new ArrayList<String>();
        readReservationHelper(optionReservationList, accountPathString, accountBeingRead.indexOf("Reservations: "));
        System.out.print("Here are your reservations, which would you like to delete? You may ignore leading zeros EX: HOU9 not HOU000000009");

        //reformats the user's input to match the system
        System.out.println(Arrays.toString(optionReservationList.toArray()));
        String reservationToDelete = scan.nextLine();
        String reservationTypeToDelete = reservationToDelete.substring(0, 3);
        String reservationNumberToDelete = reservationToDelete.substring(3);
        reservationNumberToDelete = String.format("%010d", Integer.parseInt(reservationNumberToDelete));
        reservationToDelete = reservationTypeToDelete.toUpperCase() + reservationNumberToDelete;

        //at the index under the reservation to be cancelled, shift everything up
        Path path = Paths.get(accountPathString);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        int indexOfReservationToDelete = lines.indexOf(reservationToDelete);
        int wholeAccountFileSize = lines.size() - 1;
        int i = 0;
        //deletes the reservation number from the account's data
        FileWriter writer = new FileWriter(documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt", false);
        while (indexOfReservationToDelete <= wholeAccountFileSize) {

            if (indexOfReservationToDelete < wholeAccountFileSize) {
                lines.set(indexOfReservationToDelete, lines.get(indexOfReservationToDelete + 1));
                indexOfReservationToDelete++;
                continue;
            } else if (indexOfReservationToDelete == wholeAccountFileSize) {
                lines.remove(indexOfReservationToDelete);
                wholeAccountFileSize--;
            }

            try {
                for (int k = 0; k != wholeAccountFileSize + 1; k++) {
                    writer.write(lines.get(k) + "\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer.close();
            break;

        }

        Path reservationFileToDelete = Paths.get(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationToDelete + ".txt");
        Files.deleteIfExists(reservationFileToDelete);
    }

    //////////////////////////////////////////////
    ///////////Display a Reservation//////////////
    //////////////////////////////////////////////
    public void displayResv() {
        for (Account a : accountList) {
            System.out.print(a);
        }
    }

    //////////////////////////////////////////////
    ///////////Update an Account//////////////////
    //////////////////////////////////////////////

    public void updateAccount(String documents) throws IOException {
        boolean found = false;
        // check to see if the acctList is empty or not.
        if (accountList.size() == 0) {
            readInAccountListInfo(documents);
        }

        if (accountList.size() == 0)
            createAccount(documents);
        System.out.print("Please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();

        System.out.println("Let's update your account! What would you like to change?\n" +
                "To change your mailing address press '1'\n" +
                "To change your phone number press '2'\n" +
                "To change your email address press '3'\n");

        String userChoice = scan.nextLine();
        String stringArray[] = new String[4];
        stringArray[1] = "Mailing Address: ";
        stringArray[2] = "Phone Number: ";
        stringArray[3] = "Email Address: ";


        String actualAnswer = stringArray[(Integer.parseInt(userChoice))];

        System.out.println("What would you like to change it to? ");
        String newAttributeValue = scan.nextLine();

        ArrayList<String> accountBeingRead = new ArrayList<String>();
        FileReader fr = new FileReader(documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt");

        BufferedReader in = new BufferedReader(fr);
        try {
            String currentLine;

            while ((currentLine = in.readLine()) != null) {
                accountBeingRead.add(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateHelper(accountBeingRead.indexOf(actualAnswer), newAttributeValue, documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt");
    }

    //////////////////////////////////////////////
    //////////////////Methods////////////////////
    //////////////////////////////////////////////
    //reads user's y or n input then updates reservation or account
    static boolean booleanHelper(String userAnswer) {
        boolean validInput = false;
        boolean userAnswerBoolean = false;
        while (validInput == false) {
            userAnswer = userAnswer.toUpperCase();

            if (userAnswer.equals("Y")) {
                validInput = true;
                userAnswerBoolean = true;

            } else if (userAnswer.equals("N")) {
                validInput = true;
                userAnswerBoolean = false;

            } else {
                System.out.println("Invalid input, please try again");
                validInput = false;
                userAnswer = scan.nextLine();

            }
        }
        return userAnswerBoolean;
    }

    public static void updateHelper(int indexOfLabel, String updatedString, String reservationToUpdate) throws IOException {
        //accountToUpdate should be the reservation text file path
        //indexOfLabel is the index of the attribute being changed
        ///updatedString is the new information being added

        Path path = Paths.get(reservationToUpdate);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        //changes the line after lineNumber's string to the updated one
        lines.set(indexOfLabel + 1, updatedString);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public static String readHelper(int indexOfLabel, String accountToRead) throws IOException {
        //indexOfLabel is the index of the attribute being changed
        //accountToRead should be the account number

        Path path = Paths.get(accountToRead);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        //reads the line after the attribute name
        String attribute = lines.get(indexOfLabel + 1);
        return attribute;
    }

    //reads account's reservation numbers, returns a list of reservations
    //reservationList is the list of reservations that will be returned, accountPathString is the path to the account document,
    //IndexOfReservationsLabel is where the index label is in that specific document (just in case account document fields need to be moved)
    public static List readReservationHelper(List reservationList, String accountPathString, int indexOfReservationsLabel) throws IOException {

        //accountToRead should be the account number
        Path path = Paths.get(accountPathString);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        //reads the line after lineNumber's string
        int wholeAccountFileSize = lines.size();
        int i = 1;
        while ((indexOfReservationsLabel + i) < wholeAccountFileSize) {
            String reservation = lines.get(indexOfReservationsLabel + i);
            if (reservation == "null") {
                break;
            } else {
                reservationList.add(reservation);
                i++;
            }
        }
        return reservationList;
    }
}
