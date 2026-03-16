public class BookMyStayApp {
    public static void main (String [] args){
        System.out.println("Hotel Booking Initialization");
        Guest g1 = new Guest("Neha", 25);
        System.out.println();
        Room r1 = new Room(101, "Single", 1000.0);
        System.out.println();
        Reservation res1 = new Reservation(1, 2);
    }
}

class Guest {
    String name;
    int age;
    public Guest(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Guest Name: " + name);
        System.out.println("Age: " + age);
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
        System.out.println("Room No: " + roomNumber);
        System.out.println("Type: " + type);
        System.out.println("Price per night: " + pricePerNight);
    }
}

class Reservation {
    int reservationId;
    int nights;
    public Reservation(int reservationId, int nights) {
        this.reservationId = reservationId;
        this.nights = nights;
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Nights: " + nights);
    }
}