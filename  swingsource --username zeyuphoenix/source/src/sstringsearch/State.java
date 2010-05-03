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

import java.io.Serializable;

import sstringsearch.internal.Node;
import sstringsearch.internal.Path;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> State.java<br/>
 * <b>Class:</b> State<br/>
 * <b>Created:</b> 18. mars.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   Holds the current state of the FSA. Can be used to continue search
 *   from a given state.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
public class State implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4342772434445932632L;

	/**
	 * Holds the current node
	 */
	private Node currentNode;
	
	/**
	 * Holds the current path
	 */
	private Path path;
	
	/**
	 * Holds number of characters read
	 */
	private int charactersRead;
	
	/**
	 * Holds the current character
	 */
	private int currentCharacter;
	
	/**
	 * Holds the current buffer.
	 */
	private StringBuilder currentBuffer;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param buffer The current buffer
	 * @param currentNode The current node
	 * @param path The current path
	 * @param charactersRead Number of characters read
	 * @param currentCharacter The current character
	 */
	public State (final StringBuilder currentBuffer, final Node currentNode, final Path path, final int charactersRead, final int currentCharacter) {
		this.currentBuffer = currentBuffer;
		this.currentNode = currentNode;
		this.path = path;
		this.charactersRead = charactersRead;
		this.currentCharacter = currentCharacter;
	}
	
	/**
	 * Gets the current buffer
	 */
	public StringBuilder getCurrentBuffer() {
		return currentBuffer;
	}
	
	/**
	 * Gets the current node
	 */
	public Node getCurrentNode() {
		return currentNode;
	}

	/**
	 * Gets the current path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Gets number of characters read
	 */
	public int getCharactersRead() {
		return charactersRead;
	}

	/**
	 * Gets the current character
	 */
	public int getCurrentCharacter() {
		return currentCharacter;
	}
}
