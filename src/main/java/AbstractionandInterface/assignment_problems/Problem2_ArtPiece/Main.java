public class Main {
    public static void main(String[] args) {
        Painting p = new Painting("Sunset Fields");
        Sculpture s = new Sculpture("The Thinker II");
        System.out.println(p.describe());
        System.out.println(s.describe());
        System.out.println(p.getPieceId());
        System.out.println(s.getPieceId());
    }
}
