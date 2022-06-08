package chess.view;

import chess.Controller.Controller;
import chess.model.Board;
import chess.model.piece.Piece;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class BoardView implements BoardObserver {
    private final static double SQUARE_SIZE = 65;
    private final Board model;
    private final Controller controller;
    private final Pane board;

    private final AudioClip moveSound;

    private double startDragX;
    private double startDragY;

    public BoardView(Board model, Controller controller) {
        this.board = new Pane();
        this.model = model;
        this.model.attach(this);
        this.controller = controller;

        this.moveSound = new AudioClip(getClass().getResource("/Move.mp3").toExternalForm());

        setupBoard();
        draw();
    }

    public void setupBoard() {
        this.board.setMaxSize(SQUARE_SIZE * 8, SQUARE_SIZE * 8);
        this.board.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void draw() {
        this.board.getChildren().clear();
        drawBoard();
        drawPieces();
    }

    public void drawBoard() {
        Paint darkSquare = Color.rgb(181,136,99);
        Paint lightSquare = Color.rgb(240,217,181);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle rectangle = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                rectangle.setId("squares");
                rectangle.setLayoutX(SQUARE_SIZE * col);
                rectangle.setLayoutY(SQUARE_SIZE * row);

                if ((row+col) % 2 == 0) {
                    rectangle.setFill(lightSquare);
                } else {
                    rectangle.setFill(darkSquare);
                }

                this.board.getChildren().add(rectangle);
            }
        }
    }

    public void drawPieces() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = model.getBoard()[7 - row][col];

                if (piece != null) {
                    ImageView pieceView = new ImageView(new Image(
                            getClass().getResource(piece.toURI()).toExternalForm(),
                            SQUARE_SIZE, SQUARE_SIZE, true, true));
                    pieceView.setLayoutX(SQUARE_SIZE * col);
                    pieceView.setLayoutY(SQUARE_SIZE * row);

                    if (model.isCurrentTurn(piece)) {
                        pieceView.setId("activePiece");

                        pieceView.setOnMousePressed(e -> {
                            pieceView.toFront();
                            startDragX = e.getSceneX() - e.getX() + SQUARE_SIZE / 2;
                            startDragY = e.getSceneY() - e.getY() + SQUARE_SIZE / 2;

                            pieceView.setTranslateX(e.getSceneX() - startDragX);
                            pieceView.setTranslateY(e.getSceneY() - startDragY);
                        });

                        pieceView.setOnMouseDragged(e -> {
                            pieceView.setTranslateX(e.getSceneX() - startDragX);
                            pieceView.setTranslateY(e.getSceneY() - startDragY);
                        });

                        pieceView.setOnMouseReleased(e -> {
                            int initialCol = gridNumber(pieceView.getLayoutX());
                            int initialRow = 7 - gridNumber(pieceView.getLayoutY());
                            int newCol = gridNumber(pieceView.getLayoutX() + pieceView.getTranslateX());
                            int newRow = 7 - gridNumber(pieceView.getLayoutY() + pieceView.getTranslateY());

                            controller.makeMove(initialRow, initialCol, newRow, newCol);
                        });
                    }

                    this.board.getChildren().add(pieceView);
                }
            }
        }
    }

    public Pane getBoard() {
        return this.board;
    }

    private int gridNumber(double position) {
        return (int) Math.round(position / SQUARE_SIZE);
    }

    @Override
    public void update() {
        this.moveSound.play();
    }
}
