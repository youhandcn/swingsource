package jtable.celltest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jtable.cell.MyProgressCellRenderer;
import jtable.cell.MyProgressTableModel;

/**
 * test progress table.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell TestProgressTable.java <br>
 *         2008 2008/04/01 11:35:35 <br>
 */
public class TestProgressTable extends JPanel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * test progress table.
	 */
	public TestProgressTable() {
		setLayout(new BorderLayout());

		MyProgressTableModel dm = new MyProgressTableModel();
		dm.setDataVector(new Object[][] {
				{ "not human", new Integer(100), new Integer(100) },
				{ "hard worker", new Integer(76), new Integer(76) },
				{ "ordinary guy", new Integer(51), new Integer(51) },
		        { "custom", new Integer(35), new Integer(35) },
		        { "little", new Integer(2), new Integer(2) },
				{ "lazy fellow", new Integer(12), new Integer(12) } },
				new Object[] { "Name", "Result", "Indicator" });

		JTable table = new JTable(dm);

		MyProgressCellRenderer renderer = new MyProgressCellRenderer(
				MyProgressTableModel.MIN, MyProgressTableModel.MAX);
		renderer.setStringPainted(true);
		renderer.setBackground(table.getBackground());
		// set limit value and fill color
		Hashtable<Integer, Color> limitColors = new Hashtable<Integer, Color>();
		limitColors.put(new Integer(0), Color.green);
	      limitColors.put(new Integer(20), Color.GRAY);
		limitColors.put(new Integer(40), Color.blue);
		limitColors.put(new Integer(60), Color.yellow);
		limitColors.put(new Integer(80), Color.red);
		renderer.setLimits(limitColors);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);

		//JScrollPane与JTable交会的区域颜色
		JLabel label = new JLabel("");
		label.setBackground(Color.green);
		label.setOpaque(true);
		
		JScrollPane pane = new JScrollPane(table);
		pane.add(JScrollPane.UPPER_RIGHT_CORNER, label);
		add(pane, BorderLayout.CENTER);
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("TestProgressTable");
		f.getContentPane().add(new TestProgressTable(), BorderLayout.CENTER);
		f.setSize(400, 120);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
