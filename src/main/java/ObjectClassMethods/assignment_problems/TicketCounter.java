import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

abstract class Seat {
    private final String id;

    Seat(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    abstract double getPrice();
}

class RegularSeat extends Seat {
    RegularSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 150;
    }
}

class PremiumSeat extends Seat {
    PremiumSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 250;
    }
}

class ReclinerSeat extends Seat {
    ReclinerSeat(String id) {
        super(id);
    }

    double getPrice() {
        return 400;
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

class Show {
    private final String title;
    private final LocalDateTime startTime;
    private final Map<String, Seat> seats = new LinkedHashMap<>();
    private final Set<String> bookedSeatIds = new HashSet<>();

    Show(String title, LocalDateTime startTime) {
        this.title = title;
        this.startTime = startTime;
    }

    String getTitle() {
        return title;
    }

    LocalDateTime getStartTime() {
        return startTime;
    }

    void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    Seat getSeat(String id) {
        return seats.get(id);
    }

    boolean isAvailable(String seatId) {
        return seats.containsKey(seatId) && !bookedSeatIds.contains(seatId);
    }

    void reserve(List<Seat> toReserve) {
        for (Seat seat : toReserve) {
            bookedSeatIds.add(seat.getId());
        }
    }

    void release(List<Seat> toRelease) {
        for (Seat seat : toRelease) {
            bookedSeatIds.remove(seat.getId());
        }
    }
}

class Booking {
    private final Customer customer;
    private final Show show;
    private final List<Seat> seats;
    private boolean cancelled;

    Booking(Customer customer, Show show, List<Seat> seats) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>(seats);
    }

    Customer getCustomer() {
        return customer;
    }

    Show getShow() {
        return show;
    }

    List<Seat> getSeats() {
        return new ArrayList<>(seats);
    }

    boolean isCancelled() {
        return cancelled;
    }

    double calculateTotal() {
        double total = 0;
        for (Seat seat : seats) {
            total += seat.getPrice();
        }
        return total;
    }

    String seatList() {
        List<String> ids = new ArrayList<>();
        for (Seat seat : seats) {
            ids.add(seat.getId());
        }
        return String.join(", ", ids);
    }

    void cancel(LocalDateTime now) {
        if (cancelled) {
            System.out.println(customer.getName() + "'s booking is already cancelled.");
            return;
        }
        if (!now.isBefore(show.getStartTime())) {
            System.out.println("Cannot cancel: the show has already started.");
            return;
        }
        show.release(seats);
        cancelled = true;
        System.out.println(customer.getName() + "'s booking cancelled. Seats " + seatList() + " released.");
    }
}

class BookingDesk {
    static final int MAX_SEATS = 6;

    Booking book(Customer customer, Show show, String... seatIds) {
        if (seatIds.length == 0 || seatIds.length > MAX_SEATS) {
            System.out.println("A booking must have between 1 and " + MAX_SEATS + " seats.");
            return null;
        }
        List<Seat> chosen = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (String id : seatIds) {
            if (!seen.add(id)) {
                System.out.println("Seat " + id + " was selected more than once.");
                return null;
            }
            Seat seat = show.getSeat(id);
            if (seat == null) {
                System.out.println("Seat " + id + " does not exist for this show.");
                return null;
            }
            if (!show.isAvailable(id)) {
                System.out.println("Seat " + id + " is already booked for this show.");
                return null;
            }
            chosen.add(seat);
        }
        show.reserve(chosen);
        Booking booking = new Booking(customer, show, chosen);
        System.out.println(String.format(Locale.US, "Booking confirmed for %s: %s. Total: ₹%.2f.",
                customer.getName(), booking.seatList(), booking.calculateTotal()));
        return booking;
    }
}

public class TicketCounter {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        LocalDateTime showTime = LocalDateTime.of(2025, 3, 15, 19, 0);
        Show show = new Show("7 PM Show", showTime);
        show.addSeat(new RegularSeat("A1"));
        show.addSeat(new RegularSeat("A2"));
        show.addSeat(new PremiumSeat("F5"));
        show.addSeat(new ReclinerSeat("R1"));

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");
        BookingDesk desk = new BookingDesk();
        LocalDateTime now = LocalDateTime.of(2025, 3, 15, 17, 0);

        Booking ashaBooking = desk.book(asha, show, "A1", "A2", "F5");
        desk.book(ravi, show, "A2");
        desk.book(ravi, show, "R1");
        ashaBooking.cancel(now);
        desk.book(neha, show, "A2");
    }
}
