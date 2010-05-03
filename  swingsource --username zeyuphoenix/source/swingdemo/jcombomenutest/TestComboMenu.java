package jcombomenutest;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalIconFactory;

import java.awt.Rectangle;
import java.awt.FlowLayout;
import jcombomenu.MyMenu;
import jcombomenu.MyMenuBarCombo;

/**
 * test menu with combobox.
 * 
 * @author zeyuphoenix <br>
 *         daily jcombomenutest TestComboMenu.java <br>
 *         2008 2008/03/20 9:58:57 <br>
 */
public class TestComboMenu extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * root pane.
	 */
	private JPanel jContentPane = null;
	/**
	 * my pane.
	 */
	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public TestComboMenu() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		this.setSize(410, 300);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setBounds(new Rectangle(11, 10, 289, 127));

			jPanel.add(setMenuCombo());
		}
		return jPanel;
	}

	private MyMenuBarCombo setMenuCombo() {
		MyMenuBarCombo myMenuBar = new MyMenuBarCombo();

		String[] itemStr = { "name", "Red", "Blue", "number", "255,0,0",
				"0,0,255",
				// separator
				"system", "control", "controlHighlight", "controlShadow",
				"text" };

		ButtonGroup group = new ButtonGroup();

		JMenuItem[] menuItems = new JMenuItem[7];
		Icon icon = MetalIconFactory.getFileChooserHomeFolderIcon();
		Icon icon1 = MetalIconFactory.getFileChooserListViewIcon();
		// icon menu.
		menuItems[0] = new JMenuItem(itemStr[1], icon);
		menuItems[1] = new JMenuItem(itemStr[2], icon1);
		// check menu.
		menuItems[2] = new JCheckBoxMenuItem(itemStr[4]);
		menuItems[3] = new JCheckBoxMenuItem(itemStr[5]);
		// radio menu.
		menuItems[4] = new JRadioButtonMenuItem(itemStr[8]);
		menuItems[4].setSelected(true);
		group.add(menuItems[4]);
		menuItems[5] = new JRadioButtonMenuItem(itemStr[9]);
		group.add(menuItems[5]);
		menuItems[6] = new JRadioButtonMenuItem(itemStr[10]);
		group.add(menuItems[6]);
		// create menu.
		JMenu[] menus = new JMenu[4];
		menus[0] = new JMenu(itemStr[0]);
		menus[1] = new JMenu(itemStr[3]);
		menus[2] = new JMenu(itemStr[6]);
		menus[3] = new JMenu(itemStr[7]);

		// add menu item
		menus[0].add(menuItems[0]);
		menus[0].add(menuItems[1]);
		menus[1].add(menuItems[2]);
		menus[1].add(menuItems[3]);
		menus[3].add(menuItems[4]);
		menus[3].add(menuItems[5]);
		menus[2].add(menus[3]);
		menus[2].add(menuItems[6]);

		// add menu
		MyMenu menu = new MyMenu(menuItems[0].getText());
		menu.add(menus[0]);
		menu.add(menus[1]);
		menu.addSeparator();
		menu.add(menus[2]);
		myMenuBar.add(menu);
		return myMenuBar;
	}
	/**
	 * test.
	 * 
	 * @param args
	 *            string array
	 */
	public static void main(final String[] args) {

		// show the frame.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new TestComboMenu()).setVisible(true);
			}
		});
	}
} // @jve:decl-index=0:visual-constraint="10,10"
