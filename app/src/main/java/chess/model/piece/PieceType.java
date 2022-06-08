package chess.model.piece;

import chess.model.piece.legal_move.*;

public enum PieceType {
    KING(new KingLegalMoveStrategy()),
    QUEEN(new QueenLegalMoveStrategy()),
    BISHOP(new BishopLegalMoveStrategy()),
    KNIGHT(new KnightLegalMoveStrategy()),
    ROOK(new RookLegalMoveStrategy()),
    PAWN(new PawnLegalMoveStrategy());

    private final LegalMoveStrategy legalMoveStrategy;

    PieceType(final LegalMoveStrategy legalMoveStrategy) {
        this.legalMoveStrategy = legalMoveStrategy;
    }

    public LegalMoveStrategy getLegalMoveStrategy() {
        return this.legalMoveStrategy;
    }
}
