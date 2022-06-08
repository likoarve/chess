package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import chess.model.piece.PieceColour;
import javafx.util.Pair;
import java.util.LinkedList;
import java.util.List;

public class PawnLegalMoveStrategy implements LegalMoveStrategy {
    @Override
    public List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col) {
        List<Pair<Integer, Integer>> legalMoves = new LinkedList<>();
        Piece current = board[row][col];

        // REMOVE THE LAST ROW CHECKS WHEN PROMOTION IS IMPLEMENTED !!!!!

        // White Pawn
        if (current.getColour() == PieceColour.WHITE) {
            // Advance
            if (row < 7 && board[row + 1][col] == null) {
                legalMoves.add(new Pair<>(row + 1, col));

                // Double Advance
                if (row == 1 && board[row + 2][col] == null) {
                    legalMoves.add(new Pair<>(row + 2, col));
                }
            }

            // Capture Left
            if (row < 7 && col > 0 && current.isDifferentColour(board[row + 1][col - 1])) {
                legalMoves.add(new Pair<>(row + 1, col - 1));
            }

            // Capture Right
            if (row < 7 && col < 7 && current.isDifferentColour(board[row + 1][col + 1])) {
                legalMoves.add(new Pair<>(row + 1, col + 1));
            }
        }

        // Black Pawn
        if (current.getColour() == PieceColour.BLACK) {
            // Advance
            if (row > 0 && board[row - 1][col] == null) {
                legalMoves.add(new Pair<>(row - 1, col));

                // Double Advance
                if (row == 6 && board[row - 2][col] == null) {
                    legalMoves.add(new Pair<>(row - 2, col));
                }
            }

            // Capture Left
            if (row > 0 && col > 0 && current.isDifferentColour(board[row - 1][col - 1])) {
                legalMoves.add(new Pair<>(row - 1, col - 1));
            }

            // Capture Right
            if (row > 0 && col < 7 && current.isDifferentColour(board[row - 1][col + 1])) {
                legalMoves.add(new Pair<>(row - 1, col + 1));
            }
        }

        return legalMoves;
    }
}
