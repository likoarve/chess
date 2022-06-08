package chess.model.piece.legal_move;

import chess.model.piece.Piece;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class KnightLegalMoveStrategy implements LegalMoveStrategy {
    @Override
    public List<Pair<Integer, Integer>> getLegalMoves(Piece[][] board, int row, int col) {
        List<Pair<Integer, Integer>> legalMoves = new LinkedList<>();
        Piece current = board[row][col];

        if (isLegal(board, current, row - 2, col - 1)) {
            legalMoves.add(new Pair<>(row - 2, col - 1));
        }

        if (isLegal(board, current, row - 2, col + 1)) {
            legalMoves.add(new Pair<>(row - 2, col + 1));
        }

        if (isLegal(board, current, row - 1, col - 2)) {
            legalMoves.add(new Pair<>(row - 1, col - 2));
        }

        if (isLegal(board, current, row - 1, col + 2)) {
            legalMoves.add(new Pair<>(row - 1, col + 2));
        }

        if (isLegal(board, current, row + 2, col - 1)) {
            legalMoves.add(new Pair<>(row + 2, col - 1));
        }

        if (isLegal(board, current, row + 2, col + 1)) {
            legalMoves.add(new Pair<>(row + 2, col + 1));
        }

        if (isLegal(board, current, row + 1, col - 2)) {
            legalMoves.add(new Pair<>(row + 1, col - 2));
        }

        if (isLegal(board, current, row + 1, col + 2)) {
            legalMoves.add(new Pair<>(row + 1, col + 2));
        }

        return legalMoves;
    }

    private boolean isLegal(Piece[][] board, Piece current, int newRow, int newCol) {
        try {
            return board[newRow][newCol] == null || current.isDifferentColour(board[newRow][newCol]);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
