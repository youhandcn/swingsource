/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class ValidationEvent extends java.util.EventObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean valid;

    public ValidationEvent(Object source, boolean valid) {
        super(source);
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "ValidationEvent";
    }
}
