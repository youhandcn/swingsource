/*
 *  File: MinuteIterator.java 
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
 * Implementation of the DateIterator for minutes.
 * 
 * @author kliem
 * @version $Id: MinuteIterator.java 828 2009-02-08 13:58:21Z kliem $
 */
public class MinuteIterator extends AbstractDateIterator implements DateIterator {
    /** default formatter. */
    protected IIteratorFormatter _defaultFormatter = new IIteratorFormatter() {

        /**
         * {@inheritDoc}
         */
        public String getLabel(JaretDate date, Format format) {
            if (format.equals(Format.SHORT)) {
                return date.getMinutes() + "'";
            } else if (format.equals(Format.MEDIUM)) {
                return NF.format(date.getHours()) + ":" + NF.format(date.getMinutes());
            } else {
                return NF.format(date.getHours()) + ":" + NF.format(date.getMinutes()) + ":"
                        + NF.format(date.getSeconds());
            }
        }
    };
    /** default step if none is set. */
    private static final int DEFAULT_STEP = 30;

    /** step for minutes. */
    protected int _minuteStep = DEFAULT_STEP;

    /** Number format for labels. */
    protected static final NumberFormat NF = new DecimalFormat();
    static {
        NF.setMaximumFractionDigits(0);
        NF.setMinimumIntegerDigits(2);
    }

    /**
     * Contructor supplying the setp.
     * 
     * @param minuteStep number of minutes between steps
     */
    public MinuteIterator(int minuteStep) {
        _minuteStep = minuteStep;
    }

    /**
     * {@inheritDoc}
     */
    protected void advanceDate(JaretDate date) {
        date.advanceMinutes(_minuteStep);
    }

    /**
     * {@inheritDoc}
     */
    public long getApproxStepMilliSeconds() {
        return _minuteStep * 60 * 1000;
    }

    /**
     * {@inheritDoc}
     */
    protected JaretDate correctStartDate(JaretDate date) {
        date.setSeconds(0);
        date.setMilliseconds(0);

        int diff = date.getMinutes() % _minuteStep;
        date.backMinutes(diff);

        return date;
    }

    /**
     * {@inheritDoc}
     */
    protected IIteratorFormatter getDefaultFormatter() {
        return _defaultFormatter;
    }

}
