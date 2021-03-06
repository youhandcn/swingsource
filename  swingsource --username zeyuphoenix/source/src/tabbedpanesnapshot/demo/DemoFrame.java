/*
 * DemoFrame.java
 *
 * Created on June 28, 2007, 3:19 PM
 */

package tabbedpanesnapshot.demo;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import tabbedpanesnapshot.SnapTipTabbedPane;

/**
 */
public class DemoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form DemoFrame */
	public DemoFrame() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// ">//GEN-BEGIN:initComponents
	private void initComponents() {
		snapTipTabbedPane1 = new SnapTipTabbedPane();
		jPanel1 = new JPanel();
		jRadioButton4 = new JRadioButton();
		jRadioButton3 = new JRadioButton();
		jRadioButton2 = new JRadioButton();
		jRadioButton1 = new JRadioButton();
		jButton2 = new JButton();
		jButton1 = new JButton();
		jCheckBox1 = new JCheckBox();
		jTextField1 = new JTextField();
		jLabel1 = new JLabel();
		jComboBox3 = new JComboBox();
		jScrollPane9 = new JScrollPane();
		jList5 = new JList();
		jPanel6 = new JPanel();
		jToggleButton2 = new JToggleButton();
		jPanel2 = new JPanel();
		jLabel2 = new JLabel();
		jScrollPane1 = new JScrollPane();
		jList1 = new JList();
		jScrollPane2 = new JScrollPane();
		jList2 = new JList();
		jScrollPane3 = new JScrollPane();
		jList3 = new JList();
		jSlider1 = new JSlider();
		jComboBox1 = new JComboBox();
		jTextField2 = new JTextField();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jPanel3 = new JPanel();
		jCheckBox2 = new JCheckBox();
		jScrollPane4 = new JScrollPane();
		jTextArea1 = new JTextArea();
		jComboBox2 = new JComboBox();
		jPanel4 = new JPanel();
		jSplitPane1 = new JSplitPane();
		jScrollPane5 = new JScrollPane();
		jTree1 = new JTree();
		jScrollPane6 = new JScrollPane();
		jTable1 = new JTable();
		jPanel5 = new JPanel();
		jTextField3 = new JTextField();
		jToggleButton1 = new JToggleButton();
		jCheckBox3 = new JCheckBox();
		jScrollPane7 = new JScrollPane();
		jTextArea2 = new JTextArea();
		jScrollPane8 = new JScrollPane();
		jList4 = new JList();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jRadioButton4.setText("\u7528\u4e0d\u6e05\u9664");
		jRadioButton4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jRadioButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jRadioButton3.setText("\u6bcf\u5929\u6e05\u9664");
		jRadioButton3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jRadioButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jRadioButton2.setText("\u6bcf\u5468\u6e05\u9664");
		jRadioButton2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jRadioButton1.setText("\u6bcf\u6708\u6e05\u9664");
		jRadioButton1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jButton2.setText("\u786e\u5b9a");

		jButton1.setText("\u53d6\u6d88");

		jCheckBox1.setText("\u4e0b\u8f7d\u4e4b\u524d\u662f\u5426\u8be2\u95ee");
		jCheckBox1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jLabel1.setText("\u4e0b\u8f7d\u4f4d\u7f6e");

		jComboBox3.setModel(new DefaultComboBoxModel(new String[] { "Item 1",
				"Item 2", "Item 3", "Item 4" }));

		jList5.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane9.setViewportView(jList5);

		jToggleButton2.setText("jToggleButton2");

		GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel6Layout.createSequentialGroup().addGap(20, 20, 20)
						.addComponent(jToggleButton2).addContainerGap(37,
								Short.MAX_VALUE)));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel6Layout.createSequentialGroup().addGap(27, 27, 27)
						.addComponent(jToggleButton2).addContainerGap(89,
								Short.MAX_VALUE)));

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jComboBox3,
																				GroupLayout.PREFERRED_SIZE,
																				189,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				70,
																				Short.MAX_VALUE)
																		.addComponent(
																				jButton2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jButton1))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jTextField1,
																				GroupLayout.DEFAULT_SIZE,
																				327,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jCheckBox1)
																						.addComponent(
																								jPanel6,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jRadioButton4)
																						.addComponent(
																								jRadioButton3)
																						.addComponent(
																								jRadioButton2)
																						.addComponent(
																								jRadioButton1))
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				jScrollPane9,
																				GroupLayout.PREFERRED_SIZE,
																				128,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																jTextField1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(
																				17,
																				17,
																				17)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jScrollPane9,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jCheckBox1)))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(
																				25,
																				25,
																				25)
																		.addComponent(
																				jRadioButton4)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jPanel6,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								jPanel1Layout
																										.createSequentialGroup()
																										.addComponent(
																												jRadioButton3)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jRadioButton2)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jRadioButton1)))))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jButton1)
														.addComponent(jButton2)
														.addComponent(
																jComboBox3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		snapTipTabbedPane1.addTab("\u901a\u7528\u8bbe\u7f6e", jPanel1);

		jLabel2.setText("jLabel2");

		jList1.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane1.setViewportView(jList1);

		jList2.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane2.setViewportView(jList2);

		jList3.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane3.setViewportView(jList3);

		jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "Item 1",
				"Item 2", "Item 3", "Item 4" }));

		jButton3.setText("\u53d6\u6d88");

		jButton4.setText("\u786e\u5b9a");

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jSlider1,
																								GroupLayout.DEFAULT_SIZE,
																								375,
																								Short.MAX_VALUE)
																						.addGroup(
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel2)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPane1,
																												GroupLayout.PREFERRED_SIZE,
																												104,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPane3,
																												GroupLayout.DEFAULT_SIZE,
																												114,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jScrollPane2,
																												GroupLayout.PREFERRED_SIZE,
																												107,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGap(
																				48,
																				48,
																				48)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jButton4)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jButton3))
																						.addGroup(
																								jPanel2Layout
																										.createSequentialGroup()
																										.addComponent(
																												jComboBox1,
																												GroupLayout.PREFERRED_SIZE,
																												142,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jTextField2,
																												GroupLayout.PREFERRED_SIZE,
																												168,
																												GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jScrollPane2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jScrollPane1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel2))
										.addGap(15, 15, 15)
										.addComponent(jSlider1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jComboBox1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jTextField2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jButton3)
														.addComponent(jButton4))
										.addContainerGap(20, Short.MAX_VALUE)));
		snapTipTabbedPane1.addTab("\u5b57\u4f53\u8bbe\u7f6e", jPanel2);

		jCheckBox2.setText("jCheckBox2");
		jCheckBox2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane4.setViewportView(jTextArea1);

		jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "Item 1",
				"Item 2", "Item 3", "Item 4" }));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(
								jPanel3Layout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane4,
												GroupLayout.DEFAULT_SIZE, 375,
												Short.MAX_VALUE).addComponent(
												jCheckBox2).addComponent(
												jComboBox2, 0, 375,
												Short.MAX_VALUE))
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jCheckBox2).addGap(16, 16, 16)
						.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 21,
								Short.MAX_VALUE).addComponent(jScrollPane4,
								GroupLayout.PREFERRED_SIZE, 162,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		snapTipTabbedPane1.addTab("\u7f51\u7edc\u8bbe\u7f6e", jPanel3);

		jScrollPane5.setViewportView(jTree1);

		jSplitPane1.setLeftComponent(jScrollPane5);

		jTable1.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } },
				new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane6.setViewportView(jTable1);

		jSplitPane1.setRightComponent(jScrollPane6);

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout.createSequentialGroup().addContainerGap()
						.addComponent(jSplitPane1, GroupLayout.DEFAULT_SIZE,
								375, Short.MAX_VALUE).addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				jPanel4Layout.createSequentialGroup().addContainerGap()
						.addComponent(jSplitPane1, GroupLayout.DEFAULT_SIZE,
								234, Short.MAX_VALUE).addContainerGap()));
		snapTipTabbedPane1.addTab("\u9ad8\u7ea7\u8bbe\u7f6e", jPanel4);

		jTextField3.setText("jTextField3");

		jToggleButton1.setText("jToggleButton1");

		jCheckBox3.setText("jCheckBox3");
		jCheckBox3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		jCheckBox3.setMargin(new java.awt.Insets(0, 0, 0, 0));

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane7.setViewportView(jTextArea2);

		jList4.setModel(new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane8.setViewportView(jList4);

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout
				.setHorizontalGroup(jPanel5Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel5Layout
																		.createSequentialGroup()
																		.addComponent(
																				jTextField3,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				16,
																				16,
																				16)
																		.addComponent(
																				jToggleButton1))
														.addComponent(
																jScrollPane7,
																GroupLayout.PREFERRED_SIZE,
																169,
																GroupLayout.PREFERRED_SIZE))
										.addGap(15, 15, 15)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jCheckBox3,
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane8,
																GroupLayout.DEFAULT_SIZE,
																180,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel5Layout
				.setVerticalGroup(jPanel5Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextField3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jToggleButton1)
														.addComponent(
																jCheckBox3))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane8,
																GroupLayout.DEFAULT_SIZE,
																205,
																Short.MAX_VALUE)
														.addComponent(
																jScrollPane7,
																GroupLayout.DEFAULT_SIZE,
																205,
																Short.MAX_VALUE))
										.addContainerGap()));
		snapTipTabbedPane1.addTab("\u670d\u52a1\u5668\u8bbe\u7f6e", jPanel5);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(
						snapTipTabbedPane1, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(
						snapTipTabbedPane1, GroupLayout.DEFAULT_SIZE, 284,
						Short.MAX_VALUE).addContainerGap()));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			System.setProperty("swing.useSystemFontSettings", "false");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DemoFrame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;
	private JCheckBox jCheckBox3;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JComboBox jComboBox3;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JList jList1;
	private JList jList2;
	private JList jList3;
	private JList jList4;
	private JList jList5;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;
	private JPanel jPanel5;
	private JPanel jPanel6;
	private JRadioButton jRadioButton1;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton3;
	private JRadioButton jRadioButton4;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JScrollPane jScrollPane4;
	private JScrollPane jScrollPane5;
	private JScrollPane jScrollPane6;
	private JScrollPane jScrollPane7;
	private JScrollPane jScrollPane8;
	private JScrollPane jScrollPane9;
	private JSlider jSlider1;
	private JSplitPane jSplitPane1;
	private JTable jTable1;
	private JTextArea jTextArea1;
	private JTextArea jTextArea2;
	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;
	private JToggleButton jToggleButton1;
	private JToggleButton jToggleButton2;
	private JTree jTree1;
	private SnapTipTabbedPane snapTipTabbedPane1;
	// End of variables declaration//GEN-END:variables

}
