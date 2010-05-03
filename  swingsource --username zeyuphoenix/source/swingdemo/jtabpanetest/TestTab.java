package jtabpanetest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import jtabpane.MyTabPane;
import jtabpane.MyTabPaneButton;

/**
 * Creating and using TabComponentsDemo example.
 */
public class TestTab extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	private final int tabNumber = 5;
	private final JTabbedPane pane = new JTabbedPane();
	private JMenuItem tabComponentsItem;
	private JMenuItem scrollLayoutItem;

	/**
	 * test.
	 * 
	 * @param args
	 *            testing
	 */
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TestTab("TabComponentsDemo").runTest();
			}
		});
	}

	public TestTab(String title) {
		super(title);
		initMenu();
		setLayout(new GridLayout(2, 2));
		// add(pane);
		MyTabPane tabbedPane = new MyTabPane(MyTabPane.FOUR_BUTTONS,
				SwingConstants.LEFT);
		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

		String tabs[] = { "One", "Two", "Three", "Four", "Five", "Six",
				"Seven", "Eight", "Nine", "Ten" };
		for (int i = 0; i < tabs.length; i++) {
			tabbedPane.addTab(tabs[i], createPane(tabs[i]));
			tabbedPane.setTabComponentAt(i, new MyTabPaneButton(tabbedPane));
		}
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setBackgroundAt(2, Color.green);
		add(tabbedPane);
	}

	private JPanel createPane(String s) {
		JPanel p = new JPanel();
		p.add(new JLabel(s));
		return p;
	}

	public void runTest() {
		pane.removeAll();
		for (int i = 0; i < tabNumber; i++) {
			String title = "Tab " + i;
			pane.add(title, new JLabel(title));
			initTabComponent(i);
		}
		tabComponentsItem.setSelected(true);
		pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		pane.setBackgroundAt(2, Color.RED);
		scrollLayoutItem.setSelected(false);
		setSize(new Dimension(500, 300));
		setVisible(true);
	}

	/**
	 * init tab.
	 * 
	 * @param i
	 */
	private void initTabComponent(int i) {
		pane.setTabComponentAt(i, new MyTabPaneButton(pane));
	}

	/**
	 * Setting menu
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		// create Options menu
		tabComponentsItem = new JCheckBoxMenuItem("Use TabComponents", true);
		tabComponentsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < pane.getTabCount(); i++) {
					if (tabComponentsItem.isSelected()) {
						initTabComponent(i);
					} else {
						pane.setTabComponentAt(i, null);
					}
				}
			}
		});
		scrollLayoutItem = new JCheckBoxMenuItem("Set ScrollLayout");
		scrollLayoutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pane.getTabLayoutPolicy() == JTabbedPane.WRAP_TAB_LAYOUT) {
					pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				} else {
					pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
				}
			}
		});
		JMenuItem resetItem = new JMenuItem("Reset JTabbedPane");
		resetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runTest();
			}
		});

		JMenu optionsMenu = new JMenu("Options");
		optionsMenu.add(tabComponentsItem);
		optionsMenu.add(scrollLayoutItem);
		optionsMenu.add(resetItem);
		menuBar.add(optionsMenu);
		setJMenuBar(menuBar);
	}
}
