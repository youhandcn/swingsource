package jtable.tableheader;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * the table header that have column group.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest ColumnGroup.java <br>
 *         2008 2008/03/27 16:11:54 <br>
 */
public class ColumnGroup {

	/** header renderer. */
	private TableCellRenderer renderer = null;
	/** the header group. */
	private Vector<Object> v = null;
	/** header value. */
	private String text = null;
	private int margin = 0;

	/**
	 * the table header that have column group.
	 */
	public ColumnGroup() {
		this("");
	}

	/**
	 * the table header that have column group.
	 * 
	 * @param text
	 *            header value.
	 */
	public ColumnGroup(String text) {
		this(null, text);
	}

	/**
	 * the table header that have column group.
	 * 
	 * @param renderer
	 *            header renderer.
	 * @param text
	 *            header value.
	 */
	public ColumnGroup(TableCellRenderer renderer, String text) {
		if (renderer == null) {
			this.renderer = new MyHeaderButtonRenderer();
		} else {
			this.renderer = renderer;
		}
		this.text = text;
		v = new Vector<Object>();
	}

	/**
	 * add header or group to column group.
	 * 
	 * @param obj
	 *            TableColumn or ColumnGroup
	 */
	public void add(Object obj) {
		if (obj == null) {
			return;
		}
		v.addElement(obj);
	}

	/**
	 * get column group's child.
	 * 
	 * @param column
	 *            TableColumn table column.
	 * @param v
	 *            ColumnGroups column group.
	 */
	@SuppressWarnings("unchecked")
	public Vector<ColumnGroup> getColumnGroups(TableColumn column,
			Vector<ColumnGroup> group) {
		group.addElement(this);
		if (v.contains(column)) {
			return group;
		}
		Enumeration<Object> enumTemp = v.elements();
		while (enumTemp.hasMoreElements()) {
			Object obj = enumTemp.nextElement();
			if (obj instanceof ColumnGroup) {
				Vector<ColumnGroup> groups = (Vector<ColumnGroup>) ((ColumnGroup) obj)
						.getColumnGroups(column, (Vector<ColumnGroup>) group
								.clone());
				if (groups != null)
					return groups;
			}
		}
		return null;
	}

	/**
	 * get header renderer.
	 * 
	 * @return header renderer.
	 */
	public TableCellRenderer getHeaderRenderer() {
		return renderer;
	}

	/**
	 * set header renderer.
	 * 
	 * @param renderer
	 *            header renderer.
	 */
	public void setHeaderRenderer(TableCellRenderer renderer) {
		if (renderer != null) {
			this.renderer = renderer;
		}
	}

	/**
	 * return header text.
	 * 
	 * @return header value.
	 */
	public Object getHeaderValue() {
		return text;
	}

	/**
	 * get header group size.
	 * 
	 * @param table
	 *            the table.
	 * @return size.
	 */
	public Dimension getSize(JTable table) {
		Component comp = renderer.getTableCellRendererComponent(table,
				getHeaderValue(), false, false, -1, -1);
		int height = comp.getPreferredSize().height;
		int width = 0;
		Enumeration<Object> enumTemp = v.elements();
		while (enumTemp.hasMoreElements()) {
			Object obj = enumTemp.nextElement();
			if (obj instanceof TableColumn) {
				TableColumn aColumn = (TableColumn) obj;
				width += aColumn.getWidth();
				width += margin;
			} else {
				width += ((ColumnGroup) obj).getSize(table).width;
			}
		}
		return new Dimension(width, height);
	}

	/**
	 * set the "" as a new margin.
	 * 
	 * @param margin
	 *            new margin.
	 */
	public void setColumnMargin(int margin) {
		this.margin = margin;
		Enumeration<Object> enumTemp = v.elements();
		while (enumTemp.hasMoreElements()) {
			Object obj = enumTemp.nextElement();
			if (obj instanceof ColumnGroup) {
				((ColumnGroup) obj).setColumnMargin(margin);
			}
		}
	}
}
