package gui;

import application.controller.Controller;
import gui.windows.GameWindow;
import gui.windows.StartWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrimaryWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Booking");

        // Show the StartWindow scene first
        StartWindow startWindow = new StartWindow(primaryStage);
        primaryStage.setScene(startWindow.getScene());
        primaryStage.show();
    }

    public void showMainScene(Stage primaryStage) {
        initGame(primaryStage);
    }

    private void initGame(Stage primaryStage){
        GameWindow GameWindow = new GameWindow(primaryStage);
        primaryStage.setScene(GameWindow.getScene());
    }
}
