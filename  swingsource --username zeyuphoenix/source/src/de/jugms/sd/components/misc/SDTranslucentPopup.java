/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.misc;

/*
 * KEEP IN MIND:
 * 
 * In JDK7 the AWTUtilities methods will be renamed like follows:
 * 
 * AWTUtilities.isTranslucencySupported(Translucency) is now
 * GraphicsDevice.isWindowTranslucencySupported(WindowTranslucency).
 * AWTUtilities.isTranslucencyCapable(GraphicsConfiguration) is now
 * GraphicsConfiguration.isTranslucencyCapable().
 * AWTUtilities.setWindowShape(Window, Shape) is now Window.setShape(Shape).
 * AWTUtilities.setWindowOpacity(Window, float) is now Window.setOpacity(float).
 * AWTUtilities.setWindowOpaque(boolean) is superceded by
 * Window.setBackground(Color). Passing the new Color(0, 0, 0, 0) achieves the
 * old effect of installing per-pixel translucency.
 */
/**
 * 
 * @author hansolo
 */
public class SDTranslucentPopup extends javax.swing.Popup {
    private javax.swing.JWindow popupWindow;
    private boolean toFade;
    private int currOpacity;
    private javax.swing.Timer fadeInTimer;
    private javax.swing.Timer fadeOutTimer;
    private boolean isTranslucencySupported;

    public SDTranslucentPopup(java.awt.Component owner, java.awt.Component contents, int ownerX,
            int ownerY) {
        toFade = true;
        currOpacity = 0;

        // If on translucency is not available opacity of windows doesn't work
        isTranslucencySupported = de.jugms.sd.tools.SDHelper.INSTANCE.isTranslucencySupported();

        // create a new heavyweight window
        this.popupWindow = new javax.swing.JWindow();
        popupWindow.setLocation(ownerX, ownerY);
        // add the contents to the popup
        popupWindow.getContentPane().add(contents, java.awt.BorderLayout.CENTER);

        // Disable window drop shadow on mac os x
        popupWindow.getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
        popupWindow.setFocusable(false);

        contents.invalidate();
        javax.swing.JComponent parent = (javax.swing.JComponent) contents.getParent();
        parent.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    @Override
    public void show() {
        if (this.toFade) {
            // mark the popup with 0% opacity
            this.currOpacity = 0;
            if (isTranslucencySupported) {
                com.sun.awt.AWTUtilities.setWindowOpacity(popupWindow, 0.0f);
            }
        }

        this.popupWindow.setVisible(true);
        this.popupWindow.pack();

        // mark the window as non-opaque, so that the
        // shadow border pixels take on the per-pixel
        // translucency
        if (isTranslucencySupported) {
            com.sun.awt.AWTUtilities.setWindowOpaque(this.popupWindow, false);
        }

        if (this.toFade) {
            // start fading in
            this.fadeInTimer = new javax.swing.Timer(50, new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent event) {
                    currOpacity += 20;
                    if (currOpacity <= 80) {
                        if (isTranslucencySupported) {
                            com.sun.awt.AWTUtilities.setWindowOpacity(popupWindow,
                                    currOpacity / 100.0f);
                        }
                        // workaround bug 6670649 - should call
                        // popupWindow.repaint() but that will not repaint the
                        // panel
                        popupWindow.getContentPane().repaint();
                    } else {
                        currOpacity = 100;
                        fadeInTimer.stop();
                    }
                }
            });
            this.fadeInTimer.setRepeats(true);
            this.fadeInTimer.start();
        }
    }

    @Override
    public void hide() {
        if (this.toFade) {
            // cancel fade-in if it's running.
            if (this.fadeInTimer.isRunning()) {
                this.fadeInTimer.stop();
            }

            // start fading out
            this.fadeOutTimer = new javax.swing.Timer(50, new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent event) {
                    currOpacity -= 10;
                    if (currOpacity >= 0) {
                        if (isTranslucencySupported) {
                            com.sun.awt.AWTUtilities.setWindowOpacity(popupWindow,
                                    currOpacity / 100.0f);
                        }
                        // workaround bug 6670649 - should call
                        // popupWindow.repaint() but that will not repaint the
                        // panel
                        popupWindow.getContentPane().repaint();
                    } else {
                        fadeOutTimer.stop();
                        popupWindow.setVisible(false);
                        popupWindow.removeAll();
                        popupWindow.dispose();
                        currOpacity = 0;
                    }
                }
            });
            this.fadeOutTimer.setRepeats(true);
            this.fadeOutTimer.start();
        } else {
            popupWindow.setVisible(false);
            popupWindow.removeAll();
            popupWindow.dispose();
        }
    }

    @Override
    public String toString() {
        return "SDTranslucentPopup";
    }
}
