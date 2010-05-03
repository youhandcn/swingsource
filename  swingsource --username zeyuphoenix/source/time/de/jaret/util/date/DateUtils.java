/*
 *  File: DateUtils.java 
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

import de.jaret.util.date.holidayenumerator.HolidayEnumerator;

/**
 * Date util class containing methods for JaretDate and the HolidayEnumerator.
 * 
 * @author Peter Kliem
 * @version $Id: DateUtils.java 262 2007-02-17 23:51:03Z olk $
 */
public class DateUtils {
    /**
     * Count the working days between from and to.
     * 
     * @param from start date. Will be included!
     * @param to end date. will be included.
     * @param he holidayenumerator for determinig if a day is a holiday
     * @return number of working days
     */
    public static int workingDays(JaretDate from, JaretDate to, HolidayEnumerator he) {
        int count = 0;

        JaretDate d = from.copy();
        while (d.compareDateTo(to) <= 0) {
            if (!d.isWeekendDay() && !he.isHoliday(d.getDate())) {
                count++;
            }
            d.advanceDays(1);
        }

        return count;
    }

    /** cache for the first day of the week. */
    private static int _firstDayofWeek = -1;
    /**
     * Retrieve the first day of the week using the systems default locale.
     * 
     * @return the first day of the week as defiend in gregoriean calendar for the current default locale.
     */
    public static int getFirstDayOfWeek() {
        if (_firstDayofWeek != -1) {
            return _firstDayofWeek;
        }
        Calendar cal = Calendar.getInstance();
        _firstDayofWeek = cal.getFirstDayOfWeek();
        return _firstDayofWeek;
    }
    
    
    
}
