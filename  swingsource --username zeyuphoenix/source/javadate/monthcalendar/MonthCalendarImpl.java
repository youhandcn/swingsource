package monthcalendar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * A piece of sample code to show how to implement a month calendar bean. Here
 * uses labels as cells or grids of a <code>MonthCalendar</code>.<br>
 * 
 */
public class MonthCalendarImpl extends MonthCalendar {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor: Layout style is <code>TABLE</code>, and week format
	 * is <code>SHORT</code>.
	 */
	public MonthCalendarImpl() {
		super(null, null, MonthCalendar.TABLE, MonthCalendar.SHORT, null, null);
	}

	/**
	 */
	public MonthCalendarImpl(int year, int month) {
		super(year, month);
	}

	/**
	 * Constructor: Initializes month calendar by given date.
	 * 
	 * @param date
	 *            a date which the month to be shown contains.
	 */
	public MonthCalendarImpl(Date date) {
		super(date, null, MonthCalendar.TABLE, MonthCalendar.SHORT, null, null);
	}

	/**
	 * Constructor: Initializes month calendar by given parameters.<br>
	 * <br>
	 * 
	 * Notes: this type of constructor MUST be defined in your own
	 * <code>MonthCalendar</code> implementation!
	 */
	public MonthCalendarImpl(Date date, Locale locale, Calendar calendar2,
			int layout, int weekFormat, MonthCalendarColor colors, Font font) {
		super(date, locale, layout, weekFormat, colors, font);
	}

	/**
	 * Here the component is a panel which has been laid a group of labels, as
	 * date cell. These cells are laid in <code>GridLayout</code>.
	 * 
	 * @return the grid panel.
	 */
	protected Component getComponent() {
		if (component == null) {
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			component = panel;
		}
		return component;
	}

	/**
	 * Creates some <code>Cell</code> objects. These objects act as date cells,
	 * each one showing a number standing for a date in one month.
	 */
	protected void setComponent() {
		String pattern;
		switch (weekFormat) {
		case LONG:
			pattern = "EEEE";
			break;
		case SHORT:
		default:
			pattern = "E";
		}
		weekPattern = new SimpleDateFormat(pattern, getLocale());

		final int colCount = getColumnCount();
		final int rowCount = getRowCount();

		JPanel panel = (JPanel) component;
		panel.removeAll();
		panel.setLayout(new GridLayout(rowCount, colCount));
		final Cell[][] cell = new Cell[colCount][rowCount];
		for (int i = 0; i < rowCount; i++)
			for (int j = 0; j < colCount; j++) {
				cell[j][i] = new Cell(i, j);
			}
		for (int i = 0; i < rowCount; i++)
			for (int j = 0; j < colCount; j++) {
				panel.add(cell[j][i]);
				final int i0 = i;
				final int j0 = j;
				cell[j][i].addMouseListener(new MouseAdapter() {
					/**
					 * Implements such a function: when one cell (one day) has
					 * been selected by mouse, all other cells in this month
					 * should be deselected.
					 * 
					 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
					 */
					public void mousePressed(MouseEvent e) {
						cell[j0][i0].setSelected(true);
						for (int i1 = 0; i1 < rowCount; i1++)
							for (int j1 = 0; j1 < colCount; j1++) {
								if (!(i1 == i0 && j1 == j0))
									cell[j1][i1].setSelected(false);
							}
					}
				});
			}
		panel.updateUI();// Refresh all month calendars
	}

	/**
	 * This is a private bean, and it defines a <code>JLabel</code> component by
	 * adding some special behavior.
	 */
	private class Cell extends JLabel {
		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Row index of the grids.
		 */
		int row;

		/**
		 * Column index of the grids.
		 */
		int col;

		/**
		 * The day of a month in primary calendar.
		 */
		int day = 0;

		/**
		 * "Current" day of a month in primary calendar.
		 */
		int todayInMonth = calendar.get(Calendar.DATE);

		/**
		 * Secondary date should be smaller than primary date when drawing.
		 */
		private static final int SHRINK = 4;

		/**
		 * @param row
		 * @param col
		 */
		public Cell(int row, int col) {
			this.row = row;
			this.col = col;
			if (isHeader())
				initHeader();
			else
				initCell();
		}

		/**
		 * @return true if the cell stands for a header.
		 */
		private boolean isHeader() {
			switch (layout) {
			case VERTICAL:
				if (col == 0)
					return true;
				else
					return false;
			case HORIZONTAL:
			case TABLE:
			default:
				if (row == 0)
					return true;
				else
					return false;
			}
		}

		/**
		 * Initializes the header.
		 */
		private void initHeader() {
			boolean isSaturday = false, isSunday = false;
			int field = 0, value = 0;
			switch (layout) {
			case HORIZONTAL:
				isSaturday = (col + firstInWeek) % weekCount == 0;
				isSunday = (col + firstInWeek) % weekCount == 1;
				field = Calendar.DATE;
				value = calendar.getMinimum(Calendar.DATE) + col;
				break;
			case VERTICAL:
				isSaturday = (row + firstInWeek) % weekCount == 0;
				isSunday = (row + firstInWeek) % weekCount == 1;
				field = Calendar.DATE;
				value = calendar.getMinimum(Calendar.DATE) + row;
				break;
			case TABLE:
			default:
				isSaturday = col == Calendar.SATURDAY - firstDayOfWeek;
				isSunday = col == (weekCount - (firstDayOfWeek - Calendar.SUNDAY))
						% weekCount;
				field = Calendar.DAY_OF_WEEK;
				value = (calendar.getFirstDayOfWeek() + col) % weekCount;
			}

			if (isSaturday) {
				setForeground(colors.fgSaturday);
			} else if (isSunday) {
				setForeground(colors.fgSunday);
			} else {
				setForeground(colors.fgCell);
			}
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			setFont(MonthCalendarImpl.this.getFont());
			int current = calendar.get(field);
			calendar.set(field, value);
			setText(weekPattern.format(calendar.getTime()));
			calendar.set(field, current);// Resume the date
			setBorder(BorderFactory.createLoweredBevelBorder());
		}

		/**
		 * The key method of <code>Cell</code>: initializes the date cell.
		 */
		private void initCell() {
			switch (layout) {
			case HORIZONTAL:
				day = col + 1;
				break;
			case VERTICAL:
				day = row + 1;
				break;
			case TABLE:
			default:
				if (row == 1 && col < weekCount - firstWeekRest
						|| row == getRowCount() - 1 && col >= lastWeekRest) {
					// No days in these cells.
					day = 0;
					setBackground(null);
					setForeground(null);
				} else {
					day = (row - 1) * weekCount
							+ (col - (weekCount - firstWeekRest) + 1);
				}
			}
			if (day == 0) {
				return;
			}
			calendar.set(Calendar.DATE, day);
			try {
				if (isToday(calendar)) {
					setBackground(colors.bgToday);
					setForeground(colors.fgToday);
					setBorder(BorderFactory
							.createLineBorder(colors.borderToday));
				} else {
					Date d = calendar.getTime();
					try {
						d = getFormattedDate(d);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					switch (calendar.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SATURDAY:
						setBackground(colors.bgSaturday);
						setForeground(colors.fgSaturday);
						break;
					case Calendar.SUNDAY:
						setBackground(colors.bgSunday);
						setForeground(colors.fgSunday);
						break;
					default:
						setBackground(colors.bgCell);
						setForeground(colors.fgCell);
					}

					setBorder(BorderFactory.createRaisedBevelBorder());
					setOpaque(true);// To display background color
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			calendar.set(Calendar.DATE, todayInMonth);// Resume date
		}

		/**
		 * Sets the state of selection of this cell.
		 * 
		 * @param s
		 */
		public void setSelected(boolean s) {
			if (s && selectable) {
				setBackground(colors.bgSelected);
				calendar.set(Calendar.DATE, day);
				try {
					selectedDate = getFormattedDate(calendar.getTime());
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				calendar.set(Calendar.DATE, todayInMonth);// Resume date
			} else {
				setBackground(colors.bgCell);
			}
		}

		/**
		 * The key method of <code>Cell</code>: how to draw date number on the
		 * cell.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (isHeader())
				return;// Do not render the header

			if (day > 0) {
				AttributedString dayAttrStr;
				Font font = MonthCalendarImpl.this.getFont();
				try {
					calendar.set(Calendar.DATE, day);

					String dayStr = Integer.toString(day);
					dayAttrStr = new AttributedString(dayStr);
					dayAttrStr.addAttribute(TextAttribute.FONT,
							isToday(calendar) ? font.deriveFont(Font.BOLD, font
									.getSize()
									+ SHRINK) : font);

					calendar.set(Calendar.DATE, todayInMonth);// Resume date

					Graphics2D g2 = (Graphics2D) g;
					TextLayout tl = new TextLayout(dayAttrStr.getIterator(), g2
							.getFontRenderContext());
					int width = tl.getBounds().getBounds().width;

					Dimension size = getSize();
					int x = size.width > width ? (size.width - width) / 2 : 0;

					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					// Draw in the center
					g2.drawString(dayAttrStr.getIterator(), x, (int) (tl
							.getAscent()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * @param calendar
		 * 
		 * @return true if opens highlighting switch and the date cell meets
		 *         today exactly.
		 * 
		 * @throws java.text.ParseException
		 *             actually this exception will not happen.
		 */
		private boolean isToday(Calendar calendar) throws ParseException {
			return highlighting
					&& today.equals(getFormattedDate(calendar.getTime()));
		}
	}

	/**
	 * @return Column count of the grids.
	 */
	private int getColumnCount() {
		switch (layout) {
		case HORIZONTAL:
			return calendar.getActualMaximum(Calendar.DATE);
		case VERTICAL:
			return 2;
		case TABLE:
		default:
			return weekCount;
		}
	}

	/**
	 * @return Row count of the grids.
	 */
	private int getRowCount() {
		switch (layout) {
		case HORIZONTAL:
			return 2;
		case VERTICAL:
			return calendar.getActualMaximum(Calendar.DATE);
		case TABLE:
		default:
			return (lastInMonth - firstInMonth + 1 - firstWeekRest - lastWeekRest)
					/ weekCount + 3;
		}
	}
}
