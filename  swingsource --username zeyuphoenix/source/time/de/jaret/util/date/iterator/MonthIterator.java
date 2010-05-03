/*
 *  File: MonthIterator.java 
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
 * dateIterator iterating over months.
 * 
 * @author kliem
 * @version $Id: MonthIterator.java 828 2009-02-08 13:58:21Z kliem $
 */
public class MonthIterator extends AbstractDateIterator implements DateIterator {
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return Integer.toString(date.getMonth()) + ".";
            } else if (format.equals(Format.MEDIUM)) {
                return date.getShortMonthString() + " " + date.getYear();
            } else {
                return date.getMonthString() + " " + date.getYear();
            }
        }
    };

    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceMonths(1);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return 31L * 24L * 60L * 60L * 1000L;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        date.setTime(0, 0, 0);
        date.setDay(1);
        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }

}
