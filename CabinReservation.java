import java.io.*;

public class CabinReservation extends Reservation {

    //Attributes for CabinReservation
    protected boolean fullKitchenPresent;
    protected boolean loftPresent;
    protected int priceOfReservation;

    //////////////////////////////////////////
    //Getters & setters for CabinReservation//
    //////////////////////////////////////////

    //Getter and setter for fullKitchenPresent
    public boolean getFullKitchenPresent()  {
        return fullKitchenPresent;
    }

    public void setFullKitchenPresent(boolean fullKitchenPresent) {
        this.fullKitchenPresent = fullKitchenPresent;
    }

    //Getter and setter for loftPresent
    public boolean getLoftPresent()  {
        return loftPresent;
    }

    public void setLoftPresent(boolean loftPresent) {
        this.loftPresent = loftPresent;
    }


    //Getter and setter for priceOfReservation
    public int getPriceOfReservation()  {
        return priceOfReservation;
    }

    public void setPriceOfReservation(int priceOfReservation) {
        this.priceOfReservation = priceOfReservation;
    }

    //////////////////////////////////////////
    /////////Constructors & Methods//////////
    //////////////////////////////////////////

    // 1st constructor without value
    public CabinReservation() {
    }

    //2nd constructor with more attributes and values
    public CabinReservation(String accountNumber, String reservationNumber, boolean fullKitchenPresent, boolean loftPresent) {
        // reservation number must start with "C"
        super(accountNumber, reservationNumber);
        this.fullKitchenPresent = fullKitchenPresent;
        this.loftPresent = loftPresent;
    }

    //3rd constructor with reading from file
    public CabinReservation(String line) {
        super(line);
        loftPresent = Boolean.parseBoolean(line.substring(line.indexOf("<loftPresent>") + 13, line.indexOf("</loftPresent>")));
        fullKitchenPresent = Boolean.parseBoolean(line.substring(line.indexOf("<fullKitchenPresent>") + 20, line.indexOf("</fullKitchenPresent>")));
    }

    //Method to calculate price change of reservation
    public void changePrice() {
        priceOfReservation = 0;
        priceOfReservation = numberOfNights*120;

        if (lodgingSize > 900) {
            priceOfReservation += 15;
        }

        if (fullKitchenPresent = true) {
            priceOfReservation += 20;
        }

        if (loftPresent = true) {
        priceOfReservation += ((numberOfBathrooms - 1)*5);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////
    ////////////////Method to save and complete a reservation//////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public String toString(String documents, String newReservationNumber, CabinReservation cabinReservationDefault) throws IOException {
        new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + cabinReservationDefault.accountNumber + "\\").mkdirs();
        //Create a new reservation
        File myObj = new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + cabinReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
        try {
            FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Reservations\\" + "acc-" + cabinReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
            myWriter.write(cabinReservationDefault.accountNumber +
                    "\n" + "Reservation Number" +
                    "\n" + newReservationNumber + "\n--" +
                    "\n" + "Physical Address" + "\n" +
                    this.getPhysicalAddress() + "\n--" +
                    "\n" + "Mailing Address" + "\n" +
                    this.getMailingAddress() + "\n--" +
                    "\n" + "Reservation Start Date" + "\n" +
                    this.getReservationStartDate() + "\n--" +
                    "\n" + "Number of Nights" + "\n" +
                    this.getNumberOfNights() + "\n--" +
                    "\n" + "Number of Beds" + "\n" +
                    this.getNumberOfBeds() + "\n--" +
                    "\n" + "Number of Bedrooms" + "\n" +
                    this.getNumberOfBedrooms() + "\n--" +
                    "\n" + "Number of Bathrooms" + "\n" +
                    this.getNumberOfBathrooms() + "\n--" +
                    "\n" + "Lodging Size" + "\n" +
                    this.getLodgingSize() + "\n--" +
                    "\n" + "Loft Present" + "\n" +
                    this.getLoftPresent() + "\n--" +
                    "\n" + "Full Kitchen Present" + "\n" +
                    this.getFullKitchenPresent() + "\n--" +
                    "\n" + "Price of Reservation" + "\n" +
                    this.getPriceOfReservation() + "\n--" +
                    "\n" + "Reservation Status" + "\n" +
                    this.getReservationStatus());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Couldn't write to the file");
            e.printStackTrace();
        }

        //adds new reservation number to account file
        try (FileWriter fw = new FileWriter(documents + "\\Reservation System\\Accounts\\" + "acc-" + cabinReservationDefault.accountNumber + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(newReservationNumber);

        } catch (IOException e) {
            System.out.println("Error: Couldn't find your account.");
        }
        //output reservation information to console
        BufferedReader r = new BufferedReader( new FileReader( documents + "\\Reservation System\\Reservations\\" + "acc-" + cabinReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt") );
        String output = "";
        String line;
        while ((line = r.readLine()) != null) {
            output += line;

    }
        return output;

    }

}
