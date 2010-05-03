package jlisttest;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalIconFactory;

import jlist.MyListItem;
import jlist.MyListRenderer;

public class TestJList extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JList jList = null;
	private JButton printButton = null;

	/**
	 * @param owner
	 */
	public TestJList() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			JScrollPane sp = new JScrollPane(getJList());
			jContentPane.add(sp, BorderLayout.CENTER);
			JPanel panel = new JPanel(new BorderLayout());
			printButton = new JButton("print");
			printButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListModel model = jList.getModel();
					int n = model.getSize();
					for (int i = 0; i < n; i++) {
						MyListItem item = (MyListItem) model.getElementAt(i);
						if (item.getSelect()) {
							System.out.println(item.toString());
						}
					}
				}
			});
			panel.add(printButton, BorderLayout.CENTER);
			jContentPane.add(panel, BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			Icon icon = MetalIconFactory.getFileChooserHomeFolderIcon();
			MyListItem[] items = {
					new MyListItem("Astart"),
					new MyListItem("B-BIX", true, icon),
					new MyListItem("have fun game!", false, null, false, false),
					new MyListItem("����", false), new MyListItem("abc", true),
					new MyListItem("12867831", false, icon),
					new MyListItem("happy", false, null, false, true),
					new MyListItem("defINE", false, null) };

			jList = new JList(items);
			jList.setBounds(new Rectangle(37, 17, 292, 180));

			// jList.setCellRenderer(new MyListBoxRenderer(jList));
			jList.setCellRenderer(new MyListRenderer(jList));
			jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jList.setBorder(new EmptyBorder(0, 4, 0, 0));
			jList.addMouseListener(new MyListRenderer(jList));

		}
		return jList;
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
				(new TestJList()).setVisible(true);
			}
		});
	}
} // @jve:decl-index=0:visual-constraint="10,10"
