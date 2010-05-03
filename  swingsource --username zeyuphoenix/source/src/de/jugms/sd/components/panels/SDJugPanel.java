/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.panels;

/**
 * 
 * @author hansolo
 */
public class SDJugPanel extends javax.swing.JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final java.awt.RenderingHints HINTS;
    private final java.awt.image.BufferedImage LOGO_IMAGE;
    private final java.awt.image.BufferedImage REFLECTION_IMAGE;
    private final float ALPHA;

    public SDJugPanel() {
        HINTS = new java.awt.RenderingHints(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION,
                java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        HINTS.put(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        HINTS.put(java.awt.RenderingHints.KEY_COLOR_RENDERING,
                java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        HINTS.put(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        HINTS.put(java.awt.RenderingHints.KEY_RENDERING,
                java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        LOGO_IMAGE = createLogoImage();
        REFLECTION_IMAGE = createReflectionImage(LOGO_IMAGE);
        ALPHA = 1.0f;
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        setOpaque(false);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHints(HINTS);

        int posX = (getWidth() - LOGO_IMAGE.getWidth()) / 2;
        int posY = (getHeight() - (LOGO_IMAGE.getHeight() + (int) (REFLECTION_IMAGE.getHeight() * 0.3f))) / 2;
        g2.setComposite(java.awt.AlphaComposite
                .getInstance(java.awt.AlphaComposite.SRC_OVER, ALPHA));
        g2.drawImage(LOGO_IMAGE, posX, posY, null);
        g2.drawImage(REFLECTION_IMAGE, posX, posY + LOGO_IMAGE.getHeight() + 2, null);
        g2
                .setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                        1.0f));
        g2.dispose();
    }

    private java.awt.image.BufferedImage createLogoImage() {
        final int LOGO_ALPHA = 255;
        java.awt.image.BufferedImage jugImage = new java.awt.image.BufferedImage(460, 267,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = jugImage.createGraphics();
        g2.setRenderingHints(HINTS);

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 102);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, 267);

        final float[] FRACTIONS = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(143, 210, 0, LOGO_ALPHA),
                new java.awt.Color(56, 172, 0, LOGO_ALPHA) };

        java.awt.LinearGradientPaint jugGradient = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        java.awt.geom.GeneralPath j = new java.awt.geom.GeneralPath();
        j.moveTo(132, 102);
        j.quadTo(97, 102, 97, 136);
        j.lineTo(97, 184);
        j.quadTo(93, 229, 48, 234);
        j.lineTo(32, 234);
        j.quadTo(-1, 234, 0, 267);
        j.lineTo(48, 267);
        j.quadTo(127, 263, 132, 184);
        j.lineTo(132, 102);

        java.awt.geom.GeneralPath ug = new java.awt.geom.GeneralPath();
        ug.moveTo(139, 102);
        ug.lineTo(139, 184);
        ug.quadTo(142, 265, 223, 267);
        ug.quadTo(282, 265, 299, 217);
        ug.quadTo(318, 265, 376, 267);
        ug.quadTo(449, 268, 456, 197);
        ug.lineTo(460, 149);
        ug.quadTo(425, 150, 425, 184);
        ug.quadTo(423, 231, 376, 234);
        ug.quadTo(330, 231, 328, 184);
        ug.quadTo(330, 137, 376, 135);
        ug.lineTo(393, 135);
        ug.quadTo(425, 135, 425, 102);
        ug.lineTo(376, 102);
        ug.quadTo(318, 104, 306, 140);
        ug.lineTo(306, 102);
        ug.quadTo(272, 102, 272, 136);
        ug.lineTo(272, 184);
        ug.quadTo(269, 231, 223, 234);
        ug.quadTo(176, 231, 174, 184);
        ug.lineTo(174, 136);
        ug.quadTo(174, 102, 139, 102);

        java.awt.geom.GeneralPath beanLeft = new java.awt.geom.GeneralPath();
        beanLeft.moveTo(220, 117);
        beanLeft.lineTo(214, 117);
        beanLeft.quadTo(189, 122, 186, 146);
        beanLeft.lineTo(186, 192);
        beanLeft.quadTo(189, 216, 214, 222);
        beanLeft.lineTo(220, 222);
        beanLeft.lineTo(220, 117);

        java.awt.geom.GeneralPath beanRight = new java.awt.geom.GeneralPath();
        beanRight.moveTo(227, 117);
        beanRight.lineTo(227, 222);
        beanRight.lineTo(233, 222);
        beanRight.quadTo(257, 216, 261, 192);
        beanRight.lineTo(261, 146);
        beanRight.quadTo(258, 122, 233, 117);
        beanRight.lineTo(227, 117);

        java.awt.geom.GeneralPath m = new java.awt.geom.GeneralPath();
        m.moveTo(228, 0);
        m.quadTo(222, 14, 227, 28);
        m.quadTo(240, 50, 242, 69);
        m.quadTo(243, 94, 218, 107);
        m.quadTo(240, 82, 228, 55);
        m.lineTo(219, 42);
        m.lineTo(213, 55);
        m.lineTo(216, 62);
        m.quadTo(218, 71, 210, 82);
        m.quadTo(206, 88, 197, 95);
        m.quadTo(205, 84, 208, 72);
        m.quadTo(207, 62, 201, 55);
        m.lineTo(192, 45);
        m.quadTo(181, 72, 169, 83);
        m.quadTo(179, 72, 190, 41);
        m.quadTo(189, 32, 200, 16);
        m.quadTo(194, 29, 202, 40);
        m.lineTo(212, 53);
        m.lineTo(217, 38);
        m.lineTo(214, 30);
        m.quadTo(213, 24, 213, 19);
        m.quadTo(215, 7, 228, 0);

        java.awt.geom.GeneralPath s = new java.awt.geom.GeneralPath();
        s.moveTo(258, 12);
        s.quadTo(239, 18, 244, 36);
        s.quadTo(246, 40, 254, 52);
        s.quadTo(259, 60, 260, 70);
        s.quadTo(260, 82, 252, 93);
        s.quadTo(268, 79, 267, 63);
        s.quadTo(264, 51, 258, 44);
        s.quadTo(252, 37, 250, 28);
        s.quadTo(250, 21, 258, 12);

        g2.setPaint(jugGradient);
        g2.fill(j);
        g2.fill(ug);

        g2.setColor(new java.awt.Color(106, 46, 17, LOGO_ALPHA));
        g2.fill(beanLeft);
        g2.fill(beanRight);

        g2.setColor(new java.awt.Color(232, 53, 26, LOGO_ALPHA));
        g2.fill(m);
        g2.fill(s);

        return jugImage;
    }

    private java.awt.image.BufferedImage createReflectionImage(
            java.awt.image.BufferedImage sourceImage) {
        float opacity = 0.5f;
        float fadeHeight = 0.7f;

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
        return "SDJugPanel";
    }
}
