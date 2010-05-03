/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nixieclock;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * 
 * @author hansolo
 */
public class NixieNumber extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number;
	private BufferedImage[] numberStack = new BufferedImage[10];
	private final Point2D GLOW_START = new Point2D.Float(0, 47);
	private final Point2D GLOW_STOP = new Point2D.Float(0, 133);
	private final float[] GLOW_FRACTIONS = { 0.0f, 0.5f, 1.0f };
	private final Color[] GLOW_COLORS = {
			new Color(0.647f, 0.3137f, 0.0588f, 0.2f),
			new Color(0.9882f, 0.5921f, 0.0f, 0.3f),
			new Color(0.647f, 0.3137f, 0.0588f, 0.2f) };

	private final LinearGradientPaint GLOW_GRADIENT = new LinearGradientPaint(
			GLOW_START, GLOW_STOP, GLOW_FRACTIONS, GLOW_COLORS);
	private final Rectangle2D GLOW_BOX = new Rectangle2D.Float(13, 47, 60, 86);

	private static final Color[] COLOR_ARRAY = {
			new Color(1.0f, 0.6f, 0, 0.90f), new Color(1.0f, 0.4f, 0, 0.80f),
			new Color(1.0f, 0.4f, 0, 0.4f), new Color(1.0f, 0.4f, 0, 0.15f),
			new Color(1.0f, 0.4f, 0, 0.10f), new Color(1.0f, 0.4f, 0, 0.05f) };

	private final BufferedImage[] INACTIVE_NUMBER_ARRAY = {
			createNumber(0, false), createNumber(1, false),
			createNumber(2, false), createNumber(3, false),
			createNumber(4, false), createNumber(5, false),
			createNumber(6, false), createNumber(7, false),
			createNumber(8, false), createNumber(9, false), };

	private final BufferedImage[] ACTIVE_NUMBER_ARRAY = {
			createNumber(0, true), createNumber(1, true),
			createNumber(2, true), createNumber(3, true),
			createNumber(4, true), createNumber(5, true),
			createNumber(6, true), createNumber(7, true),
			createNumber(8, true), createNumber(9, true), };

	public NixieNumber() {
		super();
		this.number = -1;
		this.setPreferredSize(new Dimension(86, 146));
		this.setSize(new Dimension(86, 146));
		init();
	}

	private void init() {
		setOpaque(false);
		createNumberStack();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();

		// Backside glow effect
		if (number > -1) {
			g2.setPaint(GLOW_GRADIENT);
			g2.fill(GLOW_BOX);
		}

		// Draw nixie number
		for (int i = 0; i < 10; i++) {
			g2.drawImage(this.numberStack[i], 12, 42, null);
		}

		// Draw hatch
		g2.drawImage(createMiddleImage(), 0, 0, null);

		// Draw tube
		g2.drawImage(createBackgroundImage(), 0, 0, null);

		// Draw light effects of tube
		g2.drawImage(createForegroundImage(), 0, 0, null);

		g2.dispose();
	}

	private BufferedImage createBackgroundImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(86, 146,
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

		// Create glass tube form
		final Area TUBE = new Area(new RoundRectangle2D.Float(1, 8, 86, 136,
				86, 86));
		final Area TUBE_BOTTOM = new Area(new RoundRectangle2D.Float(1, 75, 86,
				70, 30, 30));
		final Area TUBE_TOP = new Area(new Ellipse2D.Float(38, 0, 12, 18));
		TUBE.add(TUBE_TOP);
		TUBE.add(TUBE_BOTTOM);

		final Point2D START = new Point2D.Float(0, 0);
		final Point2D STOP = new Point2D.Float(86, 0);

		final float[] FRACTIONS = { 0.0f, 0.15f, 0.4f, 0.6f, 0.85f, 1.0f };

		final Color[] COLORS = { new Color(0.0f, 0.0f, 0.0f, 0.5f),
				new Color(0.0f, 0.0f, 0.0f, 0.3f),
				new Color(0.0f, 0.0f, 0.0f, 0.1f),
				new Color(0.0f, 0.0f, 0.0f, 0.1f),
				new Color(0.0f, 0.0f, 0.0f, 0.3f),
				new Color(0.0f, 0.0f, 0.0f, 0.5f) };

		final LinearGradientPaint GRADIENT = new LinearGradientPaint(START,
				STOP, FRACTIONS, COLORS);

		g2.setPaint(GRADIENT);
		g2.fill(TUBE);

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createMiddleImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(86, 146,
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

		// Create front hatch
		g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.2f));
		for (int x = 14; x <= 75; x += 5) {
			g2.draw(new Line2D.Float(x, 47, x, 133));
		}
		for (int y = 47; y <= 134; y += 5) {
			g2.draw(new Line2D.Float(14, y, 74, y));
		}

		// Create number contacts
		LinearGradientPaint contactGradient;
		Point2D contactStart;
		Point2D contactStop;
		final float[] CONTACT_FRACTIONS = { 0.0f, 0.5f, 1.0f };

		final Color[] CONTACT_COLORS = { new Color(0.0f, 0.0f, 0.0f, 0.3f),
				new Color(1.0f, 1.0f, 1.0f, 0.3f),
				new Color(0.0f, 0.0f, 0.0f, 0.3f) };

		for (int x = 18; x < 67; x += 6) {
			contactStart = new Point2D.Float(x, 0);
			contactStop = new Point2D.Float(x + 3, 0);
			contactGradient = new LinearGradientPaint(contactStart,
					contactStop, CONTACT_FRACTIONS, CONTACT_COLORS);
			g2.setPaint(contactGradient);
			g2.fill(new Rectangle2D.Float(x, 132, 4, 10));
		}

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createForegroundImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(86, 146,
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

		// Create left side reflection effect
		final Area SIDE_LIGHT_EFFECT = new Area(new RoundRectangle2D.Float(3,
				43, 36, 99, 18, 18));
		final Area EFFECT_SUB = new Area(new RoundRectangle2D.Float(7, 37, 36,
				105, 18, 18));
		SIDE_LIGHT_EFFECT.subtract(EFFECT_SUB);

		final Point2D SIDE_LIGHT_EFFECT_START = new Point2D.Float(3, 0);
		final Point2D SIDE_LIGHT_EFFECT_STOP = new Point2D.Float(13, 0);

		final float[] SIDE_LIGHT_EFFECT_FRACTIONS = { 0.0f, 1.0f };

		final Color[] SIDE_LIGHT_EFFECT_COLORS = {
				new Color(1.0f, 1.0f, 1.0f, 0.5f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) };

		final LinearGradientPaint SIDE_LIGHT_EFFECT_GRADIENT = new LinearGradientPaint(
				SIDE_LIGHT_EFFECT_START, SIDE_LIGHT_EFFECT_STOP,
				SIDE_LIGHT_EFFECT_FRACTIONS, SIDE_LIGHT_EFFECT_COLORS);

		g2.setPaint(SIDE_LIGHT_EFFECT_GRADIENT);
		g2.fill(SIDE_LIGHT_EFFECT);

		// Create stripe reflection effect
		final Rectangle2D STRIPE_LIGHT_EFFECT = new Rectangle2D.Float(13, 46,
				62, 1);

		final Point2D STRIPE_LIGHT_EFFECT_START = new Point2D.Float(13, 0);
		final Point2D STRIPE_LIGHT_EFFECT_STOP = new Point2D.Float(75, 0);

		final float[] STRIPE_LIGHT_EFFECT_FRACTIONS = { 0.0f, 0.5f, 1.0f };

		final Color[] STRIPE_LIGHT_EFFECT_COLORS = {
				new Color(1.0f, 1.0f, 1.0f, 0.0f),
				new Color(1.0f, 1.0f, 1.0f, 0.5f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) };

		final LinearGradientPaint STRIPE_LIGHT_EFFECT_GRADIENT = new LinearGradientPaint(
				STRIPE_LIGHT_EFFECT_START, STRIPE_LIGHT_EFFECT_STOP,
				STRIPE_LIGHT_EFFECT_FRACTIONS, STRIPE_LIGHT_EFFECT_COLORS);

		g2.setPaint(STRIPE_LIGHT_EFFECT_GRADIENT);
		g2.fill(STRIPE_LIGHT_EFFECT);

		// Create top reflection effect
		final Ellipse2D TOP_LIGHT_EFFECT = new Ellipse2D.Float(17, 11, 52, 21);

		final Point2D TOP_LIGHT_EFFECT_START = new Point2D.Float(0, 11);
		final Point2D TOP_LIGHT_EFFECT_STOP = new Point2D.Float(0, 32);

		final float[] TOP_LIGHT_EFFECT_FRACTIONS = { 0.0f, 1.0f };

		final Color[] TOP_LIGHT_EFFECT_COLORS = {
				new Color(1.0f, 1.0f, 1.0f, 0.5f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) };

		final LinearGradientPaint TOP_LIGHT_EFFECT_GRADIENT = new LinearGradientPaint(
				TOP_LIGHT_EFFECT_START, TOP_LIGHT_EFFECT_STOP,
				TOP_LIGHT_EFFECT_FRACTIONS, TOP_LIGHT_EFFECT_COLORS);

		g2.setPaint(TOP_LIGHT_EFFECT_GRADIENT);
		g2.fill(TOP_LIGHT_EFFECT);

		// Create small top reflection effect
		final Ellipse2D SMALL_TOP_LIGHT_EFFECT = new Ellipse2D.Float(39, 3, 4,
				6);

		final Point2D SMALL_TOP_LIGHT_EFFECT_START = new Point2D.Float(0, 3);
		final Point2D SMALL_TOP_LIGHT_EFFECT_STOP = new Point2D.Float(0, 9);

		final float[] SMALL_TOP_LIGHT_EFFECT_FRACTIONS = { 0.0f, 1.0f };

		final Color[] SMALL_TOP_LIGHT_EFFECT_COLORS = {
				new Color(1.0f, 1.0f, 1.0f, 0.3f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) };

		final LinearGradientPaint SMALL_TOP_LIGHT_EFFECT_GRADIENT = new LinearGradientPaint(
				SMALL_TOP_LIGHT_EFFECT_START, SMALL_TOP_LIGHT_EFFECT_STOP,
				SMALL_TOP_LIGHT_EFFECT_FRACTIONS, SMALL_TOP_LIGHT_EFFECT_COLORS);

		g2.setPaint(SMALL_TOP_LIGHT_EFFECT_GRADIENT);
		g2.fill(SMALL_TOP_LIGHT_EFFECT);

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createNumber(int number, boolean active) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(62, 97,
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

		GeneralPath numberPath = new GeneralPath();

		switch (number) {
		case 0:
			numberPath = new GeneralPath(new Ellipse2D.Float(4, 6, 46, 77));
			break;
		case 1:
			numberPath = new GeneralPath(new Ellipse2D.Float(25, 1, 3, 85));
			break;
		case 2:
			numberPath = new GeneralPath();
			numberPath.moveTo(2, 23);
			numberPath.lineTo(3, 22);
			numberPath.lineTo(6, 16);
			numberPath.lineTo(9, 13);
			numberPath.lineTo(13, 10);
			numberPath.lineTo(18, 7);
			numberPath.lineTo(25, 6);
			numberPath.lineTo(31, 6);
			numberPath.lineTo(37, 8);
			numberPath.lineTo(44, 12);
			numberPath.lineTo(47, 18);
			numberPath.lineTo(49, 25);
			numberPath.lineTo(48, 31);
			numberPath.lineTo(46, 37);
			numberPath.lineTo(42, 39);
			numberPath.lineTo(36, 42);
			numberPath.lineTo(29, 44);
			numberPath.lineTo(22, 47);
			numberPath.lineTo(14, 54);
			numberPath.lineTo(9, 60);
			numberPath.lineTo(5, 68);
			numberPath.lineTo(4, 76);
			numberPath.lineTo(3, 82);
			numberPath.lineTo(51, 82);
			break;
		case 3:
			numberPath = new GeneralPath();
			numberPath.moveTo(3, 6);
			numberPath.lineTo(48, 6);
			numberPath.lineTo(48, 8);
			numberPath.lineTo(28, 34);
			numberPath.lineTo(31, 36);
			numberPath.lineTo(35, 37);
			numberPath.lineTo(41, 40);
			numberPath.lineTo(45, 46);
			numberPath.lineTo(49, 53);
			numberPath.lineTo(49, 60);
			numberPath.lineTo(48, 67);
			numberPath.lineTo(46, 71);
			numberPath.lineTo(43, 75);
			numberPath.lineTo(38, 80);
			numberPath.lineTo(27, 83);
			numberPath.lineTo(22, 83);
			numberPath.lineTo(17, 82);
			numberPath.lineTo(12, 80);
			numberPath.lineTo(8, 77);
			numberPath.lineTo(3, 71);
			break;
		case 4:
			numberPath = new GeneralPath();
			numberPath.moveTo(50, 59);
			numberPath.lineTo(4, 59);
			numberPath.lineTo(2, 58);
			numberPath.lineTo(1, 56);
			numberPath.lineTo(35, 4);
			numberPath.lineTo(35, 83);
			break;
		case 5:
			numberPath = new GeneralPath();
			numberPath.moveTo(44, 6);
			numberPath.lineTo(10, 6);
			numberPath.lineTo(6, 41);
			numberPath.lineTo(8, 41);
			numberPath.lineTo(14, 37);
			numberPath.lineTo(21, 36);
			numberPath.lineTo(30, 36);
			numberPath.lineTo(38, 39);
			numberPath.lineTo(49, 53);
			numberPath.lineTo(49, 61);
			numberPath.lineTo(47, 69);
			numberPath.lineTo(44, 75);
			numberPath.lineTo(38, 80);
			numberPath.lineTo(30, 82);
			numberPath.lineTo(24, 83);
			numberPath.lineTo(18, 82);
			numberPath.lineTo(12, 79);
			numberPath.lineTo(6, 74);
			numberPath.lineTo(3, 69);
			break;
		case 6:
			numberPath = new GeneralPath();
			numberPath.moveTo(28, 1);
			numberPath.lineTo(4, 48);
			numberPath.append(new Ellipse2D.Float(2, 34, 48, 48), false);
			break;
		case 7:
			numberPath = new GeneralPath();
			numberPath.moveTo(2, 3);
			numberPath.lineTo(51, 3);
			numberPath.lineTo(25, 85);
			break;
		case 8:
			numberPath = new GeneralPath(new Ellipse2D.Float(1, 39, 50, 45));
			numberPath.append(new Ellipse2D.Float(6, 2, 42, 37), false);
			break;
		case 9:
			numberPath = new GeneralPath();
			numberPath.moveTo(30, 85);
			numberPath.lineTo(51, 32);
			numberPath.append(new Ellipse2D.Float(3, 3, 48, 48), false);
			break;
		}

		// Translate 5,5 because of the linewidth of 12px for the glowing effect
		g2.translate(5, 5);

		if (active) {
			// Draw active number with glow effect
			for (float width = 12; width > 0; width -= 2f) {
				g2.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_MITER));
				g2.setColor(COLOR_ARRAY[(int) (width / 2 - 1)]);
				g2.draw(numberPath);
			}
		} else {
			// Draw inactive number
			g2.setColor(new Color(0.2f, 0.2f, 0.2f, 0.6f));
			g2.draw(numberPath);
		}

		g2.dispose();

		return IMAGE;
	}

	private void createNumberStack() {
		// Order of numbers in stack
		// 1 0 2 3 9 4 8 5 7 6

		if (this.number > -1) {
			// 1
			if (this.number == 1) {
				numberStack[0] = ACTIVE_NUMBER_ARRAY[1];
			} else {
				numberStack[0] = INACTIVE_NUMBER_ARRAY[1];
			}

			// 0
			if (this.number == 0) {
				numberStack[1] = ACTIVE_NUMBER_ARRAY[0];
			} else {
				numberStack[1] = INACTIVE_NUMBER_ARRAY[0];
			}

			// 2
			if (this.number == 2) {
				numberStack[2] = ACTIVE_NUMBER_ARRAY[2];
			} else {
				numberStack[2] = INACTIVE_NUMBER_ARRAY[2];
			}

			// 3
			if (this.number == 3) {
				numberStack[3] = ACTIVE_NUMBER_ARRAY[3];
			} else {
				numberStack[3] = INACTIVE_NUMBER_ARRAY[3];
			}

			// 9
			if (this.number == 9) {
				numberStack[4] = ACTIVE_NUMBER_ARRAY[9];
			} else {
				numberStack[4] = INACTIVE_NUMBER_ARRAY[9];
			}

			// 4
			if (this.number == 4) {
				numberStack[5] = ACTIVE_NUMBER_ARRAY[4];
			} else {
				numberStack[5] = INACTIVE_NUMBER_ARRAY[4];
			}

			// 8
			if (this.number == 8) {
				numberStack[6] = ACTIVE_NUMBER_ARRAY[8];
			} else {
				numberStack[6] = INACTIVE_NUMBER_ARRAY[8];
			}

			// 5
			if (this.number == 5) {
				numberStack[7] = ACTIVE_NUMBER_ARRAY[5];
			} else {
				numberStack[7] = INACTIVE_NUMBER_ARRAY[5];
			}

			// 7
			if (this.number == 7) {
				numberStack[8] = ACTIVE_NUMBER_ARRAY[7];
			} else {
				numberStack[8] = INACTIVE_NUMBER_ARRAY[7];
			}

			// 6
			if (this.number == 6) {
				numberStack[9] = ACTIVE_NUMBER_ARRAY[6];
			} else {
				numberStack[9] = INACTIVE_NUMBER_ARRAY[6];
			}
		} else {
			for (int i = 0; i < 10; i++) {
				numberStack[i] = INACTIVE_NUMBER_ARRAY[i];
			}
		}
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		if (number < 0) {
			number = -1;
		}
		if (number > 9) {
			number = 9;
		}
		this.number = number;
		createNumberStack();
		repaint();
	}

	@Override
	public Dimension getSize() {
		return new Dimension(86, 146);
	}

	@Override
	public Dimension getSize(Dimension dim) {
		return super.getSize(new Dimension(86, 146));
	}

	@Override
	public String toString() {
		return "NixieNumber";
	}
}
