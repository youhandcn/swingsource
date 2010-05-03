package gradient.Reflection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 */
public class ReflectionApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ReflectionApp */
	public ReflectionApp() {
		super("Reflections");

		add(new ReflectionPanel());

		setSize(360, 380);
		setLocationRelativeTo(null);
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ReflectionApp().setVisible(true);
			}
		});
	}
}
