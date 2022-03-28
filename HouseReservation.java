import java.io.*;

/**
 *
 */
public class HouseReservation extends Reservation {


    /**
     * How many floors the house contains
     */
    protected int numberOfFloors;

    /**
     * Getter for numberOfFloors
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Setter for numberOfFloors
     */
    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }


    // 1st constructor without value
    public HouseReservation() {
    }

    //2nd constructor with more attributes and values
    public HouseReservation(String accountNumber, String reservationNumber, int numberOfFloors) {
        // reservation number must start with "C"
        super(accountNumber, reservationNumber);
        this.numberOfFloors = numberOfFloors;
    }

    //3rd constructor with reading from file
    public HouseReservation(String line) {
        super(line);
        numberOfFloors = Integer.parseInt(line.substring(line.indexOf(("<numberOfFloors>") + 16, line.indexOf("</numberOfFloors>"))));

    }

    public int changePrice() {
        priceOfReservation = 0;
        priceOfReservation = numberOfNights*120;

        if (lodgingSize > 900) {
            priceOfReservation += 15;
        }
        return priceOfReservation;
    }

    public String toString(String documents, String newReservationNumber, HouseReservation houseReservationDefault) throws IOException {
        /////////////////////////////SAVE AND COMPLETE THE RESERVATION////////////////////////////////////////////////////////
        new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + houseReservationDefault.accountNumber + "\\").mkdirs();
        //Create a new reservation
        File myObj = new File(documents + "\\Reservation System\\Reservations\\" + "acc-" + houseReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
        try {
            FileWriter myWriter = new FileWriter(documents + "\\Reservation System\\Reservations\\" + "acc-" + houseReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt");
            myWriter.write(houseReservationDefault.accountNumber +
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
        try (FileWriter fw = new FileWriter(documents + "\\Reservation System\\Accounts\\" + "acc-" + houseReservationDefault.accountNumber + ".txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(newReservationNumber);

        } catch (IOException e) {
            System.out.println("Error: Couldn't find your account.");
        }
        //output reservation information to console
        BufferedReader r = new BufferedReader( new FileReader( documents + "\\Reservation System\\Reservations\\" + "acc-" + houseReservationDefault.accountNumber + "\\" + "res-" + newReservationNumber + ".txt") );
        String output = "";
        String line = null;
        while ((line = r.readLine()) != null) {
            output += line;

        }
        return output;

    }
}
