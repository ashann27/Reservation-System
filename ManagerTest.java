import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ManagerTest {

    @Test
    void createAccount() {//tests an account can be created
        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);


        // check to see if the account list is empty or not.
        if (countAccounts == 0) {
            manager.readInAccountListInfo(documents);
        }

        if (countAccounts == 0) {
           manager.createAccount(documents);
        }
        manager.createAccount(documents);
        File countAccountList2[] = directory.listFiles();
        int countAccounts2 = (countAccountList2.length);

        Assert.assertTrue(countAccounts2==countAccounts + 1);


    }

    @Test
    void readInAccountListInfo() {
        //check to see if the length of the resulting accountlist is the same as the length of the account directory
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        manager.readInAccountListInfo(documents);

        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();

        //+1 for the dummy account that proves the directory is being populated
        Assert.assertEquals(manager.accountList.size()+1, countAccountList.length);
    }

    @Test
    void makeReservation() throws IOException {
        //tests that each of the reservation types can be made
        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);


        // check to see if the account list is empty or not.
        if (countAccounts == 0) {
            manager.readInAccountListInfo(documents);
        }

        if (countAccounts == 0) {
            manager.createAccount(documents);
        }


        System.out.print("To prepare this test please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();
        scan.nextLine();
        File directory2 = new File(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber);
        File countResListBefore[] = directory2.listFiles();
       int countResListBeforeInt = countResListBefore.length;
       countResListBeforeInt = countResListBeforeInt+1;

        manager.makeReservation(documents);

        File countResListAfter[] = directory2.listFiles();

        Assert.assertEquals(countResListBeforeInt,countResListAfter.length);
    }

    @Test
    void updateResv() throws IOException {
        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);
        // check to see if the acctList is empty or not.
        if (countAccountList.length == 0) {
            readInAccountListInfo();
        }

        if (countAccountList.length == 0)
            createAccount();

        System.out.print("To test this method, please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();

        System.out.println("Next, we'll need the reservation number. Remember to include the first 3 characters. ex: HOU for house");
        String reservationNumber = scan.nextLine();
        String reservationType = reservationNumber.substring(0, 3);
        reservationNumber = String.format("%010d", (Integer.parseInt(reservationNumber.substring(3))));
        reservationNumber = reservationType + reservationNumber.toUpperCase();

        System.out.println("Great! Let's begin the test");


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

        manager.updateResv(documents);

        ArrayList<String> reservationBeingRead2 = new ArrayList<String>();
        FileReader fr2 = new FileReader(documents + "\\Reservation System\\Reservations\\acc-" + accountNumber + "\\res-" + reservationNumber + ".txt");

        BufferedReader in2 = new BufferedReader(fr2);
        try {
            String currentLine;

            while ((currentLine = in2.readLine()) != null) {
                reservationBeingRead.add(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(reservationBeingRead ==reservationBeingRead2);

    }

    @Test
    void deleteReservation() throws IOException {
        //check if the reservation is deleted
        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);
        // check to see if the acctList is empty or not.
        if (countAccountList.length == 0) {
            manager.readInAccountListInfo(documents);
        }

        if (countAccountList.length == 0)
            manager.createAccount(documents);

        System.out.print("To test this method, please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
        String accountNumber = scan.next();
        scan.nextLine();
        accountNumber = String.format("%09d", (Integer.parseInt(accountNumber)));
        accountNumber = "A" + accountNumber.toUpperCase();

        System.out.println("Next, we'll need the reservation number. Remember to include the first 3 characters. ex: HOU for house");
        String reservationNumber = scan.nextLine();
        String reservationType = reservationNumber.substring(0, 3);
        reservationNumber = String.format("%010d", (Integer.parseInt(reservationNumber.substring(3))));
        reservationNumber = reservationType.toUpperCase() + reservationNumber;

        System.out.println("Great! Let's begin the test");

        manager.deleteReservation(documents);
        boolean reservationExists = new File(documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt").isFile();
        Assert.assertFalse(reservationExists);


    }

    @Test
    void updateAccount() throws IOException {
        Scanner scan = new Scanner(System.in);
        Manager manager = new Manager();
        String documents = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        File directory = new File(documents + "\\Reservation System\\Accounts\\");
        File countAccountList[] = directory.listFiles();
        int countAccounts = (countAccountList.length);
        // check to see if the acctList is empty or not.
        if (countAccountList.length == 0) {
            readInAccountListInfo();
        }

        if (countAccountList.length == 0)
            createAccount();

        System.out.print("To test this method, please enter your account number, you may ignore leading zeros EX: A9 not A000000009 " + "\n" + "\n A");
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

        manager.updateAccount(documents);

        ArrayList<String> accountBeingRead2 = new ArrayList<String>();
        FileReader fr2 = new FileReader(documents + "\\Reservation System\\Accounts\\acc-" + accountNumber + ".txt");

        BufferedReader in2 = new BufferedReader(fr2);
        try {
            String currentLine;

            while ((currentLine = in2.readLine()) != null) {
                accountBeingRead2.add(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertFalse(accountBeingRead ==accountBeingRead2);
    }
}