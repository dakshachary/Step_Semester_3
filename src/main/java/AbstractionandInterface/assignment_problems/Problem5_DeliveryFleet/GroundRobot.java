public class GroundRobot implements Trackable {
    private final String id;

    public GroundRobot(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id must not be blank");
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}
