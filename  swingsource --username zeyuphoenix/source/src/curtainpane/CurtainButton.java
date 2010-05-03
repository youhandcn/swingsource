package curtainpane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 */
public class CurtainButton extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private String text;
	private Icon icon;
	private float alignment = LEFT_ALIGNMENT;

	/** Creates a new instance of CurtainPaneButton */
	public CurtainButton() {
		this(null, null);
	}

	public CurtainButton(String text, Icon icon) {
		this.text = text;
		this.icon = icon;
		setUI(new CurtainButtonUI());
	}

	public void addActionListener(ActionListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	protected void fireActionPerformed(ActionEvent e) {
		for (ActionListener listener : listeners)
			listener.actionPerformed(e);
	}

	public void setText(String text) {
		this.text = text;
		repaint();
	}

	public String getText() {
		return text;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
		repaint();
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
		repaint();
	}
}
