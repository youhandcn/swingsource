package de.wannawork.jcalendar;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

/*
 * Copyright (c) 2003, Bodo Tasche (http://www.wannawork.de)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this 
 *       list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this 
 *       list of conditions and the following disclaimer in the documentation and/or other 
 *       materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on Apr 14, 2004
 * @author David Freels
 */
public class JCalendarCellEditor implements TableCellEditor {
  
  private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();
  //private boolean editing = false;
  private Date originalValue;
  private JCalendarComboBox calendar;
  
  public JCalendarCellEditor(JCalendarComboBox cal) {
    calendar = cal;
  }

  /**
   * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
   */
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    //Return the JCalendarComboBox
    if (value == null) {
      return calendar;
    }
    
    //Set the value of the JCalendarComboBox with the passed in Date
    if (value instanceof Date) {
      calendar.setDate((Date)value);
    }
    else {
      Date d = new Date();
      calendar.setDate(d);
    }
    
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    originalValue = calendar.getDate();
    //editing = true;
    
    //Make sure the component width matches the cell width
    calendar.setSize(table.getColumnModel().getColumn(column).getWidth(), calendar.getHeight());
    
    return calendar;
  }

  /**
   * @see javax.swing.CellEditor#cancelCellEditing()
   */
  public void cancelCellEditing() {
    fireEditingCanceled();
    //editing = false;
  }

  /**
   * @see javax.swing.CellEditor#stopCellEditing()
   */
  public boolean stopCellEditing() {
    fireEditingStopped();
    //editing = false;
    return true;
  }

  /**
   * @see javax.swing.CellEditor#getCellEditorValue()
   */
  public Object getCellEditorValue() {
    return calendar.getDate();
  }

  /**
   * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
   */
  public boolean isCellEditable(EventObject anEvent) {
    return true;
  }

  /**
   * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
   */
  public boolean shouldSelectCell(EventObject anEvent) {
    return true;
  }

  /**
   * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
   */
  public void addCellEditorListener(CellEditorListener l) {
    listeners.add(l);
  }

  /**
   * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
   */
  public void removeCellEditorListener(CellEditorListener l) {
    listeners.remove(l);
  }
  
  protected void fireEditingCanceled() {
    //Set the original originalValue back on the JCalendarComboBox
    calendar.setDate(originalValue);
    calendar.getCalendar().setTime(originalValue);
    ChangeEvent event = new ChangeEvent(this);
    CellEditorListener l;
    for (Iterator<CellEditorListener> iter = listeners.iterator(); iter.hasNext();) {
      l = iter.next();
      l.editingCanceled(event);
    }
  }
  
  protected void fireEditingStopped() {
    ChangeEvent event = new ChangeEvent(this);
    CellEditorListener l;
    for (Iterator<CellEditorListener> iter = listeners.iterator(); iter.hasNext();) {
      l = iter.next();
      l.editingStopped(event);
      
    }
  }

}
