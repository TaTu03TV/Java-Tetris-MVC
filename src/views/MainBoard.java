package views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controllers.GameController;

public class MainBoard extends JPanel implements ActionListener {

    private static GameController gameController;
    
    public MainBoard() {
        gameController = new GameController(this);
        this.start();
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        System.out.println("Action Performed");
    }

    public void start() {
        gameController.start();
    }

}
