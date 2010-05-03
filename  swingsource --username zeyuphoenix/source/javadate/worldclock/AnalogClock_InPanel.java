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
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 */
public class AnalogClock_InPanel extends JComponent implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double ANGLE_STEP = 6;
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
	private Rectangle2D hourPointer;
	private Rectangle2D minutePointer;
	private GeneralPath secondPointer;
	private Ellipse2D centerKnob;
	private Rectangle2D smallTick;
	private Rectangle2D bigTick;
	private final Color SECOND_COLOR = new Color(0xF00000);
	private final Color SHADOW_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.65f);
	private BufferedImage backgroundImage = null;

	public enum TYPE {
		LIGHT, DARK
	};

	private TYPE type = TYPE.DARK;
	private Color currentForegroundColor;
	private Color[] currentBackgroundColor;
	private Point2D center;
	private volatile int timeZoneOffsetHour = 0;
	private volatile int timeZoneOffsetMinute = 0;
	private int hour;
	private int minute;
	boolean am = (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM);
	// Flags
	private boolean secondPointerVisible = true;
	private boolean autoType = false;

	public AnalogClock_InPanel() {
		super();
		setSize(66, 66);

		init();

		CLOCK_TIMER.start();
	}

	private void init() {
		// Rotation center
		this.center = new Point2D.Float(getWidth() / 2.0f, getHeight() / 2.0f);

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
			this.currentForegroundColor = Color.BLACK;
			this.currentBackgroundColor = new Color[] { new Color(0xF7F7F7),
					new Color(0xF0F0F0) };
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
		repaint(0, 0, getWidth(), getHeight());
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

		AffineTransform oldTransform = g2.getTransform();

		// Draw background image
		if (backgroundImage == null) {
			backgroundImage = createBackgroundImage();
		}
		g2.drawImage(backgroundImage, 0, 0, null);

		// Draw hour pointer
		g2.setColor(currentForegroundColor);
		g2.rotate(Math.toRadians(hourPointerAngle), center.getX(), center
				.getY());
		g2.fill(hourPointer);

		// Draw minute pointer
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

		// g2.dispose();
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

		AffineTransform oldTransform = g2.getTransform();

		// Draw clock background
		final Point2D LIGHT_START = new Point2D.Float(0, 0);
		final Point2D LIGHT_STOP = new Point2D.Float(0, getHeight());
		final float[] LIGHT_FRACTIONS = { 0.0f, 1.0f };
		final Color[] LIGHT_COLORS = { new Color(0x000000), new Color(0x645E54) };
		final LinearGradientPaint LIGHT_GRADIENT = new LinearGradientPaint(
				LIGHT_START, LIGHT_STOP, LIGHT_FRACTIONS, LIGHT_COLORS);
		g2.setPaint(LIGHT_GRADIENT);
		g2.fill(new Ellipse2D.Float(0, 0, getWidth(), getHeight()));

		final Point2D BACKGROUND_START = new Point2D.Float(0, 1);
		final Point2D BACKGROUND_STOP = new Point2D.Float(0, getHeight() - 2);
		final float[] BACKGROUND_FRACTIONS = { 0.0f, 1.0f };
		final Color[] BACKGROUND_COLORS = currentBackgroundColor;
		final LinearGradientPaint BACKGROUND_GRADIENT = new LinearGradientPaint(
				BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS,
				BACKGROUND_COLORS);
		g2.setPaint(BACKGROUND_GRADIENT);
		g2.fill(new Ellipse2D.Float(1, 1, getWidth() - 2, getHeight() - 2));

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

	@Override
	public void setPreferredSize(Dimension dimension) {
		if (dimension.width >= dimension.height) {
			super.setSize(new Dimension(dimension.width, dimension.width));
		} else {
			super.setSize(new Dimension(dimension.height, dimension.height));
		}
		init();
	}

	@Override
	public void setSize(Dimension dimension) {
		if (dimension.width >= dimension.height) {
			super.setSize(new Dimension(dimension.width, dimension.width));
		} else {
			super.setSize(new Dimension(dimension.height, dimension.height));
		}
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

			// AutoType
			if (autoType) {
				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour >= 18
						|| Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
								- timeZoneOffsetHour < 6) {
					setType(TYPE.DARK);
				} else {
					setType(TYPE.LIGHT);
				}

				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour >= 24) {
					((ClockPanel) getParent()).dayLabel.setText("tomorrow");
				} else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
						- this.timeZoneOffsetHour < 0) {
					((ClockPanel) getParent()).dayLabel.setText("yesterday");
				} else {
					((ClockPanel) getParent()).dayLabel.setText("today");
				}
			}
			repaint(0, 0, getWidth(), getHeight());
		}
	}

	@Override
	public String toString() {
		return "DB Analog Clock";
	}
}
