package simplecheckbox;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
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
        getContentPane().add(sp, BorderLayout.CENTER);
    }

    class NodeSelectionListener extends MouseAdapter {
        JTree tree;

        NodeSelectionListener(JTree tree) {
            this.tree = tree;
        }

        @Override
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
                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
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
