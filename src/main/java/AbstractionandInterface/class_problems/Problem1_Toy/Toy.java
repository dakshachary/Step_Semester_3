public abstract class Toy {
    private static int counter = 1000;
    private final String toyId;

    public Toy() {
        counter++;
        this.toyId = "TOY-" + counter;
    }

    public abstract String makeSound();

    public String getToyId() {
        return toyId;
    }
}
