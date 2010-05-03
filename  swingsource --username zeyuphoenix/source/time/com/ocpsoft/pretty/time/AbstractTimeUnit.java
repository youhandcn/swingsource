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

import java.util.Locale;

public abstract class AbstractTimeUnit {

	protected Locale locale;
	protected XMLResourceBundleControl xrbc;
	protected TimeFormat format;
	protected String name;
	protected String pluralName;
	
	// Use sensitive defaults
	protected long maxQuantity = 0;
	protected long millisPerUnit = 1;

	public AbstractTimeUnit(Locale locale) {
		this.locale = locale;

		// Resource bundles need to be in the given package, names start with 'resources', e.g. 'resources_de.xml'
		xrbc = new XMLResourceBundleControl("com.ocpsoft.pretty.time.resources", locale);

		format = new BasicTimeFormat()
			.setPattern			(xrbc.getKey(getResourceKeyPrefix() + "Pattern"))
			.setFuturePrefix	(xrbc.getKey(getResourceKeyPrefix() + "FuturePrefix"))
			.setFutureSuffix	(xrbc.getKey(getResourceKeyPrefix() + "FutureSuffix"))
			.setPastPrefix		(xrbc.getKey(getResourceKeyPrefix() + "PastPrefix"))
			.setPastSuffix		(xrbc.getKey(getResourceKeyPrefix() + "PastSuffix"));

		name =					(xrbc.getKey(getResourceKeyPrefix() + "Name"));
		pluralName =			(xrbc.getKey(getResourceKeyPrefix() + "PluralName"));
	}

	abstract protected String getResourceKeyPrefix();

}
