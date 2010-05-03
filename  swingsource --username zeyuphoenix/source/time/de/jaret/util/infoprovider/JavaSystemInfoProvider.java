/*
 *  File: JavaSystemInfoProvider.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.infoprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * JaretSystemInfoProvider providing information about the java runtime.
 * 
 * @author Peter Kliem
 * @version $Id: JavaSystemInfoProvider.java 242 2007-02-11 21:05:07Z olk $
 */
public class JavaSystemInfoProvider implements JaretInfoProvider {

    /*
     * (non-Javadoc)
     * 
     * @see de.jaret.app.JaretSystemInfoProvider#getInfoProviderName()
     */
    public String getInfoProviderName() {
        return "Java Runtime environment";
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jaret.app.JaretSystemInfoProvider#getAccess()
     */
    public int getAccess() {
        return SysInfoEntry.ACCESS_PUBLIC;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jaret.app.JaretSystemInfoProvider#getSysInfoEntries()
     */
    public List<SysInfoEntry> getSysInfoEntries() {
        List<SysInfoEntry> entries = new ArrayList<SysInfoEntry>();
        Properties props = System.getProperties();
        Iterator it = props.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String val = props.getProperty(key);
            entries.add(new SysInfoEntry(key, val));
        }
        return entries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jaret.app.JaretSystemInfoProvider#getSubInfoProviders()
     */
    public List<JaretInfoProvider> getSubInfoProviders() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.jaret.app.JaretSystemInfoProvider#addSubInfoProvider(de.jaret.app.JaretSystemInfoProvider)
     */
    public void addSubInfoProvider(JaretInfoProvider infoProvider) {
        throw new RuntimeException("Not implemented");
    }

    public void remSubInfoProvider(JaretInfoProvider infoProvider) {
    }

}
