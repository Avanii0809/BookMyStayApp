import java.util.*;
interface Observer {
    void update(String message);
}

interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}

class BookingSystem implements Subject {
    private List<Observer> observers = new ArrayList<>();
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
    public void confirmBooking(String userName, String service) {
        String message = "Booking confirmed for " + userName + " (" + service + ")";
        notifyObservers(message);
    }
}

class EmailService implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Email sent: " + message);
    }
}

class SMSService implements Observer {
    @Override
    public void update(String message) {
        System.out.println("SMS sent: " + message);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        Observer emailService = new EmailService();
        Observer smsService = new SMSService();
        bookingSystem.registerObserver(emailService);
        bookingSystem.registerObserver(smsService);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter service (Hotel/Flight/Cab): ");
        String service = scanner.nextLine();
        bookingSystem.confirmBooking(userName, service);
        scanner.close();
    }
}
import java.util.*;
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isCancelled;
    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isCancelled() { return isCancelled; }

    public void cancel() {
        this.isCancelled = true;
    }
    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType +
                " | Room: " + roomId +
                (isCancelled ? " | CANCELLED" : " | ACTIVE");
    }
}

class BookingManager {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }
    public Reservation getReservation(String id) {
        return reservations.get(id);
    }
    public void displayAll() {
        System.out.println("\n=== Booking Records ===");
        for (Reservation r : reservations.values()) {
            System.out.println(r);
        }
    }
}

class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public InventoryManager() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }
    public void allocate(String roomType, String roomId) {
        inventory.put(roomType, inventory.get(roomType) - 1);
        rollbackStack.push(roomId);
    }
    public void rollback(String roomType) {
        if (!rollbackStack.isEmpty()) {
            String releasedRoom = rollbackStack.pop();
            inventory.put(roomType, inventory.get(roomType) + 1);
            System.out.println("Rolled back Room ID: " + releasedRoom);
        }
    }
    public void displayInventory() {
        System.out.println("\n=== Current Inventory ===");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

class CancellationService {
    public void cancelBooking(String reservationId,
                              BookingManager bookingManager,
                              InventoryManager inventoryManager)
            throws CancellationException {
        Reservation r = bookingManager.getReservation(reservationId);
        if (r == null) {
            throw new CancellationException("Reservation does not exist.");
        }
        if (r.isCancelled()) {
            throw new CancellationException("Reservation already cancelled.");
        }
        System.out.println("\nProcessing cancellation for: " + reservationId);
        inventoryManager.rollback(r.getRoomType());
        r.cancel();
        System.out.println("Cancellation successful for " + reservationId);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingManager bookingManager = new BookingManager();
        InventoryManager inventoryManager = new InventoryManager();
        CancellationService cancellationService = new CancellationService();
        Reservation r1 = new Reservation("RES201", "Alice", "Deluxe", "D1");
        Reservation r2 = new Reservation("RES202", "Bob", "Suite", "S1");
        bookingManager.addReservation(r1);
        bookingManager.addReservation(r2);
        inventoryManager.allocate("Deluxe", "D1");
        inventoryManager.allocate("Suite", "S1");
        bookingManager.displayAll();
        inventoryManager.displayInventory();
        String[] cancelRequests = {
                "RES201",
                "RES999", 
                "RES201" 
        };
        for (String id : cancelRequests) {
            try {
                cancellationService.cancelBooking(id, bookingManager, inventoryManager);
            } catch (CancellationException e) {
                System.out.println("Cancellation Failed: " + e.getMessage());
            }
        }
        bookingManager.displayAll();
        inventoryManager.displayInventory();

        System.out.println("\nSystem state remains consistent after rollback.");
    }
}
