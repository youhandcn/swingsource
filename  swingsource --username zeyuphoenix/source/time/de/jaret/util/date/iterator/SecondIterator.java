/*
 *  File: SecondIterator.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.iterator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import de.jaret.util.date.JaretDate;

/**
 * Implementation of the DateIterator for seconds.
 * 
 * @author kliem
 * @version $Id: SecondIterator.java 828 2009-02-08 13:58:21Z kliem $
 */
public class SecondIterator extends AbstractDateIterator implements DateIterator {
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return date.getSeconds() + "''";
            } else if (format.equals(Format.MEDIUM)) {
                return NF.format(date.getMinutes()) + "' " + NF.format(date.getSeconds());
            } else {
                return NF.format(date.getHours()) + ":" + NF.format(date.getMinutes()) + ":"
                        + NF.format(date.getSeconds());
            }
        }
    };
    /** default step if none is set. */
    private static final int DEFAULT_STEP = 30;

    /** step for seconds. */
    protected int _secondStep = DEFAULT_STEP;

    /** Number format for labels. */
    protected static final NumberFormat NF = new DecimalFormat();
    static {
        NF.setMaximumFractionDigits(0);
        NF.setMinimumIntegerDigits(2);
    }

    /**
     * Contructor supplying the setp.
     * 
     * @param secondStep number of seconds between steps
     */
    public SecondIterator(int secondStep) {
        _secondStep = secondStep;
    }

    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceSeconds(_secondStep);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return _secondStep * 1000;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        date.setSeconds(0);
        date.setMilliseconds(0);

        int diff = date.getSeconds() % _secondStep;
        date.backSeconds(diff);

        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }
}
