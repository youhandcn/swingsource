/*
 *  File: GraphicsHelper.java 
 *  Copyright (c) 2004-2007  Peter Kliem (Peter.Kliem@jaret.de)
 *  A commercial license is available, see http://www.jaret.de.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package de.jaret.util.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * A simple class containing several static methods for convenient painting with a grpahics object.
 * 
 * @author Peter Kliem
 * @version $Id: GraphicsHelper.java 881 2009-09-22 21:25:47Z kliem $
 */
public class GraphicsHelper {

    public static void drawStringCentered(Graphics graphics, String string, int left, int right, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        int width = right - left;
        int xx = (int) ((width - rect.getWidth()) / 2);
        graphics.drawString(string, xx, y);

    }

    public static void drawStringCenteredVCenter(Graphics graphics, String string, int left, int right, int yCenter) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        int descent = graphics.getFontMetrics().getDescent();
        int width = right - left;
        int xx = (int) ((width - rect.getWidth()) / 2);
        int y = yCenter + (int) (rect.getHeight() / 2 - descent);
        graphics.drawString(string, xx, y);

    }

    public static void drawStringRightAlignedVCenter(Graphics graphics, String string, int x, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);

        int xx = (int) (x - rect.getWidth());
        int yy = (int) (y + (rect.getHeight() / 2));
        graphics.drawString(string, xx, yy);
    }

    public static void drawStringLeftAlignedVCenter(Graphics graphics, String string, int x, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);

        int xx = x;
        int yy = (int) (y + (rect.getHeight() / 2));
        graphics.drawString(string, xx, yy);
    }

    /**
     * @param graphics graphics to use
     * @param string string to render
     * @param x center x
     * @param y basey
     */
    public static void drawStringCentered(Graphics graphics, String string, int x, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        int xx = x - (int) ((rect.getWidth()) / 2);
        graphics.drawString(string, xx, y);
    }

    public static void drawStringCenteredPoint(Graphics graphics, String string, int x, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        int xx = x - (int) ((rect.getWidth()) / 2);
        int yy = (int) (y + (rect.getHeight() / 2));
        graphics.drawString(string, xx, yy);
    }

    public static void drawStringCentered(Graphics graphics, String string, Rectangle area) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        int xx = area.x + area.width / 2 - (int) ((rect.getWidth()) / 2);
        int yy = (int) (area.y + area.height / 2 + (rect.getHeight() / 2));
        graphics.drawString(string, xx, yy);
    }

    public static int getStringDrawingWidth(Graphics graphics, String string) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        return (int) rect.getWidth();
    }

    public static int getStringDrawingHeight(Graphics graphics, String string) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        return (int) rect.getHeight() - graphics.getFontMetrics().getDescent();
    }

    public static void drawStringRightAlignedVTop(Graphics graphics, String string, int x, int yTop) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);

        int xx = (int) (x - rect.getWidth());
        int yy = (int) (yTop + rect.getHeight());
        graphics.drawString(string, xx, yy);

    }
    
    /**
     * Draw a string vertically centered between upper and lower y left aligned to x.
     * 
     * @param graphics Graphics to paint with
     * @param label label to draw
     * @param x left x
     * @param upperY upper y bound
     * @param lowerY lower y bound
     */
    public static void drawStringVCentered(Graphics graphics, String string, int x, int upperY, int lowerY) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);

        int yy = (int) upperY + (lowerY - upperY)/2 + (int)rect.getHeight() / 2;
        graphics.drawString(string, x, yy);
    }


    public static void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int dist, boolean arrowLeft,
            boolean arrowRight) {
        int off = 3;
        g.drawLine(x1 + off + 1, y1, x2 - off - 1, y2);
        if (arrowLeft) {
            g.drawLine(x1, y1, x1 + dist, y1 - off);
            g.drawLine(x1, y1, x1 + dist, y1 + off);
            g.drawLine(x1 + dist, y1 - off, x1 + dist, y1 + off);
        }
        if (arrowRight) {
            g.drawLine(x2, y2, x2 - dist, y2 - off);
            g.drawLine(x2, y2, x2 - dist, y2 + off);
            g.drawLine(x2 - dist, y2 - off, x2 - dist, y2 + off);
        }
    }

    public static void drawArrowLineVertical(Graphics g, int x1, int y1, int x2, int y2, int dist, int height,
            boolean arrowUp, boolean arrowDown) {
        int off = height;
        g.drawLine(x1, y1 + off + 1, x2, y2 - off - 1);
        if (arrowUp) {
            g.drawLine(x1, y1, x1 - off, y1 + dist);
            g.drawLine(x1, y1, x1 + off, y1 + dist);
            g.drawLine(x1 - off, y1 + dist, x1 + off, y1 + dist);
        }
        if (arrowDown) {
            g.drawLine(x2, y2, x2 - off, y2 - dist);
            g.drawLine(x2, y2, x2 + off, y2 - dist);
            g.drawLine(x2 - off, y2 - dist, x2 + off, y2 - dist);
        }
    }

    /**
     * Draw a String vertical. This method might be quite costly since it uses an image to buffer.
     * 
     * @param graphics graphics to paint with
     * @param string string to to draw
     * @param x upper left x
     * @param y upper left y
     */
    public static void drawStringVertical(Graphics graphics, String string, int x, int y) {
        Rectangle2D rect = graphics.getFontMetrics().getStringBounds(string, graphics);
        BufferedImage img = new BufferedImage((int) rect.getWidth(), (int) rect.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        
        Graphics imageGraphics = img.getGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        imageGraphics.setColor(Color.BLACK);
        
        imageGraphics.drawString(string, 0, (int) rect.getHeight());

        BufferedImage vertImg = new BufferedImage((int) rect.getHeight(), (int) rect.getWidth(),
                BufferedImage.TYPE_3BYTE_BGR);

        for (int xx = 0; xx < img.getWidth(); xx++) {
            for (int yy = 0; yy < img.getHeight(); yy++) {
                vertImg.setRGB(yy, img.getWidth()-xx-1, img.getRGB(xx, yy));
            }
        }

        graphics.drawImage(vertImg, x, y, new ImageObserver() {

            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }

        });

    }

}
