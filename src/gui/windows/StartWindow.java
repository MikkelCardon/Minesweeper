package gui.windows;

import application.model.GameSize;
import gui.PrimaryWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class StartWindow {
    private final Scene scene;
    private ComboBox comboBox = new ComboBox();
    private static GameSize gameSize = GameSize.SMALL;

    public StartWindow(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Button startButton = new Button("Start spil");
        startButton.setOnAction(event -> {
            // Transition to the main application scene
            gameSize = (GameSize) comboBox.getValue();
            PrimaryWindow mainApp = new PrimaryWindow();
            mainApp.showMainScene(primaryStage, gameSize);
        });

        layout.getChildren().add(startButton);
        layout.getChildren().add(comboBox);
        ArrayList<GameSize> gameSizes = new ArrayList<>(Arrays.asList(GameSize.SMALL, GameSize.MEDIUM, GameSize.LARGE));
        comboBox.getItems().setAll(gameSizes);
        comboBox.setValue(gameSize);


        this.scene = new Scene(layout, 300, 200);
    }

    public Scene getScene() {
        return this.scene;
    }
}
