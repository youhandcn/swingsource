/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDTextPane1 extends javax.swing.JScrollPane implements java.awt.event.FocusListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    de.jugms.sd.components.misc.SDTextPane textPane;
    private javax.swing.JToolTip toolTip;
    private de.jugms.sd.components.panels.SDScrollPaneViewport viewport;
    private String defaultText;

    public SDTextPane1() {
        super();
        setOpaque(false);
        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 0, 0, 0)));
        toolTip = new de.jugms.sd.components.misc.SDToolTip();
        toolTip.setComponent(this);

        textPane = new de.jugms.sd.components.misc.SDTextPane();
        textPane.addFocusListener(this);
        textPane.setCaretColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 1.0f));
        textPane.setCaretPosition(0);
        textPane.setMargin(new java.awt.Insets(0, 1, 0, 1));
        textPane.setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 1.0f));
        textPane.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 10, 0, 10)));
        textPane.setSelectionColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                .getLightTranspa());
        viewport = new de.jugms.sd.components.panels.SDScrollPaneViewport();
        viewport.setView(textPane);
        setViewport(viewport);
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        this.textPane.setDefaultText(defaultText);
    }

    public String getDefaultText() {
        this.defaultText = this.textPane.getDefaultText();
        return this.defaultText;
    }

    public void setText(String text) {
        textPane.setText(text);
    }

    public String getText() {
        return textPane.getText();
    }

    public de.jugms.sd.components.misc.SDTextPane getTextPane() {
        return textPane;
    }

    @Override
    public javax.swing.JToolTip createToolTip() {
        return toolTip;
    }

    @Override
    public void focusGained(java.awt.event.FocusEvent event) {
        viewport.setFocused(true);
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent event) {
        viewport.setFocused(false);
    }

    @Override
    public String toString() {
        return "SDTextPane1";
    }
}
