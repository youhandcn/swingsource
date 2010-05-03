/*
 *  File: AbstractDateIterator.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.iterator;

import java.util.TimeZone;

import de.jaret.util.date.JaretDate;

/**
 * Abstract base Implementation of a DateIterator.
 * 
 * @author kliem
 * @version $Id: AbstractDateIterator.java 883 2009-10-07 21:03:00Z kliem $
 */
public abstract class AbstractDateIterator implements DateIterator {
    /** the current date. */
    protected JaretDate _currentDate;
    /** the last date. */
    protected JaretDate _endDate;

    /** true if DST should be corrected. */
    protected boolean _correctDST = false;
    /** flag for remembering if the last date was in or out DST. */
    protected boolean _lastInDST;

    /**
     * Constructor supplying values.
     * 
     * @param startDate start date
     * @param endDate end date. <code>null</code> for unlimited iterating
     */
    public AbstractDateIterator(JaretDate startDate, JaretDate endDate) {
        reInitialize(startDate, endDate);
    }

    /**
     * Default constructor. Beware: reinitialize has to be called at least once.
     */
    public AbstractDateIterator() {

    }

    /**
     * Correct the given date to a clean starting position.
     * 
     * @param date start date
     * @return clean starting position
     */
    protected abstract JaretDate correctStartDate(JaretDate date);

    /**
     * Advance the given date by the amount necessary.
     * 
     * @param date date to be modified
     */
    protected abstract void advanceDate(JaretDate date);

    /**
     * {@inheritDoc}
     */
    public void reInitialize(JaretDate startDate, JaretDate endDate) {
        _currentDate = startDate.copy();
        _lastInDST = TimeZone.getDefault().inDaylightTime(_currentDate.getDate());
        // correct the start date
        _currentDate = correctStartDate(_currentDate);
        // remember the end date
        _endDate = endDate == null ? null : endDate.copy();
    }

    /**
     * {@inheritDoc}
     */
    public JaretDate getNextDate() {
        if (_currentDate == null) {
            throw new IllegalStateException("not initialized");
        }
        JaretDate result = _currentDate;
        _currentDate = internalNextDate();

        return result;
    }

    /**
     * Internal helper to get the next date without modifications.
     * 
     * @return the next date or <code>null</code> if no next date will be given.
     */
    private JaretDate internalNextDate() {
        if (_currentDate == null) {
            return null;
        }
        JaretDate d = _currentDate.copy();

        // if a dst change happened and a correction should be performed do the nasty
        // correction
        if (_correctDST) {
            boolean inDST = TimeZone.getDefault().inDaylightTime(_currentDate.getDate());

            if (_lastInDST != inDST) {
                if (inDST) {
                    d.advanceMillis(-TimeZone.getDefault().getDSTSavings());
                } else {
                    d.advanceMillis(TimeZone.getDefault().getDSTSavings());
                }
            }

            _lastInDST = inDST;
        }

        // do the advance
        advanceDate(d);

        if (_endDate == null || d.compareTo(_endDate) <= 0) {
            return d;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNextDate() {
        return _currentDate != null;
    }

    /**
     * {@inheritDoc}
     */
    public JaretDate previewNextDate() {
        return _currentDate != null ? _currentDate.copy() : null;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel(JaretDate date, Format format) {
        return getFormatter().getLabel(date, format);
    }

    /** if set this formatter overrides default behaviour. */
    protected IIteratorFormatter _formatter;

    /**
     * Retrieve the formatter to use. This is either a set formatter or the default formatter.
     * 
     * @return a formatter to be used.
     */
    protected IIteratorFormatter getFormatter() {
        if (_formatter != null) {
            return _formatter;
        } else {
            return getDefaultFormatter();
        }
    }

    /**
     * Internal method to retrieve a default formatter.
     * 
     * @return the default formatter of the iterator
     */
    protected abstract IIteratorFormatter getDefaultFormatter();

    /**
     * {@inheritDoc}
     */
    public void setFormatter(IIteratorFormatter formatter) {
        _formatter = formatter;
    }

    /**
     * {@inheritDoc}
     */
    public void setCorrectDST(boolean correctDST) {
        _correctDST = correctDST;
    }

}
