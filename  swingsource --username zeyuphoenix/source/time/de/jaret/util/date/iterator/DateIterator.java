/*
 *  File: DateIterator.java 
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
 * Interface describing an iterator for dates.
 * 
 * @author kliem
 * @version $Id: DateIterator.java 883 2009-10-07 21:03:00Z kliem $
 */
public interface DateIterator {
    /**
     * Enumeration for the different format types.
     */
    enum Format {
        /**
         * SHORT, MEDIUM, LONG: denote the format of the label.
         */
        SHORT, MEDIUM, LONG
    };

    /**
     * (re)initialize the iterator with a start and an end date.
     * 
     * @param startDate first date that will be returned in subsequent calls
     * @param endDate last date that will be returned
     */
    void reInitialize(JaretDate startDate, JaretDate endDate);

    /**
     * Retrieve the next date from the iterator.
     * 
     * @return next date
     */
    JaretDate getNextDate();

    /**
     * Check whether the iterator will supply a next date.
     * 
     * @return true if the next call to getNextDate() will return a valid date
     */
    boolean hasNextDate();

    /**
     * Retrieve the next date without modifying the iterator.
     * 
     * @return the next valid date or <code>null</code> if no subsequent date is available
     */
    JaretDate previewNextDate();

    /**
     * Retrieve a label for a given date.
     * 
     * @param date the date to format
     * @param format short, medium or long
     * @return label
     */
    String getLabel(JaretDate date, Format format);

    /**
     * Set a formatter to be used with this iterator overriding the default formatting.
     * 
     * @param formatter the formatter to use.
     */
    void setFormatter(IIteratorFormatter formatter);

    /**
     * Return the approximate step between dates in milli seconds.
     * 
     * @return approximate step between iterated dates in milli seconds
     */
    long getApproxStepMilliSeconds();
    
    /**
     * If the correct DST flag is set to true, the iterator will add/remove an hour if the
     * DST switch time has been passed. This is false by default and you should only use it if you are knowing
     * what you are doing. This uses the Default TimeZone for determining DST switches.
     */
    void setCorrectDST(boolean correctDST);

}
