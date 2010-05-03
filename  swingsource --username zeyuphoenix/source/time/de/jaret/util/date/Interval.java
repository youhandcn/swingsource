/*
 *  File: Interval.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date;

import de.jaret.util.misc.PropertyObservable;

/**
 * Interval with observable properties.
 * 
 * @author Peter Kliem
 * @version $Id: Interval.java 875 2009-09-03 22:07:04Z kliem $
 */
public interface Interval extends PropertyObservable {
    /** Constant defining the property name for end. */
    String PROP_END = "End";
    /** Constant defining the property name for begin. */
    String PROP_BEGIN = "Begin";

    /**
     * Set the begin timestamp of the interval.
     * 
     * @param begin the begin timestamp to be set.
     */
    void setBegin(JaretDate begin);

    /**
     * Retrieve the begin timestamp of the interval.
     * 
     * @return the begin timestamp of the interval.
     */
    JaretDate getBegin();

    /**
     * Set the end timestamp of the interval.
     * 
     * @param end the end timestamp to be set.
     */
    void setEnd(JaretDate end);

    /**
     * Retrieve the end timestamp of the interval.
     * 
     * @return the end timestamp of the interval.
     */
    JaretDate getEnd();

    /**
     * Checks whether a given date is contained in the interval.
     * 
     * @param date date to be checked
     * @return true if the date is included in the interval
     */
    boolean contains(JaretDate date);

    /**
     * Checks whether an interval is completely contained in the interval.
     * 
     * @param interval interval to check
     * @return <code>true</code> if the interval is completely contained in the interval
     */
    boolean contains(Interval interval);

    /**
     * Retrieve the length of the interval in seconds.
     * 
     * @return length of the interval in seconds
     */
    int getSeconds();

    /**
     * Check whether this intersects the given interval.
     * 
     * @param interval interval to be checked for intersection.
     * @return true if an intersection exists, false otherwise
     */
    boolean intersects(Interval interval);

}
