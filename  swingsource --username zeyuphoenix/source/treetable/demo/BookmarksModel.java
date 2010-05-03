package demo;

import java.util.Date;

import study.DynamicTreeTableModel;
import study.TreeTableModel;

public class BookmarksModel extends DynamicTreeTableModel {
	/**
	 * Names of the columns.
	 */
	private static final String[] columnNames = { "Name", "Location",
			"Last Visited", "Created" };
	/**
	 * Method names used to access the data to display.
	 */
	private static final String[] methodNames = { "getName", "getLocation",
			"getLastVisited", "getCreated" };
	/**
	 * Method names used to set the data.
	 */
	private static final String[] setterMethodNames = { "setName",
			"setLocation", "setLastVisited", "setCreated" };
	/**
	 * Classes presenting the data.
	 */
	private static final Class<?>[] classes = { TreeTableModel.class,
			String.class, Date.class, Date.class };

	public BookmarksModel(Bookmarks.BookmarkDirectory root) {
		super(root, columnNames, methodNames, setterMethodNames, classes);
	}

	/**
	 * <code>isCellEditable</code> is invoked by the JTreeTable to determine if
	 * a particular entry can be added. This is overridden to return true for
	 * the first column, assuming the node isn't the root, as well as returning
	 * two for the second column if the node is a BookmarkEntry. For all other
	 * columns this returns false.
	 */
	public boolean isCellEditable(Object node, int column) {
		switch (column) {
		case 0:
			// Allow editing of the name, as long as not the root.
			return (node != getRoot());
		case 1:
			// Allow editing of the location, as long as not a
			// directory
			return (node instanceof Bookmarks.BookmarkEntry);
		default:
			// Don't allow editing of the date fields.
			return false;
		}
	}
}
