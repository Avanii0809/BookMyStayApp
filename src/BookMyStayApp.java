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
