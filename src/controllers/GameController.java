package controllers;

import javax.swing.Timer;

import models.Grid;

public class GameController {

    private Timer timer;
    private Grid grid;
    
    public GameController(Grid Grid) {
        System.out.println("GameController");

        this.grid = Grid;
        timer = new Timer(500, grid);

        timer.start();
    }

    public void start() {
        System.out.println("GameController start");
    }

}
