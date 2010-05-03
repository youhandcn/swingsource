/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class PhoneValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        String data = field.getText();
        // RegEx for phone no.
        String regex = "^([+]\\d{0,3}|[0]\\d{0,3})?([ ]|-|/)?([0-9]\\d{0,5})(([ ]|-|/)?([0-9]\\d{0,6}))?(([ ]|-|/)?([0-9]\\d{0,6}))?(([ ]|-|/)?([0-9]\\d{0,6}))?";
        return data.matches(regex);
    }

    @Override
    public String toString() {
        return "PhoneValidator";
    }
}
