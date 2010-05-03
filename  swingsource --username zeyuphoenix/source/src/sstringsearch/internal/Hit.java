/*
 * Copyright 2005-2008 Kristian Andersen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package sstringsearch.internal;

import java.io.Serializable;

import sstringsearch.replace.ReplacingHitListener;
import sstringsearch.search.SearchingHitListener;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> Hit.java<br/>
 * <b>Class:</b> Hit<br/>
 * <b>Created:</b> 5. feb.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   Utilityclass representing a search hit.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 */
public class Hit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5934458738261910469L;

	/**
	 * Holds the searchterm that was found.
	 */
	private String searchTerm;
	
	/**
	 * Holds a SearchingHitListener assosiated with the hit.
	 */
	private SearchingHitListener searchingHitListener;

	/**
	 * Holds a ReplacingHitListener assosiated with the hit.
	 */
	private ReplacingHitListener replacingHitListener;
	
	/**
     * 
     * Constructor
     * 
	 * @param listener The HitListener assosiated with the searchterm.
	 * @param replacingHitListener The ReplacingHitListener assosiated with the searchterm.
	 * @param searchTerm The searchterm that was found.
	 */
	Hit(SearchingHitListener listener, ReplacingHitListener replacingHitListener, String searchTerm) {
		this.searchingHitListener = listener;
		this.replacingHitListener = replacingHitListener;
		this.searchTerm = searchTerm;
	}
	
	/**
	 * @return Gets the searchterm assosiated with this hit.
	 */
	public String getSearchTerm() {
		return searchTerm;
	}
	
	/**	  
	 * @return Gets the ReplacingHitListener assosiated with the hit.
	 */
	public ReplacingHitListener getReplacingHitlistener() {
		return replacingHitListener;
	}
			
	/**
	 * @return Gets the SearchingHitListener assosiated with the hit.
	 */
	public SearchingHitListener getListener() {
		return searchingHitListener;
	}
}
