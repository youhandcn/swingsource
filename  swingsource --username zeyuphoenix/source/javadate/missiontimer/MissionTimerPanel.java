package missiontimer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 */
public class MissionTimerPanel extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** the image of number 0~9 */
	private final BufferedImage[] DIGIT_ARRAY = { createDigit(0),
			createDigit(1), createDigit(2), createDigit(3), createDigit(4),
			createDigit(5), createDigit(6), createDigit(7), createDigit(8),
			createDigit(9) };
	/** the image of the dots display */
	private final BufferedImage DOTS_ON = createDots(true);
	/** the image of the dots disappear */
	private final BufferedImage DOTS_OFF = createDots(false);
	/** the image of panel background */
	private final BufferedImage BACKGROUND_IMAGE = createBackground(450, 180);
	/** mission timer */
	private final Timer TIMER = new Timer(500, this);

	private boolean dotsOn = true;
	private int sec_right = 0;
	private int sec_left = 0;
	private int min_right = 0;
	private int min_left = 0;
	private int hour_right = 0;
	private int hour_mid = 0;
	private int hour_left = 0;

	public MissionTimerPanel() {
		setPreferredSize(new Dimension(450, 180));
		setSize(new Dimension(450, 180));
		init();
	}

	private void init() {
		addMouseListener(this);
	}

	public void startTimer() {
		TIMER.start();
	}

	public void stopTimer() {
		TIMER.stop();
		dotsOn = true;
	}

	public void resetTimer() {
		TIMER.stop();
		sec_right = 0;
		sec_left = 0;
		min_right = 0;
		min_left = 0;
		hour_right = 0;
		hour_mid = 0;
		hour_left = 0;
		repaint();
		dotsOn = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
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

		g2.drawImage(BACKGROUND_IMAGE, 0, 0, this);

		g2.drawImage(DIGIT_ARRAY[hour_left], 38, 90, this);
		g2.drawImage(DIGIT_ARRAY[hour_mid], 84, 90, this);
		g2.drawImage(DIGIT_ARRAY[hour_right], 130, 90, this);

		if (dotsOn) {
			g2.drawImage(DOTS_ON, 172, 90, this);
			g2.drawImage(DOTS_ON, 301, 90, this);
		} else {
			g2.drawImage(DOTS_OFF, 172, 90, this);
			g2.drawImage(DOTS_OFF, 301, 90, this);
		}

		g2.drawImage(DIGIT_ARRAY[min_left], 213, 90, this);
		g2.drawImage(DIGIT_ARRAY[min_right], 259, 90, this);

		g2.drawImage(DIGIT_ARRAY[sec_left], 319, 90, this);
		g2.drawImage(DIGIT_ARRAY[sec_right], 365, 90, this);

		g2.dispose();
	}

	private BufferedImage createBackground(int width, int height) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage IMAGE = gfxConf.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);

		Graphics2D g2 = (Graphics2D) IMAGE.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		final Point2D START_BACKGROUND = new Point2D.Float(0, 0);
		final Point2D STOP_BACKGROUND = new Point2D.Float(0, height);

		final float[] FRACTIONS = { 0.0f, 1.0f };

		final Color[] COLORS_BACKGROUND = { new Color(0x253048),
				new Color(0x182635) };

		final Point2D START_HIGHLIGHT = new Point2D.Float(0, 79);
		final Point2D STOP_HIGHLIGHT = new Point2D.Float(0, 166);

		final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 0.9f, 1.0f };

		final Color[] COLORS_HIGHLIGHT = { new Color(0x000000),
				new Color(0x000000), new Color(0x6C8095) };

		final Point2D START = new Point2D.Float(0, 80);
		final Point2D STOP = new Point2D.Float(0, 165);

		final Color[] COLORS_RIGHT = { new Color(0x384A69), new Color(0x253144) };

		final Color[] COLORS_LEFT = { new Color(0x2F4566), new Color(0x243D54) };

		final LinearGradientPaint GRADIENT_BACKGROUND = new LinearGradientPaint(
				START_BACKGROUND, STOP_BACKGROUND, FRACTIONS, COLORS_BACKGROUND);
		g2.setPaint(GRADIENT_BACKGROUND);
		g2.fillRect(0, 0, width, height);

		final LinearGradientPaint GRADIENT_HIGHLIGHT = new LinearGradientPaint(
				START_HIGHLIGHT, STOP_HIGHLIGHT, FRACTIONS_HIGHLIGHT,
				COLORS_HIGHLIGHT);
		g2.setPaint(GRADIENT_HIGHLIGHT);
		g2.fillRect(17, 79, 176, 87);
		g2.fillRect(193, 79, 239, 87);

		final LinearGradientPaint GRADIENT_LEFT = new LinearGradientPaint(
				START, STOP, FRACTIONS, COLORS_LEFT);
		g2.setPaint(GRADIENT_LEFT);
		g2.fillRect(18, 80, 175, 85);

		final LinearGradientPaint GRADIENT_RIGHT = new LinearGradientPaint(
				START, STOP, FRACTIONS, COLORS_RIGHT);
		g2.setPaint(GRADIENT_RIGHT);
		g2.fillRect(193, 80, 238, 85);

		g2.setColor(new Color(0x6C8095));
		g2.drawLine(193, 81, 193, 164);
		g2.setColor(new Color(0x000000));
		g2.drawLine(194, 81, 194, 164);

		final BasicStroke STROKE = new BasicStroke(4.0f,
				BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
		g2.setStroke(STROKE);
		g2.setColor(new Color(0xDDF3F4));
		g2.drawLine(19, 30, 19, 36);
		g2.drawLine(19, 30, 130, 30);
		g2.drawLine(318, 30, 429, 30);
		g2.drawLine(429, 30, 429, 36);

		g2.setFont(new Font("Arial", 1, 22));
		g2.drawString("MISSION TIMER", 140, 37);

		g2.setFont(new Font("Arial", 1, 16));
		g2.drawString("HOURS", 75, 65);
		g2.drawString("MIN", 243, 65);
		g2.drawString("SEC", 355, 65);

		g2.dispose();

		return IMAGE;
	}

	// create digit with given number.
	private BufferedImage createDigit(int digit) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage IMAGE = gfxConf.createCompatibleImage(46, 65,
				Transparency.TRANSLUCENT);

		final Color COLOR_ON = new Color(0xEEFFEE);
		final Color FRAME_COLOR_ON = new Color(50, 200, 100, 128);
		final Color COLOR_OFF = new Color(0x4E5571);
		final Color FRAME_COLOR_OFF = new Color(61, 57, 142, 128);
		final BasicStroke FRAME_STROKE = new BasicStroke(1.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		Graphics2D g2 = (Graphics2D) IMAGE.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		// A
		GeneralPath segment_a = new GeneralPath();
		segment_a.moveTo(17, 0);
		segment_a.lineTo(38, 0);
		segment_a.lineTo(37, 8);
		segment_a.lineTo(16, 8);
		segment_a.closePath();

		// B
		GeneralPath segment_b = new GeneralPath();
		segment_b.moveTo(39, 0);
		segment_b.lineTo(41, 0);
		segment_b.quadTo(45, 0, 45, 5);
		segment_b.lineTo(41, 32);
		segment_b.lineTo(38, 32);
		segment_b.lineTo(35, 28);
		segment_b.closePath();

		// C
		GeneralPath segment_c = new GeneralPath();
		segment_c.moveTo(37, 33);
		segment_c.lineTo(41, 33);
		segment_c.lineTo(37, 60);
		segment_c.quadTo(36, 65, 32, 65);
		segment_c.lineTo(30, 65);
		segment_c.lineTo(34, 37);
		segment_c.closePath();

		// D
		GeneralPath segment_d = new GeneralPath();
		segment_d.moveTo(9, 57);
		segment_d.lineTo(30, 57);
		segment_d.lineTo(29, 65);
		segment_d.lineTo(8, 65);
		segment_d.closePath();

		// E
		GeneralPath segment_e = new GeneralPath();
		segment_e.moveTo(4, 33);
		segment_e.lineTo(8, 33);
		segment_e.lineTo(11, 37);
		segment_e.lineTo(7, 65);
		segment_e.lineTo(4, 65);
		segment_e.quadTo(0, 65, 0, 60);
		segment_e.closePath();

		// F
		GeneralPath segment_f = new GeneralPath();
		segment_f.moveTo(8, 5);
		segment_f.quadTo(8, 0, 13, 0);
		segment_f.lineTo(16, 0);
		segment_f.lineTo(12, 28);
		segment_f.lineTo(8, 32);
		segment_f.lineTo(4, 32);
		segment_f.closePath();

		// G
		GeneralPath segment_g = new GeneralPath();
		segment_g.moveTo(14, 29);
		segment_g.lineTo(34, 29);
		segment_g.lineTo(36, 33);
		segment_g.lineTo(32, 37);
		segment_g.lineTo(13, 37);
		segment_g.lineTo(9, 33);
		segment_g.closePath();

		g2.setStroke(FRAME_STROKE);

		switch (digit) {
		case 0:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_g);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_g);
			break;
		case 1:
			g2.setColor(COLOR_ON);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_a);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_a);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 2:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_c);
			g2.fill(segment_f);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_c);
			g2.draw(segment_f);
			break;
		case 3:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_e);
			g2.draw(segment_f);
			break;
		case 4:
			g2.setColor(COLOR_ON);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_a);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_f);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_a);
			g2.draw(segment_d);
			g2.draw(segment_e);
			break;
		case 5:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_b);
			g2.fill(segment_e);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_f);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_b);
			g2.draw(segment_e);
			break;
		case 6:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_b);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_b);
			break;
		case 7:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 8:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		case 9:
			g2.setColor(COLOR_ON);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(COLOR_OFF);
			g2.fill(segment_e);
			g2.setColor(FRAME_COLOR_ON);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_f);
			g2.draw(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_e);
			break;
		default:
			g2.setColor(COLOR_OFF);
			g2.fill(segment_a);
			g2.fill(segment_b);
			g2.fill(segment_c);
			g2.fill(segment_d);
			g2.fill(segment_e);
			g2.fill(segment_f);
			g2.fill(segment_g);
			g2.setColor(FRAME_COLOR_OFF);
			g2.draw(segment_a);
			g2.draw(segment_b);
			g2.draw(segment_c);
			g2.draw(segment_d);
			g2.draw(segment_e);
			g2.draw(segment_f);
			g2.draw(segment_g);
			break;
		}

		g2.dispose();

		return IMAGE;
	}

	private BufferedImage createDots(boolean on) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage IMAGE = gfxConf.createCompatibleImage(23, 65,
				Transparency.TRANSLUCENT);

		final Color COLOR_ON = new Color(0xEEFFEE);
		final Color FRAME_COLOR_ON = new Color(50, 200, 100, 128);
		final Color COLOR_OFF = new Color(0x4E5571);
		final Color FRAME_COLOR_OFF = new Color(61, 57, 142, 128);
		final BasicStroke FRAME_STROKE = new BasicStroke(1.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		Graphics2D g2 = (Graphics2D) IMAGE.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2.setStroke(FRAME_STROKE);

		if (on) {
			g2.setColor(COLOR_ON);
			g2.fillOval(8, 20, 7, 7);
			g2.fillOval(5, 39, 7, 7);

			g2.setColor(FRAME_COLOR_ON);
			g2.drawOval(8, 20, 7, 7);
			g2.drawOval(5, 39, 7, 7);
		} else {
			g2.setColor(COLOR_OFF);
			g2.fillOval(8, 20, 7, 7);
			g2.fillOval(5, 39, 7, 7);

			g2.setColor(FRAME_COLOR_OFF);
			g2.drawOval(8, 20, 7, 7);
			g2.drawOval(5, 39, 7, 7);
		}

		g2.dispose();

		return IMAGE;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(TIMER)) {
			dotsOn = !dotsOn;
			if (dotsOn) {
				sec_right++;
			}

			if (sec_right == 10) {
				sec_right = 0;
				sec_left++;
			}

			if (sec_left == 6) {
				sec_left = 0;
				min_right++;
			}

			if (min_right == 10) {
				min_right = 0;
				min_left++;
			}

			if (min_left == 6) {
				min_left = 0;
				hour_right++;
			}

			if (hour_right == 10) {
				hour_right = 0;
				hour_mid++;
			}

			if (hour_mid == 10) {
				hour_mid = 0;
				hour_left++;
			}

			if (hour_left == 10) {
				sec_right = 0;
				sec_left = 0;
				min_right = 0;
				min_left = 0;
				hour_right = 0;
				hour_mid = 0;
				hour_left = 0;
			}

			repaint();
		}
	}

	@Override
	public Dimension getSize() {
		return new Dimension(450, 180);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1) {
			if (TIMER.isRunning()) {
				stopTimer();
			} else {
				startTimer();
			}
		}

		if (event.getClickCount() == 2) {
			resetTimer();
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	@Override
	public void mouseReleased(MouseEvent event) {

	}

	@Override
	public void mouseEntered(MouseEvent event) {

	}

	@Override
	public void mouseExited(MouseEvent event) {

	}
}
