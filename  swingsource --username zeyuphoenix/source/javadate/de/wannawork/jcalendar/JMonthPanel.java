package de.wannawork.jcalendar;
/*
 * Copyright (c) 2003, Bodo Tasche (http://www.wannawork.de)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this 
 *       list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this 
 *       list of conditions and the following disclaimer in the documentation and/or other 
 *       materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This Panel shows a Month
 * 
 * @author Bodo Tasche, Igor Kriznar
 */
public class JMonthPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel table;
	/**
	 * Creates a JMonthPanel using the current Date and 
	 * current Local settings.
	 */
	public JMonthPanel() {
		init(Calendar.getInstance(), Locale.getDefault());
	}

	/**
	 * Creates a JMonthPanel using the cal-Date and 
	 * current Locale Settings. It doesn't use
	 * the Locale in the Calendar-Object !
	 * 
	 * @param cal Calendar to use
	 */
	public JMonthPanel(Calendar cal) {
		init(cal, Locale.getDefault());
	}

	/**
	 * Creates a JMonthPanel using the current Date
	 * and the given Locale Settings.
	 * 
	 * @param locale Locale to use
	 */
	public JMonthPanel(Locale locale) {
		init(Calendar.getInstance(locale), locale);
	}

	/**
	 * Creates a JMonthPanel using the given Date and
	 * Locale 
	 * 
	 * @param cal Calendar to use
	 * @param locale Locale to use
	 */
	public JMonthPanel(Calendar cal, Locale locale) {
		init(cal, locale);
	}

	/**
	 * Initialize the JMonthPanel
	 * 
	 * @param cal Calendar to use
	 * @param loc Locale to use
	 */
	private void init(Calendar cal, Locale loc) {
		_cal = Calendar.getInstance(loc);
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		_locale = loc;
		createGUI();
	}

		/**
	 * Creates the GUI
	 */
	private void createGUI() {
		setLayout(new BorderLayout());
		add(createHeader(), BorderLayout.NORTH);
		add(createTable(), BorderLayout.CENTER);
		refreshGUI();
	}

	/**
	 * Creates a JPanel containing the Header
	 * @return Header
	 */
	private JPanel createHeader() {
		JPanel header = new JPanel();

		header.setLayout(new GridLayout(1, 7, 1, 1));
		header.setBackground(HEADER_BACKGROUND_COLOR);

		SimpleDateFormat format = new SimpleDateFormat("E", _locale);
		Calendar cal = (Calendar) _cal.clone();

		char[] letters = new char[7];

		for (int i = 0; i < 7; i++) {
			letters[cal.get(Calendar.DAY_OF_WEEK) - 1] = format.format(cal.getTime()).charAt(0);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		}

		int pos = cal.getFirstDayOfWeek() - 1;

		for (int i = 0; i < 7; i++) {
			JLabel empty = new JLabel(String.valueOf(letters[pos]));
			
			pos++;
			if (pos > 6) {
				pos = 0;
			}

			empty.setHorizontalAlignment(JLabel.RIGHT);
			empty.setForeground(HEADER_FONT_COLOR);
			header.add(empty);
		}
		return header;
	}

	/**
	 * Creates a JPanel containing the Day-Table
	 * @return Day Table
	 */
	private JPanel createTable() {
		_days = new ArrayList<DayLabel>();

		table = new JPanel();
//		table.setBackground(BACKGROUND_COLOR);

		table.setLayout(new GridLayout(6, 7, 1, 1));

		return table;
	}

	
	private void refreshGUI() {

		table.removeAll();
		_days.clear();
		
		int position = 0;

		Calendar today = Calendar.getInstance();

		Calendar cal = (Calendar) _cal.clone();

		cal.set(Calendar.DAY_OF_MONTH, 1);

		int month = cal.get(Calendar.MONTH);

		int firstDay = cal.get(Calendar.DAY_OF_WEEK);

		if (firstDay == 0)
			firstDay--;
		else {
			firstDay -= cal.getFirstDayOfWeek();
		}

		if (firstDay < 0) {
			firstDay += 7;
		}

		while (position < firstDay) {
			JLabel empty = new JLabel();
			table.add(empty);
			position++;
		}

		int curDay = _cal.get(Calendar.DAY_OF_MONTH);

		while ((position < 42) && (cal.get(Calendar.MONTH) == month)) {
			
			boolean todayB = false;
			
			if ((cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) &&
			    (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
				(cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) {
				todayB = true;
			}
			
			DayLabel day = new DayLabel(cal.get(Calendar.DAY_OF_MONTH), todayB, this);
			table.add(day);

			_days.add(day);

			if (!unset && curDay == cal.get(Calendar.DAY_OF_MONTH)) {
				day.setSelected(true);
			}

			position++;
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		}

		while (position < 42) {
			JLabel empty = new JLabel();
			table.add(empty);
			position++;
		}

	}
	/**
	 * Sets the current Date
	 * 
	 * @param date Date to show
	 */
	public void setDate(Date date) {
		if (date==null) {
			setCalendar(null);
		} else {
			_cal.setTime(date);
			setCalendar(_cal);
		}
	}
	
	/**
	 * Returns the current selected Date
	 * 
	 * @return current selected Date
	 */
	public Date getDate() {
		if (unset) {
			return null;
		}
		return getCalendar().getTime();
	}
	
	/**
	 * Sets the current Date using a Calendar-Object
	 * 
	 * @param cal Calendar to show
	 */
	public void setCalendar(Calendar cal) {
		
		if (cal==null) {
			unset=true;
			cal= Calendar.getInstance(_locale);
		} else {
			unset=false;
		}
		
		_cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		_cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		_cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));

		refreshGUI();
		updateUI();
		
		DayLabel dayLabel = _days.get(cal.get(Calendar.DAY_OF_MONTH) - 1);
		dayLabel.grabFocus();
		
		//setBackground(BACKGROUND_COLOR);
		
		fireChangeEvent();
	}

	/**
	 * Sets the Focus to the current selected Day
	 */
	@Override
	public void grabFocus() {
		super.grabFocus();
		DayLabel dayLabel = _days.get(_cal.get(Calendar.DAY_OF_MONTH) - 1);
		dayLabel.grabFocus();
	}

	/**
	 * Returns the current Date using a Calendar-Object
	 * 
	 * @return current Date
	 */
	public Calendar getCalendar() {
		return _cal;
	}

	/**
	 * Sets the selected Day of Month
	 * 
	 * @param day selected Day
	 */
	public void setSelectedDayOfMonth(int day) {
		
		if ( _enabled && (day > 0) && (day <= _days.size())) {
			int oldday = _cal.get(Calendar.DAY_OF_MONTH);

			DayLabel dayLabel = _days.get(oldday - 1);
			dayLabel.setSelected(false);

			_cal.set(Calendar.DAY_OF_MONTH, day);

			dayLabel = _days.get(day - 1);
			dayLabel.setSelected(true);
			unset=false;
			updateUI();
			
			fireChangeEvent();
		}

	}

	/**
	 * Gets the selected Day of the Month
	 * 
	 * @return selected Day
	 */
	public int getSelectedDayOfMonth() {
		return _cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Adds a Changelistener to this JCalendarComboBox.
	 * 
	 * It will be called everytime the ComboBox is closed
	 * and the Date was changed 
	 * 
	 * @param listener ChangeListener
	 */
	public void addChangeListener(ChangeListener listener) {
		_changeListener.add(listener);
	}

	/**
	 * Removes a ChangeListener from this JCalendarComboBox
	 * 
	 * @param listener listener to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		_changeListener.remove(listener);
	}

	/**
	 * Gets all ChangeListeners
	 * @return all ChangeListeners
	 */
	public ChangeListener[] getChangeListener() {
		return (ChangeListener[]) _changeListener.toArray();
	}

	/**
	 * Fires the ChangeEvent
	 */
	protected void fireChangeEvent() {
		if (!_fireingChangeEvent) {
			_fireingChangeEvent = true;
			ChangeEvent event = new ChangeEvent(this);

			for (int i = 0; i < _changeListener.size(); i++) {
				_changeListener.get(i).stateChanged(event);
			}

			_fireingChangeEvent = false;
		}

	}
	
	/**
	 * If <code>true</code> mean that no date is set and component displays unselected calendar.
	 * @return <code>true</code> if no date is set and component displays unselected calendar
	 */
	public boolean isUnset() {
		return unset;
	}

	/**
	 * Enables/Disables the Panel
	 * @param enabled Enabled ?
	 */
	@Override
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	/**
	 * Is the Panel enabled ?
	 * @return enabled ?
	 */
	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	/**
	 * Component enabled ?
	 */
	private boolean _enabled = true;
	
	/**
	 * date not set
	 */
	private boolean unset=false;

	/**
	 * The current Calendar
	 */
	private Calendar _cal;
	/**
	 * The locale to use
	 */
	private Locale _locale;
	/**
	 * All Day-Buttons in this MonthPanel
	 */
	private ArrayList<DayLabel> _days;

	/**
	 * The list of ChangeListeners
	 */
	private ArrayList<ChangeListener> _changeListener = new ArrayList<ChangeListener>();
	/**
	 * Currently firing an ChangeEvent?
	 */
	private boolean _fireingChangeEvent = false;

	/**
	 * Color for the Background
	 */
	public static final Color BACKGROUND_COLOR = UIManager.getColor("TextField.background");
	/**
	 * Font Color for the not-selected Day
	 */
	public static final Color FONT_COLOR = UIManager.getColor("TextField.foreground");
	/**
	 * Background Color for the selected Day
	 */
	public static final Color SELECTED_BACKGROUND_COLOR = UIManager.getColor("TextField.selectionBackground");
	/**
	 * Font Color for the selected Day
	 */
	public static final Color SELECTED_FONT_COLOR = UIManager.getColor("TextField.selectionForeground");
	/**
	 * Background Color for the Header
	 */
	public static final Color HEADER_BACKGROUND_COLOR = UIManager.getColor("TextField.inactiveForeground");
	/**
	 * Font Color for the Header
	 */
	public static final Color HEADER_FONT_COLOR = UIManager.getColor("TextField.inactiveBackground");

}