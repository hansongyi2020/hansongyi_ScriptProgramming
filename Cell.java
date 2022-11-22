package minesweeper;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
/**
 * The Cell class model the cells of the MineSweeper, by customizing (subclass)
 *   the javax.swing.JButton to include row/column and states.
 */
public class Cell extends JButton {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for JButton's colors and fonts
   //  to be chosen based on cell's state
   public static final Color BG_NOT_REVEALED = Color.GRAY;
   public static final Color FG_NOT_REVEALED = Color.RED;    // flag, mines
   public static final Color BG_REVEALED = Color.DARK_GRAY;
   public static final Color FG_REVEALED_1 = Color.GREEN;
   public static final Color FG_REVEALED_2 = Color.YELLOW;
   public static final Color FG_REVEALED_3 = Color.WHITE;// number of mines
   public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

   // Define properties (package-visible)
   /** The row and column number of the cell */
   int row, col;
   /** Already revealed? */
   boolean isRevealed;
   /** Is a mine? */
   boolean isMined;
   /** Is Flagged by player? */
   boolean isFlagged;

   /** Constructor */
   public Cell(int row, int col) {
      super();   // JTextField
      this.row = row;
      this.col = col;
      // Set JButton's default display properties
      super.setFont(FONT_NUMBERS);
   }

   /** Reset this cell, ready for a new game */
   public void newGame(boolean isMined, int numMines) {
      this.isRevealed = false; // default
      this.isFlagged = false;  // default
      this.isMined = isMined;  // given
      super.setEnabled(true);  // enable button
      super.setText("");       // display blank
      paint(numMines);
   }

   /** Paint itself based on its status */
   public void paint(int numMines) {
	   if(isRevealed) {
		   super.setBackground(BG_REVEALED);
		   if(numMines == 1) {
			   super.setForeground(FG_REVEALED_1);
		   } else if(numMines == 2) {
			   super.setForeground(FG_REVEALED_2);
		   } else if(numMines == 0) {
			   super.setText("");
		   } else {
			   super.setForeground(FG_REVEALED_3);
		   }
	   }
	   else {
		   super.setBackground(BG_NOT_REVEALED);
		   super.setForeground(FG_NOT_REVEALED);
	   }	  
   }
}