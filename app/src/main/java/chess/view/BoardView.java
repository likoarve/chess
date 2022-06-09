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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
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
        drawPreviousMove();
        drawPieces();
    }

    public void drawBoard() {
        Paint darkSquare = Color.rgb(170,136,101);
        Paint lightSquare = Color.rgb(234,217,183);

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

    private void drawPreviousMove() {
        for (Pair<Integer, Integer> coordinate : model.getPreviousMove()) {
            Rectangle rec = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.rgb(255,236,68,0.3));
            rec.setLayoutX(coordinate.getValue() * SQUARE_SIZE);
            rec.setLayoutY((7 - coordinate.getKey()) * SQUARE_SIZE);
            rec.setOnMouseDragOver(e -> rec.setId("hoveredByPiece"));
            rec.setOnMouseDragExited(e -> rec.setId(null));

            this.board.getChildren().add(rec);
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
                    pieceView.setPickOnBounds(true);
                    pieceView.setOnMouseDragOver(e -> pieceView.setMouseTransparent(true));

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
            if (model.getBoard()[coordinate.getKey()][coordinate.getValue()] == null) {
                Circle dot = new Circle(SQUARE_SIZE / 6, Color.rgb(60,60,60,0.3));
                dot.setLayoutX((coordinate.getValue() + 0.5) * SQUARE_SIZE);
                dot.setLayoutY((7 - coordinate.getKey() + 0.5) * SQUARE_SIZE);
                dot.setMouseTransparent(true);

                this.board.getChildren().add(dot);
            } else {
                Circle dot = new Circle(SQUARE_SIZE / 2, Color.TRANSPARENT);
                dot.setLayoutX((coordinate.getValue() + 0.5) * SQUARE_SIZE);
                dot.setLayoutY((7 - coordinate.getKey() + 0.5) * SQUARE_SIZE);
                dot.setStroke(Color.rgb(60,60,60,0.3));
                dot.setStrokeType(StrokeType.INSIDE);
                dot.setStrokeWidth(5);
                dot.setMouseTransparent(true);

                this.board.getChildren().add(dot);
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
    public void update(String sfx) {
        String filename = String.format("/%s.mp3", sfx);
        new AudioClip(getClass().getResource(filename).toExternalForm()).play();
    }
}
