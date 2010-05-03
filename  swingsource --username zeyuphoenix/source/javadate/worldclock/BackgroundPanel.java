/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldclock;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 */
public class BackgroundPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;

	public BackgroundPanel() {
		this.setPreferredSize(new java.awt.Dimension(148, 100));
		this.setSize(new java.awt.Dimension(148, 100));
		setOpaque(false);
		init();
	}

	private void init() {
		this.backgroundImage = null;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		this.backgroundImage = null;
		repaint();
	}

	@Override
	public void setSize(java.awt.Dimension dim) {
		super.setSize(dim);
		this.backgroundImage = null;
		repaint();
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();

		if (this.backgroundImage == null) {
			this.backgroundImage = createBackgroundImage();
		}

		g2.drawImage(backgroundImage, 0, 0, null);

		g2.dispose();
	}

	private BufferedImage createBackgroundImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf
				.createCompatibleImage(getWidth(), getHeight(),
						Transparency.TRANSLUCENT);

		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		// RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		// g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
		// RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		// g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
		// RenderingHints.VALUE_STROKE_PURE);

		Point2D BACKGROUND_START = new Point2D.Double(
				0, 0);
		Point2D BACKGROUND_STOP = new Point2D.Double(
				0, getHeight());

		final float[] BACKGROUND_FRACTIONS = { 0.0f, 1.0f };

		final Color[] BACKGROUND_COLORS = {
				new Color(0x505652), new Color(0x393E3A) };

		final Shape BACKGROUND = new RoundRectangle2D.Double(
				0, 0, getWidth(), getHeight(), 5, 5);

		final LinearGradientPaint BACKGROUND_GRADIENT = new LinearGradientPaint(
				BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS,
				BACKGROUND_COLORS);

		g2.setPaint(BACKGROUND_GRADIENT);
		g2.fill(BACKGROUND);

		Point2D INNER_BACKGROUND_START = new Point2D.Double(
				0, 10);
		Point2D INNER_BACKGROUND_STOP = new Point2D.Double(
				0, getHeight() - 10);

		final float[] INNER_BACKGROUND_FRACTIONS = { 0.0f, 1.0f };

		final Color[] INNER_BACKGROUND_COLORS = {
				new Color(0x4D5651), new Color(0x393E3A) };

		final Shape INNER_BACKGROUND = new RoundRectangle2D.Double(
				10, 10, getWidth() - 20, getHeight() - 20, 5, 5);

		final LinearGradientPaint INNER_BACKGROUND_GRADIENT = new LinearGradientPaint(
				INNER_BACKGROUND_START, INNER_BACKGROUND_STOP,
				INNER_BACKGROUND_FRACTIONS, INNER_BACKGROUND_COLORS);

		g2.setPaint(INNER_BACKGROUND_GRADIENT);
		g2.fill(INNER_BACKGROUND);

		// Draw vertical inset effect
		g2.setColor(new Color(0x2F362E));
		g2.drawLine(10, 10, 10, getHeight() - 10);
		g2.drawLine(getWidth() - 10, 10, getWidth() - 10, getHeight() - 10);

		// Draw top inset effect
		g2.drawLine(10, 10, getWidth() - 10, 10);

		// Draw bottom inset effect
		g2.setColor(new Color(0x6B7167));
		g2.drawLine(10, getHeight() - 10, getWidth() - 10, getHeight() - 10);

		g2.dispose();

		return IMAGE;
	}
}
