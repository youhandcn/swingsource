package changeddatepicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * calendar panel.
 * 
 * @author shuai.zhang
 */
public class CalendarPanel extends JPanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/** control panel. */
	private JPanel northPanel = null;
	/** today panel. */
	private JPanel southPanel = null;
	/** calendar panel. */
	private JPanel centerPanel = null;
	/** month next button. */
	private JButton nextMonthButton = null;
	/** month previous button. */
	private JButton previousMonthButton = null;
	/** put year and mouth select in it. */
	private JPanel northCenterPanel = null;
	/** show month label. */
	private JLabel monthLabel = null;
	/** change year. */
	private JSpinner yearSpinner = null;
	/** show now select date */
	private JButton nowDateButton = null;
	/** month pop menu. */
	private JPopupMenu monthPopupMenu = null;
	/** menu item array. */
	private JMenuItem[] monthPopupMenuItems = null;

	/** header */
	private JTableHeader dayTableHeader = null;
	/** table */
	private JTable dayTable = null;
	/** cell renderer */
	private CalendarTableCellRenderer dayTableCellRenderer = null;
	/** header renderer */
	private CalendarTableHeaderRenderer dayTableHeaderRenderer = null;

	/** calendar model. */
	private CalendarTableModel calendarModel = null;

	/** all action in it. */
	private EventHandler eventHandler = null;
	/** all action event for save */
	private Vector<ActionListener> actionListeners = null;
	/** all change event for save */
	private Vector<ChangeListener> changeListeners = null;

	/**
	 * method root.
	 */
	public CalendarPanel() {
		super();
		initialize();
	}

	/*
	 * initialize the panel.
	 */
	private void initialize() {

		// calendar init.
		this.setLayout(new BorderLayout());
		this.setSize(180, 175);
		this.setPreferredSize(new Dimension(180, 175));
		this.setBackground(SystemColor.activeCaptionText);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		this.setOpaque(false);

		// the component border.
		this.add(getNorthPanel(), BorderLayout.NORTH);
		this.add(getSouthPanel(), BorderLayout.SOUTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);
	}

	/*
	 * This method initializes northPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNorthPanel() {
		if (northPanel == null) {
			northPanel = new JPanel();
			northPanel.setLayout(new BorderLayout());
			northPanel.setName("");
			northPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			northPanel.setBackground(SystemColor.activeCaption);
			northPanel.add(getPreviousMonthButton(), BorderLayout.WEST);
			northPanel.add(getNextMonthButton(), BorderLayout.EAST);
			northPanel.add(getNorthCenterPanel(), BorderLayout.CENTER);
		}
		return northPanel;
	}

	/*
	 * This method initializes southPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setLayout(new BorderLayout());
			southPanel.setBackground(Color.WHITE);
			southPanel.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));
			southPanel.add(getTodayLabel(), BorderLayout.CENTER);
		}
		return southPanel;
	}

	/*
	 * This method initializes centerPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			centerPanel.setOpaque(false);
			centerPanel.add(getDayTableHeader(), BorderLayout.NORTH);
			centerPanel.add(getDayTable(), BorderLayout.CENTER);
		}
		return centerPanel;
	}

	/*
	 * This method initializes nextMonthButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNextMonthButton() {
		if (nextMonthButton == null) {
			nextMonthButton = new JButton(new NextIcon(Color.green, 8, 10));
			nextMonthButton.setText("");
			nextMonthButton.setPreferredSize(new Dimension(20, 15));
			nextMonthButton.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
			nextMonthButton.setFocusable(false);
			nextMonthButton.addActionListener(getEventHandler());
		}
		return nextMonthButton;
	}

	/*
	 * This method initializes previousMonthButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPreviousMonthButton() {
		if (previousMonthButton == null) {
			previousMonthButton = new JButton(new PreviousIcon(Color.green, 8,
					10));
			previousMonthButton.setText("");
			previousMonthButton.setPreferredSize(new Dimension(20, 15));
			previousMonthButton.setBorder(BorderFactory
					.createBevelBorder(BevelBorder.RAISED));
			previousMonthButton.setFocusable(false);
			previousMonthButton.addActionListener(getEventHandler());
		}
		return previousMonthButton;
	}

	/*
	 * This method initializes northCenterPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getNorthCenterPanel() {
		if (northCenterPanel == null) {
			northCenterPanel = new JPanel();
			northCenterPanel.setLayout(new java.awt.BorderLayout());
			northCenterPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0,
					5));
			northCenterPanel.setOpaque(false);
			northCenterPanel.add(getMonthLabel(), BorderLayout.CENTER);
			northCenterPanel.add(getYearSpinner(), BorderLayout.EAST);
		}
		return northCenterPanel;
	}

	/*
	 * This method initializes monthLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getMonthLabel() {
		if (monthLabel == null) {
			monthLabel = new JLabel();
			monthLabel.setForeground(SystemColor.activeCaptionText);
			monthLabel.setHorizontalAlignment(SwingConstants.CENTER);
			monthLabel.setText("uninitialised");
			monthLabel.addMouseListener(getEventHandler());
			getCalenderModel().addMonthLabel(monthLabel);
		}
		return monthLabel;
	}

	/*
	 * CalendarTableModel created
	 * 
	 * @return CalendarTableModel
	 */
	private CalendarTableModel getCalenderModel() {
		if (calendarModel == null) {
			calendarModel = new CalendarTableModel();
			calendarModel.addChangeListener(eventHandler);
		}
		return calendarModel;
	}

	/*
	 * This method initializes yearSpinner
	 * 
	 * @return javax.swing.JSpinner
	 */
	private JSpinner getYearSpinner() {
		if (yearSpinner == null) {
			yearSpinner = new JSpinner();
			yearSpinner.setModel(getCalenderModel());
		}
		return yearSpinner;
	}

	/*
	 * This method initializes dayTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getDayTable() {
		if (dayTable == null) {
			dayTable = new JTable();
			dayTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			dayTable.setOpaque(false);
			dayTable.setPreferredSize(new Dimension(100, 80));
			dayTable.setModel(getCalenderModel());
			dayTable.setShowGrid(true);
			dayTable.setGridColor(Color.WHITE);
			dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dayTable.setCellSelectionEnabled(true);
			dayTable.setRowSelectionAllowed(true);
			dayTable.setFocusable(false);
			dayTable.addMouseListener(getEventHandler());
			TableColumn column = null;
			for (int i = 0; i < Calendar.DAY_OF_WEEK; i++) {
				column = dayTable.getColumnModel().getColumn(i);
				column.setPreferredWidth(15);
				column.setCellRenderer(getDayTableCellRenderer());
			}
		}
		return dayTable;
	}

	/*
	 * CalendarTableCellRenderer
	 * 
	 * @return CalendarTableCellRenderer
	 */
	private CalendarTableHeaderRenderer getDayTableHeaderRenderer() {
		if (dayTableHeaderRenderer == null) {
			dayTableHeaderRenderer = new CalendarTableHeaderRenderer();
		}
		return dayTableHeaderRenderer;
	}

	/*
	 * CalendarTableCellRenderer
	 * 
	 * @return CalendarTableCellRenderer
	 */
	private CalendarTableCellRenderer getDayTableCellRenderer() {
		if (dayTableCellRenderer == null) {
			dayTableCellRenderer = new CalendarTableCellRenderer();
		}
		return dayTableCellRenderer;
	}

	/*
	 * JTableHeader
	 * 
	 * @return JTableHeader
	 */
	private JTableHeader getDayTableHeader() {
		if (dayTableHeader == null) {
			dayTableHeader = getDayTable().getTableHeader();
			dayTableHeader.setResizingAllowed(false);
			dayTableHeader.setReorderingAllowed(false);
			dayTableHeader.setDefaultRenderer(getDayTableHeaderRenderer());
		}
		return dayTableHeader;
	}

	/*
	 * This method initializes todayLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JButton getTodayLabel() {
		if (nowDateButton == null) {
			nowDateButton = new JButton();
			nowDateButton.setHorizontalAlignment(SwingConstants.CENTER);
			nowDateButton.setText("");
			nowDateButton.addMouseListener(getEventHandler());
			getCalenderModel().addTodayButton(nowDateButton);
		}
		return nowDateButton;
	}

	/*
	 * This method initializes monthPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getMonthPopupMenu() {
		if (monthPopupMenu == null) {
			monthPopupMenu = new JPopupMenu();
			JMenuItem[] menuItems = getMonthPopupMenuItems();
			for (int i = 0; i < menuItems.length; i++) {
				monthPopupMenu.add(menuItems[i]);
			}
		}
		return monthPopupMenu;
	}

	/*
	 * This method initializes JMenuItem
	 * 
	 * @return MenuItem
	 */
	private JMenuItem[] getMonthPopupMenuItems() {
		if (monthPopupMenuItems == null) {
			DateFormatSymbols df = new DateFormatSymbols();
			String[] months = df.getMonths();
			monthPopupMenuItems = new JMenuItem[months.length - 1];
			for (int i = 0; i < months.length - 1; i++) {
				JMenuItem mi = new JMenuItem(months[i]);
				mi.addActionListener(eventHandler);
				monthPopupMenuItems[i] = mi;
			}
		}
		return monthPopupMenuItems;
	}

	/**
	 * ActionListener
	 * 
	 * @return Vector<ActionListener>
	 */
	private Vector<ActionListener> getActionListeners() {
		if (actionListeners == null) {
			actionListeners = new Vector<ActionListener>();
		}
		return actionListeners;
	}

	/**
	 * ChangeListener
	 * 
	 * @return Vector<ChangeListener>
	 */
	private Vector<ChangeListener> getChangeListeners() {
		if (changeListeners == null) {
			changeListeners = new Vector<ChangeListener>();
		}
		return changeListeners;
	}

	/**
	 * remove action
	 * 
	 * @param arg
	 *            event
	 */
	public void removeActionListener(ActionListener arg) {
		getActionListeners().remove(arg);
	}

	/**
	 *remove change event.
	 * 
	 * @param arg
	 *            event
	 */
	public void removeChangeListener(ChangeListener arg) {
		getChangeListeners().remove(arg);
	}

	/**
	 * The actionListener is notified when a user clicks on a date
	 * 
	 * @param arg
	 */
	public void addActionListener(ActionListener arg) {
		getActionListeners().add(arg);
	}

	/**
	 * Change listeners will be notified when the selected date is changed
	 * 
	 * @param arg
	 */
	public void addChangeListener(ChangeListener arg) {
		getChangeListeners().add(arg);
	}

	/*
	 * @return handler event.
	 */
	private EventHandler getEventHandler() {
		if (eventHandler == null) {
			eventHandler = new EventHandler();
		}
		return eventHandler;
	}

	/**
	 * all action in it.
	 * 
	 * @author shuai.zhang
	 */
	private class EventHandler implements ActionListener, MouseListener,
			ChangeListener {

		/**
		 * Invoked when an action occurs.
		 */
		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(nextMonthButton)) {
				GregorianCalendar cal = (GregorianCalendar) getCalenderModel()
						.getCalendarClone();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					cal.set(Calendar.DATE, cal
							.getActualMaximum(Calendar.DAY_OF_MONTH));
				} else {
					cal.set(Calendar.DATE, day);
				}
				getCalenderModel().setCalendar(cal.getTime());
			} else if (e.getSource().equals(previousMonthButton)) {
				GregorianCalendar cal = (GregorianCalendar) getCalenderModel()
						.getCalendarClone();
				int day = cal.get(Calendar.DATE);
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, 1);
				if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					cal.set(Calendar.DATE, cal
							.getActualMaximum(Calendar.DAY_OF_MONTH));
				} else {
					cal.set(Calendar.DATE, day);
				}
				getCalenderModel().setCalendar(cal.getTime());
			} else {
				for (int month = 0; month < monthPopupMenuItems.length; month++) {
					if (e.getSource().equals(monthPopupMenuItems[month])) {
						GregorianCalendar cal = (GregorianCalendar) getCalenderModel()
								.getCalendarClone();
						int day = cal.get(Calendar.DATE);
						cal.set(cal.get(Calendar.YEAR), month, 1);
						if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
							cal.set(Calendar.DATE, cal
									.getActualMaximum(Calendar.DAY_OF_MONTH));
						} else {
							cal.set(Calendar.DATE, day);
						}
						getCalenderModel().setCalendar(cal.getTime());
					}
				}
			}
		}

		/**
		 * Invoked when the mouse button has been clicked (pressed and released)
		 * on a component.
		 */
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(monthLabel)) {

				// show pop menu.
				getMonthPopupMenu().setLightWeightPopupEnabled(false);
				monthPopupMenu.show((Component) e.getSource(), e.getX(), e
						.getY());
			} else if (e.getSource().equals(nowDateButton)) {

				// set it is now date.
				getCalenderModel().setCalendar(new Date());
				fireActionPerformed();
			} else if (e.getSource().equals(dayTable)) {
				int row = dayTable.getSelectedRow();
				int column = dayTable.getSelectedColumn();
				if (row >= 0 && row < dayTable.getRowCount()) {
					Integer date = (Integer) getCalenderModel().getValueAt(row,
							column);
					GregorianCalendar cal = (GregorianCalendar) getCalenderModel()
							.getCalendarClone();
					if (date <= 0
							|| date > cal
									.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						return;
					}

					getCalenderModel().setCalendar(Calendar.DAY_OF_MONTH,
							date.intValue());
					fireActionPerformed();
				}
			}
		}

		/**
		 * Invoked when the mouse button has been clicked (Entered) on a
		 * component.
		 */
		public void mouseEntered(MouseEvent e) {

		}

		/**
		 * Invoked when the mouse button has been clicked (Exited ) on a
		 * component.
		 */
		public void mouseExited(MouseEvent e) {

		}

		/**
		 * Invoked when the mouse button has been clicked (pressed ) on a
		 * component.
		 */
		public void mousePressed(MouseEvent e) {

		}

		/**
		 * Invoked when the mouse button has been clicked (released) on a
		 * component.
		 */
		public void mouseReleased(MouseEvent e) {

		}

		/**
		 * Invoked when the target of the listener has changed its state.
		 * 
		 * @param e
		 *            a ChangeEvent object
		 */
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == getCalenderModel()) {
				Iterator<?> i = getChangeListeners().iterator();
				while (i.hasNext()) {
					ChangeListener cl = (ChangeListener) i.next();
					cl.stateChanged(new ChangeEvent(CalendarPanel.this));
				}
			}
		}

	}

	/**
	 * called internally when actionListeners should be notified
	 * 
	 */
	private void fireActionPerformed() {
		Iterator<?> i = getActionListeners().iterator();
		while (i.hasNext()) {
			ActionListener al = (ActionListener) i.next();
			al.actionPerformed(new ActionEvent(this,
					ActionEvent.ACTION_PERFORMED, "date selected"));
		}
	}

	/**
	 * @return select date.
	 */
	public String toString() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		return df.format(getCalenderModel().getCalendarClone().getTime());
	}
}
