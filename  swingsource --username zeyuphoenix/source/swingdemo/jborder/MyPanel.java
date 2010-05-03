package jborder;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * the panel that you can override it.<br>
 * if you want your panel title can change the panel state,you must override it.
 * 
 * @author zeyuphoenix <br>
 *         daily jborder MyPanel.java <br>
 *         2008 2008/03/20 14:03:35 <br>
 */
public class MyPanel extends JPanel implements StateTransmitter {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * there is a sample about how to use it.<br>
	 * if you want make your panel some can do,you must override it.
	 * 
	 * @param enable
	 *            is enable.
	 */
	@Override
	public void setChildrenEnabled(boolean enable) {
		Component[] children = this.getComponents();
		for (int i = 0; i < children.length; i++) {
			children[i].setEnabled(enable);
		}
	}

}
