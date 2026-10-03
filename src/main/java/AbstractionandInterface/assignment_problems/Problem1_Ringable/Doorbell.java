public class Doorbell implements Ringable {
    private final String location;

    public Doorbell(String location) {
        if (location == null || location.trim().isEmpty()) throw new IllegalArgumentException("location must not be blank");
        this.location = location;
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}
