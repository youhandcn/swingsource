package glasspanel.GlassDragAndDrop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Romain Guy
 */
public class PictureGlassPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private Point location;
	private BufferedImage shadow;

	public PictureGlassPane() {
	}

	public void moveIt(Point location) {
		Point oldLocation = this.location;
		SwingUtilities.convertPointFromScreen(location, this);
		this.location = location;

		Rectangle newClip = new Rectangle(location.x - image.getWidth() / 2,
				location.y - image.getHeight() / 2, image.getWidth(), image
						.getHeight());
		newClip.add(new Rectangle(oldLocation.x - image.getWidth() / 2,
				oldLocation.y - image.getHeight() / 2, image.getWidth(), image
						.getHeight()));
		newClip.add(new Rectangle(oldLocation.x - image.getWidth() / 2,
				oldLocation.y - image.getHeight() / 2, shadow.getWidth(),
				shadow.getHeight()));
		newClip
				.add(new Rectangle(location.x - image.getWidth() / 2,
						location.y - image.getHeight() / 2, shadow.getWidth(),
						shadow.getHeight()));

		repaint(newClip);
	}

	public void hideIt() {
		setVisible(false);
	}

	public void showIt(BufferedImage image, Point location) {
		this.image = image;
		this.shadow = new ShadowRenderer(5, 0.3f, Color.BLACK)
				.createShadow(image);

		SwingUtilities.convertPointFromScreen(location, this);
		this.location = location;

		setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null && location != null) {
			int x = location.x - image.getWidth() / 2;
			int y = location.y - image.getHeight() / 2;

			g.drawImage(shadow, x, y, null);
			g.drawImage(image, x, y, null);
		}
	}
}
