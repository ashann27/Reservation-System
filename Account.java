
import java.util.*;

public class Account {

    //Attributes & Arrays for Account
    protected String accountNumber = "-99";
    protected String mailingAddress;
    protected String phoneNumber;
    protected String emailAddress;
    protected String reservationList[];
    protected ArrayList<Reservation> reservation = new ArrayList<Reservation>();

    //////////////////////////////////////////
    //Getters & setters for HotelReservation//
    //////////////////////////////////////////
    public String getMailingAddress()  {
        return mailingAddress;
    }

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


    public String getPhoneNumber()  {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNum) {

        try {
            this.phoneNumber = phoneNum;
        }
        catch(IllegalArgumentException e) {
            System.out.print("Illegal Argument, you inputs aren't valid");
        }

        catch(IllegalStateException e) {
            System.out.print("Illegal State, this phone number already existss");
        }
    }

    public String getEmailAddress()  {
        return emailAddress;
    }

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

    /////////////////////////////////////////
    /////////Constructors & Methods//////////
    /////////////////////////////////////////

    // first type of constructor
    public Account() {
    }

    // 2nd constructor with more attributes and values
    public Account(String accountNumber, String mailingAddress, String phoneNumber, String emailAddress) {
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
        phoneNumber = line.substring(line.indexOf(("<phoneNumber>") + 13, line.indexOf("</phoneNumber>")));
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
    public void parseReservation(String line) {
        reservationList[0] = line.substring(line.indexOf("<res-C>") + 7, line.indexOf("</res-C>"));
        reservationList[1] = line.substring(line.indexOf("<res-H>") + 7, line.indexOf("</res-H>"));
        reservationList[2] = line.substring(line.indexOf("<res-O>") + 7, line.indexOf("</res-O>"));
    }

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


