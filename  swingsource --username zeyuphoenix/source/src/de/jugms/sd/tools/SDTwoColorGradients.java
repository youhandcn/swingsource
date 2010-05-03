/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.tools;

/**
 * 
 * @author hansolo
 */
public enum SDTwoColorGradients {
    GREEN(new java.awt.Color(135, 212, 0, 255), new java.awt.Color(56, 172, 0, 255)), BLUE(
            new java.awt.Color(0, 102, 255, 255), new java.awt.Color(0, 0, 102, 255)), ORANGE(
            new java.awt.Color(255, 153, 0, 255), new java.awt.Color(255, 102, 0, 255)), RED(
            new java.awt.Color(251, 41, 0, 255), new java.awt.Color(102, 0, 0, 255)), YELLOW(
            new java.awt.Color(255, 255, 0, 255), new java.awt.Color(153, 153, 0, 255)), GRAY(
            new java.awt.Color(204, 204, 204, 255), new java.awt.Color(102, 102, 102, 255)), GREEN_TRANSPA(
            new java.awt.Color(135, 212, 0, 128), new java.awt.Color(56, 172, 0, 128)), BLUE_TRANSPA(
            new java.awt.Color(0, 102, 255, 128), new java.awt.Color(0, 0, 102, 128)), ORANGE_TRANSPA(
            new java.awt.Color(255, 153, 0, 128), new java.awt.Color(255, 102, 0, 128)), RED_TRANSPA(
            new java.awt.Color(251, 41, 0, 128), new java.awt.Color(102, 0, 0, 128)), YELLOW_TRANSPA(
            new java.awt.Color(255, 255, 0, 128), new java.awt.Color(153, 153, 0, 128)), GRAY_TRANSPA(
            new java.awt.Color(204, 204, 204, 128), new java.awt.Color(102, 102, 102, 128)), BUTTON_BACKGROUND_ARMED(
            new java.awt.Color(12, 93, 255, 255), new java.awt.Color(0, 0, 111, 255)), BUTTON_FOREGROUND_ARMED(
            new java.awt.Color(12, 93, 255, 255), new java.awt.Color(0, 0, 111, 255)), BUTTON_DISABLED_ARMED(
            new java.awt.Color(255, 255, 255, 0), new java.awt.Color(255, 255, 255, 0)), BUTTON_BACKGROUND_ENABLED(
            new java.awt.Color(204, 204, 204, 255), new java.awt.Color(0, 0, 0, 255)), BUTTON_FOREGROUND_ENABLED(
            new java.awt.Color(37, 69, 109, 255), new java.awt.Color(0, 0, 0, 255)), BUTTON_DISABLED_ENABLED(
            new java.awt.Color(255, 255, 255, 0), new java.awt.Color(255, 255, 255, 0)), BUTTON_BACKGROUND_DISABLED(
            new java.awt.Color(204, 204, 204, 255), new java.awt.Color(0, 0, 0, 255)), BUTTON_FOREGROUND_DISABLED(
            new java.awt.Color(37, 69, 109, 255), new java.awt.Color(0, 0, 0, 255)), BUTTON_DISABLED_DISABLED(
            new java.awt.Color(255, 255, 255, 90), new java.awt.Color(255, 255, 255, 90)), BUTTON_HIGHLIGHT(
            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f), new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f)), RADIO_BUTTON_BACKGROUND_ARMED(
            new java.awt.Color(12, 93, 255, 255), new java.awt.Color(0, 0, 111, 255)), RADIO_BUTTON_FOREGROUND_ARMED(
            new java.awt.Color(12, 93, 255, 255), new java.awt.Color(0, 0, 111, 255)), RADIO_BUTTON_DISABLED_ARMED(
            new java.awt.Color(255, 255, 255, 0), new java.awt.Color(255, 255, 255, 0)), RADIO_BUTTON_BACKGROUND_ENABLED(
            new java.awt.Color(255, 255, 255, 255), new java.awt.Color(50, 50, 50, 255)), RADIO_BUTTON_FOREGROUND_ENABLED(
            new java.awt.Color(37, 69, 109, 255), new java.awt.Color(0, 0, 0, 255)), RADIO_BUTTON_DISABLED_ENABLED(
            new java.awt.Color(255, 255, 255, 0), new java.awt.Color(255, 255, 255, 0)), RADIO_BUTTON_BACKGROUND_DISABLED(
            new java.awt.Color(255, 255, 255, 255), new java.awt.Color(50, 50, 50, 255)), RADIO_BUTTON_FOREGROUND_DISABLED(
            new java.awt.Color(37, 69, 109, 255), new java.awt.Color(0, 0, 0, 255)), RADIO_BUTTON_DISABLED_DISABLED(
            new java.awt.Color(255, 255, 255, 90), new java.awt.Color(255, 255, 255, 90)), RADIO_BUTTON_HIGHLIGHT(
            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f), new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f));

    /**
     * Define gradient dependend parameters
     */
    private final java.awt.Color LIGHT;
    private final java.awt.Color DARK;
    private final java.awt.Color LIGHT_TRANSPA;
    private final java.awt.Color DARK_TRANSPA;
    private final java.awt.Color[] GRADIENT_DARK_LIGHT;
    private final java.awt.Color[] GRADIENT_LIGHT_DARK;
    private final java.awt.Color[] GRADIENT_DARK_LIGHT_TRANSPA;
    private final java.awt.Color[] GRADIENT_LIGHT_DARK_TRANSPA;

    /**
     * Constructor for states
     */
    SDTwoColorGradients(java.awt.Color light, java.awt.Color dark) {
        this.LIGHT = light;
        this.DARK = dark;
        this.LIGHT_TRANSPA = new java.awt.Color(light.getRed(), light.getGreen(), light.getBlue(),
                128);
        this.DARK_TRANSPA = new java.awt.Color(dark.getRed(), dark.getGreen(), dark.getBlue(), 128);
        this.GRADIENT_DARK_LIGHT = new java.awt.Color[] { dark, light };
        this.GRADIENT_LIGHT_DARK = new java.awt.Color[] { light, dark };
        this.GRADIENT_DARK_LIGHT_TRANSPA = new java.awt.Color[] { DARK_TRANSPA, LIGHT_TRANSPA };
        this.GRADIENT_LIGHT_DARK_TRANSPA = new java.awt.Color[] { LIGHT_TRANSPA, DARK_TRANSPA };

    }

    /**
     * Get the gradient dependend LIGHT color of the gradient
     * 
     * @return
     */
    public java.awt.Color getLight() {
        return this.LIGHT;
    }

    public java.awt.Color getLightTranspa() {
        return this.LIGHT_TRANSPA;
    }

    /**
     * Get the gradient dependend DARK color of the gradient
     * 
     * @return
     */
    public java.awt.Color getDark() {
        return this.DARK;
    }

    public java.awt.Color getDarkTranspa() {
        return this.DARK_TRANSPA;
    }

    public java.awt.Color[] getGradientDarkLight() {
        return this.GRADIENT_DARK_LIGHT;
    }

    public java.awt.Color[] getGradientLightDark() {
        return this.GRADIENT_LIGHT_DARK;
    }

    public java.awt.Color[] getGradientDarkLightTranspa() {
        return this.GRADIENT_DARK_LIGHT_TRANSPA;
    }

    public java.awt.Color[] getGradientLightDarkTranspa() {
        return this.GRADIENT_LIGHT_DARK_TRANSPA;
    }

    @Override
    public String toString() {
        return "SDTwoColorGradients";
    }
}
