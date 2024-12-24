package application.controller;

import application.model.Cell;
import application.model.Game;
import application.model.GameSize;

import java.util.*;

public class Controller {
    public static Game createNewGame(GameSize gameSize){
        return new Game(gameSize);
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
