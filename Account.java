
import java.util.*;

/**
 *
 */
public class Account {

    /**
     * Unique number generated and provided
     * by UI (A followed by 9 characters)
     */
    protected String accountNumber = "-99";
    /**
     * Mailing address associated with the account.
     */
    protected String mailingAddress;
    /**
     * Phone number associated with the account.
     */
    protected int phoneNumber;
    /**
     * Email address associated with this account.
     */
    protected String emailAddress;
    protected String reservationList[];
    protected ArrayList<Reservation> reservation = new ArrayList<Reservation>();

    // first type of constructor
    public Account() {

    }

    /**
     * Account files name convention should be “acc-“ followed by the account number and the
     * appropriate file extension (html, txt, or json).
     * @param accountNumber
     * @param mailingAddress
     * @param phoneNumber
     * @param emailAddress
     */
    // 2nd constructor with more attributes and values
    public Account(String accountNumber, String mailingAddress, int phoneNumber, String emailAddress) {
        {
            try {
                this.accountNumber = accountNumber;
                this.mailingAddress = mailingAddress;
                this.phoneNumber = phoneNumber;
                this.emailAddress = emailAddress;
            }
            catch(Exception IllegalArgumentException) {
                System.out.print("Illegal Argument, your inputs aren't valid");
            }
        }
    }

    //3rd constructor
    public Account(String line) {
        mailingAddress = line.substring(line.indexOf("<mailingAddress>") + 16, line.indexOf("</mailingAddress>"));
        phoneNumber = Integer.parseInt(line.substring(line.indexOf(("<phoneNumber>") + 13, line.indexOf("</phoneNumber>"))));
        emailAddress = line.substring(line.indexOf("<emailAddress>") + 14, line.indexOf("</emailAddress>"));
        parseReservation(line);
        }

    //Save account information to file
    public void saveFile(String fileName) {
    }
    //Find reservation with the same account
    public void reservationNum(String reservationNum) {
    }
    //Update account data using passed in parameters
    public void updateAccount(String mailingAddress, String phoneNumber, String emailAddress) {
    }
    //Add draft reservations to account
    public void draftReservation(Reservation reservation) {
    }


    /**
     * Getter for mailingAddress
     */
    public String getMailingAddress()  {

        return mailingAddress;

    }

    /**
     * Setter for mailingAddress
     */
    public void setMailingAddress(String mailAddy) {

        try {
            this.mailingAddress = mailAddy;
        }
        catch(IllegalArgumentException e) {
            System.out.print("Illegal Argument, you inputs aren't valid");
        }

        catch(IllegalStateException e) {
            System.out.print("Illegal State, this address already existss");
        }
    }

    /**
     * Getter for phoneNumber
     */
    public int getPhoneNumber()  {
        return phoneNumber;
    }

    /**
     * Setter for phoneNumber
     */
    public void setPhoneNumber(int phoneNum) {

        try {
            this.phoneNumber = phoneNum;
        }
        catch(IllegalArgumentException e) {
            System.out.print("Illegal Argument, you inputs aren't valid");
        }

        catch(IllegalStateException e) {
            System.out.print("Illegal State, this phone number already existss");
        }
        /*
         * if invalid input throw IllegalArgumentException
         */
    }

    /**
     * Getter for emailAddress
     */
    public String getEmailAddress()  {
        return emailAddress;
    }

    /**
     * Setter for emailAddress
     */
    public void setEmailAddress(String emailAddy) {

        try {
            this.emailAddress = emailAddy;
        }
        catch(IllegalArgumentException e) {
            System.out.print("Illegal Argument, you inputs aren't valid");
        }

        catch(IllegalStateException e) {
            System.out.print("Illegal State, this email address already exists");
        }
    }

    public String getAccountNumber() {


        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {

        try {
            this.accountNumber = accountNumber;
        }
        catch(IllegalArgumentException e) {
            System.out.print("Illegal Argument, you inputs aren't valid");
        }

        catch(IllegalStateException e) {
            System.out.print("Illegal State, this email address already exists");
        }


    }


    /*shows a list of all reservations for the account*/
    public String[] getReservationList() {

        return reservationList;
    }

    public void setReservationList(String[] reservationList) {

        this.reservationList = reservationList;
    }

    public ArrayList<Reservation> getReservation() {

        return reservation;
    }

    public void setReservation(ArrayList<Reservation> reservation) {

        this.reservation = reservation;
    }

    public void parseReservation(String line) {
        reservationList[0] = line.substring(line.indexOf("<res-C>") + 7, line.indexOf("</res-C>"));
        reservationList[1] = line.substring(line.indexOf("<res-H>") + 7, line.indexOf("</res-H>"));
        reservationList[2] = line.substring(line.indexOf("<res-O>") + 7, line.indexOf("</res-O>"));
    }



    /**
     * Output the class’s data to the screen.  toString() method
     * @return Returns account's information.
     */
    public String showData() {
        this.accountNumber = accountNumber;
        this.mailingAddress = mailingAddress;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;

        String dataToShow = "Account Number: " + accountNumber + "\n"
                    + "Mailing Address: " + mailingAddress + "\n"
                    + "PhoneNumber: " + phoneNumber + "\n"
                    + "Email Address: " + emailAddress;

        return dataToShow;

        }

    }


