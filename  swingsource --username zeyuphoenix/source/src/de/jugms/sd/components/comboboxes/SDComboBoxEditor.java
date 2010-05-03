/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.comboboxes;

public class SDComboBoxEditor implements javax.swing.ComboBoxEditor {
    de.jugms.sd.components.comboboxes.SDComboBoxTextField textField;
    de.jugms.sd.tools.RadiusType radiusType;

    public SDComboBoxEditor() {
        this.textField = new de.jugms.sd.components.comboboxes.SDComboBoxTextField();
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;
        this.textField.setRadiusType(this.radiusType);
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
        this.textField.setRadiusType(this.radiusType);
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    @Override
    public java.awt.Component getEditorComponent() {
        return this.textField;
    }

    @Override
    public void setItem(Object anObject) {
        if (anObject != null) {
            textField.setText(anObject.toString());
        }
    }

    @Override
    public Object getItem() {
        return textField.getText();
    }

    @Override
    public void selectAll() {
        textField.selectAll();
    }

    @Override
    public void addActionListener(java.awt.event.ActionListener listener) {
        textField.addActionListener(listener);
    }

    @Override
    public void removeActionListener(java.awt.event.ActionListener listener) {
        textField.removeActionListener(listener);
    }
}
