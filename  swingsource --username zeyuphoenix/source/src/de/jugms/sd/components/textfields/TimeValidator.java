/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class TimeValidator implements Validator {
    @Override
    public boolean isValid(javax.swing.JTextField field) {
        String data = field.getText();
        // RegEx for time.
        String regex = "^((([0]?[1-9]|1[0-2])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?))$";
        return data.matches(regex);
    }

    @Override
    public String toString() {
        return "TimeValidator";
    }
}
