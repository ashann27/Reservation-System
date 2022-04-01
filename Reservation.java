

/**
 *
 */
public abstract class Reservation {

    //Attributes & Arrays for Reservation
    protected String accountNumber;
    protected String reservationNumber;
    protected String physicalAddress;
    protected String mailingAddress;
    protected String reservationStartDate;
    protected int numberOfNights;
    protected int numberOfBeds = 2;
    protected int numberOfBedrooms = 1;
    protected int numberOfBathrooms = 1;
    protected int lodgingSize;
    protected int priceOfReservation;
    protected String reservationStatus = "draft";

    /////////////////////////////////////////
    /////////Constructors & Methods//////////
    /////////////////////////////////////////

    protected Reservation() {
    }

    //constructor with default values without passing values, also used for editing
    public Reservation(String reservationNumber, String reservationStatus) {
        this.accountNumber = "-99";
        this.reservationNumber = reservationNumber;
        this.reservationStatus = "draft";
        this.priceOfReservation = 0;
    }

    //2nd constructor with more attributes
    public Reservation(String accountNumber, String reservationNumber, String physicalAddress, String mailingAddress, String reservationStartDate,
                       int numberOfNights, int numberOfBeds, int numberOfBedrooms, int numberOfBathrooms, int lodgingSize, int priceOfReservation, String reservationStatus) {
        this.accountNumber = accountNumber;
        this.reservationNumber = reservationNumber;
        this.physicalAddress = physicalAddress;
        this.mailingAddress = mailingAddress;
        this.reservationStartDate = reservationStartDate;
        this.numberOfNights = numberOfNights;
    }

    // third constructor to parse variables from reading file
    public Reservation(String line) {
        // parse all variables
        accountNumber = line.substring(line.indexOf("<accountNumber>") + 15, line.indexOf("</accountNumber>"));
        reservationNumber = line.substring(line.indexOf("<reservationNumber>") + 19, line.indexOf("</reservationNumber>"));
        physicalAddress = line.substring(line.indexOf("<physicalAddress>") + 17, line.indexOf("</physicalAddress>"));
        mailingAddress = line.substring(line.indexOf("<mailingAddress>") + 16, line.indexOf("</mailingAddress>"));
        reservationStartDate = line.substring(line.indexOf("<reservationStartDate>") + 22, line.indexOf("</reservationStartDate>"));
        numberOfNights = Integer.parseInt(line.substring(line.indexOf(("<numberOfNights>") + 16, line.indexOf("</numberOfNights>"))));
        numberOfBeds = Integer.parseInt(line.substring(line.indexOf(("<numberOfBeds>") + 14, line.indexOf("</numberOfBeds>"))));
        numberOfBedrooms = Integer.parseInt(line.substring(line.indexOf(("<numberOfBedrooms>") + 18, line.indexOf("</numberOfBedrooms>"))));
        numberOfBathrooms = Integer.parseInt(line.substring(line.indexOf(("<numberOfBathrooms>") + 19, line.indexOf("</numberOfBathrooms>"))));
        lodgingSize = Integer.parseInt(line.substring(line.indexOf(("<lodgingSize>") + 13, line.indexOf("</lodgingSize>"))));
        priceOfReservation = Integer.parseInt(line.substring(line.indexOf(("<priceOfReservation>") + 20, line.indexOf("</priceOfReservation>"))));
        reservationStatus = line.substring(line.indexOf("<reservationStatus>") + 19, line.indexOf("</reservationStatus>"));
    }

    //Save reservation information to a file
    public void saveToFile(String File) {
    }

    //Read reservation information from a file
    public void readFromFile(String File) {
    }


    //////////////////////////////////////////
    //Setters & getters for HotelReservation//
    //////////////////////////////////////////
    public void setReservationStartDate(String reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public void setLodgingSize(int lodgingSize) {
        this.lodgingSize = lodgingSize;
    }

    public void setPriceOfReservation(int priceOfReservation) {
        this.priceOfReservation = priceOfReservation;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        if (mailingAddress == null) {
            this.mailingAddress = physicalAddress;
        } else {
            this.mailingAddress = mailingAddress;
        }
    }

    public String getReservationStartDate() {
        return reservationStartDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public int getLodgingSize() {
        return lodgingSize;
    }

    public int getPriceOfReservation() {
        return priceOfReservation;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }


    public String getMailingAddress() {
        return mailingAddress;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }


}