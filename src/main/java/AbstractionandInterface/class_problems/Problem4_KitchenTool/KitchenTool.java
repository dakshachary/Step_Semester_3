public abstract class KitchenTool {
    private int speedLevel = 1;

    public abstract String prepare();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel < 1 || speedLevel > 5) {
            System.out.println("Rejected: speed level must be between 1 and 5");
            return;
        }
        this.speedLevel = speedLevel;
    }
}
