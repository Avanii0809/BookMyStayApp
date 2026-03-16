public class BookMyStayApp {
    public static void main (String [] args){
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom(new Room(101, "Single", 1000.0, true));
        inventory.addRoom(new Room(102, "Double", 1500.0, false));
        inventory.addRoom(new Room(103, "Suite", 3000.0, true));
        inventory.checkAvailability();
    }
}

class Room {
    int roomNumber;
    String type;
    double pricePerNight;
    boolean isAvailable;
    public Room(int roomNumber, String type, double pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }
}

class RoomInventory {
    Room[] rooms = new Room[5];
    int count = 0;

    public void addRoom(Room room) {
        if (count < rooms.length) {
            rooms[count] = room;
            count++;
        }
    }

    public void checkAvailability() {
        System.out.println("Room Inventory");
        System.out.println("--------------");
        for (int i = 0; i < count; i++) {
            System.out.println("Room Number: " + rooms[i].roomNumber);
            System.out.println("Type: " + rooms[i].type);
            System.out.println("Price per night: " + rooms[i].pricePerNight);
            if (rooms[i].isAvailable) {
                System.out.println("Available: Yes");
            } else {
                System.out.println("Available: No");
            }
            System.out.println();
        }
    }
}