package views;

import javax.imageio.ImageIO;
import javax.management.monitor.GaugeMonitor;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.TileObserver;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.Piece;

import controllers.GameController;
import models.Grid;

public class MainBoard extends JPanel implements Observer {

    private static GameController gameController;
    private static Grid currentGrid;
    private static JLabel Title;
    private static JLabel Score;
    private static JLabel NextPiece;
    private static JLabel HoldPiece;
    private static JLabel GameOver;
    private static Font font;

    private Image backgroundImage;

    public MainBoard() {
        setLayout(null);

        // load font title
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Font/telelower.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            backgroundImage = ImageIO.read(new File("assets/Mockup/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Title = new JLabel("Tetris");
        Title.setForeground(Color.YELLOW);
        Title.setFont(font.deriveFont(50f));
        Title.setBounds(200, 15, 500, 50);
        add(Title);

        Score = new JLabel("Score: 0");
        Score.setForeground(Color.RED);
        Score.setFont(font.deriveFont(25f));
        Score.setBounds(440, 100, 500, 25);
        add(Score);

        NextPiece = new JLabel("Next Piece");
        NextPiece.setForeground(Color.RED);
        NextPiece.setFont(font.deriveFont(25f));
        NextPiece.setBounds(440, 270, 500, 25);
        add(NextPiece);

        HoldPiece = new JLabel("Hold Piece");
        HoldPiece.setForeground(Color.RED);
        HoldPiece.setFont(font.deriveFont(25f));
        HoldPiece.setBounds(440, 450, 500, 25);
        add(HoldPiece);

        GameOver = new JLabel("Game Over");
        GameOver.setForeground(Color.RED);
        GameOver.setFont(font.deriveFont(60f));
        GameOver.setBounds(100, 200, 500, 70);
        add(GameOver);
        GameOver.setVisible(false);

        currentGrid = new Grid();
        currentGrid.addObserver(this);
        gameController = new GameController(currentGrid, this);
        this.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, -10, 0, this);
        drawGrid(currentGrid.returnGrid(), g);
        drawNextPiece(currentGrid.returnNextPiece(), g);
    }

    public void start() {
        currentGrid.start();
        gameController.start();
    }

    public void update(Observable o, Object arg) {
        System.out.println(arg);
        if (arg == null && !GameOver.isVisible()) {
            System.err.println("MainBoard update");
            repaint();
            Score.setText("Score: " + currentGrid.returnScore());
        } else {
            if (arg == "Game Over") {
                System.out.println("Game Over");
                if (!GameOver.isVisible())
                    clearDisplayGrid();
                GameOver.setVisible(true);

                // Affiche une boîte de dialogue
                int response = JOptionPane.showOptionDialog(null, "Game Over. Que voulez-vous faire ?", "Game Over",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new String[] { "Rejouer", "Quitter" }, "Rejouer");

                if (response == 0) {
                    // Si l'utilisateur choisit "Rejouer", réinitialisez le jeu
                    System.out.println("Rejouer");
                    resetGame();
                } else {
                    // Si l'utilisateur choisit "Quitter", arrêtez le jeu
                    System.out.println("Quitter");
                    System.exit(0);
                }
            } else {
                System.err.println("error obervation arg not recognized");
            }
        }
    }

    public void resetGame() {
        GameOver.setVisible(false);
        currentGrid.reset();
        Score.setText("Score: 0");

    }

    public void drawGrid(int[][] grid, Graphics g) {
        // clear le pannel
        g.setColor(new Color(47, 39, 41));
        g.fillRect(10, 140, 400, 800);

        // affiche des carré de couleur rouge où grid contient 1
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(TetrisColor.getColorByValue(grid[i][j]).getColor());
                    g.fillRect(10 + i * 40, 140 + j * 40, 40, 40); // décalé de 10px à gauche et 30px vers le haut
                } else {
                    g.setColor(TetrisColor.getColorByValue(grid[i][j]).getColor());
                    g.fillRect(10 + i * 40, 140 + j * 40, 40, 40); // décalé de 10px à gauche et 30px vers le haut
                }
            }
        }
    }

    public void clearDisplayGrid() {
        Graphics g = this.getGraphics();
        g.setColor(new Color(47, 39, 41));
        g.fillRect(10, 140, 400, 800);
    }

    public void drawNextPiece(Piece p, Graphics g) {
        int[][] shape = p.getShape();
        int[] pos = p.getPos();
        int blocSize = 30;
        int color = p.getColor();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.setColor(TetrisColor.getColorByValue(color).getColor());
                    g.fillRect(445 + i * blocSize, 300 + j * blocSize, blocSize, blocSize);
                }
            }
        }
    }
}
