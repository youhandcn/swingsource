package tempx;

import javax.swing.JComponent;
import javax.swing.RepaintManager;


public class NoRepaintManager extends RepaintManager {
    public static void install() {
        RepaintManager noRepaintManager = new NoRepaintManager();
        noRepaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(noRepaintManager);
    }

    @Override
    public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
    }

    @Override
    public synchronized void addInvalidComponent(JComponent invalidComponent) {
    }

    @Override
    public void markCompletelyDirty(JComponent component) {
    }

    @Override
    public void paintDirtyRegions() {
    }
    
    
}
