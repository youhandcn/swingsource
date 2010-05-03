package demo2;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TreeTableExample0 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new TreeTableExample0();
	}

	public TreeTableExample0() {
		JFrame frame = new JFrame("TreeTable");
		
		JTreeTable treeTable = new JTreeTable(new FileSystemModel());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		frame.getContentPane().add(new JScrollPane(treeTable));
		frame.pack();
		frame.setVisible(true);
	}
}
