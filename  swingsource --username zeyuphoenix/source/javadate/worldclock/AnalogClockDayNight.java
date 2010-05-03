/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldclock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * 
 */
public class AnalogClockDayNight extends JComponent implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double ANGLE_STEP = 6;
	private final double NIGHT_DAY_ANGLE_STEP = 0.25;
	private final Timer CLOCK_TIMER = new Timer(100, this);
	private double minutePointerAngle = Calendar.getInstance().get(
			Calendar.MINUTE)
			* ANGLE_STEP;
	private double hourPointerAngle = Calendar.getInstance().get(Calendar.HOUR)
			* ANGLE_STEP * 5 + 0.5
			* Calendar.getInstance().get(Calendar.MINUTE);
	private double secondPointerAngle = Calendar.getInstance().get(
			Calendar.SECOND)
			* ANGLE_STEP;
	private double nightDayAngle = (Calendar.getInstance().get(
			Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(
			Calendar.MINUTE))
			* NIGHT_DAY_ANGLE_STEP;
	private Rectangle2D hourPointer;
	private Rectangle2D minutePointer;
	private GeneralPath secondPointer;
	private Ellipse2D centerKnob;
	private Rectangle2D smallTick;
	private Rectangle2D bigTick;
	private final java.awt.Color SECOND_COLOR = new java.awt.Color(0xF00000);
	private final java.awt.Color SHADOW_COLOR = new java.awt.Color(0.0f, 0.0f,
			0.0f, 0.65f);
	private java.awt.image.BufferedImage backgroundImage = null;
	private java.awt.image.BufferedImage nightDayImage = null;
	private final java.awt.Stroke STAR_STROKE = new java.awt.BasicStroke(0.9f,
			java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND);

	public enum TYPE {
		LIGHT, DARK
	};

	private TYPE type = TYPE.DARK;
	private static final String TIMEOFDAY_PROPERTY = "timeOfDay";
	private int timeOfDay = 0;
	private static final String DAYOFFSET_PROPERTY = "dayOffset";
	private int dayOffset = 0;
	private java.awt.Color currentForegroundColor;
	private java.awt.Color[] currentBackgroundColor;
	private Point2D center;
	private int timeZoneOffsetHour = 0;
	private int timeZoneOffsetMinute = 0;
	private int hour;
	private int minute;
	boolean am = (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM);
	// Flags
	private boolean secondPointerVisible = true;
	private boolean autoType = true;

	public AnalogClockDayNight() {
		super();
		setSize(66, 66);

		init();

		CLOCK_TIMER.start();
	}

	private void init() {
		// Rotation center
		this.center = new Point2D.Float(getWidth() / 2.0f, getWidth() / 2.0f);

		// Hour pointer
		final double HOUR_POINTER_WIDTH = getWidth() * 0.0545454545;
		final double HOUR_POINTER_HEIGHT = getWidth() * 0.3090909091;
		this.hourPointer = new Rectangle2D.Double(center.getX()
				- (HOUR_POINTER_WIDTH / 2), (getWidth() * 0.1909090909),
				HOUR_POINTER_WIDTH, HOUR_POINTER_HEIGHT);

		// Minute pointer
		final double MINUTE_POINTER_WIDTH = getWidth() * 0.0454545455;
		final double MINUTE_POINTER_HEIGHT = getWidth() * 0.4363636364;
		this.minutePointer = new Rectangle2D.Double(center.getX()
				- (MINUTE_POINTER_WIDTH / 2), (getWidth() * 0.0636363636),
				MINUTE_POINTER_WIDTH, MINUTE_POINTER_HEIGHT);

		// Second pointer
		final GeneralPath SECOND_AREA = new GeneralPath();
		SECOND_AREA.moveTo(getWidth() * 0.4863636364, center.getY());
		SECOND_AREA.lineTo(getWidth() * 0.5136363636, center.getY());
		SECOND_AREA
				.lineTo(getWidth() * 0.5045454545, getWidth() * 0.0363636364);
		SECOND_AREA
				.lineTo(getWidth() * 0.4954545455, getWidth() * 0.0363636364);
		SECOND_AREA.closePath();
		Area second = new Area(SECOND_AREA);
		second.add(new Area(new Ellipse2D.Double(getWidth() * 0.4545454545,
				getWidth() * 0.1454545455, getWidth() * 0.0909090909,
				getWidth() * 0.0909090909)));
		second.subtract(new Area(new Ellipse2D.Double(
				getWidth() * 0.4636363636, getWidth() * 0.1545454545,
				getWidth() * 0.0727272727, getWidth() * 0.0727272727)));
		this.secondPointer = new GeneralPath(second);

		// Center knob
		final double CENTER_KNOB_DIAMETER = getWidth() * 0.090909;
		this.centerKnob = new Ellipse2D.Double(center.getX()
				- CENTER_KNOB_DIAMETER / 2, center.getY()
				- CENTER_KNOB_DIAMETER / 2, CENTER_KNOB_DIAMETER,
				CENTER_KNOB_DIAMETER);

		// Minute tick mark
		final double SMALL_TICK_WIDTH = getWidth() * 0.0181818;
		final double SMALL_TICK_HEIGHT = getWidth() * 0.0363636;
		this.smallTick = new Rectangle2D.Double(center.getX()
				- (SMALL_TICK_WIDTH / 2), SMALL_TICK_HEIGHT, SMALL_TICK_WIDTH,
				SMALL_TICK_HEIGHT);

		// Hour tick mark
		final double BIG_TICK_WIDTH = getWidth() * 0.0363636;
		final double BIG_TICK_HEIGHT = getWidth() * 0.10909090;
		this.bigTick = new Rectangle2D.Double(center.getX()
				- (BIG_TICK_WIDTH / 2), SMALL_TICK_HEIGHT, BIG_TICK_WIDTH,
				BIG_TICK_HEIGHT);

		switch (type) {
		case LIGHT:
			this.currentForegroundColor = java.awt.Color.BLACK;
			this.currentBackgroundColor = new java.awt.Color[] {
					new Color(0xF7F7F7), new Color(0xF0F0F0) };
			break;

		case DARK:
			this.currentForegroundColor = Color.WHITE;
			this.currentBackgroundColor = new Color[] { new Color(0x3E3B32),
					new Color(0x232520) };
			break;

		default:
			this.currentForegroundColor = Color.WHITE;
			this.currentBackgroundColor = new Color[] { new Color(0x3E3B32),
					new Color(0x232520) };
			break;
		}
		this.backgroundImage = null;
		repaint(0, 0, getWidth(), getWidth());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		AffineTransform oldTransform = g2.getTransform();

		// Draw daynight image in the background
		g2.rotate(Math.toRadians(nightDayAngle), center.getX(), center.getY());
		if (nightDayImage == null) {
			nightDayImage = createNightDayImage();
		}
		g2.drawImage(nightDayImage, (int) (getWidth() * 0.21),
				(int) (getWidth() * 0.21), null);
		g2.rotate(Math.toRadians(-nightDayAngle), center.getX(), center.getY());

		// Draw background image
		if (backgroundImage == null) {
			backgroundImage = createBackgroundImage();
		}
		g2.drawImage(backgroundImage, 0, 0, null);

		// Draw hour pointer
		// g2.setTransform(oldTransform);
		// g2.setColor(SHADOW_COLOR);
		// g2.rotate(Math.toRadians(hourPointerAngle + (1 *
		// Math.sin(Math.toRadians(hourPointerAngle)))), center.getX(),
		// center.getY());
		// g2.fill(hourPointer);
		//
		g2.setTransform(oldTransform);
		g2.setColor(currentForegroundColor);
		g2.rotate(Math.toRadians(hourPointerAngle), center.getX(), center
				.getY());
		g2.fill(hourPointer);

		// Draw minute pointer
		// Draw pointer shadow if background is dark and pointer is white
		if (getType().equals(TYPE.DARK)) {
			g2.setTransform(oldTransform);
			g2.setColor(SHADOW_COLOR);
			g2.rotate(Math.toRadians(minutePointerAngle
					+ (1 * Math.sin(Math.toRadians(minutePointerAngle)))),
					center.getX(), center.getY());
			g2.fill(minutePointer);
		}
		g2.setTransform(oldTransform);
		g2.setColor(currentForegroundColor);
		g2.rotate(Math.toRadians(minutePointerAngle), center.getX(), center
				.getY());
		g2.fill(minutePointer);

		if (secondPointerVisible) {
			// Draw second pointer
			g2.setTransform(oldTransform);
			g2.setColor(SHADOW_COLOR);
			g2.rotate(Math.toRadians(secondPointerAngle
					+ (2 * Math.sin(Math.toRadians(secondPointerAngle)))),
					center.getX(), center.getY());
			g2.fill(secondPointer);

			g2.setTransform(oldTransform);
			g2.setColor(SECOND_COLOR);
			g2.rotate(Math.toRadians(secondPointerAngle), center.getX(), center
					.getY());
			g2.fill(secondPointer);
		}
		g2.setTransform(oldTransform);

		g2.setColor(currentForegroundColor);
		g2.fill(centerKnob);
	}

	public TYPE getType() {
		return this.type;
	}

	public void setType(TYPE type) {
		this.type = type;
		init();
	}

	public boolean isSecondPointerVisible() {
		return this.secondPointerVisible;
	}

	public void setSecondPointerVisible(boolean secondPointerVisible) {
		this.secondPointerVisible = secondPointerVisible;

		/*
		 * Adjust the timer delay due to the visibility of the second pointer.
		 */
		if (secondPointerVisible) {
			CLOCK_TIMER.stop();
			CLOCK_TIMER.setDelay(100);
			CLOCK_TIMER.start();
		} else {
			CLOCK_TIMER.stop();
			CLOCK_TIMER.setDelay(1000);
			CLOCK_TIMER.start();
		}
		init();
	}

	public boolean isAutoType() {
		return this.autoType;
	}

	public void setAutoType(boolean autoType) {
		this.autoType = autoType;
	}

	public int getTimeZoneOffsetHour() {
		return this.timeZoneOffsetHour;
	}

	public void setTimeZoneOffsetHour(int timeZoneOffsetHour) {
		this.timeZoneOffsetHour = timeZoneOffsetHour;
	}

	public int getTimeZoneOffsetMinute() {
		return this.timeZoneOffsetMinute;
	}

	public void setTimeZoneOffsetMinute(int timeZoneOffsetMinute) {
		this.timeZoneOffsetMinute = timeZoneOffsetMinute;
	}

	private BufferedImage createBackgroundImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(getWidth(),
				getWidth(), Transparency.TRANSLUCENT);
		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		AffineTransform oldTransform = g2.getTransform();

		// Define shape for the window
		final Arc2D OUTER_PIE = new Arc2D.Double(IMAGE.getWidth() * 0.21021,
				IMAGE.getHeight() * 0.21021, IMAGE.getWidth() * 0.58558, IMAGE
						.getHeight() * 0.58558, 25, 130, Arc2D.PIE);
		final Arc2D INNER_PIE = new Arc2D.Double((IMAGE.getWidth() * 0.38438),
				(IMAGE.getHeight() * 0.38438), (IMAGE.getWidth() * 0.23723),
				(IMAGE.getHeight() * 0.23723), 0, 180, Arc2D.PIE);
		final Area WINDOW = new Area(OUTER_PIE);
		WINDOW.subtract(new Area(INNER_PIE));
		final Area CLOCK_BACKGROUND_WITH_WINDOW = new Area(new Ellipse2D.Float(
				1, 1, getWidth() - 2, getWidth() - 2));
		CLOCK_BACKGROUND_WITH_WINDOW.subtract(WINDOW);

		final Area BACKGROUND_GRADIENT_WITH_WINDOW = new Area(
				new Ellipse2D.Float(0, 0, getWidth(), getWidth()));
		BACKGROUND_GRADIENT_WITH_WINDOW.subtract(WINDOW);

		// Draw clock background
		final Point2D LIGHT_START = new Point2D.Float(0, 0);
		final Point2D LIGHT_STOP = new Point2D.Float(0, getWidth());
		final float[] LIGHT_FRACTIONS = { 0.0f, 1.0f };
		final Color[] LIGHT_COLORS = { new Color(0x000000), new Color(0x645E54) };
		final LinearGradientPaint LIGHT_GRADIENT = new LinearGradientPaint(
				LIGHT_START, LIGHT_STOP, LIGHT_FRACTIONS, LIGHT_COLORS);
		g2.setPaint(LIGHT_GRADIENT);
		g2.fill(BACKGROUND_GRADIENT_WITH_WINDOW);

		final Point2D BACKGROUND_START = new Point2D.Float(0, 1);
		final Point2D BACKGROUND_STOP = new Point2D.Float(0, getWidth() - 2);
		final float[] BACKGROUND_FRACTIONS = { 0.0f, 1.0f };
		final Color[] BACKGROUND_COLORS = currentBackgroundColor;
		final LinearGradientPaint BACKGROUND_GRADIENT = new LinearGradientPaint(
				BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS,
				BACKGROUND_COLORS);
		g2.setPaint(BACKGROUND_GRADIENT);
		g2.fill(CLOCK_BACKGROUND_WITH_WINDOW);

		// Draw window frame
		final Point2D WINDOW_START = new Point2D.Double(0,
				IMAGE.getHeight() * 0.21021);
		final Point2D WINDOW_STOP = new Point2D.Double(0,
				IMAGE.getHeight() * 0.38438);
		final float[] WINDOW_FRACTIONS = { 0.0f, 0.8f, 1.0f };
		final Color[] WINDOW_COLORS;
		if (type == TYPE.DARK) {
			WINDOW_COLORS = new Color[] { new Color(0x000000),
					new Color(0x333333), new Color(0xCCCCCC) };
		} else {
			WINDOW_COLORS = new Color[] { new Color(0x333333),
					new Color(0x666666), new Color(0xAAAAAA) };
		}
		final LinearGradientPaint WINDOW_GRADIENT = new LinearGradientPaint(
				WINDOW_START, WINDOW_STOP, WINDOW_FRACTIONS, WINDOW_COLORS);
		g2.setPaint(WINDOW_GRADIENT);
		g2.draw(WINDOW);

		// Draw minutes tickmarks
		g2.setColor(currentForegroundColor);
		for (int tickAngle = 0; tickAngle < 360; tickAngle += 6) {
			g2.setTransform(oldTransform);
			g2.rotate(Math.toRadians(tickAngle), center.getX(), center.getY());
			g2.fill(smallTick);
		}

		// Draw hours tickmarks
		for (int tickAngle = 0; tickAngle < 360; tickAngle += 30) {
			g2.setTransform(oldTransform);
			g2.rotate(Math.toRadians(tickAngle), center.getX(), center.getY());
			g2.fill(bigTick);
		}

		g2.setTransform(oldTransform);

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createNightDayImage() {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		final BufferedImage IMAGE = gfxConf.createCompatibleImage(
				(int) (getWidth() * 0.585), (int) (getWidth() * 0.585),
				Transparency.TRANSLUCENT);
		Graphics2D g2 = IMAGE.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		final Point2D CENTER = new Point2D.Double(IMAGE.getWidth() / 2, IMAGE
				.getHeight() / 2);

		// Rotate image
		// g2.rotate(Math.toRadians(nightDayAngle) ,CENTER.getX(),
		// CENTER.getY());

		// Draw conical gradient for day-night-background
		final float[] FRACTIONS = { 0.0f, 0.10f, 0.14f, 0.18f, 0.32f, 0.68f,
				0.82f, 0.86f, 0.90f, 1.0f };

		final Color[] COLORS = { new Color(0x000000), new Color(0x000000),
				new Color(0x332200), new Color(0x664411), new Color(0x85A4C3),
				new Color(0x85A4C3), new Color(0x004466), new Color(0x002233),
				new Color(0x000000), new Color(0x000000) };

		final ConicalGradientPaint CONICAL_GRADIENT_PAINT = new ConicalGradientPaint(
				CENTER, nightDayAngle, FRACTIONS, COLORS);
		g2.setPaint(CONICAL_GRADIENT_PAINT);
		g2
				.fill(new Ellipse2D.Double(0, 0, IMAGE.getWidth(), IMAGE
						.getHeight()));

		// Draw sun

		// Draw sun beams
		g2.setColor(new Color(0xF0BE26));
		for (int alpha = 0; alpha < 360; alpha += 15) {
			g2.rotate(Math.toRadians(alpha), IMAGE.getWidth() * 0.49, IMAGE
					.getHeight() * 0.86206);
			g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.49, IMAGE
					.getHeight() * 0.86206, IMAGE.getWidth() * 0.51, IMAGE
					.getHeight()
					* 0.86206 - IMAGE.getWidth() * 0.12));
			g2.rotate(Math.toRadians(-alpha), IMAGE.getWidth() * 0.49, IMAGE
					.getWidth() * 0.86206);
		}

		// Draw sun body
		final Point2D START_SUN = new Point2D.Double(0,
				IMAGE.getHeight() * 0.75862);
		final Point2D STOP_SUN = new Point2D.Double(0,
				IMAGE.getHeight() * 0.96551);

		final float[] FRACTIONS_SUN = { 0.0f, 0.4f, 0.8f, 1.0f };
		final Color[] COLORS_SUN = { new Color(0xEE891A), new Color(0xF6B31B),
				new Color(0xFEE438), new Color(0xFDF589) };
		final LinearGradientPaint GRADIENT_SUN = new LinearGradientPaint(
				START_SUN, STOP_SUN, FRACTIONS_SUN, COLORS_SUN);

		final Point2D CENTER_SUN = new Point2D.Double(IMAGE.getWidth() * 0.5,
				IMAGE.getHeight() * 0.86206);
		final float[] FRACTIONS_INNERSHADOW = { 0.0f, 0.6f, 0.8f, 1.0f };
		final Color[] COLORS_INNERSHADOW = { new Color(0.5f, 0.5f, 0.5f, 0.0f),
				new Color(0.5f, 0.5f, 0.5f, 0.0f),
				new Color(0.0f, 0.0f, 0.0f, 0.1f),
				new Color(0.0f, 0.0f, 0.0f, 0.5f), };
		final RadialGradientPaint GRADIENT_SUN_INNERSHADOW = new RadialGradientPaint(
				CENTER_SUN, IMAGE.getWidth() * 0.103445f,
				FRACTIONS_INNERSHADOW, COLORS_INNERSHADOW);

		final Ellipse2D SUN = new Ellipse2D.Double(IMAGE.getWidth() * 0.39316,
				IMAGE.getHeight() * 0.75862, IMAGE.getWidth() * 0.20512, IMAGE
						.getHeight() * 0.20512);
		g2.setPaint(GRADIENT_SUN);
		g2.fill(SUN);
		g2.setPaint(GRADIENT_SUN_INNERSHADOW);
		g2.fill(SUN);

		// Draw clouds
		final Area CLOUD = new Area(new Ellipse2D.Double(0.71 * IMAGE
				.getWidth(), 0.78 * IMAGE.getHeight(), 0.1 * IMAGE.getWidth(),
				0.06 * IMAGE.getWidth()));
		CLOUD.add(new Area(new Ellipse2D.Double(0.73 * IMAGE.getWidth(),
				0.75 * IMAGE.getHeight(), 0.09 * IMAGE.getWidth(), 0.05 * IMAGE
						.getWidth())));
		CLOUD.add(new Area(new Ellipse2D.Double(0.76 * IMAGE.getWidth(),
				0.73 * IMAGE.getHeight(), 0.04 * IMAGE.getWidth(), 0.03 * IMAGE
						.getWidth())));

		final GeneralPath CLOUD1 = new GeneralPath(CLOUD);

		CLOUD.reset();
		CLOUD.add(new Area(new Ellipse2D.Double(0.28 * IMAGE.getWidth(),
				0.78 * IMAGE.getHeight(), 0.07 * IMAGE.getWidth(), 0.06 * IMAGE
						.getWidth())));
		CLOUD.add(new Area(new Ellipse2D.Double(0.23 * IMAGE.getWidth(),
				0.72 * IMAGE.getHeight(), 0.08 * IMAGE.getWidth(), 0.12 * IMAGE
						.getWidth())));
		CLOUD.add(new Area(new Ellipse2D.Double(0.16 * IMAGE.getWidth(),
				0.71 * IMAGE.getHeight(), 0.16 * IMAGE.getWidth(), 0.07 * IMAGE
						.getWidth())));
		CLOUD.add(new Area(new Ellipse2D.Double(0.21 * IMAGE.getWidth(),
				0.68 * IMAGE.getHeight(), 0.05 * IMAGE.getWidth(), 0.07 * IMAGE
						.getWidth())));

		final GeneralPath CLOUD2 = new GeneralPath(CLOUD);

		CLOUD.reset();

		g2.setColor(new Color(1.0f, 1.0f, 1.0f, 0.9f));
		g2.fill(CLOUD1);
		g2.fill(CLOUD2);

		// Draw moon
		final Point2D START_MOON = new Point2D.Double(0,
				IMAGE.getHeight() * 0.01709);
		final Point2D STOP_MOON = new Point2D.Double(0,
				IMAGE.getHeight() * 0.24137);
		final float[] FRACTIONS_MOON = { 0.0f, 1.0f };
		final Color[] COLORS_MOON = { new Color(0xFFFFFF), new Color(0xAAAAAA) };
		final Point2D MOON_CENTER = new Point2D.Double(IMAGE.getWidth() * 0.5,
				IMAGE.getHeight() * 0.13793);

		final LinearGradientPaint GRADIENT_MOON = new LinearGradientPaint(
				START_MOON, STOP_MOON, FRACTIONS_MOON, COLORS_MOON);
		final Area MOON = new Area(new Ellipse2D.Double(
				IMAGE.getWidth() * 0.376, IMAGE.getHeight() * 0.01709, IMAGE
						.getWidth() * 0.22413, IMAGE.getHeight() * 0.22413));
		MOON.subtract(new Area(new Ellipse2D.Double(IMAGE.getWidth() * 0.32482,
				0, IMAGE.getWidth() * 0.22137, IMAGE.getHeight() * 0.21689)));
		final RadialGradientPaint GRADIENT_MOON_INNERSHADOW = new RadialGradientPaint(
				MOON_CENTER, IMAGE.getWidth() * 0.11206f,
				FRACTIONS_INNERSHADOW, COLORS_INNERSHADOW);

		g2.setPaint(GRADIENT_MOON);
		g2.fill(MOON);
		g2.setPaint(GRADIENT_MOON_INNERSHADOW);
		g2.fill(MOON);

		// Draw stars
		g2.setStroke(STAR_STROKE);
		g2.setColor(new Color(0xFFFFFF));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.18965,
				IMAGE.getHeight() * 0.31034, IMAGE.getWidth() * 0.18965, IMAGE
						.getHeight() * 0.31034));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.15517,
				IMAGE.getHeight() * 0.22413, IMAGE.getWidth() * 0.15517, IMAGE
						.getHeight() * 0.22413));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.25862,
				IMAGE.getHeight() * 0.20689, IMAGE.getWidth() * 0.25862, IMAGE
						.getHeight() * 0.20689));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.36206,
				IMAGE.getHeight() * 0.15517, IMAGE.getWidth() * 0.36206, IMAGE
						.getHeight() * 0.15517));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.32758,
				IMAGE.getHeight() * 0.10344, IMAGE.getWidth() * 0.32758, IMAGE
						.getHeight() * 0.10344));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.41379,
				IMAGE.getHeight() * 0.06896, IMAGE.getWidth() * 0.41379, IMAGE
						.getHeight() * 0.06896));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.68965,
				IMAGE.getHeight() * 0.10344, IMAGE.getWidth() * 0.68965, IMAGE
						.getHeight() * 0.10344));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.65517,
				IMAGE.getHeight() * 0.15517, IMAGE.getWidth() * 0.65517, IMAGE
						.getHeight() * 0.15517));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.74137,
				IMAGE.getHeight() * 0.15517, IMAGE.getWidth() * 0.74137, IMAGE
						.getHeight() * 0.15517));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.75862,
				IMAGE.getHeight() * 0.20689, IMAGE.getWidth() * 0.75862, IMAGE
						.getHeight() * 0.20689));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.82758,
				IMAGE.getHeight() * 0.20689, IMAGE.getWidth() * 0.82758, IMAGE
						.getHeight() * 0.20689));
		g2.draw(new Line2D.Double(IMAGE.getWidth() * 0.79310,
				IMAGE.getHeight() * 0.24137, IMAGE.getWidth() * 0.79310, IMAGE
						.getHeight() * 0.24137));

		g2.dispose();

		return IMAGE;
	}

	@Override
	public void setPreferredSize(Dimension dimension) {
		if (dimension.width >= dimension.height) {
			super.setSize(new Dimension(dimension.width, dimension.width));
		} else {
			super.setSize(new Dimension(dimension.height, dimension.height));
		}
		if (nightDayImage != null) {
			nightDayImage.flush();
		}
		nightDayImage = null;
		init();
	}

	@Override
	public void setSize(Dimension dimension) {
		if (dimension.width >= dimension.height) {
			super.setSize(new Dimension(dimension.width, dimension.width));
		} else {
			super.setSize(new Dimension(dimension.height, dimension.height));
		}
		if (nightDayImage != null) {
			nightDayImage.flush();
		}
		nightDayImage = null;
		init();
	}

	@Override
	public void setSize(int width, int height) {
		if (width >= height) {
			super.setPreferredSize(new Dimension(width, width));
			super.setSize(width, width);
		} else {
			super.setPreferredSize(new Dimension(height, height));
			super.setSize(height, height);
		}
		if (nightDayImage != null) {
			nightDayImage.flush();
		}
		nightDayImage = null;
		init();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(CLOCK_TIMER)) {
			// Seconds
			secondPointerAngle = Calendar.getInstance().get(Calendar.SECOND)
					* ANGLE_STEP
					+ Calendar.getInstance().get(Calendar.MILLISECOND)
					* ANGLE_STEP / 1000;

			// Hours
			hour = Calendar.getInstance().get(Calendar.HOUR)
					- this.timeZoneOffsetHour;
			if (hour > 12) {
				hour -= 12;
			}
			if (hour < 0) {
				hour += 12;
			}

			// Minutes
			minute = Calendar.getInstance().get(Calendar.MINUTE)
					+ this.timeZoneOffsetMinute;
			if (minute > 60) {
				minute -= 60;
				hour++;
			}
			if (minute < 0) {
				minute += 60;
				hour--;
			}

			// Calculate angles from current hour and minute values
			hourPointerAngle = hour * ANGLE_STEP * 5 + (0.5) * minute;
			minutePointerAngle = minute * ANGLE_STEP;

			if (Calendar.getInstance().get(Calendar.SECOND) == 0
					&& Calendar.getInstance().get(Calendar.MILLISECOND) < 150) {
				nightDayAngle = ((Calendar.getInstance().get(
						Calendar.HOUR_OF_DAY) - timeZoneOffsetHour)
						* 60 + Calendar.getInstance().get(Calendar.MINUTE) + timeZoneOffsetMinute)
						* NIGHT_DAY_ANGLE_STEP;
				nightDayImage.flush();
				nightDayImage = null;
			}

			// AutoType
			if (autoType) {
				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- timeZoneOffsetHour < 4) {
					// Night
					int oldTimeOfDay = this.timeOfDay;
					setType(TYPE.DARK);
					this.timeOfDay = -2;
					firePropertyChange(TIMEOFDAY_PROPERTY, oldTimeOfDay,
							timeOfDay);
				} else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- timeZoneOffsetHour < 6) {
					// Sunrise
					int oldTimeOfDay = this.timeOfDay;
					setType(TYPE.DARK);
					this.timeOfDay = -1;
					firePropertyChange(TIMEOFDAY_PROPERTY, oldTimeOfDay,
							timeOfDay);
				} else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour >= 20) {
					// Night
					int oldTimeOfDay = this.timeOfDay;
					setType(TYPE.DARK);
					this.timeOfDay = -2;
					firePropertyChange(TIMEOFDAY_PROPERTY, oldTimeOfDay,
							timeOfDay);
				} else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour >= 18) {
					// Sunset
					int oldTimeOfDay = this.timeOfDay;
					setType(TYPE.DARK);
					this.timeOfDay = 1;
					firePropertyChange(TIMEOFDAY_PROPERTY, oldTimeOfDay,
							timeOfDay);
				} else {
					// Day
					int oldTimeOfDay = this.timeOfDay;
					setType(TYPE.LIGHT);
					this.timeOfDay = 0;
					firePropertyChange(TIMEOFDAY_PROPERTY, oldTimeOfDay,
							timeOfDay);
				}

				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour >= 24) {
					int oldDayOffset = this.dayOffset;
					this.dayOffset = 1;

					firePropertyChange(DAYOFFSET_PROPERTY, oldDayOffset,
							dayOffset);
				} else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour < 0) {
					int oldDayOffset = this.dayOffset;
					this.dayOffset = -1;
					firePropertyChange(DAYOFFSET_PROPERTY, oldDayOffset,
							dayOffset);
				} else {
					int oldDayOffset = this.dayOffset;
					this.dayOffset = 0;
					firePropertyChange(DAYOFFSET_PROPERTY, oldDayOffset,
							dayOffset);
				}
			}
			repaint(0, 0, getWidth(), getWidth());
		}
	}

	@Override
	public String toString() {
		return "DayNight Analog Clock";
	}
}
