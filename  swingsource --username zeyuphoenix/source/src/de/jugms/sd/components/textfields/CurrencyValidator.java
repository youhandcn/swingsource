/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class CurrencyValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        String data = field.getText();
        // RegEx for currency.
        String regex = "^((\\$)|(\\$$)?(\\d{1,3},?(\\d{3},?)*\\d{3}(\\.\\d{1,3})?|\\d{1,3}(\\.\\d{2})?)$";
        return data.matches(regex);
    }

    @Override
    public String toString() {
        return "CurrencyValidator";
    }
}
