package GUI;

/**
 *
 * @author Ahmet Kadir Aksu
 * 
 * Interface for Peg Solitaire Game with graphical user interface
 */
interface PegSolitaireGame {
    enum cell {EMPTY, PEG, OUT};
    
    void initializeBoard();
    
    void updateCellButtons();
    
    void updateBoard();
    
        
    void changeBoard(int board);
    
    void playUser(int trow, int tcolumn, char direction);

    void playAuto();
    
    boolean checkMove(int cRow, int cColumn, char cDirection);
    
    void undo();
    
    boolean endGame();
    int score();
    
    int countEmpty();

    cell[][] map1();
    cell[][] map2();
    cell[][] map3();
    cell[][] map4();
    cell[][] map5();
}
