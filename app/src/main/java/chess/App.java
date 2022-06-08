package chess;

import chess.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        primaryStage.setScene(new View().getScene());
        primaryStage.show();
    }
}
