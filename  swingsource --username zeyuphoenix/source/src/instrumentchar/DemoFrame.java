package instrumentchar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 */
public class DemoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form DemoFrame */
	public DemoFrame() {
		initComponents();
	}

	private void initComponents() {

		gauge = new Gauge();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0));

		gauge.setName("ÒÇ±íÍ¼");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(gauge, BorderLayout.CENTER);
		panel.add(new JLabel("        "), BorderLayout.NORTH);
		panel.add(new JLabel("        "), BorderLayout.EAST);
		panel.add(new JLabel("        "), BorderLayout.WEST);
		panel.add(new JLabel("        "), BorderLayout.SOUTH);
		getContentPane().add(panel);
		gauge.setValue(10);

		new Thread() {
			@Override
			public void run() {
				while (true) {

					try {
						Thread.sleep(3000);
						int newNum = new Random().nextInt(100);
						int oldNum = Double.valueOf(gauge.getValue())
								.intValue();
						if (newNum < oldNum) {
							for (int i = oldNum; i >= newNum; i--) {
								Thread.sleep(24);
								gauge.setValue(i);
							}
						} else {
							for (int i = oldNum; i <= newNum; i++) {
								Thread.sleep(24);
								gauge.setValue(i);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		pack();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DemoFrame().setVisible(true);
			}
		});
	}

	private Gauge gauge;
}
