public class DeliveryDrone extends Drone implements Trackable {
    private final String id;

    public DeliveryDrone(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id must not be blank");
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " flying to deliver a package";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}
