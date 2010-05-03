/*
 *  File: ITimeProvider.java 
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

/**
 * Interface describing somthing that provides a timestamp.
 * 
 * @author kliem
 * @version $Id: ITimeProvider.java 702 2007-12-30 11:54:55Z kliem $
 */
public interface ITimeProvider {
    /**
     * Retrieve the current time.
     * 
     * @return current time as millis.
     */
    long getCurrentTime();

    /**
     * Retrieve the current time as a Date object.
     * 
     * @return current time as java.util.Date
     */
    Date getCurentTimeAsDate();

}
