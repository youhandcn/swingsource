package layeredpanel.LayeredPaneLayout;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;

/**
 * 放大镜，将它设置在JLayeredPane上层<br>
 * 添加: 设置可以放大的倍数
 */
public class Magnifier extends JComponent {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/** 放大镜的图像 */
	private BufferedImage magnifierImage = null;
	private JLayeredPane layeredPane;
	/** 放大的图像的缓存 */
	private BufferedImage bufferImage = null;
	private int zoomLevel = 2;

	public Magnifier(JLayeredPane layeredPane) {
		this.layeredPane = layeredPane;

		loadImages();

		layeredPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent mouseEvent) {
				Point location = mouseEvent.getPoint();
				location.translate(-getWidth() / 2, -getHeight() / 2);
				setLocation(location);
			}
		});
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent componentEvent) {
				resetBuffer();
			}

			@Override
			public void componentMoved(ComponentEvent componentEvent) {
			}

			@Override
			public void componentResized(ComponentEvent componentEvent) {
				resetBuffer();
			}

			@Override
			public void componentShown(ComponentEvent componentEvent) {
			}
		});
	}

	public int getZoomLevel() {
		return this.zoomLevel;
	}

	public void setZoomLevel(int zoom) {
		if (zoom < 1) {
			zoom = 1;
		}

		int oldZoom = this.zoomLevel;
		this.zoomLevel = zoom;
		firePropertyChange("zoomLevel", oldZoom, this.zoomLevel);

		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(magnifierImage.getWidth(), magnifierImage
				.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (bufferImage == null) {
			bufferImage = createImageBuffer();
		}

		Graphics2D g2 = bufferImage.createGraphics();
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, bufferImage.getWidth(), bufferImage.getHeight());
		g2.setComposite(AlphaComposite.Src);

		Point location = getLocation();
		location.translate(getWidth() / 2, getHeight() / 2);

		int myLayer = JLayeredPane.getLayer(this);
		for (int i = myLayer - 1; i >= 2; i -= 2) {
			Component[] components = layeredPane.getComponentsInLayer(i);
			for (Component c : components) {
				if (c.getBounds().contains(location)) {
					g2.translate(c.getX(), c.getY());
					c.paint(g2);
					g2.translate(-c.getX(), -c.getY());
				}
			}
		}

		g2.dispose();

		if (zoomLevel > 1) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

			Shape clip = g.getClip();
			Area newClip = new Area(clip);
			newClip.intersect(new Area(new Ellipse2D.Double(6.0, 6.0, 138.0,
					138.0)));

			g.setClip(newClip);
			g.drawImage(bufferImage, (int) (-getX() * zoomLevel - getWidth()
					* 0.5 * (zoomLevel - 1)),
					(int) (-getY() * zoomLevel - getHeight() * 0.5
							* (zoomLevel - 1)), bufferImage.getWidth()
							* zoomLevel, bufferImage.getHeight() * zoomLevel,
					null);
			g.setClip(clip);
		}

		g.drawImage(magnifierImage, 0, 0, null);
	}

	/**
	 * 容器变更,从新设置缓存图像
	 */
	private void resetBuffer() {
		bufferImage = null;
	}

	/**
	 * 载入放大镜的图像
	 */
	private void loadImages() {
		try {
			magnifierImage = ImageIO.read(getClass().getResource(
					"images/magnifier.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 构造一个缓存图像
	 */
	private BufferedImage createImageBuffer() {
		GraphicsEnvironment local = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = local.getDefaultScreenDevice();
		GraphicsConfiguration config = device.getDefaultConfiguration();

		Container parent = getParent();
		return config.createCompatibleImage(parent.getWidth(), parent
				.getHeight(), Transparency.TRANSLUCENT);
	}
}
