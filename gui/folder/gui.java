package gui.folder;
import java.awt.Color;
import javax.swing.JFrame;
/**
 *
 * @author ahmet
 * 
 * I am not sure but I think it works only with JDK 17 Compiler or newer.
 * I tried to compile with jdk 11 but it did not work,
 * and I am not sure why
 */
public class gui {
    
    public static void main(String args[]){
        /**
         * Starts the game with board 2
         */
        PegSolitaireFrame gameFrame = new PegSolitaireFrame(2);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.getContentPane().setBackground(Color.GREEN);
        gameFrame.setSize(900,700);
        gameFrame.setVisible(true);
    }
}
