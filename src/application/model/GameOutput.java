package application.model;

import application.controller.Controller;
import application.controller.StopWatch;
import gui.windows.GameWindow;
import gui.windows.StartWindow;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOutput {

    public static void lostAlert(GameWindow game){
        stopTime();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("DU TABTE");
        alert.showAndWait();
        disableGame(game);
    }

    public static void winAlert(GameWindow game){
        stopTime();
        Leaderboard currentGame = Controller.createLeaderboard(StartWindow.getName().getText(), StopWatch.durationDouble());

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Du vandt");
        alert.setContentText("Din tid: " + StopWatch.durationDouble() + " sekunder");
        if (Controller.getTop3Leaderboard().contains(currentGame)){
            int placement = Controller.getTop3Leaderboard().indexOf(currentGame)+1;
            alert.setHeaderText("Du fik en " + placement + " plads!!");
        }
        alert.showAndWait();
        disableGame(game);
    }

    private static void stopTime(){
        GameWindow.getTimer().stop();
        StopWatch.stop();
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
