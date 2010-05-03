package jtabpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Component to be used as tabComponent; Contains a JLabel to show the text and
 * a JButton to close the tab it belongs to
 * 
 * @author zeyuphoenix <br>
 *         daily jtabpane MyTabPaneButton.java <br>
 *         2008 2008/03/22 15:29:20 <br>
 */
public class MyTabPaneButton extends JPanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the Button
	 */
	private final JTabbedPane pane;

	/**
	 * the pane that the button in it.
	 * 
	 * @param pane
	 *            the tab pane.
	 */
	public MyTabPaneButton(final JTabbedPane pane) {
		// not set default FlowLayout' gaps
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.pane = pane;
		setOpaque(false);

		// make JLabel read titles from JTabbedPane
		JLabel label = new JLabel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public String getText() {
				int i = pane.indexOfTabComponent(MyTabPaneButton.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
		};

		add(label);
		// add more space between the label and the button
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		// tab button
		JButton button = new TabButton();
		add(button);
		// add more space to the top of the component
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	/**
	 * create tab button.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtabpane MyTabPaneButton.java <br>
	 *         2008 2008/03/22 18:16:56 <br>
	 */
	private class TabButton extends JButton implements ActionListener {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * button size.
		 */
		private static final int buttonSize = 17;

		/**
		 * button size.
		 */
		public TabButton() {
			this(buttonSize);
		}

		/**
		 * create button in tab.
		 * 
		 * @param size
		 *            button size.
		 */
		public TabButton(int size) {
			setPreferredSize(new Dimension(size, size));
			setToolTipText("close this tab");
			// Make the button looks the same for all Laf's
			setUI(new BasicButtonUI());
			// Make it transparent
			setContentAreaFilled(false);
			// No need to be focusable
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			// Making nice rollover effect
			// we use the same listener for all buttons
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			// Close the proper tab by clicking the button
			addActionListener(this);
		}

		/**
		 * the button action.
		 */
		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(MyTabPaneButton.this);
			if (i != -1) {
				pane.remove(i);
			}
		}

		/**
		 * don't want to update UI for this button.
		 */
		public void updateUI() {
		}

		/**
		 * repaint the button.
		 * 
		 * @param g
		 *            Graphics
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			// inset
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
					- delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
					- delta - 1);
			g2.dispose();
		}

		/**
		 * action class about button listener.
		 */
		private final MouseListener buttonMouseListener = new MouseAdapter() {
			/**
			 * mouse enter.
			 */
			public void mouseEntered(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(true);
				}
			}

			/**
			 * mouse exited.
			 */
			public void mouseExited(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(false);
				}
			}
		};

	}
}
