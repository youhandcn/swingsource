package magnifier;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Magnifier extends JFrame {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/** 主容器 */
	private Container container = getContentPane();

	/**
	 * 放大镜x坐标 计算方式：setCoordinateX = absoluteCoordinateX -
	 * relativeCoordinateXWhenMousePressed
	 */
	private int setCoordinateX;
	/**
	 * 放大镜y坐标 计算方式：setCoordinateY = absoluteCoordinateY -
	 * relativeCoordinateYWhenMousePressed
	 */
	private int setCoordinateY;
	/** 鼠标绝对x坐标 */
	private int absoluteCoordinateX;
	/** 鼠标绝对y坐标 */
	private int absoluteCoordinateY;
	/** 鼠标按下时的相对x坐标 */
	private int relativeCoordinateXWhenMousePressed;
	/** 鼠标按下时的相对y坐标 */
	private int relativeCoordinateYWhenMousePressed;
	/** 标记鼠标是否按下。如果按下则为true，否则为false */
	private boolean mousePressedNow;
	/** 放大镜尺寸 */
	private int magnifierSize = 100;
	/** 放大镜内容面板 */
	private MagnifierPanel magnifierPanel = new MagnifierPanel(magnifierSize);

	/**
	 * 
	 * 构造函数，创建一个放大镜窗体
	 */
	public Magnifier() {
		// 窗体边缘
		setUndecorated(true);
		setResizable(false);
		container.add(magnifierPanel);
		addMouseListener(new MouseFunctions());
		addMouseMotionListener(new MouseMotionFunctions());
		updateSize(magnifierSize);
		this.setVisible(true);
	}

	/**
	 * 程序入口点
	 * 
	 * @param arg
	 *            启动参数，这里为空
	 */
	public static void main(String arg[]) {
		Magnifier magnifier = new Magnifier();
		magnifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * * 更新窗体
	 * 
	 * @param magnifierSize
	 *            放大镜尺寸
	 */
	public void updateSize(int magnifierSize) {
		magnifierPanel.setMagnifierSize(magnifierSize + 100);
		setSize(magnifierSize + 100, magnifierSize + 100);
		// 更新所有子控件
		validate();
	}

	private class MouseFunctions extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getClickCount() == 1) {
				// 如果鼠标左键点了一下，说明按住了窗体
				mousePressedNow = true;
				relativeCoordinateXWhenMousePressed = e.getX();
				relativeCoordinateYWhenMousePressed = e.getY();
			}
		}

		public void mouseReleased(MouseEvent e) {
			mousePressedNow = false;
		}
	}

	private class MouseMotionFunctions extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			if (mousePressedNow == true) {
				// 如果此时鼠标按下了，说明在拖拽窗体
				absoluteCoordinateX = Magnifier.this.getLocationOnScreen().x
						+ e.getX();
				absoluteCoordinateY = Magnifier.this.getLocationOnScreen().y
						+ e.getY();
				setCoordinateX = absoluteCoordinateX
						- relativeCoordinateXWhenMousePressed;
				setCoordinateY = absoluteCoordinateY
						- relativeCoordinateYWhenMousePressed;
				magnifierPanel.setMagnifierLocation(setCoordinateX,
						setCoordinateY);
				setLocation(setCoordinateX, setCoordinateY);
			}
		}
	}
}

class MagnifierPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image screenImage;
	/** * 放大镜的尺寸 */
	private int magnifierSize;
	private int locationX;
	private int locationY;
	private Robot robot;

	/**
	 * * 带参数的构造函数
	 * 
	 * @param magnifierSize
	 *            放大尺寸
	 */
	public MagnifierPanel(int magnifierSize) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}
		// 截屏幕
		screenImage = robot.createScreenCapture(new Rectangle(0, 0, Toolkit
				.getDefaultToolkit().getScreenSize().width, Toolkit
				.getDefaultToolkit().getScreenSize().height));
		this.magnifierSize = magnifierSize;
	}

	/**
	 * * 设置放大镜的位置
	 * 
	 * @param locationX
	 *            x坐标
	 * @param locationY
	 *            y坐标
	 */
	public void setMagnifierLocation(int locationX, int locationY) {
		this.locationX = locationX;
		this.locationY = locationY;
		// 注意重画控件
		repaint();
	}

	/**
	 * * 设置放大镜的尺寸
	 * 
	 * @param magnifierSize
	 *            放大镜尺寸
	 */
	public void setMagnifierSize(int magnifierSize) {
		this.magnifierSize = magnifierSize;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent((Graphics2D) g);
		// 关键处理代码
		g.drawImage(screenImage, // 要画的图片
				0, // 目标矩形的第一个角的x坐标
				0, // 目标矩形的第一个角的y坐标
				magnifierSize, // 目标矩形的第二个角的x坐标
				magnifierSize, // 目标矩形的第二个角的y坐标
				locationX + (magnifierSize / 4), // 源矩形的第一个角的x坐标
				locationY + (magnifierSize / 4), // 源矩形的第一个角的y坐标
				locationX + (magnifierSize / 4 * 3), // 源矩形的第二个角的x坐标
				locationY + (magnifierSize / 4 * 3), // 源矩形的第二个角的y坐标
				this);
	}
}
