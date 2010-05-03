/*
 *  File: MapWatcher.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.collections;

/**
 * @author Peter Kliem
 * @version $id$
 */
public interface MapWatcher {
    public void elementAdded(Object key, Object value);

    public void elementRemoved(Object key, Object value);
}
