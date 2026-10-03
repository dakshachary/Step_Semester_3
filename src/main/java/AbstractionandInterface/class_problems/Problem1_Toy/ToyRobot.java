public class ToyRobot extends Toy {
    private final String name;

    public ToyRobot(String name) {
        super();
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name must not be blank");
        this.name = name;
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}
