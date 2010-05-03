/*
 *  File: HolidayEnumerator_fr_FR.java 
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
 * HolidayEnumerator for France. (Enumerator pour les jours feri� en france) Information from
 * http://fr.wikipedia.org/wiki/Jour_f%C3%A9ri%C3%A9 <b>No warranty!</b>
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumerator_fr_FR.java 293 2007-03-11 17:50:57Z olk $
 */
public class HolidayEnumerator_fr_FR extends HolidayEnumeratorBase {
    /** Region central france; default. */
    public static final String REGION_CENTRAL = "CEN";
    /** Region alsace, equivivalent to modelle. */
    public static final String REGION_ALSACE = "ALS";
    /** Region moselle, equivalent to alsace. */
    public static final String REGION_MOSELLE = "MOS";

    /** all regions. */
    private static final String[] REGIONIDS = {REGION_CENTRAL, REGION_ALSACE, REGION_MOSELLE};
    
    /**
     * This HolidayEnumerator is always bound to the locale fr_FR.
     * 
     * @param regionId additional regionId defined as constants or <code>null</code> for default.
     */
    public HolidayEnumerator_fr_FR(String regionId) {
        _regionId = regionId;
        _locale = Locale.FRANCE;
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
        addNamedDate(year, 0, 1, true, "Jour de l'an");

        addNamedDate(year, 4, 1, true, "F�te du Travail");

        addNamedDate(year, 4, 8, true, "Victoire de 1945");

        addNamedDate(year, 6, 14, true, "F�te nationale");

        addNamedDate(year, 7, 15, true, "Assomption");

        addNamedDate(year, 10, 1, true, "La toussaint");

        addNamedDate(year, 10, 11, true, "Armistice 1918");

        addNamedDate(year, 11, 25, true, "No�l");
        if (_regionId != null && (_regionId.equals(REGION_ALSACE) || _regionId.equals(REGION_MOSELLE))) {
            addNamedDate(year, 11, 26, true, "Saint �tienne");
        } else {
            addNamedDate(year, 11, 26, false, "Saint �tienne");
        }

        // easter days
        EasyDate ed = calcEaster(year);
        addNamedDate(year, ed.month, ed.day, true, "P�ques");
        addNamedDate(year, ed.month, ed.day, 1, true, "Lundi de P�ques");
        if (_regionId != null && (_regionId.equals(REGION_ALSACE) || _regionId.equals(REGION_MOSELLE))) {
            addNamedDate(year, ed.month, ed.day, -2, true, "Vendredi saint");
        } else {
            addNamedDate(year, ed.month, ed.day, -2, false, "Vendredi saint");
        }

        // Ascension
        addNamedDate(year, ed.month, ed.day, 39, true, "Ascension");

        // Pentec�te
        addNamedDate(year, ed.month, ed.day, 49, true, "Pentec�te");

    }

}
