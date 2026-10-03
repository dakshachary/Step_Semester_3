public class Sculpture extends ArtPiece {
    private final String title;

    public Sculpture(String title) {
        super();
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title must not be blank");
        this.title = title;
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}
