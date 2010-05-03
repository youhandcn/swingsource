package clickingeffect;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

/**
 */
public class ClickingEffectPane extends JComponent implements ActionListener,
		MouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ANIMATION_FRAMES = 20;
	private static final int ANIMATION_INTERVAL = 10;
	private int frameRadium = 100;
	private int frameIndex;
	private Timer timer;
	private Animator animator = new SimpleAnimator();
	private Point click_point;
	private Component clickedComponent;

	public ClickingEffectPane() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public Component getClickedComponent() {
		return clickedComponent;
	}

	@Override
	protected void addImpl(Component comp, Object constraints, int index) {
		addHandler(comp);
		super.addImpl(comp, constraints, index);
	}

	@Override
	public void remove(Component comp) {
		removeHandler(comp);
		super.remove(comp);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (isAnimating() && click_point != null && animator != null) {
			animator.paint(this, g, click_point, frameIndex * frameRadium
					/ ANIMATION_FRAMES, frameRadium);
		}
	}

	private boolean isAnimating() {
		return timer != null && timer.isRunning();
	}

	private void closeTimer() {
		if (isAnimating()) {
			timer.stop();
			timer = null;
			frameIndex = 0;
			if (animator != null)
				animator.destroy();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frameIndex++;
		repaint();
		if (frameIndex >= ANIMATION_FRAMES) {
			closeTimer();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Object src = e.getSource();
		if (src instanceof Component) {
			clickedComponent = (Component) src;
			Point p = getLocationOnScreen();
			click_point = e.getLocationOnScreen();
			click_point.x -= p.x;
			click_point.y -= p.y;
			closeTimer();
			if (animator != null)
				animator.init(this);

			timer = new Timer(ANIMATION_INTERVAL, this);
			timer.start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	private void addHandler(Component comp) {
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
		if (comp instanceof Container) {
			Container container = (Container) comp;
			int count = container.getComponentCount();
			for (int i = 0; i < count; i++) {
				addHandler(container.getComponent(i));
			}
		}
	}

	private void removeHandler(Component comp) {
		comp.removeMouseListener(this);
		comp.removeMouseMotionListener(this);
		if (comp instanceof Container) {
			Container container = (Container) comp;
			int count = container.getComponentCount();
			for (int i = 0; i < count; i++) {
				removeHandler(container.getComponent(i));
			}
		}
	}

	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
}
