package gui.windows;

import gui.PrimaryWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartWindow {
    private final Scene scene;

    public StartWindow(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Button startButton = new Button("Start spil");
        startButton.setOnAction(event -> {
            // Transition to the main application scene
            PrimaryWindow mainApp = new PrimaryWindow();
            mainApp.showMainScene(primaryStage);
        });

        layout.getChildren().add(startButton);

        this.scene = new Scene(layout, 300, 200);
    }

    public Scene getScene() {
        return this.scene;
    }
}
