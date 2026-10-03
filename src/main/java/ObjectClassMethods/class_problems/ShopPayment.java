import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Product {
    private final String name;
    private final double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }
}

class OrderItem {
    private final Product product;
    private final int quantity;

    OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    Product getProduct() {
        return product;
    }

    int getQuantity() {
        return quantity;
    }

    double getSubtotal() {
        return product.getPrice() * quantity;
    }
}

enum OrderStatus {
    PENDING("Pending"), PAID("Paid");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
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

    Order createOrder() {
        Order order = new Order(this);
        System.out.println("Order created for Customer " + name + ".");
        return order;
    }
}

class Order {
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.PENDING;

    Order(Customer customer) {
        this.customer = customer;
    }

    Customer getCustomer() {
        return customer;
    }

    String getLabel() {
        return "Order " + customer.getName();
    }

    OrderStatus getStatus() {
        return status;
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        items.add(new OrderItem(product, quantity));
    }

    double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    void recordPaymentResult(boolean success) {
        if (success) {
            status = OrderStatus.PAID;
        }
    }
}

interface PaymentMethod {
    String getName();

    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private final boolean approved;

    CreditCardPayment(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return "Credit Card";
    }

    public boolean processPayment(double amount) {
        return approved && amount > 0;
    }
}

class PayPalPayment implements PaymentMethod {
    private final boolean approved;

    PayPalPayment(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return "PayPal";
    }

    public boolean processPayment(double amount) {
        return approved && amount > 0;
    }
}

class BankTransferPayment implements PaymentMethod {
    private final boolean approved;

    BankTransferPayment(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return "Bank Transfer";
    }

    public boolean processPayment(double amount) {
        return approved && amount > 0;
    }
}

class PaymentService {
    boolean pay(Order order, PaymentMethod method) {
        if (order.isEmpty()) {
            System.out.println("Cannot process payment for an empty order.");
            return false;
        }
        if (order.getStatus() == OrderStatus.PAID) {
            System.out.println(order.getLabel() + " is already paid.");
            return false;
        }
        System.out.println("Payment initiated via " + method.getName() + " for " + order.getLabel() + ".");
        boolean success = method.processPayment(order.getTotal());
        order.recordPaymentResult(success);
        System.out.println("Payment for " + order.getLabel() + (success ? " successful." : " failed.")
                + " Order status: " + order.getStatus().getLabel() + ".");
        return success;
    }
}

public class ShopPayment {
    public static void main(String[] args) {
        Product a = new Product("Product A", 250.0);
        Product b = new Product("Product B", 120.0);
        Product c = new Product("Product C", 99.99);
        PaymentService service = new PaymentService();

        Customer x = new Customer("X");
        Order orderX = x.createOrder();
        orderX.addItem(a, 2);
        orderX.addItem(b, 1);
        service.pay(orderX, new CreditCardPayment(true));

        Customer y = new Customer("Y");
        Order orderY = y.createOrder();
        service.pay(orderY, new CreditCardPayment(true));

        Customer z = new Customer("Z");
        Order orderZ = z.createOrder();
        orderZ.addItem(c, 1);
        service.pay(orderZ, new PayPalPayment(false));
    }
}
