package application.model;

import application.controller.StopWatch;
import gui.windows.GameWindow;
import gui.windows.StartWindow;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOutput {

    public static void lostAlert(GameWindow game, Timeline time){
        stopTime(time);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("DU TABTE");
        alert.showAndWait();
        disableGame(game);
    }
    public static void winAlert(GameWindow game, Timeline time){
        stopTime(time);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Du vandt");
        alert.setContentText("Din tid: " + StopWatch.durationSeconds() + " sekunder");
        alert.showAndWait();
        disableGame(game);
    }

    private static void stopTime(Timeline timer){
        timer.stop();
        StopWatch.stop();
        System.out.println(StopWatch.durationSeconds());
    }

    private static void disableGame(GameWindow gameWindow){
        for (StackPane stackPane : gameWindow.getStackPaneArrayList()) {
            stackPane.setDisable(true);
        }
    }

    public static void restartGame(Stage primaryStage){
        StartWindow startWindow = new StartWindow(primaryStage);
        primaryStage.setScene(startWindow.getScene());
    }
}
