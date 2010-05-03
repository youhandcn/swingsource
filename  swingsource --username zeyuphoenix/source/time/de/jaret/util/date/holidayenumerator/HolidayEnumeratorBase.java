/*
 *  File: HolidayEnumeratorBase.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.holidayenumerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Base implementation for HolidayEnumerators. This abstract class contains a complete base implementation and some date
 * calculation code (easter and other). Concrete HolidayEnumertaors have to implement the method
 * <code>fillMap(int year)</code> to fill all hollidays (and special days). To add a day the method
 * <code>addNamedDate</code> should be used. Attention has to be taken: all month values start with 0 (january = 0)
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumeratorBase.java 297 2007-03-12 21:38:00Z olk $
 */
public abstract class HolidayEnumeratorBase implements HolidayEnumerator {
    /** map containing the holidays and special days. */
    protected Map<Integer, Map<Integer, Map<Integer, NamedDate>>> _yearMap = new HashMap<Integer, Map<Integer, Map<Integer, NamedDate>>>();
    /** calendar instance for calculations. */
    protected static final Calendar CALENDAR = new GregorianCalendar();
    /** locale. */
    protected Locale _locale;
    /** region id. */
    protected String _regionId;

    
    
    
    /**
     * Calculate the holidays for a given year. This method has to be implemented by concrete implementations of
     * HolidayEnumerators.
     * 
     * @param year year to calculate the holidays for
     */
    protected abstract void fillMap(int year);

    /**
     * {@inheritDoc} Default implementation returns en empty array.
     */
    public String[] getAvailableRegionIds() {
        return new String[0];
    }

    /**
     * {@inheritDoc }toString delivers the region.
     */
    public String toString() {
        return _regionId != null ? _regionId : "NONE";
    }

    /**
     * {@inheritDoc}. Two hes are qual if loale and region match.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof HolidayEnumerator)) {
            return false;
        }
        HolidayEnumerator he = (HolidayEnumerator) obj;

        if (!_locale.equals(he.getLocale())) {
            return false;
        }
        if (_regionId == null && he.getRegionId() == null) {
            return true;
        }
        if (_regionId != null) {
            return _regionId.equals(he.getRegionId());
        }
        if (he.getRegionId() != null) {
            return he.getRegionId().equals(_regionId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _locale.hashCode() + 13 * (_regionId != null ? _regionId.hashCode() : 0);
    }

    /**
     * Do initializations.
     */
    protected void init() {
        _yearMap = new HashMap<Integer, Map<Integer, Map<Integer, NamedDate>>>();
    }

    private NamedDate getNamedDate(int year, int month, int day) {
        if (_yearMap == null) {
            init();
        }
        Map<Integer, Map<Integer, NamedDate>> yMap = _yearMap.get(year);
        if (yMap == null) {
            fillMap(year);
            yMap = _yearMap.get(year);
        }
        Map<Integer, NamedDate> mMap = yMap.get(month);
        if (mMap != null) {
            return mMap.get(day);
        }
        return null;
    }

    /**
     * Add a named date to the list of holidays.
     * 
     * @param year year
     * @param month (0= January!)
     * @param day day
     * @param isHoliday true for holiday, false = special day
     * @param name name of the date
     */
    protected void addNamedDate(int year, int month, int day, boolean isHoliday, String name) {
        Map<Integer, Map<Integer, NamedDate>> yMap = _yearMap.get(year);
        if (yMap == null) {
            yMap = new HashMap<Integer, Map<Integer, NamedDate>>();
            _yearMap.put(year, yMap);
        }
        Map<Integer, NamedDate> mMap = yMap.get(month);
        if (mMap == null) {
            mMap = new HashMap<Integer, NamedDate>();
            yMap.put(month, mMap);
        }
        Date date = getDate(year, month, day, 0);
        NamedDate namedDate = new NamedDateImpl(date, name, isHoliday);
        mMap.put(day, namedDate);
    }

    protected void addNamedDate(int year, int month, int day, int dayOffset, boolean isHoliday, String name) {
        Date date = getDate(year, month, day, dayOffset);
        EasyDate ed = new EasyDate(date);
        year = ed.year;
        month = ed.month;
        day = ed.day;
        addNamedDate(year, month, day, isHoliday, name);
    }

    protected void addNamedDate(EasyDate ed, boolean isHoliday, String name) {
        int year = ed.year;
        int month = ed.month;
        int day = ed.day;
        addNamedDate(year, month, day, isHoliday, name);
    }

    protected Date getDate(int year, int month, int day, int dayOffset) {
        CALENDAR.set(Calendar.YEAR, year);
        CALENDAR.set(Calendar.MONTH, month);
        CALENDAR.set(Calendar.DAY_OF_MONTH, day);
        CALENDAR.set(Calendar.HOUR_OF_DAY, 0);
        CALENDAR.set(Calendar.MINUTE, 0);
        CALENDAR.set(Calendar.SECOND, 0);
        CALENDAR.set(Calendar.MILLISECOND, 0);
        // add dayOffset
        CALENDAR.add(Calendar.DAY_OF_YEAR, dayOffset);
        return CALENDAR.getTime();
    }

    protected int getWeekday(Date date) {
        CALENDAR.setTime(date);
        return CALENDAR.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isHoliday(Date date) {
        EasyDate ed = new EasyDate(date);
        NamedDate d = getNamedDate(ed.year, ed.month, ed.day);
        // System.out.println("y:"+year+" m:"+month+" d:"+day+"("+d+")");
        if (d == null) {
            return false;
        }
        return d.isHoliday();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSpecialDay(Date date) {
        EasyDate ed = new EasyDate(date);
        NamedDate d = getNamedDate(ed.year, ed.month, ed.day);
        if (d == null) {
            return false;
        }
        return !d.isHoliday();
    }

    /**
     * {@inheritDoc}
     */
    public String getDayName(Date date) {
        EasyDate ed = new EasyDate(date);
        NamedDate d = getNamedDate(ed.year, ed.month, ed.day);
        if (d == null) {
            return null;
        }
        return d.getName();
    }

    /**
     * {@inheritDoc}
     */
    public Locale getLocale() {
        return _locale;
    }

    /**
     * {@inheritDoc}
     */
    public void setLocale(Locale locale) {
        _locale = locale;
        init();
    }

    /**
     * {@inheritDoc}
     */
    public String getRegionId() {
        return _regionId;
    }

    /**
     * {@inheritDoc}
     */
    public void setRegionId(String regionId) {
        _regionId = regionId;
        init();
    }

    /**
     * {@inheritDoc}
     */
    public List<NamedDate> getNamedDays(int year, boolean includeSpecialDays) {
        List<NamedDate> result = new ArrayList<NamedDate>();
        for (int month = 0; month < 12; month++) {
            result.addAll(getNamedDays(year, month, includeSpecialDays));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public List<NamedDate> getNamedDays(int year, int month, boolean includeSpecialDays) {
        List<NamedDate> result = new ArrayList<NamedDate>();
        if (_yearMap == null) {
            init();
        }
        Map<Integer, Map<Integer, NamedDate>> yMap = _yearMap.get(year);
        if (yMap == null) {
            fillMap(year);
            yMap = _yearMap.get(year);
        }
        Map<Integer, NamedDate> mMap = yMap.get(month);
        if (mMap != null) {
            for (Integer day : mMap.keySet()) {
                NamedDate d = mMap.get(day);
                if (d.isHoliday() || includeSpecialDays) {
                    result.add(d);
                }
            }
            // Collections.sort(result);
        }
        return result;
    }

    /**
     * Calculate the easter date (sunday) for a given year. This method was supplied by Marita Gierlings.
     * 
     * @author Peter Kliem
     * 
     * @param year year (valid input from 1583 to 2499)
     * @return an Easydate indicating easter sunday.
     */
    public EasyDate calcEaster(int year) {
        if (year < 1583 || year > 2499) {
            throw new IllegalArgumentException("Valid input from 1583 to 2499");
        }
        int jahr = year;
        int monat = -1;
        int tag = -1;
        int M = 0;
        int N = 0;
        if (1583 <= jahr && jahr <= 1699) {
            M = 22;
            N = 2;
        } else if (1700 <= jahr && jahr <= 1799) {
            M = 23;
            N = 3;
        } else if (1800 <= jahr && jahr <= 1899) {
            M = 23;
            N = 4;
        } else if (1900 <= jahr && jahr <= 2099) {
            M = 24;
            N = 5;
        } else if (2100 <= jahr && jahr <= 2199) {
            M = 24;
            N = 6;
        } else if (2200 <= jahr && jahr <= 2299) {
            M = 25;
            N = 0;
        } else if (2300 <= jahr && jahr <= 2499) {
            M = 26;
            N = 1;
        } else if (2400 <= jahr && jahr <= 2499) {
            M = 25;
            N = 1;
        }
        int a = jahr % 19;
        int b = jahr % 4; // Korrektur Schaltjahr
        int c = jahr % 7;
        int d = (19 * a + M) % 30;
        int e = (2 * b + 4 * c + 6 * d + N) % 7;

        if ((d + e) <= 9) {
            int easter = 22 + d + e;
            monat = 2;
            tag = easter;
        } else if ((d + e) > 9) {
            if (d + e - 9 == 26) {
                int easter = 19;
                monat = 3;
                tag = easter;
            } else if ((d + e - 9) == 25 && d == 28 && a > 10) {
                int easter = 18;
                monat = 3;
                tag = easter;
            } else {
                int easter = d + e - 9;
                monat = 3;
                tag = easter;
            }
        }
        if (monat == -1) {
            throw new RuntimeException("??? Error in easter calculation");
        }
        return new EasyDate(jahr, monat, tag);
    }

    /**
     * Calculate the nTh weekday in a month (i.e. the 3rd Monday in october)
     * 
     * @param year year
     * @param month month (starting with january = 0)
     * @param weekday weekday as constant from java.util.Calendar
     * @param nTh first=1, ... max 5
     * @return date as EasyDate or null if not found
     */
    public EasyDate nThWeekdayInMonth(int year, int month, int weekday, int nTh) {
        if (nTh > 4 || nTh < 0) {
            throw new IllegalArgumentException("nTh must be within 1..4");
        }
        EasyDate result = null;
        int i = 0;
        int n = 0;
        int daysInMonth = daysInMonth(year, month);
        while (i < daysInMonth && result == null) {
            Date d = getDate(year, month, 1, i);
            if (getWeekday(d) == weekday) {
                n++;
                if (n == nTh) {
                    result = new EasyDate(d);
                }
            }
            i++;
        }
        return result;
    }

    /**
     * Calculate the last weekday in a given month.
     * 
     * @param year year
     * @param month (January=0!)
     * @param weekday constant from java.util.Calendar
     * @return EasyDate
     */
    public EasyDate lastWeekdayInMonth(int year, int month, int weekday) {
        EasyDate result = null;
        int i = 0;
        int daysInMonth = daysInMonth(year, month);
        while (i < daysInMonth) {
            Date d = getDate(year, month, 1, i);
            if (getWeekday(d) == weekday) {
                result = new EasyDate(d);
            }
            i++;
        }
        return result;
    }

    /**
     * If the given Date is a saturday return the friday before, if it is a sunday return the monday after, in all other
     * cases return <code>null</code>. Used for correction of US holidays.
     * 
     * @param ed date to go from
     * @return shifted date or null
     */
    public EasyDate fridayOrMonday(EasyDate ed) {
        if (getWeekday(ed.date) == Calendar.SATURDAY) {
            return new EasyDate(getDate(ed.year, ed.month, ed.day, -1));
        } else if (getWeekday(ed.date) == Calendar.SUNDAY) {
            return new EasyDate(getDate(ed.year, ed.month, ed.day, 1));
        } else {
            return null;
        }
    }

    protected int daysInMonth(int year, int month) {
        Date d = getDate(year, month, 1, 0);
        CALENDAR.setTime(d);
        return CALENDAR.getMaximum(Calendar.DAY_OF_MONTH);
    }

    public EasyDate getEasyDate(int year, int month, int day) {
        return new EasyDate(year, month, day);
    }

    /**
     * Simple holder class for a split date.
     * 
     * @author Peter Kliem
     * @version $Id: HolidayEnumeratorBase.java 297 2007-03-12 21:38:00Z olk $
     */
    public class EasyDate {
        public int year;
        public int month;
        public int day;
        public Date date;

        public EasyDate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.date = getDate(year, month, day, 0);
        }

        public EasyDate(Date date) {
            CALENDAR.setTime(date);
            this.date = date;
            year = CALENDAR.get(Calendar.YEAR);
            month = CALENDAR.get(Calendar.MONTH);
            day = CALENDAR.get(Calendar.DAY_OF_MONTH);
        }

        public boolean equals(int year, int month, int day) {
            return year == this.year && month == this.month && day == this.day;
        }

    }

    /**
     * Implementation of the NamedDate for use with holiday enumerators.
     * 
     * @author Peter Kliem
     * @version $Id: HolidayEnumeratorBase.java 297 2007-03-12 21:38:00Z olk $
     */
    public class NamedDateImpl implements NamedDate, Comparable<NamedDate> {
        private String _name;
        private Date _date;
        private boolean _holiday;

        public NamedDateImpl(Date date, String name, boolean holiday) {
            if (date == null) {
                throw new RuntimeException("date may not be null");
            }
            _date = date;
            _name = name;
            _holiday = holiday;
        }

        /**
         * {@inheritDoc}
         */
        public String getName() {
            return _name;
        }

        /**
         * {@inheritDoc}
         */
        public Date getDate() {
            return _date;
        }

        /**
         * {@inheritDoc}
         */
        public boolean isHoliday() {
            return _holiday;
        }

        /**
         * {@inheritDoc}
         */
        public boolean equals(Object obj) {
            return _date.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        public int hashCode() {
            return _date.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        public String toString() {
            return "NamedDateImpl[date=" + _date + ";name=" + _name + ";isHoliday=" + _holiday + "]";
        }

        /**
         * {@inheritDoc}
         */
        public int compareTo(NamedDate date) {
            return _date.compareTo((date.getDate()));
        }
    }
}
