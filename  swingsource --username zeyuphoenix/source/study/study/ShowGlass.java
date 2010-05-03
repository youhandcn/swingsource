package study;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import flowlayout.FlowLayout;

public class ShowGlass extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MarkGlassPane glassPane = null;
	private JButton button = null;

	public ShowGlass() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		glassPane = new MarkGlassPane();
		this.setGlassPane(glassPane);
		glassPane.setVisible(true);
		button = new JButton("Test");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				glassPane.setMarkInfo(button.getLocation(), button.getSize());
			}
		});
		button.setSize(80, 25);
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(button);
	}

	public static void main(String[] args) {
		new ShowGlass().setVisible(true);
	}
}
