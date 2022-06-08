package chess.model.piece;

import chess.model.piece.legal_move.LegalMoveStrategy;

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

    public LegalMoveStrategy getLegalMoveStrategy() {
        return this.type.getLegalMoveStrategy();
    }

    public String toURI() {
        return String.format("/%s_%s.png", colour.toString(), type.toString()).toLowerCase();
    }

    public boolean isDifferentColour(Piece other) {
        if (other == null) {
            return false;
        } else {
            return this.getColour() != other.getColour();
        }
    }
}
