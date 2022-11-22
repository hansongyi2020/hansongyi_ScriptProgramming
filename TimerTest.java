package minesweeper;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;

public class TimerTest extends JPanel{		
	private static final long serialVersionUID = 1L;
	private TimerBar timerBar;
	private Thread threadBar;
	
	public TimerTest() {
		int second = 100;
		
		super.setLayout(null);
		
		timerBar = new TimerBar(second);
		threadBar = new Thread(timerBar);
		threadBar.start();
		super.add(timerBar);
		super.setPreferredSize(new Dimension(600, 100));
	}
	
	public class TimerBar extends JLabel implements Runnable {
		private static final long serialVersionUID = 1L;
		int width = 600, height = 50;
		int x = 5, y = 25;
		Color color = new Color(255, 0, 0);
		
		int second;

		public TimerBar(int second) {
			setBackground(color);
			setOpaque(true);
			setBounds(x, y, width, height);
			
			this.second = second;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000 / (width / second));
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (getWidth() > 0) {
					width -= 1;	// 너비가 1씩 줄어듦
					//System.out.println(width);
					setBounds(x, y, width, height);
				} else {
					//System.out.println("종료");
					break;
				}
				if(GameBoardPanel.newGame) {}
			}
		}
	}
}