/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class TextValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        if (field instanceof SDTextField) {
            if (field.getText().isEmpty()
                    || field.getText().equals(((SDTextField) field).getDefaultText())) {
                return false;
            } else {
                return true;
            }
        } else {
            if (field.getText().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public String toString() {
        return "TextValidator";
    }
}
