/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public interface ValidationEventListener extends java.util.EventListener {
    public void validationEventPerformed(ValidationEvent event);
}