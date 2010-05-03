/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldclock;

import java.awt.Color;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ConicalGradientPaint implements Paint {
	private final Point2D CENTER;
	private final double[] FRACTION_ANGLE;
	private final double[] RED_STEP_LOOKUP;
	private final double[] GREEN_STEP_LOOKUP;
	private final double[] BLUE_STEP_LOOKUP;
	private final double[] ALPHA_STEP_LOOKUP;
	private final Color[] COLORS;
	final double COLOR_FACTOR = 1.0 / 255.0;

	public ConicalGradientPaint(Point2D center, float[] fractions,
			Color[] colors) throws IllegalArgumentException {
		this(center, 0.0, fractions, colors);
	}

	public ConicalGradientPaint(Point2D center, double offset,
			float[] fractions, Color[] colors) throws IllegalArgumentException {
		super();

		if (fractions.length != colors.length) {
			throw new IllegalArgumentException(
					"Fractions and colors must equal in size");
		}

		// Adjust fractions and colors array in the case where startvalue !=
		// 0.0f and/or endvalue != 1.0f
		java.util.List<Float> fractionList = new java.util.ArrayList<Float>(
				fractions.length);
		for (float fraction : fractions) {
			fractionList.add(fraction);
		}
		java.util.List<Color> colorList = new java.util.ArrayList<Color>(
				colors.length);
		for (Color color : colors) {
			colorList.add(color);
		}

		if (fractionList.get(0) != 0.0f) {
			fractionList.add(0, 0.0f);
			Color tmpColor = colorList.get(0);
			colorList.add(0, tmpColor);
		}

		if (fractionList.get(fractionList.size() - 1) != 1.0f) {
			fractionList.add(1.0f);
			colorList.add(colors[0]);
		}

		Float[] tmpFractions = fractionList.toArray(new Float[] {});
		Color[] tmpColors = colorList.toArray(new Color[] {});

		this.CENTER = center;
		this.COLORS = tmpColors;

		// Prepare lookup table for the angles of each fraction
		this.FRACTION_ANGLE = new double[tmpFractions.length];
		for (int i = 0; i < tmpFractions.length; i++) {
			FRACTION_ANGLE[i] = tmpFractions[i] * 360;
		}

		// Prepare lookup tables for the color stepsize of each color
		RED_STEP_LOOKUP = new double[COLORS.length];
		GREEN_STEP_LOOKUP = new double[COLORS.length];
		BLUE_STEP_LOOKUP = new double[COLORS.length];
		ALPHA_STEP_LOOKUP = new double[COLORS.length];

		for (int i = 0; i < (COLORS.length - 1); i++) {
			RED_STEP_LOOKUP[i] = ((COLORS[i + 1].getRed() - COLORS[i].getRed()) * COLOR_FACTOR)
					/ (FRACTION_ANGLE[i + 1] - FRACTION_ANGLE[i]);
			GREEN_STEP_LOOKUP[i] = ((COLORS[i + 1].getGreen() - COLORS[i]
					.getGreen()) * COLOR_FACTOR)
					/ (FRACTION_ANGLE[i + 1] - FRACTION_ANGLE[i]);
			BLUE_STEP_LOOKUP[i] = ((COLORS[i + 1].getBlue() - COLORS[i]
					.getBlue()) * COLOR_FACTOR)
					/ (FRACTION_ANGLE[i + 1] - FRACTION_ANGLE[i]);
			ALPHA_STEP_LOOKUP[i] = ((COLORS[i + 1].getAlpha() - COLORS[i]
					.getAlpha()) * COLOR_FACTOR)
					/ (FRACTION_ANGLE[i + 1] - FRACTION_ANGLE[i]);
		}
	}

	@Override
	public PaintContext createContext(ColorModel colorModel,
			Rectangle deviceBounds, Rectangle2D userBounds,
			AffineTransform transform, RenderingHints hints) {
		Point2D transformedCenter = transform.transform(CENTER, null);
		return new ConicalGradientPaintContext(transformedCenter);
	}

	@Override
	public int getTransparency() {
		return Transparency.TRANSLUCENT;
	}

	private class ConicalGradientPaintContext implements PaintContext {
		final private Point2D CENTER;

		public ConicalGradientPaintContext(Point2D center) {
			super();
			this.CENTER = new Point2D.Double(center.getX(), center.getY());
		}

		@Override
		public void dispose() {
		}

		@Override
		public ColorModel getColorModel() {
			return ColorModel.getRGBdefault();
		}

		@Override
		public Raster getRaster(int x, int y, int tileWidth, int tileHeight) {
			final double ROTATION_CENTER_X = -x + CENTER.getX();
			final double ROTATION_CENTER_Y = -y + CENTER.getY();

			final int MAX = FRACTION_ANGLE.length;

			// Create raster for given colormodel
			WritableRaster raster = getColorModel()
					.createCompatibleWritableRaster(tileWidth, tileHeight);

			// Create data array with place for red, green, blue and alpha
			// values
			int[] data = new int[(tileWidth * tileHeight * 4)];

			double dx;
			double dy;
			double distance;
			double angle;
			double currentRed = 0;
			double currentGreen = 0;
			double currentBlue = 0;
			double currentAlpha = 0;

			for (int py = 0; py < tileHeight; py++) {
				for (int px = 0; px < tileWidth; px++) {

					// Calculate the distance between the current position and
					// the rotation angle
					dx = px - ROTATION_CENTER_X;
					dy = py - ROTATION_CENTER_Y;
					distance = Math.sqrt(dx * dx + dy * dy);

					// Avoid division by zero
					if (distance == 0) {
						distance = 1;
					}

					// 0° degree on top
					angle = Math.abs(Math.toDegrees(Math.acos(dx / distance)));

					if (dx >= 0 && dy <= 0) {
						angle = 90.0 - angle;
					} else if (dx >= 0 && dy >= 0) {
						angle += 90.0;
					} else if (dx <= 0 && dy >= 0) {
						angle += 90.0;
					} else if (dx <= 0 && dy <= 0) {
						angle = 450.0 - angle;
					}

					// Check for each angle in fractionAngles array
					for (int i = 0; i < (MAX - 1); i++) {
						if ((angle >= FRACTION_ANGLE[i])) {
							currentRed = COLORS[i].getRed() * COLOR_FACTOR
									+ (angle - FRACTION_ANGLE[i])
									* RED_STEP_LOOKUP[i];
							currentGreen = COLORS[i].getGreen() * COLOR_FACTOR
									+ (angle - FRACTION_ANGLE[i])
									* GREEN_STEP_LOOKUP[i];
							currentBlue = COLORS[i].getBlue() * COLOR_FACTOR
									+ (angle - FRACTION_ANGLE[i])
									* BLUE_STEP_LOOKUP[i];
							currentAlpha = COLORS[i].getAlpha() * COLOR_FACTOR
									+ (angle - FRACTION_ANGLE[i])
									* ALPHA_STEP_LOOKUP[i];

							continue;
						}
					}

					// Fill data array with calculated color values
					int base = (py * tileWidth + px) * 4;
					data[base + 0] = (int) (currentRed * 255);
					data[base + 1] = (int) (currentGreen * 255);
					data[base + 2] = (int) (currentBlue * 255);
					data[base + 3] = (int) (currentAlpha * 255);
				}
			}

			// Fill the raster with the data
			raster.setPixels(0, 0, tileWidth, tileHeight, data);

			return raster;
		}
	}

}
