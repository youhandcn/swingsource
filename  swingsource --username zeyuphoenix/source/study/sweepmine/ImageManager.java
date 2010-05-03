package sweepmine;

import java.net.URL;
import javax.swing.ImageIcon;

public final class ImageManager {

	public static ImageIcon loadImage(String imagePath) {

		if (imagePath == null) {
			return new ImageIcon("/resources/null.gif");
		}
		URL imageURL = ImageManager.class.getResource(imagePath);
		if (imageURL == null) {
			return new ImageIcon("/resources/null.gif");
		} else {
			return new ImageIcon(imageURL);
		}
	}
}
