package gui.windows;

import application.controller.Controller;
import application.model.Cell;
import application.model.GameSize;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.ArrayList;

public class GameWindow {
    private Scene scene;

    public GameWindow(Stage primaryStage) {
        GridPane pane = new GridPane();
        initContent(pane);

        VBox layout = new VBox(10); // Root layout
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().add(pane); // Add GridPane to VBox

        this.scene = new Scene(layout, 400, 400); // Set VBox as root
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    ArrayList<StackPane> stackPaneArrayList = new ArrayList<>();
    public void initContent(GridPane pane) {
        //ToDO: Få GameSize fra StartWindow
        ArrayList<Cell> cells = Controller.createNewGame(GameSize.MEDIUM);

        for (Cell cell : cells) {

            Rectangle rectangle = new Rectangle(25, 25);
            String cellText = cell.getCellText().equals("0") ? "" : cell.getCellText();
            Text text = new Text(cellText);

            rectangle.setStyle("-fx-fill: grey; -fx-stroke: black; -fx-stroke-width: 1;");
            rectangle.setUserData(cell);

            StackPane stackPane = new StackPane(text, rectangle);
            stackPane.setMinSize(25, 25);
            stackPaneArrayList.add(stackPane);
            stackPane.setUserData("noFlag");
            stackPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) leftClick(stackPane);
                else if (event.getButton() == MouseButton.SECONDARY) rightClick(stackPane);
            });
            pane.add(stackPane, cell.getX(), cell.getY());
        }
    }

    private void leftClick(StackPane stackPane) {
        Rectangle rectangle = (Rectangle) stackPane.getChildren().get(1);
        Cell cell = (Cell) rectangle.getUserData();
        if (stackPane.getUserData().equals("Flag")){
            return;
        }
        cell.setShown(true);
        if (cell.isBombe()){
            rectangle.setFill(Color.RED);
            lostAlert();
            System.out.println("DU TABTE"); //TODO LOST-Metode
        } else{
            stackPane.getChildren().remove(1);
            revealEmptyCells(cell);
        }
    }

    private void rightClick(StackPane stackPane) {
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
            return;
        }
        Rectangle flag = new Rectangle(15, 15);
        flag.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
        stackPane.getChildren().add(flag);
        stackPane.setUserData("Flag");
    }

    private void revealEmptyCells(Cell cell){
//        checkIfEmpty(0, 1, cell); //DOWN
//        checkIfEmpty(0, -1, cell); // UP
//        checkIfEmpty(1, 0, cell); //RIGHT
//        checkIfEmpty(-1, 0, cell); //LEFT
        for (ArrayList<Integer> position : Controller.getPositionsAroundCell()) {
            checkIfEmpty(position.get(0), position.get(1), cell);
        }
    }
    private void checkIfEmpty(int xDif, int yDif, Cell currentCell){
        int xPos = currentCell.getX() + xDif;
        int yPos = currentCell.getY() + yDif;
        for (Cell cell : Controller.getCellsCurrentGame()) {
            if (cell.getX() == xPos && cell.getY() == yPos &&  currentCell.getCellText().equals("0")  && !cell.isShown() && !cell.isBombe()){
                int index = Controller.getCellsCurrentGame().indexOf(cell);
                stackPaneArrayList.get(index).getChildren().removeLast();
                cell.setShown(true);
                if(cell.getCellText().equals("0")){
                    revealEmptyCells(cell);
                }
            }
        }
    }
    private void lostAlert(){
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("DU TABTE");
        alert.showAndWait();
        //test
    }

    public Scene getScene() {
        return this.scene;
    }
}
