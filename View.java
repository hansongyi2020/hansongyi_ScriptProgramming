package minesweeper;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.util.Timer;
import java.util.TimerTask;

public class View extends JPanel{	
	private static final long serialVersionUID = 1L;

	//카운트다운을 수행할 변수 선언 실시
	static int count = 10;

	int width = 75, height = 75;
	int x = 260, y = 15;
	private static JLabel lbl;
	
	public View() {
		//create Label
		lbl = new JLabel();
		lbl.setOpaque(true);
		lbl.setBounds(x, y, width, height);
		lbl.setForeground(Color.BLUE);
		lbl.setText(count + "");
		lbl.setFont(new Font("맑은고딕", Font.PLAIN, 50));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		
		super.setLayout(null);
		super.add(lbl);
		super.setPreferredSize(new Dimension(100, 100));
	}
	
	public static void main(String[] args) {	
		Timer timer=new Timer();
		TimerTask task=new TimerTask(){	
			@Override
		    public void run() {
		    //TODO Auto-generated method stub
				if(count >= 0){ //count값이 5보다 작거나 같을때까지 수행
					System.out.println("[카운트다운 : "+count+"]");
					lbl.setText(count + "");
					count--; //실행횟수 감소 			
				}
				else{
					timer.cancel(); //타이머 종료
					System.out.println("[카운트다운 : 종료]");
					String[] buttons4 = {"New Game", "exit"};
	             	int result = JOptionPane.showOptionDialog(null, "Time Over!", "Game Result",
	             			 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons4, "Game Over!");
	             	 System.out.println("Game Result: " + result);
		             	 if(result==0){
		             		GameBoardPanel.newGame();
		            	 }else {System.exit(0);}
				}			
		    }	
		};
		timer.schedule(task, 1000, 1000); //실행 Task, 1초뒤 실행, 1초마다 반복	
	}			
}