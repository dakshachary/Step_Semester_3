import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

abstract class Vehicle {
    private final String name;
    private boolean available = true;

    Vehicle(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    boolean isAvailable() {
        return available;
    }

    void markRented() {
        available = false;
    }

    void markReturned() {
        available = true;
    }

    abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    Sedan(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return 40.0 * days;
    }
}

class SUV extends Vehicle {
    SUV(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        double total = 65.0 * days;
        return days >= 5 ? total * 0.90 : total;
    }
}

class Truck extends Vehicle {
    Truck(String name) {
        super(name);
    }

    double calculateCharge(int days) {
        return 90.0 * days + 50.0;
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

class Rental {
    private final Vehicle vehicle;
    private final Customer customer;
    private final int days;
    private boolean active = true;

    Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
    }

    Vehicle getVehicle() {
        return vehicle;
    }

    Customer getCustomer() {
        return customer;
    }

    int getDays() {
        return days;
    }

    boolean isActive() {
        return active;
    }

    double calculateCharge() {
        return vehicle.calculateCharge(days);
    }

    void close() {
        active = false;
    }
}

class RentalService {
    private final List<Rental> rentals = new ArrayList<>();

    Rental rent(Customer customer, Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental duration must be at least 1 day.");
            return null;
        }
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName() + " is currently unavailable.");
            return null;
        }
        Rental rental = new Rental(vehicle, customer, days);
        vehicle.markRented();
        rentals.add(rental);
        System.out.println(String.format(Locale.US, "%s rented successfully by %s. Rental charge: $%.2f.",
                vehicle.getName(), customer.getName(), rental.calculateCharge()));
        return rental;
    }

    void returnVehicle(Rental rental) {
        if (rental == null || !rental.isActive()) {
            System.out.println("No active rental to return.");
            return;
        }
        rental.close();
        rental.getVehicle().markReturned();
        System.out.println(rental.getVehicle().getName() + " returned by " + rental.getCustomer().getName() + ".");
    }
}

public class VehicleRental {
    public static void main(String[] args) {
        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");
        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");
        RentalService service = new RentalService();

        Rental r1 = service.rent(c1, sedanA, 3);
        service.rent(c2, sedanA, 2);
        service.returnVehicle(r1);
        service.rent(c3, suvB, 5);
    }
}
