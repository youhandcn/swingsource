/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class SDTextField extends javax.swing.JTextField implements java.awt.event.FocusListener,
        javax.swing.event.DocumentListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final java.awt.image.BufferedImage HOUSE_IMAGE;
    private final java.awt.image.BufferedImage MAIL_IMAGE;
    private final java.awt.image.BufferedImage PHONE_IMAGE;
    private final java.awt.image.BufferedImage PEN_IMAGE;
    private final java.awt.image.BufferedImage LENS_IMAGE;
    private final java.awt.image.BufferedImage CLOCK_IMAGE;
    private final java.awt.image.BufferedImage CALENDAR_IMAGE;
    private final java.awt.image.BufferedImage EURO_IMAGE;
    private final java.awt.image.BufferedImage DOLLAR_IMAGE;
    private final javax.swing.border.AbstractBorder ICON_BORDER;
    private final javax.swing.border.AbstractBorder NO_ICON_BORDER;
    private final java.awt.Color INACTIVE_FOREGROUND_COLOR;
    private final java.awt.Color ACTIVE_FOREGROUND_COLOR;
    private boolean glossy;
    private String defaultText;
    private boolean validInput;
    private Validator validator;
    private javax.swing.JToolTip toolTip;
    private de.jugms.sd.tools.RadiusType radiusType;

    public enum ICON_TYPE {
        HOUSE, MAIL, NO_ICON, PHONE, PEN, LENS, CLOCK, CALENDAR, EURO, DOLLAR
    };

    public ICON_TYPE type;

    public SDTextField() {
        super();
        this.HOUSE_IMAGE = createHouseImage();
        this.MAIL_IMAGE = createMailImage();
        this.PHONE_IMAGE = createPhoneImage();
        this.PEN_IMAGE = createPenImage();
        this.LENS_IMAGE = createLensImage();
        this.CLOCK_IMAGE = createClockImage();
        this.CALENDAR_IMAGE = createCalendarImage();
        this.EURO_IMAGE = createEuroImage();
        this.DOLLAR_IMAGE = createDollarImage();
        this.ICON_BORDER = new javax.swing.border.EmptyBorder(0, 32, 0, 8);
        this.NO_ICON_BORDER = new javax.swing.border.EmptyBorder(0, 10, 0, 8);
        this.INACTIVE_FOREGROUND_COLOR = new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f);
        this.ACTIVE_FOREGROUND_COLOR = new java.awt.Color(1.0f, 1.0f, 1.0f, 0.9f);
        this.glossy = true;
        this.defaultText = "";
        setType(ICON_TYPE.MAIL);
        this.validInput = false;
        setOpaque(true);
        setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f));
        setCaretColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f));
        setCaretPosition(0);
        setSelectedTextColor(java.awt.Color.WHITE);
        setSelectionColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa());
        setPreferredSize(new java.awt.Dimension(150, 22));
        setMargin(new java.awt.Insets(0, 1, 0, 1));
        setBorder(this.NO_ICON_BORDER);

        addFocusListener(this);
        getDocument().addDocumentListener(this);

        toolTip = new de.jugms.sd.components.misc.SDToolTip();
        toolTip.setComponent(this);

        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(createTextFieldImage(), 0, 0, this);

        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }

    private java.awt.image.BufferedImage createTextFieldImage() {
        java.awt.image.BufferedImage textFieldImage = new java.awt.image.BufferedImage(getWidth(),
                (getHeight()), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = textFieldImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight());
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight() - 2);
        final java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(0, getHeight()
                - (getHeight() / 2.2f));

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.66f, 1.0f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.08f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.04f) };
        final java.awt.Color[] COLORS_DISABLED;

        if (this.isEnabled()) {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };
            if (hasFocus()) {
                COLORS_FOREGROUND = new java.awt.Color[] {
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getLight(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getLight() };
            } else {
                COLORS_FOREGROUND = new java.awt.Color[] {
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f) };
            }
            COLORS_DISABLED = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };
        } else {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.5f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.05f) };
            COLORS_FOREGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.4f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.45f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.45f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.4f) };
            COLORS_DISABLED = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f) };
        }

        // Draw background rectangle
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.fill(getBackgroundShape());

        // Draw foreground rectangle
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(
                FOREGROUND_START, FOREGROUND_STOP, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        if (isGlossy()) {
            // Draw highlight shape
            java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(
                    HIGHLIGHT_START, HIGHLIGHT_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
            g2.setPaint(paintHighlight);
            g2.fill(getHighlightShape());
        }

        // Draw disabled rectangle
        java.awt.LinearGradientPaint paintDisabled = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(paintDisabled);
        g2.fill(getForegroundShape());

        // Draw Icon
        int offX;
        int offY;

        switch (this.type) {
        case HOUSE:
            // Draw house icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(HOUSE_IMAGE, offX, offY, null);
            break;
        case MAIL:
            // Draw mail icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(MAIL_IMAGE, offX, offY, null);
            break;
        case NO_ICON:
            // Default text field without icon
            setBorder(this.NO_ICON_BORDER);
            break;
        case PHONE:
            // Draw phone icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(PHONE_IMAGE, offX, offY, null);
            break;
        case PEN:
            // Draw pen icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(PEN_IMAGE, offX, offY, null);
            break;
        case LENS:
            // Draw lens icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(LENS_IMAGE, offX, offY, null);
            break;
        case CLOCK:
            // Draw clock icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(CLOCK_IMAGE, offX, offY, null);
            break;
        case CALENDAR:
            // Draw calendar icon
            setBorder(this.ICON_BORDER);
            offX = 8;
            offY = 4;
            g2.drawImage(CALENDAR_IMAGE, offX, offY, null);
            break;
        case DOLLAR:
            // Draw dollar icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(DOLLAR_IMAGE, offX, offY, null);
            break;
        case EURO:
            // Draw euro icon
            setBorder(this.ICON_BORDER);
            offX = 9;
            offY = 4;
            g2.drawImage(EURO_IMAGE, offX, offY, null);
            break;
        default:
            // Default text field without icon
            setBorder(this.NO_ICON_BORDER);
            break;
        }

        g2.dispose();

        return textFieldImage;
    }

    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                getBackgroundRadius(), getBackgroundRadius());
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                foregroundRadius, foregroundRadius);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.geom.RoundRectangle2D rrectHighlight = (java.awt.geom.RoundRectangle2D) getForegroundShape();
        java.awt.geom.Rectangle2D rectHighlight = new java.awt.geom.Rectangle2D.Float(1,
                getHeight() - (getHeight() / 2.2f), getWidth() - 2, getHeight() - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(rrectHighlight);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    public ICON_TYPE getType() {
        return this.type;
    }

    public void setType(ICON_TYPE type) {
        this.type = type;
        switch (type) {
        case HOUSE:
            setDefaultText("adress");
            validator = new TextValidator();
            break;
        case MAIL:
            setDefaultText("email address");
            validator = new MailValidator();
            break;
        case PHONE:
            setDefaultText("phone number");
            validator = new PhoneValidator();
            break;
        case PEN:
            setDefaultText("subject");
            validator = new TextValidator();
            break;
        case LENS:
            setDefaultText("search");
            validator = new TextValidator();
            break;
        case CLOCK:
            setDefaultText("time");
            validator = new TimeValidator();
            break;
        case CALENDAR:
            setDefaultText("date");
            validator = new DateValidator();
            break;
        case DOLLAR:
            setDefaultText("0.0");
            validator = new CurrencyValidator();
            break;
        case EURO:
            setDefaultText("0.0");
            validator = new CurrencyValidator();
            break;
        case NO_ICON:
            validator = new TextValidator();
            break;
        }
        repaint();
    }

    public boolean isGlossy() {
        return this.glossy;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
        repaint();
    }

    public String getDefaultText() {
        return this.defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
        setText(defaultText);
        repaint();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
    }

    public boolean validateData() {
        this.validInput = validator.isValid(this);
        return this.validInput;
    }

    public void reset() {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                setForeground(INACTIVE_FOREGROUND_COLOR);
                setText(defaultText);
                validInput = false;
            }
        });
    }

    @Override
    public javax.swing.JToolTip createToolTip() {
        return toolTip;
    }

    private java.awt.image.BufferedImage createHouseImage() {
        java.awt.image.BufferedImage houseImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) houseImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        java.awt.geom.GeneralPath house = new java.awt.geom.GeneralPath();

        house.moveTo(7, 1);
        house.lineTo(14, 8);
        house.lineTo(11, 8);
        house.lineTo(11, 14);
        house.lineTo(8, 14);
        house.lineTo(8, 9);
        house.lineTo(5, 9);
        house.lineTo(5, 14);
        house.lineTo(2, 14);
        house.lineTo(2, 8);
        house.lineTo(0, 8);
        house.lineTo(2, 5);
        house.lineTo(2, 1);
        house.lineTo(4, 1);
        house.lineTo(4, 3);
        house.lineTo(5, 2);
        house.lineTo(7, 1);
        g2.fill(house);
        g2.dispose();

        return houseImage;
    }

    private java.awt.image.BufferedImage createMailImage() {
        java.awt.image.BufferedImage mailImage = new java.awt.image.BufferedImage(15, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) mailImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        g2.fillRect(0, 3, 14, 10);
        g2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.7f));
        g2.drawLine(0, 4, 6, 10);
        g2.drawLine(6, 10, 8, 10);
        g2.drawLine(8, 10, 14, 4);
        g2.drawLine(0, 13, 4, 9);
        g2.drawLine(14, 13, 10, 9);
        g2.dispose();

        return mailImage;
    }

    private java.awt.image.BufferedImage createPhoneImage() {
        java.awt.image.BufferedImage phoneImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) phoneImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        java.awt.geom.GeneralPath phone = new java.awt.geom.GeneralPath();
        phone.moveTo(3, 0);
        phone.lineTo(6, 4);
        phone.lineTo(3, 6);
        phone.lineTo(8, 11);
        phone.lineTo(11, 8);
        phone.lineTo(14, 12);
        phone.lineTo(12, 14);
        phone.quadTo(10, 14, 9, 14);
        phone.quadTo(6, 13, 3, 10);
        phone.quadTo(2, 9, 1, 7);
        phone.quadTo(0, 4, 1, 2);
        phone.lineTo(3, 0);
        g2.fill(phone);
        g2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.7f));
        g2.drawLine(1, 2, 4, 5);
        g2.drawLine(9, 10, 12, 13);
        g2.dispose();

        return phoneImage;
    }

    private java.awt.image.BufferedImage createPenImage() {
        java.awt.image.BufferedImage penImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) penImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        java.awt.geom.GeneralPath pen = new java.awt.geom.GeneralPath();
        pen.moveTo(4, 1);
        pen.lineTo(11, 8);
        pen.lineTo(13, 13);
        pen.lineTo(8, 11);
        pen.lineTo(1, 4);
        pen.lineTo(4, 1);
        g2.fill(pen);
        g2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.7f));
        g2.drawLine(2, 5, 5, 2);
        g2.drawLine(8, 11, 11, 8);
        g2.dispose();

        return penImage;
    }

    private java.awt.image.BufferedImage createLensImage() {
        java.awt.image.BufferedImage lensImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) lensImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        java.awt.Stroke stroke = new java.awt.BasicStroke(2.0f, java.awt.BasicStroke.CAP_ROUND,
                java.awt.BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);
        g2.drawOval(1, 1, 9, 9);
        g2.drawLine(9, 9, 13, 13);
        g2.dispose();

        return lensImage;
    }

    private java.awt.image.BufferedImage createClockImage() {
        java.awt.image.BufferedImage clockImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) clockImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        g2.fillOval(1, 1, 12, 12);
        g2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.7f));
        g2.drawLine(6, 2, 6, 7);
        g2.drawLine(6, 7, 10, 7);
        g2.dispose();

        return clockImage;
    }

    private java.awt.image.BufferedImage createCalendarImage() {
        java.awt.image.BufferedImage calendarImage = new java.awt.image.BufferedImage(15, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) calendarImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        g2.fillRect(1, 3, 13, 9);
        g2.fillRect(3, 1, 2, 2);
        g2.fillRect(10, 1, 2, 2);
        g2.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.7f));
        g2.setFont(new java.awt.Font("Verdana", 1, 7));
        g2.drawString("13", 2, 10);
        g2.dispose();

        return calendarImage;
    }

    private java.awt.image.BufferedImage createDollarImage() {
        java.awt.image.BufferedImage dollarImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) dollarImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        g2.setFont(new java.awt.Font("Verdana", 1, 14));
        g2.drawString("$", 2, 12);
        g2.dispose();

        return dollarImage;
    }

    private java.awt.image.BufferedImage createEuroImage() {
        java.awt.image.BufferedImage euroImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) euroImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        g2.setFont(new java.awt.Font("Verdana", 1, 14));
        g2.drawString("$$", 2, 12);
        g2.dispose();

        return euroImage;
    }

    // FocusListener methods
    @Override
    public void focusGained(java.awt.event.FocusEvent event) {
        if (getText().equals(defaultText)) {
            javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
                @Override
                public void run() {
                    // Handler logic
                    setForeground(ACTIVE_FOREGROUND_COLOR);
                    setText("");
                }
            });
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent event) {
        if (getText().isEmpty() || getText().equals(getDefaultText())) {
            javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
                @Override
                public void run() {
                    // Handler logic
                    setForeground(INACTIVE_FOREGROUND_COLOR);
                    setText(defaultText);
                    validInput = false;
                }
            });
        } else {
            this.validInput = true;
        }
    }

    // DocumentListener methods
    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent event) {
        fireValidationEvent(new ValidationEvent(this, validateData()));
    }

    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent event) {
        fireValidationEvent(new ValidationEvent(this, validateData()));
    }

    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent event) {
        fireValidationEvent(new ValidationEvent(this, validateData()));
    }

    // Creation and handling of ValidationEvent
    public void addValidationEventListener(ValidationEventListener listener) {
        listenerList.add(ValidationEventListener.class, listener);
    }

    public void removeValidationEventListener(ValidationEventListener listener) {
        listenerList.remove(ValidationEventListener.class, listener);
    }

    void fireValidationEvent(ValidationEvent event) {
        Object[] listeners = listenerList.getListenerList();
        int max = listeners.length;

        for (int i = 0; i < max; i++) {
            if (listeners[i] == ValidationEventListener.class) {
                ((ValidationEventListener) listeners[i + 1]).validationEventPerformed(event);
            }
        }
    }

    private float getBackgroundRadius() {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            return (getHeight());
        default:
            return (10.0f);
        }
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    @Override
    public String toString() {
        return "SDTextField";
    }
}
