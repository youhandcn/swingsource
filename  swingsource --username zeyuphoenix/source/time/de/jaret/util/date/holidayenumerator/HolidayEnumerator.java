/*
 *  File: HolidayEnumerator.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.holidayenumerator;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Interface for a class enumerating holidays and other specially named days for a given Locale and additional RegionID.
 * A special day is a day with a given name but without a meaning to the working world (such as the 24.12. in germany:
 * it is named "Heiligabend" but it is not a holiday by law). Those can be used for additional information in calendar
 * tools.
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumerator.java 293 2007-03-11 17:50:57Z olk $
 */
public interface HolidayEnumerator {
    /**
     * Check whether a given day denotes a special (named) day.
     * 
     * @param date The date to be checked
     * @return true if the date denotes a named day.
     */
    boolean isSpecialDay(Date date);

    /**
     * Check whether a given day denotes a holiday.
     * 
     * @param date date to be checked
     * @return true if the date denotes a holiday
     */
    boolean isHoliday(Date date);

    /**
     * Retrieve the name of a special day or holiday.
     * 
     * @param date date for which the name is to be retrieved
     * @return the name of the day or <code>null</code> if no name is found.
     */
    String getDayName(Date date);

    /**
     * Retrieve a list of named days for a given year.
     * 
     * @param year year to be questioned
     * @param includeSpecialDays if true, special days are includes, otherwise the list will only contain holidays
     * @return an ordered list of named dates
     */
    List<NamedDate> getNamedDays(int year, boolean includeSpecialDays);

    /**
     * Retrieve a list of named days for a given year.
     * 
     * @param year year to be questioned
     * @param month month to be questioned
     * @param includeSpecialDays if true, special days are includes, otherwise the list will only contain holidays
     * @return an ordered list of named dates
     */
    List<NamedDate> getNamedDays(int year, int month, boolean includeSpecialDays);

    /**
     * Retrieve the Locale of this HolidayEnumerator.
     * 
     * @return configured Locale
     */
    Locale getLocale();

    /**
     * Retrieve the RegionId of this HolidayEnumerator.
     * 
     * @return the regionid
     */
    String getRegionId();

    /**
     * Retrieve the available region identifiers of the holiday enumerator.
     * 
     * @return the availbale ids or an empty array for no regions
     */
    String[] getAvailableRegionIds();

}
