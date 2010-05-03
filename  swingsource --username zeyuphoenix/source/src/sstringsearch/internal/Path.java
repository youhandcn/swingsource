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
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> Path.java<br/>
 * <b>Class:</b> Path<br/>
 * <b>Created:</b> 5. feb.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   Keeps track of nodes while traversing the graph.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
public class Path implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -4731131232763850112L;

	/**
	 * A list holding the nodes that have been visisted.
	 */
	private LinkedList<Node> thePath;
	
    /**
     * Defines how many nodes should be kept in the list.
     */
    private int length;
    
	
	/**
     * 
     * Constructor.
     * 
	 * @param length How many nodes should be kept in the list.
	 */
	public Path(int length) {
		this.thePath = new LinkedList<Node>();
        this.length = length;
	}
	
	/**
     * 
     * Adds another node to the list.
     * If the list has reached maximum length, the
     * first entry is removed. (FILO)
     * 
	 * @param newNode A visted node that should be added to the list.
	 */
	public void add(final Node newNode) {
		thePath.addFirst(newNode);
		if(thePath.size() > length) {
			thePath.removeLast();
		}
	}
	
	/**
	 * Resets the list, removes all nodes. 
	 */
	public void clear() {		
		thePath.clear();
	}
	
	/**
     * 
     * Returns the size of the list.
     * 
	 * @return The size of the list.
	 */
	int size() {
		return thePath.size();
	}
	
	/**
     * 
     * Returns an iterator that can be
     * used to iterate over the nodes in the list.
     * 
	 * @return An iterator that can be used to iterate over the nodes in the list.
	 */
	ListIterator<Node> iterator() {
	    return thePath.listIterator();		
	}
	
}
