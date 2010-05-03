package gradient.RadialGradient;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 */
public class RadialGradientApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of RadialGradientApp */
	public RadialGradientApp() {
		super("Radial Gradient");

		JPanel panel = new JPanel();
		panel.add(new SphereComponent(false));
		panel.add(new SphereComponent(true));
		add(panel);

		pack();
		setLocationRelativeTo(null);
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RadialGradientApp().setVisible(true);
			}
		});
	}
}
