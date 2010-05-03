/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.instruments;


/**
 *
 * @author hansolo
 */
public class LedDisplay extends javax.swing.JComponent
{
    private int maxValue;
    private int value;
    private int ledsOn;
    private int ledsOff;
    private int noOfLeds;
    private enum COLOR
    {
        GREEN,
        YELLOW,
        RED
    };
    private final java.awt.image.BufferedImage LED_GREEN_ON = createLedImage(true, COLOR.GREEN);
    private final java.awt.image.BufferedImage LED_YELLOW_ON = createLedImage(true, COLOR.YELLOW);
    private final java.awt.image.BufferedImage LED_RED_ON = createLedImage(true, COLOR.RED);
    private final java.awt.image.BufferedImage LED_OFF = createLedImage(false, COLOR.GREEN);

    public LedDisplay()
    {
        super();        
        init();
    }

    private void init()
    {
        this.maxValue = 100;
        this.value = 50;
        this.noOfLeds = 12;
        setPreferredSize(new java.awt.Dimension(calcWidth(), 10));
        calc();
    }

    public int getMaxValue()
    {
        return this.maxValue;
    }

    public void setMaxValue(int maxValue)
    {
        this.maxValue = maxValue;
        calc();
    }

    public int getValue()
    {
        return this.value;
    }

    public void setValue(int value)
    {
        this.value = value;
        calc();
    }

    public int getNoOfLeds()
    {
        return this.noOfLeds;
    }

    public void setNoOfLeds(int noOfLeds)
    {
        this.noOfLeds = noOfLeds;
        setSize(calcWidth(), getHeight());
        calc();
    }

    private void calc()
    {
        int amountPerLed = (this.maxValue / noOfLeds);
        this.ledsOn =  this.value / amountPerLed;
        this.ledsOff = this.noOfLeds - this.ledsOn;
        repaint();
    }

    private int calcWidth()
    {
        return ((noOfLeds * 21) + 3);
    }

    @Override
    public void setSize(int width, int height)
    {
        this.noOfLeds = width / 21;
        super.setSize(calcWidth(), height);
        calc();
    }

    @Override
    public void setSize(java.awt.Dimension dim)
    {
        this.noOfLeds = dim.width / 21;
        super.setSize(new java.awt.Dimension(calcWidth(), dim.height));
        calc();
    }


    @Override
    protected void paintComponent(java.awt.Graphics g)
    {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        int counter = 3;

        for (int i = 0 ; i < ledsOn ; i++)
        {
            if ((i + 1) == (noOfLeds - 1) || (i + 1) == (noOfLeds - 2))
            {
                g2.drawImage(LED_YELLOW_ON, counter , 0, null);
            }
            else if ((i + 1) == noOfLeds)
            {
                g2.drawImage(LED_RED_ON, counter , 0, null);
            }
            else
            {
                g2.drawImage(LED_GREEN_ON, counter , 0, null);
            }
            counter += 21;
        }

        for (int i = 0 ; i < ledsOff ; i++)
        {
            g2.drawImage(LED_OFF, counter, 0, null);
            counter += 21;
        }

        g2.dispose();
    }

    private java.awt.image.BufferedImage createLedImage(boolean isOn, COLOR color)
    {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(18, 10, java.awt.Transparency.TRANSLUCENT);

        java.awt.Graphics2D g2 = IMAGE.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);


        // Background rectangle
        java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Double(0.0, 0.0);
        java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Double(0.0, 18.0);

        final float[] BACKGROUND_FRACTIONS =
        {
            0.0f,
            0.25f,
            1.0f
        };

        final java.awt.Color[] BACKGROUND_COLORS =
        {
            new java.awt.Color(0xCCCCCC),
            new java.awt.Color(0x666666),
            new java.awt.Color(0x666666)
        };

        final java.awt.LinearGradientPaint BACKGROUND_GRADIENT = new java.awt.LinearGradientPaint(BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS, BACKGROUND_COLORS);
        final java.awt.geom.Rectangle2D BACKGROUND = new java.awt.geom.Rectangle2D.Double(0, 0, 18, 10);
        g2.setPaint(BACKGROUND_GRADIENT);
        g2.fill(BACKGROUND);

        if (isOn)
        {
            // Foreground rectangle ON
            java.awt.geom.Point2D FOREGROUND_ON_START = new java.awt.geom.Point2D.Double(0.0, 1.0);
            java.awt.geom.Point2D FOREGROUND_ON_STOP = new java.awt.geom.Point2D.Double(0.0, 16.0);

            final float[] FOREGROUND_ON_FRACTIONS =
            {
                0.0f,
                1.0f
            };

            final java.awt.Color[] FOREGROUND_ON_COLORS;
            switch (color)
            {
                case GREEN:
                    FOREGROUND_ON_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0x547E00),
                        new java.awt.Color(0xB6FF00)
                    };
                    break;

                case YELLOW:
                    FOREGROUND_ON_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0x727400),
                        new java.awt.Color(0xFFF200)
                    };
                    break;

                case RED:
                    FOREGROUND_ON_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0x660000),
                        new java.awt.Color(0xFB0000)
                    };
                    break;

                default:
                    FOREGROUND_ON_COLORS = new java.awt.Color[]
                    {
                        new java.awt.Color(0x547E00),
                        new java.awt.Color(0xB6FF00)
                    };
                    break;
            }

            final java.awt.LinearGradientPaint FOREGROUND_ON_GRADIENT = new java.awt.LinearGradientPaint(FOREGROUND_ON_START, FOREGROUND_ON_STOP, FOREGROUND_ON_FRACTIONS, FOREGROUND_ON_COLORS);
            final java.awt.geom.Rectangle2D FOREGROUND_ON = new java.awt.geom.Rectangle2D.Double(1, 1, 16, 8);
            g2.setPaint(FOREGROUND_ON_GRADIENT);
            g2.fill(FOREGROUND_ON);

            final java.awt.geom.Point2D FOREGROUND_HIGHLIGHT_CENTER = new java.awt.geom.Point2D.Double(6, 7);

            final float[] FOREGROUND_HIGHLIGHT_FRACTIONS =
            {
                0.0f,
                0.33f
            };

            final java.awt.Color[] FOREGROUND_HIGHLIGHT_COLORS =
            {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f)
            };

            final java.awt.RadialGradientPaint FOREGROUND_HIGHLIGHT_GRADIENT = new java.awt.RadialGradientPaint(FOREGROUND_HIGHLIGHT_CENTER, 7.0f, FOREGROUND_HIGHLIGHT_FRACTIONS, FOREGROUND_HIGHLIGHT_COLORS);

            final java.awt.geom.Rectangle2D FOREGROUND_HIGHLIGHT = new java.awt.geom.Rectangle2D.Double(1.0, 1.0, 16.0, 8.0);

            g2.setPaint(FOREGROUND_HIGHLIGHT_GRADIENT);
            g2.fill(FOREGROUND_HIGHLIGHT);
        }
        else
        {
            // Foreground rectangle OFF
            java.awt.geom.Point2D FOREGROUND_OFF_START = new java.awt.geom.Point2D.Double(0.0, 1.0);
            java.awt.geom.Point2D FOREGROUND_OFF_STOP = new java.awt.geom.Point2D.Double(0.0, 17.0);

            final float[] FOREGROUND_OFF_FRACTIONS =
            {
                0.0f,
                1.0f
            };

            final java.awt.Color[] FOREGROUND_OFF_COLORS =
            {
                new java.awt.Color(0x333333),
                new java.awt.Color(0x666666)
            };

            final java.awt.LinearGradientPaint FOREGROUND_OFF_GRADIENT = new java.awt.LinearGradientPaint(FOREGROUND_OFF_START, FOREGROUND_OFF_STOP, FOREGROUND_OFF_FRACTIONS, FOREGROUND_OFF_COLORS);
            final java.awt.geom.Rectangle2D FOREGROUND_OFF = new java.awt.geom.Rectangle2D.Double(1, 1, 16, 8);
            g2.setPaint(FOREGROUND_OFF_GRADIENT);
            g2.fill(FOREGROUND_OFF);
        }

        // Highlight rectangle
        java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Double(0.0, 1.0);
        java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Double(0.0, 4.0);

        final float[] HIGHLIGHT_FRACTIONS =
        {
            0.0f,
            1.0f
        };

        final java.awt.Color[] HIGHLIGHT_COLORS =
        {
            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f)
        };

        java.awt.LinearGradientPaint HIGHLIGHT_GRADIENT = new java.awt.LinearGradientPaint(HIGHLIGHT_START, HIGHLIGHT_STOP, HIGHLIGHT_FRACTIONS, HIGHLIGHT_COLORS);
        java.awt.geom.Rectangle2D HIGHLIGHT = new java.awt.geom.Rectangle2D.Double(1, 1, 16, 4);
        g2.setPaint(HIGHLIGHT_GRADIENT);
        g2.fill(HIGHLIGHT);


        g2.dispose();

        return IMAGE;
    }
}
