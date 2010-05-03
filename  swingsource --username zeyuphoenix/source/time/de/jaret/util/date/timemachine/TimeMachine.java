/*
 *  File: TimeMachine.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.date.timemachine;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Implementation of a time supplier that can be controlled by various options. This is useful to cut dependencies on
 * system time. The class should usually be used as a singleton, but is no singleton itself.
 * 
 * @author kliem
 * @version $Id: TimeMachine.java 702 2007-12-30 11:54:55Z kliem $
 */
public class TimeMachine implements ITimeProvider {
    /** the current time. */
    private volatile long _currentTime;
    /** listener list. */
    private List<ITimeMachineListener> _listeners = new Vector<ITimeMachineListener>();

    /** flag indicating forward movement in time only. */
    private boolean _forwardOnly = true;
    /** flag indicating autoincrement. */
    private boolean _autoIncrement = false;
    /** flag indicating the time machine supplies realtime. */
    private boolean _realTime = false;

    /**
     * Construct the time machine with an initial time.
     * 
     * @param initialTime time (millis)
     */
    public TimeMachine(long initialTime) {
        _currentTime = initialTime;
    }

    /**
     * {@inheritDoc} base method that supplies the timea nd might auto increment the time value.
     */
    public long getCurrentTime() {
        if (_realTime) {
            return System.currentTimeMillis();
        }
        if (_autoIncrement) {
            synchronized (this) {
                long time = _currentTime;
                increment();
                return time;
            }
        } else {
            return _currentTime;
        }
    }

    /**
     * {@inheritDoc} Wraps millis from getCurrentTime in a Date object.
     */
    public Date getCurentTimeAsDate() {
        return new Date(getCurrentTime());
    }

    private void increment() {
        _currentTime++;
        fireTimeChanged(_currentTime - 1, _currentTime);
    }

    /**
     * Set the time of the timemachine.
     * 
     * @param time millis
     */
    public synchronized void setTime(long time) {
        if (_realTime) {
            throw new IllegalArgumentException("Time machine is in real time mode.");
        }
        if (_forwardOnly && time < _currentTime) {
            throw new IllegalArgumentException("Time must move forward");
        }
        long oldTime = _currentTime;
        _currentTime = time;
        fireTimeChanged(oldTime, _currentTime);
    }

    /**
     * Add a listener to the timemachine.
     * 
     * @param timeMachineListener time machine listener
     */
    public synchronized void addTimeMachineListener(ITimeMachineListener timeMachineListener) {
        if (!_listeners.contains(timeMachineListener)) {
            _listeners.add(timeMachineListener);
        }
    }

    /**
     * Remove a listener from the time machine.
     * 
     * @param timeMachineListener listener to remove
     */
    public synchronized void remTimeMachineListener(ITimeMachineListener timeMachineListener) {
        _listeners.remove(timeMachineListener);
    }

    /**
     * Inform listeners about a change.
     * 
     * @param oldTime last time
     * @param newTime current (= new) time
     */
    protected void fireTimeChanged(long oldTime, long newTime) {
        for (ITimeMachineListener listener : _listeners) {
            listener.timeChanged(this, oldTime, newTime);
        }
    }

    /**
     * true if the time will always be going towards the future.
     * 
     * @return <code>true</code> for ongoing time only
     */
    public boolean isForwardOnly() {
        return _forwardOnly;
    }

    /**
     * Set forward protection: if set to <code>true</code> time shifts going before the current time will be
     * disallowed. Forward only is the default.
     * 
     * @param forwardOnly <code>true</code> for forward only mode.
     */
    public void setForwardOnly(boolean forwardOnly) {
        _forwardOnly = forwardOnly;
    }

    /**
     * If autoincrement is <code>true</code> the time will be incremented by 1 millisecond with each time request.
     * 
     * @return <code>true</code> if autoincrement is set
     */
    public boolean isAutoIncrement() {
        return _autoIncrement;
    }

    /**
     * set autoincrement mode.
     * 
     * @param autoIncrement if sert to <code>true</code> the time will be increased by 1 millisecond which each
     * request
     */
    public void setAutoIncrement(boolean autoIncrement) {
        _autoIncrement = autoIncrement;
    }

    /**
     * If this return <code>true</code> the time machine is in real time mode always providing the system's current
     * time.
     * 
     * @return true for real time mode
     */
    public boolean isRealTime() {
        return _realTime;
    }

    /**
     * Set the realtime mode.
     * 
     * @param realTime if set to <code>true</code> the time machine will be disabled, always returning the systems's
     * current time
     */
    public void setRealTime(boolean realTime) {
        _realTime = realTime;
    }
}
