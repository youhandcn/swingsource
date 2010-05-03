package tablelayout;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Demo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {
		new Demo();
	}

	public Demo() {
		super("The Power of Preferred Sizes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();

		JScrollPane jScrollPane = new JScrollPane();
		// b - border
		// f - FILL
		// p - PREFERRED
		// vs - vertical space between labels and text fields
		// vg - vertical gap between form elements
		// hg - horizontal gap between form elements
		double b = 10;
		double f = TableLayout.FILL;
		double p = TableLayout.PREFERRED;
		double vs = 5;
		double vg = 10;
		double hg = 10;

		double size[][] = { { b, f, hg, p, hg, p, b },
				{ b, p, vs, p, vg, p, vs, p, vg, p, vs, p, vg, p, b } };

		TableLayout layout = new TableLayout(size);
		JPanel pane = new JPanel();
		pane.setLayout(layout);

		// Create all controls
		JLabel labelName = new JLabel("Name");
		JLabel labelAddress = new JLabel("Address");
		JLabel labelCity = new JLabel("City");
		JLabel labelState = new JLabel("State");
		JLabel labelZip = new JLabel("Zip");

		JTextField textfieldName = new JTextField(10);
		JTextField textfieldAddress = new JTextField(20);
		JTextField textfieldCity = new JTextField(10);
		JTextField textfieldState = new JTextField(2);
		JTextField textfieldZip = new JTextField(5);

		JButton buttonOk = new JButton("OK");
		JButton buttonCancel = new JButton("Cancel");
		JPanel panelButton = new JPanel();
		panelButton.add(buttonOk);
		panelButton.add(buttonCancel);

		// Add all controls
		pane.add(labelName, "1,  1, 5, 1");
		pane.add(textfieldName, "1,  3, 5, 3");
		pane.add(labelAddress, "1,  5, 5, 5") ;
		pane.add(textfieldAddress, "1,  7, 5, 7");
		pane.add(labelCity, "1,  9");
		pane.add(textfieldCity, "1, 11");
		pane.add(labelState, "3,  9");
		pane.add(textfieldState, "3, 11");
		pane.add(labelZip, "5,  9");
		pane.add(textfieldZip, "5, 11");
		pane.add(panelButton, "1, 12, 5, 13");

		jScrollPane.getViewport().add(pane);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(jScrollPane, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}
}
