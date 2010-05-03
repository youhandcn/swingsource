/*
 *  File: DayIterator.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.iterator;

import de.jaret.util.date.JaretDate;

/**
 * Implementation of the DateIterator for days.
 * 
 * @author kliem
 * @version $Id: DayIterator.java 845 2009-02-22 18:28:44Z kliem $
 */
public class DayIterator extends AbstractDateIterator implements DateIterator {
    /** day step. */
    protected int _days = 1;
    
    
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return Integer.toString(date.getDay()) + ".";
            } else if (format.equals(Format.MEDIUM)) {
                return Integer.toString(date.getDay()) + ".(" + date.getShortDayOfWeekString() + ")";
            } else {
                return Integer.toString(date.getDay()) + ".(" + date.getDayOfWeekString() + ")";
            }
        }
    };

    /**
     * Constructs the iterator with a 1 day step.
     */
    public DayIterator() {
        // defaults to 1
    }
    /**
     * Constructs the iterator with a n day step.
     * @param days number of days per step
     */
    public DayIterator(int days) {
        _days = days;
    }
    
    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceDays(_days);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return (long)_days* 24L * 60L * 60L * 1000L;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        date.setTime(0, 0, 0);
        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }

}
