package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class BishopLegalMoveStrategy implements LegalMoveStrategy {

    @Override
    public List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col) {
        List<Pair<Integer, Integer>> legalMoves = new LinkedList<>();
        Piece current = board[row][col];
        Piece newSquare;

        // UP-RIGHT
        for (int i = 1; i < 8; i++) {
            try {
                newSquare = board[row + i][col + i];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (newSquare == null) {
                legalMoves.add(new Pair<>(row + i, col + i));
            } else {
                if (current.isDifferentColour(newSquare)) {
                    legalMoves.add(new Pair<>(row + i, col + i));
                }
                break;
            }
        }

        // UP-LEFT
        for (int i = 1; i < 8; i++) {
            try {
                newSquare = board[row + i][col - i];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (newSquare == null) {
                legalMoves.add(new Pair<>(row + i, col - i));
            } else {
                if (current.isDifferentColour(newSquare)) {
                    legalMoves.add(new Pair<>(row + i, col - i));
                }
                break;
            }
        }

        // DOWN-RIGHT
        for (int i = 1; i < 8; i++) {
            try {
                newSquare = board[row - i][col + i];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (newSquare == null) {
                legalMoves.add(new Pair<>(row - i, col + i));
            } else {
                if (current.isDifferentColour(newSquare)) {
                    legalMoves.add(new Pair<>(row - i, col + i));
                }
                break;
            }
        }

        // UP-LEFT
        for (int i = 1; i < 8; i++) {
            try {
                newSquare = board[row - i][col - i];
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            if (newSquare == null) {
                legalMoves.add(new Pair<>(row - i, col - i));
            } else {
                if (current.isDifferentColour(newSquare)) {
                    legalMoves.add(new Pair<>(row - i, col - i));
                }
                break;
            }
        }

        return legalMoves;
    }
}
