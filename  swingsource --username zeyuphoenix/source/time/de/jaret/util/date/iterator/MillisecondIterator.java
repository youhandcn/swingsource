/*
 *  File: MillisecondIterator.java 
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
 * @version $Id: MillisecondIterator.java 829 2009-02-08 14:00:40Z kliem $
 */
public class MillisecondIterator extends AbstractDateIterator implements DateIterator {
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return date.getMillis() + "ms";
            } else if (format.equals(Format.MEDIUM)) {
                return NF.format(date.getSeconds()) + "'' " + NF.format(date.getMillis());
            } else {
                String str = NF.format(date.getHours()) + ":" + NF.format(date.getMinutes()) + ":"
                        + NF.format(date.getSeconds());
                int millis = date.getMillis();
                String str2 = NF2.format(millis);
                return str + ":" + str2;
            }
        }
    };
    /** default step if none is set. */
    private static final int DEFAULT_STEP = 100;

    /** step for seconds. */
    protected long _millisecondStep = DEFAULT_STEP;

    /** Number format for labels (2 digits). */
    protected static final NumberFormat NF = new DecimalFormat();
    /** Number format for labels (3 digits). */
    protected static final NumberFormat NF2 = new DecimalFormat();
    static {
        NF.setMaximumFractionDigits(0);
        NF.setMinimumIntegerDigits(2);
        NF2.setMaximumFractionDigits(0);
        NF2.setMinimumIntegerDigits(3);
    }

    /**
     * Contructor supplying the setp.
     * 
     * @param millisecondStep number of milli seconds between steps
     */
    public MillisecondIterator(long millisecondStep) {
        _millisecondStep = millisecondStep;
    }

    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceMillis(_millisecondStep);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return _millisecondStep;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        long diff = date.getMillis() % _millisecondStep;
        date.advanceMillis(-diff);

        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }

}
