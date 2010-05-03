/*
 *  File: WeekIterator.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.iterator;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.jaret.util.date.JaretDate;

/**
 * Implementation of the DateIterator iterating over weeks.
 * 
 * @author kliem
 * @version $Id: WeekIterator.java 828 2009-02-08 13:58:21Z kliem $
 */
public class WeekIterator extends AbstractDateIterator implements DateIterator {
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return Integer.toString(date.getWeekOfYear()) + ".";
            } else if (format.equals(Format.MEDIUM)) {
                return Integer.toString(date.getWeekOfYear()) + ".(" + date.toDisplayStringDate() + ")";
            } else {
                JaretDate nextDate = previewNextDate();
                return Integer.toString(date.getWeekOfYear()) + ". (" + date.toDisplayStringDate()
                        + (nextDate != null ? "-" + nextDate.copy().backDays(1).toDisplayStringDate() : "") + ")";
            }
        }
    };
    /** number of days in a week. */
    private static final int DAYSINWEEK = 7;

    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceDays(DAYSINWEEK);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return 7 * 24 * 60 * 60 * 1000;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        date.setTime(0, 0, 0);

        Calendar cal = new GregorianCalendar();
        int firstDayInWeek = cal.getFirstDayOfWeek();

        // search clean starting day
        while (date.getDayOfWeek() != firstDayInWeek) {
            date.backDays(1);
        }
        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }

}
