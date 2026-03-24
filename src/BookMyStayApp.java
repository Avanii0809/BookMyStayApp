import java.util.*;

class BookingEntry {
    private int id;
    private String userName;
    private String service;
    private Date bookingDate;
    public BookingEntry(int id, String userName, String service, Date bookingDate) {
        this.id = id;
        this.userName = userName;
        this.service = service;
        this.bookingDate = bookingDate;
    }
    public int getId() { return id; }
    public String getUserName() { return userName; }
    public String getService() { return service; }
    public Date getBookingDate() { return bookingDate; }
    @Override
    public String toString() {
        return "Booking ID: " + id +
               ", User: " + userName +
               ", Service: " + service +
               ", Date: " + bookingDate;
    }
}

class BookingRepository {
    private List<BookingEntry> bookings = new ArrayList<>();
    public void addBooking(BookingEntry booking) {
        bookings.add(booking);
    }
    public List<BookingEntry> getAllBookings() {
        return bookings;
    }
    public List<BookingEntry> getBookingsByUser(String userName) {
        List<BookingEntry> result = new ArrayList<>();
        for (BookingEntry b : bookings) {
            if (b.getUserName().equalsIgnoreCase(userName)) {
                result.add(b);
            }
        }
        return result;
    }
}

class BookingReportService {
    private BookingRepository repository;
    public BookingReportService(BookingRepository repository) {
        this.repository = repository;
    }
    public void displayAllBookings() {
        List<BookingEntry> bookings = repository.getAllBookings();
        System.out.println("\n--- All Bookings ---");
        for (BookingEntry b : bookings) {
            System.out.println(b);
        }
    }
    public void displayBookingsByUser(String userName) {
        List<BookingEntry> bookings = repository.getBookingsByUser(userName);
        System.out.println("\n--- Bookings for " + userName + " ---");
        for (BookingEntry b : bookings) {
            System.out.println(b);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingRepository repo = new BookingRepository();
        repo.addBooking(new BookingEntry(1, "Debbie", "Hotel Booking", new Date()));
        repo.addBooking(new BookingEntry(2, "Debbie", "Flight Booking", new Date()));
        repo.addBooking(new BookingEntry(3, "Avani", "Cab Booking", new Date()));
        BookingReportService service = new BookingReportService(repo);
        service.displayAllBookings();
        service.displayBookingsByUser("Debbie");
    }
}
