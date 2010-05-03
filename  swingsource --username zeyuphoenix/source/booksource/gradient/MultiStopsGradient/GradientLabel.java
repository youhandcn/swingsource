package gradient.MultiStopsGradient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 */
public class GradientLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of GradientButton */
	public GradientLabel(String text) {
		super(text);
		setForeground(Color.WHITE);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Paint oldPaint = g2.getPaint();

		LinearGradientPaint p;

		p = new LinearGradientPaint(0.0f, 0.0f, 0.0f, 20.0f, new float[] {
				0.0f, 0.5f, 0.501f, 1.0f }, new Color[] { new Color(0x63a5f7),
				new Color(0x3799f4), new Color(0x2d7eeb), new Color(0x30a5f9) });
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), 21);

		g2.setPaint(oldPaint);
		super.paintComponent(g);
	}
}
