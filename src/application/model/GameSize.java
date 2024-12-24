package application.model;

public enum GameSize {
    SMALL(10, 15, "EASY"), MEDIUM(20, 40, "NORMAL"), LARGE(40, 160, "HARD");

    private String beskrivelse;
    private int size;
    private int amountOfBombs;

    GameSize(int size, int amountOfBombs, String beskrivelse) {
        this.beskrivelse = beskrivelse;
        this.size = size;
        this.amountOfBombs = amountOfBombs;
    }

    public int getSize() {
        return size;
    }

    public int getAmountOfBombs() {
        return amountOfBombs;
    }
    public String toString(){
        return beskrivelse + ", " + size + "x" + size;
    }
}
