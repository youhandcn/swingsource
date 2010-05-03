package changeddatepicker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>. <br>
 * in there, I put button in it.
 * 
 * @author zeyuphoenix <br>
 *         2008 2008/03/31 18:14:40 <br>
 */
public class CalendarTableCellRenderer extends JLabel implements
		TableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a JButton using simple.
	 */
	public CalendarTableCellRenderer() {
	}

	/**
	 * Returns the component used for drawing the cell. This method is used to
	 * configure the renderer appropriately before drawing.
	 * 
	 * @param table
	 *            the <code>JTable</code> that is asking the renderer to draw;
	 *            can be <code>null</code>
	 * @param value
	 *            the value of the cell to be rendered.
	 * @param isSelected
	 *            true if the cell is to be rendered with the selection
	 *            highlighted; otherwise false
	 * @param hasFocus
	 *            if true, render cell appropriately.
	 * @param row
	 *            the row index of the cell being drawn.
	 * @param column
	 *            the column index of the cell being drawn
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel label = this;
		// get table model.
		CalendarTableModel calendarModel = (CalendarTableModel) table
				.getModel();
		GregorianCalendar calendar = (GregorianCalendar) calendarModel
				.getCalendarClone();

		if (row >= 0) {
			Integer day = (Integer) value;

			label.setText(day.toString());
			Calendar today = new GregorianCalendar();
			int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			// Setting Foreground
			if (calendar.get(Calendar.DATE) == day.intValue()) {
				if (today.get(Calendar.DATE) == day.intValue()
						&& today.get(Calendar.MONTH) == calendarModel
								.getCalendarClone().get(Calendar.MONTH)
						&& today.get(Calendar.YEAR) == calendarModel
								.getCalendarClone().get(Calendar.YEAR)) {
					label.setForeground(Color.GREEN);
				} else {
					label.setForeground(Color.RED);
				}
			} else if (day.intValue() < 1 || day.intValue() > lastDay) {

				return null;
			} else {
				if (today.get(Calendar.DATE) == day.intValue()
						&& today.get(Calendar.MONTH) == calendarModel
								.getCalendarClone().get(Calendar.MONTH)
						&& today.get(Calendar.YEAR) == calendarModel
								.getCalendarClone().get(Calendar.YEAR)) {
					label.setForeground(Color.GREEN);
				} else {
					label.setForeground(Color.BLACK);
				}
			}
			// Setting background
			if (calendar.get(Calendar.DATE) == day.intValue()) {
				label.setBackground(Color.PINK);
			} else {
				label.setBackground(new Color(210, 228, 238));
			}
		}
		return label;
	}
	/**
	 * 
	 */
	public void paint(Graphics g) {

		// this is a hack to get the background painted
		// when using Windows Look & Feel
		g.setColor(new Color(210, 228, 238));

		g.fillRect(0, 0, getWidth(), getHeight());

		super.paint(g);
	}

}
