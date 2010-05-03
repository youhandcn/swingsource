package jtreetest;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import jtree.MyCellEditor;
import jtree.MyFolderIcon;
import jtree.MyLeafIcon;
import jtree.MyTextIcon;
import jtree.MyTreeNode;
import jtree.MyTreeRenderer;

public class TestJtree extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;
	private JTree jTree = null;
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
		this.setSize(446, 300);
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
			jContentPane.add(getJScrollPane());

			JButton enabledButton = new JButton("Enabled");
			JButton disabledButton = new JButton("Disabled");
			JPanel pane = new JPanel();
			pane.setLayout(new GridLayout(2, 1));
			enabledButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setNodeEnabled(true);
				}
			});
			disabledButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setNodeEnabled(false);
				}
			});
			pane.add(enabledButton);
			pane.add(disabledButton);
			jContentPane.add(pane);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
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
			nodes[0].setIcon(MetalIconFactory.getTreeFolderIcon());
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
			nodes[3].setEnabled(false);
			nodes[5].setEnabled(false);

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
			renderer.setMyLeafIcon(new MyLeafIcon());
			renderer.setMyOpenIcon(new MyFolderIcon());
			jTree1.putClientProperty("JTree.icons", makeIcons());
			renderer.setMyClosedIcon(new MyFolderIcon());
			jTree1.setCellRenderer(renderer);
			jTree1.setEditable(true);
			jTree1.setCellEditor(new MyCellEditor(jTree1,
					(DefaultTreeCellRenderer) jTree1.getCellRenderer()));
		}
		return jTree1;
	}

	private void setNodeEnabled(boolean enabled) {
		TreePath[] path = jTree1.getSelectionPaths();
		if (path == null)
			return;
		MyTreeNode node = null;
		for (int i = 0; i < path.length; i++) {
			node = (MyTreeNode) path[i].getLastPathComponent();
			// if (node.isRoot()) {
			// } else {
			int beforeChildCount = node.getChildCount();
			node.setEnabled(enabled);
			int afterChildCount = node.getChildCount();
			if (beforeChildCount == afterChildCount) {
				((DefaultTreeModel) jTree1.getModel()).nodeChanged(node);
			} else {
				((DefaultTreeModel) jTree1.getModel()).reload();
			}
			// }
		}
	}

	private Hashtable<String, Icon> makeIcons() {
		Hashtable<String, Icon> icons = new Hashtable<String, Icon>();
		icons.put("floppyDrive", MetalIconFactory.getTreeFloppyDriveIcon());
		icons.put("hardDrive", MetalIconFactory.getTreeHardDriveIcon());
		icons.put("computer", MetalIconFactory.getTreeComputerIcon());
		icons.put("c", MyTextIcon.getIcon("c"));
		icons.put("java", MyTextIcon.getIcon("java"));
		icons.put("html", MyTextIcon.getIcon("html"));
		return icons;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree() {
				/**
				 * UID.
				 */
				private static final long serialVersionUID = 1L;

				public String getToolTipText(MouseEvent evt) {
					if (getRowForLocation(evt.getX(), evt.getY()) == -1)
						return null;
					TreePath curPath = getPathForLocation(evt.getX(), evt
							.getY());
					return ((ToolTipTreeNode) curPath.getLastPathComponent())
							.getToolTipText();
				}
			};

			DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jTree
					.getCellRenderer();
			renderer.setOpenIcon(null);
			renderer.setClosedIcon(null);
			renderer.setLeafIcon(null);
			BasicTreeUI ui = (BasicTreeUI) jTree.getUI();
			ui.setExpandedIcon(null);
			ui.setCollapsedIcon(null);

		}
		return jTree;
	}

	private class ToolTipTreeNode extends DefaultMutableTreeNode {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		private String toolTipText;

		public ToolTipTreeNode(String str, String toolTipText) {
			super(str);
			this.toolTipText = toolTipText;
		}

		public String getToolTipText() {
			return toolTipText;
		}
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
