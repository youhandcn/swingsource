/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.instruments;

/**
 *
 * @author hansolo
 */
public class ValueDisplay extends javax.swing.JComponent
{
    public enum COLOR
    {
        BEIGE,
        BLUE,
        ORANGE,
        RED,
        GREEN,
        WHITE
    };
    private COLOR color;
    private double value;
    private int decimals;
    private String unit;
    private java.awt.Font font;
    private java.awt.Font unitFont;
    private java.awt.image.BufferedImage backgroundImage;

    public ValueDisplay()
    {
        this.setPreferredSize(new java.awt.Dimension(128, 48));
        this.setMinimumSize(new java.awt.Dimension(64, 24));
        this.setSize(new java.awt.Dimension(128, 48));
        init();
    }

    private void init()
    {
        this.value = 0.0;
        this.decimals = 3;
        this.unit = "°C";
        this.color = COLOR.BEIGE;
        this.font = new java.awt.Font("Arial", 1, 38);
        this.unitFont = new java.awt.Font("Arial", 1, 24);
        this.backgroundImage = null;
    }

    public double getValue()
    {
        return this.value;
    }

    public void setValue(double value)
    {
        this.value = value;
        repaint();
    }

    public int getDecimals()
    {
        return this.decimals;
    }

    public void setDecimals(int decimals)
    {
        this.decimals = decimals;
        repaint();
    }

    public String getUnit()
    {
        return this.unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
        repaint();
    }

    public COLOR getColor()
    {
        return this.color;
    }

    public void setColor(COLOR color)
    {
        this.color = color;
        this.backgroundImage = null;
        repaint();
    }

    @Override
    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        this.font = new java.awt.Font("Arial", 1, getHeight() - 10);
        this.unitFont = new java.awt.Font("Arial", 1, getHeight() / 2);
        this.backgroundImage = null;
        repaint();
    }

    @Override
    public void setSize(java.awt.Dimension dim)
    {
        super.setSize(dim);
        this.font = new java.awt.Font("Arial", 1, getHeight() - 8);
        this.unitFont = new java.awt.Font("Arial", 1, getHeight() / 2);
        this.backgroundImage = null;
        repaint();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g)
    {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING, java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (this.backgroundImage == null)
        {
            this.backgroundImage = createBackgroundImage();
        }

        g2.drawImage(backgroundImage, 0, 0, null);

        // Draw text        
        final java.awt.Color TEXT_COLOR;
        switch (color)
        {
            case BEIGE:
                TEXT_COLOR = new java.awt.Color(0x333333);
                break;

            case BLUE:
                TEXT_COLOR = new java.awt.Color(0x124564);
                break;

            case ORANGE:
                TEXT_COLOR = new java.awt.Color(0x451C24);
                break;

            case RED:
                TEXT_COLOR = new java.awt.Color(0x04040E);
                break;

            case GREEN:
                TEXT_COLOR = new java.awt.Color(0x070A01);
                break;

            case WHITE:
                TEXT_COLOR = new java.awt.Color(0x000000);
                break;

            default:
                TEXT_COLOR = new java.awt.Color(0x333333);
                break;
        }

        g2.setColor(TEXT_COLOR);
        final java.awt.FontMetrics METRICS_UNIT = g2.getFontMetrics(unitFont);
        g2.setFont(unitFont);
        final java.awt.geom.Rectangle2D UNIT_BOUNDARY = METRICS_UNIT.getStringBounds(" " + unit, g2);
        g2.drawString(" " + unit, (int) ((getWidth() - UNIT_BOUNDARY.getWidth()) - 4), (int) (backgroundImage.getHeight() - UNIT_BOUNDARY.getHeight() / 2));

        g2.setFont(font);
        final java.awt.FontMetrics METRICS_FONT = g2.getFontMetrics(font);
        final java.awt.geom.Rectangle2D TEXT_BOUNDARY = METRICS_FONT.getStringBounds(formatValue(value), g2);
        g2.drawString(formatValue(value), (int) ((getWidth() - UNIT_BOUNDARY.getWidth() - TEXT_BOUNDARY.getWidth()) - 2), (int) (backgroundImage.getHeight() * 0.8f));

        g2.dispose();
    }

    public String formatValue(double inValue)
    {
        StringBuffer decBuffer = new StringBuffer();
        decBuffer.append("0");

        if (this.decimals > 0)
        {
            decBuffer.append(".");
        }

        for (int i = 0; i < this.decimals; i++)
        {
            decBuffer.append("0");
        }

        java.text.DecimalFormat decFormat = new java.text.DecimalFormat(decBuffer.toString(), new java.text.DecimalFormatSymbols(java.util.Locale.US));

        return decFormat.format(inValue);
    }

    private java.awt.image.BufferedImage createBackgroundImage()
    {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(this.getWidth(), this.getHeight(), java.awt.Transparency.TRANSLUCENT);

        java.awt.Graphics2D g2 = IMAGE.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);


        java.awt.geom.Point2D BACKGROUND_HIGHLIGHT_START = new java.awt.geom.Point2D.Double(0, this.getHeight());
        java.awt.geom.Point2D BACKGROUND_HIGHLIGHT_STOP = new java.awt.geom.Point2D.Double(0, 1);

        final float[] BACKGROUND_HIGHLIGHT_FRACTIONS =
        {
            0.0f,
            0.15f
        };

        final java.awt.Color[] BACKGROUND_HIGHLIGHT_COLORS =
        {
            new java.awt.Color(210, 211, 212, 255),
            new java.awt.Color(210, 211, 212, 0)
        };

        final java.awt.Shape BACKGROUND = new java.awt.geom.RoundRectangle2D.Double(0, 1, this.getWidth(), this.getHeight() - 1, 8, 8);

        final java.awt.LinearGradientPaint BACKGROUND_HIGHLIGHT_GRADIENT = new java.awt.LinearGradientPaint(BACKGROUND_HIGHLIGHT_START, BACKGROUND_HIGHLIGHT_STOP, BACKGROUND_HIGHLIGHT_FRACTIONS, BACKGROUND_HIGHLIGHT_COLORS);

        g2.setPaint(BACKGROUND_HIGHLIGHT_GRADIENT);
        g2.fill(BACKGROUND);

        // Background rectangle
        java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Double(0.0, 0.0);
        java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Double(0.0, this.getHeight() - 1);

        final float[] BACKGROUND_FRACTIONS =
        {
            0.0f,
            0.1f,
            1.0f
        };

        final java.awt.Color[] BACKGROUND_COLORS =
        {
            new java.awt.Color(0x556153),
            new java.awt.Color(0x818573),
            new java.awt.Color(0x989C86)
        };

        final java.awt.LinearGradientPaint BACKGROUND_GRADIENT = new java.awt.LinearGradientPaint(BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS, BACKGROUND_COLORS);
        g2.setPaint(BACKGROUND_GRADIENT);
        g2.fill(BACKGROUND);


        // Foreground rectangle
        java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Double(0.0, 1.0);
        java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Double(0.0, this.getHeight() - 2);

        final float[] FOREGROUND_FRACTIONS =
        {
            0.0f,
            0.03f,
            0.49f,
            0.5f,
            1.0f
        };

        final java.awt.Color[] FOREGROUND_COLORS;
        
        switch (color)
        {
            case BEIGE:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xC8C8B1),
                        new java.awt.Color(0xF1EDCF),
                        new java.awt.Color(0xEAE6C2),
                        new java.awt.Color(0xE1DCB7),
                        new java.awt.Color(0xEDE8BF)
                    };
                break;

            case BLUE:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xA9C6CC),
                        new java.awt.Color(0xD5F1FF),
                        new java.awt.Color(0xABE0FF),
                        new java.awt.Color(0x8AD5FF),
                        new java.awt.Color(0xBFE8FF)
                    };                
                break;

            case ORANGE:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xFFEACD),
                        new java.awt.Color(0xFFEACD),
                        new java.awt.Color(0xFD9A00),
                        new java.awt.Color(0xD7840C),
                        new java.awt.Color(0xFEC262)
                    };
                break;

            case RED:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xFED0CE),
                        new java.awt.Color(0xFED0CE),
                        new java.awt.Color(0xFB0F00),
                        new java.awt.Color(0xB4210D),
                        new java.awt.Color(0xFB2403)
                    };
                break;

            case GREEN:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xFFFFFF),
                        new java.awt.Color(0xF6FFCB),
                        new java.awt.Color(0xD2FF00),
                        new java.awt.Color(0xAED000),
                        new java.awt.Color(0xD2FF00)
                    };
                break;

                case WHITE:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xFFFFFF),
                        new java.awt.Color(0xFFFFFF),
                        new java.awt.Color(0xF1F6F2),
                        new java.awt.Color(0xE5EFF4),
                        new java.awt.Color(0xFFFFFF)
                    };
                break;

            default:
                FOREGROUND_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0xC8C8B1),
                        new java.awt.Color(0xF1EDCF),
                        new java.awt.Color(0xEAE6C2),
                        new java.awt.Color(0xE1DCB7),
                        new java.awt.Color(0xEDE8BF)
                    };                
                break;
        }

        final java.awt.LinearGradientPaint FOREGROUND_GRADIENT = new java.awt.LinearGradientPaint(FOREGROUND_START, FOREGROUND_STOP, FOREGROUND_FRACTIONS, FOREGROUND_COLORS);
        final java.awt.Shape FOREGROUND = new java.awt.geom.RoundRectangle2D.Double(1, 1, this.getWidth() - 2, this.getHeight() - 3, 7, 7);
        g2.setPaint(FOREGROUND_GRADIENT);
        g2.fill(FOREGROUND);

        g2.dispose();

        return IMAGE;
    }
}
