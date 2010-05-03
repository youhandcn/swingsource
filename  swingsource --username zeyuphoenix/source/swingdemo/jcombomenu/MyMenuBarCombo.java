package jcombomenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 * the menu bar that repaint it.
 * 
 * @author zeyuphoenix <br>
 *         daily jmenu MyMenuBar.java <br>
 *         2008 2008/03/19 17:24:27 <br>
 */
public class MyMenuBarCombo extends JMenuBar {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * menu
	 */
	private JMenu menu = null;
	/**
	 * size.
	 */
	private Dimension preferredSize = null;

	/**
	 * the menu bar that repaint it.
	 */
	public MyMenuBarCombo() {
	}

	/**
	 * Appends the specified menu to the end of the menu bar.
	 * 
	 * @param menu
	 *            the <code>JMenu</code> component to add
	 * @return the menu component
	 */
	public JMenu add(JMenu menu) {
		this.menu = menu;
		// set UI.
		Color color = UIManager.getColor("Menu.selectionBackground");
		UIManager.put("Menu.selectionBackground", UIManager
				.getColor("Menu.background"));
		menu.updateUI();
		UIManager.put("Menu.selectionBackground", color);
		// add listener.
		MenuItemListener listener = new MenuItemListener();
		setListener(menu, listener);
		super.add(menu);
		return menu;
	}

	/**
	 * menu item listener.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jcombomenu MyMenuBarCombo.java <br>
	 *         2008 2008/03/19 18:42:43 <br>
	 */
	private class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JMenuItem item = (JMenuItem) e.getSource();
			menu.setText(item.getText());
			menu.requestFocus();
		}
	}

	/**
	 * set listener.
	 * 
	 * @param item
	 *            item menu.
	 * @param listener
	 *            listener
	 */
	private void setListener(JMenuItem item, ActionListener listener) {
		if (item instanceof JMenu) {
			JMenu menu = (JMenu) item;
			int n = menu.getItemCount();
			for (int i = 0; i < n; i++) {
				setListener(menu.getItem(i), listener);
			}
		} else if (item != null) {
			// null means separator
			item.addActionListener(listener);
		}
	}

	/**
	 * get select item.
	 * 
	 * @return item value.
	 */
	public String getSelectedItem() {
		return menu.getText() == null ? "" : menu.getText();
	}

	/**
	 * set prefer size.
	 * 
	 * @param size
	 *            size.
	 */
	public void setPreferredSize(Dimension size) {
		preferredSize = size;
	}

	/**
	 * get prefer size.
	 * 
	 * @return menu size.
	 */
	public Dimension getPreferredSize() {
		if (preferredSize == null) {
			super.getPreferredSize();
			Dimension menuD = getItemSize(menu);
			Insets margin = menu.getMargin();
			Dimension retD = new Dimension(menuD.width, margin.top
					+ margin.bottom + menuD.height);
			menu.setPreferredSize(retD);
			preferredSize = retD;
		}
		return preferredSize;
	}

	/**
	 * get menu item max size.
	 * 
	 * @param menu
	 *            menu.
	 * @return size.
	 */
	private Dimension getItemSize(JMenu menu) {
		Dimension d = new Dimension(0, 0);
		int count = menu.getItemCount();
		// get the max size.
		for (int i = 0; i < count; i++) {
			Dimension itemD;
			JMenuItem item = menu.getItem(i);
			if (item instanceof JMenu) {
				itemD = getItemSize((JMenu) item);
			} else if (item != null) {
				itemD = item.getPreferredSize();
			} else {
				itemD = new Dimension(0, 0);
			}
			d.width = Math.max(d.width, itemD.width);
			d.height = Math.max(d.height, itemD.height);
		}
		return d;
	}
}
