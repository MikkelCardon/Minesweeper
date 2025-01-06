package gui.windows;

import application.controller.Controller;
import application.controller.StopWatch;
import application.model.Cell;
import application.model.GameOutput;
import application.model.GameSize;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static application.model.ClickMethod.leftClick;
import static application.model.ClickMethod.rightClick;

public class GameWindow {
    private Scene scene;
    private GameSize gameSize;

    public GameWindow(Stage primaryStage, GameSize gameSize) {
        this.gameSize = gameSize;
        GridPane gamePane = new GridPane();
        gamePane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        GridPane layoutPane = new GridPane();
        initLayout(layoutPane, primaryStage);
        initContent(gamePane);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().add(layoutPane);
        layout.getChildren().add(gamePane);

        this.scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //ToDo: Kan med fordel flyttes til component package, ny klasse der hedder Layout.
    private int secondsElapsed = 0;
    private static Timeline timer;
    private void initLayout(GridPane layoutPane, Stage stage) {
        Label labelForGame = new Label("GAME: " + gameSize);
        labelForGame.setStyle("-fx-font-size: 16px;");
        layoutPane.add(labelForGame, 0, 0);
        Button button = new Button("Genstart");
        layoutPane.add(button, 0, 1);
        button.setOnAction(event -> GameOutput.restartGame(stage));

        Label label = new Label("Time: ");
        Label timeLabel = new Label("0");
        timeLabel.setFont(new Font(20));
        layoutPane.add(label, 0, 2);
        layoutPane.add(timeLabel, 1, 2);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            timeLabel.setText(String.valueOf(secondsElapsed));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private ArrayList<StackPane> stackPaneArrayList = new ArrayList<>();
    private ArrayList<Cell> cells = new ArrayList<>();

    public void initContent(GridPane pane) {
        cells = Controller.createNewGame(gameSize).getCellsCurrentGame();
        cellWithBomb = getCellsWithBomb();
        for (Cell cell : cells) {

            Rectangle rectangle = new Rectangle(25, 25);
            String cellText = cell.getCellText().equals("0") ? "" : cell.getCellText();
            Text text = new Text(cellText);
            colorOfText(text, cell);
            text.setFont(new Font(20));
            rectangle.setStyle("-fx-fill: grey; -fx-stroke: black; -fx-stroke-width: 1;");
            rectangle.setUserData(cell);

            StackPane stackPane = new StackPane(text, rectangle);
            stackPane.setMinSize(25, 25);
            stackPane.setStyle("-fx-border-color: grey; -fx-border-width: 0.5;");
            stackPaneArrayList.add(stackPane);
            stackPane.setUserData("noFlag");
            stackPane.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) leftClick(stackPane, this);
                else if (event.getButton() == MouseButton.SECONDARY) rightClick(stackPane, this);
            });
            pane.add(stackPane, cell.getX(), cell.getY());
        }
    }
    private Set<Cell> placedFlags = new HashSet<>();

    private boolean firstClick = true;
    public void revealEmptyCells(Cell cell){
        if (firstClick){
            StopWatch.start();
            timer.play();
        }
        for (ArrayList<Integer> position : Controller.getPositionsAroundCell()) {
            checkIfEmpty(position.get(0), position.get(1), cell);
        }
        firstClick = false;
    }

    public void checkIfEmpty(int xDif, int yDif, Cell currentCell){
        int xPos = currentCell.getX() + xDif;
        int yPos = currentCell.getY() + yDif;
        for (Cell cell : cells) {
            if (cell.getX() == xPos && cell.getY() == yPos && (currentCell.getCellText().equals("0") || firstClick) && !cell.isShown() && !cell.isBombe()){
                int index = cells.indexOf(cell);
                if (stackPaneArrayList.get(index).getUserData().equals("Flag")) return;
                stackPaneArrayList.get(index).getChildren().removeLast();
                cell.setShown(true);
                if(cell.getCellText().equals("0")){
                    revealEmptyCells(cell);
                }
            }
        }
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
            GameOutput.winAlert(this);
        }
    }
    private void colorOfText(Text text, Cell cell){
        String number = cell.getCellText();
        Color color = switch (number) {
            case "1" -> Color.BLUE;
            case "2" -> Color.GREEN;
            case "3" -> Color.RED;
            default -> Color.BLACK;
        };
        text.setFill(color);
    }

    public ArrayList<StackPane> getStackPaneArrayList() {
        return stackPaneArrayList;
    }

    public static Timeline getTimer() {
        return timer;
    }

    public Set<Cell> getPlacedFlags() {
        return placedFlags;
    }
}
