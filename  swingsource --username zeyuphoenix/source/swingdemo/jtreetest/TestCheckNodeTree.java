package jtreetest;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

import jtree.MyCheckRenderer;
import jtree.MyTreeNode;

public class TestCheckNodeTree extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	public TestCheckNodeTree() {
		super("CheckNode TreeExample");
		setSize(600, 400);
		String[] strs = { "swing", // 0
				"platf", // 1
				"basic", // 2
				"metal", // 3
				"JTree" }; // 4

		MyTreeNode[] nodes = new MyTreeNode[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nodes[i] = new MyTreeNode(strs[i]);
		}
		nodes[0].add(nodes[1]);
		nodes[1].add(nodes[2]);
		nodes[1].add(nodes[3]);
		nodes[0].add(nodes[4]);
		nodes[3].setSelected(true);
		JTree tree = new JTree(nodes[0]);
		tree.setCellRenderer(new MyCheckRenderer());
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		tree.addMouseListener(new NodeSelectionListener(tree));
		JScrollPane sp = new JScrollPane(tree);
		
		ModePanel mp = new ModePanel(nodes);
		mp.setOpaque(false);
		JTextArea textArea = new JTextArea(3, 10);
		JScrollPane textPanel = new JScrollPane(textArea);
		JButton button = new JButton("print");
		button.setOpaque(false);
		button.addActionListener(new ButtonActionListener(nodes[0], textArea));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(mp, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		textPanel.setBackground(Color.blue);
		getContentPane().add(sp, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.EAST);
		getContentPane().add(textPanel, BorderLayout.SOUTH);
	}

	class NodeSelectionListener extends MouseAdapter {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);
			TreePath path = tree.getPathForRow(row);
			// TreePath path = tree.getSelectionPath();
			if (path != null) {
				MyTreeNode node = (MyTreeNode) path.getLastPathComponent();
				boolean isSelected = !(node.isSelected());
				node.setSelected(isSelected);
				if (node.getSelectionMode() == 4) {
					if (isSelected) {
						tree.expandPath(path);
					} else {
						tree.collapsePath(path);
					}
				}
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
				// I need revalidate if node is root. but why?
				if (row == 0) {
					tree.revalidate();
					tree.repaint();
				}
			}
		}
	}

	class ModePanel extends JPanel implements ActionListener {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		MyTreeNode[] nodes;
		JRadioButton b_single, b_dig_in;

		ModePanel(MyTreeNode[] nodes) {
			this.nodes = nodes;
			setLayout(new GridLayout(2, 1));
			setBorder(new TitledBorder("Check Mode"));
			ButtonGroup group = new ButtonGroup();
			add(b_dig_in = new JRadioButton("DIG_IN  "));
			add(b_single = new JRadioButton("SINGLE  "));
			group.add(b_dig_in);
			group.add(b_single);
			b_dig_in.addActionListener(this);
			b_single.addActionListener(this);
			b_dig_in.setSelected(true);
		}

		public void actionPerformed(ActionEvent e) {
			int mode;
			if (b_single == e.getSource()) {
				mode = 1;
			} else {
				mode = 4;
			}
			for (int i = 0; i < nodes.length; i++) {
				nodes[i].setSelectionMode(mode);
			}
		}
	}

	class ButtonActionListener implements ActionListener {
		MyTreeNode root;
		JTextArea textArea;

		ButtonActionListener(final MyTreeNode root, final JTextArea textArea) {
			this.root = root;
			this.textArea = textArea;
		}

		public void actionPerformed(ActionEvent e) {
			Enumeration<?> enumTemp = root.breadthFirstEnumeration();
			while (enumTemp.hasMoreElements()) {
				MyTreeNode node = (MyTreeNode) enumTemp.nextElement();
				if (node.isSelected()) {
					TreeNode[] nodes = node.getPath();
					textArea.append("\n" + nodes[0].toString());
					for (int i = 1; i < nodes.length; i++) {
						textArea.append("/" + nodes[i].toString());
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		TestCheckNodeTree frame = new TestCheckNodeTree();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}
