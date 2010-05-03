/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.tools;

/**
 * 
 * @author hansolo
 */
public enum SDGlobals {
    INSTANCE;

    private de.jugms.sd.tools.SDTwoColorGradients activeGradient = de.jugms.sd.tools.SDTwoColorGradients.BLUE;
    private de.jugms.sd.tools.SDTwoColorGradients activeTranspaGradient = de.jugms.sd.tools.SDTwoColorGradients.BLUE_TRANSPA;

    public de.jugms.sd.tools.SDTwoColorGradients getActiveGradient() {
        return this.activeGradient;
    }

    public void setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients activeGradient) {
        this.activeGradient = activeGradient;
    }

    public de.jugms.sd.tools.SDTwoColorGradients getActiveTranspaGradient() {
        return this.activeTranspaGradient;
    }

    public void setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients activeTranspaGradient) {
        this.activeTranspaGradient = activeTranspaGradient;
    }

    @Override
    public String toString() {
        return "SDGlobals";
    }
}
