package gui;

import application.controller.Controller;
import application.model.GameSize;
import gui.windows.GameWindow;
import gui.windows.StartWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrimaryWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        // Show the StartWindow scene first
        StartWindow startWindow = new StartWindow(primaryStage);
        primaryStage.setScene(startWindow.getScene());
        primaryStage.show();
    }

    public void showMainScene(Stage primaryStage, GameSize gameSize) {
        initGame(primaryStage, gameSize);
    }

    private void initGame(Stage primaryStage, GameSize gameSize){
        GameWindow GameWindow = new GameWindow(primaryStage, gameSize);
        primaryStage.setScene(GameWindow.getScene());
    }
}
