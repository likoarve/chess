package chess.model.piece;

public class Piece {
    private final PieceType type;
    private final PieceColour colour;

    public Piece(PieceType type, PieceColour colour) {
        this.type = type;
        this.colour = colour;
    }

    public PieceColour getColour() {
        return colour;
    }

    public String toURI() {
        return String.format("/%s_%s.png", colour.toString(), type.toString()).toLowerCase();
    }

    public boolean isDifferentColour(Piece other) {
        if (other == null) {
            return false;
        }

        return this.getColour() != other.getColour();
    }
}
