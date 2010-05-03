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

import java.io.Serializable;

/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> SearchingHitListener.java<br/>
 * <b>Class:</b> SearchingHitListener<br/>
 * <b>Created:</b> 13. okt.. 2006<br/>
 * </br>
 * <p>
 * <b>Description:</b>
 * </p>
 * <p>
 * Classes implementing this interface can be notified when StringSearch finds a hit.
 * Implementations of this class should be fully serializable if one wants to serialize the
 * finite state automata graph.
 * 
 * </p>
 * 
 * 
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision: 1.2 $
 */
public interface SearchingHitListener extends Serializable {

     
    /**
     * 
     * Called by StringSearch upon a hit.
     * 
     * @param startIndex The startindex of the hit.
     * @param endIndex The endindex of the hit
     * @param string The string that was found. 
     * @param callbackArguments Callback arguments.
     * 
     * @return true if the search should continue
     * 
     */
    boolean foundAt(long startIndex, long endIndex, String string, Object... callbackArguments);
               
    
}
