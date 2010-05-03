/*
 *  File: HolidayEnumeratorFactory.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.holidayenumerator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Factory for supplying a configured HolidayEnumerator. HolidayEnumerators are charcterized by a locale and an optional
 * additional RegionID. The latter is used to differentiate regions in a country and should be used more finegrained
 * than the Locale's variant.
 * <p>
 * This factory searches in the package de.jaret.util.date.holidayenumertor for classes named "HolidayEnumerator_"+
 * locale string represenation (i.e. "de_DE"). They should have a no argument constructor or a constructor taking the
 * regionId as an argument.
 * </p>
 * 
 * @author Peter Kliem
 * @version $Id: HolidayEnumeratorFactory.java 293 2007-03-11 17:50:57Z olk $
 */
public class HolidayEnumeratorFactory {
    /** Basename for holiday enumerator implementations. */
    private static final String BASENAME = "de.jaret.util.date.holidayenumerator.HolidayEnumerator_";

    /** map for storing already instantiated HolidayEnumerators. */
    private static Map<String, HolidayEnumerator> __enumerators = new HashMap<String, HolidayEnumerator>();

    /**
     * Supply a configured HolidayEnumerator for a locale and a RegionID. The RegionID is an addition to the locale
     * concept, allowing differentiation beetween areas in a country.
     * 
     * @param locale Locale for the HolidayEnumerator
     * @param regionId optional RegionID. (may be <code>null</code>).
     * @return configured HolidayEnumerator or <code>null</code> if no HolidayEnumerator is available for the
     * specified locale.
     */
    public static HolidayEnumerator getHolidayEnumeratorInstance(Locale locale, String regionId) {
        // try to find exact match
        HolidayEnumerator he = __enumerators.get(getHolidayEnumeratorId(locale, regionId));
        if (he != null) {
            return he;
        }
        // not found: try to instantiate a HolidayEnumerator
        he = instantiateHolidayEnumerator(locale, regionId);
        if (he != null) {
            __enumerators.put(getHolidayEnumeratorId(locale, regionId), he);
        }
        return he; // result may be null
    }

    /**
     * Supply holidayenumerator for a combinated String of language and country (e.g. de_DE) and an optional regionID.
     * 
     * @param languageAndCountry languag_country
     * @param regionId optional region ID
     * @return holidayenumerator or <code>null</code> if not available or arguments not valid
     */
    public static HolidayEnumerator getHolidayEnumeratorInstance(String languageAndCountry, String regionId) {
        StringTokenizer tokenizer = new StringTokenizer(languageAndCountry, "_");
        String language = null;
        String country = null;
        if (tokenizer.hasMoreTokens()) {
            language = tokenizer.nextToken();
        }
        if (tokenizer.hasMoreTokens()) {
            country = tokenizer.nextToken();
        }
        if (country == null || language == null) {
            return null;
        }

        Locale locale = new Locale(language, country);
        return getHolidayEnumeratorInstance(locale, regionId);
    }

    /**
     * Construct an identifying String for locale and regionId.
     * 
     * @param locale locale of the he
     * @param regionId region id of the he or <code>null</code>
     * @return concatenation of locae identifying string an regionId
     */
    private static String getHolidayEnumeratorId(Locale locale, String regionId) {
        return locale.toString() + "_" + (regionId != null ? regionId : "");
    }

    /**
     * Get the class of a holiday enumerator for a given locale.
     * 
     * @param locale Locale to lokk for
     * @return Class of HoliayEnumerator or null.
     */
    @SuppressWarnings("unchecked")
    private static Class<? extends HolidayEnumerator> getHolidayEnumeratorClass(Locale locale) {
        // classname
        String classname = BASENAME + locale.toString();
        Class<? extends HolidayEnumerator> clazz = null;
        try {
            clazz = (Class<? extends HolidayEnumerator>) Class.forName(classname);
        } catch (Exception e) {
            // class not found -> does not matter
        }
        return clazz;
    }

    /**
     * Tries to instantiate a suitable HolidayEnumeraotor for the given parameters. May ignore the regionId.
     * 
     * @param locale locale for the holiday enumerator
     * @param regionId regionId for the he
     * @return a HolidayEnumerator matching the parameters or null indicating no HolidayEnumerator could be
     * instantiated.
     */
    private static HolidayEnumerator instantiateHolidayEnumerator(Locale locale, String regionId) {
        Class<? extends HolidayEnumerator> clazz = getHolidayEnumeratorClass(locale);
        if (clazz == null) {
            // no he found
            return null;
        }
        Constructor<? extends HolidayEnumerator> constructor;
        // try constructing with regionID
        try {
            constructor = clazz.getConstructor(new Class[] {String.class});
            HolidayEnumerator he = (HolidayEnumerator) constructor.newInstance(new Object[] {regionId});
            return he;
        } catch (Exception e) {
            // ignore ... next try without parameters
        }
        // if not succesful up to here try constructing without reginId
        try {
            constructor = clazz.getConstructor(new Class[] {});
            HolidayEnumerator he = (HolidayEnumerator) constructor.newInstance(new Object[] {});
            return he;
        } catch (Exception e) {
            // not succesful -> will return null
        }
        return null;
    }

    /** available he locales. */
    private static List<Locale> _availableHolidayEnumeratorLocales;

    /**
     * Retrieve the list of Locales for that HolidayEnumerators can be found. This operation tries to
     * instantiate the holiday enumerators, so it is quite expensive the first time called.
     * 
     * @return List of Locales.
     */
    public static List<Locale> getAvailableHolidayEnumeratorLocales() {
        if (_availableHolidayEnumeratorLocales != null) {
            return _availableHolidayEnumeratorLocales;
        }
        List<Locale> result = new ArrayList<Locale>();
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            if (getHolidayEnumeratorClass(locales[i]) != null) {
                result.add(locales[i]);
            }
        }
        _availableHolidayEnumeratorLocales = result;
        return result;
    }

}
