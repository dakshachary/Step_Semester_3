public class ScoutDrone extends Drone {
    private final String id;

    public ScoutDrone(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id must not be blank");
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " flying a scouting route";
    }
}
