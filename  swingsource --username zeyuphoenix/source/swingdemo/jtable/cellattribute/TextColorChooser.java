package jtable.cellattribute;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * <code>JColorChooser</code> provides a pane of controls designed to allow a
 * user to manipulate and select a color.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cellattribute TextColorChooser.java <br>
 *         2008 2008/03/31 17:32:11 <br>
 */
public class TextColorChooser extends JColorChooser {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <code>JColorChooser</code> provides a pane of controls designed to
	 * allow a user to manipulate and select a color.
	 * 
	 * @param target
	 *            color.
	 * @param reference
	 *            color.
	 * @param isForgroundSelection
	 *            see the select
	 */
	public TextColorChooser(Color target, Color reference,
			boolean isForgroundSelection) {
		super(target);
		if (isForgroundSelection) {
			setPreviewPanel(new TextPreviewLabel(target, reference,
					isForgroundSelection));
		} else {
			setPreviewPanel(new TextPreviewLabel(reference, target,
					isForgroundSelection));
		}
		updateUI();
	}

	/**
	 * Shows a modal color-chooser dialog and blocks until the dialog is hidden.
	 * 
	 * @param component
	 *            the parent <code>Component</code> for the dialog
	 * @param title
	 *            the String containing the dialog's title
	 * @return the selected color or <code>null</code> if the user opted out
	 * @exception HeadlessException
	 *                if GraphicsEnvironment.isHeadless() returns true.
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 */
	public Color showDialog(Component component, String title) {
		ColorChooserDialog dialog = new ColorChooserDialog(component, title,
				this);
		dialog.setVisible(true);
		Color col = dialog.getColor();
		dialog.dispose();
		return col;
	}
}

/**
 * create the table cell's color.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cellattribute TextColorChooser.java <br>
 *         2008 2008/03/31 17:20:31 <br>
 */
class TextPreviewLabel extends JLabel {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** text */
	private String sampleText = "  Sample Text  Sample Text  ";
	/** is fore or back select. */
	private boolean isForgroundSelection = false;

	/**
	 * create the table cell's color.
	 */
	public TextPreviewLabel() {
		this(Color.black, Color.white, true);
	}

	/**
	 * create the table cell's color.
	 * 
	 * @param fore
	 *            fore ground
	 * @param back
	 *            back ground
	 * @param isForgroundSelection
	 *            is fore or back select.
	 */
	public TextPreviewLabel(Color fore, Color back, boolean isForgroundSelection) {
		setOpaque(true);
		setForeground(fore);
		setBackground(back);
		this.isForgroundSelection = isForgroundSelection;
		setText(sampleText);
	}

	/**
	 * set fore ground.
	 * 
	 * @param col
	 *            set color.
	 */
	public void setForeground(Color col) {
		if (isForgroundSelection) {
			super.setForeground(col);
		} else {
			super.setBackground(col);
		}
	}

}

/**
 * show the chooser dialog and return the selected color.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cellattribute TextColorChooser.java <br>
 *         2008 2008/03/31 17:33:53 <br>
 */
class ColorChooserDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** initial color */
	private Color initialColor = null;
	/** selected color. */
	private Color retColor = null;

	/**
	 * show the chooser dialog and return the selected color.
	 * 
	 * @param c
	 *            the father component.
	 * @param title
	 *            the title
	 * @param chooserPane
	 *            JColorChooser that java do it.
	 */
	public ColorChooserDialog(Component c, String title,
			final JColorChooser chooserPane) {
		super(JOptionPane.getFrameForComponent(c), title, true);
		setResizable(false);

		String okString = UIManager.getString("ColorChooser.okText");
		String cancelString = UIManager.getString("ColorChooser.cancelText");
		String resetString = UIManager.getString("ColorChooser.resetText");

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(chooserPane, BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton(okString);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retColor = chooserPane.getColor();
				setVisible(false);
			}
		});
		buttonPane.add(okButton);

		JButton cancelButton = new JButton(cancelString);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retColor = null;
				setVisible(false);
			}
		});
		buttonPane.add(cancelButton);

		JButton resetButton = new JButton(resetString);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooserPane.setColor(initialColor);
			}
		});
		buttonPane.add(resetButton);
		contentPane.add(buttonPane, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(c);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
	}

	/**
	 * get the select color.
	 * 
	 * @return color.
	 */
	public Color getColor() {
		return retColor;
	}
}
