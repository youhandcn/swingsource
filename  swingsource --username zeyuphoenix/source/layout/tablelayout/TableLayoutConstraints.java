package tablelayout;

import java.util.NoSuchElementException;

/**
 * TableLayoutConstraints binds components to their constraints.
 */
public class TableLayoutConstraints implements TableLayoutConstants {

	/** Cell in which the upper left corner of the component lays */
	public int col1, row1;

	/** Cell in which the lower right corner of the component lays */
	public int col2, row2;

	/** Horizontal justification if component occupies just one cell */
	public int hAlign;

	/** Vertical justification if component occupies just one cell */
	public int vAlign;

	/**
	 * Constructs an TableLayoutConstraints with the default settings. This
	 * constructor is equivalent to TableLayoutConstraints(0, 0, 0, 0, FULL,
	 * FULL).
	 */
	public TableLayoutConstraints() {
		col1 = row1 = col1 = col2 = 0;
		hAlign = vAlign = FULL;
	}

	/**
	 * Constructs an TableLayoutConstraints a set of constraints.
	 * 
	 * @param col
	 *            column where the component is placed
	 * @param row
	 *            row where the component is placed
	 */
	public TableLayoutConstraints(int col, int row) {
		this(col, row, col, row, FULL, FULL);
	}

	/**
	 * Constructs an TableLayoutConstraints a set of constraints.
	 * 
	 * @param col1
	 *            column where upper-left corner of the component is placed
	 * @param row1
	 *            row where upper-left corner of the component is placed
	 * @param col2
	 *            column where lower-right corner of the component is placed
	 * @param row2
	 *            row where lower-right corner of the component is placed
	 */
	public TableLayoutConstraints(int col1, int row1, int col2, int row2) {
		this(col1, row1, col2, row2, FULL, FULL);
	}

	/**
	 * Constructs an TableLayoutConstraints a set of constraints.
	 * 
	 * @param col1
	 *            column where upper-left corner of the component is placed
	 * @param row1
	 *            row where upper-left corner of the component is placed
	 * @param col2
	 *            column where lower-right corner of the component is placed
	 * @param row2
	 *            row where lower-right corner of the component is placed
	 * @param hAlign
	 *            horizontal justification of a component in a single cell
	 * @param vAlign
	 *            vertical justification of a component in a single cell
	 */
	public TableLayoutConstraints(int col1, int row1, int col2, int row2,
			int hAlign, int vAlign) {
		this.col1 = col1;
		this.row1 = row1;
		this.col2 = col2;
		this.row2 = row2;

		if ((hAlign == LEFT) || (hAlign == RIGHT) || (hAlign == CENTER)
				|| (hAlign == FULL) || (hAlign == LEADING)
				|| (hAlign == TRAILING)) {
			this.hAlign = hAlign;
		} else
			this.hAlign = FULL;

		if ((vAlign == TOP) || (vAlign == BOTTOM) || (vAlign == CENTER)) {
			this.vAlign = vAlign;
		} else
			this.vAlign = FULL;
	}

	/**
	 * Constructs an TableLayoutConstraints from a string.
	 * 
	 * @param constraints
	 *            indicates TableLayoutConstraints's position and justification
	 *            as a string in the form "column, row" or "column, row,
	 *            horizontal justification, vertical justification" or "column
	 *            1, row 1, column 2, row 2" or "column 1, row 1, column 2, row
	 *            2, horizontal justification, vertical justification". It is
	 *            also acceptable to delimit the parameters with spaces instead
	 *            of commas.
	 */
	public TableLayoutConstraints(String constraints) {
		// Use default values for any parameter not specified or specified
		// incorrectly. The default parameters place the component in a single
		// cell at column 0, row 0. The component is fully justified.
		this.col1 = 0;
		this.row1 = 0;
		this.col2 = 0;
		this.row2 = 0;
		this.hAlign = FULL;
		this.vAlign = FULL;

		// Check constraints
		if (constraints == null || constraints.equals(""))
			throw new RuntimeException("constraints is error! ");
		// Parse constraints using spaces or commas
		String[] constraintArray = constraints.split(",");
		int numConstraint = constraintArray.length;

		try {
			// Check constraints
			if ((numConstraint != 2) && (numConstraint != 4)
					&& (numConstraint != 6))
				throw new RuntimeException();

			if (numConstraint == 2 && constraintArray[0].trim().matches("\\d+")
					&& constraintArray[1].trim().matches("\\d+")) {

				// Get the first column (assume component is in only one column)
				col1 = new Integer(constraintArray[0].trim()).intValue();
				col2 = col1;

				// Get the first row (assume component is in only one row)
				row1 = new Integer(constraintArray[1].trim()).intValue();
				row2 = row1;
			} else if (numConstraint == 4
					&& constraintArray[0].trim().matches("\\d+")
					&& constraintArray[1].trim().matches("\\d+")
					&& constraintArray[2].trim().matches("\\d+")
					&& constraintArray[3].trim().matches("\\d+")) {
				// Get the column
				col1 = new Integer(constraintArray[0].trim()).intValue();
				col2 = new Integer(constraintArray[2].trim()).intValue();

				// Get the row
				row1 = new Integer(constraintArray[1].trim()).intValue();
				row2 = new Integer(constraintArray[3].trim()).intValue();
			} else if (constraintArray[0].trim().matches("\\d+")
					&& constraintArray[1].trim().matches("\\d+")
					&& constraintArray[2].trim().matches("\\d+")
					&& constraintArray[3].trim().matches("\\d+")) {
				// Get the column
				col1 = new Integer(constraintArray[0].trim()).intValue();
				col2 = new Integer(constraintArray[2].trim()).intValue();

				// Get the row
				row1 = new Integer(constraintArray[1].trim()).intValue();
				row2 = new Integer(constraintArray[3].trim()).intValue();
				parse(constraintArray[4].trim(), constraintArray[5].trim());
			} else {
				throw new RuntimeException();
			}
		} catch (NoSuchElementException error) {
		} catch (RuntimeException error) {
			throw new IllegalArgumentException(
					"Expected constraints in one of the following formats:\n"
							+ "  col1, row1\n  col1, row1, col2, row2\n"
							+ "  col1, row1, hAlign, vAlign\n"
							+ "  col1, row1, col2, row2, hAlign, vAlign\n"
							+ "Constraints provided '" + constraints + "'");
		}

		// Make sure row2 >= row1
		if (row2 < row1)
			row2 = row1;

		// Make sure col2 >= col1
		if (col2 < col1)
			col2 = col1;
	}

	private void parse(String tokenA, String tokenB) {

		// Check if token means horizontally justification the component
		if ((tokenA.equalsIgnoreCase("L")) || (tokenA.equalsIgnoreCase("LEFT")))
			hAlign = LEFT;
		else if ((tokenA.equalsIgnoreCase("C"))
				|| (tokenA.equalsIgnoreCase("CENTER")))
			hAlign = CENTER;
		else if ((tokenA.equalsIgnoreCase("F"))
				|| (tokenA.equalsIgnoreCase("FULL")))
			hAlign = FULL;
		else if ((tokenA.equalsIgnoreCase("R"))
				|| (tokenA.equalsIgnoreCase("RIGHT")))
			hAlign = RIGHT;
		else if ((tokenA.equalsIgnoreCase("LD"))
				|| (tokenA.equalsIgnoreCase("LEADING")))
			hAlign = LEADING;
		else if ((tokenA.equalsIgnoreCase("TL"))
				|| (tokenA.equalsIgnoreCase("TRAILING")))
			hAlign = TRAILING;
		else
			throw new RuntimeException();

		// Check if token means horizontally justification the component
		if ((tokenB.equalsIgnoreCase("T")) || (tokenB.equalsIgnoreCase("TOP")))
			vAlign = TOP;
		else if ((tokenB.equalsIgnoreCase("C"))
				|| (tokenB.equalsIgnoreCase("CENTER")))
			vAlign = CENTER;
		else if ((tokenB.equalsIgnoreCase("F"))
				|| (tokenB.equalsIgnoreCase("FULL")))
			vAlign = FULL;
		else if ((tokenB.equalsIgnoreCase("B"))
				|| (tokenB.equalsIgnoreCase("BOTTOM")))
			vAlign = BOTTOM;
		else
			throw new RuntimeException();
	}

	/**
	 * Gets a string representation of this TableLayoutConstraints.
	 * 
	 * @return a string in the form "row 1, column 1, row 2, column 2,
	 *         horizontal justification, vertical justification"
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(this.col1);
		buffer.append(", ");
		buffer.append(this.row1);
		buffer.append(", ");

		buffer.append(this.col2);
		buffer.append(", ");
		buffer.append(this.row2);
		buffer.append(", ");

		final String h[] = { "left", "center", "full", "right", "leading",
				"trailing" };
		final String v[] = { "top", "center", "full", "bottom" };

		buffer.append(h[this.hAlign]);
		buffer.append(", ");
		buffer.append(v[this.vAlign]);

		return buffer.toString();
	}

}
