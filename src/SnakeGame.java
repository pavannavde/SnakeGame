import javax.swing.*;
public class SnakeGame extends JFrame {
    GameBoard Board;
    //constructor
    SnakeGame()
    {
        Board = new GameBoard();
        add(Board);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {

        // Initializing Snake Game
        SnakeGame snakegame = new SnakeGame();
    }
}
