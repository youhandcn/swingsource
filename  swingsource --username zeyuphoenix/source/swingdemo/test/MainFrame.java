package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jbordertest.TestJBorder;
import jbutton.RButton;
import jcomboboxtest.TestJComboBox;
import jcombomenutest.TestComboMenu;
import jlisttest.TestJList;
import jtable.celltest.TestButtonCellTable;
import jtable.celltest.TestCellEachEditor;
import jtable.celltest.TestEachCellTable;
import jtable.celltest.TestProgressTable;
import jtable.celltest.TestRadioCell;
import jtable.tableheadertest.TestComboxHeaderTable;
import jtable.tableheadertest.TestEditButtonHeaderTable;
import jtable.tableheadertest.TestGroupHeaderTable;
import jtable.tableheadertest.TestHightHeaderTabel;
import jtable.tableheadertest.TestListTable;
import jtable.tableheadertest.TestMyTableSorter;
import jtable.tableheadertest.TestWideHeaderTabel;
import jtable.tableverticalheadertest.TestMyVerticalTable;
import jtabletest.TestColumn;
import jtabletest.TestHideColumnTable;
import jtabletest.TestRow;
import jtabpanetest.TestTab;
import jtreetest.TestCheckNodeTree;
import jtreetest.TestJtree;

/**
 * just for test.
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private RButton RButton = null;
	private RButton RButton1 = null;
	private RButton RButton2 = null;
	private RButton RButton3 = null;
	private RButton RButton31 = null;
	private RButton RButton311 = null;
	private RButton RButton3111 = null;
	private RButton RButton3112 = null;
	private RButton RButton3113 = null;
	private RButton RButton3114 = null;
	private RButton RButton3115 = null;
	private RButton RButton3116 = null;
	private RButton RButton3117 = null;
	private RButton RButton3118 = null;
	private RButton RButton3119 = null;
	private RButton RButton31110 = null;
	private RButton RButton31111 = null;
	private RButton RButton31112 = null;
	private RButton RButton31113 = null;
	private RButton RButton31114 = null;
	private RButton RButton31115 = null;
	private RButton RButton31116 = null;
	private RButton RButton31117 = null;

	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBounds(250, 250, 800, 600);
		this.setLayout(null);
		this.setTitle("Test My Two Weeks Study!");
		this.add(getRButton());
		this.add(getRButton1());
		this.add(getRButton2());
		this.add(getRButton3());
		this.add(getRButton31());
		this.add(getRButton311());
		this.add(getRButton3111());
		this.add(getRButton3112());
		this.add(getRButton3113());
		this.add(getRButton3114());
		this.add(getRButton3115());
		this.add(getRButton3116());
		this.add(getRButton3117());
		this.add(getRButton3118());
		this.add(getRButton3119());
		this.add(getRButton31110());
		this.add(getRButton31111());
		this.add(getRButton31112());
		this.add(getRButton31113());
		this.add(getRButton31114());
		this.add(getRButton31115());
		this.add(getRButton31116());
		this.add(getRButton31117());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes RButton
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton() {
		if (RButton == null) {
			RButton = new RButton(0);
			RButton.setBounds(new Rectangle(50, 50, 80, 40));
			RButton.setText("JBorder");
			RButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					(new TestJBorder()).setVisible(true);
				}
			});
		}
		return RButton;
	}

	/**
	 * This method initializes RButton1
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton1() {
		if (RButton1 == null) {
			RButton1 = new RButton(1);
			RButton1.setBounds(new Rectangle(50, 100, 120, 30));
			RButton1.setText("ComboBox");
			RButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame frame = new TestJComboBox();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton1;
	}

	/**
	 * This method initializes RButton2
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton2() {
		if (RButton2 == null) {
			RButton2 = new RButton(2);
			RButton2.setText("ComMenu");
			RButton2.setBounds(new Rectangle(50, 150, 100, 30));
			RButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestComboMenu();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton2;
	}

	/**
	 * This method initializes RButton3
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3() {
		if (RButton3 == null) {
			RButton3 = new RButton(3);
			RButton3.setText("TestJList");
			RButton3.setBounds(new Rectangle(50, 200, 120, 30));
			RButton3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestJList();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3;
	}

	/**
	 * This method initializes RButton31
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31() {
		if (RButton31 == null) {
			RButton31 = new RButton(4);
			RButton31.setText("Tree");
			RButton31.setBounds(new Rectangle(50, 230, 60, 60));
			RButton31.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestCheckNodeTree();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31;
	}

	/**
	 * This method initializes RButton311
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton311() {
		if (RButton311 == null) {
			RButton311 = new RButton(0);
			RButton311.setText("Jtree");
			RButton311.setBounds(new Rectangle(50, 300, 120, 30));
			RButton311.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestJtree();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton311;
	}

	/**
	 * This method initializes RButton3111
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3111() {
		if (RButton3111 == null) {
			RButton3111 = new RButton(2);
			RButton3111.setText("Tab");
			RButton3111.setBounds(new Rectangle(50, 350, 120, 30));
			RButton3111.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new TestTab("Tab").runTest();
				}
			});
		}
		return RButton3111;
	}

	/**
	 * This method initializes RButton3112
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3112() {
		if (RButton3112 == null) {
			RButton3112 = new RButton(3);
			RButton3112.setText("Column");
			RButton3112.setBounds(new Rectangle(50, 420, 80, 80));
			RButton3112.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestColumn();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3112;
	}

	/**
	 * This method initializes RButton3113
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3113() {
		if (RButton3113 == null) {
			RButton3113 = new RButton(4);
			RButton3113.setText("Hide");
			RButton3113.setBounds(new Rectangle(300, 60, 60, 60));
			RButton3113.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestHideColumnTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3113;
	}

	/**
	 * This method initializes RButton3114
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3114() {
		if (RButton3114 == null) {
			RButton3114 = new RButton(1);
			RButton3114.setText("TestRow");
			RButton3114.setBounds(new Rectangle(300, 130, 120, 30));
			RButton3114.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestRow();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3114;
	}

	/**
	 * This method initializes RButton3115
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3115() {
		if (RButton3115 == null) {
			RButton3115 = new RButton(3);
			RButton3115.setText("Vertical");
			RButton3115.setBounds(new Rectangle(300, 180, 120, 30));
			RButton3115.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestMyVerticalTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3115;
	}

	/**
	 * This method initializes RButton3116
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3116() {
		if (RButton3116 == null) {
			RButton3116 = new RButton(1);
			RButton3116.setText("ComHeader");
			RButton3116.setBounds(new Rectangle(300, 230, 120, 30));
			RButton3116.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestComboxHeaderTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3116;
	}

	/**
	 * This method initializes RButton3117
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3117() {
		if (RButton3117 == null) {
			RButton3117 = new RButton(2);
			RButton3117.setText("PT");
			RButton3117.setBounds(new Rectangle(250, 280, 60, 120));
			RButton3117.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame f = new JFrame("TestProgressTable");
					f.getContentPane().add(new TestProgressTable(),
							BorderLayout.CENTER);
					f.setSize(500, 300);
					centerFrame(f);
					f.setVisible(true);
				}
			});
		}
		return RButton3117;
	}

	/**
	 * This method initializes RButton3118
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3118() {
		if (RButton3118 == null) {
			RButton3118 = new RButton(0);
			RButton3118.setText("RadioCell");
			RButton3118.setBounds(new Rectangle(300, 330, 120, 30));
			RButton3118.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestRadioCell();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3118;
	}

	/**
	 * This method initializes RButton3119
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton3119() {
		if (RButton3119 == null) {
			RButton3119 = new RButton(4);
			RButton3119.setText("HT");
			RButton3119.setBounds(new Rectangle(300, 360, 60, 60));
			RButton3119.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestEditButtonHeaderTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton3119;
	}

	/**
	 * This method initializes RButton31110
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31110() {
		if (RButton31110 == null) {
			RButton31110 = new RButton(2);
			RButton31110.setText("GroupHeader");
			RButton31110.setBounds(new Rectangle(300, 430, 120, 30));
			RButton31110.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestGroupHeaderTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31110;
	}

	/**
	 * This method initializes RButton31111
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31111() {
		if (RButton31111 == null) {
			RButton31111 = new RButton(1);
			RButton31111.setText("HightHeader");
			RButton31111.setBounds(new Rectangle(550, 120, 120, 30));
			RButton31111.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestHightHeaderTabel();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31111;
	}

	/**
	 * This method initializes RButton31112
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31112() {
		if (RButton31112 == null) {
			RButton31112 = new RButton(0);
			RButton31112.setText("ListTable");
			RButton31112.setBounds(new Rectangle(550, 170, 120, 30));
			RButton31112.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestListTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31112;
	}

	/**
	 * This method initializes RButton31113
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31113() {
		if (RButton31113 == null) {
			RButton31113 = new RButton(4);
			RButton31113.setText("Sort");
			RButton31113.setBounds(new Rectangle(550, 200, 60, 60));
			RButton31113.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame frame = new JFrame("SortableTable Example");
					frame.getContentPane().add(new TestMyTableSorter(),
							BorderLayout.CENTER);
					frame.setSize(400, 200);
					centerFrame(frame);
					frame.setVisible(true);

				}
			});
		}
		return RButton31113;
	}

	/**
	 * This method initializes RButton31114
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31114() {
		if (RButton31114 == null) {
			RButton31114 = new RButton(3);
			RButton31114.setText("WideHeader");
			RButton31114.setBounds(new Rectangle(550, 270, 120, 30));
			RButton31114.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestWideHeaderTabel();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31114;
	}

	/**
	 * This method initializes RButton31115
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31115() {
		if (RButton31115 == null) {
			RButton31115 = new RButton(1);
			RButton31115.setText("ButtonCell");
			RButton31115.setBounds(new Rectangle(550, 320, 120, 30));
			RButton31115.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestButtonCellTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31115;
	}

	/**
	 * This method initializes RButton31116
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31116() {
		if (RButton31116 == null) {
			RButton31116 = new RButton(3);
			RButton31116.setText("C");
			RButton31116.setBounds(new Rectangle(550, 370, 50, 120));
			RButton31116.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestCellEachEditor();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31116;
	}

	/**
	 * This method initializes RButton31117
	 * 
	 * @return javax.swing.RButton
	 */
	private RButton getRButton31117() {
		if (RButton31117 == null) {
			RButton31117 = new RButton(2);
			RButton31117.setText("EachCell");
			RButton31117.setBounds(new Rectangle(550, 500, 120, 30));
			RButton31117.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					JFrame frame = new TestEachCellTable();
					centerFrame(frame);
					frame.setVisible(true);
				}
			});
		}
		return RButton31117;
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
				// try {
				// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				//			
				// } catch (Exception e) {
				// e.printStackTrace();
				// }

				try {
					// JFrame.setDefaultLookAndFeelDecorated(true);
					// JDialog.setDefaultLookAndFeelDecorated(true);

					// SubstanceImageWatermark waterMark = new
					// SubstanceImageWatermark(
					// MainFrame.class
					// .getResourceAsStream("/image/back.png"));
					// waterMark.setKind(ImageWatermarkKind.SCREEN_CENTER_SCALE);
					// SubstanceSkin skin = new OfficeBlue2007Skin()
					// .withWatermark(waterMark);

					// UIManager
					// .setLookAndFeel(new
					// SubstanceOfficeBlue2007LookAndFeel());

					// UIManager
					// .setLookAndFeel(new
					// SubstanceOfficeSilver2007LookAndFeel());
					// SubstanceLookAndFeel.setSkin(skin);
				} catch (Exception e) {
					e.printStackTrace();
				}

				(new MainFrame()).setVisible(true);
			}
		});
	}

	/**
	 */
	protected void centerFrame(JFrame frame) {

		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = frame.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}

			frame.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}