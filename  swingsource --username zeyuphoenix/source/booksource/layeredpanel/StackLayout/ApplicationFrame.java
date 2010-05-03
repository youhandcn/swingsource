package layeredpanel.StackLayout;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Romain Guy
 */
public class ApplicationFrame extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AvatarChooser chooser;
    private CurvesPanel curves;

    public ApplicationFrame() throws HeadlessException {
        super("Stack Layout");
        
        buildContentPane();
        //buildDebugControls();
        
        startAnimation();
        
        setSize(640, 400);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void startAnimation() {
        Timer timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                curves.animate();
                curves.repaint();
            }
        });
        timer.start();
    }

    private void buildContentPane() {
        JPanel pane = new JPanel();
        pane.setLayout(new StackLayout());
        
        GradientPanel gradient = new GradientPanel();
        chooser = new AvatarChooser();
        curves = new CurvesPanel();
        
        pane.add(gradient, StackLayout.TOP);
        pane.add(chooser, StackLayout.TOP);
        pane.add(curves, StackLayout.TOP);
        
        add(pane);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ApplicationFrame tester = new ApplicationFrame();
                tester.setVisible(true);
            }
        });
    }
}