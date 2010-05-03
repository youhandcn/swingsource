/*
 *  File: IIteratorFormatter.java 
 *  Copyright (c) 2004-2009  Peter Kliem (Peter.Kliem@jaret.de)
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
 * Interface used to decsribe a simple formatter for date iterators.
 * 
 * @author kliem
 * @version $Id: IIteratorFormatter.java 829 2009-02-08 14:00:40Z kliem $
 */
public interface IIteratorFormatter {
    /**
     * Provide a formatted output for a given date.
     * 
     * @param date date to format
     * @param format format (short, medium, long)
     * @return the formatted date as String
     */
    String getLabel(JaretDate date, DateIterator.Format format);
}
