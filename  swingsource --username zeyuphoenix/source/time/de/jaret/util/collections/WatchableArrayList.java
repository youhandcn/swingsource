/*
 *  File: WatchableArrayList.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Peter Kliem
 * @version $id$
 */
public class WatchableArrayList implements List {
    private List<ListWatcher> _listeners;
    private List _content;

    /**
     * 
     */
    public WatchableArrayList() {
        _content = new ArrayList();
    }

    public void addListWatcher(ListWatcher watcher) {
        if (_listeners == null) {
            _listeners = new ArrayList<ListWatcher>();
        }
        _listeners.add(watcher);
    }

    public void removeListWatcher(ListWatcher watcher) {
        if (_listeners != null) {
            _listeners.remove(watcher);
        }
    }

    private void fireElementAdded(Object o) {
        if (_listeners != null) {
            for (ListWatcher watcher : _listeners) {
                watcher.elementAdded(o);
            }
        }
    }

    private void fireElementRemoved(Object o) {
        if (_listeners != null) {
            for (ListWatcher watcher : _listeners) {
                watcher.elementRemoved(o);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#size()
     */
    public int size() {
        return _content.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {
        return _content.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#contains(java.lang.Object)
     */
    public boolean contains(Object o) {
        return _content.contains(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#iterator()
     */
    public Iterator iterator() {
        return _content.iterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#toArray()
     */
    public Object[] toArray() {
        return _content.toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#toArray(java.lang.Object[])
     */
    public Object[] toArray(Object[] a) {
        return _content.toArray(a);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(Object o) {
        boolean result = _content.add(o);
        fireElementAdded(o);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#remove(java.lang.Object)
     */
    public boolean remove(Object o) {
        boolean wasInList = _content.contains(o);
        boolean result = _content.remove(o);
        if (wasInList) {
            fireElementRemoved(o);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#containsAll(java.util.Collection)
     */
    public boolean containsAll(Collection c) {
        return _content.containsAll(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#addAll(java.util.Collection)
     */
    public boolean addAll(Collection c) {
        // TODO listener
        return _content.addAll(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    public boolean addAll(int index, Collection c) {
        // TODO listener
        return _content.addAll(index, c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#removeAll(java.util.Collection)
     */
    public boolean removeAll(Collection c) {
        // TODO listener
        return _content.removeAll(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#retainAll(java.util.Collection)
     */
    public boolean retainAll(Collection c) {
        // TODO listener
        return _content.retainAll(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#clear()
     */
    public void clear() {
        // TODO listener
        _content.clear();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#get(int)
     */
    public Object get(int index) {
        return _content.get(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#set(int, java.lang.Object)
     */
    public Object set(int index, Object element) {
        // TODO listener
        return _content.set(index, element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#add(int, java.lang.Object)
     */
    public void add(int index, Object element) {
        _content.add(index, element);
        fireElementAdded(element);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#remove(int)
     */
    public Object remove(int index) {
        Object o = _content.remove(index);
        fireElementRemoved(o);
        return o;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#indexOf(java.lang.Object)
     */
    public int indexOf(Object o) {
        return _content.indexOf(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    public int lastIndexOf(Object o) {
        return _content.lastIndexOf(o);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#listIterator()
     */
    public ListIterator listIterator() {
        return _content.listIterator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#listIterator(int)
     */
    public ListIterator listIterator(int index) {
        return _content.listIterator(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.List#subList(int, int)
     */
    public List subList(int fromIndex, int toIndex) {
        return _content.subList(fromIndex, toIndex);
    }

}
