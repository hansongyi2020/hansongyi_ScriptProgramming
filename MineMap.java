package minesweeper;

/**
 * Locations of Mines
 */
public class MineMap {
   // package access
   int numMines;
   boolean[][] isMined = new boolean[GameBoardPanel.ROWS][GameBoardPanel.COLS];
         // default is false

   // Constructor
   public MineMap() {
      super();
   }

   // Allow user to change the rows and cols
   //select minesweeper
   public void newMineMap(int numMines) {
      this.numMines = numMines;
      // Hardcoded for illustration and testing, assume numMines=10
      //isMined randomly 
      for (int row = 0; row < this.numMines; ++row) {
    	  isMined[(int)(Math.random()*10)][(int)(Math.random()*10)] = true;
      }      
}}