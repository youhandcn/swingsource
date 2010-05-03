package monthcalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

/**
 * This is a simple bean to realize two functions:
 */
public class MetaChooser extends JComponent {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * "Previous" button.
	 */
	private JButton preButton = null;

	/**
	 * "Next" button.
	 */
	private JButton nextButton = null;

	/**
	 * Item list.
	 */
	private JComponent listBox = null;

	/**
	 * Associated with an iterator.
	 */
	private transient Iterator listHelper = null;

	/**
	 * Default constructor: Uses default character arrow on button and no change
	 * when click.
	 * 
	 * @param listBox
	 *            The middle component in this bean.
	 */
	public MetaChooser(JComponent listBox) {
		preButton = new JButton(new PreviousIcon(Color.darkGray, 8, 10));
		nextButton = new JButton(new NextIcon(Color.darkGray, 8, 10));
		this.listBox = listBox == null ? new JFormattedTextField() : listBox;
		initialize();
	}

	/**
	 * Constructor: Uses specific button (marked with characters or icons).
	 * 
	 * @param listBox
	 *            the middle component in this bean.
	 * @param preBtn
	 *            a button to denote move to previous item.
	 * @param nextBtn
	 *            a button to denote move to next item.
	 */
	public MetaChooser(JComponent listBox, JButton preButton, JButton nextButton) {
		this.preButton = preButton;
		this.nextButton = nextButton;
		this.listBox = listBox == null ? new JFormattedTextField() : listBox;
		initialize();
	}

	/**
	 * This method initializes the GUI.
	 */
	private void initialize() {
		try {
			// Initialize helper
			if (listBox instanceof JComboBox) {
				listHelper = new ComboIterator((JComboBox) listBox);
			} else if (listBox instanceof JFormattedTextField) {
				listHelper = new IntegerTextIterator(
						(JFormattedTextField) listBox);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		preButton.setBorder(BorderFactory.createRaisedBevelBorder());
		preButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listHelper.movePrevious();
			}
		});

		nextButton.setBorder(BorderFactory.createRaisedBevelBorder());
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listHelper.moveNext();
			}
		});

		listBox.setPreferredSize(new Dimension(100, 20));

		// Set layout of this bean.
		this.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.gridy = 0;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints3.gridx = 2;
		gridBagConstraints3.gridy = 0;
		gridBagConstraints3.fill = java.awt.GridBagConstraints.VERTICAL;
		this.add(preButton, gridBagConstraints1);
		this.add(listBox, gridBagConstraints2);
		this.add(nextButton, gridBagConstraints3);
	}

	/**
	 * Sets the current item's index.
	 * 
	 * @param index
	 *            the current item's index to set.
	 * @throws Exception
	 * 
	 * @see com.jungleford.common.iterator.Iterator#setCurrentItem(int)
	 */
	public void setCurrent(int index) throws Exception {
		listHelper.setCurrentItem(index);
	}
}
