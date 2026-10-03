public class Tablet extends ClassroomDevice implements Chargeable {
    private final String assetTag;

    public Tablet(String assetTag) {
        if (assetTag == null || assetTag.trim().isEmpty()) throw new IllegalArgumentException("assetTag must not be blank");
        this.assetTag = assetTag;
    }

    @Override
    public String operate() {
        return "Tablet " + assetTag + " displaying lesson";
    }

    @Override
    public String charge() {
        return assetTag + " charging";
    }

    @Override
    public String charge(int minutes) {
        return assetTag + " charging for " + minutes + " minutes";
    }
}
