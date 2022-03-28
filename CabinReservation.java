import java.io.*;

/**
 *
 */
public class CabinReservation extends Reservation {



    /**
     *Whether the reservation has a full kitchen present or not
     */
    protected boolean fullKitchenPresent;

    /**
     *  Whether the reservation has a loft or not
     */
    protected boolean loftPresent;

    protected int priceOfReservation;

    // 1st constructor without value
    public CabinReservation() throws FileNotFoundException {

    }

    //2nd constructor with more attributes and values
    public CabinReservation(String accountNumber, String reservationNumber, boolean fullKitchenPresent, boolean loftPresent) throws FileNotFoundException {
        // reservation number must start with "C"
        super(accountNumber, reservationNumber);
        this.fullKitchenPresent = fullKitchenPresent;
        this.loftPresent = loftPresent;

    }

    //3rd constructor with reading from file
    public CabinReservation(String line) throws FileNotFoundException {
        super(line);
        loftPresent = Boolean.parseBoolean(line.substring(line.indexOf("<loftPresent>") + 13, line.indexOf("</loftPresent>")));
        fullKitchenPresent = Boolean.parseBoolean(line.substring(line.indexOf("<fullKitchenPresent>") + 20, line.indexOf("</fullKitchenPresent>")));
    }

    /**
     * Getter for fullKitchenPresent
     */
    public boolean getFullKitchenPresent()  {
        return fullKitchenPresent;
    }
    /**
     * Setter for fullKitchenPresent
     */
    public void setFullKitchenPresent(boolean fullKitchenPresent) {
        this.fullKitchenPresent = fullKitchenPresent;
    }


    /**
     * Getter for loftPresent
     */
    public boolean getLoftPresent()  {
        return loftPresent;
    }
    /**
     * Setter for loftPresent
     */
    public void setLoftPresent(boolean loftPresent) {
        this.loftPresent = loftPresent;
    }

    /**
     * Getter for priceOfReservation
     */
    public int getPriceofReservation()  {
        return priceOfReservation;
    }
    /**
     * Setter for priceOfReservation
     */
    public void setPriceofReservation(int priceofReservation) {
        this.priceOfReservation = priceofReservation;
    }

    /**
     *  changes the price of a resrevation
     */
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

    public String toString(String documents, String newReservationNumber, CabinReservation cabinReservationDefault) throws IOException {
        /////////////////////////////SAVE AND COMPLETE THE RESERVATION////////////////////////////////////////////////////////
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
        String line = null;
        while ((line = r.readLine()) != null) {
            output += line;

    }
        return output;

    }

}