package application.controller;

import application.model.Game;
import application.model.GameSize;
import application.model.Leaderboard;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

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

    private static ArrayList<Leaderboard> allLeaderboardTries = new ArrayList<>();
    private static ArrayList<Leaderboard> top3Leaderboard = new ArrayList<>();

    public static Leaderboard createLeaderboard(String name, double time){
        Leaderboard leaderboard = new Leaderboard(name, time);
        updateLeaderboardFile(leaderboard);
        return leaderboard;
    }

    public static void getLeaderboard() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/application/controller/leaderboard.txt"))){
            top3Leaderboard = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null && !line.equals("")){
                String[] splittet = line.split("/");
                double time = Double.parseDouble(splittet[0]);
                top3Leaderboard.add(new Leaderboard(splittet[1], time));
            }
        } catch (IOException ex){
            System.out.println("ERROR:     "  + ex.getMessage());
        }
        System.out.println(top3Leaderboard);
    }

    public static void updateLeaderboardFile(Leaderboard newTime){
        findPlacementInLeaderboard(newTime);
        if (top3Leaderboard.size() == 4){
            top3Leaderboard.removeLast();
        }
        try (PrintWriter printWriter = new PrintWriter(new File("src/application/controller/leaderboard.txt"))){
            StringBuilder sb = new StringBuilder();
            for (Leaderboard leaderboard : top3Leaderboard) {
                sb.append(leaderboard.getTime()+"/"+leaderboard.getName() + "\n");
            }
            printWriter.print(sb);
            printWriter.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static void findPlacementInLeaderboard(Leaderboard newTime){
        //If leaderboard is empty
        if (top3Leaderboard.isEmpty()){
            top3Leaderboard.add(newTime); return;
        }
        //If size is 2 or 3
        if (top3Leaderboard.getFirst().getTime() > newTime.getTime()){
            top3Leaderboard.addFirst(newTime); return;
        }
        else if(top3Leaderboard.getLast().getTime() < newTime.getTime()){
            top3Leaderboard.add(newTime); return;
        }


        //If size is 3, find where new time fits inbetween
        for (int i = 0; i < top3Leaderboard.size()-1; i++) {
            if ((top3Leaderboard.get(i).getTime() < newTime.getTime()) && (top3Leaderboard.get(i+1).getTime() > newTime.getTime())){
                top3Leaderboard.add(i+1, newTime); return;
            }
        }
    }

    public static ArrayList<Leaderboard> getTop3Leaderboard() {
        return top3Leaderboard;
    }
}
