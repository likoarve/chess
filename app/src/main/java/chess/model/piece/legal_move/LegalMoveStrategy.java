package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import javafx.util.Pair;
import java.util.List;

public interface LegalMoveStrategy {
    List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col);
}
