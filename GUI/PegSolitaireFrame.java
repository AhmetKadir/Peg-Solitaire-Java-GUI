package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.Timer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Ahmet Kadir Aksu
 * 
 * Peg Solitaire Game with graphical user interface.
 */
public class PegSolitaireFrame extends JFrame
                                implements PegSolitaireGame{
    
    /**
     * Components
     */
    private JTextField fileNameFieldLoad;
    private JTextField fileNameFieldSave;
    private JLabel textScore;
    private JButton autoPlayButton;
    private JButton undoButton;
    private JButton resetButton;
    private JButton loadButton;
    private JButton saveButton;
    private Timer timer, timer2;
    private JRadioButton board1Button,
                         board2Button,
                         board3Button,
                         board4Button,
                         board5Button;
    private ButtonGroup radioGroup;
        
    /*array for keep cell buttons*/
    private JButton[] buttons = new JButton[81];
    int clickNumber = 0;
    private int x_loc = 0;
    private int y_loc = 0;
    int x_location, y_location;
    int button_index;
    boolean radioButtonControl = false;
    boolean []action = new boolean[81];
    
    /**
     * Variables to use for undo function
     */
    private int last_row;
    private int last_column;
    private char last_direc;
    
    
    cell[][] gameBoard = {
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
    {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
    {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
    {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
    {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT}
    };
    

    public PegSolitaireFrame(int boardType){
        super("Peg Solitaire");
        setLayout(null);
        System.out.println("Board Type is " + boardType);
        changeBoard(boardType);
        initializeBoard();
        
        updateCellButtons();
        
        autoPlayButton = new JButton("Auto Play");
        autoPlayButton.setBounds(650, 200 , 200, 50);
        add(autoPlayButton);
        
        undoButton = new JButton("Undo");
        undoButton.setBounds(650, 275 , 200, 50);
        add(undoButton);
        
        resetButton = new JButton("Reset");
        resetButton.setBounds(650, 350, 200, 50);
        add(resetButton);
   
        saveButton = new JButton ("Save");
        saveButton.setBounds(650, 500, 200, 50);
        add ( saveButton ); 
        
        loadButton = new JButton("Load");
        loadButton.setBounds(650, 425, 200, 50);
        add(loadButton);
        
        fileNameFieldLoad = new JTextField("Enter the fileName(Ex: game1)");
        fileNameFieldLoad.setBounds(250, 550, 200, 50);
        fileNameFieldLoad.setVisible(false);
        fileNameFieldLoad.setBackground(Color.CYAN);
        fileNameFieldLoad.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        add(fileNameFieldLoad);
        
        fileNameFieldSave = new JTextField("Enter the fileName(Ex: game1)");
        fileNameFieldSave.setBounds(250, 550, 200, 50);
        fileNameFieldSave.setVisible(false);
        fileNameFieldSave.setBackground(Color.CYAN);
        fileNameFieldSave.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        add(fileNameFieldSave);
        
        TextFieldHandler handler = new TextFieldHandler();
        fileNameFieldLoad.addActionListener( handler );
        fileNameFieldSave.addActionListener( handler );
        
        textScore = new JLabel(String.format(
                                    "SCORE: %d",score())
        );
        textScore.setBounds(650, 50, 150, 50);
        textScore.setBackground(Color.WHITE);
        textScore.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        textScore.setForeground(Color.BLACK);
        add(textScore);
        
        if(boardType == 1)
             board1Button = new JRadioButton("Board 1", true);
        else board1Button = new JRadioButton("Board 1", false);
        board1Button.setBounds(50, 500, 100, 40);
        add(board1Button);
        
        if(boardType == 2)
             board2Button = new JRadioButton("Board 2", true);
        else board2Button = new JRadioButton("Board 2", false);
        board2Button.setBounds(150, 500, 100, 40);
        add(board2Button);
        
        
        if(boardType == 3)
             board3Button = new JRadioButton("Board 3", true);
        else board3Button = new JRadioButton("Board 3", false);
        board3Button.setBounds(250, 500, 100, 40);
        add(board3Button);
        
        if(boardType == 4)
             board4Button = new JRadioButton("Board 4", true);
        else board4Button = new JRadioButton("Board 4", false);
        board4Button.setBounds(350, 500, 100, 40);
        add(board4Button);

        if(boardType == 5)
             board5Button = new JRadioButton("Board 5", true);
        else board5Button = new JRadioButton("Board 5", false);
        board5Button.setBounds(450, 500, 100, 40);
        add(board5Button);
        
        radioGroup = new ButtonGroup();
        radioGroup.add(board1Button);
        radioGroup.add(board2Button);
        radioGroup.add(board3Button);
        radioGroup.add(board4Button);
        radioGroup.add(board5Button);
        /*radioGroup.add(board6Button);*/
        
        board1Button.addItemListener(
            new RadioButtonHandler(1));
        board2Button.addItemListener(
            new RadioButtonHandler(2));
        board3Button.addItemListener(
            new RadioButtonHandler(3));
        board4Button.addItemListener(
            new RadioButtonHandler(4));
        board5Button.addItemListener(
            new RadioButtonHandler(5));
        /*board6Button.addItemListener(
            new RadioButtonHandler(6));*/
        
        
        

        /**
         * item listener for board radio buttons
         */
        

        //actionlistener for autoPlay button
        autoPlayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(endGame() == true){
                    timer.stop(); 
                    JOptionPane.showMessageDialog
                        (PegSolitaireFrame.this,String.format(
                            "GAME IS OVER\n YOUR SCORE IS %d",score()));
                    return;
                }
                timer = new Timer(500, new ActionListener() {
                @Override
                    public void actionPerformed(ActionEvent e) {  
                        playAuto();
                        updateBoard();
                        autoPlayButton.setBackground(Color.BLACK);
                        autoPlayButton.setForeground(Color.WHITE);
                        repaint();   
                        revalidate();
                        if(endGame() == true){
                            timer.stop(); 
                            JOptionPane.showMessageDialog
                                (PegSolitaireFrame.this,String.format(
                                    "GAME IS OVER\n YOUR SCORE IS %d",score()));
                        }
                    }
                });
                timer.setRepeats(true);
                timer.setCoalesce(true);
                timer.setInitialDelay(0);
                timer.start();
                if(endGame()==true)timer.stop();
            }
        });//end actionlistener for autoPlay button
        
        //action listener for undo button
        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(countEmpty() == 1) return; 
                undo();      
                updateBoard();
                repaint();
                revalidate();
            };
        });//end actionlistener for undo button
        
        //action listener for reset button
        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                PegSolitaireFrame newGame = new PegSolitaireFrame(boardType);
                newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newGame.setResizable(false);
                newGame.getContentPane().setBackground(Color.decode("#A29587"));
                newGame.setSize(900,700);
                newGame.setVisible(true);
                dispose();
            };
        });//end actionlistener for reset button
        
        /**
         * Action Listener for save button
         */
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileNameFieldSave.setVisible(true);
            };
        });
        
        /**
         * ActionListener for load button
         */
        loadButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            fileNameFieldLoad.setVisible(true);
            //fileNameFieldLoad.setVisible(false);
            };
        });
    } // end Constructor

    
   /* private class ButtonHandler implements ActionListener{
        //handle button event
        private int x_loc = 1;
        private int y_loc = JButton.WIDTH;
        public void actionPerformed(ActionEvent event){
            //playUser("02-R");
            JOptionPane.showMessageDialog(PegSolitaireFrame.this,
                    String.format(
                        "You pressed: %d %d",x_loc, y_loc));
        }
    }*/
    
    //item listener for radio buttons
    private class RadioButtonHandler implements ItemListener {
        int board;
        public RadioButtonHandler(int selectedBoard) {
            board = selectedBoard;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if(radioButtonControl == true){
                setVisible(false);
                PegSolitaireFrame newGame = new PegSolitaireFrame(board);
                newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newGame.setResizable(false);
                newGame.getContentPane().setBackground(Color.decode("#A29587"));
                newGame.setSize(900,700);
                newGame.setVisible(true);
                dispose();
            }
            else radioButtonControl = true;
            
        }
    }//end item listener for radio buttons
    
    public class TextFieldHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String str ="";
            if(event.getSource() == fileNameFieldLoad){
                str = String.format("%s",
                        event.getActionCommand());
                try {                
                    str = str + ".txt";
                    var loadF=new FileInputStream(str); 
                    Scanner sc = new Scanner(loadF);
                    while(sc.hasNextLine()){
                        for(int i = 0; i < gameBoard.length; i++){
                            for(int j=0 ; j< gameBoard[i].length; j++){
                                switch(sc.nextLine()){
                                    case "peg":
                                        gameBoard[i][j] = cell.PEG;
                                        break;
                                    case "out":
                                        gameBoard[i][j] = cell.OUT;
                                        break;
                                    case "empty":
                                        gameBoard[i][j] = cell.EMPTY;
                                        break;                       
                                }
                            }
                        }
                    }
                    sc.close();
                    System.out.println("\nReading from the file is successful");
                    JOptionPane.showMessageDialog(null, String.format("DONE"));
                    updateBoard();
		}catch(IOException exc) {  
                    JOptionPane.showMessageDialog(null
                            , String.format("Could not find the file !"));
                    exc.printStackTrace();  
                }  
                fileNameFieldLoad.setVisible(false);
            }

            else if (event.getSource() == fileNameFieldSave){
                str =String.format("%s", event.getActionCommand());
                try {
                    str = str + ".txt";
                    System.out.println(str);
                    var savedF = new FileWriter(str);
                    for(int i = 0; i < gameBoard.length; i++){
                        for(int j=0 ; j< gameBoard[i].length; j++){
                            switch(gameBoard[i][j]){
                                case PEG:
                                    savedF.write("peg");
                                    break;
                                case OUT:
                                    savedF.write("out");
                                    break;
                                case EMPTY:
                                    savedF.write("empty");
                                    break;                       
                            }
                            savedF.write("\n");
                        }
                    }
                    savedF.close();
                    System.out.println("\nWriting to the file is successful");
                    JOptionPane.showMessageDialog(null, String.format("DONE"));
		} catch (IOException exc) {
                    System.out.println("ERROR");
                    exc.printStackTrace();
                }
                fileNameFieldSave.setVisible(false);
            }
            
            
            
        }
    }
    
    public void initializeBoard(){
        button_index = 0;
        x_location = 50;
        y_location = 50;
        for(int i = 0 ; i < gameBoard.length; i++){
            for(int j = 0; j<gameBoard[i].length; j++){
                if(gameBoard[i][j] == cell.PEG){
                    buttons[button_index] = new JButton("P");
                    buttons[button_index].setBounds(x_location, y_location, 45, 45);  
                    buttons[button_index].setBackground(Color.decode("#7261A3"));
                    action[button_index]=true;
                    buttons[button_index].setToolTipText
                                (String.format("(%d,%d)",i,j));
                    add(buttons[button_index]);
                }
                
                else if(gameBoard[i][j] == cell.OUT){
                    buttons[button_index] = new JButton("");
                    buttons[button_index].setBounds(x_location, y_location,45,45);
                    buttons[button_index].setBackground(Color.decode("#0F0F0F"));
                    action[button_index]=false;
                    add(buttons[button_index]);
                }
                
                else if(gameBoard[i][j] == cell.EMPTY){
                    buttons[button_index] = new JButton("");
                    buttons[button_index].setBounds(x_location, y_location ,45,45);
                    buttons[button_index].setBackground(Color.decode("#D4C2FC"));
                    action[button_index]=true;
                    buttons[button_index].setToolTipText
                                (String.format("(%d,%d)",i,j));
                    add(buttons[button_index]);
                }
                button_index++;
                x_location += 48;
          
            }
            y_location+=48;
            x_location = 50;
        }
    }
    
    public void updateCellButtons(){
        for(int i = 0; i<button_index; i++){
            if(action[i] == true){
                int index = i;
                int x_loc2 = i/9;
                int y_loc2 = i%9;
                     
                /**
                 * Actionlistener for cell buttons.
                 */
                buttons[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        clickNumber++;
                        if(endGame() == true) {
                            JOptionPane.showMessageDialog
                                (PegSolitaireFrame.this,String.format(
                                     "GAME IS OVER\n YOUR SCORE IS %d",score()));
                            return;
                        }
                        if(clickNumber == 1){
                            for(int a =0; a<gameBoard.length; a++){
                                for(int j = 0; j<gameBoard[a].length; j++){
                                   buttons[index].setBackground(Color.decode("#F8F1FF"));

                                }
                            }
                            x_loc = x_loc2;
                            y_loc = y_loc2;
                        }
                        
                        else if(clickNumber == 2){     
                            if(x_loc == x_loc2 && y_loc == y_loc2 - 2)
                                playUser(x_loc, y_loc, 'R');

                            else if(x_loc == x_loc2 && y_loc == y_loc2 + 2)
                                playUser(x_loc, y_loc, 'L');

                            else if(x_loc + 2 == x_loc2 && y_loc == y_loc2)
                                playUser(x_loc, y_loc, 'D');
                                
                            else if(x_loc - 2 == x_loc2 && y_loc == y_loc2)
                                playUser(x_loc, y_loc, 'U');
                            updateBoard();
                            
                            if(endGame() == true) {
                                JOptionPane.showMessageDialog
                                    (PegSolitaireFrame.this,String.format(
                                        "GAME IS OVER\n YOUR SCORE IS %d",score()));
                            return;
                        }
                            clickNumber = 0;
                        }
                    }
                });
                
            }
        }//end actionlistener for cell buttons
    }
    public void updateBoard(){
        x_location = 50; 
        y_location = 50;
        button_index = 0;                
        for(int i = 0 ; i < gameBoard.length; i++){
            for(int j = 0; j<gameBoard[i].length; j++){
                if(gameBoard[i][j] == cell.PEG){
                    buttons[button_index].setText("P");
                    buttons[button_index].setBounds(x_location, y_location, 45, 45);  
                    buttons[button_index].setBackground(Color.decode("#7261A3"));
                    action[button_index]=true;
                    buttons[button_index].setToolTipText
                                (String.format("(%d,%d)",i,j));
                    add(buttons[button_index]);
                }
                
                else if(gameBoard[i][j] == cell.OUT){
                    buttons[button_index].setText("");
                    buttons[button_index].setBounds(x_location, y_location,45,45);
                    buttons[button_index].setBackground(Color.decode("#0F0F0F"));
                    action[button_index]=false;
                    add(buttons[button_index]);
                }
                
                else if(gameBoard[i][j] == cell.EMPTY){
                    buttons[button_index].setText("");
                    buttons[button_index].setBounds(x_location, y_location ,45,45);
                    buttons[button_index].setBackground(Color.decode("#D4C2FC"));
                    action[button_index]=true;
                    buttons[button_index].setToolTipText
                                (String.format("(%d,%d)",i,j));
                    add(buttons[button_index]);
   
                }
                button_index++;
                x_location += 48;
                
            }
            y_location+=48;
            x_location = 50;
        }
        textScore.setText(String.format(
                                    "SCORE: %d",score()));
    }// end updateBoard
        
    public void changeBoard(int board){
        switch(board){
            case 1:
                for(int i = 0; i <map1().length; i++){
                    for(int j =0; j<map1()[i].length; j++){
                        gameBoard[i][j] = map1()[i][j];
                    }
                }
                break;
            case 2:
                for(int i = 0; i <map2().length; i++){
                    for(int j =0; j<map2()[i].length; j++){
                        gameBoard[i][j] = map2()[i][j];
                    }
                }
                break; 
             case 3:
                for(int i = 0; i <map3().length; i++){
                    for(int j =0; j<map3()[i].length; j++){
                        gameBoard[i][j] = map3()[i][j];
                    }
                }
                break;
             case 4:
                for(int i = 0; i <map4().length; i++){
                    for(int j =0; j< map4()[i].length; j++){
                        gameBoard[i][j] = map4()[i][j];
                    }
                }
                break;
            case 5:
                for(int i = 0; i < map5().length; i++){
                    for(int j =0; j< map5()[i].length; j++){
                        gameBoard[i][j] = map5()[i][j];
                    }
                }
                break;
        }
    }
    
    public void playUser(int trow, int tcolumn, char direction){                         
        if(checkMove(trow, tcolumn, direction) == false) return;
        last_row = trow;
        last_column= tcolumn;
        last_direc = direction;
        gameBoard[trow][tcolumn]=cell.EMPTY;
        switch(direction){                                         
            case 'R':
                gameBoard [trow][tcolumn+1]=cell.EMPTY;
                gameBoard [trow][tcolumn+2]=cell.PEG;
                break;
            case 'L':
                gameBoard [trow][tcolumn-1]=cell.EMPTY;
                gameBoard [trow][tcolumn-2]=cell.PEG;
                break;
            case 'U':
                gameBoard [trow-1][tcolumn]=cell.EMPTY;
                gameBoard [trow-2][tcolumn]=cell.PEG;
                break;
            case 'D':
                gameBoard [trow+1][tcolumn]=cell.EMPTY;
                gameBoard [trow+2][tcolumn]=cell.PEG;
                break;
            default:
                break;    
        }               
    }//end PlayUser

    public void playAuto(){
        int trow;
        int tcolumn,rDirec;
        int validMove;
        char direction = 'R';
        int xsize = gameBoard.length;
        int ysize = gameBoard[0].length;
       
        /*Generates random values and get a random move*/
        do{
            trow = (int)Math.floor(Math.random()*(xsize-1+1)+0);                    
            tcolumn = (int)Math.floor(Math.random()*(ysize-1+1)+0);
            rDirec = (int)Math.floor(Math.random()*(3-0+1)+0);;
            switch(rDirec){
                case 0 :
                    direction = 'R';
                    break;
                case 1 :
                    direction = 'L';
                    break;
                case 2 :
                    direction = 'U';
                    break;
                case 3 :
                    direction = 'D';
                    break;
            }
            /*After generating a random move, it checks if the move is valid or not.
            If move is not valid, generates a new move and tries again
            If move is valid, then makes the move.*/
            if(gameBoard[trow][tcolumn] == cell.OUT 
                    || gameBoard[trow][tcolumn] == cell.EMPTY)
                    validMove = 0;
            else if (checkMove(trow, tcolumn, direction) == true) 
                validMove = 1;
            else validMove = 0;
            
            if(validMove == 1){           
                int index = 0;
                int index1 = trow*9 + tcolumn;
                int index2 = 2;
                gameBoard [trow][tcolumn] =cell.EMPTY;

                switch(direction){
                    case 'R':
                        gameBoard [trow][tcolumn+1] = cell.EMPTY;
                        gameBoard [trow][tcolumn+2] = cell.PEG;
                        index = trow*9 + tcolumn +2 ;
                        index2 = trow*9 + tcolumn +1;
                        break;
                    case 'L':
                        gameBoard [trow][tcolumn-1] =cell.EMPTY;
                        gameBoard [trow][tcolumn-2] =cell.PEG;
                        index = trow*9 + tcolumn -2 ;
                        index2 = trow*9 + tcolumn -1;

                        break;
                    case 'U':
                        gameBoard [trow-1][tcolumn] =cell.EMPTY;
                        gameBoard [trow-2][tcolumn] =cell.PEG;
                        index = (trow-2)*9 + tcolumn ;
                        index2 = (trow-1)*9 + tcolumn;
                        break;
                    case 'D':
                        gameBoard [trow+1][tcolumn] =cell.EMPTY;
                        gameBoard [trow+2][tcolumn] =cell.PEG;
                        index = (trow+2)*9 + tcolumn  ;
                        index2 = (trow+1)*9 + tcolumn;
                        break;
                }
                int index_ = index;
                int indexfirst = index1;
                int indexsecond = index2;
                timer2 = new Timer(10000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {  
                                buttons[index_].setBackground(Color.WHITE);
                                buttons[indexfirst].setBackground(Color.decode("#84DCCF"));
                                buttons[indexsecond].setBackground(Color.decode("#84DCCF"));
                                repaint();
                                timer2.stop();
                            }
                        });
                        timer2.setRepeats(true);
                        timer2.setCoalesce(true);
                        timer2.setInitialDelay(0);
                        timer2.start();
                updateBoard();
            }
        }while(validMove == 0);
    }    //end playAuto
    
    public boolean checkMove(int cRow, int cColumn, char cDirection){       
        int xsize = gameBoard.length;
        int ysize = gameBoard[0].length;
        switch (cDirection){                                                        
            case 'R':
                if (cColumn + 2 > (ysize - 1)) return false;
                if (gameBoard[cRow][cColumn + 1] != cell.PEG) return false;
                if (gameBoard[cRow][cColumn + 2] != cell.EMPTY) return false;
                break;
            case 'L':
                if (cColumn -2 < 0) return false;
                if (gameBoard[cRow][cColumn - 1] != cell.PEG) return false;
                if (gameBoard[cRow][cColumn - 2] != cell.EMPTY) return false;
                break;
            case 'U':
                if (cRow -2 < 0 ) return false;
                if (gameBoard[cRow - 1][cColumn] != cell.PEG) return false;
                if (gameBoard[cRow - 2][cColumn] != cell.EMPTY) return false;
                break;
            case 'D':
                if (cRow + 2 > (xsize - 1)) return false;
                if (gameBoard[cRow + 1][cColumn] != cell.PEG) return false;
                if (gameBoard[cRow + 2][cColumn] != cell.EMPTY) return false;
                break;
        }
        return true;
    }
    
    public void undo(){
        gameBoard[last_row][last_column]=cell.PEG;
        switch(last_direc){                                         
            case 'R':
                gameBoard [last_row][last_column+1]=cell.PEG;
                gameBoard [last_row][last_column+2]=cell.EMPTY;
                break;
            case 'L':
                gameBoard [last_row][last_column-1]=cell.PEG;
                gameBoard [last_row][last_column-2]=cell.EMPTY;
                break;
            case 'U':
                gameBoard [last_row-1][last_column]=cell.PEG;
                gameBoard [last_row-2][last_column]=cell.EMPTY;
                break;
            case 'D':
                gameBoard [last_row+1][last_column]=cell.PEG;
                gameBoard [last_row+2][last_column]=cell.EMPTY;
                break;
            default:
                break;    
        }            
    }
    
    public boolean endGame() {                                           
    /**
     * Checks every cell in the board.
     * If the cell has peg on it, 
     * and there is a peg beside the cell and there is a empty cell beside it,
     * movement is possible.
     * So the game continues.
     * If there is no cell satisfies this conditions, game is over.
     */
    int xsize = gameBoard.length;
    int ysize = gameBoard[0].length;
    for(int i=0 ; i<xsize; i++){
        for(int j=0 ; j<ysize; j++){
            if(gameBoard[i][j] == cell.PEG){
                if(j < ysize -2  && gameBoard[i][j+1] == cell.PEG 
                        && gameBoard[i][j+2] == cell.EMPTY) return false;
                if(j > 1 && gameBoard[i][j-1] == cell.PEG 
                        && gameBoard[i][j-2] == cell.EMPTY) return false;
                if(i < xsize-2 && gameBoard[i+1][j] == cell.PEG 
                        && gameBoard[i+2][j] == cell.EMPTY) return false;
                if(i > 1 && gameBoard[i-1][j] == cell.PEG 
                        && gameBoard[i-2][j] == cell.EMPTY) return false;
            }
        }
    }
    return true;
}

    public int score() {
        int score = 0; 
        int xsize = gameBoard.length;
        int ysize = gameBoard[0].length;
        for(int i=0; i<xsize; i++){
            for(int j=0; j<ysize; j++){
                if (gameBoard[i][j] == cell.PEG) score++;
            }
        }
        return score;
    }
    
    public int countEmpty(){
        int emptyCounter = 0;
            for(int i =0; i<gameBoard.length; i++){
                for(int j=0; j<gameBoard.length; j++){
                    if (gameBoard[i][j] == cell.EMPTY)
                        emptyCounter++;
                }
            }
        return emptyCounter;
    }//end countEmpty
    
    
    public cell[][] map1(){            
        cell[][] board1 = {
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT},  
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT}      
        };
        return board1;
    }

    public cell[][] map2(){
        cell[][] board2 = {
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT}
        };
        return board2;
    }

    public cell[][] map3(){
        cell[][] board3 = {
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT},  
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT}
        };
        return board3;
    }
    public cell[][] map4(){

        cell[][] board4 = {
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT},  
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.OUT},
        };
        return board4;
    }

    public cell[][] map5(){
        cell[][] board5 = {
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.OUT, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.EMPTY, cell.PEG, cell.PEG, cell.PEG, cell.PEG},
            {cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT},
            {cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.PEG, cell.PEG, cell.OUT, cell.OUT, cell.OUT},
            {cell.OUT, cell.OUT, cell.OUT, cell.OUT, cell.PEG, cell.OUT, cell.OUT, cell.OUT, cell.OUT}
        };

        return board5;
    }
}//end class PegSolitaireFrame