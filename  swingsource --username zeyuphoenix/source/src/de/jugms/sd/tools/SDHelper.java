/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.tools;

/**
 * 
 * @author hansolo
 */
public enum SDHelper {
    INSTANCE;

    public java.awt.Color setAlpha(java.awt.Color sourceColor, int alpha) {
        return new java.awt.Color(sourceColor.getRed(), sourceColor.getGreen(), sourceColor
                .getBlue(), alpha);
    }

    public boolean isTranslucencySupported() {
        try {
            Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            Class<?> translucencyClass = Class.forName("com.sun.awt.AWTUtilities$Translucency");
            Object[] kinds = translucencyClass.getEnumConstants();
            if (kinds != null) {
                Object TRANSLUCENT = kinds[1];
                java.lang.reflect.Method mIsTranslucencySupported = awtUtilitiesClass.getMethod(
                        "isTranslucencySupported", translucencyClass);

                Object ret;

                ret = mIsTranslucencySupported.invoke(null, TRANSLUCENT);

                if (ret instanceof Boolean) {
                    return ((Boolean) ret).booleanValue();
                }
            }
        } catch (ClassNotFoundException exception) {
            return false;
        } catch (NoSuchMethodException exception) {
            return false;
        } catch (SecurityException exception) {
            return false;
        } catch (IllegalAccessException exception) {
            return false;
        } catch (IllegalArgumentException exception) {
            return false;
        } catch (java.lang.reflect.InvocationTargetException exception) {
            return false;
        }

        return false;
    }

    public java.awt.image.BufferedImage createReflectionImage(
            java.awt.image.BufferedImage sourceImage, float opacity, float fadeHeight) {
        if (opacity < 0 || opacity > 1) {
            opacity = 0.6f;
        }
        if (fadeHeight < 0 || fadeHeight > 1) {
            fadeHeight = 0.4f;
        }

        java.awt.image.BufferedImage reflectionImage = new java.awt.image.BufferedImage(sourceImage
                .getWidth(), sourceImage.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) reflectionImage.getGraphics();

        g2.translate(0, sourceImage.getHeight());
        g2.scale(1, -1);
        g2.drawRenderedImage(sourceImage, null);
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.DST_IN));
        g2.setPaint(new java.awt.GradientPaint(0, sourceImage.getHeight() * fadeHeight,
                new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f), 0, sourceImage.getHeight(),
                new java.awt.Color(0.0f, 0.0f, 0.0f, opacity)));
        g2.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        g2.dispose();

        return reflectionImage;
    }

    @Override
    public String toString() {
        return "SDHelper";
    }
}
