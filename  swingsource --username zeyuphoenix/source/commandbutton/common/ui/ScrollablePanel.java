package common.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import common.JCommandButton;

/**
 * ScrollablePanel allows to have scrolling buttons on each side.
 * 
 * @author jb Creation date: Nov 13, 2003
 */
public class ScrollablePanel<T extends JComponent> extends JPanel {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JCommandButton leftScroller;

	private JCommandButton rightScroller;

	private JScrollPane scrollPane;

	private T view = null;

	private int widthToScrollTo = 0;

	protected EventListenerList listenerList = new EventListenerList();

	protected boolean scrollOnRollover;

	public enum ScrollTo {
		FIRST, LAST
	}

	public static interface ScrollableSupport {
		public JCommandButton createLeftScroller();

		public JCommandButton createRightScroller();
	}

	public ScrollablePanel(T c, ScrollableSupport scrollableSupport) {
		super();
		view = c;
		this.scrollPane = new JScrollPane();
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		this.scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scrollPane.setAutoscrolls(false);
		this.scrollPane.setViewportView(c);
		add(this.scrollPane);

		this.leftScroller = scrollableSupport.createLeftScroller();
		this.configureLeftScrollerButtonAction();

		this.rightScroller = scrollableSupport.createRightScroller();
		this.configureRightScrollerButtonAction();

		this.setLayout(new ScrollablePanelLayout());
	}

	public void setScrollOnRollover(boolean toScrollOnRollover) {
		this.leftScroller.setFireActionOnRollover(toScrollOnRollover);
		this.rightScroller.setFireActionOnRollover(toScrollOnRollover);
	}

	public void increaseWidthBy(int increaseBy, ScrollTo scrollTo) {
		validateScrolling(widthToScrollTo + increaseBy, scrollTo);
	}

	public void validateScrolling(int width, ScrollTo scrollTo) {
		widthToScrollTo = width;
		int visibleWidth = getWidth();// - 4;
		 //System.out.println("Visible : " + visibleWidth + ", required " +
		 //width);
		if (visibleWidth > 0 && visibleWidth < widthToScrollTo) {
			addScrollers();
			if (scrollTo != null) {
				int x = 0;
				if (scrollTo == ScrollTo.LAST) {
					// if (getComponentCount() <= 1) {
					x = widthToScrollTo + this.leftScroller.getWidth()
							+ this.rightScroller.getWidth();// - 4;
					// } else {
					// x = widthToScrollTo;// - 4;
					// }
				}
				scrollTo(x);
			} else {
				this.rightScroller.setEnabled(view.getVisibleRect().getX()
						+ view.getVisibleRect().getWidth() < width);
				this.leftScroller.setEnabled(view.getVisibleRect().getX() > 0);
			}
		} else {
			removeScrollers();
		}
	}

	public void scrollTo(int x) {
		//System.out.println("Scrolling to " + x + " out of " + view.getWidth());
		view.scrollRectToVisible(new Rectangle(x, 0, 8, 0));
		this.rightScroller.setEnabled(view.getVisibleRect().getX()
				+ view.getVisibleRect().getWidth() < widthToScrollTo);
		this.leftScroller.setEnabled(view.getVisibleRect().getX() > 0);
		fireStateChanged();
	}

	public void removeScrollers() {
		if (this.leftScroller.getParent() == this) {
			view.scrollRectToVisible(new Rectangle(0, 0, 2, 2));
			remove(this.leftScroller);
			remove(this.rightScroller);
			revalidate();
			repaint();
		}
	}

	private void addScrollers() {
		add(this.leftScroller, BorderLayout.WEST);
		add(this.rightScroller, BorderLayout.EAST);
		revalidate();
//		System.out.println("Min in scrollers of " + view.getClass().getName() + " is " + view.getMinimumSize());
		view.setPreferredSize(view.getMinimumSize());
		view.setSize(view.getMinimumSize());
		//System.out.println("Doing the layout " + view.getWidth() + ":" + view.getMinimumSize().width);
		doLayout();
		
		//System.out.println("Scrollers added, total pref width is " + getPreferredSize().width);
		repaint();
	}

	protected void configureLeftScrollerButtonAction() {
		this.leftScroller.setFireActionOnRollover(true);
		this.leftScroller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int scrollBy = 12;
				double x = view.getVisibleRect().getX() - scrollBy;
				if (x <= -scrollBy) {
					leftScroller.setEnabled(false);
					return;
				}
				scrollTo((int) x);
				// Rectangle rect = new Rectangle((int) x, 0, scrollBy, 8);
				// // scroll the view to see the rectangle
				// view.scrollRectToVisible(rect);
				// // System.err.println("Scroll left to " + x);
				// rightScroller.setEnabled(true);
				//
				// fireStateChanged();
			}
		});
	}

	protected void configureRightScrollerButtonAction() {
		this.rightScroller.setFireActionOnRollover(true);
		this.rightScroller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//int scrollBy = 12;
				double x = view.getVisibleRect().getX()
						+ view.getVisibleRect().getWidth();
				//System.out.println("Scroll right to " + x + ":" + widthToScrollTo);
				if (x >= widthToScrollTo) {
					rightScroller.setEnabled(false);
					return;
				}
				//System.out.println("scrollTo " + x);
				scrollTo((int) x);
				// Rectangle rect = new Rectangle((int) x, 0, scrollBy, 8);
				// // scroll the view to see the rectangle
				// view.scrollRectToVisible(rect);
				// leftScroller.setEnabled(true);
				//
				// fireStateChanged();
			}
		});
	}

	public T getView() {
		return view;
	}

	public JViewport getViewport() {
		return this.scrollPane.getViewport();
	}

	/**
	 * Layout for the scrollable panel.
	 * 
	 * @author Kirill Grouchnikov
	 * @author Topologi
	 */
	protected class ScrollablePanelLayout implements LayoutManager {
		/**
		 * Creates new layout manager.
		 */
		public ScrollablePanelLayout() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 * java.awt.Component)
		 */
		public void addLayoutComponent(String name, Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		public void removeLayoutComponent(Component c) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		public Dimension preferredLayoutSize(Container c) {
			return new Dimension(c.getWidth(), 21);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		public Dimension minimumLayoutSize(Container c) {
			return this.preferredLayoutSize(c);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		public void layoutContainer(Container c) {
			int width = c.getWidth();
			int height = c.getHeight();

			ScrollablePanel<?> sPanel = (ScrollablePanel<?>) c;
			boolean isScrollerButtonsShowing = (sPanel.leftScroller.getParent() == sPanel);
			int scrollPanelWidth = isScrollerButtonsShowing ? width
					- sPanel.leftScroller.getPreferredSize().width
					- sPanel.rightScroller.getPreferredSize().width - 4 : width;
			int x = 0;
			if (isScrollerButtonsShowing) {
				int spw = sPanel.leftScroller.getPreferredSize().width;
				sPanel.leftScroller.setBounds(0, 0, spw, height);
				x += spw + 2;
			}
			sPanel.scrollPane.setBounds(x, 0, scrollPanelWidth, height);
			x += scrollPanelWidth + 2;
			if (isScrollerButtonsShowing) {
				int spw = sPanel.rightScroller.getPreferredSize().width;
				sPanel.rightScroller.setBounds(x, 0, spw, height);
			}
		}
	}

	public boolean isShowingScrollButtons() {
		return (leftScroller.getParent() == this);
	}

	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	protected void fireStateChanged() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		ChangeEvent changeEvent = new ChangeEvent(this);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
			}
		}
	}

}
