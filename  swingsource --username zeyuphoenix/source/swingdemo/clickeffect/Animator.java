package clickeffect;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 */
public interface Animator {
	void init(Component pane);

	void paint(Component c, Graphics g, Point point, int index, int total);

	void destroy();
}
