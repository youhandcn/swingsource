/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nixieclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
 * 
 * @author hansolo
 */
public class BackgroundPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;

	public BackgroundPanel() {
		this.setPreferredSize(new Dimension(148, 64));
		this.setSize(new Dimension(148, 64));
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
	public void setSize(Dimension dim) {
		super.setSize(dim);
		this.backgroundImage = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
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

		Point2D BACKGROUND_START = new Point2D.Double(
				0, 0);
		Point2D BACKGROUND_STOP = new Point2D.Double(
				0, getHeight());

		final float[] BACKGROUND_FRACTIONS = { 0.0f, 1.0f };

		final Color[] BACKGROUND_COLORS = {
				new Color(0x444444), new Color(0x222222) };

		final Shape BACKGROUND = new RoundRectangle2D.Double(
				0, 0, getWidth(), getHeight(), 10, 10);

		final LinearGradientPaint BACKGROUND_GRADIENT = new LinearGradientPaint(
				BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS,
				BACKGROUND_COLORS);

		g2.setPaint(BACKGROUND_GRADIENT);
		g2.fill(BACKGROUND);

		g2.dispose();

		return IMAGE;
	}
}
