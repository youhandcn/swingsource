package instrumentchar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * 仪表盘
 */
public class Gauge extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 显示信息的默认字体
	private static final Font FONT = new Font("宋体", 0, 18);
	// 表盘显示最小值
	private float min = -10;
	// 表盘显示最大值
	private float max = 100;
	// 默认显示信息
	private String unitString = "温度";

	// 亮度
	public enum Luminosity {
		LIGHT, DARK
	};

	// 默认亮度
	public Luminosity brightness = Luminosity.DARK;

	private BufferedImage backgroundImage = null;
	private BufferedImage pointerImage = null;
	private BufferedImage highlightImage = null;
	private BufferedImage digits = null;

	private double offsetX = 0;
	private double offsetY = 0;
	private double rotationCenterX = 0;
	private double rotationCenterY = 0;
	private double value = 0;
	private double factor = ((2 * Math.PI) * 0.834d) / (max - min);
	private double rotationOffset = -100 * Math.PI / 120;
	// 数字0-9的图片
	private final BufferedImage[] DIGIT_ARRAY = { getDigit(0), getDigit(1),
			getDigit(2), getDigit(3), getDigit(4), getDigit(5), getDigit(6),
			getDigit(7), getDigit(8), getDigit(9) };
	// 数字负号的图片
	private final BufferedImage DIGIT_MINUS = getDigit(-1);
	// 数字小数点的图片
	private final BufferedImage DIGIT_DOT = getDigit(-2);
	private AffineTransform oldTransform;

	// 设定仪表图的大小
	private Dimension dimension = new Dimension(256, 256);

	public Gauge() {
		super();
		setPreferredSize(dimension);
		setSize(dimension);
		init();
	}

	private void init() {
		if (backgroundImage != null) {
			backgroundImage.flush();
		}
		backgroundImage = createInstrumentBackground();
		if (pointerImage != null) {
			pointerImage.flush();
		}
		if (highlightImage != null) {
			highlightImage.flush();
		}
		highlightImage = createHighlight();

		pointerImage = createPointer();
		offsetX = getWidth() / 2.0f - pointerImage.getWidth() / 2.0f;
		offsetY = getHeight() / 2.0f - pointerImage.getHeight()
				+ pointerImage.getWidth() / 2.0f;
		rotationCenterX = pointerImage.getWidth() / 2.0d;
		rotationCenterY = pointerImage.getHeight() - pointerImage.getWidth()
				/ 2.0d;
		factor = ((2 * Math.PI) * 0.834d) / (max - min);
		setDigits(value);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2.drawImage(backgroundImage, 0, 0, this);
		g2.drawImage(digits, (int) (dimension.width * 0.325f),
				(int) (dimension.height * 0.61f), this);

		oldTransform = g2.getTransform();

		g2.translate(offsetX, offsetY);
		g2.rotate(rotationOffset + (value - min) * factor, rotationCenterX,
				rotationCenterY);
		g2.drawImage(pointerImage, 0, 0, this);

		g2.setTransform(oldTransform);
		g2.drawImage(highlightImage, 0, 0, this);

		g2.dispose();
	}

	private BufferedImage createPointer() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(
				(int) (getWidth() * 0.06f), (int) (getHeight() * 0.37f),
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

		GeneralPath pointerLeft = new GeneralPath();
		pointerLeft.moveTo(1, IMAGE.getHeight() * 0.92f);
		pointerLeft.lineTo(IMAGE.getWidth() / 2.0f, 0);
		pointerLeft.lineTo(IMAGE.getWidth() / 2.0f, IMAGE.getHeight() * 0.92f);
		pointerLeft.closePath();
		GeneralPath pointerRight = new GeneralPath();
		pointerRight.moveTo(IMAGE.getWidth() - 1, IMAGE.getHeight() * 0.92f);
		pointerRight.lineTo(IMAGE.getWidth() / 2.0f, 0);
		pointerRight.lineTo(IMAGE.getWidth() / 2.0f, IMAGE.getHeight() * 0.92f);
		pointerRight.closePath();

		g2.setColor(new Color(0x970000));
		g2.fill(pointerRight);
		g2.setColor(new Color(0xFF0000));
		g2.fill(pointerLeft);

		final Ellipse2D OUTER_KNOB = new Ellipse2D.Double(0, IMAGE.getHeight()
				- IMAGE.getWidth(), IMAGE.getWidth(), IMAGE.getWidth());
		final Point2D OUTER_KNOB_START = new Point2D.Double(0, IMAGE
				.getHeight()
				- IMAGE.getWidth());
		final Point2D OUTER_KNOB_STOP = new Point2D.Double(0, IMAGE.getHeight());
		final float[] OUTER_KNOB_FRACTIONS = { 0.0f, 1.0f };
		final Color[] OUTER_KNOB_COLORS = { new Color(0x666666),
				new Color(0x111111) };
		final LinearGradientPaint OUTER_KNOB_PAINT = new LinearGradientPaint(
				OUTER_KNOB_START, OUTER_KNOB_STOP, OUTER_KNOB_FRACTIONS,
				OUTER_KNOB_COLORS);

		g2.setPaint(OUTER_KNOB_PAINT);
		g2.fill(OUTER_KNOB);

		final Ellipse2D INNER_KNOB = new Ellipse2D.Double(1, IMAGE.getHeight()
				- IMAGE.getWidth() + 1, IMAGE.getWidth() - 2.0f, IMAGE
				.getWidth() - 2.0f);
		final Point2D INNER_KNOB_CENTER = new Point2D.Double(
				IMAGE.getWidth() / 2, IMAGE.getHeight()
						- (IMAGE.getWidth() / 2));
		final float[] INNER_KNOB_FRACTIONS = { 0.0f, 0.2f, 1.0f };
		final Color[] INNER_KNOB_COLORS = { new Color(0xFFFFFF),
				new Color(0x999999), new Color(0x333333) };
		final RadialGradientPaint INNER_KNOB_PAINT = new RadialGradientPaint(
				INNER_KNOB_CENTER, (IMAGE.getWidth() - 2) / 2,
				INNER_KNOB_FRACTIONS, INNER_KNOB_COLORS);

		g2.setPaint(INNER_KNOB_PAINT);
		g2.fill(INNER_KNOB);

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createInstrumentBackground() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(getWidth(),
				getHeight(), Transparency.TRANSLUCENT);

		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		// ******************* INSTRUMENT FRAME
		// *****************************************
		final Point2D FRAME1_CENTER = new Point2D.Double(getWidth() / 2.0f,
				getHeight() / 2.0f);
		final Point2D FRAME2_START = new Point2D.Double(0,
				(getHeight() - (getHeight() * 0.92d)) / 2);
		final Point2D FRAME2_STOP = new Point2D.Double(0, getHeight()
				- (getHeight() - (getHeight() * 0.92d)) / 2);
		final Point2D FRAME3_CENTER = new Point2D.Double(getWidth() / 2.0f,
				getHeight() / 2.0f);

		final float[] FRAME1_FRACTIONS = { 0.75f, 1.0f };

		final float[] FRAME2_FRACTIONS = { 0.0f, 1.0f };

		final float[] FRAME3_FRACTIONS = { 0.94f, 1.0f };

		final Color[] FRAME1_COLORS = { new Color(0xFFFFFF),
				new Color(0x7B7B7B) };

		final Color[] FRAME2_COLORS = { new Color(0x666666),
				new Color(0xFFFFFF) };

		final Color[] FRAME3_COLORS = { new Color(0xFFFFFF),
				new Color(0xCCCCCC) };

		final RadialGradientPaint FRAME1_PAINT = new RadialGradientPaint(
				FRAME1_CENTER, getWidth() / 2.0f, FRAME1_FRACTIONS,
				FRAME1_COLORS);
		final LinearGradientPaint FRAME2_PAINT = new LinearGradientPaint(
				FRAME2_START, FRAME2_STOP, FRAME2_FRACTIONS, FRAME2_COLORS);
		final RadialGradientPaint FRAME3_PAINT = new RadialGradientPaint(
				FRAME3_CENTER, (getWidth() * 0.86f) / 2.0f, FRAME3_FRACTIONS,
				FRAME3_COLORS);

		g2.setPaint(FRAME1_PAINT);
		g2.fill(getFrameShape1());

		g2.setPaint(FRAME2_PAINT);
		g2.fill(getFrameShape2());
		g2.setColor(new Color(0xC7C7C7));
		g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g2.draw(getFrameShape2());

		g2.setPaint(FRAME3_PAINT);
		g2.fill(getFrameShape3());

		// ******************* INSTRUMENT BACKGROUND
		// ************************************
		final Point2D BACKGROUND_START = new Point2D.Double(0,
				(getWidth() - (getWidth() * 0.80d)) / 2);
		final Point2D BACKGROUND_STOP = new Point2D.Double(0, getHeight()
				- (getWidth() - (getWidth() * 0.80d)) / 2);

		final float[] BACKGROUND_FRACTIONS = { 0.0f, 0.45f, 1.0f };

		final Color[] BACKGROUND_COLORS;
		switch (brightness) {
		case DARK:
			BACKGROUND_COLORS = new Color[] { new Color(0x000000),
					new Color(0x333333), new Color(0x999999) };
			break;
		case LIGHT:
			BACKGROUND_COLORS = new Color[] { new Color(0xFFFFFF),
					new Color(0xCCCCCC), new Color(0x666666) };
			break;
		default:
			BACKGROUND_COLORS = new Color[] { new Color(0x000000),
					new Color(0x333333), new Color(0x999999) };
			break;
		}

		final LinearGradientPaint BACKGROUND_PAINT = new LinearGradientPaint(
				BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS,
				BACKGROUND_COLORS);

		g2.setPaint(BACKGROUND_PAINT);
		g2.fill(getBackgroundShape());

		// ******************* INSTRUMENT BACKGROUND INNER SHADOW
		// ***********************
		final Point2D INNER_SHADOW_CENTER = new Point2D.Double(
				getWidth() / 2.0d, getHeight() / 2.0d);
		final float INNER_SHADOW_RADIUS = (getWidth() * 0.8f) / 2.0f;

		final float[] INNER_SHADOW_FRACTIONS = { 0.8f, 1.0f };

		final Color[] INNER_SHADOW_COLORS = {
				new Color(0.0f, 0.0f, 0.0f, 0.0f),
				new Color(0.0f, 0.0f, 0.0f, 0.3f) };

		final RadialGradientPaint INNER_SHADOW_PAINT = new RadialGradientPaint(
				INNER_SHADOW_CENTER, INNER_SHADOW_RADIUS,
				INNER_SHADOW_FRACTIONS, INNER_SHADOW_COLORS);

		g2.setPaint(INNER_SHADOW_PAINT);
		g2.fill(getBackgroundShape());

		// ******************* DIGITAL PANEL
		// ************************************************
		final Point2D DIGITAL1_START = new Point2D.Double(0,
				getHeight() * 0.60f);
		final Point2D DIGITAL1_STOP = new Point2D.Double(0, getHeight() * 0.60f
				+ getHeight() * 0.13f);

		final float[] DIGITAL1_FRACTIONS = { 0.0f, 1.0f };

		final Color[] DIGITAL1_COLORS = { new Color(0x333333),
				new Color(0x999999) };

		final LinearGradientPaint DIGITAL1_PAINT = new LinearGradientPaint(
				DIGITAL1_START, DIGITAL1_STOP, DIGITAL1_FRACTIONS,
				DIGITAL1_COLORS);

		g2.setPaint(DIGITAL1_PAINT);
		g2.fill(new RoundRectangle2D.Double(getWidth() * 0.31f,
				getHeight() * 0.60f, getWidth() * 0.39f, getHeight() * 0.13f,
				6, 6));

		final Point2D DIGITAL2_START = new Point2D.Double(0,
				getHeight() * 0.605185f);
		final Point2D DIGITAL2_STOP = new Point2D.Double(0, getHeight()
				* 0.605185f + getHeight() * 0.12f);

		final float[] DIGITAL2_FRACTIONS = { 0.0f, 1.0f };

		final Color[] DIGITAL2_COLORS = { new Color(0x000000),
				new Color(0x333333) };

		final LinearGradientPaint DIGITAL2_PAINT = new LinearGradientPaint(
				DIGITAL2_START, DIGITAL2_STOP, DIGITAL2_FRACTIONS,
				DIGITAL2_COLORS);

		g2.setPaint(DIGITAL2_PAINT);
		g2.fill(new RoundRectangle2D.Double(getWidth() * 0.31481f,
				getHeight() * 0.605181f, getWidth() * 0.38f,
				getHeight() * 0.12f, 5, 5));

		// ******************* TICKMARKS
		// ****************************************************
		createTickmarks(g2);

		// ******************* MIN-MAX BLOCKS
		// ***********************************************
		final Point2D BLOCK1_CENTER = new Point2D.Double(
				getWidth() * 0.365f + 2.0, getHeight() * 0.74f + 2.0);
		final Point2D BLOCK2_CENTER = new Point2D.Double(
				getWidth() * 0.61f + 2.0, getHeight() * 0.74f + 2.0);

		final float[] BLOCK_FRACTIONS = { 0.0f, 1.0f };

		final Color[] BLOCK_COLORS = { new Color(0xFFFFFF), new Color(0x333333) };
		final RadialGradientPaint BLOCK1_PAINT = new RadialGradientPaint(
				BLOCK1_CENTER, 3, BLOCK_FRACTIONS, BLOCK_COLORS);
		final RadialGradientPaint BLOCK2_PAINT = new RadialGradientPaint(
				BLOCK2_CENTER, 3, BLOCK_FRACTIONS, BLOCK_COLORS);
		g2.setPaint(BLOCK1_PAINT);
		g2.fill(new Ellipse2D.Double(getWidth() * 0.365f, getHeight() * 0.74f,
				6, 6));
		g2.setPaint(BLOCK2_PAINT);
		g2.fill(new Ellipse2D.Double(getWidth() * 0.61f, getHeight() * 0.74f,
				6, 6));

		// ******************* UNIT STRING
		// **************************************************
		g2.setFont(FONT);

		switch (brightness) {
		case LIGHT:
			g2.setColor(new Color(0x000000));
			break;
		case DARK:
			g2.setColor(new Color(0xFFFFFF));
			break;
		default:
			g2.setColor(new Color(0xFFFFFF));
			break;
		}

		final FontMetrics METRICS = g2.getFontMetrics();
		final Rectangle2D TEXT_BOUNDARY = METRICS.getStringBounds(
				getUnitString(), g2);

		g2.drawString(getUnitString(), (int) ((getWidth() - TEXT_BOUNDARY
				.getWidth()) / 2 - 1), getHeight() * 0.80f
				- METRICS.getHeight()
				- ((getHeight() - METRICS.getHeight()) / 2)
				+ METRICS.getAscent() - 1);

		// ******************* LENS EFFECT
		// **************************************************
		final Point2D LENS_EFFECT_START = new Point2D.Double(0,
				getHeight() * 0.12037f);
		final Point2D LENS_EFFECT_STOP = new Point2D.Double(0, getHeight()
				* 0.12037f + getHeight() * 0.53f);

		final float[] LENS_EFFECT_FRACTIONS = { 0.0f, 0.4f };

		final Color[] LENS_EFFECT_COLORS = { new Color(1.0f, 1.0f, 1.0f, 0.2f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f) };

		final LinearGradientPaint LENS_EFFECT_PAINT = new LinearGradientPaint(
				LENS_EFFECT_START, LENS_EFFECT_STOP, LENS_EFFECT_FRACTIONS,
				LENS_EFFECT_COLORS);

		g2.setPaint(LENS_EFFECT_PAINT);
		g2
				.fill(new Ellipse2D.Double(getWidth() * 0.25f,
						getHeight() * 0.12037f, getWidth() * 0.51f,
						getHeight() * 0.53f));

		// Release graphics2d object
		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createHighlight() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(getWidth(),
				getHeight(), Transparency.TRANSLUCENT);

		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		Shape highlightShape = getHighlightShape();
		final Point2D HIGHLIGHT_START = new Point2D.Double(0,
				(getHeight() - (getHeight() * 0.92d)) / 2);
		final Point2D HIGHLIGHT_STOP = new Point2D.Double(0, HIGHLIGHT_START
				.getY()
				+ highlightShape.getBounds2D().getHeight());

		final float[] HIGHLIGHT_FRACTIONS = { 0.0f, 1.0f };

		final Color[] HIGHLIGHT_COLORS = { new Color(1.0f, 1.0f, 1.0f, 0.25f),
				new Color(1.0f, 1.0f, 1.0f, 0.05f) };
		LinearGradientPaint HIGHLIGHT_PAINT = new LinearGradientPaint(
				HIGHLIGHT_START, HIGHLIGHT_STOP, HIGHLIGHT_FRACTIONS,
				HIGHLIGHT_COLORS);
		g2.setPaint(HIGHLIGHT_PAINT);
		g2.fill(highlightShape);

		g2.dispose();

		return IMAGE;
	}

	private Shape getFrameShape1() {
		return new Ellipse2D.Double(0, 0, getWidth(), getHeight());
	}

	private Shape getFrameShape2() {
		double origin = (getWidth() - (getWidth() * 0.92d)) / 2;
		return new Ellipse2D.Double(origin, origin, getWidth() * 0.92d,
				getHeight() * 0.92d);
	}

	private Shape getFrameShape3() {
		double origin = (getWidth() - (getWidth() * 0.86d)) / 2;
		return new Ellipse2D.Double(origin, origin, getWidth() * 0.86d,
				getHeight() * 0.86d);
	}

	private Shape getBackgroundShape() {
		double origin = (getWidth() - (getWidth() * 0.80d)) / 2;
		return new Ellipse2D.Double(origin, origin, getWidth() * 0.80d,
				getHeight() * 0.80d);
	}

	private Shape getHighlightShape() {
		Shape secondShape = (Shape) (new Ellipse2D.Double(
				(getWidth() - getWidth() * 1.5f) / 2.0f, getHeight() * 0.4f,
				getWidth() * 1.5f, getHeight()));
		final Area result = new Area(getFrameShape2());
		result.subtract(new Area(secondShape));
		return result;
	}

	private void createTickmarks(Graphics2D g2) {
		// Store former transformation
		AffineTransform FORMER_TRANSFORM = g2.getTransform();

		final Font STD_FONT;
		final BasicStroke MEDIUM_STROKE;
		final BasicStroke THIN_STROKE;
		final int TEXT_DISTANCE;
		final int MIN_LENGTH;
		final int MED_LENGTH;
		final int MAX_LENGTH;
		if (getWidth() < 200) {
			MEDIUM_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL);
			THIN_STROKE = new BasicStroke(0.5f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL);
			STD_FONT = new Font("Verdana", 0, 6);
			TEXT_DISTANCE = 12;
			MIN_LENGTH = 2;
			MED_LENGTH = 4;
			MAX_LENGTH = 6;
		} else {
			MEDIUM_STROKE = new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL);
			THIN_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL);
			STD_FONT = new Font("Verdana", 0, 9);
			TEXT_DISTANCE = 18;
			MIN_LENGTH = 4;
			MED_LENGTH = 6;
			MAX_LENGTH = 8;
		}
		final Color TEXT_COLOR;
		final Color TICK_COLOR;
		switch (brightness) {
		case DARK:
			TEXT_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
			TICK_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
			break;
		case LIGHT:
			TEXT_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
			TICK_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
			break;
		default:
			TEXT_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
			TICK_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
			break;
		}

		// Create the watch itself
		final float RADIUS = getWidth() * 0.38f;
		final Point2D CENTER = new Point2D.Double(getWidth() / 2.0f,
				getHeight() / 2.0f);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		// Draw ticks
		Point2D innerPoint;
		Point2D outerPoint;
		Point2D textPoint = null;
		Point2D textCenter = null;
		Line2D tick;
		int counter = 0;
		int tickCounter = 0;
		float valueCounter = min;
		float valueStep = (max - min) / 100;

		g2.setFont(STD_FONT);

		double sinValue = 0;
		double cosValue = 0;

		double step = (2.0d * Math.PI) / (120.0d);
		double freeArea = 10.0d * step;

		for (double alpha = 2 * Math.PI - freeArea; alpha >= freeArea - step; alpha -= step) {
			g2.setStroke(THIN_STROKE);
			sinValue = Math.sin(alpha);
			cosValue = Math.cos(alpha);
			textPoint = new Point2D.Double(CENTER.getX()
					+ (RADIUS - TEXT_DISTANCE) * sinValue, CENTER.getY()
					+ (RADIUS - TEXT_DISTANCE) * cosValue);
			innerPoint = new Point2D.Double(CENTER.getX()
					+ (RADIUS - MIN_LENGTH) * sinValue, CENTER.getY()
					+ (RADIUS - MIN_LENGTH) * cosValue);
			outerPoint = new Point2D.Double(CENTER.getX() + RADIUS * sinValue,
					CENTER.getY() + RADIUS * cosValue);
			g2.setColor(TICK_COLOR);

			// Different tickmark every 5 units
			if (counter == 5) {
				g2.setColor(TEXT_COLOR);
				g2.setStroke(THIN_STROKE);
				innerPoint = new Point2D.Double(CENTER.getX()
						+ (RADIUS - MED_LENGTH) * sinValue, CENTER.getY()
						+ (RADIUS - MED_LENGTH) * cosValue);
				outerPoint = new Point2D.Double(CENTER.getX() + RADIUS
						* sinValue, CENTER.getY() + RADIUS * cosValue);
			}

			// Different tickmark every 10 units plus text
			if (counter == 10 || counter == 0) {
				g2.setColor(TEXT_COLOR);
				g2.setStroke(MEDIUM_STROKE);
				innerPoint = new Point2D.Double(CENTER.getX()
						+ (RADIUS - MAX_LENGTH) * sinValue, CENTER.getY()
						+ (RADIUS - MAX_LENGTH) * cosValue);
				outerPoint = new Point2D.Double(CENTER.getX() + RADIUS
						* sinValue, CENTER.getY() + RADIUS * cosValue);

				final Rectangle2D TEXT_BOUNDARY = g2.getFontMetrics()
						.getStringBounds(Integer.toString((int) valueCounter),
								g2);
				textCenter = new Point2D.Double(TEXT_BOUNDARY.getCenterX(),
						TEXT_BOUNDARY.getCenterY());

				g2.fill(rotateTextAroundCenter(g2, Integer
						.toString((int) valueCounter),
						(int) (textPoint.getX() - textCenter.getX()),
						(int) ((textPoint.getY() + textCenter.getY()) * 2),
						Math.toDegrees(Math.PI - alpha)));

				counter = 0;
				tickCounter++;
			}

			// Draw ticks
			tick = new Line2D.Double(innerPoint.getX(), innerPoint.getY(),
					outerPoint.getX(), outerPoint.getY());
			g2.draw(tick);

			counter++;
			valueCounter += valueStep;
		}

		// Restore former transformation
		g2.setTransform(FORMER_TRANSFORM);
	}

	/**
	 * 创造显示的数字
	 */
	private BufferedImage getDigit(int digit) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage IMAGE = gfxConf.createCompatibleImage(13, 26,
				Transparency.TRANSLUCENT);

		Graphics2D g2 = (Graphics2D) IMAGE.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		// A
		GeneralPath segment_a = new GeneralPath();
		segment_a.moveTo(3, 0);
		segment_a.lineTo(9, 0);
		segment_a.moveTo(2, 1);
		segment_a.lineTo(10, 1);
		segment_a.moveTo(3, 2);
		segment_a.lineTo(9, 2);

		// B
		GeneralPath segment_b = new GeneralPath();
		segment_b.moveTo(12, 4);
		segment_b.lineTo(12, 10);
		segment_b.moveTo(11, 3);
		segment_b.lineTo(11, 11);
		segment_b.moveTo(10, 4);
		segment_b.lineTo(10, 10);

		// C
		GeneralPath segment_c = new GeneralPath();
		segment_c.moveTo(12, 16);
		segment_c.lineTo(12, 21);
		segment_c.moveTo(11, 15);
		segment_c.lineTo(11, 22);
		segment_c.moveTo(10, 16);
		segment_c.lineTo(10, 21);

		// D
		GeneralPath segment_d = new GeneralPath();
		segment_d.moveTo(3, 25);
		segment_d.lineTo(9, 25);
		segment_d.moveTo(2, 24);
		segment_d.lineTo(10, 24);
		segment_d.moveTo(3, 23);
		segment_d.lineTo(9, 23);

		// E
		GeneralPath segment_e = new GeneralPath();
		segment_e.moveTo(2, 16);
		segment_e.lineTo(2, 21);
		segment_e.moveTo(1, 15);
		segment_e.lineTo(1, 22);
		segment_e.moveTo(0, 16);
		segment_e.lineTo(0, 21);

		// F
		GeneralPath segment_f = new GeneralPath();
		segment_f.moveTo(2, 4);
		segment_f.lineTo(2, 10);
		segment_f.moveTo(1, 3);
		segment_f.lineTo(1, 11);
		segment_f.moveTo(0, 4);
		segment_f.lineTo(0, 10);

		// G
		GeneralPath segment_g = new GeneralPath();
		segment_g.moveTo(3, 12);
		segment_g.lineTo(9, 12);
		segment_g.moveTo(2, 13);
		segment_g.lineTo(10, 13);
		segment_g.moveTo(3, 14);
		segment_g.lineTo(9, 14);

		g2.setColor(new Color(0xFF0000));

		switch (digit) {
		case 0:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			break;
		case 1:
			g2.draw(segment_b);
			g2.draw(segment_c);
			break;
		case 2:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_g);
			break;
		case 3:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_g);
			break;
		case 4:
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 5:
			g2.draw(segment_a);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 6:
			g2.draw(segment_a);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 7:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			break;
		case 8:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 9:
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case -1:
			g2.draw(segment_g);
			break;
		case -2:
			g2.fillOval(10, 22, 3, 3);
			break;
		}

		g2.dispose();

		return IMAGE;
	}

	/**
	 * 设置显示数字
	 */
	private void setDigits(double value) {
		if (this.digits != null) {
			this.digits.flush();
		}
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage image = gfxConf.createCompatibleImage(
				(int) (getWidth() * 0.38f), (int) (getHeight() * 0.12f),
				Transparency.TRANSLUCENT);

		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		if (value < 0) {
			g2.drawImage(DIGIT_MINUS, 0, 2, this);
			value = Math.abs(value);
		}
		g2.drawImage(DIGIT_ARRAY[((int) value % 100) / 10], 13, 2, this);
		g2.drawImage(DIGIT_ARRAY[(int) value % 10], 29, 2, this);
		g2.drawImage(DIGIT_DOT, 33, 2, this);
		g2.drawImage(DIGIT_ARRAY[(int) ((value * 10.0) % 10)], 48, 2, this);
		g2.drawImage(DIGIT_ARRAY[(int) (((value * 100.0) % 100) % 10)], 64, 2,
				this);
		g2.drawImage(DIGIT_ARRAY[(int) (((value * 1000.0) % 1000) % 10)], 80,
				2, this);

		g2.dispose();

		this.digits = image;
	}

	private Shape rotateTextAroundCenter(Graphics2D g2, final String TEXT,
			final int TEXT_POSITION_X, final int TEXT_POSITION_Y,
			final double ROTATION_ANGLE) {
		final Rectangle2D TEXT_BOUNDARY = g2.getFontMetrics().getStringBounds(
				TEXT, g2).getBounds2D();

		final FontRenderContext RENDER_CONTEXT = g2.getFontRenderContext();
		final GlyphVector GLYPH_VECTOR = g2.getFont().createGlyphVector(
				RENDER_CONTEXT, TEXT);

		final Shape GLYPH = GLYPH_VECTOR.getOutline((int) -TEXT_BOUNDARY
				.getCenterX(), 2 * (int) TEXT_BOUNDARY.getCenterY());

		final AffineTransform OLD_TRANSFORM = g2.getTransform();
		g2.translate(TEXT_POSITION_X + TEXT_BOUNDARY.getCenterX(),
				TEXT_POSITION_Y / 2 - TEXT_BOUNDARY.getCenterY()
						+ TEXT_BOUNDARY.getHeight());

		g2.rotate(Math.toRadians(ROTATION_ANGLE), -TEXT_BOUNDARY.getCenterX()
				+ TEXT_BOUNDARY.getWidth() / 2, TEXT_BOUNDARY.getCenterY()
				- TEXT_BOUNDARY.getHeight() / 2);
		g2.fill(GLYPH);
		g2.setTransform(OLD_TRANSFORM);

		return GLYPH;
	}

	public void setValue(double value) {
		if (value < min) {
			value = min;
		}
		if (value > max) {
			value = max;
		}
		this.value = value;
		setDigits(value);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	public double getValue() {
		return this.value;
	}

	public void setBrightness(Luminosity brightness) {
		this.brightness = brightness;
		init();
		repaint();
	}

	public void setMin(float min) {
		this.min = min;
		init();
		repaint();
	}

	public void setMax(float max) {
		this.max = max;
		init();
		repaint();
	}

	public String getUnitString() {
		return this.unitString;
	}

	public void setUnitString(String unitString) {
		this.unitString = unitString;
		init();
	}

	@Override
	public void setSize(Dimension dimension) {
		if (dimension == null) {
			throw new IllegalArgumentException("inappropriate argument.");
		}
		this.dimension = dimension;
		setSize(dimension.width, dimension.height);
	}

	@Override
	public void setSize(int width, int height) {
		if (width >= height) {
			this.dimension = new Dimension(width, width);
			super.setSize(width, width);
		} else {
			this.dimension = new Dimension(height, height);
			super.setSize(height, height);
		}
		repaint();
	}
}
