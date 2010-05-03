package jtable;

import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.ScrollPaneLayout;

/**
 * the layout that ScrollPane with button in it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable MyScrollPaneLayout.java <br>
 *         2008 2008/03/31 16:33:29 <br>
 */
public class MyScrollPaneLayout extends ScrollPaneLayout {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the layout that ScrollPane with button in it.
	 */
	public MyScrollPaneLayout() {
		super.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}

	/**
	 * Returns the vertical scrollbar-display policy.
	 * 
	 * @return an integer giving the display policy
	 * @see #setVerticalScrollBarPolicy
	 */
	public void setVerticalScrollBarPolicy(int x) {

		// Used to set the vertical scroll bar policy
		super.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
	}

	/**
	 * Lays out the scrollpane. The positioning of components depends on the
	 * following constraints.
	 * 
	 * @param parent
	 *            the <code>Container</code> to lay out
	 */
	public void layoutContainer(Container parent) {
		super.layoutContainer(parent);

		if ((colHead == null) || (!colHead.isVisible()) || (upperRight == null)
				|| (vsb == null)) {
			return;
		}

		Rectangle vsbR = new Rectangle(0, 0, 0, 0);
		vsbR = vsb.getBounds(vsbR);

		Rectangle colHeadR = new Rectangle(0, 0, 0, 0);
		colHeadR = colHead.getBounds(colHeadR);
		colHeadR.width -= vsbR.width;
		colHead.getBounds(colHeadR);

		Rectangle upperRightR = upperRight.getBounds();
		upperRightR.x -= vsbR.width;
		upperRightR.width += vsbR.width + 1;
		upperRight.setBounds(upperRightR);
	}
}
