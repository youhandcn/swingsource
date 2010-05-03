package monthcalendar;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A common interface for month calendar bean.<br>
 */
public abstract class MonthCalendar extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// //////////
	// Styles //
	// //////////
	/**
	 * Layout style is horizontal.
	 */
	public static final int HORIZONTAL = 0;

	/**
	 * Layout style is vertical.
	 */
	public static final int VERTICAL = 1;

	/**
	 * Layout style is table, Monday as the first day of the week.
	 */
	public static final int TABLE = 2;

	/**
	 * Week style is short format: "E".
	 */
	public static final int SHORT = 0;

	/**
	 * Week style is long format: "EEEE".
	 */
	public static final int LONG = 1;

	/**
	 * A parser to parse <code>java.sql.Date</code> type.
	 */
	private static final DateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * The primary calendar instance for this bean, usually Gregorian calendar.
	 */
	protected Calendar calendar;
	
	/**
	 * Layout style. Available style:<br>
	 * <code>HORIZONTAL</code>, <code>VERTICAL</code>, <code>TABLE</code>.
	 */
	protected int layout;

	/**
	 * Week display format. Available format:<br>
	 * <code>SHORT</code>, <code>LONG</code>.
	 */
	protected int weekFormat;

	/**
	 * Cell rendering scheme.
	 */
	protected MonthCalendarColor colors;

	/**
	 * The switch for highlighting today on the table, only available for
	 * derived classes.
	 */
	protected boolean highlighting;

	/**
	 * Selected date, for single date chooser.
	 */
	protected Date selectedDate;

	/**
	 * Selected dates, for multiple date chooser.
	 */
	protected Set<Date> selectedDates;

	// //////////////
	// Components //
	// //////////////
	/**
	 * To display a month calendar cells.
	 */
	public Component component = null;

	// //////////////////
	// Private fields //
	// //////////////////
	/**
	 * Today when component startup.
	 */
	protected Date today;

	/**
	 * How many days in a week.
	 */
	protected int weekCount;

	/**
	 * First day of a week in current region, some countries use SUNDAY and
	 * others use MONDAY.
	 */
	protected int firstDayOfWeek;

	/**
	 * Date of the first day in current month, i.e. 1
	 */
	protected int firstInMonth;

	/**
	 * Date of the first day in current month, maybe 30, 31, 28 or 29.
	 */
	protected int lastInMonth;

	/**
	 * Position in a week of the first day in current month.
	 */
	protected int firstInWeek;

	/**
	 * Position in a week of the last day in current month.
	 */
	private int lastInWeek;

	/**
	 * How many days in the first week.
	 */
	protected int firstWeekRest;

	/**
	 * How many days in the last week.
	 */
	protected int lastWeekRest;

	/**
	 * "E" for short and "EEEE" for long.
	 */
	protected DateFormat weekPattern;

	/**
	 * If the table cell can be selected or not.
	 */
	protected boolean selectable;

	/**
	 * If the component is selected or not.
	 */
	protected boolean selected;

	/**
	 * Constructor: Initializes month calendar by given year and month.
	 * 
	 * @param year
	 *            from 1 to 292278994 (the maximum year).
	 * @param month
	 *            from <code>Calendar.JANUARY</code> to
	 *            <code>Calendar.DECEMBER</code>.
	 */
	public MonthCalendar(int year, int month) {
		this.layout = TABLE;
		this.weekFormat = SHORT;
		calendar = Calendar.getInstance();
		colors = MonthCalendarColor.DEFAULT;
		if (year <= calendar.getMaximum(Calendar.YEAR) &&
		// Year should no
				// larger than
		// 292278994,
				year >= calendar.getMinimum(Calendar.YEAR))
		// and no less than
		// 1.
		{
			calendar.set(Calendar.YEAR, year);
		}
		if (month <= calendar.getMaximum(Calendar.MONTH) &&
		// Month should no
				// larger than 11,
				month >= calendar.getMinimum(Calendar.MONTH))
		// and no less than
		// 0.
		{
			calendar.set(Calendar.MONTH, month);
		}
		initialize();
	}

	/**
	 * Every derived class should define a constructor by using these
	 * parameters, because <code>YearCalendar</code> has used reflect
	 * technology, and only accept constructor like this style.
	 * 
	 * @param date
	 *            a date which the month to be shown contains.
	 * @param locale
	 *            the locale this calendar uses.
	 * @param calendar2
	 *            an additional calendar shown on the side of the normal date,
	 *            for example, Chinese lunar calendar.
	 * @param layout
	 *            layout of this calendar. Three kinds of layout have been
	 *            defined: <code>HORIZONTAL</code>, <code>VERTICAL</code>, and
	 *            <code>TABLE</code>.
	 * @param weekFormat
	 *            "SHORT" or "LONG".
	 * @param colors
	 *            month calendar color scheme.
	 * @param font
	 *            the font this calendar uses.
	 */
	public MonthCalendar(Date date, Locale locale,
			int layout, int weekFormat, MonthCalendarColor colors, Font font) {
		this.layout = layout;
		this.weekFormat = weekFormat;
		if (locale != null) {
			calendar = Calendar.getInstance(locale);
			super.setLocale(locale);
		} else {
			calendar = Calendar.getInstance();
		}
		if (date != null) {
			calendar.setTime(date);
		}
		this.colors = colors == null ? MonthCalendarColor.DEFAULT : colors;
		if (font != null) {
			setFont(font);
		}
		initialize();
	}

	/**
	 * This method initializes GUI.
	 */
	private void initialize() {
		selectable = true;
		selected = false;
		highlighting = true;
		selectedDate = calendar.getTime();// default selected date is today
		selectedDates = new HashSet<Date>();
		setToday(calendar.getTime());// Here setToday() will use dynamic binding
		// with actual implementation,
		// and setToday() has already called initDayParams() first.

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getComponent());
	}

	/**
	 * Initialize some private members about calendar.
	 * 
	 * @param calendar
	 */
	private void initDayParams(Calendar calendar) {
		weekCount = calendar.getMaximum(Calendar.DAY_OF_WEEK)
				- calendar.getMinimum(Calendar.DAY_OF_WEEK) + 1;
		firstDayOfWeek = calendar.getFirstDayOfWeek();
		firstInMonth = calendar.getActualMinimum(Calendar.DATE);
		lastInMonth = calendar.getActualMaximum(Calendar.DATE);
		int today = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, firstInMonth);
		firstInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DATE, lastInMonth);
		lastInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.DATE, today);// Resume the date
		firstWeekRest = firstInWeek - firstDayOfWeek >= 0 ? weekCount
				- (firstInWeek - firstDayOfWeek) : firstDayOfWeek - firstInWeek;
		lastWeekRest = lastInWeek - firstDayOfWeek >= 0 ? lastInWeek
				- firstDayOfWeek + 1 : weekCount
				- (firstDayOfWeek - lastInWeek) + 1;
	}

	/**
	 * Refresh the table, only called by derived classes.
	 */
	protected void refresh() {
		initDayParams(calendar);
		getComponent();// Makes sure that the component has been initialized.
		setComponent();
	}

	/**
	 * Convert a <code>Date</code> value to another <code>Date</code> value
	 * which has the same date but ignore the precise time, i.e. the format as
	 * "yyyy-MM-dd"
	 * 
	 * @param d
	 *            the <code>Date</code> object to convert.
	 * 
	 * @return the <code>Date</code> object ignoring precise time.
	 * 
	 * @throws java.text.ParseException
	 *             actually this exception will not happen.
	 */
	public static Date getFormattedDate(Date d) throws ParseException {
		return DATEFORMAT.parse(new java.sql.Date(d.getTime()).toString());
	}

	/**
	 * Check the year value in the correct range.
	 * 
	 * @param calendar
	 * @param year
	 * 
	 * @return true if the year is valid.
	 */
	public static boolean yearValid(Calendar calendar, int year) {
		return year >= calendar.getMinimum(Calendar.YEAR)
				&& year <= calendar.getMaximum(Calendar.YEAR);
	}

	/**
	 * This method initializes the <b>core calendar component</b>, which will
	 * draw concrete calendar appearance. This method should be implemented by
	 * derived classes. The <code>Component</code> can be any appropriate Swing
	 * component, and depends on developer's design.
	 * 
	 * @return the component which stands for calendar.
	 */
	protected abstract Component getComponent();

	/**
	 * This method is the most important implementation in
	 * <code>MonthCalendar</code>. <br>
	 * The main purpose is to define a scheme for how to deploying and drawing
	 * cells or units in the concrete calendar.<br>
	 * Before calling this method, <code>getComponent()</code> operation must be
	 * called, to create a calendar instance. <br>
	 * In fact, developer do not need to care about this process, because a
	 * private method <code>initialize()</code> has done the entire job. <br>
	 * Another function of this method is refresh the calendar appearance when
	 * some events are triggered, for example, the month changes.
	 */
	protected abstract void setComponent();

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
		if (calendar != null) {
			this.calendar = calendar;
			refresh();
		}
	}

	/**
	 */
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		setCalendar(Calendar.getInstance(locale));
	}

	/**
	 * @param colors
	 *            the color scheme to set.
	 */
	public void setColors(MonthCalendarColor colors) {
		this.colors = colors == null ? MonthCalendarColor.DEFAULT : colors;
		setComponent();
	}

	/**
	 * @param layout
	 *            the layout to set.
	 */
	public void setLayout(int layout) {
		this.layout = layout;
		setComponent();
	}

	/**
	 * @param weekFormat
	 *            the week header format to set, "SHORT" or "LONG".
	 */
	public void setWeekFormat(int weekFormat) {
		this.weekFormat = weekFormat;
		setComponent();
	}

	/**
	 * @param month
	 *            from <code>Calendar.JANUARY</code> to
	 *            <code>Calendar.DECEMBER</code>
	 */
	public void setMonth(int month) {
		if (month <= calendar.getMaximum(Calendar.MONTH) && // Month should no
				// larger than 11,
				month >= calendar.getMinimum(Calendar.MONTH))// and no less than
		// 0.
		{
			calendar.set(Calendar.MONTH, month);
		}
		refresh();
	}

	/**
	 * Sets what the current day is.
	 * 
	 * @param date
	 */
	public void setToday(Date date) {
		if (date != null) {
			try {
				today = getFormattedDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		refresh();
	}

	/**
	 * @param selectable
	 *            true if this calendar can do selection.
	 */
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * @return true if this calendar is selected.
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @return the selected date.
	 */
	public Date getSelectedDate() {
		return selectedDate;
	}

	/**
	 * @return the selected dates.
	 */
	public Set<Date> getSelectedDates() {
		return selectedDates;
	}

	/**
	 * Opens or closes the highlighting switch, if true, today, festivals and
	 * memory days will show, otherwise they will hide.
	 * 
	 * @param highlighting
	 *            true if turn on highlighting switch.
	 */
	public void setHighlighting(boolean highlighting) {
		this.highlighting = highlighting;
		repaint();
	}

	/**
	 * This bean contains three components:
	 * <ul>
	 * <li>A <code>MonthCalendar</code></li>
	 * <li>A year chooser</li>
	 * <li>A month chooser</li>
	 * </ul>
	 * The last two components are instances of <code>MetaChooser</code>. User
	 * can choose a day by given year and month.
	 */
	static class DateChooser extends JComponent {
		/**
		 * Default serial version ID.
		 */
		private static final long serialVersionUID = 1L;

		protected boolean highlighting;

		/**
		 * To mark whether the calendar restored current date
		 */
		private boolean restored;

		/**
  	 * 
  	 */
		protected MonthCalendar monthCalendar;

		/**
		 * Container for additional components
		 */
		private JPanel chooserPanel = null;

		/**
		 * Year chooser
		 */
		protected MetaChooser yearChooser = null;

		/**
		 * Month chooser
		 */
		protected MetaChooser monthChooser = null;

		/**
		 * A button clicked to restore current month
		 */
		protected JButton restoreButton = null;

		/**
		 * Constructor: Creates a <code>DateChooser</code> for specific
		 * <code>MonthCalendar</code>.
		 * 
		 * @param cal
		 * @param todayHighlight
		 */
		public DateChooser(MonthCalendar cal, boolean todayHighlight) {
			highlighting = todayHighlight;
			monthCalendar = cal;
			monthCalendar.setHighlighting(todayHighlight);
			initialize();// Call initialize() in DateChooser, not in
			// MonthCalendar
		}

		/**
		 * This method initializes this.
		 */
		private void initialize() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(getChooserPanel());
			this.add(monthCalendar);
		}

		/**
		 * Restores the date to "current".
		 * 
		 * @param current
		 * @throws Exception
		 */
		protected void restore(Date current) throws Exception {
			monthCalendar.calendar.setTime(current);
			if (this.highlighting) {
				monthCalendar.highlighting = true;
			}
			monthCalendar.selectedDate = monthCalendar.calendar.getTime();
			restored = true;
			yearChooser.setCurrent(monthCalendar.calendar.get(Calendar.YEAR));
			monthChooser.setCurrent(monthCalendar.calendar.get(Calendar.MONTH));
			restored = false;
			monthCalendar.refresh();
		}

		/**
		 * Add a component on the top of this panel. This method should be
		 * called only once.
		 * 
		 * @param component
		 *            the component to add.
		 */
		protected void addComponentOnTop(JComponent component) {
			// Rearrange the layout
			this.remove(chooserPanel);
			this.remove(monthCalendar);

			this.add(component);
			this.add(chooserPanel);
			this.add(monthCalendar);
		}

		/**
		 * @return the chooser panel.
		 */
		private JPanel getChooserPanel() {
			if (chooserPanel == null) {
				chooserPanel = new JPanel();
				chooserPanel.add(getYearChooser());
				chooserPanel.add(getMonthChooser());
				chooserPanel.add(getRestoreButton());
			}
			return chooserPanel;
		}

		/**
		 * @return the month chooser.
		 */
		private MetaChooser getMonthChooser() {
			if (monthChooser == null) {
				createMonthChooser(getLocale());
			}
			return monthChooser;
		}

		/**
		 * Creates a <code>MetaChooser</code>
		 * 
		 * @param locale
		 */
		private void createMonthChooser(Locale locale) {
			final JComboBox cb = new JComboBox(new DateFormatSymbols(locale)
					.getMonths());
			cb.setSelectedItem(cb.getItemAt(monthCalendar.calendar
					.get(Calendar.MONTH)));
			cb.removeItemAt(cb.getItemCount() - 1);// Because DateFormatSymbols
			// contains 13 month items,
			// puzzled :(
			cb.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (!restored) {
						monthCalendar.calendar.set(Calendar.MONTH, cb
								.getSelectedIndex());
						monthCalendar.refresh();
					}
				}
			});
			monthChooser = new MetaChooser(cb);
		}

		/**
		 * @return the restore button.
		 */
		private JButton getRestoreButton() {
			if (restoreButton == null) {
				restoreButton = new JButton();
				restoreButton.setText("½ñÌì");
				restoreButton
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								try {
									restore(new Date());
								} catch (Exception e1) {
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
				ftf.setValue(monthCalendar.calendar.get(Calendar.YEAR));// Use
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
		 * Sets the year value for the text field and the primary calendar of
		 * <code>monthCalendar</code>.
		 * 
		 * @param ftf
		 * @param year
		 */
		private void setYear(JFormattedTextField ftf, int year) {
			if (!yearValid(monthCalendar.calendar, year)) {// year
				// must
				// start
				// from
				// A.D.
				ftf.setValue(monthCalendar.calendar.get(Calendar.YEAR));
			} else {
				monthCalendar.calendar.set(Calendar.YEAR, year);
				monthCalendar.refresh();
			}
		}

		/**
		 * Sets the locale of <code>monthCalendar</code>.
		 * 
		 */
		public void setLocale(Locale locale) {
			super.setLocale(locale);
			monthCalendar.setLocale(locale);
			chooserPanel.remove(monthChooser);
			chooserPanel.remove(restoreButton);
			createMonthChooser(locale);
			chooserPanel.add(monthChooser);
			chooserPanel.add(restoreButton);
		}

		/**
		 * @return the selected date.
		 * 
		 * @see com.jungleford.smartcalendar.MonthCalendar#getSelectedDate()
		 */
		public Date getSelectedDate() {
			return monthCalendar.getSelectedDate();
		}

		/**
		 * @return the selected dates.
		 * 
		 * @see com.jungleford.smartcalendar.MonthCalendar#getSelectedDates()
		 */
		public Set<Date> getSelectedDates() {
			return monthCalendar.getSelectedDates();
		}

		/**
		 * @return the <code>MonthCalendar</code> object in the chooser.
		 */
		public MonthCalendar getMonthCalendar() {
			return monthCalendar;
		}
	}
}
