package chess.view;

import chess.Controller.Controller;
import chess.model.Board;
import chess.model.piece.Piece;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;

public class BoardView implements BoardObserver {
    private final static double SQUARE_SIZE = 65;
    private final Board model;
    private final Controller controller;
    private final Pane board;

    private double startDragX;
    private double startDragY;

    public BoardView(Board model, Controller controller) {
        this.board = new Pane();
        this.model = model;
        this.model.attach(this);
        this.controller = controller;

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
                rectangle.setLayoutX(SQUARE_SIZE * col);
                rectangle.setLayoutY(SQUARE_SIZE * row);

                rectangle.setOnMouseDragEntered(e -> rectangle.setId("hoveredByPiece"));
                rectangle.setOnMouseDragExited(e -> rectangle.setId(null));

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
                            drawLegalMoves(model.getLegalMoves(7 - gridNumber(pieceView.getLayoutY()),
                                    gridNumber(pieceView.getLayoutX())));

                            startDragX = e.getSceneX() - e.getX() + SQUARE_SIZE / 2;
                            startDragY = e.getSceneY() - e.getY() + SQUARE_SIZE / 2;
                            pieceView.setTranslateX(e.getSceneX() - startDragX);
                            pieceView.setTranslateY(e.getSceneY() - startDragY);
                            pieceView.toFront();
                        });

                        pieceView.setOnDragDetected(e -> pieceView.startFullDrag());
                        pieceView.setOnMouseDragOver(e -> pieceView.setMouseTransparent(true));

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

    private void drawLegalMoves(List<Pair<Integer, Integer>> legalMoves) {
        for (Pair<Integer, Integer> coordinate : legalMoves) {
            Rectangle rec = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.rgb(255,0,0,0.3));
            rec.setLayoutX(coordinate.getValue() * SQUARE_SIZE);
            rec.setLayoutY((7 - coordinate.getKey()) * SQUARE_SIZE);
            rec.setOnMouseDragOver(e -> rec.setId("hoveredByPiece"));
            rec.setOnMouseDragExited(e -> rec.setId(null));

            this.board.getChildren().add(rec);
        }
    }

    public Pane getBoard() {
        return this.board;
    }

    private int gridNumber(double position) {
        return (int) Math.round(position / SQUARE_SIZE);
    }

    @Override
    public void update(String sfx) {
        String filename = String.format("/%s.mp3", sfx);
        new AudioClip(getClass().getResource(filename).toExternalForm()).play();
    }
}
