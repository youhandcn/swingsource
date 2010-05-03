package checkbox;

import java.util.EventListener;

/**
 * The listener that's notified when the checking in a TreeCheckingModel
 * changes.
 */
public interface TreeCheckingListener extends EventListener {
	/**
	 * Called whenever the value of the checking changes.
	 * 
	 * @param e
	 *            the event that characterizes the change.
	 */
	void valueChanged(TreeCheckingEvent e);
}