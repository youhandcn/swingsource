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
package sstringsearch.search;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;

import sstringsearch.State;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> Searcher.java<br/>
 * <b>Class:</b> Searcher<br/>
 * <b>Created:</b> 6. mai. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   For performing stringsearch.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 */
public interface Searcher extends Serializable {

	/**
     * 
     * Adds a new searchterm with a HitListener.
     * <b>Use this method when you are using StringSearch.search()</b>
     * 
     * @param term The searchterm to add.
     * @param listener The HitListener called when this searchterm is encountered.
     * @throws IllegalArgumentException
     * @see HitListener
     */
    public void addSearchTerm(final String term, final SearchingHitListener listener) throws IllegalArgumentException;
    
    
    /**
     * 
     * Searches through character data provided by the reader.
     * When one of the defined searchterms is encountered the corresponding listener 
     * is called.
     * <b>Requires that all searchterms are registered with a HitListener</b>
     * 
     * @param reader A reader that provides the data to search through. 
     * @param callbackArguments Arguments that may be included when calling the listener.
     * @return The state of the finite state automata when finished. This can be used to continue searching from a previous state
     * @throws IOException If there is a problem reading the data.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     * @throws IllegalStateException If the FSA is not ready to perform search, e.g. if no searchterms are defined.
     */
    public State search(final Reader reader, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException; 

    
    
    /**
     * 
     * Searches through character data provided by the reader.
     * When one of the defined searchterms is encountered the corresponding listener 
     * is called.
     * <b>Requires that all searchterms are registered with a HitListener</b>
     * 
     * @param reader A reader that provides the data to search through. 
     * @param state The state to start from.
     * @param callbackArguments Arguments that may be included when calling the listener.
     * @return The state of the finite state automata when finished. This can be used to continue searching from a previous state
     * @throws IOException If there is a problem reading the data.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     * @throws IllegalStateException If the FSA is not ready to perform search, e.g. if no searchterms are defined.
     */
    public State search(final Reader input, final State state, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException;      	    	    	

    
    /**
     * 
     * Removes a searchterm.
     * 
     * @param term The searchterm to remove.
     */
    public void removeSearchTerm(final String term);
    
    /**
     * 
     * Serialize the finite state automata to an outputstream. Useful to avoid building a static finite state automata for
     * each run.
     * 
     * @param outputStream The outputstream to write finite state automata to.
     * @throws IOException If something goes wrong while serializing the class.
     */
    public void save(final OutputStream outputStream) throws IOException;

    /**
     * 
     * Convience-method to save a finite state automata to file.
     * 
     * @param filename The file to write finite state automata to.
     * @throws IOException If something goes wrong while serializing the class.
     */
    public void save(final String filename) throws IOException;
    
    /**
     * 
     * Get the number of defined searchterms.
     * 
     * @return The number of defined searchterms.
     */
    public long getNumberOfSearchTerms();
    
}
