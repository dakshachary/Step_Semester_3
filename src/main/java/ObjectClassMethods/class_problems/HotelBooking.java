import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

abstract class Room {
    private final int number;
    private final List<Reservation> reservations = new ArrayList<>();

    Room(int number) {
        this.number = number;
    }

    int getNumber() {
        return number;
    }

    abstract String getCategory();

    abstract double calculatePrice(int nights);

    String getLabel() {
        return getCategory() + " Room " + number;
    }

    boolean isAvailable(LocalDate start, LocalDate end) {
        for (Reservation reservation : reservations) {
            if (reservation.isActive() && reservation.overlaps(start, end)) {
                return false;
            }
        }
        return true;
    }

    void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

class StandardRoom extends Room {
    StandardRoom(int number) {
        super(number);
    }

    String getCategory() {
        return "Standard";
    }

    double calculatePrice(int nights) {
        return 100.0 * nights;
    }
}

class DeluxeRoom extends Room {
    DeluxeRoom(int number) {
        super(number);
    }

    String getCategory() {
        return "Deluxe";
    }

    double calculatePrice(int nights) {
        return 180.0 * nights;
    }
}

class Suite extends Room {
    Suite(int number) {
        super(number);
    }

    String getCategory() {
        return "Suite";
    }

    double calculatePrice(int nights) {
        return 300.0 * nights + 75.0;
    }
}

class Customer {
    private final String name;

    Customer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class Reservation {
    static final int CANCELLATION_DEADLINE_DAYS = 2;
    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MMM d", Locale.US);

    private final Customer customer;
    private final Room room;
    private final LocalDate start;
    private final LocalDate end;
    private boolean active = true;

    Reservation(Customer customer, Room room, LocalDate start, LocalDate end) {
        this.customer = customer;
        this.room = room;
        this.start = start;
        this.end = end;
    }

    Customer getCustomer() {
        return customer;
    }

    Room getRoom() {
        return room;
    }

    boolean isActive() {
        return active;
    }

    int getNights() {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    double calculatePrice() {
        return room.calculatePrice(getNights());
    }

    boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
        return start.isBefore(otherEnd) && otherStart.isBefore(end);
    }

    String getPeriod() {
        if (start.getMonth() == end.getMonth() && start.getYear() == end.getYear()) {
            return start.format(MONTH_DAY) + "-" + end.getDayOfMonth();
        }
        return start.format(MONTH_DAY) + "-" + end.format(MONTH_DAY);
    }

    LocalDate getCancellationDeadline() {
        return start.minusDays(CANCELLATION_DEADLINE_DAYS);
    }

    boolean cancel(LocalDate today) {
        if (!active) {
            System.out.println("Reservation is already cancelled.");
            return false;
        }
        if (today.isAfter(getCancellationDeadline())) {
            System.out.println("Cannot cancel: the cancellation deadline for " + room.getLabel() + " ("
                    + getPeriod() + ") has passed.");
            return false;
        }
        active = false;
        System.out.println("Reservation for " + customer.getName() + ", " + room.getLabel() + " (" + getPeriod()
                + ") cancelled successfully.");
        return true;
    }
}

class HotelDesk {
    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MMM d", Locale.US);

    boolean checkAvailability(Room room, LocalDate start, LocalDate end) {
        boolean free = end.isAfter(start) && room.isAvailable(start, end);
        System.out.println(room.getLabel() + " is " + (free ? "available" : "not available") + " from "
                + start.format(MONTH_DAY) + " to " + end.format(MONTH_DAY) + ".");
        return free;
    }

    Reservation reserve(Customer customer, Room room, LocalDate start, LocalDate end) {
        if (!end.isAfter(start) || !room.isAvailable(start, end)) {
            System.out.println(room.getLabel() + " is not available from " + start.format(MONTH_DAY) + " to "
                    + end.format(MONTH_DAY) + ".");
            return null;
        }
        Reservation reservation = new Reservation(customer, room, start, end);
        room.addReservation(reservation);
        System.out.println(String.format(Locale.US, "Reservation confirmed for %s, %s (%s). Price: $%.2f.",
                customer.getName(), room.getLabel(), reservation.getPeriod(), reservation.calculatePrice()));
        return reservation;
    }
}

public class HotelBooking {
    public static void main(String[] args) {
        Room standard101 = new StandardRoom(101);
        Room deluxe201 = new DeluxeRoom(201);
        Customer a = new Customer("Customer A");
        Customer b = new Customer("Customer B");
        Customer c = new Customer("Customer C");
        HotelDesk desk = new HotelDesk();
        LocalDate today = LocalDate.of(2025, 12, 20);

        desk.checkAvailability(standard101, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5));
        Reservation resA = desk.reserve(a, standard101, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5));
        desk.reserve(b, standard101, LocalDate.of(2026, 1, 3), LocalDate.of(2026, 1, 7));
        resA.cancel(today);
        desk.reserve(c, deluxe201, LocalDate.of(2026, 2, 10), LocalDate.of(2026, 2, 12));
    }
}
