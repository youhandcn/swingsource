/*
 *  File: PropertyObservable.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.misc;

import java.beans.PropertyChangeListener;

/**
 * Interface for an observable object.
 * 
 * @author Peter Kliem
 * @version $Id: PropertyObservable.java 250 2007-02-12 00:15:49Z olk $
 */
public interface PropertyObservable {
    /**
     * Add a listener to be informed when a property changes.
     * 
     * @param listener PropertyChangeListener to inform
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a previously added PropertyChangeListener.
     * 
     * @param listener listener to remove.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}
