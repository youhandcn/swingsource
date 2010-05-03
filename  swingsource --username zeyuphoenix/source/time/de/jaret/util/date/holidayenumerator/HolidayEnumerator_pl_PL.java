/*
 *  File: HolidayEnumerator_pl_PL.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.holidayenumerator;

import java.util.Locale;

/**
 * HolidayEnumerator for Poland. Includes public holidays according to <a
 * href="http://en.wikipedia.org/wiki/Holidays_in_Poland">http://en.wikipedia.org/wiki/Holidays_in_Poland</a>. <b>The
 * calculations are without any warranty!</b>
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumerator_pl_PL.java 293 2007-03-11 17:50:57Z olk $
 */
public class HolidayEnumerator_pl_PL extends HolidayEnumeratorBase {
    /**
     * Constructs the he for poland.
     * 
     * @param regionId region id (currently no region support)
     */
    public HolidayEnumerator_pl_PL(String regionId) {
        _regionId = regionId;
        _locale = new Locale("pl", "PL");
    }

    /*
     * 7th Sunday after Easter Pentecost Sunday pierwszy dzie? Zielonych ?wi?tek (Zielone ?wi?tki) As this holiday
     * always falls on a Sunday it is not widely known. 9th Thursday after Easter Corpus Christi dzie? Bo?ego Cia?a
     * (Bo?e Cia?o)
     */
    /**
     * {@inheritDoc}
     */
    protected void fillMap(int year) {
        // general holidays
        addNamedDate(year, 0, 1, true, "Nowy Rok");
        addNamedDate(year, 4, 1, true, "?wi?to Pa?stwowe");
        addNamedDate(year, 4, 3, true, "?wi?to Narodowe Trzeciego Maja");

        addNamedDate(year, 7, 15, true, "Wniebowzi?cie Naj?wi?tszej Maryi Panny");
        addNamedDate(year, 10, 1, true, "Wszystkich ?wi?tych");
        addNamedDate(year, 10, 11, true, "Narodowe ?wi?to Niepodleg?o?ci");
        // addNamedDate(year, 11, 24, false, "Heiligabend");
        addNamedDate(year, 11, 25, true, "pierwszy dzie? Bo?ego Narodzenia");
        addNamedDate(year, 11, 26, true, "drugi dzie? Bo?ego Narodzenia");
        // addNamedDate(year, 11, 31, false, "Sylvester");

        // easter days
        EasyDate ed = calcEaster(year);
        addNamedDate(year, ed.month, ed.day, true, "pierwszy dzie? Wielkiej Nocy");
        addNamedDate(year, ed.month, ed.day, 1, true, "drugi dzie? Wielkiej Nocy");
        addNamedDate(year, ed.month, ed.day, 7, true, "pierwszy dzie? Zielonych ?wi?tek");

        // 9th Thursday after Easter
        addNamedDate(year, ed.month, ed.day, 4 + 9 * 7, true, "dzie? Bo?ego Cia?a");

    }

}
