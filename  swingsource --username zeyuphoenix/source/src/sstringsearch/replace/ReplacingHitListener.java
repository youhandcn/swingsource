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
package sstringsearch.replace;

import java.io.Serializable;

/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> ReplacingHitListener.java<br/>
 * <b>Class:</b> ReplacingHitListener<br/>
 * <b>Created:</b> 13. mar. 2008<br/>
 * </br>
 * <p>
 * <b>Description:</b>
 * </p>
 * <p>
 * A HitListener that is notified upon hits when calling StringSearch.searchAndReplace()
 * The searchterm found is replaced in the result with the String returned from the replace() method.
 * Implementations of this class should be fully serializable if one wants to serialize the
 * finite state automata graph.
 * 
 * </p>
 * 
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision: 1.2 $
 */
public interface ReplacingHitListener extends Serializable {
            
    /**
     * 
     * This method is called whenever the assosiated string is encountered in the input.
     * In the result the encountered String is replaced by the String returned from this method
     * 
     * @param stringToReplace The String found in input, will be replaced in output
     * @param callbackArguments Optional callback arguments.
     * @return The String that should replace the encountered Stirng in the output.
     */
    String replace(final String stringToReplace, Object... callbackArguments);
    
}
