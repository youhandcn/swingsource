package gradient.MultiStopsGradient;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 */
public class MultiStopsGradient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of MultiStopsGradient */
	public MultiStopsGradient() {
		super("Multi-stops Gradient");

		add(new GradientLabel("Gradients"));

		setSize(320, 55);
		setLocationRelativeTo(null);
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MultiStopsGradient().setVisible(true);
			}
		});
	}
}
