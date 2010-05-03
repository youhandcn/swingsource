package game;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class JCounter extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon[] numSet = {
			new ImageIcon(JCounter.class.getResource("c0.gif")),
			new ImageIcon(JCounter.class.getResource("c1.gif")),
			new ImageIcon(JCounter.class.getResource("c2.gif")),
			new ImageIcon(JCounter.class.getResource("c3.gif")),
			new ImageIcon(JCounter.class.getResource("c4.gif")),
			new ImageIcon(JCounter.class.getResource("c5.gif")),
			new ImageIcon(JCounter.class.getResource("c6.gif")),
			new ImageIcon(JCounter.class.getResource("c7.gif")),
			new ImageIcon(JCounter.class.getResource("c8.gif")),
			new ImageIcon(JCounter.class.getResource("c9.gif")), };
	private JButton[] counter = { new JButton(numSet[0]),
			new JButton(numSet[0]), new JButton(numSet[0]) };
	private int counterNum;
	private Insets space;

	public JCounter() {
		this(0);
	}

	public JCounter(int num) {
		super();
		setSize(23, 39);

		space = new Insets(0, 0, 0, 0);
		this.counterNum = num;
		for (int i = 0; i < 3; i++) {
			counter[i].setSize(13, 23);
			counter[i].setMargin(space);
			add(counter[i]);
		}
		this.setVisible(true);
		resetImage();
	}

	public int getCounterNum() {
		return (counterNum);
	}

	private void setCounterNum(int num) {
		this.counterNum = num;
	}

	private void resetImage() {
		int ones, tens, hundreds;
		ones = counterNum % 10;
		tens = counterNum % 100 / 10;
		hundreds = (counterNum) % 1000 / 100;
		this.counter[0].setIcon(numSet[hundreds]);
		this.counter[1].setIcon(numSet[tens]);
		this.counter[2].setIcon(numSet[ones]);
	}

	public void resetCounter(int num) {
		setCounterNum(num);
		resetImage();
		this.repaint();
	}

	public void counterAdd() {
		this.counterNum++;
		resetImage();
		this.repaint();
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("Test");
		jf.setSize(23, 39);
		JCounter jc = new JCounter();
		jf.setContentPane(jc);
		jf.setVisible(true);
		jc.resetCounter(394);
	}

}
