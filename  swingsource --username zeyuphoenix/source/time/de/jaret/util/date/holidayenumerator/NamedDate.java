/*
 *  File: NamedDate.java 
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

/**
 * Interface for the combintaion of a date and a name for the date. As an additional information the date can be
 * categorized to be a holiday.
 * 
 * @author Peter Kliem
 * @version $Id: NamedDate.java 242 2007-02-11 21:05:07Z olk $
 */
public interface NamedDate extends Comparable<NamedDate> {
    /**
     * Retrive the name of the date.
     * 
     * @return name of the date
     */
    String getName();

    /**
     * Retrieve the date.
     * 
     * @return date
     */
    Date getDate();

    /**
     * Return whether the day is marked as a holiday.
     * 
     * @return tre for holiday
     */
    boolean isHoliday();
}
