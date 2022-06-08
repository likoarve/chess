package chess.Controller;

import chess.model.Board;
import chess.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private final Board model;
    private final View view;
    private static final int NUM_THREADS = 1;
    private final ExecutorService pool;

    public Controller(Board model, View view) {
        this.model = model;
        this.view = view;
        this.pool = Executors.newFixedThreadPool(NUM_THREADS, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
    }

    public void makeMove(int initialRow, int initialCol, int newRow, int newCol) {
        model.makeMove(initialRow, initialCol, newRow, newCol);
        view.redrawBoard();
    }
}
