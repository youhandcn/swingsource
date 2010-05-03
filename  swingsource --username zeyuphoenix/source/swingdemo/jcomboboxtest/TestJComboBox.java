package jcomboboxtest;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Rectangle;

import jcombobox.MyComboBox;
import jcombobox.MyComboBoxItem;
import jcombobox.MyComboBoxRenderer;

/**
 * the class that test my JCombobox.
 * 
 * @author zeyuphoenix <br>
 *         daily jcomboboxtest TestJComboBox.java <br>
 *         2008 2008/03/18 18:25:10 <br>
 */
public class TestJComboBox extends JFrame {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * RootPane.
	 */
	private JPanel jContentPane = null;
	/**
	 * my JCombobox.
	 */
	private MyComboBox jComboBox = null;

	/**
	 * This is the default constructor.
	 */
	public TestJComboBox() {
		super();
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Test JComboBox");
	}

	/**
	 * This method initializes jContentPane.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJComboBox(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox.
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			Object[] items = { new MyComboBoxItem("Astart"),
					new MyComboBoxItem("B", true, true),
					new MyComboBoxItem("1ssssssssssssssss", false),
					new MyComboBoxItem("2", true),
					new MyComboBoxItem("abc", true, true),
					new MyComboBoxItem("2", false),
					new MyComboBoxItem("2ssswwsdsdsdsd"),
					new MyComboBoxItem("def", false, true) };

			jComboBox = new MyComboBox(items);
			jComboBox.setBounds(new Rectangle(30, 30, 60, 30));

			Dimension d = jComboBox.getPreferredSize();
			jComboBox.setPreferredSize(new Dimension(60, d.height));
			jComboBox.setPopupWidth(d.width);
			jComboBox.setRenderer(new MyComboBoxRenderer(jComboBox));
			jComboBox.addActionListener(new MyComboBoxRenderer(jComboBox));
		}
		return jComboBox;
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
				(new TestJComboBox()).setVisible(true);
			}
		});
	}
}
