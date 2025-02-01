package application.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game {
    private GameSize gameSize;
    public Game(GameSize gameSize) {
        this.gameSize = gameSize;
        createCells(gameSize);
    }

    private ArrayList<Cell> cellsCurrentGame = new ArrayList<>();

    public void createCells(GameSize gameSize){
        ArrayList<Cell> cells = new ArrayList<>();
        int numberOfCells = gameSize.getSize(); //Hvis getSize = 10, så er det 10x10 spil

        for (int y = 0; y < numberOfCells; y++) {
            for (int x = 0; x < numberOfCells; x++) {
                Cell newCell = new Cell(x, y, this);
                cellsCurrentGame.add(newCell);
            }
        }
    }

    public void setBombs(GameSize gameSize, Cell firstCell) {
        Random rand = new Random();
        int maxSize = cellsCurrentGame.size();

        Set<Integer> randomIndex = new HashSet<>();
        while (randomIndex.size() != gameSize.getAmountOfBombs()){
            int indexOfNewBomb = rand.nextInt(maxSize);
            if (indexOfNewBomb != (firstCell.getX() + firstCell.getY()* gameSize.getSize())){
                randomIndex.add(indexOfNewBomb);
            }
        }

        for (Integer index : randomIndex) {
            cellsCurrentGame.get(index).setBomb();
        }
        System.out.println(randomIndex);
    }

    public ArrayList<Cell> getCellsCurrentGame() {
        return  new ArrayList<>(cellsCurrentGame);
    }
}
