/*
 *  File: HolidayEnumerator_en_US.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.holidayenumerator;

import java.util.Calendar;
import java.util.Locale;

/**
 * Implementation of a HolidayEnumerator for the US. Information obtained from wikipedia
 * (http://en.wikipedia.org/wiki/List_of_holidays_by_country#United_States) <b>No warranty! No regions supported! Only
 * federal holidays!</b>
 * <p>
 * Corrections to peter.kliem@jaret.de
 * </p>
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumerator_en_US.java 297 2007-03-12 21:38:00Z olk $
 */
public class HolidayEnumerator_en_US extends HolidayEnumeratorBase {

    
    public HolidayEnumerator_en_US() {
        _locale = Locale.US;
    }
    
    /**
     * {@inheritDoc}
     */
    protected void fillMap(int year) {
        EasyDate ed = new EasyDate(year, 0, 1);
        addNamedDate(ed, true, "New Year's Day");
        ed = fridayOrMonday(ed);
        if (ed != null) {
            addNamedDate(ed, true, "New Year's Day (observed)");
        }

        ed = new EasyDate(year, 6, 4);
        addNamedDate(ed, true, "Independence Day");
        ed = fridayOrMonday(ed);
        if (ed != null) {
            addNamedDate(ed, true, "Independence Day (observed)");
        }

        ed = new EasyDate(year, 10, 11);
        addNamedDate(ed, true, "Veteran's Day");
        ed = fridayOrMonday(ed);
        if (ed != null) {
            addNamedDate(ed, true, "Veteran's Day (observed)");
        }

        // Martin Luther King day: third monday in january
        addNamedDate(nThWeekdayInMonth(year, 0, Calendar.MONDAY, 3), true, "Martin Luther King Day");

        // Presidents's Day: third monday in february
        addNamedDate(nThWeekdayInMonth(year, 1, Calendar.MONDAY, 3), true, "President's Day");

        // Memorial Day: last monday in may
        addNamedDate(lastWeekdayInMonth(year, 4, Calendar.MONDAY), true, "Memorial Day");

        // labor day: first monday in september
        addNamedDate(nThWeekdayInMonth(year, 8, Calendar.MONDAY, 1), true, "Labor Day");

        // Columbus Day: second Monday in october
        addNamedDate(nThWeekdayInMonth(year, 9, Calendar.MONDAY, 2), true, "Columbus Day");

        // Thanksgiving Day: forth thursday in november
        addNamedDate(nThWeekdayInMonth(year, 10, Calendar.THURSDAY, 4), true, "Thanksgiving Day");

        addNamedDate(year, 11, 24, false, "Christmas eve");
        addNamedDate(year, 11, 26, false, "Boxing day");

        // shifted christmas day may overwrite christmas eve or boxibg day
        ed = new EasyDate(year, 11, 25);
        addNamedDate(ed, true, "Christmas Day");
        ed = fridayOrMonday(ed);
        if (ed != null) {
            addNamedDate(ed, true, "Christmas Day (observed)");
        }

        addNamedDate(year, 11, 31, false, "New year's eve");

        // if new year's eve is a friday it will be a holiday (oberved new year's day)
        // this will overwrite new years eve
        ed = new EasyDate(year, 11, 31);
        if (getWeekday(ed.date) == Calendar.FRIDAY) {
            addNamedDate(ed, true, "New Year's Day (observed)");
        }

    }
}
