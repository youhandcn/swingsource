package jtable.cell;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * create the pane that some radio pane in it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyRadioPanel.java <br>
 *         2008 2008/04/01 10:07:16 <br>
 */
public class MyRadioPanel extends JPanel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** radio button group. */
	private JRadioButton[] buttons = null;

	/**
	 * create the pane that some radio pane in it.
	 * 
	 * @param strButtonText
	 *            radio pane's text.
	 */
	public MyRadioPanel(String[] strButtonText) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		buttons = new JRadioButton[strButtonText.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(strButtonText[i]);
			buttons[i].setFocusPainted(false);
			add(buttons[i]);
		}
	}

	/**
	 * set which index select.
	 * 
	 * @param index
	 *            which index select.
	 */
	public void setSelectedIndex(int index) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setSelected(i == index);
		}
	}

	/**
	 * get which index select.
	 * 
	 * @return which index select.
	 */
	public int getSelectedIndex() {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].isSelected()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * get button groups.
	 * 
	 * @return button groups.
	 */
	public JRadioButton[] getButtons() {
		return buttons;
	}
}
