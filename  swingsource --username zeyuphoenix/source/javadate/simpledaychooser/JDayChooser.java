package simpledaychooser;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * JDayChooser is a simple bean for choosing a day.
 */
public class JDayChooser extends JPanel implements ActionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** the button array put all number day and column day . */
	private JButton[] days = null;

	/** mark the select day. */
	private JButton selectedDay = null;
	/** mark the today. */
	private int day = -1;

	/** the no select day background color. */
	private Color oldDayBackgroundColor;
	/** the select day background color. */
	private Color selectedColor;
	/** the calendar. */
	private Calendar calendar;

	/**
	 * JDayChooser constructor.
	 */
	public JDayChooser() {
		days = new JButton[49];
		calendar = Calendar.getInstance();
		// set layout
		setLayout(new GridLayout(7, 6));

		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				int index = x + (7 * y);

				if (y == 0) {
					// Create a button that doesn't react on clicks or focus
					// changes.
					days[index] = new DecoratorButton();
				} else {
					// create day button.
					days[index] = new JButton("");
					days[index].addActionListener(this);
					days[index].addKeyListener(this);
				}

				days[index].setMargin(new Insets(0, 0, 0, 0));
				days[index].setFocusPainted(false);
				this.add(days[index]);
			}
		}

		init();
		setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		updateUI();
	}

	/**
	 * initialize the local specific names for the days of the week.
	 */
	protected void init() {
		JButton testButton = new JButton();
		oldDayBackgroundColor = testButton.getBackground();
		selectedColor = new Color(160, 160, 160);

		Date date = calendar.getTime();
		calendar = Calendar.getInstance(Locale.getDefault());
		calendar.setTime(date);

		drawDayNames();
		drawDays();
	}

	/**
	 * Draws the day names of the day columns.
	 */
	private void drawDayNames() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale
				.getDefault());
		String[] dayNames = dateFormatSymbols.getShortWeekdays();

		int day = firstDayOfWeek;

		for (int i = 0; i < 7; i++) {

			days[i].setText(dayNames[day]);

			if (day == 1) {
				days[i].setForeground(new Color(164, 0, 0));
			} else {
				days[i].setForeground(new Color(0, 90, 164));
			}

			if (day < 7) {
				day++;
			} else {
				day -= 6;
			}
		}
	}

	/**
	 * Hides and shows the day buttons.
	 */
	private void drawDays() {
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tmpCalendar.set(Calendar.MINUTE, 0);
		tmpCalendar.set(Calendar.SECOND, 0);
		tmpCalendar.set(Calendar.MILLISECOND, 0);

		int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);

		int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;

		if (firstDay < 0) {
			firstDay += 7;
		}

		int i;

		for (i = 0; i < firstDay; i++) {
			days[i + 7].setVisible(false);
			days[i + 7].setText("");
		}

		tmpCalendar.add(Calendar.MONTH, 1);

		Date firstDayInNextMonth = tmpCalendar.getTime();
		tmpCalendar.add(Calendar.MONTH, -1);

		Date day = tmpCalendar.getTime();
		int n = 0;
		Color foregroundColor = getForeground();

		while (day.before(firstDayInNextMonth)) {
			days[i + n + 7].setText(Integer.toString(n + 1));
			days[i + n + 7].setVisible(true);

			if ((tmpCalendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance(
					Locale.getDefault()).get(Calendar.DAY_OF_YEAR))
					&& (tmpCalendar.get(Calendar.YEAR) == Calendar.getInstance(
							Locale.getDefault()).get(Calendar.YEAR))) {
				days[i + n + 7].setForeground(new Color(164, 0, 0));
			} else {
				days[i + n + 7].setForeground(foregroundColor);
			}

			if ((n + 1) == this.day) {
				days[i + n + 7].setBackground(selectedColor);
				selectedDay = days[i + n + 7];
			} else {
				days[i + n + 7].setBackground(oldDayBackgroundColor);
			}

			n++;
			tmpCalendar.add(Calendar.DATE, 1);
			day = tmpCalendar.getTime();
		}

		for (int k = n + i + 7; k < 49; k++) {
			days[k].setVisible(false);
			days[k].setText("");
		}

	}

	/**
	 * Sets the day. This is a bound property.
	 * 
	 * @param d
	 *            the day
	 * 
	 * @see #getDay
	 */
	public void setDay(int d) {
		if (d < 1) {
			d = 1;
		}
		Calendar tmpCalendar = (Calendar) calendar.clone();
		tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
		tmpCalendar.add(Calendar.MONTH, 1);
		tmpCalendar.add(Calendar.DATE, -1);

		int maxDaysInMonth = tmpCalendar.get(Calendar.DATE);

		if (d > maxDaysInMonth) {
			d = maxDaysInMonth;
		}

		int oldDay = day;
		day = d;

		if (selectedDay != null) {
			selectedDay.setBackground(oldDayBackgroundColor);
			selectedDay.repaint();
		}

		for (int i = 7; i < 49; i++) {
			if (days[i].getText().equals(Integer.toString(day))) {
				selectedDay = days[i];
				selectedDay.setBackground(selectedColor);
				break;
			}
		}

		firePropertyChange("day", oldDay, day);
	}

	/**
	 * Returns the selected day.
	 * 
	 * @return the day value
	 * 
	 * @see #setDay
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets a specific month. This is needed for correct graphical
	 * representation of the days.
	 * 
	 * @param month
	 *            the new month
	 */
	public void setMonth(int month) {
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.MONTH, month);
		if (maxDays == day) {
			day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}

		setDay(day);

		drawDays();
	}

	/**
	 * Sets a specific year. This is needed for correct graphical representation
	 * of the days.
	 * 
	 * @param year
	 *            the new year
	 */
	public void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
		drawDays();
	}

	/**
	 * Sets a specific calendar. This is needed for correct graphical
	 * representation of the days.
	 * 
	 * @param calendar
	 *            the new calendar
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		drawDays();
	}

	/**
	 * JDayChooser is the ActionListener for all day buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String buttonText = button.getText();
		int day = new Integer(buttonText).intValue();
		setDay(day);
	}

	/**
	 * JDayChooser is the KeyListener for all day buttons.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int offset = (e.getKeyCode() == KeyEvent.VK_UP) ? (-7) : ((e
				.getKeyCode() == KeyEvent.VK_DOWN) ? (+7)
				: ((e.getKeyCode() == KeyEvent.VK_LEFT) ? (-1) : ((e
						.getKeyCode() == KeyEvent.VK_RIGHT) ? (+1) : 0)));

		int newDay = getDay() + offset;

		if ((newDay >= 1)
				&& (newDay <= calendar.getMaximum(Calendar.DAY_OF_MONTH))) {
			for (int i = 7; i < 49; i++) {
				if (days[i].getText().equals(Integer.toString(newDay))) {
					days[i].requestFocus();
					break;
				}
			}
			setDay(newDay);

		}
	}

	/**
	 * Does nothing.
	 * 
	 * @param e
	 *            the KeyEvent
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Does nothing.
	 * 
	 * @param e
	 *            the KeyEvent
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * create the button no focus and no action.
	 */
	private class DecoratorButton extends JButton {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public DecoratorButton() {
			setBackground(new Color(210, 228, 238));
		}

		@Override
		public void addMouseListener(MouseListener l) {
		}

		@Override
		public boolean isFocusable() {
			return false;
		}
	};

	/**
	 */
	public static void main(String[] s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				JFrame frame = new JFrame("JDayChooser");
				frame.getContentPane().add(new JDayChooser());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
}
