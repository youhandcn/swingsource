package jtable;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jcombomenu.ArrowIcon;

/**
 * Provides a scrollable view of a lightweight component. A
 * <code>JScrollPane</code> manages a viewport, optional vertical and
 * horizontal scroll bars, and optional row and column heading viewports.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable MyScrollPane.java <br>
 *         2008 2008/03/31 16:38:31 <br>
 */
public class MyScrollPane extends JScrollPane {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** the button panel that in table header. */
	private Component columnButtonPane = null;

	/**
	 * Provides a scrollable view of a lightweight component.
	 * 
	 * @param table
	 *            the table in JScrollPane.
	 */
	public MyScrollPane(JTable table) {
		super(table);
		TableColumnModel cm = table.getColumnModel();
		// set new header.
		LimitedTableHeader header = new LimitedTableHeader(cm);
		table.setTableHeader(header);
		columnButtonPane = createUpperCorner(header);
		setCorner(UPPER_RIGHT_CORNER, columnButtonPane);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		// set layout.
		MyScrollPaneLayout layout = new MyScrollPaneLayout();
		setLayout(layout);
		layout.syncWithScrollPane(this);
	}

	/**
	 * create the button panel that in table header.
	 * 
	 * @param header
	 *            JTable Header
	 * @return the button panel that in table header.
	 */
	protected Component createUpperCorner(JTableHeader header) {
		ColumnButtonPanel corner = new ColumnButtonPanel(header);
		return corner;
	}

	/**
	 * This is the object which manages the header of the <code>JTable</code>.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtable MyScrollPane.java <br>
	 *         2008 2008/03/31 16:43:45 <br>
	 */
	private class LimitedTableHeader extends JTableHeader {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * This is the object which manages the header of the
		 * <code>JTable</code>.
		 * 
		 * @param cm
		 *            Table Column Model.
		 */
		public LimitedTableHeader(TableColumnModel cm) {
			super(cm);
		}

		// actually, this is a not complete way. but easy one.
		// you can see last column painted wider, short time :)
		// If you don't like this kind cheap fake,
		// you have to overwrite the paint method in UI class.
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			columnButtonPane.repaint();
		}
	}

	/**
	 * create the button panel that in table header.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtable MyScrollPane.java <br>
	 *         2008 2008/03/31 16:46:51 <br>
	 */
	private class ColumnButtonPanel extends JPanel {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		/** the pane in table. */
		private JTable table = null;
		/** the table column model. */
		private TableColumnModel cm = null;
		/** show button. */
		private JButton revealButton = null;
		/** hide button. */
		private JButton hideButton = null;
		/** save column stack . */
		private Stack<TableColumn> stack = null;

		/**
		 * create the button panel that in table header.
		 * 
		 * @param header
		 *            JTable Header
		 */
		public ColumnButtonPanel(JTableHeader header) {
			setLayout(new GridLayout(1, 2));
			setBorder(new LinesBorder(SystemColor.controlShadow, new Insets(0,
					1, 0, 0)));
			init(header);
		}

		/**
		 * init the button panel.
		 * 
		 * @param header
		 *            JTable Header
		 */
		private void init(JTableHeader header) {
			stack = new Stack<TableColumn>();
			table = header.getTable();
			cm = table.getColumnModel();

			revealButton = createButton(header, SwingConstants.WEST);
			hideButton = createButton(header, SwingConstants.EAST);
			add(revealButton);
			add(hideButton);

			revealButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TableColumn column = (TableColumn) stack.pop();
					cm.addColumn(column);
					if (stack.empty()) {
						revealButton.setEnabled(false);
					}
					hideButton.setEnabled(true);
					table.sizeColumnsToFit(-1);
				}
			});
			hideButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int n = cm.getColumnCount();
					TableColumn column = cm.getColumn(n - 1);
					stack.push(column);
					cm.removeColumn(column);
					if (n < 3) {
						hideButton.setEnabled(false);
					}
					revealButton.setEnabled(true);
					table.sizeColumnsToFit(-1);
				}
			});

			if (1 < cm.getColumnCount()) {
				hideButton.setEnabled(true);
			} else {
				hideButton.setEnabled(false);
			}
			revealButton.setEnabled(false);

		}

		/**
		 * create the button panel that in table header.
		 * 
		 * @param header
		 *            table header.
		 * @param direction
		 *            direction.
		 * @return the button panel that in table header.
		 */
		protected JButton createButton(JTableHeader header, int direction) {
			int iconHeight = header.getPreferredSize().height - 8;
			JButton button = new JButton();
			// set icon.
			button.setIcon(new ArrowIcon(iconHeight, direction, true));
			button.setDisabledIcon(new ArrowIcon(iconHeight, direction, false));
			button.setRequestFocusEnabled(false);
			// set attribute.
			button.setForeground(header.getForeground());
			button.setBackground(header.getBackground());
			button.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			return button;
		}
	}
}
