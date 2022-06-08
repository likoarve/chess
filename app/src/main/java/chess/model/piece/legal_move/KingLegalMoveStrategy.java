package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class KingLegalMoveStrategy implements LegalMoveStrategy {
    @Override
    public List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col) {
        List<Pair<Integer, Integer>> legalMoves = new LinkedList<>();
        Piece current = board[row][col];
        Piece newSquare;

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                // Check if new square is inside the board
                try {
                    newSquare = board[row + rowOffset][col + colOffset];
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }

                if (newSquare == null || current.isDifferentColour(newSquare)) {
                    legalMoves.add(new Pair<>(row + rowOffset, col + colOffset));
                }
            }
        }

        return legalMoves;
    }
}
