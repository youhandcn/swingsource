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
import java.util.ArrayList;
import java.util.List;

import sstringsearch.replace.ReplacingHitListener;
import sstringsearch.search.SearchingHitListener;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> HitTreeNode.java<br/>
 * <b>Class:</b> HitTreeNode<br/>
 * <b>Created:</b> 5. feb.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   A node used to represent a searchterm in the underlying datastructure.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
class HitTreeNode implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2550718404539421775L;

	/**
	 * The character this node represents.
	 */
	private char character;
	
	/**
	 * A searching hit listener.
	 */
	private SearchingHitListener searchingHitListener;
		
	/**
	 * A replacing hit listener.
	 */
	private ReplacingHitListener replacingHitListener;
	
	/**
	 * The original searchterm, may differ from the actual searchterm on case insensitive searches.
	 */
	private String searchTerm;
	
	/**
	 * This nodes parent.
	 */
	private HitTreeNode parent;
	
	/**
	 * This nodes children.
	 */
	private List<HitTreeNode> children;
	
	/**
	 * Constructor. Only used to create the root node.
	 */
	HitTreeNode() {
		children = new ArrayList<HitTreeNode>();
	}
	
	/**
     * 
     * Constructor for interal nodes and leaves.
     * 
	 * @param character The character that this node represents.
	 * @param parent This nodes parent.
	 */
	HitTreeNode(final char character, final HitTreeNode parent) {
		this();
		this.character = character;
		this.parent = parent;
	}
		
	/**
     * 
     * Gets the character that this node represent.
     * 
	 * @return The character that this node represent
	 */
	char getCharacter() {
		return character;
	}
	
	/**
     * 
     * Gets this nodes parent.
     * 
	 * @return This nodes parent.
	 */
	HitTreeNode getParent() {
		return parent;
	}
	
	/**
     * 
     * Gets the SearchingHitListener assosiated with this node.
     * Returns null if no SearchingHitListener is set for this node
     * 
	 * @return The SearchingHitListener assosiated with this node or null if no SearchingHitListener is set.
	 */
	SearchingHitListener getSearchingHitListener() {
		return searchingHitListener;
	}

	/**
     * 
     * Sets the SearchingHitListener assosiated with this node.
     * 
	 * @param listener The SearchingHitListener assosiated with this node.
	 */	
    void setSearchingHitListener(final SearchingHitListener searchingHitListener) {
		this.searchingHitListener = searchingHitListener;
	}
    
    /**
     * 
     * Gets the ReplacingHitListener assosiated with this node.
     * Returns null if no ReplacingHitListener is set for this node
     * 
	 * @return The ReplacingHitListener assosiated with this node or null if no ReplacingHitListener is set.
	 */
    ReplacingHitListener getReplacingHitListener() {
		return replacingHitListener;
	}

    /**
     * 
     * Sets the ReplacingHitListener assosiated with this node.
     * 
	 * @param listener The HitListener assosiated with this node.
	 */	
	void setReplacingHitListener(final ReplacingHitListener replacingHitListener) {
		this.replacingHitListener = replacingHitListener;
	}
	    
	/**
     * 
     * Gets the searchterm assosiated with this nodes.
     * 
	 * @return The searchterm assosiated with this nodes.
	 */
	String getSearchTerm() {
		return searchTerm;
	}
	
	/**
     * 
     * Returns true if this node represents a hit.
     * (if a searchterm and hitlistener is defined)
     * 
	 * @return True if this node represents a hit.
	 */
	boolean isHit() {
		return (searchingHitListener != null || replacingHitListener != null) && searchTerm != null;
	}
	
	/**
     * 
     * Sets the searchterm assosiated with this node.
     * 
	 * @param searchTerm The searchterm to assosiated with this node.
	 */
	void setSearchTerm(final String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	/**
     * 
     * Adds a child to this node. Ensures that the children 
     * are sorted with respect to the character they represent.
     * 
	 * @param newNode The new node to add.
	 */
	void addChild(final HitTreeNode newNode) {
		int index = 0;
		for(HitTreeNode node : children) {		
			final int res = node.getCharacter() - newNode.getCharacter();
			if(res > 0) {
				break;
			}
			++index;
		}
		children.add(index, newNode);
	}
	
	/**
     * 
     * Gets the next node in the tree when traversing from the root.
     * Returns a child which represents the provided character or null
     * if such a child does not exist.
     * 
	 * @param nextChar The next character read from the stream.
	 * @return A matching child or null if no matching child exists.
	 */
	HitTreeNode getNext(final char nextChar) {
		final int index = searchChildren(nextChar);
		if(index >= 0) {
			return children.get(index);
		}
		else{
			return null;
		}	
	}	
		
	/**
     * 
     * Gets a list containing this nodes children.
     * 
	 * @return A list containing this nodes children.
	 */
	List<HitTreeNode> getChildren() {
		return children;
	}	
	
	/**
     * 
     * Searches all children for a node matching the given character.
     * Returns the childs index in the list or -1 if no matching child is found.
     * 
	 * @param theChar The character to look for.
	 * @return The index of a child matching the index or -1 if no matching child is found.
	 */
	int searchChildren(final char theChar) {
		int ret = 0;
		int high = children.size();
		int low = -1;
		int index;
		while(high - low > 1) {
			index = (low + high) >>> 1;
			if(children.get(index).getCharacter() - theChar < 0) {
				low = index;
			}
			else {
				high = index;
			}
		}
		if(high == children.size() || children.get(high).getCharacter() - theChar != 0) { 
			ret = -1;
		}
		else {
			ret = high;
		}
		return ret;
	}
	
	/**
	 * 
	 * Returns true if this node has got children, false otherwise.
	 * 
	 * @return True if this node has got children, false otherwise.
	 */
	boolean hasGotChildren() {
		return children.size() > 0;				
	}
}
