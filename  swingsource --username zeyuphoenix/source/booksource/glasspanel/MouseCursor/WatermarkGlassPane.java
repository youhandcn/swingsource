package glasspanel.MouseCursor;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * 
 * @author Romain Guy
 */
public class WatermarkGlassPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;

	/** Creates a new instance of WatermarkGlassPane */
	public WatermarkGlassPane() {
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

	@Override
	protected void paintComponent(Graphics g) {
		if (image == null) {
			try {
				image = ImageIO.read(getClass().getResource("watermark.png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		g.drawImage(image, getWidth() - image.getWidth(), getHeight()
				- image.getHeight(), null);
	}
}
