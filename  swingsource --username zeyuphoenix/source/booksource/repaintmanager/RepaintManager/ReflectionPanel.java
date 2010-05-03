package repaintmanager.RepaintManager;



import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author Romain Guy <romain.guy@mac.com>
 */
public class ReflectionPanel extends JPanel {
    private JPanel contentPane;

    private BufferedImage contentBuffer;
    private BufferedImage reflectionBuffer;
    
    private Graphics2D contentGraphics;
    private Graphics2D reflectionGraphics;
    
    private GradientPaint alphaMask;
    
    private float length = 0.65f;
    private float opacity = 0.75f;
    
    private boolean initialized = false;
    
    public ReflectionPanel() {
        super(new GridBagLayout());
        setOpaque(false);
        
        buildContentPane();
        buildFiller();
        
        installRepaintManager();
        
        initialized = true;
    }
    
    private void installRepaintManager() {
        ReflectionRepaintManager manager = new ReflectionRepaintManager();
        RepaintManager.setCurrentManager(manager);
    }
    
    private void buildContentPane() {
        contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(false);
        
        add(contentPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }
    
    private void buildFiller() {
        add(Box.createVerticalGlue(), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        paintContent(g);
        paintReflection(g);
    }

    private void paintReflection(Graphics g) {
        int width = contentPane.getWidth();
        int height = (int) (contentPane.getHeight() * length);
        createReflection(g, width, height);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(1.0, -1.0);
        g2.drawImage(reflectionBuffer, 0, -contentPane.getHeight() - height, null);
        g2.dispose();
    }

    private void createReflection(Graphics g, int width, int height) {
        if (reflectionBuffer == null || reflectionBuffer.getWidth() != width ||
                reflectionBuffer.getHeight() != height) {
            if (reflectionBuffer != null) {
                reflectionBuffer.flush();
                reflectionGraphics.dispose();
            }
            
            reflectionBuffer =
                    GraphicsUtilities.createCompatibleImage(contentBuffer,
                        width, height);
            reflectionGraphics = reflectionBuffer.createGraphics();
            
            alphaMask = new GradientPaint(0.0f, 0.0f, new Color(0.0f, 0.0f, 0.0f, 0.0f),
                    0.0f, height, new Color(0.0f, 0.0f, 0.0f, opacity), true);
        }

        int yOffset = contentPane.getHeight() - height;
        Rectangle clip = g.getClipBounds();
        
        Graphics2D g2 = reflectionGraphics;
        g2.setClip(clip.x, clip.y - yOffset, clip.width, clip.height);
        
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(clip.x, clip.y - yOffset, clip.width, clip.height);
        g2.setComposite(AlphaComposite.SrcOver);
        
        g2.translate(0, -yOffset);
        g2.drawImage(contentBuffer, 0, 0, null);
        g2.translate(0, yOffset);
        
        g2.setComposite(AlphaComposite.DstIn);
        g2.setPaint(alphaMask);
        g2.fillRect(clip.x, clip.y - yOffset, clip.width, clip.height);
    }

    private void paintContent(Graphics g) {
        if (contentBuffer == null ||
                contentBuffer.getWidth() != contentPane.getWidth() ||
                contentBuffer.getHeight() != contentPane.getHeight()) {
            if (contentBuffer != null) {
                contentBuffer.flush();
                contentGraphics.dispose();
            }
            
            contentBuffer =
                    GraphicsUtilities.createCompatibleTranslucentImage(
                        contentPane.getWidth(), contentPane.getHeight());
            contentGraphics = contentBuffer.createGraphics();
        }
        
        Graphics2D g2 = contentGraphics;
        g2.clipRect(contentPane.getX(), contentPane.getY(),
                    contentPane.getWidth(), contentPane.getHeight());

        g2.setComposite(AlphaComposite.Clear);
        Rectangle clip = g.getClipBounds();
        g2.fillRect(clip.x, clip.y, clip.width, clip.height);
        g2.setComposite(AlphaComposite.SrcOver);
        
        g2.setColor(g.getColor());
        g2.setFont(g.getFont());
        super.paint(g2);

        g.drawImage(contentBuffer, 0, 0, null);
    }

    private class ReflectionRepaintManager extends RepaintManager {
        public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
            Rectangle dirtyRegion = getDirtyRegion(c);

            int lastDeltaX = c.getX();
            int lastDeltaY = c.getY();

            Container parent = c.getParent();
            while (parent instanceof JComponent) {
                if (!parent.isVisible()) {
                    return;
                }

                if (parent instanceof ReflectionPanel) {
                    x += lastDeltaX;
                    y += lastDeltaY;

                    int gap = contentPane.getHeight() - h - y;
                    h += 2 * gap + h;

                    lastDeltaX = lastDeltaY = 0;

                    c = (JComponent) parent;
                }

                lastDeltaX += parent.getX();
                lastDeltaY += parent.getY();

                parent = parent.getParent();
            }

            super.addDirtyRegion(c, x, y, w, h);
        }
    }
    
    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        if (initialized) {
            contentPane.add(comp, constraints, index);
        } else {
            super.addImpl(comp, constraints, index);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = contentPane.getPreferredSize();
        size.height *= 1.0f + length;
        return size;
    }
    
    @Override
    public void remove(int index) {
        contentPane.remove(index);
    }

    @Override
    public void remove(Component comp) {
        contentPane.remove(comp);
    }

    @Override
    public void removeAll() {
        contentPane.removeAll();
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        if (initialized) {
            contentPane.setLayout(mgr);
        } else {
            super.setLayout(mgr);
        }
    }
}
