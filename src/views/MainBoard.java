package views;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MainBoard extends JPanel implements ActionListener {
    
    public MainBoard() {
        
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        System.out.println("Action Performed");
    }

    public void start() {
        System.out.println("Start");
    }

}
