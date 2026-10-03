public class ParcelNote extends DeliveryNote {
    private final String trackingId;

    public ParcelNote(String trackingId) {
        if (trackingId == null || trackingId.trim().isEmpty()) throw new IllegalArgumentException("trackingId must not be blank");
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}
