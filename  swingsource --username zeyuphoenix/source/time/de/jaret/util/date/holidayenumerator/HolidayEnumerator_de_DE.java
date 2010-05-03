/*
 *  File: HolidayEnumerator_de_DE.java 
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
import java.util.Date;
import java.util.Locale;

/**
 * HolidayEnumerator for Germany. The Regions are used to indicate the state (Bundesland). Holidays not valid in the
 * selected region are marked as special days. If no region is given the days are also marked as special days. <b>The
 * calculations are without any warranty!</b>
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumerator_de_DE.java 297 2007-03-12 21:38:00Z olk $
 */
public class HolidayEnumerator_de_DE extends HolidayEnumeratorBase {
    /** RegionId for Nordrhein-Westfalen. */
    public static final String DE_DE_REGION_NRW = "NRW";
    /** RegionID for Berlin. */
    public static final String DE_DE_REGION_BLN = "BLN";
    /** RegionID for Bayern. */
    public static final String DE_DE_REGION_BAV = "BAV";
    /** RegionID for Hamburg. */
    public static final String DE_DE_REGION_HH = "HH";
    /** RegionID for Bremen. */
    public static final String DE_DE_REGION_HB = "HB";
    /** RegionID for Schleswig-Holstein. */
    public static final String DE_DE_REGION_SHLST = "SHLST";
    /** RegionID for Niedersachsen. */
    public static final String DE_DE_REGION_NIE = "NIE";
    /** RegionID for Mecklenburg-Vorpommern. */
    public static final String DE_DE_REGION_MVP = "MVP";
    /** RegionID for Brandenburg. */
    public static final String DE_DE_REGION_BRA = "BRA";
    /** RegionID for Thueringen. */
    public static final String DE_DE_REGION_THUE = "THUE";
    /** RegionID for Sachsen. */
    public static final String DE_DE_REGION_SACH = "SACH";
    /** RegionID for Sachsen-Anhalt. */
    public static final String DE_DE_REGION_SAAN = "SAAN";
    /** RegionID for Baden-Wuertenberg. */
    public static final String DE_DE_REGION_BAWUE = "BAWUE";
    /** RegionID for Hessen. */
    public static final String DE_DE_REGION_HES = "HES";
    /** RegionID for Saarland. */
    public static final String DE_DE_REGION_SAAR = "SAAR";
    /** RegionID for Rheinland-Pfalz. */
    public static final String DE_DE_REGION_RHPFA = "RHPFA";

    private static final String[] REGIONIDS = {DE_DE_REGION_NRW, DE_DE_REGION_BLN, DE_DE_REGION_BAV, DE_DE_REGION_HH,
            DE_DE_REGION_HB, DE_DE_REGION_SHLST, DE_DE_REGION_NIE, DE_DE_REGION_MVP, DE_DE_REGION_BRA,
            DE_DE_REGION_THUE, DE_DE_REGION_SACH, DE_DE_REGION_SAAN, DE_DE_REGION_BAWUE, DE_DE_REGION_HES,
            DE_DE_REGION_SAAR, DE_DE_REGION_RHPFA};

    /**
     * Construct a holiday enumerator for germany.
     * 
     * @param regionId region id or <code>null</code> for no special region
     */
    public HolidayEnumerator_de_DE(String regionId) {
        _regionId = regionId;
        _locale = Locale.GERMANY;
    }


    /**
     * {@inheritDoc}
     */
    public String[] getAvailableRegionIds() {
        return REGIONIDS;
    }

    /**
     * {@inheritDoc}
     */
    protected void fillMap(int year) {
        // general holidays
        addNamedDate(year, 0, 1, true, "Neujahr");
        addNamedDate(year, 4, 1, true, "Tag der Arbeit");
        addNamedDate(year, 9, 3, true, "Tag der deutschen Einheit");
        addNamedDate(year, 11, 24, false, "Heiligabend");
        addNamedDate(year, 11, 25, true, "1. Weihnachtsfeiertag");
        addNamedDate(year, 11, 26, true, "2. Weihnachtsfeiertag");
        addNamedDate(year, 11, 31, false, "Sylvester");

        // easter days
        EasyDate ed = calcEaster(year);
        addNamedDate(year, ed.month, ed.day, true, "Ostersonntag");
        addNamedDate(year, ed.month, ed.day, 1, true, "Ostermontag");
        addNamedDate(year, ed.month, ed.day, -2, true, "Karfreitag");

        // christi himmelfahrt
        addNamedDate(year, ed.month, ed.day, 39, true, "Christi Himmelfahrt");

        // pfingsten
        addNamedDate(year, ed.month, ed.day, 49, true, "Pfingstsonntag");
        addNamedDate(year, ed.month, ed.day, 50, true, "Pfingstmontag");

        // hl drei könige
        // bayern, sachsen-anhalt und bw
        if (_regionId != null
                && (_regionId.equals(DE_DE_REGION_BAV) || _regionId.equals(DE_DE_REGION_SAAN) || _regionId
                        .equals(DE_DE_REGION_BAWUE))) {
            addNamedDate(year, 0, 6, true, "Heilige drei Könige");
        } else {
            addNamedDate(year, 0, 6, false, "Heilige drei Könige");
        }

        // fronleichnam
        // bayer, bawue, hessen nrw, rheinland-pfalz, saarland
        if (_regionId != null
                && (_regionId.equals(DE_DE_REGION_BAV) || _regionId.equals(DE_DE_REGION_BAWUE)
                        || _regionId.equals(DE_DE_REGION_HES) || _regionId.equals(DE_DE_REGION_NRW)
                        || _regionId.equals(DE_DE_REGION_RHPFA) || _regionId.equals(DE_DE_REGION_SAAR))) {
            addNamedDate(year, ed.month, ed.day, 60, true, "Fronleichnam");
        } else {
            addNamedDate(year, ed.month, ed.day, 60, false, "Fronleichnam");
        }

        // Mariä Himmelfahrt
        // nur saarland
        if (_regionId != null && (_regionId.equals(DE_DE_REGION_SAAR))) {
            addNamedDate(year, 7, 15, true, "Mariä Himmelfahrt");
        } else {
            addNamedDate(year, 7, 15, false, "Mariä Himmelfahrt");
        }

        // reformationstag
        // brandnburg, meck vorpomm, sachen, s-anhalt, thüringen
        if (_regionId != null
                && (_regionId.equals(DE_DE_REGION_BRA) || _regionId.equals(DE_DE_REGION_MVP)
                        || _regionId.equals(DE_DE_REGION_SACH) || _regionId.equals(DE_DE_REGION_SAAN) || _regionId
                        .equals(DE_DE_REGION_THUE))) {
            addNamedDate(year, 9, 31, true, "Reformationstag");
        } else {
            addNamedDate(year, 9, 31, false, "Reformationstag");
        }

        // allerheiligen
        // bayern, bawue, nrw, rhpfalz, saarland,
        if (_regionId != null
                && (_regionId.equals(DE_DE_REGION_BAV) || _regionId.equals(DE_DE_REGION_BAWUE)
                        || _regionId.equals(DE_DE_REGION_NRW) || _regionId.equals(DE_DE_REGION_RHPFA) || _regionId
                        .equals(DE_DE_REGION_SAAR))) {
            addNamedDate(year, 10, 1, true, "Allerheiligen");
        } else {
            addNamedDate(year, 10, 1, false, "Allerheiligen");
        }

        // buss und bettag
        // Mittwoch vor dem letzten Sonntag im Kirchenjahr, d. h. der Mittwoch zwischen dem 16. und 22. November
        // nur sachsen
        EasyDate bbt = calcBussBettag(year);
        if (_regionId != null && (_regionId.equals(DE_DE_REGION_SACH))) {
            addNamedDate(year, bbt.month, bbt.day, true, "Buß- und Bettag");
        } else {
            addNamedDate(year, bbt.month, bbt.day, false, "Buß- und Bettag");
        }

        // special days only
        addNamedDate(year, ed.month, ed.day, -48, false, "Rosenmontag");
        addNamedDate(year, ed.month, ed.day, -52, false, "Weiberfastnacht");
    }

    /**
     * Calculate the buss- und bettag. (Mittwoch vor dem letzten Sonntag im Kirchenjahr, d. h. der Mittwoch zwischen dem
     * 16. und 22. November)
     * 
     * @param year year
     * @return EasyDatewith the date
     */
    protected EasyDate calcBussBettag(int year) {
        EasyDate result = null;
        int i = 0;
        while (i < 7 && result == null) {
            Date d = getDate(year, 10, 16, i);
            if (getWeekday(d) == Calendar.WEDNESDAY) {
                result = new EasyDate(d);
            }
            i++;
        }
        return result;
    }

}
