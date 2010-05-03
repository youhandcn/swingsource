package game;

/**
 * This program is written by Jerry Shen(Shen Ji Feng) use the technology of
 * SWING GUI and the OO design
 * 
 * @author Jerry Shen all rights reserved.
 * Email:jerry.shen@cognizant.com; jerry_shen_sjf@yahoo.com.cn
 * Please report bug to these emails.
 * Open source under GPLv3
 * 
 * version 1.2
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class WinFrame extends JFrame implements MouseListener {
	private JPanel winPane;

	private JLabel msg1;

	private JLabel msg2;

	private JLabel msg3;

	private JButton easy;

	private JButton middle;

	private JButton hard;

	private int level;

	private boolean isOk = false;

	public WinFrame(String strName) {
		super(strName);
		setSize(150, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		level = 1;

		winPane = new JPanel();
		msg1 = new JLabel("  Congratulation!  ");
		msg2 = new JLabel("   You win.   ");
		msg3 = new JLabel("  Click to restart!  ");
		easy = new JButton("Easy");
		easy.addMouseListener(this);
		middle = new JButton("Middle");
		middle.addMouseListener(this);
		hard = new JButton("Hard");
		hard.addMouseListener(this);
		winPane.add(msg1);
		winPane.add(msg2);
		winPane.add(msg3);
		winPane.add(easy);
		winPane.add(middle);
		winPane.add(hard);

		setContentPane(winPane);
		setLocation(250, 220);
	}

	public int getMineNum() {
		return (level*12);
	}

	public boolean getWinOk() {
		return (isOk);
	}
	
	public void setWinOk(boolean flag){
		isOk = flag;
	}

	// the event handle to deal with the mouse click
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Jerry Click");
		if (e.getSource() == easy) {
			level = 1;
		}
		if (e.getSource() == middle) {
			level = 2;
		}
		if (e.getSource() == hard) {
			level = 3;
		}
		isOk = true;
		this.setVisible(false);
	}

	public void mousePressed(MouseEvent e) {
		//System.out.println("Jerry Press");

	}

	public void mouseReleased(MouseEvent e) {
		//System.out.println("Jerry Release");
	}

	public void mouseExited(MouseEvent e) {
		//System.out.println("Jerry Exited");

	}

	public void mouseEntered(MouseEvent e) {
		//System.out.println("Jerry Entered");

	}

	public static void main(String[] args) {
		WinFrame win = new WinFrame("Win");
		win.setVisible(true);
	}
}
