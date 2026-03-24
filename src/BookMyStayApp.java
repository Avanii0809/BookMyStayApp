import java.util.*;

class BookingRequest {
    private String guestName;
    private String roomType;
    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        notifyAll(); 
    }
    public synchronized BookingRequest getRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}

class InventoryManager {
    private Map<String, Integer> inventory = new HashMap<>();
    public InventoryManager() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }
    public synchronized boolean allocateRoom(String roomType, String guestName) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType + " to " + guestName);
            inventory.put(roomType, available - 1);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + guestName + " (No " + roomType + " available)");
            return false;
        }
    }
    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

class BookingProcessor extends Thread {
    private BookingQueue queue;
    private InventoryManager inventory;
    public BookingProcessor(String name, BookingQueue queue, InventoryManager inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }
    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            BookingRequest request = queue.getRequest();
            inventory.allocateRoom(
                    request.getRoomType(),
                    request.getGuestName()
            );
        }
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {
        BookingQueue queue = new BookingQueue();
        InventoryManager inventory = new InventoryManager();
        queue.addRequest(new BookingRequest("Alice", "Deluxe"));
        queue.addRequest(new BookingRequest("Bob", "Deluxe"));
        queue.addRequest(new BookingRequest("Charlie", "Suite"));
        queue.addRequest(new BookingRequest("David", "Suite"));
        BookingProcessor t1 = new BookingProcessor("Thread-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Thread-2", queue, inventory);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        inventory.displayInventory();
        System.out.println("\nSystem maintained consistency under concurrent load.");
    }
}
