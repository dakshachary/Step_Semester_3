public class Main {
    public static void main(String[] args) {
        ToyCar c = new ToyCar("Speedster");
        ToyRobot r = new ToyRobot("Bolt");
        System.out.println(c.makeSound());
        System.out.println(r.makeSound());
        System.out.println(c.getToyId());
        System.out.println(r.getToyId());
    }
}
