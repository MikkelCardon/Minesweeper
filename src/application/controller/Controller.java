package application.controller;

import application.model.Cell;
import application.model.GameSize;

import java.util.*;

public class Controller {

    public static ArrayList<Cell> createNewGame(GameSize gameSize){
        //ToDo: Skal lave x-antal nye celler
        //ToDo: lave x-antal til bomber
        ArrayList<Cell> cells = new ArrayList<>();
        int numberOfCells = gameSize.getSize(); //Hvis getSize = 10, så er det 10x10 spil

        for (int y = 0; y < numberOfCells; y++) {
            for (int x = 0; x < numberOfCells; x++) {
                Cell newCell = new Cell(x, y);
                cells.add(newCell);
                cellsCurrentGame.add(newCell);
            }
        }
        setBombs(cells);
        return cells;
    }

    private static void setBombs(ArrayList<Cell> cells) {
        Random rand = new Random();
        int maxSize = cells.size();

        Set<Integer> randomIndex = new HashSet<>();
        while (randomIndex.size() != 10){
            randomIndex.add(rand.nextInt(maxSize));
        }

        for (Integer index : randomIndex) {
            cells.get(index).setBomb();
        }
        System.out.println(randomIndex);
    }

    private static ArrayList<Cell> cellsCurrentGame = new ArrayList<>();

    public static ArrayList<Cell> getCellsCurrentGame() {
        return  new ArrayList<>(cellsCurrentGame);
    }

    private static ArrayList<ArrayList<Integer>> positionsAroundCell = new ArrayList<>();
    public static void generatePositionsAroundCell(){
        positionsAroundCell.add(new ArrayList<>(Arrays.asList(0, 1)));
        positionsAroundCell.add(new ArrayList<>(Arrays.asList(0, -1)));

        positionsAroundCell.add(new ArrayList<>(Arrays.asList(1, 0)));
        positionsAroundCell.add(new ArrayList<>(Arrays.asList(-1, 0)));

        positionsAroundCell.add(new ArrayList<>(Arrays.asList(1, 1)));
        positionsAroundCell.add(new ArrayList<>(Arrays.asList(-1, -1)));

        positionsAroundCell.add(new ArrayList<>(Arrays.asList(1, -1)));
        positionsAroundCell.add(new ArrayList<>(Arrays.asList(-1, 1)));
    }

    public static ArrayList<ArrayList<Integer>> getPositionsAroundCell() {
        return new ArrayList<>(positionsAroundCell);
    }


}
