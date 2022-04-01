import java.io.*;

/**
 *
 */
public class HotelReservation extends Reservation {
    //Attribute for HotelReservation
    protected boolean kitchenettePresent;


    //////////////////////////////////////////
    //Getters & setters for HotelReservation//
    //////////////////////////////////////////
    public boolean getKitchenettePresent()  {
        return kitchenettePresent;
    }
    public void setKitchenettePresent(boolean kitchenettePresent) {

        this.kitchenettePresent = kitchenettePresent;
    }


    /////////////////////////////////////////
    /////////Constructors & Methods//////////
    /////////////////////////////////////////
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

    //Method to calculate price change of reservation
    public void changePrice() {
        priceOfReservation = 0;
        priceOfReservation = numberOfNights*120;

        if (lodgingSize > 900) {
            priceOfReservation += 15;
        }
        priceOfReservation += 50;
        if (kitchenettePresent) priceOfReservation += 10;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    ////////////////Method to save and complete a reservation//////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public String toString(String documents, String newReservationNumber, HotelReservation hotelReservationDefault) throws IOException {
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
}