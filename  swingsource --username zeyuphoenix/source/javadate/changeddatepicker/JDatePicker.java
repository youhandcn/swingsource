package changeddatepicker;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * calendar select
 * 
 * @author sz
 */
public class JDatePicker extends JTextField {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * action event
	 */
	private EventHandler eventHandler;
	/**
	 * popup menu.
	 */
	private Popup popup = null;
	/**
	 * calendar panel.
	 */
	private CalendarPanel datePanel;
	/**
	 * is show flag.
	 */
	private boolean flag = false;

	/**
	 * root method.
	 */
	public JDatePicker() {

		// extends JTextField
		super();

		// calendar init.
		datePanel = new CalendarPanel();
		datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// event handler init.
		eventHandler = new EventHandler();

	}

	/**
	 * init JTextField
	 * 
	 * @param intX
	 *            X point
	 * @param intY
	 *            Y point
	 * @param intWidth
	 *            width
	 * @param intHeight
	 *            height
	 */
	public void init(int intX, int intY, int intWidth, int intHeight) {

		// init.
		setBounds(intX, intY, intWidth, intHeight);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setHorizontalAlignment(JTextField.CENTER);
		setEditable(false);

		// set text.
		setText(datePanel.toString());

		// add action.
		addMouseListener(eventHandler);
		addHierarchyBoundsListener(eventHandler);
		datePanel.addChangeListener(eventHandler);
		datePanel.addActionListener(eventHandler);

	}

	/**
	 * rewrite to string
	 * 
	 * @return String
	 */
	public String toString() {
		return datePanel.toString();
	}

	/**
	 * show popup menu.
	 */
	private void showPopup() {
		if (popup == null) {
			if (getFlag()) {

				PopupFactory fac = new PopupFactory();
				// x and y.
				Point xy = getLocationOnScreen();
				datePanel.setVisible(true);
				popup = fac.getPopup(this, datePanel, (int) xy.getX(),
						(int) (xy.getY() + this.getHeight() - 1));
				popup.show();
			}
		}
	}

	/**
	 * hide popup menu.
	 */
	private void hidePopup() {
		if (popup != null) {

			JDatePicker.this.setText(datePanel.toString());
			popup.hide();
			popup = null;
		}
	}

	/**
	 * owner trace.
	 * 
	 * @author sz
	 */
	private class EventHandler implements ActionListener,
			HierarchyBoundsListener, ChangeListener, MouseListener {

		/**
		 * move.
		 * 
		 * @param e
		 *            event
		 */
		public void ancestorMoved(HierarchyEvent e) {
			hidePopup();
		}

		/**
		 * focus remove
		 * 
		 * @param e
		 *            event
		 */
		public void ancestorResized(HierarchyEvent e) {
			hidePopup();
		}

		/**
		 * Action
		 * 
		 * @param e
		 *            event
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(JDatePicker.this)) {

				if (popup == null) {
					showPopup();
				} else {
					hidePopup();
				}
			} else if (e.getSource().equals(datePanel)) {
				hidePopup();
			}
		}

		/**
		 * calendar change event.
		 * 
		 * @param e
		 *            event
		 */
		public void stateChanged(ChangeEvent e) {
			if (e.getSource().equals(datePanel)) {

				JDatePicker.this.setText(datePanel.toString());
			}
		}

		/**
		 * mouse Click
		 * 
		 * @param e
		 *            event
		 */
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(JDatePicker.this)) {

				if (popup == null) {
					showPopup();
				} else {
					hidePopup();
				}
			} else if (e.getSource().equals(datePanel)) {
				hidePopup();
			}
		}

		/**
		 * mouse Entered
		 * 
		 * @param e
		 *            event
		 */
		public void mouseEntered(MouseEvent e) {

		}

		/**
		 * mouse Exited
		 * 
		 * @param e
		 *            event
		 */
		public void mouseExited(MouseEvent e) {

		}

		/**
		 * mouse Pressed
		 * 
		 * @param e
		 *            event
		 */
		public void mousePressed(MouseEvent e) {

		}

		/**
		 * mouse Released
		 * 
		 * @param e
		 *            event
		 */
		public void mouseReleased(MouseEvent e) {

		}
	}

	/**
	 * is hide.
	 * 
	 * @return hide.
	 */
	public boolean getFlag() {
		return flag;
	}

	/**
	 * set is hide
	 * 
	 * @param flag
	 *            hide
	 */
	public void setFlag(boolean flag) {
		hidePopup();
		this.flag = flag;
	}

	/**
	 * hide popup menu.
	 */
	public void hide() {
		hidePopup();
	}
}
