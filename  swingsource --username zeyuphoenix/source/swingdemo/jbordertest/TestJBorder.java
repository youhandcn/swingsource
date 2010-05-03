package jbordertest;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import jborder.MyPanel;
import jborder.MyTitledPane;
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
public class TestJBorder extends JFrame {
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
	public TestJBorder() {
		super();
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

			ButtonGroup group = new ButtonGroup();

			jContentPane.setLayout(new GridLayout(3, 2));

			JCheckBox mycheBox1 = new JCheckBox("Test box1");
			mycheBox1.setSelected(true);
			final MyTitledPane mypane1 = new MyTitledPane(mycheBox1);
			mycheBox1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					mypane1
							.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
			MyPanel myPanel = new MyPanel();
			myPanel.add(new JButton("you dead"));
			mypane1.setTransmittingAllowed(true);
			mypane1.setTransmitter(myPanel);
			mypane1.getContentPane().add(myPanel);
			//			
			JCheckBox mycheBox2 = new JCheckBox(
					"<html>Title (<font color=\"#ff00ff\"><i>Test box2</i></font>)");
			mycheBox2.setSelected(true);
			final MyTitledPane mypane2 = new MyTitledPane(mycheBox2);
			mycheBox2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					mypane2
							.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
			MyPanel myPanel2 = new MyPanel();
			myPanel2.add(getJComboBox());
			mypane2.setTransmittingAllowed(true);
			mypane2.setTransmitter(myPanel2);
			mypane2.getContentPane().add(myPanel2);

			JRadioButton ridioButton1 = new JRadioButton(
					"<html><font color=\"#ff0000\"><i>Test RadioButton2</i></font>");
			JRadioButton ridioButton2 = new JRadioButton("Test RadioButton1");
			group.add(ridioButton1);
			group.add(ridioButton2);

			final MyTitledPane mypane3 = new MyTitledPane(ridioButton1);
			ridioButton1.setSelected(true);
			ridioButton1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					mypane3
							.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
			MyPanel myPanel3 = new MyPanel();
			myPanel3.add(new JButton("you CLICK"));
			myPanel3.add(new JLabel("you SHOW"));
			mypane3.setTransmittingAllowed(true);
			mypane3.setTransmitter(myPanel3);
			mypane3.getContentPane().add(myPanel3);

			final MyTitledPane mypane4 = new MyTitledPane(ridioButton2);
			ridioButton2.setSelected(true);
			ridioButton2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					mypane4
							.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
			MyPanel myPanel4 = new MyPanel();
			myPanel4.add(new JCheckBox("showing"));
			mypane4.setTransmittingAllowed(true);
			mypane4.setTransmitter(myPanel4);
			mypane4.getContentPane().add(myPanel4);

			jContentPane.add(mypane1);
			jContentPane.add(mypane2);
			jContentPane.add(mypane3);
			jContentPane.add(mypane4);

			// add action button.
			final JButton click = new JButton(
					"<html>Click (<font color=\"#00ff00\"><i>TOP</i></font>)");
			final JButton click2 = new JButton(
					"<html>Click (<font color=\"#00ff00\"><i>LEFT</i></font>)");

			final String[] posStr = { "", "ABOVETOP", "TOP", "BELOWTOP",
					"ABOVEBOTTOM", "BOTTOM", "BELOWBOTTOM" };
			final String[] jusStr = { "", "LEFT", "CENTER", "RIGHT" };

			click.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 1; i < posStr.length; i++) {
						if (click.getText().contains(posStr[i])) {
							((TitledBorder) mypane1.getBorder())
									.setTitlePosition(i);
							mypane1.revalidate();
							mypane1.repaint();
							((TitledBorder) mypane2.getBorder())
									.setTitlePosition(i);
							mypane2.revalidate();
							mypane2.repaint();
							((TitledBorder) mypane3.getBorder())
									.setTitlePosition(i);
							mypane3.revalidate();
							mypane3.repaint();
							((TitledBorder) mypane4.getBorder())
									.setTitlePosition(i);
							mypane4.revalidate();
							mypane4.repaint();
							if (i == posStr.length - 1) {
								click
										.setText("<html>Click (<font color=\"#00ffff\"><i>"
												+ posStr[1] + "</i></font>)");
							} else {
								click
										.setText("<html>Click (<font color=\"#00ffff\"><i>"
												+ posStr[1 + i]
												+ "</i></font>)");
							}

							return;
						}

					}
				}
			});
			// make title center left and right.
			click2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 1; i < jusStr.length; i++) {
						if (click2.getText().contains(jusStr[i])) {
							((TitledBorder) mypane1.getBorder())
									.setTitleJustification(i);
							mypane1.revalidate();
							mypane1.repaint();
							((TitledBorder) mypane2.getBorder())
									.setTitleJustification(i);
							mypane2.revalidate();
							mypane2.repaint();
							((TitledBorder) mypane3.getBorder())
									.setTitleJustification(i);
							mypane3.revalidate();
							mypane3.repaint();
							((TitledBorder) mypane4.getBorder())
									.setTitleJustification(i);
							mypane4.revalidate();
							mypane4.repaint();

							if (i == jusStr.length - 1) {
								click2
										.setText("<html>Click (<font color=\"#00ff00\"><i>"
												+ jusStr[1] + "</i></font>)");
							} else {
								click2
										.setText("<html>Click (<font color=\"#00ff00\"><i>"
												+ jusStr[1 + i]
												+ "</i></font>)");
							}
							return;
						}

					}
				}
			});
			jContentPane.add(click);
			jContentPane.add(click2);
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
			// jComboBox.setBounds(new Rectangle(30, 30, 60, 30));

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
				(new TestJBorder()).setVisible(true);
			}
		});
	}
}
