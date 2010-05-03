package study;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * 
 * @author Romain Guy
 */
public class MarkGlassPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;

	/** Creates a new instance of WatermarkGlassPane */
	public MarkGlassPane() {
		image = createCheckMarkImage();
	}

	@Override
	public boolean contains(int x, int y) {
		if (getMouseListeners().length == 0
				&& getMouseMotionListeners().length == 0
				&& getMouseWheelListeners().length == 0
				&& getCursor() == Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) {
			if (image == null) {
				return false;
			} else {
				int imageX = getWidth() - image.getWidth();
				int imageY = getHeight() - image.getHeight();

				// if the mouse cursor is on a non-opaque pixel, mouse events
				// are allowed
				int inImageX = x - imageX;
				int inImageY = y - imageY;

				if (inImageX >= 0 && inImageY >= 0
						&& inImageX < image.getWidth()
						&& inImageY < image.getHeight()) {
					int color = image.getRGB(inImageX, inImageY);
					return (color >> 24 & 0xFF) > 0;
				}

				return x > imageX && x < getWidth() && y > imageY
						&& y < getHeight();
			}
		}
		return super.contains(x, y);
	}

	private static final int CHECK_MARKER_SIZE = 20;
	private static final Stroke MEDIUM_STROKE = new BasicStroke(1.8f);

	private BufferedImage createCheckMarkImage() {
		BufferedImage markerImage = new BufferedImage(CHECK_MARKER_SIZE + 8,
				CHECK_MARKER_SIZE + 8, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) markerImage.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		Point origin = new Point(0, 0);

		Color light = new Color(251, 41, 0, 255);
		Color dark = new Color(102, 0, 0, 255);

		GradientPaint gradientBackground = new GradientPaint(new Point(
				origin.x + 2, origin.y + 2), light, new Point(origin.x + 2,
				origin.y + 18), dark);
		RadialGradientPaint gradientShadow = new RadialGradientPaint(new Point(
				origin.x + CHECK_MARKER_SIZE / 2 + 3, origin.y
						+ CHECK_MARKER_SIZE / 2 + 3), 7.5f, new float[] { 0.0f,
				1.0f }, new Color[] { new Color(0, 0, 0, 255),
				new Color(0, 0, 0, 20) });

		// Shadow
		g2.setPaint(gradientShadow);
		g2.fillOval(origin.x + 4, origin.y + 6, CHECK_MARKER_SIZE,
				CHECK_MARKER_SIZE);

		// Marker
		g2.setPaint(gradientBackground);
		g2.fillOval(origin.x + 2, origin.y + 2, CHECK_MARKER_SIZE,
				CHECK_MARKER_SIZE);
		g2.setPaint(Color.WHITE);
		g2.setStroke(MEDIUM_STROKE);
		g2.drawOval(origin.x + 1, origin.y + 1, CHECK_MARKER_SIZE,
				CHECK_MARKER_SIZE);

		g2.drawLine(origin.x + 5, origin.y + 9, origin.x + 8, origin.y + 12);
		g2.drawLine(origin.x + 8, origin.y + 12, origin.x + 12, origin.y + 6);

		g2.dispose();

		return markerImage;
	}

	private Point point;
	private Dimension size;
	private Point oldPoint;
	private Dimension oldSize;

	public void setMarkInfo(Point point, Dimension size) {
		oldPoint = this.point;
		oldSize = this.size;
		this.point = point;
		this.size = size;
		if (oldPoint != null && oldSize != null) {
			repaint(oldPoint.x + oldSize.width - 10, oldPoint.y - size.height, 25, 25);
		}
		repaint(point.x + size.width - 10, point.y + size.height, 25, 25);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image == null) {
			try {
				image = createCheckMarkImage();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
		AlphaComposite originalComposite = (AlphaComposite) g2d.getComposite();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.9f);
		AlphaComposite clearAlphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.0f);
		if (oldPoint != null && oldSize != null) {
			g2d.setComposite(clearAlphaComposite);
			g2d.drawImage(image, oldPoint.x + oldSize.width - 10, oldPoint.y + size.height,
					25, 25, null);
			g2d.setComposite(originalComposite);
		}
		if (point != null && size != null) {
			g2d.setComposite(alphaComposite);
			g2d.drawImage(image, point.x + size.width - 10, point.y + size.height, 25, 25,
					null);
			g2d.setComposite(originalComposite);
		}
		g2d.dispose();
	}
}