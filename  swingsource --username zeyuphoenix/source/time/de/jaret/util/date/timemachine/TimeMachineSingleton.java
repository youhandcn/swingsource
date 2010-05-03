/*
 *  File: TimeMachineSingleton.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.timemachine;

/**
 * Singleton to provide a time machine. The time machine will be setup to begin with the current time. 
 * Do your setup early in your application setup if you want a special starting time or a real time provider.
 * 
 * @author kliem
 * @version $Id: TimeMachineSingleton.java 702 2007-12-30 11:54:55Z kliem $
 */
public class TimeMachineSingleton {
    /** the time machine instance. */
    private static TimeMachine _instance = new TimeMachine(System.currentTimeMillis());
    
    
    /**
     * Retrieve the time provider instance.
     * @return time provider to be used
     */
    public static ITimeProvider getTimeProvider() {
        return _instance;
    }
    
    /**
     * Retrieve the time machine for configuration etc.
     * @return the time machien instance
     */
    public static TimeMachine getTimeMachine() {
        return _instance;
    }
    
    
}
