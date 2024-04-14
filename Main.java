import views.MainFrame;
import views.Menu;

/**
 * Main class to run the program
    *

    * This class is used to run the program. It contains the main method to start the program.

    *
    * @version 1.0
    * @since 2024-04-14
    * @see MainFrame
    * @see Menu
 */
public class Main {
    private static MainFrame mainFrame;
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}