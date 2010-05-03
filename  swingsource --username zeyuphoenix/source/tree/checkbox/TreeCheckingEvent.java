package checkbox;

import java.util.EventObject;

import javax.swing.tree.TreePath;

/**
 * An event that characterizes a change in the current checking.
 */
public class TreeCheckingEvent extends EventObject {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	/** Paths this event represents. */
	protected TreePath leadingPath;

	/**
	 * Returns the paths that have been added or removed from the selection.
	 */
	public TreePath getLeadingPath() {
		return this.leadingPath;
	}

	public TreeCheckingEvent(TreePath path) {
		super(path);
		this.leadingPath = path;
	}

}
