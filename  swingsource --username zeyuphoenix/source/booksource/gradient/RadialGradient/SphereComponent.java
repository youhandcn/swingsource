package gradient.RadialGradient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 */
public class SphereComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isDrawLogo = false;

	/** Creates a new instance of SphereComponent */
	public SphereComponent(boolean isDrawLogo) {
		this.isDrawLogo = isDrawLogo;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		setFont(getFont().deriveFont(70.f).deriveFont(Font.BOLD));

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Retains the previous state
		Paint oldPaint = g2.getPaint();

		// Fills the circle with solid blue color
		g2.setColor(new Color(0x0153CC));
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Adds shadows at the top
		Paint p;
		p = new GradientPaint(0, 0, new Color(0.0f, 0.0f, 0.0f, 0.4f), 0,
				getHeight(), new Color(0.0f, 0.0f, 0.0f, 0.0f));
		g2.setPaint(p);
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Adds highlights at the bottom
		p = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.0f), 0,
				getHeight(), new Color(1.0f, 1.0f, 1.0f, 0.4f));
		g2.setPaint(p);
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Creates dark edges for 3D effect
		p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
				getHeight() / 2.0), getWidth() / 2.0f,
				new float[] { 0.0f, 1.0f }, new Color[] {
						new Color(6, 76, 160, 127),
						new Color(0.0f, 0.0f, 0.0f, 0.8f) });
		g2.setPaint(p);
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Adds oval inner highlight at the bottom
		p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
				getHeight() * 1.5), getWidth() / 2.3f, new Point2D.Double(
				getWidth() / 2.0, getHeight() * 1.75 + 6), new float[] { 0.0f,
				0.8f }, new Color[] { new Color(64, 142, 203, 255),
				new Color(64, 142, 203, 0) },
				RadialGradientPaint.CycleMethod.NO_CYCLE,
				RadialGradientPaint.ColorSpaceType.SRGB, AffineTransform
						.getScaleInstance(1.0, 0.5));
		g2.setPaint(p);
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Adds oval specular highlight at the top left
		p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,
				getHeight() / 2.0), getWidth() / 1.4f, new Point2D.Double(45.0,
				25.0), new float[] { 0.0f, 0.5f }, new Color[] {
				new Color(1.0f, 1.0f, 1.0f, 0.4f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) },
				RadialGradientPaint.CycleMethod.NO_CYCLE);
		g2.setPaint(p);
		g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

		// Restores the previous state
		g2.setPaint(oldPaint);

		// Draws the logo
		if (isDrawLogo) {
			FontRenderContext context = g2.getFontRenderContext();
			TextLayout layout = new TextLayout("R", getFont(), context);
			Rectangle2D bounds = layout.getBounds();

			float x = (getWidth() - (float) bounds.getWidth()) / 2.0f;
			float y = (getHeight() + (float) bounds.getHeight()) / 2.0f;

			g2.setColor(Color.WHITE);
			layout.draw(g2, x, y);

			Area shadow = new Area(layout.getOutline(null));
			shadow.subtract(new Area(layout.getOutline(AffineTransform
					.getTranslateInstance(1.0, 1.0))));
			g2.setColor(Color.BLACK);
			g2.translate(x, y);
			g2.fill(shadow);
			g2.translate(-x, -y);
		}
	}
}
