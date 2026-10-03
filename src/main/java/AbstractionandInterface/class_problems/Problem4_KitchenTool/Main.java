public class Main {
    public static void main(String[] args) {
        Blender b = new Blender();
        b.setSpeedLevel(3);
        System.out.println(b.getSpeedLevel());
        b.setSpeedLevel(9);
        System.out.println(b.getSpeedLevel());
        System.out.println(b.prepare());
        System.out.println(b.clean());
    }
}
