package gui;

import application.controller.Controller;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        initContent();
        Application.launch(PrimaryWindow.class);
    }

    private static void initContent() {
        Controller.generatePositionsAroundCell();
    }
}
