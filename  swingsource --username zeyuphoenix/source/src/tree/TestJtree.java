package tree;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class TestJtree extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	private JScrollPane jScrollPane1 = null;
	private JTree jTree1 = null;

	/**
	 * This is the default constructor
	 */
	public TestJtree() {
		super();
		initialize();
	}

	public static void main(String[] args) {
		new TestJtree().setVisible(true);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setAlwaysOnTop(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout(2, 2);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getJScrollPane1());
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTree1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree1() {
		if (jTree1 == null) {
			String[] strs = { "swing", // 0
					"plaf.c", // 1
					"basic.java", // 2
					"metal.html", // 3
					"Computer", // 4
					"C:", // 5
					"motif", "X:" }; // 6

			MyTreeNode[] nodes = new MyTreeNode[strs.length];
			for (int i = 0; i < strs.length; i++) {
				nodes[i] = new MyTreeNode(strs[i]);
			}
			nodes[0].setIcon(MetalIconFactory.getFileChooserHomeFolderIcon());
			nodes[0].add(nodes[1]);
			nodes[1].add(nodes[2]);
			nodes[1].add(nodes[3]);
			nodes[0].add(nodes[4]);
			nodes[0].add(nodes[5]);
			nodes[5].add(nodes[6]);
			nodes[5].add(nodes[7]);
			nodes[6].setIconName("computer");
			nodes[7].setIconName("floppyDrive");
			nodes[5].setIconName("hardDrive");

			jTree1 = new JTree(nodes[0]) {
				/**
				 * UID.
				 */
				private static final long serialVersionUID = 1L;

				public boolean isPathEditable(TreePath path) {
					MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
					if (node.isEnabled()) {
						return isEditable();
					} else {
						return false;
					}
				}
			};

			MyTreeRenderer renderer = new MyTreeRenderer();
			jTree1.putClientProperty("JTree.icons", makeIcons());
			jTree1.setCellRenderer(renderer);
			jTree1.setEditable(true);
			jTree1.setCellEditor(new MyCellEditor(jTree1,
					(DefaultTreeCellRenderer) jTree1.getCellRenderer()));
		}
		return jTree1;
	}

	private Hashtable<String, Icon> makeIcons() {
		Hashtable<String, Icon> icons = new Hashtable<String, Icon>();
		icons.put("floppyDrive", MetalIconFactory.getTreeFloppyDriveIcon());
		icons.put("hardDrive", MetalIconFactory.getTreeHardDriveIcon());
		icons.put("computer", MetalIconFactory.getTreeComputerIcon());
		return icons;
	}
}
