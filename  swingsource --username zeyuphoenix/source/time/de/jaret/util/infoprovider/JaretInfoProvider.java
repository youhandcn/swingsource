/*
 *  File: JaretInfoProvider.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.infoprovider;

import java.util.List;

/**
 * Interface for an information provider providing hirarchical structured information in a key/value fashion .
 * 
 * @author Peter Kliem
 * @version $Id: JaretInfoProvider.java 242 2007-02-11 21:05:07Z olk $
 */
public interface JaretInfoProvider {
    /**
     * Give a human readable name for the information provider
     * 
     * @return Name to be displayed
     */
    public String getInfoProviderName();

    /**
     * Access to the information provided by this infoprovider
     * 
     * @return one of the JaretAccess constants
     */
    public int getAccess();

    /**
     * return a list of the info entries
     * 
     * @return List of the information as SysInfoEntries
     */
    public List<SysInfoEntry> getSysInfoEntries();

    /**
     * List of subinformation providers registered to this info provider
     * 
     * @return List of the registered infoprovders
     */
    public List<JaretInfoProvider> getSubInfoProviders();

    /**
     * Add an information provider as a sub information provider
     * 
     * @param infoProvider infoprovider to be added
     */
    public void addSubInfoProvider(JaretInfoProvider infoProvider);

    /**
     * Remove agegsitered sub infoprovider
     * 
     * @param infoProvider infoprovider to be removed
     */
    public void remSubInfoProvider(JaretInfoProvider infoProvider);

}
