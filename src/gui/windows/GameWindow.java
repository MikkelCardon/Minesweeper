package gui.windows;

import application.controller.Controller;
import application.model.Cell;
import application.model.GameSize;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
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
import java.util.HashSet;
import java.util.Set;

public class GameWindow {
    private Scene scene;
    private GameSize gameSize = GameSize.SMALL;

    public GameWindow(Stage primaryStage) {
        GridPane gamePane = new GridPane();
        gamePane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        GridPane layoutPane = new GridPane();
        initLayout(layoutPane);
        initContent(gamePane);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().add(layoutPane);
        layout.getChildren().add(gamePane);

        //ToDo: Scene width og height kan tage parameter fra GameSize klassen.
        int widthSize;
        int heightSize;
        this.scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initLayout(GridPane layoutPane) {
        layoutPane.add(new Label("GAME: " + gameSize), 0, 0);
    }

    private ArrayList<StackPane> stackPaneArrayList = new ArrayList<>();
    private ArrayList<Cell> cells = new ArrayList<>();

    public void initContent(GridPane pane) {
        //ToDO: Få GameSize fra StartWindow
        cells = Controller.createNewGame(gameSize);
        cellWithBomb = getCellsWithBomb();
        for (Cell cell : cells) {

            Rectangle rectangle = new Rectangle(25, 25);
            String cellText = cell.getCellText().equals("0") ? "" : cell.getCellText();
            Text text = new Text(cellText);

            rectangle.setStyle("-fx-fill: grey; -fx-stroke: black; -fx-stroke-width: 1;");
            rectangle.setUserData(cell);

            StackPane stackPane = new StackPane(text, rectangle);
            stackPane.setMinSize(25, 25);
            stackPane.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");
            stackPaneArrayList.add(stackPane);
            stackPane.setUserData("noFlag");
            stackPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) leftClick(stackPane);
                else if (event.getButton() == MouseButton.SECONDARY) rightClick(stackPane);
            });
            pane.add(stackPane, cell.getX(), cell.getY());
        }
    }
    private Set<Cell> placedFlags = new HashSet<>();

    private void leftClick(StackPane stackPane) {
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
            lostAlert();
            System.out.println("DU TABTE"); //TODO LOST-Metode
        } else{
            stackPane.getChildren().remove(1);
            revealEmptyCells(cell);
            checkIfGameIsWon();
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
            placedFlags.remove(cell);
            return;
        }
        Rectangle flag = new Rectangle(15, 15);
        flag.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
        stackPane.getChildren().add(flag);
        stackPane.setUserData("Flag");
        placedFlags.add(cell);
        checkIfGameIsWon();
    }

    private boolean firstClick = true;
    private void revealEmptyCells(Cell cell){
        for (ArrayList<Integer> position : Controller.getPositionsAroundCell()) {
            checkIfEmpty(position.get(0), position.get(1), cell);
        }
        firstClick = false;
    }

    private void checkIfEmpty(int xDif, int yDif, Cell currentCell){
        int xPos = currentCell.getX() + xDif;
        int yPos = currentCell.getY() + yDif;
        for (Cell cell : Controller.getCellsCurrentGame()) {
            if (cell.getX() == xPos && cell.getY() == yPos && (currentCell.getCellText().equals("0") || firstClick) && !cell.isShown() && !cell.isBombe()){
                int index = Controller.getCellsCurrentGame().indexOf(cell);
                if (stackPaneArrayList.get(index).getUserData().equals("Flag")) return;
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
    }
    private void winAlert(){
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Du vandt");
        alert.showAndWait();
    }

    public Scene getScene() {
        return this.scene;
    }
    ArrayList<Cell> cellWithBomb = new ArrayList<>();
    public ArrayList<Cell> getCellsWithBomb(){
        ArrayList<Cell> cellBomb = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.isBombe()){
                cellBomb.add(cell);
            }
        }
        return cellBomb;
    }

    public void checkIfGameIsWon(){
        if (placedFlags.size() != cellWithBomb.size()) return;
        //tjekker om alle bomber er flaged
        for (Cell bomb : cellWithBomb) {
            for (Cell placedFlag : placedFlags) {
                if (!cellWithBomb.contains(placedFlag)){
                    return;
                }
            }
        }
        //tjekker om alle minus bomber isShown.
        int shownCount = 0;
        for (Cell cell : cells) {
            if (cell.isShown()){
                shownCount++;
            }
        }
        if (shownCount == (cells.size()-cellWithBomb.size())){
            winAlert();
        }
    }
}
