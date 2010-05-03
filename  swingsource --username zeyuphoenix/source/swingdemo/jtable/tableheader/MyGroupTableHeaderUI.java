package jtable.tableheader;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * BasicTableHeaderUI implementation.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyGroupTableHeaderUI.java <br>
 *         2008 2008/03/27 16:54:45 <br>
 */
public class MyGroupTableHeaderUI extends BasicTableHeaderUI {
	/**
	 * paint table header.
	 * 
	 * @param g
	 *            Graphics
	 * @param c
	 *            JComponent
	 */
	public void paint(Graphics g, JComponent c) {
		Rectangle clipBounds = g.getClipBounds();
		if (header.getColumnModel() == null) {
			return;
		}
		((MyTableHeader) header).setColumnMargin();
		int column = 0;
		Dimension size = header.getSize();
		Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
		Hashtable<ColumnGroup, Rectangle> h = new Hashtable<ColumnGroup, Rectangle>();
		Enumeration<?> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			cellRect.height = size.height;
			cellRect.y = 0;
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			Enumeration<?> cGroups = ((MyTableHeader) header)
					.getColumnGroups(aColumn);
			if (cGroups != null) {
				int groupHeight = 0;
				while (cGroups.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
					Rectangle groupRect = (Rectangle) h.get(cGroup);
					if (groupRect == null) {
						groupRect = new Rectangle(cellRect);
						Dimension d = cGroup.getSize(header.getTable());
						groupRect.width = d.width;
						groupRect.height = d.height;
						h.put(cGroup, groupRect);
					}
					paintCell(g, groupRect, cGroup);
					groupHeight += groupRect.height;
					cellRect.height = size.height - groupHeight;
					cellRect.y = groupHeight;
				}
			}
			cellRect.width = aColumn.getWidth() + 1;
			// + columnMargin;
			if (cellRect.intersects(clipBounds)) {
				paintCell(g, cellRect, column);
			}
			cellRect.x += cellRect.width;
			column++;
		}
	}

	/**
	 * paint the header cell.
	 * 
	 * @param g
	 *            Graphics
	 * @param cellRect
	 *            Rectangle
	 * @param columnIndex
	 *            column index.
	 */
	private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
		TableCellRenderer renderer = aColumn.getHeaderRenderer();
		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), aColumn.getHeaderValue(), false, false, -1,
				columnIndex);
		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * paint the header cell.
	 * 
	 * @param g
	 *            Graphics
	 * @param cellRect
	 *            Rectangle
	 * @param cGroup
	 *            ColumnGroup
	 */
	private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
		TableCellRenderer renderer = cGroup.getHeaderRenderer();
		Component component = renderer.getTableCellRendererComponent(header
				.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);
		rendererPane.add(component);
		rendererPane.paintComponent(g, component, header, cellRect.x,
				cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * get header height.//max column'row add.
	 * 
	 * @return height.
	 */
	private int getHeaderHeight() {
		int height = 0;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);
			TableCellRenderer renderer = aColumn.getHeaderRenderer();
			Component comp = renderer.getTableCellRendererComponent(header
					.getTable(), aColumn.getHeaderValue(), false, false, -1,
					column);
			int cHeight = comp.getPreferredSize().height;
			Enumeration<?> enumTemp = ((MyTableHeader) header)
					.getColumnGroups(aColumn);
			if (enumTemp != null) {
				while (enumTemp.hasMoreElements()) {
					ColumnGroup cGroup = (ColumnGroup) enumTemp.nextElement();
					cHeight += cGroup.getSize(header.getTable()).height;
				}
			}
			height = Math.max(height, cHeight);
		}
		return height;
	}

	/**
	 * create the header size.
	 * 
	 * @param width
	 *            header column width.
	 * @return size
	 */
	private Dimension createHeaderSize(long width) {
		TableColumnModel columnModel = header.getColumnModel();
		width += columnModel.getColumnMargin() * columnModel.getColumnCount();
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	/**
	 * Return the preferred size of the header. The preferred height is the
	 * maximum of the preferred heights of all of the components provided by the
	 * header renderer. The preferred width is the sum of the preferred widths
	 * of each column (plus inter-cell spacing).
	 */
	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration<?> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}

	/**
	 * Creates the mouse listener for the JTableHeader.
	 */
	protected MouseInputListener createMouseInputListener() {
		return new MouseInputHandler((MyTableHeader) header);
	}

	/**
	 * This inner class is marked &quot;public&quot; due to a compiler bug. This
	 * class should be treated as a &quot;protected&quot; inner class.
	 * Instantiate it only within subclasses of BasicTableUI.
	 */
	private class MouseInputHandler extends
			BasicTableHeaderUI.MouseInputHandler {
		/** the show component. */
		private Component dispatchComponent = null;
		/** the UI table header. */
		private MyTableHeader header = null;

		/**
		 * This inner class is marked &quot;public&quot; due to a compiler bug.
		 * This class should be treated as a &quot;protected&quot; inner class.
		 * 
		 * @param header
		 *            MyTableHeader
		 */
		public MouseInputHandler(MyTableHeader header) {
			this.header = header;
		}

		/**
		 * set component.
		 * 
		 * @param e
		 *            MouseEvent
		 */
		private void setDispatchComponent(MouseEvent e) {
			Component editorComponent = header.getEditorComponent();
			Point p = e.getPoint();
			Point p2 = SwingUtilities.convertPoint(header, p, editorComponent);
			dispatchComponent = SwingUtilities.getDeepestComponentAt(
					editorComponent, p2.x, p2.y);
		}

		/**
		 * repost event.
		 * 
		 * @param e
		 *            MouseEvent
		 * @return is able
		 */
		private boolean repostEvent(MouseEvent e) {
			if (dispatchComponent == null) {
				return false;
			}
			MouseEvent e2 = SwingUtilities.convertMouseEvent(header, e,
					dispatchComponent);
			dispatchComponent.dispatchEvent(e2);
			return true;
		}

		/**
		 * mouse Pressed event.
		 * 
		 * @param e
		 *            MouseEvent
		 */
		public void mousePressed(MouseEvent e) {
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			super.mousePressed(e);

			if (header.getResizingColumn() == null) {
				Point p = e.getPoint();
				TableColumnModel columnModel = header.getColumnModel();
				int index = columnModel.getColumnIndexAtX(p.x);
				if (index != -1) {
					if (header.editCellAt(index, e)) {
						setDispatchComponent(e);
						repostEvent(e);
					}
				}
			}
		}

		/**
		 * mouse Released event.
		 * 
		 * @param e
		 *            MouseEvent
		 */
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			if (!SwingUtilities.isLeftMouseButton(e)) {
				return;
			}
			repostEvent(e);
			dispatchComponent = null;
		}
	}
}
