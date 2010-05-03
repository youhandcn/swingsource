package jtabpane;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

/**
 * the tab pane UI that you want to show.
 * 
 * @author zeyuphoenix <br>
 *         daily jtabpane MyTabbedPaneUI.java <br>
 *         2008 2008/03/25 13:43:58 <br>
 */
public class MyTabbedPaneUI extends MetalTabbedPaneUI {
	/** button traces. */
	private ActionListener[] buttonListeners;

	/**
	 * install UI.
	 * 
	 * @param c
	 *            JComponent
	 */
	public void installUI(JComponent c) {
		this.tabPane = (JTabbedPane) c;
		c.setLayout(createLayoutManager());
		installDefaults();
		installComponents();
		installListeners();
		installKeyboardActions();

		runCount = 1;
		selectedRun = 0;
	}

	/**
	 * remove install UI.
	 * 
	 * @param c
	 *            JComponent
	 */
	public void uninstallUI(JComponent c) {
		uninstallComponents();
		super.uninstallUI(c);
	}

	/**
	 * Invoked by <code>installUI</code> to create a layout manager object to
	 * manage the <code>JTabbedPane</code>.
	 * 
	 * @return a layout manager object
	 */
	protected LayoutManager createLayoutManager() {
		return new MyTabbedLayout(tabPane);
	}

	/**
	 * Creates and installs any required subcomponents for the JTabbedPane.
	 * Invoked by installUI.
	 */
	protected void installComponents() {
		JButton[] buttons = ((MyTabPane) tabPane).getButtons();
		for (int i = 0; i < buttons.length; i++) {
			tabPane.add(buttons[i]);
		}
	}

	/**
	 * Removes any installed subcomponents from the JTabbedPane. Invoked by
	 * uninstallUI.
	 */
	protected void uninstallComponents() {
		JButton[] buttons = ((MyTabPane) tabPane).getButtons();
		for (int i = 0; i < buttons.length; i++) {
			tabPane.remove(buttons[i]);
		}
	}

	/**
	 * install listener.
	 */
	protected void installListeners() {
		super.installListeners();
		MyTabPane stabPane = (MyTabPane) tabPane;
		JButton[] buttons = stabPane.getButtons();
		int n = buttons.length;
		buttonListeners = new ActionListener[n];
		// trace button listener.
		for (int i = 0; i < n; i++) {
			buttonListeners[i] = null;
			String str = buttons[i].getActionCommand();

			if (str.equals(TRAILING + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						int index = sPane.getVisibleStartIndex()
								+ sPane.getVisibleCount();
						return (index < sPane.getTabCount()) ? index : 0;
					}
				};
			} else if (str.equals(PREVIOUS + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						return getStartIndex(sPane.getVisibleStartIndex() - 1);
					}
				};
			} else if (str.equals(NEXT + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						return sPane.getVisibleStartIndex()
								+ sPane.getVisibleCount();
					}
				};
			} else if (str.equals(TOP + "")) {
				buttonListeners[i] = new ShiftTabs();
			} else if (str.equals(LEFT + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						return sPane.getVisibleStartIndex() - 1;
					}
				};
			} else if (str.equals(RIGHT + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						return sPane.getVisibleStartIndex() + 1;
					}
				};
			} else if (str.equals(BOTTOM + "")) {
				buttonListeners[i] = new ShiftTabs() {
					protected int getStartIndex() {
						return getStartIndex(sPane.getTabCount() - 1);
					}
				};
			}
			buttons[i].addActionListener(buttonListeners[i]);
		}
	}

	/**
	 * remove install listener.
	 */
	protected void uninstallListeners() {
		super.uninstallListeners();
		JButton[] buttons = ((MyTabPane) tabPane).getButtons();
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].removeActionListener(buttonListeners[i]);
		}
	}

	/**
	 * Returns the tab index which intersects the specified point in the
	 * JTabbedPane's coordinate space.<br>
	 * farther's method. I override it.
	 */
	public int tabForCoordinate(JTabbedPane pane, int x, int y) {
		MyTabPane stabPane = (MyTabPane) tabPane;
		int visibleCount = stabPane.getVisibleCount();
		int visibleStartIndex = stabPane.getVisibleStartIndex();

		for (int i = 0, index = visibleStartIndex; i < visibleCount; i++, index++) {
			if (rects[index].contains(x, y)) {
				return index;
			}
		}
		return -1;
	}

	/**
	 * paint the tab pane.
	 * 
	 * @param g
	 *            Graphics
	 * @param c
	 *            JComponent
	 */
	public void paint(Graphics g, JComponent c) {
		int selectedIndex = tabPane.getSelectedIndex();
		int tabPlacement = tabPane.getTabPlacement();

		ensureCurrentLayout();

		MyTabPane stabPane = (MyTabPane) tabPane;
		int visibleCount = stabPane.getVisibleCount();
		int visibleStartIndex = stabPane.getVisibleStartIndex();

		Rectangle iconRect = new Rectangle(), textRect = new Rectangle();
		Rectangle clipRect = g.getClipBounds();

		tabRuns[0] = visibleStartIndex;

		for (int i = 0, index = visibleStartIndex; i < visibleCount; i++, index++) {
			if (rects[index].intersects(clipRect)) {
				paintTab(g, tabPlacement, rects, index, iconRect, textRect);
			}
		}
		if (stabPane.isVisibleTab(selectedIndex)) {
			if (rects[selectedIndex].intersects(clipRect)) {
				paintTab(g, tabPlacement, rects, selectedIndex, iconRect,
						textRect);
			}
		}

		paintContentBorder(g, tabPlacement, selectedIndex);
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		g.setColor(selectHighlight);
		if (tabPlacement != TOP
				|| selectedIndex < 0
				|| (rects[selectedIndex].y + rects[selectedIndex].height + 1 < y)
				|| !((MyTabPane) tabPane).isVisibleTab(selectedIndex)) {
			g.drawLine(x, y, x + w - 2, y);
		} else {
			Rectangle selRect = rects[selectedIndex];
			g.drawLine(x, y, selRect.x + 1, y);
			if (selRect.x + selRect.width < x + w - 2) {
				g.drawLine(selRect.x + selRect.width, y, x + w - 2, y);
			} else {
				g.setColor(shadow);
				g.drawLine(x + w - 2, y, x + w - 2, y);
			}
		}
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		g.setColor(darkShadow);
		if (tabPlacement != BOTTOM || selectedIndex < 0
				|| (rects[selectedIndex].y - 1 > h)
				|| !((MyTabPane) tabPane).isVisibleTab(selectedIndex)) {
			g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
		} else {
			Rectangle selRect = rects[selectedIndex];
			g.drawLine(x, y + h - 1, selRect.x, y + h - 1);
			if (selRect.x + selRect.width < x + w - 2) {
				g.drawLine(selRect.x + selRect.width, y + h - 1, x + w - 1, y
						+ h - 1);
			}
		}
	}

	/**
	 * some method that override fateher'method.
	 */
	protected Insets getTabAreaInsets(int tabPlacement) {
		MyTabPane stabPane = (MyTabPane) tabPane;
		Dimension d = stabPane.getButtonPreferredSize();
		int n = stabPane.getButtonKind();
		int buttonPlacement = stabPane.getButtonPlacement();

		Insets currentInsets = new Insets(0, 0, 0, 0);
		if (tabPlacement == TOP) {
			currentInsets.top = tabAreaInsets.top;
			currentInsets.bottom = tabAreaInsets.bottom;
		} else {
			currentInsets.top = tabAreaInsets.bottom;
			currentInsets.bottom = tabAreaInsets.top;
		}
		if (buttonPlacement == RIGHT) {
			currentInsets.left = tabAreaInsets.left;
			currentInsets.right = tabAreaInsets.right + n * d.width;
		} else {
			currentInsets.left = tabAreaInsets.left + n * d.width;
			currentInsets.right = tabAreaInsets.right;
		}
		return currentInsets;
	}

	/**
	 * some method that override fateher'method.
	 */
	protected int c(int tabCount, int run) {
		MyTabPane stabPane = (MyTabPane) tabPane;
		return stabPane.getVisibleStartIndex() + stabPane.getVisibleCount() - 1;
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void ensureCurrentLayout() {
		MyTabbedLayout layout = (MyTabbedLayout) tabPane.getLayout();
		layout.calculateLayoutInfo();
		setButtonsEnabled();
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void setButtonsEnabled() {
		MyTabPane stabPane = (MyTabPane) tabPane;
		int visibleCount = stabPane.getVisibleCount();
		int visibleStartIndex = stabPane.getVisibleStartIndex();
		JButton[] buttons = stabPane.getButtons();
		boolean lEnable = 0 < visibleStartIndex;
		boolean rEnable = visibleStartIndex + visibleCount < tabPane
				.getTabCount();
		for (int i = 0; i < buttons.length; i++) {
			boolean enable = false;
			String str = buttons[i].getActionCommand();
			if (str.equals(TRAILING + "")) {
				enable = lEnable || rEnable;
			} else if (str.equals(PREVIOUS + "")) {
				enable = lEnable;
			} else if (str.equals(NEXT + "")) {
				enable = rEnable;
			} else if (str.equals(TOP + "")) {
				enable = lEnable;
			} else if (str.equals(LEFT + "")) {
				enable = lEnable;
			} else if (str.equals(RIGHT + "")) {
				enable = rEnable;
			} else if (str.equals(BOTTOM + "")) {
				enable = rEnable;
			}
			buttons[i].setEnabled(enable);
		}
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void ensureVisibleTabAt(int index) {
		MyTabPane stabPane = (MyTabPane) tabPane;
		int visibleCount = stabPane.getVisibleCount();
		int visibleStartIndex = stabPane.getVisibleStartIndex();
		int visibleEndIndex = visibleStartIndex + visibleCount - 1;

		if (visibleStartIndex < index && index < visibleEndIndex) {
			return;
		}
		if (index <= visibleStartIndex) {

			if (visibleStartIndex == 0)
				return;
			stabPane.setVisibleStartIndex(--visibleStartIndex);
			((MyTabbedLayout) tabPane.getLayout()).calculateLayoutInfo();
			int count = stabPane.getVisibleCount();
			int startIndex = stabPane.getVisibleStartIndex();
			if (startIndex <= index && index <= startIndex + count - 1) {
			} else {
				stabPane.setVisibleStartIndex(++visibleStartIndex);
			}
		}
		if (visibleEndIndex <= index) {

			if (visibleStartIndex == visibleCount + 1)
				return;
			stabPane.setVisibleStartIndex(++visibleStartIndex);
			((MyTabbedLayout) tabPane.getLayout()).calculateLayoutInfo();
			int count = stabPane.getVisibleCount();
			int startIndex = stabPane.getVisibleStartIndex();
			if (startIndex <= index && index <= startIndex + count - 1) {
			} else {
				stabPane.setVisibleStartIndex(--visibleStartIndex);
			}
		}
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void selectNextTab(int current) {
		for (int i = current + 1; i < tabPane.getTabCount(); i++) {
			if (tabPane.isEnabledAt(i)) {
				ensureVisibleTabAt(i);
				tabPane.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void selectPreviousTab(int current) {
		for (int i = current - 1; 0 <= i; i--) {
			if (tabPane.isEnabledAt(i)) {
				ensureVisibleTabAt(i);
				tabPane.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * set max tab height.
	 * 
	 * @param maxTabHeight
	 *            tab height.
	 */
	public void setMaxTabHeight(int maxTabHeight) {
		this.maxTabHeight = maxTabHeight;
	}

	/**
	 * get max tab height.
	 * 
	 * @return tab height.
	 */
	public int getMaxTabHeight() {
		return maxTabHeight;
	}

	/**
	 * get the rectangle.
	 * 
	 * @return rectangle.
	 */
	public Rectangle[] getRects() {
		return rects;
	}

	/**
	 * get the tab pane.
	 * 
	 * @return tab pane.
	 */
	public MyTabPane getTabbedPane() {
		return (MyTabPane) tabPane;
	}

	/**
	 * some method that override fateher'method.
	 */
	@SuppressWarnings("deprecation")
	protected FontMetrics getFontMetrics() {
		Font font = tabPane.getFont();
		return Toolkit.getDefaultToolkit().getFontMetrics(font);
	}

	/**
	 * some method that override fateher'method.
	 */
	protected int calculateMaxTabHeight(int tabPlacement) {
		return super.calculateMaxTabHeight(tabPlacement);
	}

	/**
	 * some method that override fateher'method.
	 */
	protected int calculateTabWidth(int tabPlacement, int tabIndex,
			FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics);
	}

	/**
	 * some method that override fateher'method.
	 */
	protected void assureRectsCreated(int tabCount) {
		super.assureRectsCreated(tabCount);
	}

	/**
	 * This class should be treated as a &quot;protected&quot; inner class.
	 * Instantiate it only within subclasses of BasicTabbedPaneUI.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane MyTabbedPaneUI.java <br>
	 *         2008 2008/03/25 14:08:02 <br>
	 */
	class MyTabbedLayout extends BasicTabbedPaneUI.TabbedPaneLayout {
		JTabbedPane tabPane;

		/**
		 * Instantiate it only within subclasses of BasicTabbedPaneUI.
		 * 
		 * @param tabPane
		 *            tab pane layout.
		 */
		public MyTabbedLayout(JTabbedPane tabPane) {
			this.tabPane = tabPane;
		}

		/**
		 * override father'method.
		 */
		public void layoutContainer(Container parent) {
			super.layoutContainer(parent);
			if (tabPane.getComponentCount() < 1) {
				return;
			}

			int tabPlacement = tabPane.getTabPlacement();
			int maxTabHeight = calculateMaxTabHeight(tabPlacement);
			Insets tabAreaInsets = getTabAreaInsets(tabPlacement);
			Insets insets = tabPane.getInsets();
			Rectangle bounds = tabPane.getBounds();

			MyTabPane stabPane = (MyTabPane) tabPane;
			Dimension d = stabPane.getButtonPreferredSize();
			JButton[] buttons = stabPane.getButtons();
			int buttonPlacement = stabPane.getButtonPlacement();

			int x, y;
			if (tabPlacement == TOP) { // TOP
				y = bounds.y + insets.top + tabAreaInsets.top;
			} else { // BOTTOM
				y = bounds.y + bounds.height - insets.bottom
						- tabAreaInsets.bottom - maxTabHeight;
			}
			if (buttonPlacement == RIGHT) { // RIGHT
				x = bounds.x + bounds.width - insets.right;
				for (int i = buttons.length - 1; 0 <= i; i--) {
					x -= d.width;
					buttons[i].setBounds(x, y, d.width, d.height);
				}
			} else { // LEFT
				x = bounds.x + insets.left;
				for (int i = 0; i < buttons.length; i++) {
					buttons[i].setBounds(x, y, d.width, d.height);
					x += d.width;
				}
			}
		}

		/**
		 * override father'method.
		 */
		public void calculateLayoutInfo() {
			int tabCount = tabPane.getTabCount();
			assureRectsCreated(tabCount);
			calculateTabWidths(tabPane.getTabPlacement(), tabCount);
			calculateTabRects(tabPane.getTabPlacement(), tabCount);
		}

		/**
		 * override father'method.
		 */
		protected void calculateTabWidths(int tabPlacement, int tabCount) {
			if (tabCount == 0) {
				return;
			}
			FontMetrics metrics = getFontMetrics();
			int maxTabHeight = calculateMaxTabHeight(tabPlacement);
			setMaxTabHeight(maxTabHeight);
			Rectangle[] rects = getRects();
			for (int i = 0; i < tabCount; i++) {
				rects[i].width = calculateTabWidth(tabPlacement, i, metrics);
				rects[i].height = maxTabHeight;
			}
		}

		/**
		 * override father'method.
		 */
		protected void calculateTabRects(int tabPlacement, int tabCount) {
			if (tabCount == 0) {
				return;
			}
			MyTabPane stabPane = (MyTabPane) tabPane;
			Dimension size = tabPane.getSize();
			Insets insets = tabPane.getInsets();
			Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

			int maxTabHeight = getMaxTabHeight();
			int x = insets.left + tabAreaInsets.left;
			int y;
			if (tabPlacement == TOP) {
				y = insets.top + tabAreaInsets.top;
			} else { // BOTTOM
				y = size.height - insets.bottom - tabAreaInsets.bottom
						- maxTabHeight;
			}

			int returnAt = size.width - (insets.right + tabAreaInsets.right);
			Rectangle[] rects = getRects();
			int visibleStartIndex = stabPane.getVisibleStartIndex();
			int visibleCount = 0;

			for (int i = visibleStartIndex; i < tabCount; i++) {
				Rectangle rect = rects[i];
				if (visibleStartIndex < i) {
					rect.x = rects[i - 1].x + rects[i - 1].width;
				} else {
					rect.x = x;
				}

				if (rect.x + rect.width > returnAt) {
					break;
				} else {
					visibleCount++;
					rect.y = y;
				}
			}
			stabPane.setVisibleCount(visibleCount);
			stabPane.setVisibleStartIndex(visibleStartIndex);
		}
	}

	/**
	 * the tab action.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane MyTabbedPaneUI.java <br>
	 *         2008 2008/03/25 13:47:07 <br>
	 */
	private class ShiftTabs implements ActionListener {
		/** tab pane. */
		MyTabPane sPane = null;

		/**
		 * action trace.
		 * 
		 * @param e
		 *            ActionEvent.
		 */
		public void actionPerformed(ActionEvent e) {
			sPane = getTabbedPane();
			int index = getStartIndex();
			sPane.setVisibleStartIndex(index);
			sPane.repaint();
		}

		/**
		 * get start index.
		 * 
		 * @return start index.
		 */
		protected int getStartIndex() {
			return 0; // first tab
		}

		/**
		 * get index.
		 * 
		 * @param lastIndex
		 *            the last index.
		 * @return start index.
		 */
		protected int getStartIndex(int lastIndex) {
			Insets insets = sPane.getInsets();
			Insets tabAreaInsets = getTabAreaInsets(sPane.getTabPlacement());
			int width = sPane.getSize().width - (insets.left + insets.right)
					- (tabAreaInsets.left + tabAreaInsets.right);
			int index;
			Rectangle[] rects = getRects();
			for (index = lastIndex; 0 <= index; index--) {
				width -= rects[index].width;
				if (width < 0) {
					break;
				}
			}
			return ++index;
		}
	}

}
