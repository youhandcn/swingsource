/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI implements
        javax.swing.SwingConstants {
    private de.jugms.sd.tools.RadiusType radiusType;
    de.jugms.sd.components.buttons.SDArrowButton button;

    public SDComboBoxUI(javax.swing.JComboBox comboBox) {
        super();
        super.comboBox = comboBox;

        comboBox.setOpaque(false);
        comboBox.setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f));
        comboBox.setFont(new java.awt.Font("Verdana", 0, 12));
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;

        if (comboBox.isEditable()) {
            comboBox.setEditor(new de.jugms.sd.components.comboboxes.SDComboBoxEditor());
        } else {
            comboBox.setRenderer(new de.jugms.sd.components.comboboxes.SDComboBoxCellRenderer(
                    comboBox));
        }
    }

    public de.jugms.sd.components.buttons.SDArrowButton getArrowButton() {
        return this.button;
    }

    @Override
    protected javax.swing.plaf.basic.ComboPopup createPopup() {
        popup = new de.jugms.sd.components.comboboxes.SDComboPopup(comboBox);
        return popup;
    }

    @Override
    protected javax.swing.JButton createArrowButton() {
        button = new de.jugms.sd.components.buttons.SDArrowButton(SOUTH, EAST);
        button.setRadiusType(this.radiusType);
        button.setName("ComboBox.arrowButton");
        return button;
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    @Override
    public String toString() {
        return "SDComboBoxUI";
    }
}
