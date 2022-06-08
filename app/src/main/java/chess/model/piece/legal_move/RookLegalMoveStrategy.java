package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class RookLegalMoveStrategy implements LegalMoveStrategy {

    @Override
    public List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col) {
        List<Pair<Integer, Integer>> legalMoves = new LinkedList<>();
        Piece current = board[row][col];

        // Move left
        for (int i = col - 1; i >= 0; i--) {
            // Check if square contains a piece
            if (board[row][i] != null) {
                if (current.isDifferentColour(board[row][i])) {
                    legalMoves.add(new Pair<>(row, i));
                }
                break;
            }

            legalMoves.add(new Pair<>(row, i));
        }

        // Move right
        for (int i = col + 1; i < 8; i++) {
            // Check if square contains a piece
            if (board[row][i] != null) {
                if (current.isDifferentColour(board[row][i])) {
                    legalMoves.add(new Pair<>(row, i));
                }
                break;
            }

            legalMoves.add(new Pair<>(row, i));
        }

        // Move down
        for (int i = row - 1; i >= 0; i--) {
            // Check if square contains a piece
            if (board[i][col] != null) {
                if (current.isDifferentColour(board[i][col])) {
                    legalMoves.add(new Pair<>(i, col));
                }
                break;
            }

            legalMoves.add(new Pair<>(i, col));
        }

        // Move up
        for (int i = row + 1; i < 8; i++) {
            // Check if square contains a piece
            if (board[i][col] != null) {
                if (current.isDifferentColour(board[i][col])) {
                    legalMoves.add(new Pair<>(i, col));
                }
                break;
            }

            legalMoves.add(new Pair<>(i, col));
        }

        return legalMoves;
    }
}
