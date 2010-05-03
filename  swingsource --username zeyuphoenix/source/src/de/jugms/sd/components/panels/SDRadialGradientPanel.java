/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.panels;

public class SDRadialGradientPanel extends javax.swing.JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    java.awt.Color innerColor;
    java.awt.Color outerColor;
    float[] dist;
    java.awt.Color[] colors;

    public SDRadialGradientPanel() {
        this.innerColor = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight();
        this.outerColor = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark();
        dist = new float[] { 0.0f, 1.0f };

        colors = new java.awt.Color[] { innerColor, outerColor };
    }

    public SDRadialGradientPanel(java.awt.Color innerColor, java.awt.Color outerColor) {
        this.innerColor = innerColor;
        this.outerColor = outerColor;
        dist = new float[] { 0.0f, 1.0f };

        colors = new java.awt.Color[] { innerColor, outerColor };
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(getWidth() / 2,
                getHeight() / 2);
        float radius = getWidth() / 2;

        java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center, radius,
                dist, colors);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        java.awt.Paint oldPaint = g2.getPaint();
        g2.setPaint(gradient);
        g2.fill(g2.getClip());
        g2.setPaint(oldPaint);
    }

    public java.awt.Color getInnerColor() {
        return this.innerColor;
    }

    public void setInnerColor(java.awt.Color innerColor) {
        this.innerColor = innerColor;
        repaint();
    }

    public java.awt.Color getOuterColor() {
        return this.outerColor;
    }

    public void setOuterColor(java.awt.Color outerColor) {
        this.outerColor = outerColor;
        repaint();
    }

    public void refresh() {
        setInnerColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight());
        setOuterColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark());

        colors = new java.awt.Color[] { innerColor, outerColor };
    }

    @Override
    public String toString() {
        return "SDRadialGradientPanel";
    }
}
