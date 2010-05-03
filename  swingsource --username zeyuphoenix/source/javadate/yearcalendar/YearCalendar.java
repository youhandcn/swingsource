package yearcalendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import monthcalendar.MetaChooser;
import monthcalendar.MonthCalendar;
import monthcalendar.MonthCalendarColor;
import monthcalendar.MonthCalendarImpl;

/**
 * A calendar bean to display a specific year. This bean is just reusge of
 * <code>MonthCalendar</code>, every month is a <code>MonthCalendar</code>
 * instance. And a special point in generating a <code>YearCalendar</code>:
 * reflection technics are used, and developer can use a <code>Class</code>
 * object of a concrete implemetation class of <code>MonthCalendar</code> as
 * constructor's parameter, then year calendar will use this style. Such a
 * process is something like changing skin. The following picture is a "label"
 * style year calendar, of cause, you can create one in "table" style by
 * writing:<br>
 * <br>
 */
public class YearCalendar extends JComponent {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	// //////////
	// Styles //
	// //////////
	/**
	 * All months placed in one line.
	 */
	public static final int HORIZONTAL = 0;

	/**
	 * In a column.
	 */
	public static final int VERTICAL = 1;

	/**
	 * In two lines, half year in each.
	 */
	public static final int SEMIYEAR = 2;

	/**
	 * In 4 lines, a quarter in each.
	 */
	public static final int QUARTER = 3;

	/**
	 * The calendar instance for this bean.
	 */
	protected Calendar calendar;

	/**
	 * The secondary calendar which can display on this bean, for example,
	 * Chinese lunar calendar or Islam calendar.
	 */
	protected Calendar calendar2;

	/**
	 * Today when component startup.
	 */
	protected Date today;

	/**
	 * An additional message for this calendar.
	 */
	protected Object message;

	/**
	 * Every month is a independent component.
	 */
	protected MonthCalendar[] monthCalendar;

	/**
	 * A concrete type of month calendar.
	 */
	protected Class<MonthCalendarImpl> mcScheme;

	/**
	 * Layout style. Available style:<br>
	 * <code>HORIZONTAL</code>, <code>VERTICAL</code>, <code>SEMIYEAR</code>,
	 * <code>QUARTER</code>
	 */
	protected int layout;

	/**
	 * Cell rendering scheme
	 */
	protected MonthCalendarColor colors;
	/**
	 * A set of festivals.
	 */
	protected Set<Date> festivals;

	/**
	 * A set of memory days.
	 */
	protected Set<Date> memories;

	/**
	 * The switch for highlighting today on the table, only available for
	 * derived classes.
	 */
	protected boolean highlighting;

	/**
	 * Message container
	 */
	protected JLabel msgLabel = null;

	/**
	 * A panel to contain all month calendars.
	 */
	private JPanel panel = null;

	/**
	 * Uses <code>GridLayout</code>
	 */
	private GridLayout gridLayout = null;

	/**
	 * Can appoint width and height of each month calendar grid.
	 */
	private int gridWidth, gridHeight;

	/**
	 * Default constructor: Uses "table" style (<code>MonthCalendarImpl</code>).
	 */
	public YearCalendar() {
		this(MonthCalendarImpl.class);
	}

	/**
	 * Constructor: Uses a specific style.
	 * 
	 * @param mcScheme
	 *            a <code>Class</code> object of one implementation of
	 *            <code>MonthCalendar</code>.
	 */
	public YearCalendar(Class<MonthCalendarImpl> mcScheme) {
		this.mcScheme = mcScheme;
		this.layout = QUARTER;
		calendar = Calendar.getInstance(getLocale());
		calendar2 = null;
		colors = MonthCalendarColor.DEFAULT;
		initialize();
	}

	/**
	 * Constructor: Creates a calendar for a specific year.
	 * 
	 * @param year
	 *            from 1 to 292278994
	 */
	public YearCalendar(int year) {
		this.mcScheme = MonthCalendarImpl.class;
		this.layout = QUARTER;
		calendar = Calendar.getInstance(getLocale());
		calendar2 = null;
		colors = MonthCalendarColor.DEFAULT;
		if (year <= calendar.getMaximum(Calendar.YEAR) && // Year should no
				// larger than
				// 292278994,
				year >= calendar.getMinimum(Calendar.YEAR))// and no less than
		// 1.
		{
			calendar.set(Calendar.YEAR, year);
		}
		initialize();
	}

	/**
	 * Constructor: Creates a calendar of a year in which a specific day
	 * locates.
	 * 
	 * @param date
	 *            a date which the year to be shown contains.
	 */
	public YearCalendar(Date date) {
		this.mcScheme = MonthCalendarImpl.class;
		this.layout = QUARTER;
		calendar = Calendar.getInstance(getLocale());
		calendar2 = null;
		colors = MonthCalendarColor.DEFAULT;
		if (date != null) {
			calendar.setTime(date);
		}
		initialize();
	}

	/**
	 * Constructor: Creates a year calendar by using given parameters.
	 * 
	 * @param date
	 *            a date which the year to be shown contains.
	 * @param calendar2
	 *            an additional calendar shown on the side of the normal date,
	 *            for example, Chinese lunar calendar.
	 * @param mcScheme
	 *            a <code>Class</code> object of one implementation of
	 *            <code>MonthCalendar</code>.
	 * @param layout
	 *            <code>HORIZONTAL</code>, <code>VERTICAL</code>,
	 *            <code>SEMIYEAR</code>, or <code>QUARTER</code>.
	 * @param colors
	 *            calendar color scheme.
	 * @param font
	 *            the font this calendar uses.
	 * 
	 * @see #calendar2
	 * @see #layout
	 * @see #colors
	 */
	public YearCalendar(Date date, Calendar calendar2,
			Class<MonthCalendarImpl> mcScheme, int layout,
			MonthCalendarColor colors, Font font) {
		this.mcScheme = mcScheme;
		this.layout = layout;
		calendar = Calendar.getInstance(getLocale());
		this.calendar2 = calendar2;
		if (date != null) {
			calendar.setTime(date);
			if (calendar2 != null) {
				calendar2.setTime(date);
			}
		}
		this.colors = colors;
		setFont(font);
		initialize();
	}

	/**
	 * This method initializes GUI.
	 */
	private void initialize() {
		gridWidth = 0;
		gridHeight = 0;
		festivals = new HashSet<Date>();// Initialize festival set.
		memories = new HashSet<Date>();// Initialize memory set.
		highlighting = true;
		setToday(calendar.getTime());

		// //////////////////
		// Initialize GUI //
		// //////////////////
		msgLabel = new JLabel();
		msgLabel.setFont(getFont());
		msgLabel.setBackground(getBackground());
		msgLabel.setForeground(getForeground());
		msgLabel.setHorizontalAlignment(JLabel.CENTER);
		msgLabel.setVerticalAlignment(JLabel.CENTER);
		this.setLayout(new BorderLayout());
		this.add(msgLabel, java.awt.BorderLayout.SOUTH);
		this.add(getPanel(), java.awt.BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEtchedBorder());
	}

	/**
	 * Gets the count of months in a year.
	 * 
	 * @param calendar
	 * 
	 * @return the count of months in a year.
	 */
	public static int getMonthCount(Calendar calendar) {
		return calendar.getMaximum(Calendar.MONTH)
				- calendar.getMinimum(Calendar.MONTH) + 1;
	}

	/**
	 * A key method in this class. Initializes every month calendar grid.
	 * 
	 * @param calendar
	 */
	private void setMonthCalendar(Calendar calendar) {
		try {
			int count = getMonthCount(calendar);
			if (monthCalendar == null || monthCalendar != null
					&& monthCalendar.length != count) {
				monthCalendar = new MonthCalendar[count];
			}
			final List<MonthCalendar> list = new ArrayList<MonthCalendar>();
			for (int i = 0; i < count; i++) {
				int currentMonth = calendar.get(Calendar.MONTH);
				calendar.set(Calendar.MONTH, i);
				Constructor<MonthCalendarImpl> con;
				// Here use reflect technology to implement dynamic object
				// generating of month calendar.
				// And here use J2SE 5.0's new feature "Varargs": variable
				// amount of parameters
				con = mcScheme.getConstructor(Date.class, Locale.class,
						Calendar.class, int.class, int.class,
						MonthCalendarColor.class, Font.class);
				monthCalendar[i] = (MonthCalendar) con.newInstance(calendar
						.getTime(), getLocale(), calendar2,
						MonthCalendar.TABLE, MonthCalendar.LONG, colors,
						getFont());

				calendar.set(Calendar.MONTH, currentMonth);// Resume month
				monthCalendar[i].setToday(calendar.getTime());
				list.add(monthCalendar[i]);
			}
			// The following codes set that
			// only one month calendar component in the selectable state at a
			// time.
			for (int i = 0; i < count; i++) {
				final MonthCalendar currentCalendar = monthCalendar[i];
				final int pos = i;
				currentCalendar.component.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						currentCalendar.setSelectable(true);
						list.remove(currentCalendar);
						for (int j = 0; j < list.size(); j++) {
							list.get(j).setSelectable(false);
						}
						list.add(pos, currentCalendar);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rearrange month calendars.
	 */
	protected void refresh() {
		if (panel != null) {
			setGrid();
			panel.removeAll();
			panel.setLayout(gridLayout);
			for (int i = 0; i < monthCalendar.length; i++) {
				if (monthCalendar[i] != null) {
					panel.add(monthCalendar[i]);
					if (gridWidth > 0 && gridHeight > 0) {
						monthCalendar[i].setPreferredSize(new Dimension(
								gridWidth, gridHeight));
					}
				}
			}
			panel.updateUI();// Refresh all month calendars
		}
	}

	/**
	 * Sets what the current day is.
	 * 
	 * @param date
	 */
	public void setToday(Date date) {
		try {
			today = MonthCalendar.getFormattedDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param year
	 *            from 1 to 292278994
	 */
	public void setYear(int year) {
		if (MonthCalendar.yearValid(calendar, year)) {
			calendar.set(Calendar.YEAR, year);
			setToday(calendar.getTime());
			setMonthCalendar(calendar);
			refresh();
		}
	}

	/**
	 * @param width
	 *            width of each month calendar grid.
	 * @param height
	 *            height of each month calendar grid.
	 */
	public void setGridSize(int width, int height) {
		if (monthCalendar != null && width > 0 && height > 0) {
			gridWidth = width;
			gridHeight = height;
			for (int i = 0; i < monthCalendar.length; i++) {
				if (monthCalendar[i] != null) {
					monthCalendar[i].setPreferredSize(new Dimension(width,
							height));
				}
			}
		}
	}

	/**
	 * @param layout
	 *            <code>HORIZONTAL</code>, <code>VERTICAL</code>,
	 *            <code>SEMIYEAR</code>, or <code>QUARTER</code>.
	 */
	public void setLayout(int layout) {
		this.layout = layout;
		refresh();
	}

	/**
	 * @see study.SmartCalendar#getCalendar()
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @see study.SmartCalendar#setCalendar(java.util.Calendar)
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		setToday(calendar.getTime());
		setMonthCalendar(calendar);
		refresh();
	}

	/**
	 * @param calendar2
	 *            the secondary calendar to set.
	 */
	public void setCalendar2(Calendar calendar2) {
		this.calendar2 = calendar2;
		refresh();
	}

	/**
	 * @see java.awt.Component#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		setCalendar(Calendar.getInstance(locale));
	}

	/**
	 * @see study.SmartCalendar#getMessage()
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @see study.SmartCalendar#setMessage(java.lang.Object)
	 */
	public void setMessage(Object msg) {
		message = msg;
		msgLabel.setFont(getFont());
		msgLabel.setBackground(getBackground());
		msgLabel.setForeground(getForeground());
		msgLabel.setText(msg.toString());
	}

	/**
	 * Creates a panel.
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanel() {
		if (panel == null) {
			gridLayout = getGridLayout();
			panel = new JPanel();
			setMonthCalendar(calendar);
			refresh();
		}
		return panel;
	}

	/**
	 * Creates <code>GridLayout</code> and sets them.
	 * 
	 * @return
	 */
	private GridLayout getGridLayout() {
		if (gridLayout == null) {
			gridLayout = new GridLayout();
			gridLayout.setHgap(5);
			gridLayout.setVgap(5);
		}
		return gridLayout;
	}

	/**
	 * Sets the grids by layout scheme.
	 */
	private void setGrid() {
		gridLayout = getGridLayout();
		int monthCount = getMonthCount(calendar);
		switch (layout) {
		case HORIZONTAL:
			gridLayout.setColumns(monthCount);
			gridLayout.setRows(1);
			break;
		case VERTICAL:
			gridLayout.setColumns(1);
			gridLayout.setRows(monthCount);
			break;
		case SEMIYEAR:
			gridLayout.setColumns(monthCount / 2);
			gridLayout.setRows(monthCount % 2 == 0 ? 2 : 3);
		case QUARTER:
		default:
			gridLayout.setColumns(monthCount / 4);
			gridLayout.setRows(monthCount % 4 == 0 ? 4 : 5);
		}
	}

	/**
	 * This bean contains a <code>YearCalendar</code> and display by choosing a
	 * specific year.
	 * 
	 * @version 0.1
	 * @author Samuel Lee
	 */
	static class YearChooser extends JComponent {
		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * To mark whether the calendar restored current date.
		 */
		private boolean restored;

		// //////////////
		// Components //
		// //////////////
		/**
  	 * 
  	 */
		protected YearCalendar yearCalendar;

		/**
		 * Container for additional components.
		 */
		private JPanel chooserPanel = null;

		/**
		 * Year chooser.
		 */
		protected MetaChooser yearChooser = null;

		/**
		 * A button clicked to restore current month.
		 */
		protected JButton restoreButton = null;

		/**
		 * Constructor: Creates a <code>YearChooser</code> for specific
		 * <code>YearCalendar</code>.
		 * 
		 * @param cal
		 */
		public YearChooser(YearCalendar cal) {
			yearCalendar = cal;
			initialize();// Call initialize() in YearChooser, not in
			// YearCalendar
		}

		/**
		 * This method initializes this.
		 */
		private void initialize() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(getChooserPanel());
			this.add(yearCalendar);
		}

		/**
		 * Restores the date to "current".
		 * 
		 * @param current
		 * @throws Exception 
		 */
		protected void restore(Date current) throws Exception {
			yearCalendar.calendar.setTime(current);
			if (yearCalendar.calendar2 != null) {
				yearCalendar.calendar2.setTime(current);
			}
			restored = true;
			yearChooser.setCurrent(yearCalendar.calendar.get(Calendar.YEAR));
			restored = false;
			yearCalendar.calendar.setTime(current);
			yearCalendar.setMonthCalendar(yearCalendar.calendar);
			yearCalendar.refresh();
		}

		/**
		 * Adds a component on the top of this panel.
		 * 
		 * @param component
		 *            the component to be added.
		 */
		protected void addComponentOnTop(JComponent component) {
			// Rearrange the layout
			this.remove(chooserPanel);
			this.remove(yearCalendar);

			this.add(component);
			this.add(chooserPanel);
			this.add(yearCalendar);
		}

		/**
		 * @return the chooser panel.
		 */
		private JPanel getChooserPanel() {
			if (chooserPanel == null) {
				chooserPanel = new JPanel();
				chooserPanel.add(getYearChooser());
				chooserPanel.add(getRestoreButton());
			}
			return chooserPanel;
		}

		/**
		 * @return the restore button.
		 */
		private JButton getRestoreButton() {
			if (restoreButton == null) {
				restoreButton = new JButton();
				restoreButton.setText("Restore...");
				restoreButton
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								try {
									restore(new Date());
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
			}
			return restoreButton;
		}

		/**
		 * @return the year chooser.
		 */
		private MetaChooser getYearChooser() {
			if (yearChooser == null) {
				final JFormattedTextField ftf = new JFormattedTextField(
						new DecimalFormat("#"));
				ftf.setValue(yearCalendar.calendar.get(Calendar.YEAR));// Use
				// auto-boxing
				ftf.getDocument().addDocumentListener(new DocumentListener() {
					public void insertUpdate(DocumentEvent e) {
						setYear(ftf);
					}

					public void removeUpdate(DocumentEvent e) {
					}

					public void changedUpdate(DocumentEvent e) {
					}
				});
				ftf.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {// When input a
						// year number
						// and press
						// enter, this
						// method will
						// be triggered
						setYear(ftf);
					}
				});
				yearChooser = new MetaChooser(ftf);
			}
			return yearChooser;
		}

		/**
		 * Called by <code>yearChooser</code>.
		 * 
		 * @param ftf
		 */
		private void setYear(JFormattedTextField ftf) {
			if (ftf.getValue() instanceof Integer && !restored) {
				setYear(ftf, (Integer) ftf.getValue());
			}
			if (ftf.getValue() instanceof Long && !restored) {
				setYear(ftf, ((Long) ftf.getValue()).intValue());
			}
		}

		/**
		 * @param ftf
		 * @param year
		 */
		private void setYear(JFormattedTextField ftf, int year) {
			if (!MonthCalendar.yearValid(yearCalendar.calendar, year)) {// year
				// must
				// start
				// from
				// A.D.
				ftf.setValue(yearCalendar.calendar.get(Calendar.YEAR));
			} else {
				yearCalendar.setYear(year);
			}
		}

		/**
		 * @see java.awt.Component#setLocale(java.util.Locale)
		 */
		public void setLocale(Locale locale) {
			yearCalendar.setLocale(locale);
		}

		/**
		 * @param width
		 * @param height
		 * 
		 * @see com.jungleford.smartcalendar.YearCalendar#setGridSize(int, int)
		 */
		public void setGridSize(int width, int height) {
			yearCalendar.setGridSize(width, height);
		}

		/**
		 * Call <code>MonthCalendar2</code>'s relevant method.
		 * 
		 * @param msg
		 * 
		 * @see com.jungleford.smartcalendar.YearCalendar#setMessage(java.lang.Object)
		 */
		public void setMessage(Object msg) {
			yearCalendar.setMessage(msg);
		}

		/**
		 * @return Returns the <code>YearCalendar</code> object in the chooser.
		 */
		public YearCalendar getYearCalendar() {
			return yearCalendar;
		}
	}
}
