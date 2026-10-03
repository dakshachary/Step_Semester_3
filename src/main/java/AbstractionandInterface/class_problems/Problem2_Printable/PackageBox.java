public class PackageBox implements Printable {
    private final String trackingId;

    public PackageBox(String trackingId) {
        if (trackingId == null || trackingId.trim().isEmpty()) throw new IllegalArgumentException("trackingId must not be blank");
        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}
