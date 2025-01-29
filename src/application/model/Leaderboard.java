package application.model;

import java.util.concurrent.TimeUnit;

public class Leaderboard {

    private String name;
    private double time;
    private static int trySTART_ID = 1;
    private int tryID;

    public Leaderboard(String name, double time) {
        this.name = name;
        this.time = time;
        this.tryID = trySTART_ID++;
    }

    public double getTime() {
        return time;
    }

    public int getTryID() {
        return tryID;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return time + "s - " + name;
    }

}
