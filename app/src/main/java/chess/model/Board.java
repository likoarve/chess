package chess.model;

import chess.model.piece.Piece;
import chess.model.piece.PieceColour;
import chess.model.piece.PieceType;
import chess.view.BoardObserver;
import javafx.util.Pair;

import java.util.List;

public class Board {
    private final Piece[][] board;
    private PieceColour currentTurn;
    private BoardObserver observer;

    public Board() {
        this.board = new Piece[8][8];
        this.currentTurn = PieceColour.WHITE;
        loadStartingPosition();
    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public boolean isCurrentTurn(Piece piece) {
        return piece.getColour() == currentTurn;
    }

    public void loadStartingPosition() {
        // White Pieces
        board[0][0] = new Piece(PieceType.ROOK, PieceColour.WHITE);
        board[0][1] = new Piece(PieceType.KNIGHT, PieceColour.WHITE);
        board[0][2] = new Piece(PieceType.BISHOP, PieceColour.WHITE);
        board[0][3] = new Piece(PieceType.QUEEN, PieceColour.WHITE);
        board[0][4] = new Piece(PieceType.KING, PieceColour.WHITE);
        board[0][5] = new Piece(PieceType.BISHOP, PieceColour.WHITE);
        board[0][6] = new Piece(PieceType.KNIGHT, PieceColour.WHITE);
        board[0][7] = new Piece(PieceType.ROOK, PieceColour.WHITE);

        // Black Pieces
        board[7][0] = new Piece(PieceType.ROOK, PieceColour.BLACK);
        board[7][1] = new Piece(PieceType.KNIGHT, PieceColour.BLACK);
        board[7][2] = new Piece(PieceType.BISHOP, PieceColour.BLACK);
        board[7][3] = new Piece(PieceType.QUEEN, PieceColour.BLACK);
        board[7][4] = new Piece(PieceType.KING, PieceColour.BLACK);
        board[7][5] = new Piece(PieceType.BISHOP, PieceColour.BLACK);
        board[7][6] = new Piece(PieceType.KNIGHT, PieceColour.BLACK);
        board[7][7] = new Piece(PieceType.ROOK, PieceColour.BLACK);

        // Pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece(PieceType.PAWN, PieceColour.WHITE);
            board[6][i] = new Piece(PieceType.PAWN, PieceColour.BLACK);
        }
    }

    public void makeMove(int initialRow, int initialCol, int newRow, int newCol) {
        if (initialRow == newRow && initialCol == newCol) {
            return;
        }

        if (board[initialRow][initialCol].getColour() != currentTurn) {
            return;
        }

        this.board[newRow][newCol] = this.board[initialRow][initialCol];
        this.board[initialRow][initialCol] = null;
        changeTurn();

        this.observer.update();
    }

    public void changeTurn() {
        if (currentTurn == PieceColour.WHITE) {
            currentTurn = PieceColour.BLACK;
        } else {
            currentTurn = PieceColour.WHITE;
        }
    }

    public void attach(BoardObserver observer) {
        this.observer = observer;
    }
}
