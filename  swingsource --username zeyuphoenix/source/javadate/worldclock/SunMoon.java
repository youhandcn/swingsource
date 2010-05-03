package worldclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 */
public class SunMoon extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int timeOfDay = 0;

	public enum TYPE {
		SUNRISE, SUN, SUNSET, MOON
	};

	private final BufferedImage SUNRISE_IMAGE = createSunMoonImage(TYPE.SUNRISE);
	private final BufferedImage SUN_IMAGE = createSunMoonImage(TYPE.SUN);
	private final BufferedImage SUNSET_IMAGE = createSunMoonImage(TYPE.SUNSET);
	private final BufferedImage MOON_IMAGE = createSunMoonImage(TYPE.MOON);

	public SunMoon() {
		super();
		setPreferredSize(new java.awt.Dimension(24, 24));
		setSize(new java.awt.Dimension(24, 24));
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		switch (timeOfDay) {
		case -2:
			g2.drawImage(MOON_IMAGE, 0, 0, null);
			break;

		case -1:
			g2.drawImage(SUNRISE_IMAGE, 0, 0, null);
			break;

		case 0:
			g2.drawImage(SUN_IMAGE, 0, 0, null);
			break;

		case 1:
			g2.drawImage(SUNSET_IMAGE, 0, 0, null);
			break;

		default:
			g2.drawImage(SUN_IMAGE, 0, 0, null);
			break;
		}
	}

	private BufferedImage createSunMoonImage(TYPE type) {

		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(24, 24,
				Transparency.TRANSLUCENT);
		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		final Point2D START_SUN = new Point2D.Double(6, 4);
		final Point2D STOP_SUN = new Point2D.Double(16, 17);
		final float[] FRACTIONS_SUN = { 0.0f, 0.2f, 0.6f, 1.0f };
		final Color[] COLORS_SUN = { new Color(0xFDF589), new Color(0xFEE438),
				new Color(0xF6B31B), new Color(0xEE891A) };
		final LinearGradientPaint GRADIENT_SUN = new LinearGradientPaint(
				START_SUN, STOP_SUN, FRACTIONS_SUN, COLORS_SUN);

		final Point2D CENTER_SUN = new Point2D.Double(11.5, 11.5);
		final float[] FRACTIONS_SUN_INNERSHADOW = { 0.0f, 0.6f, 0.8f, 1.0f };
		final Color[] COLORS_INNERSHADOW = { new Color(0.5f, 0.5f, 0.5f, 0.0f),
				new Color(0.5f, 0.5f, 0.5f, 0.0f),
				new Color(0.0f, 0.0f, 0.0f, 0.1f),
				new Color(0.0f, 0.0f, 0.0f, 0.5f), };
		final RadialGradientPaint GRADIENT_INNERSHADOW = new RadialGradientPaint(
				CENTER_SUN, 10f, FRACTIONS_SUN_INNERSHADOW, COLORS_INNERSHADOW);

		final Point2D START_SUN_LIGHT = new Point2D.Double(0, 4);
		final Point2D STOP_SUN_LIGHT = new Point2D.Double(0, 12);
		final float[] FRACTIONS_SUN_LIGHT = { 0.0f, 1.0f };
		final Color[] COLORS_SUN_LIGHT = { new Color(1.0f, 1.0f, 1.0f, 0.4f),
				new Color(1.0f, 1.0f, 1.0f, 0.05f), };
		final LinearGradientPaint GRADIENT_SUN_LIGHT = new LinearGradientPaint(
				START_SUN_LIGHT, STOP_SUN_LIGHT, FRACTIONS_SUN_LIGHT,
				COLORS_SUN_LIGHT);

		final Color[] COLORS_SUNSET = { new Color(0xFCCC89),
				new Color(0xFCAB2E), new Color(0xF25D0E), new Color(0xE74811) };
		final LinearGradientPaint GRADIENT_SUNSET = new LinearGradientPaint(
				START_SUN, STOP_SUN, FRACTIONS_SUN, COLORS_SUNSET);

		final Point2D START_MOON = new Point2D.Double(0, 2);
		final Point2D STOP_MOON = new Point2D.Double(0, 21);
		final float[] FRACTIONS_MOON = { 0.0f, 1.0f };
		final Color[] COLORS_MOON = { new Color(0xFFFFFF), new Color(0xAAAAAA) };
		final LinearGradientPaint GRADIENT_MOON = new LinearGradientPaint(
				START_MOON, STOP_MOON, FRACTIONS_MOON, COLORS_MOON);
		final Area MOON = new Area(new Ellipse2D.Double(2, 2, 20, 20));
		MOON.subtract(new Area(new Ellipse2D.Double(1, 1, 15, 20)));

		switch (type) {
		case SUNRISE: // SUNRISE
			g2.translate(0, 12);
			// Draw shadow
			g2.setColor(new Color(0x000000));
			g2.translate(1, 1);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}
			g2.translate(-1, -1);

			// Draw sun
			g2.setColor(new Color(0xF0BE26));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}

			g2.setPaint(GRADIENT_SUN);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.setPaint(GRADIENT_INNERSHADOW);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.rotate(Math.toRadians(-10), 10, 8);
			g2.setPaint(GRADIENT_SUN_LIGHT);
			g2.fill(new Ellipse2D.Double(11, 8, 4, 4));
			g2.rotate(Math.toRadians(10), 10, 8);
			g2.translate(0, -12);
			break;

		case SUN: // SUN
			// Draw shadow
			g2.setColor(new Color(0x000000));
			g2.translate(1, 1);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}
			g2.translate(-1, -1);

			// Draw sun
			g2.setColor(new Color(0xF0BE26));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}

			g2.setPaint(GRADIENT_SUN);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.setPaint(GRADIENT_INNERSHADOW);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.rotate(Math.toRadians(-10), 10, 8);
			g2.setPaint(GRADIENT_SUN_LIGHT);
			g2.fill(new Ellipse2D.Double(11, 8, 4, 4));
			g2.rotate(Math.toRadians(10), 10, 8);
			break;

		case SUNSET:
			// Draw shadow
			g2.translate(0, 12);
			g2.setColor(new Color(0x000000));
			g2.translate(1, 1);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));
			g2.translate(-1, -1);

			// Draw sun
			g2.setPaint(GRADIENT_SUNSET);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.setPaint(GRADIENT_INNERSHADOW);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.rotate(Math.toRadians(-10), 10, 8);
			g2.setPaint(GRADIENT_SUN_LIGHT);
			g2.fill(new Ellipse2D.Double(11, 8, 4, 4));
			g2.rotate(Math.toRadians(10), 10, 8);
			g2.translate(0, -12);
			break;

		case MOON: // MOON
			// Draw shadow
			g2.rotate(Math.toRadians(10), 12, 12);
			g2.setColor(new Color(0x000000));
			g2.translate(1, 1);
			g2.fill(MOON);
			g2.translate(-1, -1);

			// Draw moon
			g2.setPaint(GRADIENT_MOON);
			g2.fill(MOON);

			g2.setPaint(GRADIENT_INNERSHADOW);
			g2.fill(MOON);
			g2.rotate(Math.toRadians(-10), 12, 12);
			break;

		default: // SUN
			// Draw shadow
			g2.setColor(new Color(0x000000));
			g2.translate(1, 1);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}
			g2.translate(-1, -1);

			// Draw sun
			g2.setColor(new Color(0xF0BE26));
			for (int alpha = 0; alpha < 360; alpha += 15) {
				g2.rotate(Math.toRadians(alpha), 12, 12);
				g2.draw(new Line2D.Double(12, 1, 12, 20));
				g2.rotate(Math.toRadians(-alpha), 12, 12);
			}

			g2.setPaint(GRADIENT_SUN);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.setPaint(GRADIENT_INNERSHADOW);
			g2.fill(new Ellipse2D.Double(2, 2, 20, 20));

			g2.rotate(Math.toRadians(-10), 10, 8);
			g2.setPaint(GRADIENT_SUN_LIGHT);
			g2.fill(new Ellipse2D.Double(11, 8, 4, 4));
			g2.rotate(Math.toRadians(10), 10, 8);
			break;
		}

		g2.dispose();

		return IMAGE;
	}

	public int getTimeOfDay() {
		return this.timeOfDay;
	}

	public void setTimeOfDay(int timeOfDay) {
		this.timeOfDay = timeOfDay;
		repaint();
	}

	@Override
	public Dimension getSize() {
		return new Dimension(24, 24);
	}

	@Override
	public Dimension getSize(Dimension dim) {
		return new Dimension(24, 24);
	}

}