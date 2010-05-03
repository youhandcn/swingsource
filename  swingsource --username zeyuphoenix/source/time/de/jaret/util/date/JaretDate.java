/*
 *  File: JaretDate.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import de.jaret.util.misc.PropertyObservableBase;

/**
 * A wrapper for <code>java.util.Date</code> and <code>java.util.Calendar</code> to ease the use of date and time.
 * There are some convenience methods for quick textual output. They are located in a delegate that supports i8n. Use
 * <code>setLocale</code> to set the locale of the delegate. The delegate is static and any change will affect the
 * output of all JaretDates!
 * <p>
 * A good practice is to use an immutable type for representing dates. The JaretDate is not immutable! This is nice in a
 * lot of ways but it requires some caution when copying instances. Recommended is to use it like an immuntable type
 * most of the times (use the copy() method).
 * </p>
 * 
 * @author Peter Kliem
 * @version $Id: JaretDate.java 883 2009-10-07 21:03:00Z kliem $
 */
public class JaretDate extends PropertyObservableBase implements Comparable<JaretDate> {
    /** number of milliseconds in one hour. */
    public static final long MILLIS_PER_HOUR = 60 * 60 * 1000;
    /** number of milliseconds in one minute. */
    public static final long MILLIS_PER_MINUTE = 60 * 1000;
    /** number of milliseconds in one second. */
    public static final long MILLIS_PER_SECOND = 1000;

    /**
     * Delegate for date formatting and parsing.
     */
    private static JaretDateFormatter _jaretDateFormatter = new JaretDateFormatter();

    /** encapsulated Date. */
    private Date _date;
    /** Calendar will only be instantiated if necessary. */
    private Calendar _calendar;

    /**
     * Constructs a JaretDate with the current date and time.
     */
    public JaretDate() {
        _date = new Date();
    }

    /**
     * Constructs a JaretDate using a clone of the given Date.
     * 
     * @param date initial date to set
     */
    public JaretDate(Date date) {
        _date = (Date) date.clone();
    }

    /**
     * Constructs a JaretDate by milliseconds given in a String.
     * 
     * @param millisecondsString as String
     */
    public JaretDate(String millisecondsString) {
        long ms = Long.parseLong(millisecondsString);
        _date = new Date(ms);
    }

    /**
     * Constructs a JaretDate using the given millesecond value.
     * 
     * @param milliseconds as long
     */
    public JaretDate(long milliseconds) {
        long ms = milliseconds;
        _date = new Date(ms);
    }

    /**
     * Constructs a new JaretDate object with the same time as the given jaretDate.
     * 
     * @param jdate JaretDate to copy
     */
    public JaretDate(JaretDate jdate) {
        _date = new Date(jdate.getDate().getTime());
    }

    /**
     * Constructs a JaretDate setting it to a given date and time.
     * 
     * @param day day of month
     * @param month month of year
     * @param year year
     * @param hours hours in day
     * @param minutes minutes in hour
     * @param seconds seconds in minute
     */
    public JaretDate(int day, int month, int year, int hours, int minutes, int seconds) {
        this();
        setDateTime(day, month, year, hours, minutes, seconds);
    }

    /**
     * Retrieve the configured JaretDateFormatter.
     * 
     * @return JaretDateFormatter used for formatting and parsing
     */
    public JaretDateFormatter getJaretDateFormatter() {
        return _jaretDateFormatter;
    }

    /**
     * Sets the JaretDateFormatter to be used for parsing and formatting. The JaretDateFormatter is used for all
     * JaretDates! A Change will affect parsing and formatting of all JaretDates!
     * 
     * @param jaretDateFormatter the new Formatter to be used.
     */
    public static void setJaretDateFormatter(JaretDateFormatter jaretDateFormatter) {
        // JaretDateFormatter oldVal = _jaretDateFormatter;
        _jaretDateFormatter = jaretDateFormatter;
        // if we detect a real change, fire a prop change thus enabling listeners to redraw
        // textual representations
        /*
         * if (!oldVal.equals(jaretDateFormatter)) { firePropertyChange(); }
         */}

    /**
     * Set the locale of the JaretDateFormatter. Calling will fire a property change for the date.
     * 
     * @param locale Locale to be used
     */
    public void setLocale(Locale locale) {
        _jaretDateFormatter.setLocale(locale);
        firePropertyChange();
    }

    /**
     * Sets the date and time of the JaretDate by values.
     * 
     * @param day day of month
     * @param month month of year
     * @param year year
     * @param hours hours in day
     * @param minutes minutes in hour
     * @param seconds seconds in minute
     * @param milliseconds milliseconds in second
     */
    public void setDateTime(int day, int month, int year, int hours, int minutes, int seconds, int milliseconds) {
        Calendar cal = getCalendar();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MILLISECOND, milliseconds);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Sets the date and time of the JaretDate by values.
     * 
     * @param day day of month
     * @param month month of year
     * @param year year
     * @param hours hours in day
     * @param minutes minutes in hour
     * @param seconds seconds in minute
     */
    public void setDateTime(int day, int month, int year, int hours, int minutes, int seconds) {
        setDateTime(day, month, year, hours, minutes, seconds, 0);
    }

    /**
     * Set the time. Does not affect the date.
     * 
     * @param hours hour value
     * @param minutes minute value
     * @param seconds seconds value
     * @param milliseconds millisecond value
     * @return the modified date
     */
    public JaretDate setTime(int hours, int minutes, int seconds, int milliseconds) {
        Calendar cal = getCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MILLISECOND, milliseconds);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
        return this;
    }

    /**
     * Set the time. Does not affect the date. Will set the milliseconds to zero.
     * 
     * @param hours hour value
     * @param minutes minute value
     * @param seconds seconds value
     * @return the modified date
     */
    public JaretDate setTime(int hours, int minutes, int seconds) {
        setTime(hours, minutes, seconds, 0);
        return this;
    }

    /**
     * Set the date. Does not affect the time.
     * 
     * @param day day of month
     * @param month month
     * @param year year
     * @return this
     */
    public JaretDate setDate(int day, int month, int year) {
        Calendar cal = getCalendar();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
        return this;
    }

    /**
     * Set the date. Does not affect the time.
     * 
     * @param date JaretDate to copy the date from
     * @return this
     */
    public JaretDate setDate(JaretDate date) {
        setDate(date.getDay(), date.getMonth(), date.getYear());
        return this;
    }

    
    
    /**
     * Set the month of the year.
     * 
     * @param month month of the year (1=january
     */
    public void setMonth(int month) {
        Calendar cal = getCalendar();
        cal.set(Calendar.MONTH, month - 1);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Set the day of month.
     * 
     * @param day day of month
     */
    public void setDay(int day) {
        Calendar cal = getCalendar();
        cal.set(Calendar.DAY_OF_MONTH, day);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Set the hour in day field of the date. Does not affect any other field.
     * 
     * @param hours hour value to set
     */
    public void setHours(int hours) {
        if (hours > 23) {
            throw new IllegalArgumentException("hour must not exceedd 23");
        }
        Calendar cal = getCalendar();
        cal.set(Calendar.HOUR_OF_DAY, hours);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Set the minutes in the date. Does not affect any other field.
     * 
     * @param minutes minute value
     */
    public void setMinutes(int minutes) {
        if (minutes > 59) {
            throw new IllegalArgumentException("minutes must not exceed 59");
        }
        Calendar cal = getCalendar();
        cal.set(Calendar.MINUTE, minutes);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Set the seconds. Will set the milliseconds to zero.
     * 
     * @param seconds second value
     */
    public void setSeconds(int seconds) {
        if (seconds > 59) {
            throw new IllegalArgumentException("seconds must not exceed 59");
        }
        Calendar cal = getCalendar();
        cal.set(Calendar.SECOND, seconds);
        cal.set(Calendar.MILLISECOND, 0);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Set the milliseconds.
     * 
     * @param milliseconds millisecond value
     */
    public void setMilliseconds(int milliseconds) {
        if (milliseconds > 999) {
            throw new IllegalArgumentException("milliseconds must not exceed 999");
        }
        Calendar cal = getCalendar();
        cal.set(Calendar.MILLISECOND, milliseconds);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
    }

    /**
     * Sets the date by a parseble String. If the String fails to be parsed the result will be <code>false</code> and
     * the date will not be changed. The parsing will be delegated to the JaretDateFormatter.
     * 
     * @param text string to be parsed as date
     * @return true if parsing is successful false otherwise
     */
    public boolean setDateByText(String text) {
        Date date = _jaretDateFormatter.parseTextualDate(text);
        if (date == null) {
            return false;
        } else {
            _date = date;
            firePropertyChange();
            return true;
        }
    }

    /**
     * Supply a configured (set to the current time) Calendar for internal use.
     * 
     * @return Calendar set to the current date
     */
    protected Calendar getCalendar() {
        if (_calendar == null) {
            _calendar = Calendar.getInstance(_jaretDateFormatter.getLocale());
        }
        _calendar.setTime(_date);
        return _calendar;
    }

    /**
     * Calculate the Milliseconds from 0:00 ignoring the millisecond field.
     * 
     * @return Milliseconds from 0:00 ignoring millisecond field
     */
    public long getMillisInDay() {
        Calendar cal = getCalendar();
        long h = cal.get(Calendar.HOUR_OF_DAY);
        long m = cal.get(Calendar.MINUTE);
        long s = cal.get(Calendar.SECOND);
        return h * MILLIS_PER_HOUR + m * MILLIS_PER_MINUTE + s * MILLIS_PER_SECOND;
    }

    /**
     * Returns the encapsulated java.util.Date.
     * 
     * @return the encapsulated java.util.Date
     */
    public Date getDate() {
        return _date;
    }

    /**
     * Convenient way for a quick textual output of the timestamp.
     * 
     * @return a textual representation of the date (timestamp)
     */
    public String toDisplayString() {
        return _jaretDateFormatter.generateDisplayString(this);
    }

    /**
     * Convenient way for quick textual output of the date (day) only.
     * 
     * @return a textual representation of the date (day)
     */
    public String toDisplayStringDate() {
        return _jaretDateFormatter.generateDisplayStringDate(this);
    }

    /**
     * Convenient way for quick textual represenation (time).
     * 
     * @param seconds if true, seconds will be included in the format hh:mm:ss
     * @return a textual represenation of the time
     */
    public String toDisplayStringTime(boolean seconds) {
        return _jaretDateFormatter.toDisplayStringTime(this, seconds);
    }

    /**
     * 
     * @return toDisplayStringTime(false)
     */
    public String toDisplayStringTime() {
        return toDisplayStringTime(false);
    }

    /**
     * The standard toString behaviour is the value of the date in milliseconds.
     * 
     * @return milliseconds as String
     */
    public String toString() {
        return Long.toString(_date.getTime());
    }

    /**
     * Sets the date to the current system time.
     */
    public void setToNow() {
        _date.setTime(System.currentTimeMillis());
        firePropertyChange();
    }

    /**
     * Substracts the the given date from this date and returns the difference in minutes.
     * 
     * @param date the Date to be substracted
     * @return difference in minutes
     */
    public double diffMinutes(JaretDate date) {
        return ((double) diffMilliSeconds(date)) / MILLIS_PER_MINUTE;
    }

    /**
     * Substracts the the given date from this date and returns the difference in seconds (long).
     * 
     * @param date the Date to be substracted
     * @return difference in seconds (long)
     */
    public int diffSeconds(JaretDate date) {
        return (int) ((_date.getTime() - date.getDate().getTime()) / MILLIS_PER_SECOND);
    }

    /**
     * Substracts the the given date from this date and returns the difference in seconds.
     * 
     * @param date the Date to be substracted
     * @return difference in seconds
     */
    public long diffSecondsL(JaretDate date) {
        return (long) ((_date.getTime() - date.getDate().getTime()) / MILLIS_PER_SECOND);
    }

    /**
     * Substracts the the given date from this date and returns the difference in milliseconds.
     * 
     * @param date the Date to be substracted
     * @return difference in milliseconds
     */
    public long diffMilliSeconds(JaretDate date) {
        return _date.getTime() - date.getDate().getTime();
    }

    /**
     * Advance by a certain number of months.
     * 
     * @param months number of months
     * @return modified date
     */
    public JaretDate advanceMonths(int months) {
        Calendar cal = getCalendar();
        cal.add(Calendar.MONTH, months);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
        return this;
    }

    /**
     * Advance by a certain number of years.
     * 
     * @param years number of years to advance
     * @return modified date
     */
    public JaretDate advanceYears(int years) {
        Calendar cal = getCalendar();
        cal.add(Calendar.YEAR, years);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
        return this;
    }

    /**
     * Advance by a certain number of days.
     * 
     * @param days number of days to advance
     * @return modified date
     */
    public JaretDate advanceDays(int days) {
        Calendar cal = getCalendar();
        cal.add(Calendar.DAY_OF_YEAR, days);
        _date.setTime(cal.getTimeInMillis());
        firePropertyChange();
        return this;
    }

    /**
     * Adds a number of days to the date.
     * 
     * @param days number of days to add
     * @return the date for cascading calls
     */
    public JaretDate advanceDays(double days) {
        _date.setTime(_date.getTime() + ((long) (days * 24.0 * 60.0 * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate backDays(double days) {
        _date.setTime(_date.getTime() - ((long) (days * 24.0 * 60.0 * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate advanceHours(double hours) {
        _date.setTime(_date.getTime() + ((long) (hours * 60.0 * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate backHours(double hours) {
        _date.setTime(_date.getTime() - ((long) (hours * 60.0 * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate advanceMinutes(double minutes) {
        _date.setTime(_date.getTime() + ((long) (minutes * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate backMinutes(double minutes) {
        _date.setTime(_date.getTime() - ((long) (minutes * 60.0 * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate advanceSeconds(double seconds) {
        _date.setTime(_date.getTime() + ((long) (seconds * 1000.0)));
        firePropertyChange();
        return this;
    }

    public JaretDate advanceMillis(long millis) {
        _date.setTime(_date.getTime() + millis);
        firePropertyChange();
        return this;
    }

    public JaretDate backSeconds(double seconds) {
        _date.setTime(_date.getTime() - ((long) (seconds * 1000.0)));
        firePropertyChange();
        return this;
    }

    /**
     * {@inheritDoc} Two JaretDates are equal if the encapsulated Date is equal.
     */
    public boolean equals(Object o) {
        return _date.equals(((JaretDate) o).getDate());
    }

    /**
     * {@inheritDoc} Hashcode is the Hash of the encapsulted Date.
     */
    public int hashCode() {
        return _date.hashCode();
    }

    /**
     * Compare this to another JaretDate.
     * 
     * @param date JaretDate to compare to
     * @return 0 for an exact match, &gt;0 for this&gt;date, &lt;0 for this&lt;date
     */
    public int compareTo(JaretDate date) {
        return _date.compareTo(date._date);
    }

    /**
     * Compares only the time of day of the date with the given day.
     * 
     * @param date the date to compare to
     * @return 0 for an exact match, &gt;0 for this&gt;date, &lt;0 for this&lt;date
     */
    public long compareTimeTo(JaretDate date) {
        return getMillisInDay() - date.getMillisInDay();
    }

    /**
     * Compare the date (day) of this and the given date.
     * 
     * @param date date to compare to
     * @return 0 for exact match, &gt;0 for this&gt;date, &lt;0 for this&lt;date
     */
    public int compareDateTo(JaretDate date) {
        int result;
        result = getYear() - date.getYear();
        if (result == 0) {
            result = getDayOfYear() - date.getDayOfYear();
        }
        return result;
    }

    /**
     * Produce a new JaretDate with the time of this date.
     * 
     * @return a new instance of JaretDate
     */
    public JaretDate copy() {
        return new JaretDate(this);
    }

    /**
     * Retrive the month (1 based).
     * 
     * @return month (1 for january)
     */
    public int getMonth() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * Retrieve a string for the month of the date (full length).
     * 
     * @return string for teh month
     */
    public String getMonthString() {
        return _jaretDateFormatter.getMonthString(getMonth() - 1);
    }

    /**
     * Retrieve short string for the month.
     * 
     * @return shor (abbreviated) month name
     */
    public String getShortMonthString() {
        return _jaretDateFormatter.getShortMonthString(getMonth() - 1);
    }

    public int getWeekOfYear() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Retrieve localized name of the current day of the week.
     * 
     * @return long name of the day of week
     */
    public String getDayOfWeekString() {
        return _jaretDateFormatter.getDayOfWeekString(getDayOfWeek());
    }

    public String getShortDayOfWeekString() {
        return _jaretDateFormatter.getShortDayOfWeekString(getDayOfWeek());
    }

    /**
     * Get the day of the week as constant defined in java.util.Calendar.
     * 
     * @return day of the week.
     */
    public int getDayOfWeek() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isWeekendDay() {
        return getDayOfWeek() == Calendar.SATURDAY || getDayOfWeek() == Calendar.SUNDAY;
    }

    public int getDay() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getHours() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutes() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.MINUTE);
    }

    public int getSeconds() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.SECOND);
    }

    public int getMillis() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.MILLISECOND);
    }

    public int getDayOfYear() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Retrieve the year of the date.
     * 
     * @return year of the date
     */
    public int getYear() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.YEAR);
    }

    private void firePropertyChange() {
        firePropertyChange("date", null, this);
    }

    /**
     * Clear time fields to reduce the jaretdate to a clean date.
     * 
     */
    public void clearTime() {
        setHours(0);
        setMinutes(0);
        setSeconds(0);
        setMilliseconds(0);
    }

    /**
     * Retrieve a jaret date for the first day in a given week.
     * 
     * @param week week
     * @param year year
     * @return first day of that week
     */
    public static JaretDate getFirstDayOfAWeek(int week, int year) {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        JaretDate d = new JaretDate(cal.getTime());
        d.clearTime();
        return d;
    }
        

}
