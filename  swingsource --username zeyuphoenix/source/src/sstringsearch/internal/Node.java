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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import sstringsearch.replace.ReplacingHitListener;
import sstringsearch.search.SearchingHitListener;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> Node.java<br/>
 * <b>Class:</b> Node<br/>
 * <b>Created:</b> 5. feb.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   A node used to build the finite state automata.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
public class Node implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2210487901467832989L;

	/**
	 * This nodes neighbours. The graph is directed and only outgoing neighbours are stored.
	 */
	private List<Node> neighbours;
	
	/**
	 * The start node. This is a special node in the finite state automata.
	 */
	private Node startNode;
	
	/**
	 * Each node in the finite state automata can represent several hits.
     * These are stored in a tree-structure so they can be searched 
     * efficently. This is a reference to the tree root.
	 */
	private HitTreeNode hitTreeRoot;
	
	/**
	 * The character that this node represent.
	 */
	private char character;
	
	/**
	 * Constructor. Only used to created the special start node.
	 */
	public Node() {		
		super();
		this.startNode = this;
		this.hitTreeRoot = new HitTreeNode();
		this.neighbours = new ArrayList<Node>();	
	}
	
	/**
	 * Constructor, used to created a node i the finite state automata graph.
	 * 
	 * @param startNode A reference to the special startnode.
	 * @param character The character represented by this node.
	 */
	public Node(final Node startNode, final char character) {		
		this();
		this.startNode = startNode;
		this.character = character;
	}
		
	/**
	 * 
	 * Gets the character represented by this node.
	 * 
	 * @return The character represented by this node.
	 */
	public char getCharacter() {
		return character;
	}		
		
	/**
	 * 
	 * Adds a hit to this node, i.e. the character this node represents
	 * is the last character in a searchterm. 
	 * 
	 * @param term The searchterm.
	 * @param originalTerm The original searchterm, used in case-insensitive searches where searchterms are  converted to lower-case.
	 * @param searchingHitListener  The listener to call when this searchterm is encountered during search.
	 */
	public void addHit(final String term, final String originalTerm, final SearchingHitListener searchingHitListener) {				
		doAddHit(term, originalTerm, searchingHitListener);
	}
	
	/**
	 * 
	 * Adds a hit to this node, i.e. the character this node represents
	 * is the last character in a searchterm. 
	 * 
	 * @param term The searchterm.
	 * @param originalTerm The original searchterm, used in case-insensitive searches where searchterms are converted to lower-case.
	 * @param listener  The listener to call when this searchterm is encountered during search and replace.
	 */
	public void addHit(final String term, final String originalTerm, final ReplacingHitListener listener) {				
		doAddHit(term, originalTerm, listener);
	}
	
	/**
	 * 
	 * Removes a hit from this node. Each node may
	 * be a hitnode for multiple searchterms.
	 * 
	 * 
	 * @param term The searchterm to remove
	 * @return True if the searchterm could be removed, false otherwise.
	 */
	public boolean removeHit(final String term) {
		HitTreeNode currentNode = hitTreeRoot;
		for(int i = term.length() - 1; i >= 0; i--) {			
			final HitTreeNode nextNode = currentNode.getNext(term.charAt(i));
			if(nextNode == null) {
				return false;
			}
			currentNode = nextNode;
		}
		currentNode.setSearchingHitListener(null);
		currentNode.setSearchTerm(null);
		while(!currentNode.isHit() && currentNode.getChildren().size() == 0) {
			final HitTreeNode nextNode = currentNode;
			currentNode = currentNode.getParent();
			if(currentNode == null) {
				break;
			}
			currentNode.getChildren().remove(nextNode);
		}
		return true;
	}
	
	/**
	 * 
	 * Checks if we have encountered a hit during search.
	 * 
	 * @param path The path we currently have traversed through the graph.
	 * @return A list of hits, or null if no hits are encountered. 
	 */
	public List<Hit> checkForHits(final Path path) {
		LinkedList<Hit> hitList = null;						
		HitTreeNode currentNode = hitTreeRoot;		
		final ListIterator<Node> pathIterator = path.iterator();
		for(int i = path.size() - 1; i >= 0; i--) {																
			final HitTreeNode nextNode = currentNode.getNext(pathIterator.next().getCharacter());			
			if(nextNode == null) {
				break;
			}
			else {				
				currentNode = nextNode;
				if(currentNode.isHit()) {
                    if(hitList == null) {
                        hitList = new LinkedList<Hit>();
                    }
					hitList.addFirst(new Hit(currentNode.getSearchingHitListener(), currentNode.getReplacingHitListener(), currentNode.getSearchTerm()));
				}
			}
		}					
		return hitList;
	}
		
	/**
	 * 
	 * Adds a neighbour to this node.
	 * Make sure the neighbours are sorted
	 * with respect to the character they represent.
	 * 
	 * @param newNode The new node to add.
	 */
	public void addNeighbour(final Node newNode) {	
		int index = 0;
		while(index < neighbours.size()) {
			if(neighbours.get(index).getCharacter() - newNode.getCharacter() > 0) {
				break;
			}
			++index;
		}
		neighbours.add(index, newNode);
	}	
		
	/**
	 * 
	 * Gets the next node while traversing the graph. If
	 * any of the neighbours match the given character this
	 * is returned. Otherwise the startnode is returned.
	 * 
	 * @param character The next character read from the reader.
	 * @return A neighbour matching the given character or the startnode if no such node exists.
	 */
	public Node getNext(final char character) {
		final int index = searchNeigbours(character);		
		if(index < 0) {
			return startNode;
		}
		else {
			return neighbours.get(index);
		}
	}
		
	/**
	 * 
	 * Searches the neighbours for nodes matching the characters.
	 * Uses binary search so the neighbours will have to be sorted.
	 * 
	 * @param theChar The character to search for.
	 * @return The index of a matching node or -1 if none is found.
	 */
	public int searchNeigbours(final char theChar) {
		int ret = 0;
		int high = neighbours.size();
		int low = -1;
		int index;
		while(high - low > 1) {
			index = (low + high) >>> 1;
			if(neighbours.get(index).getCharacter() - theChar < 0) {
				low = index;
			}
			else {
				high = index;
			}
		}
		if(high == neighbours.size() || neighbours.get(high).getCharacter() - theChar != 0) { 
			ret = -1;
		}
		else {
			ret = high;
		}
		return ret;
	}
    
    /**
     * 
     * Returns true if this node represents a hit.
     * 
     * @return True if this node represents a hit, false otherwise
     */
    boolean isHit() {
        return hitTreeRoot.hasGotChildren();
    }
    
    /**
	 * 
	 * Adds a hit to this node, i.e. the character this node represents
	 * is the last character in a searchterm. 
	 * 
	 * @param term The searchterm.
	 * @param originalTerm The original searchterm, used in case-insensitive searches where searchterms are converted to lower-case.
	 * @param listener  The listener to call when this searchterm is encountered during search or replace.
	 */
	private void doAddHit(final String term, final String originalTerm, final Object listener) {				
		HitTreeNode currentNode = hitTreeRoot;
		for(int i = term.length() - 1; i >= 0; i--) {			
			HitTreeNode nextNode = currentNode.getNext(term.charAt(i));
			if(nextNode == null) {
				nextNode = new HitTreeNode(term.charAt(i), currentNode);
				currentNode.addChild(nextNode);				
			}
			currentNode = nextNode;
			if(i == 0) {
				currentNode.setSearchTerm(originalTerm);
				if(listener instanceof SearchingHitListener) {
					currentNode.setSearchingHitListener((SearchingHitListener)listener);
				}
				else if(listener instanceof ReplacingHitListener) {
					currentNode.setReplacingHitListener((ReplacingHitListener)listener);
				}
			}			
		}
	}
    
}
