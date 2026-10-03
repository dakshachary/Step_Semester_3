public class Painting extends ArtPiece {
    private final String title;

    public Painting(String title) {
        super();
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title must not be blank");
        this.title = title;
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}
