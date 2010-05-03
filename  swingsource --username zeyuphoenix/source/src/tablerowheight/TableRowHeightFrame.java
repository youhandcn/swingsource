package tablerowheight;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableRowHeightFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<String> col = new Vector<String>();
	private DefaultTableModel dm = new DefaultTableModel(col, 0);
	private JScrollPane jScrollPane1 = new JScrollPane();
	private JTable table = new JTable(dm);

	public TableRowHeightFrame() {

		try {
			loadString();
			jbInit();
			loadata();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		TableRowHeightFrame frm = new TableRowHeightFrame();

		frm.setBounds(400, 300, 600, 400);
		frm.setVisible(true);

	}

	private void loadString() {
		col.add("id");
		col.add("name");
		col.add("age");
		col.add("address");

		dm.setColumnIdentifiers(col);
	}

	private void jbInit() throws Exception {
		this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(table, null);
		MouseAdapterListener listener = new MouseAdapterListener();
		table.addMouseListener(listener);
		table.addMouseMotionListener(listener);

	}

	private void loadata() {
		dm.addRow(new Object[] { "1", "zt", "30", "wuxi" });
		dm.addRow(new Object[] { "2", "zt", "30", "wuxi" });
		dm.addRow(new Object[] { "3", "zt", "30", "wuxi" });
		dm.addRow(new Object[] { "4", "zt", "30", "wuxi" });
	}

	private class MouseAdapterListener extends MouseMotionAdapter implements
			MouseListener {
		int oldY = 0;
		int newY = 0;
		int row = 0;
		int oldHeight = 0;
		boolean drag = false;
		int increase = 0;

		public MouseAdapterListener() {

		}

		public void mouseMoved(MouseEvent e) {
			int onRow = table.rowAtPoint(e.getPoint());

			int height = 0;
			for (int i = 0; i <= onRow; i++) {
				height = height + table.getRowHeight(i);
			}

			if (height - e.getY() < 3) {
				drag = true;
				table.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			} else {
				drag = false;
				table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		}

		public void mouseDragged(MouseEvent e) {
			if (drag) {
				int value = oldHeight + e.getY() - oldY;
				if (value < 30)
					table.setRowHeight(row, table.getRowHeight());
				else
					table.setRowHeight(row, oldHeight + e.getY() - oldY);

				table.setRowSelectionInterval(row, row);
			}
		}

		public void mousePressed(MouseEvent e) {
			oldY = e.getY();
			row = table.rowAtPoint(e.getPoint());
			oldHeight = table.getRowHeight(row);
			table.setRowSelectionInterval(row, row);
		}

		public void mouseReleased(MouseEvent e) {
			newY = e.getY();
			table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
}
