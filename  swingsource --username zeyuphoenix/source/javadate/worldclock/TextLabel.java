package worldclock;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

/**
 * 
 */
public class TextLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final java.awt.Color TEXT_COLOR = new java.awt.Color(0xF0F0F0);

	public TextLabel() {
		super("");
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		final FontMetrics METRICS = g2.getFontMetrics(g2.getFont());

		// Draw text shadow
		g2.setColor(Color.BLACK);
		g2.drawString(getText(), getInsets().left + 1, (getHeight() - METRICS
				.getDescent()) + 1);

		// Draw text
		g2.setColor(TEXT_COLOR);
		g2.drawString(getText(), getInsets().left, (getHeight() - METRICS
				.getDescent()));

		g2.dispose();
	}
}
