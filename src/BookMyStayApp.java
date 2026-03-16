public class BookMyStayApp {
    public static void main (String [] args){
        BookingManager bookingManager = new BookingManager();
        Reservation r1 = new Reservation(1, "Neha", "Single");
        Reservation r2 = new Reservation(2, "Amit", "Suite");
        try {
            bookingManager.requestBooking(r1);
            r1.displayReservation();
            System.out.println();
            bookingManager.requestBooking(r2);
            r2.displayReservation();
        } catch (BookingException e) {
            System.out.println("Booking Error: " + e.getMessage());
        }
    }
}

class Reservation {
    int reservationId;
    String guestName;
    String roomType;

    public Reservation(int reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
    }
}

class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class BookingManager {
    public void requestBooking(Reservation reservation) throws BookingException {
        if (reservation.roomType.equalsIgnoreCase("Suite")) {
            throw new BookingException("Room type not available");
        }
        System.out.println("Booking successful for " + reservation.guestName);
    }
}