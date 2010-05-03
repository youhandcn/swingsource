/*
 *  File: SysInfoEntry.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.infoprovider;

/**
 * An entry provided by JaretSystemInfoProviders
 * 
 * @author Peter Kliem
 * @version $Id: SysInfoEntry.java 242 2007-02-11 21:05:07Z olk $
 */
public class SysInfoEntry {
    // Access levels
    public final static int ACCESS_PUBLIC = 0;
    public final static int ACCESS_INTERN = 1;
    public final static int ACCESS_PRIVATE = 2;
    public final static int ACCESS_DEBUG = 3;

    public String name;
    public String value;
    public int access = ACCESS_PUBLIC;

    public SysInfoEntry(String name, String val) {
        this(name, val, ACCESS_PUBLIC);
    }

    public SysInfoEntry(String name, String val, int access) {
        this.name = name;
        this.value = val;
        this.access = access;
    }
}
