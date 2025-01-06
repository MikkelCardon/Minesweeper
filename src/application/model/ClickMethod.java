package application.model;

import gui.windows.GameWindow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ClickMethod {
    public static void leftClick(StackPane stackPane, GameWindow game) {
        if (stackPane.getChildren().size() == 1) {
            return;
        }
        if (stackPane.getUserData().equals("Flag")){
            return;
        }
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(1);
        Cell cell = (Cell) rectangle.getUserData();
        cell.setShown(true);

        if (cell.isBombe()){
            rectangle.setFill(Color.RED);
            GameOutput.lostAlert(game);
        } else{
            stackPane.getChildren().remove(1);
            game.revealEmptyCells(cell);
            game.checkIfGameIsWon();
        }
    }

    public static void rightClick(StackPane stackPane, GameWindow game) {
        if (stackPane.getChildren().size() < 2){
            return;
        }
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(1);
        Cell cell = (Cell) rectangle.getUserData();
        if (cell.isShown()){
            return;
        }

        if (stackPane.getUserData().equals("Flag")){
            stackPane.getChildren().removeLast();
            stackPane.setUserData("noFlag");
            game.getPlacedFlags().remove(cell);
            return;
        }
        Rectangle flag = new Rectangle(15, 15);
        flag.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
        stackPane.getChildren().add(flag);
        stackPane.setUserData("Flag");
        game.getPlacedFlags().add(cell);
        game.checkIfGameIsWon();
    }
}
