import java.awt.Color;
import javax.swing.JFrame;

import GUI.PegSolitaireFrame;

/**
 *
 * @author Ahmet Kadir Aksu
 * 
 * Driver class to run Peg Solitaire game.
 */
public class Driver {
    
    public static void main(String args[]){
        /**
         * Starts the game with board 2
         */
        PegSolitaireFrame gameFrame = new PegSolitaireFrame(2);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.getContentPane().setBackground(Color.decode("#A29587"));
        gameFrame.setSize(900,700);
        gameFrame.setVisible(true);
    }
}
