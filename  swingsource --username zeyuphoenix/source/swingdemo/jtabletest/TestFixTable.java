package jtabletest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;

/***
 */
public class TestFixTable extends JTableHeader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private JScrollPane scrollPane;

	private int col = 0;

	private FixedMouseListenter mouseListener;

	public TestFixTable(JTable table, JScrollPane scrollPane) {
		super(table.getTableHeader().getColumnModel());
		this.table = table;
		this.scrollPane = scrollPane;
		init();

	}

	private void init() {
		mouseListener = new FixedMouseListenter();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setTableHeader(this);
		this.addMouseListener(mouseListener);
		table.addMouseListener(mouseListener);
		scrollPane.addComponentListener(new ScrolPaneComponentListener());

		table.setFillsViewportHeight(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int division = mouseListener.getDivision();
		if (division > 0) {
			Rectangle r = getVisibleRect();
			BufferedImage image = new BufferedImage(division, r.height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g2 = image.getGraphics();
			g2.setClip(0, 0, division, r.height);
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, division, r.height);
			super.paint(g2);
			g.drawImage(image, r.x, r.y, division, r.height, null);
			g2.dispose();
		}
	}

	public int getFixCol() {
		return col;
	}

	/**
 **/
	public void setFixCol(int fixCol) {
		this.col = fixCol;
	}

	private int division;

	private class FixedColumnsDelegate extends JLabel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			Rectangle r = table.getBounds();
			if (division > 0) {
				table.invalidate();
				table.validate();
				Rectangle visibleRect = table.getVisibleRect();
				BufferedImage image = new BufferedImage(division, r.height,
						BufferedImage.TYPE_INT_ARGB);
				Graphics g2 = image.getGraphics();

				g2
						.setClip(0, visibleRect.y, division,
								table.getBounds().height);

				g2.setColor(Color.RED);
				g2.fillRect(0, 0, division, table.getBounds().height);

				table.paint(g2);
				g.drawImage(image, 0, 0, division, table.getBounds().height, 0,
						visibleRect.y, division, visibleRect.y
								+ table.getBounds().height, null);
				g2.dispose();
			}
		}
	}

	private class FixedMouseListenter extends MouseAdapter {

		private FixedColumnsDelegate fixedColumns;

		private boolean added;

		public FixedMouseListenter() {
			fixedColumns = new FixedColumnsDelegate();
		}

		public void mouseReleased(MouseEvent e) {
			doMosuseAction();
		}

		public void mouseMoved(MouseEvent e) {
			doMosuseAction();
		}

		private void doMosuseAction() {
			freeze();
		}

		public void freeze() {
			JLayeredPane pane = table.getRootPane().getLayeredPane();
			if (added) {
				pane.remove(fixedColumns);
			}
			pane.add(fixedColumns, JLayeredPane.POPUP_LAYER);
			setBoundsOnFrozenColumns();
			added = true;
			fixedColumns.setVisible(true);
		}

		public void setBoundsOnFrozenColumns() {
			if (col >= 0) {
				division = table.getCellRect(1, col, true).x
						+ table.getCellRect(1, col, true).width;
				int limit = scrollPane.getBounds().width
						- scrollPane.getVerticalScrollBar().getBounds().width
						- 2;
				division = Math.min(division, limit);
				JLayeredPane pane = table.getRootPane().getLayeredPane();
				Point p = scrollPane.getLocationOnScreen();
				SwingUtilities.convertPointFromScreen(p, pane);
				Rectangle scrollPaneBounds = scrollPane.getBounds();
				int headerHeight = table.getTableHeader().getBounds().height + 2;
				int hScrollHeight = (scrollPane.getHorizontalScrollBar()
						.isVisible()) ? scrollPane.getHorizontalScrollBar()
						.getBounds().height : 0;

				int columnMargin = table.getColumnModel().getColumnMargin();
				p.x += 2 * columnMargin;

				int scrollRowHeaderWidth = 0;
				/**
				 *fix bug:
				 **/
				if (scrollPane.getRowHeader() != null) {
					scrollRowHeaderWidth = scrollPane.getRowHeader().getWidth();
					if (scrollRowHeaderWidth <= 0)
						scrollRowHeaderWidth = 0;
				}
				p.x += scrollRowHeaderWidth;
				fixedColumns.setBounds(p.x + 1, p.y + headerHeight, division,
						scrollPaneBounds.height - headerHeight - hScrollHeight
								- 2);
			}
		}

		public int getDivision() {
			return division;
		}
	}

	private class ScrolPaneComponentListener implements ComponentListener {

		public void componentHidden(ComponentEvent e) {
			displayMessage(e.getComponent().getClass().getName()
					+ " --- Hidden");

		}

		public void componentMoved(ComponentEvent e) {
			displayMessage(e.getComponent().getClass().getName() + " --- Moved");
		}

		public void componentResized(ComponentEvent e) {
			displayMessage(e.getComponent().getClass().getName()
					+ " --- Resized");

			freeze();

		}

		public void componentShown(ComponentEvent e) {
			displayMessage(e.getComponent().getClass().getName() + " --- Shown");
		}

		private void displayMessage(String msg) {
			System.out.println(msg);
		}

		private void freeze() {
			mouseListener.freeze();
		}
	}

	public static String columnNames[] = { "Customer Name", "City",
			"Payment Amount", "Date", "Item", "Quantity", "Related", "Price",
			"Method", "Campaign", "Affiliate" };

	public static String customers[] = { "Stores", "Exxon", "Chevron",
			"General", "ConocoPhillips", "General", "Ford", "Citigroup",
			"Bank", "AT&T", "Berkshire", "J.P.", "American", "Hewlett-Packard",
			"International", "Valero", "Verizon", "McKesson", "Cardinal",
			"Goldman", "Morgan", "Home", "Procter", "CVS", "UnitedHealth",
			"Kroger", "Boeing", "AmerisourceBergen", "Costco", "Merrill",
			"Target", "State", "WellPoint", "Dell", "Johnson", "Marathon",
			"Lehman", "Wachovia", "United", "Walgreen", "Wells", "Dow",
			"MetLife", "Microsoft", "Sears", "United", "Pfizer", "Lowe's",
			"Time", "Caterpillar", "Medco", "Archer", "Fannie", "Freddie",
			"Safeway", "Sunoco", "Lockheed", "Sprint", "PepsiCo", "Intel",
			"Altria", "Supervalu", "Kraft", "Allstate", "Motorola", "Best",
			"Walt", "FedEx", "Ingram", "Sysco", "Cisco", "Johnson",
			"Honeywell", "Prudential", "American", "Northrop", "Hess", "GMAC",
			"Comcast", "Alcoa", "DuPont", "New", "Coca-Cola", "News", "Aetna",
			"TIAA-CREF", "General", "Tyson", "HCA", "Enterprise", "Macy's",
			"Delphi", "Travelers", "Liberty", "Hartford", "Abbott",
			"Washington", "Humana", "Massachusetts", "3M" };

	public static String[] cities = { "Alaska", "Arizona ", "Arkansas ",
			"California ", "Colorado ", "Connecticut ", "Delaware ",
			"District of", "Florida ", "Georgia ", "Hawaii ", "Idaho ",
			"Illinois ", "Indiana ", "Iowa ", "Kansas ", "Kentucky ",
			"Louisiana ", "Maine ", "Maryland ", "Massachusetts ", "Michigan ",
			"Minnesota ", "Mississippi ", "Missouri ", "Montana ", "Nebraska ",
			"Nevada ", "New Hampshire", "New Jersey", "New Mexico", "New York",
			"North Carolina", "North Dakota", "Ohio ", "Oklahoma ", "Oregon ",
			"Pennsylvania ", "Rhode Island", "South Carolina", "South Dakota",
			"Tennessee ", "Texas ", "Utah ", "Vermont ", "Virginia ",
			"Washington ", "West Virginia", "Wisconsin ", "Wyoming " };

	public static void main(String arg[]) throws Exception {
		int rows = customers.length;
		int columns = columnNames.length;
		Object data[][] = new Object[rows][columns];
		for (int i = 0; i < rows; ++i) {
			data[i][0] = customers[i];
		}
		for (int i = 0; i < rows; ++i) {
			data[i][1] = cities[i % cities.length];
		}
		for (int i = 0; i < rows; ++i) {
			data[i][2] = new BigDecimal(Math.random() * 10000);
			data[i][2] = ((BigDecimal) data[i][2]).setScale(2,
					BigDecimal.ROUND_CEILING);
		}
		for (int i = 3; i < columns; ++i) {
			for (int x = 0; x < rows; ++x) {
				data[x][i] = "element:" + x + "," + i;
			}
		}
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		TestFixTable tableHeader = new TestFixTable(table, scrollPane);
		tableHeader.setFixCol(2);
		JFrame frame = new JFrame("Test");
		frame.add("Center", scrollPane);
		frame.setSize(600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
