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

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class JMineButton extends JButton {
	private int col;
	private int row;
	private int flag = 0;
	private boolean clickFlag = false;

	JMineButton(int row, int col, ImageIcon icon) {
		super(icon);
		this.row = row;
		this.col = col;
	}

	public boolean getClickFlag() {
		return (clickFlag);
	}

	public void setClickFlag(boolean toSet) {
		clickFlag = toSet;
	}

	public int getCol() {
		return (col);
	}

	public int getRow() {
		return (row);
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return (flag);
	}
}