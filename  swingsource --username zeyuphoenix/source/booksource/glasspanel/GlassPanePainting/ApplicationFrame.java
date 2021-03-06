package glasspanel.GlassPanePainting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import swinglayout.GroupLayout;
import swinglayout.LayoutStyle;

/**
 * 
 * @author Romain Guy
 */
public class ApplicationFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_DELAY = 300;

	private ProgressGlassPane glassPane;

	public ApplicationFrame() {
		initComponents();
		setGlassPane(glassPane = new ProgressGlassPane());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
	// ">//GEN-BEGIN:initComponents
	private void initComponents() {
		JButton buttonDownload;
		JLabel jLabel1;
		JScrollPane jScrollPane1;

		jLabel1 = new JLabel();
		buttonDownload = new JButton();
		jScrollPane1 = new JScrollPane();
		filesTable = new JTable();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Glass Pane Painting");
		setResizable(false);
		jLabel1.setText("Pick a file for download");

		buttonDownload.setText("Start Download");
		buttonDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonDownloadActionPerformed(evt);
			}
		});

		filesTable.setModel(new DefaultTableModel(new Object[][] {
				{ "aerith.png", "/www/progx/images/", "PNG", "5/17/2006" },
				{ "blog.html", "/www/progx", "HTML", "3/1/2006" },
				{ "index.html", "/www/progx", "HTML", "9/12/2006" },
				{ "pictures.zip", "/www/progx", "ZIP", "10/8/2006" } },
				new String[] { "Name", "Path", "Type", "Date" }));
		jScrollPane1.setViewportView(filesTable);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								GroupLayout.TRAILING,
								layout
										.createSequentialGroup()
										.add(
												layout
														.createParallelGroup(
																GroupLayout.TRAILING)
														.add(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				buttonDownload))
														.add(
																layout
																		.createParallelGroup(
																				GroupLayout.LEADING)
																		.add(
																				layout
																						.createSequentialGroup()
																						.addContainerGap()
																						.add(
																								jLabel1))
																		.add(
																				layout
																						.createSequentialGroup()
																						.addContainerGap()
																						.add(
																								jScrollPane1,
																								GroupLayout.DEFAULT_SIZE,
																								513,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING)
				.add(
						layout.createSequentialGroup().add(20, 20, 20).add(
								jLabel1).addPreferredGap(LayoutStyle.RELATED)
								.add(jScrollPane1, GroupLayout.PREFERRED_SIZE,
										275, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.RELATED).add(
										buttonDownload).addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 553) / 2, (screenSize.height - 394) / 2,
				553, 394);
	}// </editor-fold>//GEN-END:initComponents

	private void buttonDownloadActionPerformed(ActionEvent evt) {// GEN-FIRST:event_buttonDownloadActionPerformed
		getGlassPane().setVisible(true);
		startDownloadThread();
	}// GEN-LAST:event_buttonDownloadActionPerformed

	private void startDownloadThread() {
		Thread downloader = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				do {
					try {
						Thread.sleep(30 + (int) (Math.random() * MAX_DELAY));
					} catch (InterruptedException ex) {
						// who cares here?
					}
					i += (int) (Math.random() * 5);
					glassPane.setProgress(i);
				} while (i < 100);
				glassPane.setVisible(false);
				glassPane.setProgress(0);
			}
		});
		downloader.start();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ApplicationFrame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JTable filesTable;
	// End of variables declaration//GEN-END:variables

}
