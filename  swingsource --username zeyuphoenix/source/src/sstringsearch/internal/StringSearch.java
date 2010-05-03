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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import sstringsearch.State;
import sstringsearch.StringSearchFactory;
import sstringsearch.replace.Replacer;
import sstringsearch.replace.ReplacingHitListener;
import sstringsearch.search.Searcher;
import sstringsearch.search.SearchingHitListener;


/**
 * 
 * <b>Project:</b> sstringsearch<br/>
 * <b>File:</b> StringSearch.java<br/>
 * <b>Class:</b> StringSearch<br/>
 * <b>Created:</b> 5. feb.. 2008<br/>
 * </br>
 * <b>Description:</b> 
 * <p>
 *   Performs stringsearch using a finite state automata.
 * </p>
 *   
 * @author Kristian Andersen <a href="http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=" onclick="window.open('http://mailhide.recaptcha.net/d?k=01ykugPKy1nAyBlPPSI6U5Lw==&amp;c=4vn0YwJOVAH-7MU_xp9x2nmncHuzR2CepX1zAxqpppA=', '', 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=300'); return false;" title="Reveal this e-mail address">Email</a>
 * @version $Revision$
 *
 */
public final class StringSearch implements Serializable, Searcher, Replacer {
	
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5625428331304169009L;
	
	/**
	 * Lock object for synchronization.
	 */
	private transient Object lock = new Object();
	    	
    /**
     * The number of nodes that need to be stored.
     */
    private int pathLength;
    
    /**
     * Defines if the search should be case-sensitive.
     */
    private boolean caseSensitive;

    /**
     * A reference to the special startnode in the finite state automata.
     */
    private Node startNode;

    /**
     * The nodes in the finite state automata.
     */
    private List<Node> nodes;
    
    /**
     * The number of searchterms.
     */
    private long nbrOfTerms;
        
    /**
     * The longest defined searchterm, used to determine necessary buffersize 
     */
    private int longestTerm = 0;

    /**
     * 
     * Constructor
     * 
     * @param caseSensitive Should the search be case sensitive.
     */
    public StringSearch(final boolean caseSensitive) {
    	super();
        this.startNode = new Node();
        this.nodes = new ArrayList<Node>();
        this.nbrOfTerms = 0;
        this.pathLength = Integer.MIN_VALUE;
        this.caseSensitive = caseSensitive;
    }
    
    /**
     * 
     * @see sstringsearch.search.Searcher#addSearchTerm(java.lang.String, sstringsearch.HitListener)
     */
    public void addSearchTerm(final String term, final SearchingHitListener listener) throws IllegalArgumentException {
    	doAddSearchTerm(term, listener);
    }
    
    
    /**
     * @see sstringsearch.replace.Replacer#addSearchTerm(java.lang.String, sstringsearch.replace.ReplacingHitListener)
     */
    public void addSearchTerm(final String term, final ReplacingHitListener listener) throws IllegalArgumentException {
    	doAddSearchTerm(term, listener);
    }
    
    
    /**
     * @see sstringsearch.search.Searcher#removeSearchTerm(java.lang.String)
     */
    public void removeSearchTerm(final String term) {   	    	    	    	
	    if(term == null) {
	    	throw new IllegalArgumentException(ErrorMessages.UNABLE_TO_REMOVE + term);
	    }    	        	           
	    final int index = searchNodes(term.charAt(term.length() - 1));	    
	    if(index < 0) {
	    	throw new IllegalArgumentException(ErrorMessages.UNABLE_TO_REMOVE + term);
	    }
	    final Node currentNode = nodes.get(index);
	    synchronized (lock) {    
	        final boolean res = currentNode.removeHit(term);
	        if(!res) {
	            throw new IllegalArgumentException(ErrorMessages.UNABLE_TO_REMOVE + term);
	        }  	        
	    }        
	    --nbrOfTerms;    	
    }
   
    
    /**
     * @see sstringsearch.search.Searcher#search(java.io.Reader, java.lang.Object[])
     */
    public State search(final Reader reader, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException {     
    	final State state = new State(new StringBuilder(longestTerm), startNode, new Path(pathLength), 0, -1);
    	return search(reader, null, state, false, callbackArguments);
    }
    
        
    /**
     * @see sstringsearch.search.Searcher#search(java.io.Reader, sstringsearch.State, java.lang.Object[])
     */
    public State search(final Reader input, final State state, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException {      	    	
    	return search(input, null, state, false, callbackArguments);
    } 
           
    
    /*
     * @see net.sourceforge.sstringsearch.SearchAndReplace#searchAndReplace(java.io.Reader, java.io.Writer, java.lang.Object[])
     */
    public State searchAndReplace(final Reader input, final Writer result, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException {     
    	final State state = new State(new StringBuilder(longestTerm), startNode, new Path(pathLength), 0, -1);
    	return search(input, result, state, true, callbackArguments);
    }
    
    
    /**
     * @see sstringsearch.replace.Replacer#searchAndReplace(java.io.Reader, java.io.Writer, sstringsearch.State, java.lang.Object[])
     */
    public State searchAndReplace(final Reader input, final Writer result, final State state, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException {     
    	return search(input, result, state, true, callbackArguments);
    }       
    
    /**
     * @deprecated Use {@linkplain StringSearchFactory} instead
     * @param inputStream
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static StringSearch load(final InputStream inputStream) throws IOException, ClassNotFoundException {
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
     * @deprecated Use {@linkplain StringSearchFactory} instead
     * @param filename
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static StringSearch load(final String filename) throws IOException, ClassNotFoundException {
    	StringSearch ret = null;
        final FileInputStream fis = new FileInputStream(filename);
        try {
            ret = load(fis);
        }
        finally {
            if(fis != null) {
                fis.close();
            }
        }
        return ret;
    }

    
    
    /**
     * 
     * Serialize the finite state automata to an outputstream. Useful to avoid building a static finite state automata for
     * each run.
     * 
     * @param outputStream The outputstream to write finite state automata to.
     * @throws IOException If something goes wrong while serializing the class.
     */
    public void save(final OutputStream outputStream) throws IOException {
    	synchronized (lock) {
    		ObjectOutputStream objOutputStream = null;
    		try {
    			objOutputStream = new ObjectOutputStream(outputStream);
    			objOutputStream.writeObject(this);
    		}
    		finally {
    			if(objOutputStream != null) {
    				objOutputStream.close();
    			}
    		}    
    	}
    }

    /**
     * 
     * Convience-method to save a finite state automata to file.
     * 
     * @param filename The file to write finite state automata to.
     * @throws IOException If something goes wrong while serializing the class.
     */
    public void save(final String filename) throws IOException {
        final FileOutputStream fos = new FileOutputStream(filename);
		try {
			save(fos);
		}
		finally {
			if(fos != null) {
				fos.close();
			}
		}        
    }

    /**
     * 
     * Get the number of defined searchterms.
     * 
     * @return The number of defined searchterms.
     */
    public long getNumberOfSearchTerms() {
        return nbrOfTerms;
    }
        
    public void setLock(Object lock) {
    	this.lock = lock;
    }
    
    /**
     * 
     * Utility-method to add a node. Makes sure the list of nodes are sorted.
     * 
     * @param newNode The new node to add.
     */
    private void addNode(final Node newNode) {
    	int index = 0;
		while(index < nodes.size()) {
			if(nodes.get(index).getCharacter() - newNode.getCharacter() > 0) {
				break;
			}
			++index;
		}
		nodes.add(index, newNode);
    }
  
    /**
     * 
     * Searches the nodes of the finite state automata.
     * Uses binary search requiring the nodes to be sorted. 
     * 
     * @param theChar The character to search.
     * @return The index of a matching node or -1 if no such node exists.
     */
    private int searchNodes(final char theChar) {
		int ret = 0;
		int high = nodes.size();
		int low = -1;
		int index;
		while(high - low > 1) {
			index = (low + high) >>> 1;
			if(nodes.get(index).getCharacter() - theChar < 0) {
				low = index;
			}
			else {
				high = index;
			}
		}
		if(high == nodes.size() || nodes.get(high).getCharacter() - theChar != 0) { 
			ret = -1;
		}
		else {
			ret = high;
		}
		return ret;
	}
    
    /**
     * 
     * Implements the algorithm that does the actual searching.
     * 
     * @param input A reader that provides the data to search through.
     * @param result A Writer where the result is written to 
     * @param state The state to start from.
     * @param doReplace Flag indicating search / search &amp; replace
     * @param callbackArguments Arguments that may be included when calling the listener.
     * @return The state of the finite state automata when finished. This can be used to continue searching from a previous state
     * @throws IOException If there is a problem reading the data.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     * @throws IllegalStateException If the FSA is not ready to perform search, e.g. if no searchterms are defined.
     */
    private State search(final Reader input, final Writer result, final State state, final boolean doReplace, final Object... callbackArguments) throws IOException, IllegalArgumentException, IllegalStateException {      	    	    	

    	if(input == null) {
            throw new IllegalArgumentException(ErrorMessages.DATA_NULL);
        }
    	else if(doReplace && result == null) {
    		throw new IllegalArgumentException(ErrorMessages.WRITER_NULL);
    	}
    	else if(state == null) {
            throw new IllegalArgumentException(ErrorMessages.STATE_NULL);
        }
    	else if(state.getCurrentNode() == null) {
            throw new IllegalArgumentException(ErrorMessages.STATE_NODE_NULL);
        }
    	else if(state.getPath() == null) {
            throw new IllegalArgumentException(ErrorMessages.STATE_PATH_NULL);
        }    	    	
    	else if(nbrOfTerms < 1) {
            throw new IllegalStateException(ErrorMessages.NO_SEARCHTERMS);
        }    
    	
    	final StringBuilder buffer = state.getCurrentBuffer();
    	final Path path = state.getPath();
        Node currentNode = state.getCurrentNode();    	    	    	
        int charactersRead = state.getCharactersRead();
        int currentCharacter = state.getCurrentCharacter();
        while((currentCharacter = input.read()) != -1) {        	        	
        	++charactersRead;
        	char character = (char) currentCharacter;
            if(caseSensitive == false) {
                character = Character.toLowerCase(character);
            }                    	            
            final Node nextNode = currentNode.getNext(character);            
            if(nextNode == startNode) {                    	        		
            	if(doReplace && buffer.length() > 0) {
        			result.write(buffer.toString());
        			buffer.setLength(0);
        		}
            	path.clear();
        		currentNode = startNode.getNext(character);        		
        		if(currentNode != startNode) {        			
        			path.add(currentNode); 
        			if(doReplace) {
                		buffer.append(character);
                	}
        		}    
        		else {    
        			if(doReplace) {        				
                		if(buffer.length() > 0) {
                			result.write(buffer.toString());
                		}
                		result.write(character);
                		buffer.setLength(0);
                	}
        		}
            }
            else {
            	currentNode = nextNode;        		
            	path.add(currentNode);  
            	if(doReplace) {
            		buffer.append(character);
            	}
            }                           
        	final List<Hit> hits = currentNode.checkForHits(path);    		
        	if(hits != null) {
        	    for(Hit hit : hits) {
        	        final int begin = charactersRead - hit.getSearchTerm().length();
        	        if(doReplace) {        	  
        	        	final ReplacingHitListener listener = hit.getReplacingHitlistener();
        	        	if(listener == null) {
        	        		throw new IllegalStateException(ErrorMessages.WRONG_LISTENER + hit.getSearchTerm());
        	        	}
        	        	result.write(listener.replace(hit.getSearchTerm(), callbackArguments));
        	        	buffer.setLength(0);
        	        }
        	        else {
        	        	final SearchingHitListener listener = hit.getListener();
        	        	if(listener == null) {
        	        		throw new IllegalStateException(ErrorMessages.WRONG_LISTENER + hit.getSearchTerm());
        	        	}
        	        	final boolean doContinue = listener.foundAt(begin, charactersRead, hit.getSearchTerm(), callbackArguments);        		
        	        	if(!doContinue) {
        	        		return new State(buffer, currentNode, path, charactersRead, currentCharacter);
        	        	}
        	        }
        	    }
            }
        }    
        return new State(buffer, currentNode, path, charactersRead, currentCharacter);
    }
    
    /**
     * 
     * Adds a new searchterm
     * 
     * @param term The searchterm to add.
     * @param listener The listener called when this searchterm is encountered.
     * @throws IllegalArgumentException
     */
    private void doAddSearchTerm(final String term, final Object listener) throws IllegalArgumentException {    	
        if(listener == null) {
            throw new IllegalArgumentException(ErrorMessages.LISTENER_NULL);
        }
        if(term == null) {
            throw new IllegalArgumentException(ErrorMessages.SEARCHTERM_NULL);
        }
        if(term.length() == 0) {
            throw new IllegalArgumentException(ErrorMessages.SEARCHTERM_EMPTY);
        }
        String newTerm = term;
        if(caseSensitive == false) {
            newTerm = newTerm.toLowerCase();
        }
        
        Node currentNode = startNode;
        
        synchronized (lock) {
            if(newTerm.length() > pathLength) {
                pathLength = newTerm.length();
            }	               	                        
	    	for(int i = 0; i < term.length(); i++) {
	    		final int index = searchNodes(newTerm.charAt(i));    		
	       		if(index >= 0) {    			       			
	       			final Node tempNode = nodes.get(index);      
	    			final int index2 = currentNode.searchNeigbours(tempNode.getCharacter());    			
	    			if(index2 < 0) {
	    				currentNode.addNeighbour(tempNode);
	    			}
	    			currentNode = tempNode;
	    		}
	    		else {    			
	    			final Node newNode = new Node(startNode, newTerm.charAt(i));
	    			addNode(newNode);
	    			currentNode.addNeighbour(newNode);   			
	    			currentNode = newNode;
	    		}
	    		if(i == newTerm.length() - 1) {
	    			if(listener instanceof SearchingHitListener) {
	    				currentNode.addHit(newTerm, term, (SearchingHitListener)listener);
	    			}
	    			else if(listener instanceof ReplacingHitListener) {
	    				currentNode.addHit(newTerm, term, (ReplacingHitListener)listener);
	    			}
	    		}
	    	}	    	
    	}
        if(term.length() > longestTerm) {
        	longestTerm = term.length();
        }
    	++nbrOfTerms;
    }
        
    /**
     * 
     * Holds error messages.
     * 
     */
    private final static class ErrorMessages {
        
    	public static final String STATE_NULL = "State can not be null";
    	
    	public static final String STATE_PATH_NULL = "state.getPath() can not be null";

		public static final String STATE_NODE_NULL = "state.getCurrentNode() can not be null";
		
		public final static String DATA_NULL = "Can not search: null";
		
		public final static String WRITER_NULL = "Can not write result to: null";
        
        public final static String SEARCHTERM_EMPTY = "The searchterm must contain at least 1 character";

        public final static String SEARCHTERM_NULL = "The searchterm can not be null";

        public final static String LISTENER_NULL = "The listener can not be null";
        
        public final static String WRONG_LISTENER = "No listener found. Use HitListener for search() and ReplacingHitListener for searchAndReplace()";
        
        public final static String NO_SEARCHTERMS = "No searchterms present";

        public final static String UNABLE_TO_REMOVE = "Could not remove: ";
    }
}
