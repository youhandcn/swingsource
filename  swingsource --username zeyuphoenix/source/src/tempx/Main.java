package tempx;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.RepaintManager;
import javax.swing.SwingWorker;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int counter = 0;

	/**
	 * Creates new form MainFrame
	 */
	public Main() {
		setSize(400, 250);
		initComponents();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Main().setVisible(true);
	}

	private JLabel messageLabel;
	// private ProgressListener listener;
	private JProgressBar progressBar;
	private JButton button = null;
	boolean flag = false;
	private JPanel MsgPanel;


	private void initComponents() {

		MsgPanel = new JPanel();
		MsgPanel.setOpaque(true);
		MsgPanel.setLayout(new GridLayout(2, 1, 3, 0));
		messageLabel = new JLabel();
		messageLabel.setSize(100, 25);
		messageLabel.setText("xx");
		MsgPanel.setBackground(Color.blue);
		messageLabel.setLocation(20, 20);
		progressBar = new JProgressBar();
		progressBar.setSize(300, 25);
		progressBar.setLocation(20, 50);
		// listener = new ProgressListener(progressBar, messageLabel);
		MsgPanel.add(messageLabel);
		MsgPanel.add(progressBar);
		this.getContentPane().setLayout(null);
		this.getContentPane().add(messageLabel);
		this.getContentPane().add(progressBar);
		button = new JButton("press");
		button.setSize(80, 25);
		button.setLocation(20, 160);

//		messageLabel.setVisible(false);
		progressBar.setVisible(false);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Trace trace = new Trace();
				// trace.addPropertyChangeListener(listener);
				// trace.execute();
				flag = true;
				Thread runner = new Thread() {
					@Override
					public void run() {
						Runnable run = new Runnable() {
							@Override
							public void run() {
								while (flag) {
//									messageLabel.getGraphics().clearRect(0, 0,
//											getWidth(), getHeight());
//									messageLabel.paint(messageLabel
//											.getGraphics());
									progressBar
											.paint(progressBar.getGraphics());
								    RepaintManager x = RepaintManager.currentManager(messageLabel);
								    x.paintDirtyRegions();
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						};

						new Thread(run).start();
					}
				};
				runner.start();

				for (int i = 0; i < 50; i++) {
					System.out.println(222);
					try {
						Thread.sleep(500);
						progressBar.setValue(i);
						messageLabel.setText(i + "xxxxxxxxx");
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				flag = false;
			}
		});
		this.getContentPane().add(button);
	}

	class Trace extends SwingWorker<Void, Integer> {

		@Override
		protected Void doInBackground() throws Exception {

			setProgress(20, "loading 1");
			try {
				Thread.sleep(1000);
				setProgress(30, "loading 2");
				Thread.sleep(2000);
				setProgress(60, "loading 3");
				Thread.sleep(5000);
				setProgress(80, "loading 4");
				Thread.sleep(1000);
				setProgress(100, "loading 5");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			return null;
		}

		public void setProgress(int value, String message) {
			progressBar.setValue(value);
			messageLabel.setText(message);
			// setProgress(value);
			// firePropertyChange("message", null, message);
		}

		@Override
		protected void done() {
			super.done();
			int val = JOptionPane.showConfirmDialog(Main.this, "画面将关闭", "画面关闭",
					JOptionPane.YES_NO_OPTION);
			if (val == 0) {
				Main.this.dispose();
			}
		}
	}

	public class ProgressListener implements PropertyChangeListener {

		ProgressListener(JProgressBar progressBar, JLabel label) {
			this.progressBar = progressBar;
			this.progressBar.setValue(0);
			this.label = label;
			label.setText("初始化进度: ");
		}

		public void propertyChange(PropertyChangeEvent evt) {
			String strPropertyName = evt.getPropertyName();
			if ("progress".equals(strPropertyName)) {
				progressBar.setIndeterminate(false);
				int progress = (Integer) evt.getNewValue();
				progressBar.setValue(progress);
			} else if ("message".equals(strPropertyName)) {
				label.setText(evt.getNewValue().toString());
			}
		}

		private JProgressBar progressBar;
		private JLabel label;
	}
}

