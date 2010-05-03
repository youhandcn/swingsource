package jtable.tableverticalheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;

/**
 * BasicTableUI implementation
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyVerticalHeaderCellUI.java <br>
 *         2008 2008/03/28 18:24:23 <br>
 */
public class MyVerticalHeaderCellUI extends BasicTableUI {
	/**
	 * Paint a representation of the <code>table</code> instance that was set
	 * in installUI().
	 */
	public void paint(Graphics g, JComponent c) {
		Rectangle oldClipBounds = g.getClipBounds();
		Rectangle clipBounds = new Rectangle(oldClipBounds);
		int tableWidth = table.getColumnModel().getTotalColumnWidth();
		clipBounds.width = Math.min(clipBounds.width, tableWidth);
		g.setClip(clipBounds);

		int firstIndex = table.rowAtPoint(new Point(0, clipBounds.y));
		int lastIndex = table.getRowCount() - 1;

		Rectangle rowRect = new Rectangle(0, 0, tableWidth, table
				.getRowHeight()
				+ table.getRowMargin());
		rowRect.y = firstIndex * rowRect.height;

		for (int index = firstIndex; index <= lastIndex; index++) {
			if (rowRect.intersects(clipBounds)) {
				paintRow(g, index);
			}
			rowRect.y += rowRect.height;
		}
		g.setClip(oldClipBounds);
	}

	/**
	 * Paint row.
	 * 
	 * @param g
	 *            Graphics
	 * @param row
	 *            row index.
	 */
	private void paintRow(Graphics g, int row) {
		Rectangle rect = g.getClipBounds();
		boolean drawn = false;

		MyVerticalHeaderModel tableModel = (MyVerticalHeaderModel) table
				.getModel();
		MyCellSpan cellAtt = (MyCellSpan) tableModel.getCellAttribute();
		int numColumns = table.getColumnCount();

		for (int column = 0; column < numColumns; column++) {
			Rectangle cellRect = table.getCellRect(row, column, true);
			int cellRow, cellColumn;
			if (cellAtt.isVisible(row, column)) {
				cellRow = row;
				cellColumn = column;
			} else {
				cellRow = row + cellAtt.getSpan(row, column)[MyCellSpan.ROW];
				cellColumn = column
						+ cellAtt.getSpan(row, column)[MyCellSpan.COLUMN];
			}
			if (cellRect.intersects(rect)) {
				drawn = true;
				paintCell(g, cellRect, cellRow, cellColumn);
			} else {
				if (drawn)
					break;
			}
		}

	}

	/**
	 * paint Cell.
	 * 
	 * @param g
	 *            Graphics
	 * @param cellRect
	 *            Rectangle
	 * @param row
	 *            row index.
	 * @param column
	 *            column index.
	 */
	private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
		int spacingHeight = table.getRowMargin();
		int spacingWidth = table.getColumnModel().getColumnMargin();

		Color c = g.getColor();
		g.setColor(table.getGridColor());
		g.drawRect(cellRect.x, cellRect.y, cellRect.width - 1,
				cellRect.height - 1);
		g.setColor(c);

		cellRect.setBounds(cellRect.x + spacingWidth / 2, cellRect.y
				+ spacingHeight / 2, cellRect.width - spacingWidth,
				cellRect.height - spacingHeight);

		if (table.isEditing() && table.getEditingRow() == row
				&& table.getEditingColumn() == column) {
			Component component = table.getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		} else {
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component component = table.prepareRenderer(renderer, row, column);

			if (component.getParent() == null) {
				rendererPane.add(component);
			}
			rendererPane.paintComponent(g, component, table, cellRect.x,
					cellRect.y, cellRect.width, cellRect.height, true);
		}
	}

}
