public abstract class ArtPiece {
    private static int counter = 1000;
    private final String pieceId;

    public ArtPiece() {
        counter++;
        this.pieceId = "ART-" + counter;
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}
