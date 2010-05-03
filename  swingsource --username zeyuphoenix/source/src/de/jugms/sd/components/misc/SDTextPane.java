/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDTextPane extends javax.swing.JTextPane {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private javax.swing.JToolTip toolTip;
    private String defaultText;

    public SDTextPane() {
        super();
        toolTip = new de.jugms.sd.components.misc.SDToolTip();
        toolTip.setComponent(this);
        setOpaque(false);
        setMargin(new java.awt.Insets(0, 1, 0, 1));
        setBorder(new javax.swing.border.EmptyBorder(0, 10, 0, 10));
        setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f));
        setCaretColor(java.awt.Color.WHITE);
        setSelectionColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                .getLightTranspa());
        this.defaultText = "";
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getDefaultText() {
        return this.defaultText;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public javax.swing.JToolTip createToolTip() {
        return toolTip;
    }

    @Override
    public String toString() {
        return "SDTextPane";
    }
}
