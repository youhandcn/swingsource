/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class DateValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        String data = field.getText();
        // RegEx for date
        String regex = "^(([1-9])|(0[1-9])|(1[0-2]))((\\/)|(\\.)|(\\-))((0[1-9])|([1-31]))((\\/)|(\\.)|(\\-))((\\d{2})|(\\d{4}))$";
        return data.matches(regex);
    }

    @Override
    public String toString() {
        return "DateValidator";
    }
}
