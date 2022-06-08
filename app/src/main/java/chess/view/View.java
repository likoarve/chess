package chess.view;

import chess.Controller.Controller;
import chess.model.Board;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class View {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final Board model;
    private final Controller controller;
    private final Scene scene;
    private final StackPane root;
    private final BoardView boardView;

    public View() {
        this.root = new StackPane();
        this.scene = new Scene(this.root, WIDTH, HEIGHT);
        this.scene.getStylesheets().add(getClass().getResource("/root.css").toExternalForm());

        this.model = new Board();
        this.controller = new Controller(this.model, this);

        this.boardView = new BoardView(this.model, this.controller);
        this.root.getChildren().add(this.boardView.getBoard());
    }

    public Scene getScene() {
        return this.scene;
    }

    public void redrawBoard() {
        this.boardView.draw();
    }
}
