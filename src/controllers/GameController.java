package controllers;

import javax.swing.Timer;

import views.MainBoard;

public class GameController {

    private Timer timer;
    
    public GameController(MainBoard MainBoard) {
        System.out.println("GameController");

        timer = new Timer(1000, MainBoard);



        timer.start();
    }

    public void start() {
        System.out.println("GameController start");
    }

}
