package jtabpane;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * tab pane that have some new method.
 * 
 * @author zeyuphoenix <br>
 *         daily jtabpane MyTabPane.java <br>
 *         2008 2008/03/25 13:15:45 <br>
 */
public class MyTabPane extends JTabbedPane {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ROTATE.
	 */
	public static final int ONE_BUTTON = 1;
	/**
	 * PREVIOUS | NEXT.
	 */
	public static final int TWO_BUTTONS = 2;
	/**
	 * FIRST | LEFT_SHIFT | RIGHT_SHIFT | LAST.
	 */
	public static final int FOUR_BUTTONS = 4;
	/** button. */
	private int buttonPlacement = 0;
	/** button kind. */
	private int buttonKind = 0;
	/** the buttons in tab. */
	private JButton[] tabPaneButtons = null;
	/** button size. */
	private Dimension buttonSize = null;
	/** visible number. */
	private int visibleCount = 0;
	/** visible start. */
	private int visibleStartIndex = 0;
	/**
	 * button's width.
	 */
	private final int BUTTON_WIDTH = 16;
	/**
	 * button's height.
	 */
	private final int BUTTON_HEIGHT = 16;

	/**
	 * create tab pane.
	 */
	public MyTabPane() {
		this(TWO_BUTTONS, RIGHT);
	}

	/**
	 * create tab pane.
	 * 
	 * @param buttonKind
	 *            have button kind.
	 * @param buttonPlacement
	 *            button dir.
	 */
	public MyTabPane(int buttonKind, int buttonPlacement) {
		setButtonPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		tabPaneButtons = createButtons(buttonKind);
		this.buttonPlacement = buttonPlacement;
		visibleStartIndex = 0;

		setUI(new MyTabbedPaneUI());
	}

	/**
	 * Sets the tab placement for this tab pane.
	 * 
	 * The default value, if not set, is <code>SwingConstants.TOP</code>.
	 * 
	 * @param tabPlacement
	 *            the placement for the tabs relative to the content.
	 */
	public void setTabPlacement(int tabPlacement) {
		if (tabPlacement == LEFT || tabPlacement == RIGHT) {
			throw new IllegalArgumentException("not suported: LEFT and RIGHT");
		}
		super.setTabPlacement(tabPlacement);
	}

	/**
	 * get placement for this tab pane.
	 * 
	 * @return the placement for the tabs relative to the content.
	 */
	public int getButtonPlacement() {
		return buttonPlacement;
	}

	/**
	 * set button size.
	 * 
	 * @param d
	 *            size.
	 */
	public void setButtonPreferredSize(Dimension d) {
		if (d != null) {
			buttonSize = d;
		}
	}

	/**
	 * get button size.
	 * 
	 * @return button size.
	 */
	public Dimension getButtonPreferredSize() {
		return buttonSize;
	}

	/**
	 * get tab buttons.
	 * 
	 * @return tab buttons.
	 */
	public JButton[] getButtons() {
		return tabPaneButtons;
	}

	/**
	 * get tab buttons kind.
	 * 
	 * @return tab buttons kind.
	 */
	public int getButtonKind() {
		return buttonKind;
	}

	/**
	 * Inserts a <code>component</code>, at <code>index</code>.
	 * 
	 * @param title
	 *            the title to be displayed in this tab
	 * @param icon
	 *            the icon to be displayed in this tab
	 * @param component
	 *            The component to be displayed when this tab is clicked.
	 * @param tip
	 *            the tooltip to be displayed for this tab
	 * @param index
	 *            the position to insert this new tab
	 */
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		if (component instanceof TabbedPaneInterface) {
			if (component != null) {
				component.setVisible(true);
				addImpl(component, null, -1);
			}
			return;
		}
		super.insertTab(title, icon, component, tip, index);
	}

	/**
	 * the tab is visible.
	 * 
	 * @param index
	 *            the tab index.
	 * @return is visible.
	 */
	public boolean isVisibleTab(int index) {
		if ((visibleStartIndex <= index)
				&& (index < visibleStartIndex + visibleCount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * set the visible count.
	 * 
	 * @return the visible count.
	 */
	public int getVisibleCount() {
		return visibleCount;
	}

	/**
	 * set the visible count.
	 * 
	 * @param visibleCount
	 *            the visible count.
	 */
	public void setVisibleCount(int visibleCount) {
		if (visibleCount < 0) {
			return;
		}
		this.visibleCount = visibleCount;
	}

	/**
	 * set the visible start.
	 * 
	 * @return the visible start.
	 */
	public int getVisibleStartIndex() {
		return visibleStartIndex;
	}

	/**
	 * set the visible start.
	 * 
	 * @param visibleStartIndex
	 *            the visible start.
	 */
	public void setVisibleStartIndex(int visibleStartIndex) {
		if (visibleStartIndex < 0 || getTabCount() <= visibleStartIndex) {
			return;
		}
		this.visibleStartIndex = visibleStartIndex;
	}

	/**
	 * create next or last buttons.
	 * 
	 * @param buttonKind
	 *            button kind.
	 * @return buttons.
	 */
	private JButton[] createButtons(int buttonKind) {
		JButton[] tabPaneButtons = null;
		switch (buttonKind) {
		case ONE_BUTTON:
			this.buttonKind = buttonKind;
			tabPaneButtons = new JButton[buttonKind];
			tabPaneButtons[0] = new PrevOrNextButton(EAST);
			tabPaneButtons[0].setActionCommand(TRAILING + "");
			break;
		case TWO_BUTTONS:
			this.buttonKind = buttonKind;
			tabPaneButtons = new JButton[buttonKind];
			tabPaneButtons[0] = new PrevOrNextButton(WEST);
			tabPaneButtons[0].setActionCommand(PREVIOUS + "");
			tabPaneButtons[1] = new PrevOrNextButton(EAST);
			tabPaneButtons[1].setActionCommand(NEXT + "");
			break;
		case FOUR_BUTTONS:
			this.buttonKind = buttonKind;
			tabPaneButtons = new JButton[buttonKind];
			tabPaneButtons[0] = new FirstOrLastButton(WEST);
			tabPaneButtons[0].setActionCommand(TOP + "");
			tabPaneButtons[1] = new PrevOrNextButton(WEST);
			tabPaneButtons[1].setActionCommand(LEFT + "");
			tabPaneButtons[2] = new PrevOrNextButton(EAST);
			tabPaneButtons[2].setActionCommand(RIGHT + "");
			tabPaneButtons[3] = new FirstOrLastButton(EAST);
			tabPaneButtons[3].setActionCommand(BOTTOM + "");
			break;
		default:
		}
		return tabPaneButtons;
	}

	/**
	 * create next button.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane MyTabPane.java <br>
	 *         2008 2008/03/25 11:59:34 <br>
	 */
	private class PrevOrNextButton extends BasicArrowButton implements
			TabbedPaneInterface {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public PrevOrNextButton(int direction) {
			super(direction);
		}
	}

	/**
	 * create last button.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane MyTabPane.java <br>
	 *         2008 2008/03/25 11:59:55 <br>
	 */
	private class FirstOrLastButton extends StopArrowButton implements
			TabbedPaneInterface {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public FirstOrLastButton(int direction) {
			super(direction);
		}
	}

	/**
	 * the interface that for ...
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane TabbedPaneInterface.java <br>
	 *         2008 2008/03/25 14:17:17 <br>
	 */
	private interface TabbedPaneInterface {

	}
}
