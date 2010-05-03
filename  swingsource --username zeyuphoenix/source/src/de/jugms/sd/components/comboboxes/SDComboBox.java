/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.comboboxes;

/**
 * 
 * @author hansolo
 */
public class SDComboBox extends javax.swing.JComboBox {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDComboBox() {
        super();
        editor = new de.jugms.sd.components.comboboxes.SDComboBoxEditor();
        setUI(new de.jugms.sd.ui.SDComboBoxUI(this));
        setPreferredSize(new java.awt.Dimension(150, 22));
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;
    }

    public SDComboBox(javax.swing.ComboBoxModel model) {
        super(model);
        editor = new de.jugms.sd.components.comboboxes.SDComboBoxEditor();
        setUI(new de.jugms.sd.ui.SDComboBoxUI(this));
        setPreferredSize(new java.awt.Dimension(150, 22));
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;
    }

    public SDComboBox(Object[] items) {
        super(items);
        editor = new de.jugms.sd.components.comboboxes.SDComboBoxEditor();
        setUI(new de.jugms.sd.ui.SDComboBoxUI(this));
        setPreferredSize(new java.awt.Dimension(150, 22));
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;
    }

    public SDComboBox(java.util.Vector<?> items) {
        super(items);
        editor = new de.jugms.sd.components.comboboxes.SDComboBoxEditor();
        setUI(new de.jugms.sd.ui.SDComboBoxUI(this));
        setPreferredSize(new java.awt.Dimension(150, 22));
        this.radiusType = de.jugms.sd.tools.RadiusType.ROUNDED;
    }

    @Override
    public void setEditable(boolean editable) {
        super.setEditable(editable);
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
        ((de.jugms.sd.components.comboboxes.SDComboBoxEditor) getEditor())
                .setRadiusType(radiusType);
        ((de.jugms.sd.ui.SDComboBoxUI) getUI()).getArrowButton().setRadiusType(radiusType);
        ((de.jugms.sd.components.comboboxes.SDComboBoxCellRenderer) getRenderer())
                .setRadiusType(radiusType);
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    @Override
    public String toString() {
        return "SDComboBox";
    }
}
