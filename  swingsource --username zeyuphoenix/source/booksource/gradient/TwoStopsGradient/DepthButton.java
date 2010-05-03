package gradient.TwoStopsGradient;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.JButton;

/**
 */
public class DepthButton extends JButton {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of DepthButton */
	public DepthButton(String text) {
		super(text);
		// 不填充JButton的绘制区域，自己绘制
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// 创建渐变填充
		GradientPaint p = new GradientPaint(0, 0, new Color(0xFFFFFF), 0,
				getHeight(), new Color(0x95A2DE));

		// 保存旧有填充
		Paint oldPaint = g2.getPaint();
		// 绘制填充
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());

		// 恢复旧有状态
		g2.setPaint(oldPaint);

		super.paintComponent(g);
	}
}
