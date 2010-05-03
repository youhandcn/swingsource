package oldcheckbox;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class TestCheckNodeTree extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	public TestCheckNodeTree() {
		super("CheckNode");
		setSize(600, 400);
		String[] strs = { "JComponent", // 0
				"JButton", // 1
				"Info", // 2
				"Action", // 3
				"JLabel" }; // 4

		CheckBoxTreeNode[] nodes = new CheckBoxTreeNode[strs.length];
		for (int i = 0; i < strs.length; i++) {
			nodes[i] = new CheckBoxTreeNode(strs[i]);
		}
		nodes[0].add(nodes[1]);
		nodes[1].add(nodes[2]);
		nodes[1].add(nodes[3]);
		nodes[0].add(nodes[4]);
		CheckBoxTree tree = new CheckBoxTree(new DefaultTreeModel(nodes[0]));
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane sp = new JScrollPane(tree);
		getContentPane().add(sp, BorderLayout.CENTER);
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
