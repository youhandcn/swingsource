package jsplitpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import sun.swing.DefaultLookup;

public class SearchSplitPaneUI extends BasicSplitPaneUI {
	private static final Color BG_COLOR = Color.gray;
	private static final Color BUTTON_COLOR = Color.BLACK;

	public SearchSplitPaneUI() {
		super();
	}

	public static ComponentUI createUI(JComponent c) {

		return new SearchSplitPaneUI();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		g.setColor(Color.green);
		int width = c.getWidth();
		int height = c.getHeight();
		g.drawRect(3, 3, width - 6, height - 6);
	}

	/**
	 * Creates the default divider.
	 */
	@Override
	public BasicSplitPaneDivider createDefaultDivider() {
		return new MyBasicSplitPaneDivider(this);
	}

	// Indicates wether the one of splitpane sides is expanded
	private boolean keepHidden = false;

	/**
	 * Set the value to indicate if one of the splitpane sides is expanded.
	 */
	public void setMyKeepHidden(boolean keepHidden) {
		this.keepHidden = keepHidden;
	}

	/**
	 * The value returned indicates if one of the splitpane sides is expanded.
	 * 
	 * @return true if one of the splitpane sides is expanded, false otherwise.
	 */
	public boolean getKeepHidden() {
		return keepHidden;
	}

	private class MyBasicSplitPaneDivider extends BasicSplitPaneDivider {

		/**
         * 
         */
		private static final long serialVersionUID = 1L;
		boolean centerOneTouchButtons;

		private SearchSplitPaneUI splitPaneUI = null;

		public MyBasicSplitPaneDivider(SearchSplitPaneUI ui) {
			super(ui);
			centerOneTouchButtons = true;

			splitPaneUI = ui;

			setLayout(new DividerLayout());
			setBasicSplitPaneUI(ui);
			orientation = splitPane.getOrientation();
			setCursor((orientation == JSplitPane.HORIZONTAL_SPLIT) ? Cursor
					.getPredefinedCursor(Cursor.E_RESIZE_CURSOR) : Cursor
					.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			setBackground(UIManager.getColor("SplitPane.background"));

		}

		/**
		 * Creates and return an instance of JButton that can be used to
		 * collapse the right component in the split pane.
		 */
		@Override
		protected JButton createRightOneTouchButton() {
			JButton b = new JButton() {

				/**
                 * 
                 */
				private static final long serialVersionUID = 1L;

				public void setBorder(Border border) {
				}

				@Override
				public void paint(Graphics g) {
					if (splitPane != null) {
						int[] xs = new int[3];
						int[] ys = new int[3];
						int blockSize;

						// ... then draw the arrow.
						if (orientation == JSplitPane.VERTICAL_SPLIT) {
							blockSize = Math.min(getHeight(), ONE_TOUCH_SIZE);
							xs[0] = blockSize;
							xs[1] = blockSize << 1;
							xs[2] = 0;
							ys[0] = blockSize;
							ys[1] = ys[2] = 0;
						} else {
							blockSize = Math.min(getWidth(), ONE_TOUCH_SIZE);
							xs[0] = xs[2] = 0;
							xs[1] = blockSize;
							ys[0] = 0;
							ys[1] = blockSize;
							ys[2] = blockSize << 1;
						}
						g.setColor(BUTTON_COLOR);
						g.fillPolygon(xs, ys, 3);
					}
				}

				// Don't want the button to participate in focus traversable.

				public boolean isFocusTraversable() {
					return false;
				}
			};
			b.setMinimumSize(new Dimension(ONE_TOUCH_SIZE, ONE_TOUCH_SIZE));
			b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			b.setFocusPainted(false);
			b.setBorderPainted(false);
			b.setRequestFocusEnabled(false);
			return b;
		}

		@Override
		protected void oneTouchExpandableChanged() {

			if (!DefaultLookup.getBoolean(splitPane, splitPaneUI,
					"SplitPane.supportsOneTouchButtons", true)) {
				// Look and feel doesn't want to support one touch buttons,
				// bail.
				return;
			}
			if (splitPane.isOneTouchExpandable() && leftButton == null
					&& rightButton == null) {
				/*
				 * Create the left button and add an action listener to
				 * expand/collapse it.
				 */
				leftButton = createLeftOneTouchButton();
				if (leftButton != null)
					leftButton.addActionListener(new OneTouchActionHandler(
							true, this));

				/*
				 * Create the right button and add an action listener to
				 * expand/collapse it.
				 */
				rightButton = createRightOneTouchButton();
				if (rightButton != null)
					rightButton.addActionListener(new OneTouchActionHandler(
							false, this));

				if (leftButton != null && rightButton != null) {
					add(leftButton);
					// add(rightButton);
				}
			}
			revalidate();
		}

		private void revalidate() {
			invalidate();
			if (splitPane != null) {
				splitPane.revalidate();
			}
		}

		/**
		 * Creates and return an instance of JButton that can be used to
		 * collapse the left component in the split pane.
		 */
		protected JButton createLeftOneTouchButton() {
			JButton b = new JButton() {

				/**
                 * 
                 */
				private static final long serialVersionUID = 1L;

				public void setBorder(Border b) {
				}

				public void paint(Graphics g) {
					if (splitPane != null) {
						int[] xs = new int[3];
						int[] ys = new int[3];
						int blockSize;

						// ... then draw the arrow.
						g.setColor(BUTTON_COLOR);
						if (orientation == JSplitPane.VERTICAL_SPLIT) {
							blockSize = Math.min(getHeight(), ONE_TOUCH_SIZE);
							xs[0] = blockSize;
							xs[1] = 0;
							xs[2] = blockSize << 1;
							ys[0] = 0;
							ys[1] = ys[2] = blockSize;
							g.drawPolygon(xs, ys, 3); // Little trick to make
							// the
							// arrows of equal size
						} else {
							blockSize = Math.min(getWidth(), ONE_TOUCH_SIZE);
							xs[0] = xs[2] = blockSize;
							xs[1] = 0;
							ys[0] = 0;
							ys[1] = blockSize;
							ys[2] = blockSize << 1;
						}
						g.fillPolygon(xs, ys, 3);
					}
				}

				// Don't want the button to participate in focus traversable.

				public boolean isFocusTraversable() {
					return false;
				}
			};
			b.setMinimumSize(new Dimension(ONE_TOUCH_SIZE, ONE_TOUCH_SIZE));
			b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			b.setFocusPainted(false);
			b.setBorderPainted(false);
			b.setRequestFocusEnabled(false);
			return b;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Dimension size = getSize();
			g.setColor(BG_COLOR);
			g.fillRect(1, 1, size.width - 1, size.height - 1);
			g.setColor(Color.GRAY);
			g.drawRect(0, 0, size.width, size.height);
			if (leftButton != null) {
				leftButton.repaint();
			}
			if (rightButton != null) {
				rightButton.repaint();
			}
		}

		/**
		 * Used to layout a <code>BasicSplitPaneDivider</code>. Layout for the
		 * divider involves appropriately moving the left/right buttons around.
		 * <p>
		 */
		protected class DividerLayout implements LayoutManager {

			public void layoutContainer(Container c) {
				if (leftButton != null && rightButton != null) {
					if (splitPane.isOneTouchExpandable()) {
						Insets insets = getInsets();

						if (orientation == JSplitPane.VERTICAL_SPLIT) {
							int extraX = (insets != null) ? insets.left : 0;
							int blockSize = getHeight();

							if (insets != null) {
								blockSize -= (insets.top + insets.bottom);
								blockSize = Math.max(blockSize, 0);
							}
							blockSize = Math.min(blockSize, ONE_TOUCH_SIZE);

							int y = (c.getSize().height - blockSize) / 2;

							if (!centerOneTouchButtons) {
								y = (insets != null) ? insets.top : 0;
								extraX = 0;
							}
							int width = (int) MyBasicSplitPaneDivider.this
									.getSize().getWidth();
							leftButton.setBounds(extraX - ONE_TOUCH_OFFSET
									+ width / 2, y, blockSize * 2, blockSize);
							rightButton.setBounds(extraX - ONE_TOUCH_OFFSET
									+ ONE_TOUCH_OFFSET * 2 + width / 2, y,
									blockSize * 2, blockSize);
						} else {
							int extraY = (insets != null) ? insets.top : 0;
							int blockSize = getWidth();
							if (insets != null) {
								blockSize -= (insets.left + insets.right);
								blockSize = Math.max(blockSize, 0);
							}
							blockSize = Math.min(blockSize, ONE_TOUCH_SIZE);

							int x = (c.getSize().width - blockSize) / 2;

							if (!centerOneTouchButtons) {
								x = (insets != null) ? insets.left : 0;
								extraY = 0;
							}
							int height = (int) MyBasicSplitPaneDivider.this
									.getSize().getHeight();
							leftButton.setBounds(x, extraY - ONE_TOUCH_OFFSET
									+ height / 2, blockSize, blockSize * 2);
							rightButton.setBounds(x, extraY - ONE_TOUCH_OFFSET
									+ ONE_TOUCH_OFFSET * 2 + height / 2,
									blockSize, blockSize * 2);
						}
					} else {
						leftButton.setBounds(-5, -5, 1, 1);
						rightButton.setBounds(-5, -5, 1, 1);
					}
				}
			}

			public Dimension minimumLayoutSize(Container c) {
				// NOTE: This isn't really used, refer to
				// BasicSplitPaneDivider.getPreferredSize for the reason.
				// I leave it in hopes of having this used at some point.
				if (splitPane == null) {
					return new Dimension(0, 0);
				}
				Dimension buttonMinSize = null;

				if (splitPane.isOneTouchExpandable() && leftButton != null) {
					buttonMinSize = leftButton.getMinimumSize();
				}

				Insets insets = getInsets();
				int width = getDividerSize();
				int height = width;

				if (orientation == JSplitPane.VERTICAL_SPLIT) {
					if (buttonMinSize != null) {
						int size = buttonMinSize.height;
						if (insets != null) {
							size += insets.top + insets.bottom;
						}
						height = Math.max(height, size);
					}
					width = 1;
				} else {
					if (buttonMinSize != null) {
						int size = buttonMinSize.width;
						if (insets != null) {
							size += insets.left + insets.right;
						}
						width = Math.max(width, size);
					}
					height = 1;
				}
				return new Dimension(width, height);
			}

			public Dimension preferredLayoutSize(Container c) {
				return minimumLayoutSize(c);
			}

			public void removeLayoutComponent(Component c) {
			}

			public void addLayoutComponent(String string, Component c) {
			}
		} // End of class BasicSplitPaneDivider.DividerLayout

		/**
		 * Listeners installed on the one touch expandable buttons.
		 */
		private class OneTouchActionHandler implements ActionListener {
			/**
			 * True indicates the resize should go the minimum (top or left) vs
			 * false which indicates the resize should go to the maximum.
			 */
			private boolean toMinimum;

			private BasicSplitPaneDivider splitPanelDivider;

			OneTouchActionHandler(boolean toMinimum,
					BasicSplitPaneDivider splitPanelDivider) {
				this.toMinimum = toMinimum;
				this.splitPanelDivider = splitPanelDivider;
			}

			public void actionPerformed(ActionEvent e) {
				Insets insets = splitPane.getInsets();
				int lastLoc = splitPane.getLastDividerLocation();
				int currentLoc = splitPaneUI.getDividerLocation(splitPane);
				int newLoc;

				// We use the location from the UI directly, as the location the
				// JSplitPane itself maintains is not necessarly correct.
				if (toMinimum) {
					if (orientation == JSplitPane.VERTICAL_SPLIT) {
						if (currentLoc >= (splitPane.getHeight()
								- insets.bottom - getHeight())) {
							int maxLoc = splitPane.getMaximumDividerLocation();
							newLoc = Math.min(lastLoc, maxLoc);
							splitPaneUI.setMyKeepHidden(false);
							splitPanelDivider.remove(leftButton);
							splitPanelDivider.add(rightButton);
						} else {
							newLoc = insets.top;
							splitPaneUI.setMyKeepHidden(true);
							splitPanelDivider.remove(leftButton);
							splitPanelDivider.add(rightButton);
						}
					} else {
						if (currentLoc >= (splitPane.getWidth() - insets.right - getWidth())) {
							int maxLoc = splitPane.getMaximumDividerLocation();
							newLoc = Math.min(lastLoc, maxLoc);
							splitPaneUI.setMyKeepHidden(false);
							splitPanelDivider.remove(leftButton);
							splitPanelDivider.add(rightButton);
						} else {
							newLoc = insets.left;
							splitPaneUI.setMyKeepHidden(true);
							splitPanelDivider.remove(leftButton);
							splitPanelDivider.add(rightButton);
						}
					}
				} else {
					if (orientation == JSplitPane.VERTICAL_SPLIT) {
						if (currentLoc == insets.top) {
							int maxLoc = splitPane.getMaximumDividerLocation();
							newLoc = Math.min(lastLoc, maxLoc);
							splitPaneUI.setMyKeepHidden(false);
							splitPanelDivider.remove(rightButton);
							splitPanelDivider.add(leftButton);
						} else {
							newLoc = splitPane.getHeight() - getHeight()
									- insets.top;
							splitPaneUI.setMyKeepHidden(true);
							splitPanelDivider.remove(rightButton);
							splitPanelDivider.add(leftButton);
						}
					} else {
						if (currentLoc == insets.left) {
							int maxLoc = splitPane.getMaximumDividerLocation();
							newLoc = Math.min(lastLoc, maxLoc);
							splitPaneUI.setMyKeepHidden(false);
							splitPanelDivider.remove(rightButton);
							splitPanelDivider.add(leftButton);
						} else {
							newLoc = splitPane.getWidth() - getWidth()
									- insets.left;
							splitPaneUI.setMyKeepHidden(true);
							splitPanelDivider.remove(rightButton);
							splitPanelDivider.add(leftButton);
						}
					}
				}
				if (currentLoc != newLoc) {
					splitPane.setDividerLocation(newLoc);
					// We do this in case the dividers notion of the location
					// differs from the real location.
					splitPane.setLastDividerLocation(currentLoc);
				}
			}
		} // End of class BasicSplitPaneDivider.LeftActionListener
	}
}