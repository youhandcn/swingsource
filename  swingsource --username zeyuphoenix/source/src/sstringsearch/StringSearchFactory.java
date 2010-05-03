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
package sstringsearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import sstringsearch.internal.StringSearch;
import sstringsearch.replace.Replacer;
import sstringsearch.search.Searcher;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> StringSearchFactory.java<br/>
 * <b>Class:</b> StringSearchFactory<br/>
 * <b>Created:</b> 7. mai. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   The main source for obtaining objects for doing both search and search&amp; replace.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 */
@SuppressWarnings("deprecation")
public class StringSearchFactory{

	/**
	 * 
	 * Returns a casesensitive Searcher, capable of doing stringsearch.
	 * 
	 * @return A Searcher.
	 */
	public static Searcher getSearcher() {
		Searcher search = getSearcher(true);
		return search;
	}
	
	/**
	 * 
     * Returns a  Searcher, capable of doing stringsearch.
	 * @param caseSensitive Decides if the Searcher is case sensitive.
	 * @return A Searcher.
	 */
	public static Searcher getSearcher(boolean caseSensitive) {
		Searcher search = new StringSearch(caseSensitive);
		return search;
	}
	
	/**
	 * 
	 * Returns a casesensitive Replacer, capable of doing search and replace.
	 * 
	 * @return A Replacer.
	 */
	public static Replacer getReplacer() {
		Replacer searchAndReplace = getReplacer(true);
		return searchAndReplace;
	}
	
	/**
	 * 
	 * Returns a Replacer, capable of doing search and replace.
	 * 
	 * @param caseSensitive Decides if the Replacer is case sensitive.
	 * @return A Replacer.
	 */
	public static Replacer getReplacer(boolean caseSensitive) {
		Replacer searchAndReplace = new StringSearch(caseSensitive);
		return searchAndReplace;
	}
	
    /**
     * 
     * Loads a serialized Searcher from InputStream.
     * 
     * @param inputStream The InputStream to read from.
     * @return A deserialized A Searcher
     * @throws IOException of anything goes wrong reading the serialized object.
     * @throws ClassNotFoundException Will probably never happen :-)
     */
    public static Searcher loadSearcher(final InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objInputStream = null;
        StringSearch ret = null;
        try {
            objInputStream = new ObjectInputStream(inputStream);
            ret = (StringSearch) objInputStream.readObject();
            ret.setLock(new Object());
        }
        finally {
            if(objInputStream != null) {
                objInputStream.close();
            }
        }        
        return ret;
    }
    
    /**
     * 
     * Loads a serialized Replacer from InputStream.
     * 
     * @param inputStream The InputStream to read from.
     * @return A deserialized A Replacer
     * @throws IOException of anything goes wrong reading the serialized object.
     * @throws ClassNotFoundException Will probably never happen :-)
     */
    public static Replacer loadReplacer(final InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objInputStream = null;
        StringSearch ret = null;
        try {
            objInputStream = new ObjectInputStream(inputStream);
            ret = (StringSearch) objInputStream.readObject();
            ret.setLock(new Object());
        }
        finally {
            if(objInputStream != null) {
                objInputStream.close();
            }
        }        
        return ret;
    }	
}
