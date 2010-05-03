/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.tools;

/**
 * 
 * @author hansolo
 */
public enum SDGlobalAnimationTimer implements java.awt.event.ActionListener {
    INSTANCE;

    javax.swing.Timer animation = new javax.swing.Timer(50, this);
    private java.util.Set<javax.swing.JComponent> animatedComponentSet = new java.util.HashSet<javax.swing.JComponent>();
    private static boolean containsGlassPane = false;

    public void start() {
        if (!this.animation.isRunning()) {
            this.animation.start();
        }
    }

    public void addComponent(javax.swing.JComponent component) {
        if (component instanceof de.jugms.sd.components.panels.SDGlassPane) {
            containsGlassPane = true;
        }
        this.animatedComponentSet.add(component);
    }

    public void removeComponent(javax.swing.JComponent component) {
        if (component instanceof de.jugms.sd.components.panels.SDGlassPane) {
            containsGlassPane = false;
        }
        this.animatedComponentSet.remove(component);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        for (javax.swing.JComponent component : animatedComponentSet) {
            if (containsGlassPane) {
                // If animatedComponentSet contains glasspane, only this
                // has to be repainted because than every other component
                // will be repainted also
                if (component instanceof de.jugms.sd.components.panels.SDGlassPane) {
                    component.repaint();
                }
            } else {
                component.repaint();
            }
        }
    }

    @Override
    public String toString() {
        return "SDGlobalAnimationTimer";
    }
}
