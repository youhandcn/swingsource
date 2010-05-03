/*
 *  File: TimeHelper.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date;

import java.text.ParseException;
import java.util.StringTokenizer;

import de.jaret.util.misc.FormatHelper;

/**
 * Some methods helping to deal with time values.
 * 
 * @author Peter Kliem
 * @version $Id: TimeHelper.java 242 2007-02-11 21:05:07Z olk $
 */
public class TimeHelper {

    public static String secondsToString(int sec, boolean includeSeconds) {
        int hours = sec / 3600;
        int minutes = (sec % 3600) / 60;
        int seconds = (sec % 60);
        String str = FormatHelper.NFInt2Digits().format(hours) + ":" + FormatHelper.NFInt2Digits().format(minutes);
        if (includeSeconds) {
            str = str + ":" + FormatHelper.NFInt2Digits().format(seconds);
        }
        return str;
    }

    public static int timeStringToSeconds(String str) throws ParseException {
        try {
            StringTokenizer tokenizer = new StringTokenizer(str, ":");
            int h = Integer.parseInt(tokenizer.nextToken());
            int m = Integer.parseInt(tokenizer.nextToken());
            int s = 0;
            if (tokenizer.hasMoreTokens()) {
                s = Integer.parseInt(tokenizer.nextToken());
            }
            return h * 3600 + m * 60 + s;
        } catch (Exception e) {
            throw new ParseException(str, 0);
        }
    }

}
