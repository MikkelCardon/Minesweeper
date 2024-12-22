package application.model;

import application.controller.Controller;
import com.sun.source.tree.LiteralTree;

import java.util.ArrayList;

public class Cell {
    private boolean isBombe = false;
    private boolean isShown = false;
    private String cellText = "X";

    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBombe() {
        return isBombe;
    }

    public String getCellText() {
        return String.valueOf(bomberOmkring());
    }

    public int bomberOmkring(){
        int amountOfBombs = 0;
        for (ArrayList<Integer> pos : Controller.getPositionsAroundCell()) {
            if (checkBombe(pos.getFirst(), pos.getLast())) {
                amountOfBombs++;
            }
        }
        return amountOfBombs;
    }

    public boolean checkBombe(int xDif, int yDif){
        int xPos = x + xDif;
        int yPos = y + yDif;
        for (Cell cell : Controller.getCellsCurrentGame()) {
            if (cell.getX() == xPos && cell.getY() == yPos){
                return cell.isBombe;
            }
        }
        return false;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public void setBomb() {
        this.cellText = "X";
        this.isBombe = true;
    }


}
