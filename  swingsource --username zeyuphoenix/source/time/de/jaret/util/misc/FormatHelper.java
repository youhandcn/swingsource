/*
 *  File: FormatHelper.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.misc;

import java.text.NumberFormat;

/**
 * @author Peter Kliem
 * @version $Id: FormatHelper.java 242 2007-02-11 21:05:07Z olk $
 */
public class FormatHelper {
    private static NumberFormat __double2Digits;

    public static NumberFormat NFDouble2Digits() {
        if (__double2Digits == null) {
            __double2Digits = NumberFormat.getNumberInstance();
            __double2Digits.setMaximumFractionDigits(2);
            __double2Digits.setMinimumFractionDigits(2);
        }
        return __double2Digits;
    }

    private static NumberFormat __int2Digits;

    public static NumberFormat NFInt2Digits() {
        if (__int2Digits == null) {
            __int2Digits = NumberFormat.getNumberInstance();
            __int2Digits.setMaximumIntegerDigits(2);
            __int2Digits.setMinimumIntegerDigits(2);
        }
        return __int2Digits;
    }

}
