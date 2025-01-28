package gui;

import application.controller.Controller;
import application.model.Leaderboard;
import javafx.application.Application;

import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws IOException {
        initContent();
        Controller.getLeaderboard();
        Application.launch(PrimaryWindow.class);
    }

    private static void initContent() {
        Controller.generatePositionsAroundCell();
    }
}
