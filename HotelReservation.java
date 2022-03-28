import java.io.*;

/**
 *
 */
public class HotelReservation extends Reservation {

    /**
     * Whether a kitchenette is present or not
     */
    protected boolean kitchenettePresent;

    /**
     * Getter for kitchenettePresent
     */
    public boolean getKitchenettePresent()  {
        return kitchenettePresent;

    }


    /**
     * Setter for kitchenettePresent
     */
    public void setKitchenettePresent(boolean kitchenettePresent) {
        this.kitchenettePresent = kitchenettePresent;
    }



    // 1st constructor without value
    public HotelReservation() {

    }

    //2nd constructor with more attributes and values
    public HotelReservation(String accountNumber, String reservationNumber, boolean kitchenettePresent) {
        // reservation number must start with "C"
        super(accountNumber, reservationNumber);
        this.kitchenettePresent = kitchenettePresent;
    }

    //3rd constructor with reading from file
    public HotelReservation(String line) {
        super(line);
        kitchenettePresent = Boolean.parseBoolean(line.substring(line.indexOf("<kitchenettePresent>") + 20, line.indexOf("</kitchenettePresent>")));
    }


    public void changePrice() {
        priceOfReservation = 0;
        priceOfReservation = numberOfNights*120;

        if (lodgingSize > 900) {
            priceOfReservation += 15;
        }
        priceOfReservation += 50;
        if (kitchenettePresent) priceOfReservation += 10;
    }

    public String toString(String documents, String newReservationNumber, HotelReservation hotelReservationDefault) throws IOException {
        /////////////////////////////SAVE AND COMPLETE THE RESERVATION////////////////////////////////////////////////////////
        new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + hotelReservationDefault.accountNumber + "\\").mkdirs();
        //Create a new reservation
        File myObj = new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + hotelReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
        try {
            FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Reservations\\" + "acc-" + hotelReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
            myWriter.write(hotelReservationDefault.accountNumber +
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
                    "\n" + "Kitchenette Present" + "\n" +
                    this.getKitchenettePresent() + "\n--" +
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
        try (FileWriter fw = new FileWriter(documents + "\\Reservation System\\Accounts\\" + "acc-" + hotelReservationDefault.accountNumber + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(newReservationNumber);

        } catch (IOException e) {
            System.out.println("Error: Couldn't find your account.");
        }
        //output reservation information to console
        BufferedReader r = new BufferedReader( new FileReader( documents + "\\Reservation System\\Reservations\\" + "acc-" + hotelReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt") );
        String output = "";
        String line = null;
        while ((line = r.readLine()) != null) {
            output += line;

        }
        return output;

    }

    /**
     * @param reservationNumber
     * @param reservationStartDate
     * @param numberOfNights
     * @param numberOfBeds
     * @param lodgingSize
     * @param numberOfBathrooms
     * @param numberOfBedrooms
     * @param reservationStatus
     * @param priceOfReservation
     * @param physicalAddress
     * @param mailingAddress

    public abstract void Reservation(void reservationNumber, Date reservationStartDate, int numberOfNights, int numberOfBeds, void lodgingSize, int numberOfBathrooms, int numberOfBedrooms, string reservationStatus, void priceOfReservation, boolean fullKitchenPresent, boolean loftPresent, string physicalAddress, string mailingAddress);


     * @param reservationNumber
     * @param reservationStartDate
     * @param numberOfNights
     * @param numberOfBeds
     * @param lodgingSize
     * @param numberOfBathrooms
     * @param numberOfBedrooms
     * @param reservationStatus
     * @param physicalAddress

    public abstract void Reservation(void reservationNumber, Date reservationStartDate, int numberOfNights, int numberOfBeds, void lodgingSize, int numberOfBathrooms, int numberOfBedrooms, string reservationStatus, void priceOfReservation, boolean fullKitchenPresent, boolean loftPresent, string physicalAddress);
*/
}