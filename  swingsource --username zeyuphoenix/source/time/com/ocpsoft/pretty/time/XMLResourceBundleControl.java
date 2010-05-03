/*
 * PrettyTime is an OpenSource Java time comparison library for creating human
 * readable time.
 * 
 * Copyright (C) 2009 - Lincoln Baxter, III <lincoln@ocpsoft.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see the file COPYING.LESSER3 or visit the
 * GNU website at <http://www.gnu.org/licenses/>.
 */
package com.ocpsoft.pretty.time;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Defines the callback methods needed for the loading of our resources.
 * 
 * @author Thomas Weitzel <tweitzel@synformation.com>
 */
public class XMLResourceBundleControl extends ResourceBundle.Control {

	private static final String format = "xml";
	private ResourceBundle bundle;

	public XMLResourceBundleControl(String resources, Locale locale) {
		super();
		bundle = ResourceBundle.getBundle(resources, locale, this);
	}

	public List<String> getFormats(String baseName) {
		return Collections.singletonList(format);
	}

	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {

		if (null == baseName || null == locale || null == format || null == loader)
			throw new NullPointerException();

		ResourceBundle bundle = null;
		if (format.equals("xml")) {
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, format);
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (null != url) {
					URLConnection connection = url.openConnection();
					if (null != connection) {
						// Caching is switched off
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}

			if (null != stream) {
				BufferedInputStream bis = new BufferedInputStream(stream);
				bundle = new XMLResourceBundle(bis);
				bis.close();
			}
		}
		return bundle;
	}

	public String getKey(String arg) {
		return bundle.getString(arg);
	}

}

class XMLResourceBundle extends ResourceBundle {

	private Properties properties;

	public XMLResourceBundle(InputStream stream) throws IOException {
		properties = new Properties();
		properties.loadFromXML(stream);
	}

	protected Object handleGetObject(String key) {
		return properties.getProperty(key);
	}

	public Enumeration<String> getKeys() {
		Vector<String> stringVector = new Vector<String>();
		Enumeration<?> enumeration = properties.keys();
		while (enumeration.hasMoreElements()) {
			stringVector.add((String) enumeration.nextElement());
		}
		return stringVector.elements();
	}

}