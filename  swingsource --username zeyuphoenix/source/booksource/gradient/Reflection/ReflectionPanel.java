package gradient.Reflection;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 */
public class ReflectionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;

	/** Creates a new instance of ReflectionPanel */
	public ReflectionPanel() {
		try {
			image = ImageIO.read(getClass().getResource("Mirror Lake.jpg"));
			image = createReflection(image);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		setOpaque(false);
	}

	private BufferedImage createReflection(BufferedImage image) {
		int height = image.getHeight();

		BufferedImage result = new BufferedImage(image.getWidth(), height * 2,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		// Paints original image
		g2.drawImage(image, 0, 0, null);

		// Paints mirrored image
		g2.scale(1.0, -1.0);
		g2.drawImage(image, 0, -height - height, null);
		g2.scale(1.0, -1.0);

		// Move to the origin of the clone
		g2.translate(0, height);

		// Creates the alpha mask
		GradientPaint mask;
		mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0,
				height / 2, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);

		// Sets the alpha composite
		g2.setComposite(AlphaComposite.DstIn);

		// Paints the mask
		g2.fillRect(0, 0, image.getWidth(), height);

		g2.dispose();
		return result;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.translate(20, 20);
		g2.drawImage(image, 0, 0, null);
		g2.translate(-20, -20);
	}
}
