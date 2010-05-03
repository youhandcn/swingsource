/*
 *  File: ITimeMachineListener.java 
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
 * Listener that monitors a time machine.
 * 
 * @author kliem
 * @version $Id: ITimeMachineListener.java 702 2007-12-30 11:54:55Z kliem $
 */
public interface ITimeMachineListener {

    /**
     * Method get called whenever the time in the time machine changed.
     * 
     * @param timeMachine the time machine
     * @param lastTime the last time of the time machine 
     * @param currentTime the new time of the time machine
     */
    void timeChanged(TimeMachine timeMachine, long lastTime, long currentTime);
    
    
}
