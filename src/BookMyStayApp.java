public class BookMyStayApp {
    Room[] rooms = new Room[5];
    int count = 0;

    public void addRoom(Room room) {
        if (count < rooms.length) {
            rooms[count] = room;
            count++;
        } else {
            System.out.println("Inventory full!");
        }
    }

    public void displayRooms() {
        System.out.println("Hotel Room Inventory Data");
        System.out.println("-------------------------");

        for (int i = 0; i < count; i++) {
            System.out.println("Room No: " + rooms[i].roomNumber);
            System.out.println("Type: " + rooms[i].type);
            System.out.println("Price per night: " + rooms[i].pricePerNight);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        BookMyStayApp inventory = new BookMyStayApp();
        Room r1 = new Room(101, "Single", 1000.0);
        Room r2 = new Room(102, "Double", 1500.0);
        Room r3 = new Room(103, "Suite", 3000.0);
        inventory.addRoom(r1);
        inventory.addRoom(r2);
        inventory.addRoom(r3);
        inventory.displayRooms();
    }
}

class Room {
    int roomNumber;
    String type;
    double pricePerNight;
    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }
}