package repaintmanager.TranslucentPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class TranslucentPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image = null;
    
    @Override
    public void paint(Graphics g) {
        if (image == null ||
            image.getWidth() != getWidth() ||
            image.getHeight() != getHeight()) {
            
            image = (BufferedImage) createImage(getWidth(), getHeight());
        }

        Graphics2D g2 = image.createGraphics();
        g2.setClip(g.getClip());
        super.paint(g2);
        g2.dispose();
        
        g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.2f));
        g2.drawImage(image, 0, 0, null);
    }

    public static void main(String... args) {

        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("Translucent Panel");
                
                f.setContentPane(new TranslucentPanel());
                f.getContentPane().setLayout(new BorderLayout());
                
                Object[] names = new Object[] {
                    "Title", "Artist", "Album"
                };
                String[][] data = new String[][] {
                    { "Los Angeles", "Sugarcult", "Lights Out" },
                    { "Do It Alone", "Sugarcult", "Lights Out" },
                    { "Made a Mistake", "Sugarcult", "Lights Out" },
                    { "Kiss You Better", "Maximo Park", "A Certain Trigger" },
                    { "All Over the Shop", "Maximo Park", "A Certain Trigger" },
                    { "Going Missing", "Maximo Park", "A Certain Trigger" }
                };
                JTable table = new JTable(data, names);
                f.add(table);
                
                JPanel p = new JPanel();
                p.add(new JButton("Play"));
                p.add(new JButton("Pause"));
                p.add(new JButton("Stop"));
                f.add(p, BorderLayout.SOUTH);
                
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                f.setSize(400, 300);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            } 
        });
    }
}