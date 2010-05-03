package clock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.text.AttributedString;

/**
 * A piece of sample code to show how to develop a nice-looking analog-type
 * clock by using this API.<br>
 * This class creates a square-shaped face and curve-like hands with abundant
 * colors.<br>
 */
public class MyParts extends BasicParts {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Radius of the clock face.
	 */
	protected float radius;

	/**
	 * 12 hour ticks.
	 */
	protected Shape tick;

	/**
	 * Other 48 minute ticks not at time punctualities.
	 */
	private GeneralPath smallTick;

	/**
	 * X coordinate of left top corner.
	 */
	private static float xNW = 0;

	/**
	 * Y coordinate of left top corner.
	 */
	private static float yNW = 0;

	/**
	 * Width of the square.
	 */
	private static float width = 170;

	/**
	 * Additional margin size in proportion of radius by percentage.
	 */
	private static float marginOfRadius = 0.1f;

	/**
	 * Default constructor.
	 * 
	 * @throws Exception
	 * 
	 */
	public MyParts() throws Exception {
		super(
				new Rectangle2D.Float(xNW, yNW, width, width),// Clock face has
				// been
				// initialized.
				new GeneralPath(), new GeneralPath(), new GeneralPath(),
				BasicParts.ROMAN, new BasicColor(new Color(200, 255, 200),
						Color.BLACK, Color.RED.darker(), Color.BLUE.darker(),
						new Color(166, 107, 16), new Color(127, 0, 85),
						Color.BLACK));
		initialize();
	}

	/**
	 * To initialize some parameters and every parts shape. Here we set hour
	 * hand and minute hand as shape of B¨¦zier, and second hand as stripe
	 * rectangle.
	 */
	protected void initialize() {
		x = xNW + width / 2;
		y = yNW + width / 2;
		radius = width / 2 - 5;

		float fstWidthOfRadius = 4;// Distance between two 1st control points of
		// B¨¦zier curve is 400% width of the hand
		float fstLengthOfRadius = 0.333f;// Distance between the 1st control
		// points of B¨¦zier curve along the
		// hand axis is 33.3% length of the
		// hand
		float sndWidthOfRadius = 2;// Distance between two 2nd control points of
		// B¨¦zier curve is 200% width of the hand
		float sndLengthOfRadius = 0.5f;// Distance between the 2nd control
		// points of B¨¦zier curve along the
		// hand axis is 50% length of the hand

		// Initialize hour hand
		// Width of the hour hand is 8% of the dail radius
		float hWidthOfRadius = 0.08f;
		// Length of the hour hand is 70% of the dail radius
		float hLengthOfRadius = 0.7f;
		// Margin length of the hour hand is 10% of the dail radius
		float hMarginOfRadius = 0.1f;
		hourHand = new GeneralPath();
		createHand(hourHand, x, y, radius, hWidthOfRadius, hLengthOfRadius,
				hMarginOfRadius, fstWidthOfRadius, fstLengthOfRadius,
				sndWidthOfRadius, sndLengthOfRadius);

		// Initialize minute hand
		// Width of the minute hand is 4% of the dail radius
		float mWidthOfRadius = 0.06f;
		// Length of the minute hand is 85% of the dail radius
		float mLengthOfRadius = 0.9f;
		// Margin length of the minute hand is 15% of the dail radius
		float mMarginOfRadius = 0.15f;
		minuteHand = new GeneralPath();
		createHand(minuteHand, x, y, radius, mWidthOfRadius, mLengthOfRadius,
				mMarginOfRadius, fstWidthOfRadius, fstLengthOfRadius,
				sndWidthOfRadius, sndLengthOfRadius);

		// Initialize second hand
		// Width of the second hand is 2% of the dail radius
		float sWidthOfRadius = 0.02f;
		// Length of the second hand is 105% of the dail radius
		float sLengthOfRadius = 1.05f;
		// Margin length of the second hand is 25% of the dail radius
		float sMarginOfRadius = 0.25f;
		secondHand = new GeneralPath();
		createHand(secondHand, x, y, radius, sWidthOfRadius, sLengthOfRadius,
				sMarginOfRadius);

		initTransform();

		// Initialize the first tick at 12:00
		float tickRadius = radius * 0.02f;// Radius of the tick is 2% of the
		// dail radius
		tick = new Ellipse2D.Float(x - tickRadius, y - radius - tickRadius,
				tickRadius * 2, tickRadius * 2);

		// Initialize the first tick
		float tWidthOfRadius = 0.01f;// Width of the decisecond hand is 4% of
		// the dail radius
		float tLengthOfRadius = 0.02f;// Length of the decisecond hand is 10% of
		// the dail radius
		smallTick = new GeneralPath();
		createHand(smallTick, x, y - radius, radius, tWidthOfRadius,
				tLengthOfRadius / 2, tLengthOfRadius / 2);

		// Initialize container size
		TextLayout layout12, layout3, layout6, layout9;
		layout12 = new TextLayout(new AttributedString(numbers[0])
				.getIterator(), Clock.frc);
		layout3 = new TextLayout(
				new AttributedString(numbers[3]).getIterator(), Clock.frc);
		layout6 = new TextLayout(
				new AttributedString(numbers[6]).getIterator(), Clock.frc);
		layout9 = new TextLayout(
				new AttributedString(numbers[9]).getIterator(), Clock.frc);
		float sizeMargin = (float) trans.getTranslateX() / radius;
		size.width = (int) (x + radius * (1 + sizeMargin)
				+ layout3.getBounds().getBounds().width + radius
				* marginOfRadius + layout9.getBounds().getBounds().width + radius
				* marginOfRadius);
		size.height = (int) (y + radius * (1 + sizeMargin)
				+ layout12.getAscent() + layout12.getDescent()
				+ layout12.getLeading() + layout6.getAscent()
				+ layout6.getDescent() + layout6.getLeading());
		
		
	}

	/**
	 * Generate hour hand and minute hand shape. The hands are designed as
	 * B¨¦zier curve.
	 * 
	 * @param hand
	 *            shape of the hand
	 * @param x
	 *            X coordinate of the rotate center
	 * @param y
	 *            Y coordinate of the rotate center
	 * @param radius
	 *            radius of the clock face
	 * @param widthPercent
	 *            width of the hand in proportion to the radius by percentage
	 * @param lengthPercent
	 *            length of the hand in proportion to the radius by percentage
	 * @param marginPercent
	 *            margin length of the hand in proportion to the radius by
	 *            percentage
	 * @param firstWidthPercent
	 *            distance between two 1st control points of B¨¦zier in
	 *            proportion to the radius by percentage
	 * @param firstLengthPercent
	 *            distance between the 1st control point of B¨¦zier and the
	 *            rotate center, in direction of the hand, in proportion to the
	 *            radius by percentage
	 * @param secondWidthPercent
	 *            distance between two 2nd control points of B¨¦zier in
	 *            proportion to the radius by percentage
	 * @param secondLengthPercent
	 *            distance between the 2nd control point of B¨¦zier and the
	 *            rotate center, in direction of the hand, in proportion to the
	 *            radius by percentage
	 * 
	 * @see java.awt.geom.GeneralPath#moveTo(float, float)
	 * @see java.awt.geom.GeneralPath#curveTo(float, float, float, float, float,
	 *      float)
	 */
	private void createHand(Shape hand, float x, float y, float radius,
			float widthPercent, float lengthPercent, float marginPercent,
			float firstWidthPercent, float firstLengthPercent,
			float secondWidthPercent, float secondLengthPercent) {
		GeneralPath h = (GeneralPath) hand;
		h.moveTo(x, y);
		h.curveTo(x - radius * (widthPercent / 2) * (firstWidthPercent / 2), y
				- radius * marginPercent * (firstLengthPercent / 2), x - radius
				* (widthPercent / 2) * (secondWidthPercent / 2), y - radius
				* marginPercent * (secondLengthPercent / 2), x, y - radius
				* lengthPercent);
		h.curveTo(x + radius * (widthPercent / 2) * (secondWidthPercent / 2), y
				- radius * marginPercent * (secondLengthPercent / 2), x
				+ radius * (widthPercent / 2) * (firstWidthPercent / 2), y
				- radius * marginPercent * (firstLengthPercent / 2), x, y);
	}

	/**
	 * @see com.jungleford.smartcalendar.clock.analog.BasicParts#initTransform()
	 */
	@Override
	protected void initTransform() {
		super.initTransform();

		// Move all parts, to leave some margin
		float distanceOfRadius = 0.3f;// Margin is 30% of the dail radius
		trans = AffineTransform.getTranslateInstance(radius * distanceOfRadius,
				radius * distanceOfRadius);

	}

	/**
	 * Paint ticks and time punctualities.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int max = 12;
		BasicColor c = (BasicColor) colors;

		// Draw 12 numbers by using specific font
		drawNumbers(g, numbers, marginOfRadius, new Font("Ravie", Font.BOLD
				+ Font.ITALIC, 8));

		// Draw 12 hour ticks, here use SimpleParts
		drawTicks(g, tick, max, x, y, trans, c.tick);
		// Draw 48 minute ticks, here use SimpleParts
		drawTicks(g, smallTick, 60, x, y, trans, c.tick);
	}

	/**
	 * An algorithm to locate time punctualities numbers on a round clock face
	 * 
	 * @param g
	 *            graphics environment
	 * @param numbers
	 *            time punctualities numbers
	 * @param marginPercent
	 *            leave some margin between numbers and clock boundary, in
	 *            proportion to the radius by percentage
	 * @param font
	 *            font of the numbers
	 */
	private void drawNumbers(Graphics g, String[] numbers, float marginPercent,
			Font font) {
		int max = 12;
		Color color = ((BasicColor) colors).numbers;

		if (numbers != null && numbers.length >= max) {
			AttributedString num;
			TextLayout layout;
			// Because the following accurate values are not zero accually,
			// but very close to zero.
			float sZero1 = (float) Math.sin((2 * Math.PI / 12) * 0);
			float sZero2 = (float) Math.sin((2 * Math.PI / 12) * 6);
			float cZero1 = (float) Math.cos((2 * Math.PI / 12) * 3);
			float cZero2 = (float) Math.cos((2 * Math.PI / 12) * 9);

			for (int p = 0; p < max; p++) {
				num = new AttributedString(numbers[p]);
				if (font != null) {
					num.addAttribute(TextAttribute.FONT, font);
					layout = new TextLayout(numbers[p], font, Clock.frc);
				} else {
					layout = new TextLayout(num.getIterator(), Clock.frc);
				}

				float width = layout.getBounds().getBounds().width;
				float sin = (float) Math.sin((2 * Math.PI / 12) * p);
				float cos = (float) Math.cos((2 * Math.PI / 12) * p);
				// Mark point
				float px = (float) (x + trans.getTranslateX() + radius
						* (1 + marginPercent) * sin);
				float py = (float) (y + trans.getTranslateY() - radius
						* (1 + marginPercent) * cos);

				// To locate the number at accurate position
				if (sin > sZero2) {// Right side
					px -= 1.5 * sin * width;
				} else if (sin == sZero1 || sin == sZero2) {// Top or bottom
					px -= width / 2;
				} else {// Left side
					px += 0.75 * Math.abs(sin) * width;
				}

				if (cos < cZero2) {// Underside
					py -= layout.getAscent();
				} else if (cos == cZero2 || cos == cZero1) {// Most left or most
					// right
					py += (layout.getAscent() - layout.getDescent()) / 2;
				} else {// Upside
					py += (layout.getAscent() + layout.getDescent())
							+ layout.getAscent();
				}

				super.drawNumber(g, num, px, py, color);
			}
		}
	}

	/**
	 * Draws ticks.
	 * 
	 * @param g
	 *            Graphics environment.
	 * @param tick
	 *            shape of the ticks.
	 * @param x
	 *            X coordinate of the rotate center.
	 * @param y
	 *            Y coordinate of the rotate center.
	 * @param color
	 *            color used.
	 * @param trans
	 *            an additional transform, usually translate.
	 * @param tickNumber
	 *            numbers of the ticks, usually 12 or 60.
	 */
	public static void drawTicks(Graphics g, Shape tick, int tickNumber,
			float x, float y, AffineTransform trans, Color color) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = AffineTransform.getRotateInstance(0, x, y);

		for (int p = 0; p < tickNumber; p++) {
			at = RotateParts.getRotateInstance(x, y, tickNumber, p);
			g2.setPaint(color);
			g2.fill(trans.createTransformedShape(at
					.createTransformedShape(tick)));
		}
	}

	/**
	 * Generates concrete hand shape. The hands are designed as strip rectangle.<br>
	 * This is a common method.
	 * 
	 * @param hand
	 *            shape of the hand.
	 * @param x
	 *            X coordinate of the rotate center.
	 * @param y
	 *            Y coordinate of the rotate center.
	 * @param radius
	 *            rotate radius.
	 * @param widthPercent
	 *            width of the hand in proportion to the radius by percentage.
	 * @param lengthPercent
	 *            length of the hand in proportion to the radius by percentage.
	 * @param marginPercent
	 *            margin length of the hand in proportion to the radius by
	 *            percentage.
	 * 
	 */
	public static void createHand(Shape hand, float x, float y, float radius,
			float widthPercent, float lengthPercent, float marginPercent) {
		GeneralPath h = (GeneralPath) hand;
		h.moveTo(x - radius * (widthPercent / 2), y + radius * marginPercent);
		h.lineTo(x + radius * (widthPercent / 2), y + radius * marginPercent);
		h.lineTo(x + radius * (widthPercent / 2), y - radius * lengthPercent);
		h.lineTo(x - radius * (widthPercent / 2), y - radius * lengthPercent);
		h.lineTo(x - radius * (widthPercent / 2), y + radius * marginPercent);
	}
}
