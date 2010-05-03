package jborder;

/**
 * set the panel enable or not.
 * 
 * @author zeyuphoenix <br>
 *         daily jborder StateTransmitter.java <br>
 *         2008 2008/03/20 11:04:27 <br>
 */
public interface StateTransmitter {
	/**
	 * set panel enable.
	 * 
	 * @param enable
	 *            is enable
	 */
	public void setChildrenEnabled(boolean enable);

	/**
	 * there is a sample about how to use it.
	 * 
	 * public void setChildrenEnabled(boolean enable){***************<br>
	 * ******Component[] children = panel.getComponents();***********<br>
	 * ******for(int i = 0; i< children.length; i++) {***************<br>
	 * ************children[i].setEnabled(enable); ******************<br>
	 * ******}*******************************************************<br>
	 * }*************************************************************<br>
	 */
}
