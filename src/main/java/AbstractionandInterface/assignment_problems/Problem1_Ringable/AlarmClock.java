public class AlarmClock implements Ringable {
    private final String time;

    public AlarmClock(String time) {
        if (time == null || time.trim().isEmpty()) throw new IllegalArgumentException("time must not be blank");
        this.time = time;
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}
