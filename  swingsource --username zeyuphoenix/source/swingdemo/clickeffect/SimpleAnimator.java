package clickeffect;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 */
public class SimpleAnimator implements Animator {
	private Stroke stroke = new BasicStroke(5);

	@Override
	public void paint(Component c, Graphics g, Point point, int index, int total) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				((float) (total - index) / total)));
		g2d.setStroke(stroke);
		paintRainBow(g, point.x, point.y, index, total);
	}

	private void paintRainBow(Graphics g, int x, int y, int dec, int maxd) {
		int red = 255 * dec * 3 / maxd;
		if (red > 511)
			red = 0;
		else if (red > 255)
			red = 511 - red;
		int green = 255 * (3 * dec - maxd) / maxd;
		if (green < 0)
			green = 0;
		else if (green > 255)
			green = 511 - green;
		int blue = 255 * (3 * dec - 2 * maxd) / maxd;
		if (blue < 0)
			blue = 0;
		g.setColor(new Color(red, green, blue));
		g.drawOval(x - dec / 2, y - dec / 2, dec, dec);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(Component pane) {
	}
}
