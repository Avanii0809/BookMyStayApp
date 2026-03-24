import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private String guestName;
    private String roomType;
    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;
    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "system_state.dat";
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Corrupted state. Starting with clean data.");
        }
        return null;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        PersistenceService persistenceService = new PersistenceService();
        SystemState restoredState = persistenceService.load();
        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;
        if (restoredState != null) {
            bookingHistory = restoredState.bookingHistory;
            inventory = restoredState.inventory;
        } else {
            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);
        }
        System.out.println("\n--- Current System State ---");
        bookingHistory.add(new Reservation("RES301", "Alice", "Deluxe"));
        bookingHistory.add(new Reservation("RES302", "Bob", "Suite"));
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);
        inventory.put("Suite", inventory.get("Suite") - 1);
        System.out.println("\nBookings:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
        SystemState currentState = new SystemState(bookingHistory, inventory);
        persistenceService.save(currentState);
        System.out.println("\nSystem ready for shutdown and recovery.");
    }
}
