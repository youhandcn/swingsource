/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class MailValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        String data = field.getText();
        // RegEx for e-mail
        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
        return data.matches(regex);
    }

    @Override
    public String toString() {
        return "MailValidator";
    }
}
