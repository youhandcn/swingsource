package jborder;

import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyTitledPane extends JPanel {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel border.
	 */
	private MyTitledBorder border = null;
	/**
	 * the component in the title pane.
	 */
	private JComponent component = null;
	/**
	 * the title pane.
	 */
	private JPanel panel = null;
	/**
	 * is enable allow.
	 */
	private boolean transmittingAllowed = false;
	/**
	 * enable or not.
	 */
	private StateTransmitter transmitter = null;

	public MyTitledPane() {
		this(new JLabel("Title"));
	}

	public MyTitledPane(JComponent component) {
		this.component = component;
		border = new MyTitledBorder(component);
		setBorder(border);
		panel = new JPanel();
		setLayout(null);
		add(component);
		add(panel);
		transmittingAllowed = false;
		transmitter = null;
	}

	/**
	 * get now component.
	 * 
	 * @return now component.
	 */
	public JComponent getTitleComponent() {
		return component;
	}

	/**
	 * remove old component and add new one.
	 * 
	 * @param newComponent
	 *            new Component
	 */
	public void setTitleComponent(JComponent newComponent) {
		remove(component);
		add(newComponent);
		border.setTitleComponent(newComponent);
		component = newComponent;
	}

	/**
	 * get the content pane.
	 * 
	 * @return pane.
	 */
	public JPanel getContentPane() {
		return panel;
	}

	/**
	 * reset the pane layout.
	 */
	@Override
	public void doLayout() {
		Insets insets = getInsets();
		Rectangle rect = getBounds();
		rect.x = 0;
		rect.y = 0;

		Rectangle compR = border.getComponentRect(rect, insets);
		component.setBounds(compR);
		rect.x += insets.left;
		rect.y += insets.top;
		rect.width -= insets.left + insets.right;
		rect.height -= insets.top + insets.bottom;
		panel.setBounds(rect);
	}

	/**
	 * get panel is enable allow.
	 * 
	 * @param enable
	 *            is enable allow.
	 */
	public void setTransmittingAllowed(boolean enable) {
		transmittingAllowed = enable;
	}

	/**
	 * set panel is enable allow.
	 * 
	 * @return is enable allow.
	 */
	public boolean getTransmittingAllowed() {
		return transmittingAllowed;
	}

	/**
	 * set pane component is enable.
	 * 
	 * @param transmitter
	 *            is enable
	 */
	public void setTransmitter(StateTransmitter transmitter) {
		this.transmitter = transmitter;
	}

	/**
	 * get pane component is enable.
	 * 
	 * @return is enable.
	 */
	public StateTransmitter getTransmitter() {
		return transmitter;
	}

	/**
	 * set pane and it's children is enable.
	 * 
	 * @param enable
	 *            enable or not.
	 */
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		if (transmittingAllowed && transmitter != null) {
			transmitter.setChildrenEnabled(enable);
		}
	}
}
