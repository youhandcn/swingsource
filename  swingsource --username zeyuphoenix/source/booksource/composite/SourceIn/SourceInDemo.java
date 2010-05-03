package composite.SourceIn;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 */
public class SourceInDemo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox shadow;

	public SourceInDemo() {
		super("Source In");

		add(new ImageViewer(), BorderLayout.CENTER);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		panel.add(shadow = new JCheckBox("Drop Shadow"));
		shadow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				repaint();
			}
		});
		add(panel, BorderLayout.SOUTH);

		setSize(350, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private class ImageViewer extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image, landscape;

		private ImageViewer() {
			try {
				image = ImageIO.read(getClass().getResource("picture.png"));
				landscape = ImageIO.read(getClass()
						.getResource("landscape.jpg"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			BufferedImage temp = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = temp.createGraphics();

			if (shadow.isSelected()) {
				int x = (getWidth() - image.getWidth()) / 2;
				int y = (getHeight() - image.getHeight()) / 2;
				g2.drawImage(image, x + 4, y + 10, null);

				Composite oldComposite = g2.getComposite();
				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_IN, 0.75f));
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.setComposite(oldComposite);
				g2.drawImage(image, x, y, null);
			} else {
				int x = (getWidth() - image.getWidth()) / 2;
				int y = (getHeight() - image.getHeight()) / 2;
				g2.drawImage(image, x, y, null);

				Composite oldComposite = g2.getComposite();
				g2.setComposite(AlphaComposite.SrcIn);
				x = (getWidth() - landscape.getWidth()) / 2;
				y = (getHeight() - landscape.getHeight()) / 2;
				g2.drawImage(landscape, x, y, null);
				g2.setComposite(oldComposite);
			}

			g2.dispose();
			g.drawImage(temp, 0, 0, null);
		}
	}

	public static void main(String... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SourceInDemo().setVisible(true);
			}
		});
	}
}
