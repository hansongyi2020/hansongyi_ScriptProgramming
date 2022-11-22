package minesweeper;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for the game properties
   public static final int ROWS = 10;      // number of cells
   public static final int COLS = 10;

   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;  // Cell width and height, in pixels
   public static final int CANVAS_WIDTH  = CELL_SIZE * COLS; // Game board width/height
   public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

   // Define properties (package-visible)
   /** The game board composes of ROWSxCOLS cells */
   static Cell cells[][] = new Cell[ROWS][COLS];
   
   /**numMines */
   static int numMine;
 
   /**set level */
   public static void setlevel() {
	   //Difficulty level selection dialog
	   // 확인 창은 int 형으로 반환하며
	   // 0, 1, 2 중 하나입니다.
	   String[] buttons1 = {"Beginner", "Intermediate", "Advanced"};
	   int num = JOptionPane.showOptionDialog(null, "Select Level(Beginner, Intermediate, Advanced)", "Select Level",
			   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons1, "Beginner");
		  
	   if(num == 0) {  
		   numMine = 5;
		   System.out.println("Level: Beginner");
	   }else if(num == 1){
		   numMine = 10;
		   System.out.println("Level: Intermediate");
	   }else {
		   numMine = 15;
		   System.out.println("Level: Advanced");
	   }   
   }
   /** Constructor */
   public GameBoardPanel() {
	  super.setLayout(new GridLayout(ROWS, COLS, 2, 2));  // JPanel
	  
      // Allocate the 2D array of Cell, and added into content-pane. 셀 하나마다 객체 생성
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);
         }
      }

      // [TODO 3] Allocate a common listener as the MouseEvent listener for all the
      //  Cells (JButtons)
      CellMouseListener listener = new CellMouseListener();
      // [TODO 4] Every cell adds this common listener
      for (int row = 0; row < ROWS; ++row) {
       for (int col = 0; col < COLS; ++col) {
          cells[row][col].addMouseListener(listener);   // For all rows and cols
       }
      }

      // Set the size of the content-pane and pack all the components
      //  under this container.
      super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
   }

   // Initialize and re-initialize a new game
   public static void newGame() {
      // Get a new mine map
	  int numMines = 0;
	  CellMouseListener.countFlag = 3;
	  setlevel();
      MineMap mineMap = new MineMap();
      mineMap.newMineMap(numMine);//minesweeper 초기화

      // Reset cells, mines, and flags
      for (int row = 0; row < ROWS; row++) {
         for (int col = 0; col < COLS; col++) {
            // Initialize each cell with/without mine
            cells[row][col].newGame(mineMap.isMined[row][col], numMines);
         }   
      }
      View.main(null);
      View.count = 10;
   }

   // Return the number of mines [0, 8] in the 8 neighboring cells
   //  of the given cell at (srcRow, srcCol).
   //주변을 돌면서 minesweeper 개수 측정
   private int getSurroundingMines(int srcRow, int srcCol) {
      int numMines = 0;
      for (int row = srcRow - 1; row <= srcRow + 1; row++) {
         for (int col = srcCol - 1; col <= srcCol + 1; col++) {
            // Need to ensure valid row and column numbers too
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
               if (cells[row][col].isMined) numMines++;
            }
         }
      }
      return numMines;
   }

   // Reveal the cell at (srcRow, srcCol)
   // If this cell has 0 mines, reveal the 8 neighboring cells recursively
   private void revealCell(int srcRow, int srcCol) {
      int numMines = getSurroundingMines(srcRow, srcCol);
	  cells[srcRow][srcCol].setText(numMines + "");
	  cells[srcRow][srcCol].isRevealed = true;
	  cells[srcRow][srcCol].paint(numMines);  // based on isRevealed
      if (numMines == 0) {
        // Recursively reveal the 8 neighboring cells
         for (int row = srcRow - 1; row <= srcRow + 1; row++) {
            for (int col = srcCol - 1; col <= srcCol + 1; col++) {
               // Need to ensure valid row and column numbers too
               if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                  if (!cells[row][col].isRevealed) revealCell(row, col);
               }
            }
         }
      }
   }

   // Return true if the player has won (all cells have been revealed or were mined)
   public boolean hasWon() {
      // ......
      return true;
   }

   // [TODO 2] Define a Listener Inner Class
   private class CellMouseListener extends MouseAdapter {
	   static int countFlag = 3;
	  @Override
      public void mouseClicked(MouseEvent e) {
		 // Get the source object that fired the Event
         Cell sourceCell = (Cell)e.getSource();
         // For debugging
         System.out.println("You clicked on (" + sourceCell.row + "," + sourceCell.col + ")");

         // Left-click to reveal a cell; Right-click to plant/remove the flag.
         if (e.getButton() == MouseEvent.BUTTON1) {  // Left-button clicked
            // [TODO 5]
            // if you hit a mine, game over
            // else reveal this cell
            if (sourceCell.isMined) {//if hit a mine
               System.out.println("Game Over");
               sourceCell.setText("*");
               //dialog
               String[] buttons2 = {"New Game", "exit"};
        	   int result = JOptionPane.showOptionDialog(null, "Game Over!", "Game Result",
        			   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons2, "New Game");
        	   System.out.println("Game Result: " + result);
	        	   if(result==0){//if new game
	        		   newGame();
	        	   }else {System.exit(0);}///if exit
            } else {//if not hit a mine
               revealCell(sourceCell.row, sourceCell.col);
               if(checkPlayerWon()) {//if 모든 셀을 다 돌았을 떄
                 System.out.println("Player Won!!");         
                 //dialog
                 String[] buttons3 = {"New Game", "exit"};
             	 int result = JOptionPane.showOptionDialog(null, "Player Won!!", "Game Result",
             			 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons3, "Player Won!!");
             	 System.out.println("Game Result: " + result);
	             	 if(result==0){
	             		newGame();
	            	 }else {System.exit(0);}
               }
            }
         } else if(e.getButton() == MouseEvent.BUTTON3) { // right-button clicked
            // [TODO 6]
            // If this cell is flagged, remove the flag
            // else plant a flag.   
        	if(countFlag > 0) {
		        if(sourceCell.isFlagged) {//if flag
		             System.out.println("Remove a flag");
		             System.out.println("remain flag: " + countFlag);
		             sourceCell.setText("");
		             sourceCell.isFlagged = false;
		         } else {    //if not flag     	
			         System.out.println("Plant a flag");
			         sourceCell.setText(">");
			         sourceCell.isFlagged = true;
			         if(cells[sourceCell.row][sourceCell.col].isMined) {
			        	 revealCell(sourceCell.row, sourceCell.col);
			         }
			         System.out.println("You find a flag");
			         --countFlag;
			         System.out.println("remain flag: " + countFlag);
			     }
        	}
        	else{
        		System.out.println("Flag missing!");
        		JOptionPane.showMessageDialog(null, "Flag missing!");
        	}
         }
	  }
      // [TODO 7] Check if the player has won, after revealing this cell
      public boolean checkPlayerWon(){
         boolean checkp_won = true;
         for (int row = 0; row < ROWS; row++) {
             for (int col = 0; col < COLS; col++) {         
                if(!(cells[row][col].isMined)) {//만약 지뢰가 아닐때
                 //공개된 셀이 아니라면 player는 not won
                  if(!(cells[row][col].isRevealed)) checkp_won = false;}
                }}
         return checkp_won;
     }

   }
}