package application.model;

public enum GameSize {
    SMALL(5, 5), MEDIUM(10, 10), LARGE(20, 40);

    private int size;
    private int amountOfBombs;

    GameSize(int size, int amountOfBombs) {
        this.size = size;
        this.amountOfBombs = amountOfBombs;
    }

    public int getSize() {
        return size;
    }

    public int getAmountOfBombs() {
        return amountOfBombs;
    }
}
