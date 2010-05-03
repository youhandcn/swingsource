package changeddatepicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class TestJDate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	private JButton printButton = null;

	/**
	 * @param owner
	 */
	public TestJDate() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			JPanel panelTemp = new JPanel();
			panelTemp.add(getJEndTextField());
			jContentPane.add(panelTemp, BorderLayout.CENTER);
			JPanel panel = new JPanel(new BorderLayout());
			printButton = new JButton("print");

			printButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						SwingUtilities.updateComponentTreeUI(TestJDate.this);

	
						UIManager
								.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
						SwingUtilities.updateComponentTreeUI(TestJDate.this);
						UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				SwingUtilities.updateComponentTreeUI(TestJDate.this);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

			panel.add(printButton, BorderLayout.CENTER);
			jContentPane.add(panel, BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JDatePicker jEndDatePicker = null;

	/*
	 * This method initializes jStartTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJEndTextField() {
		if (jEndDatePicker == null) {

			jEndDatePicker = new JDatePicker();

			jEndDatePicker.init(15, 15, 90, 25);

			jEndDatePicker.setText("YYYY/MM/DD");
			jEndDatePicker.setEditable(false);
			jEndDatePicker.setFlag(true);
			jEndDatePicker.setBackground(Color.green);
			jEndDatePicker.setEnabled(true);
		}
		return jEndDatePicker;
	}

	/**
	 * 
	 */
	public void mouseClicked(MouseEvent e) {

		jEndDatePicker.hide();
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array
	 */
	public static void main(final String[] args) {

		// show the frame.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
//				JFrame.setDefaultLookAndFeelDecorated(true);
//				JDialog.setDefaultLookAndFeelDecorated(true);
//
//				SubstanceImageWatermark waterMark = new SubstanceImageWatermark(
//						TestJDate.class
//								.getResourceAsStream("/image/back.png"));
//				waterMark.setKind(ImageWatermarkKind.SCREEN_CENTER_SCALE);
//				SubstanceSkin skin = new OfficeBlue2007Skin()
//						.withWatermark(waterMark);
//				try {
//					UIManager
//							.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
//				} catch (UnsupportedLookAndFeelException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}			
//				SubstanceLookAndFeel.setSkin(skin);
				(new TestJDate()).setVisible(true);
			}
		});
	}
}
