package de.wannawork.jcalendar;

/*
 * Copyright (c) 2003, Bodo Tasche (http://www.wannawork.de) All rights
 * reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. * Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 *       materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JWindow;
import javax.swing.RootPaneContainer;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner.DateEditor;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This Class creates a ComboBox for selecting the Date. If pressed, it shows a
 * Popup that contains a JCalendarPanel.
 * 
 * You can add a ChangeListener to this ComboBox to receive change events.
 * 
 * It is possible to change the Text on the ComboBox using the
 * DateFormat-Parameter.
 * 
 * @author Bodo Tasche, David Freels, Ivan Latysh, Igor Kriznar
 */
public class JCalendarComboBox extends JPanel implements ActionListener, ChangeListener, SwingConstants {

	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		
		try {
			
			final JCalendarComboBox cal= new JCalendarComboBox();
			
			cal.addChangeListener(new ChangeListener() {
			
				@Override
				public void stateChanged(ChangeEvent e) {
					//Thread.dumpStack();
					System.out.println(cal.getDate());
				}
			});
			
			cal.setDateFormat(new SimpleDateFormat("dd.mm.yyyy"));
			cal.setSpiningCalendarField(Calendar.DAY_OF_MONTH);
			cal.setDate(null);
			
			JFrame f= new JFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.getContentPane().add(cal);
			f.pack();
			f.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Where should be the Popup? 
	 */
	private int _popupLocation = LEFT;

	/**
     * If the Window looses Focus and the ToggleButton get's it, don't popup the
     * Window again...
	 */
	private boolean _calendarWindowFocusLost = false;

	/**
	 * Current selected Day
	 */
	private Calendar _selected;

	/**
	 * The ToggleButton for hiding/showing the JCalendarPanel
	 */
    private JToggleButton _button;

    /**
     * The text field that holds the date
     */
    private JSpinner _spinner;

	/**
	 * The JWindow for the PopUp
	 */
	private JWindow _calendarWindow;
	/**
	 * The JCalendarPanel inside the PopUp
	 */
	private JCalendarPanel _calendarPanel;
	/**
	 * The list of ChangeListeners
	 */
	private ArrayList<ChangeListener> _changeListener = new ArrayList<ChangeListener>();
	/**
	 * Currently firing an ChangeEvent?
	 */
	private boolean _fireingChangeEvent = false;
	/**
	 * Something changed in the JCalendarPanel ?
	 */
	//private boolean _changed = false;

	private boolean _changing;

	private DateEditor _editor;

	private SpinnerDateModel _model;
	
	boolean _unset=false;

	/**
     * Creates a Calendar using the current Date and current Local settings.
	 */
	public JCalendarComboBox() {
		_calendarPanel = new JCalendarPanel();
		createGUI();
	}

	/**
     * Creates a Calendar using the cal-Date and current Locale Settings. It
     * doesn't use the Locale in the Calendar-Object !
	 * 
	 * @param cal Calendar to use
	 */
	public JCalendarComboBox(Calendar cal) {
		_calendarPanel = new JCalendarPanel(cal);
		createGUI();
	}

	/**
     * Creates a Calendar using the current Date and the given Locale Settings.
	 * 
	 * @param locale Locale to use
	 */
	public JCalendarComboBox(Locale locale) {
		_calendarPanel = new JCalendarPanel(locale);
		createGUI();
	}

	/**
     * Creates a Calender using the given Date and Locale
	 * 
	 * @param cal Calendar to use
	 * @param locale Locale to use
	 */
	public JCalendarComboBox(Calendar cal, Locale locale) {
		_calendarPanel = new JCalendarPanel(cal, locale);
		createGUI();
	}

	/**
     * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal Calendar to use
	 * @param locale Locale to use
	 * @param dateFormat DateFormat for the ComboBox
	 */
	public JCalendarComboBox(Calendar cal, Locale locale, DateFormat dateFormat) {
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat);
		createGUI();
	}

	/**
     * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal Calendar to use
	 * @param locale Locale to use
	 * @param dateFormat DateFormat for the ComboBox
	 * @param location Location of the Popup (LEFT, CENTER or RIGHT)
	 */
	public JCalendarComboBox(Calendar cal, Locale locale, DateFormat dateFormat, int location) {
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat);
		_popupLocation = location;
		createGUI();
	}

	/**
     * Creates a Calender using the given Calendar, Locale and DateFormat.
	 * 
	 * @param cal Calendar to use
	 * @param locale Locale to use
	 * @param dateFormat DateFormat for the ComboBox
	 * @param location Location of the Popup (LEFT, CENTER or RIGHT)
	 * @param flat Flat Buttons for next/last Month/Year
	 */
	public JCalendarComboBox(Calendar cal, Locale locale, DateFormat dateFormat, int location, boolean flat) {
		_calendarPanel = new JCalendarPanel(cal, locale, dateFormat, flat);
		_popupLocation = location;
		createGUI();
	}

	/**
     * Creates a Calender using the given Calendar, Locale and DateFormat.
     * 
     * @param cal Calendar to use
     * @param locale Locale to use
     * @param dateFormat DateFormat for the ComboBox
     * @param location Location of the Popup (LEFT, CENTER or RIGHT)
     * @param flat Flat Buttons for next/last Month/Year
     * @param maxYear The highest year to be displayed in the year combo.
     */
    public JCalendarComboBox(Calendar cal, Locale locale, DateFormat dateFormat, int location, boolean flat, int maxYear) {
        _calendarPanel = new JCalendarPanel(cal, locale, dateFormat, flat, maxYear);
        _popupLocation = location;
        createGUI();
    }

    /**
	 * Creates the GUI for the JCalendarComboBox
	 */
	private void createGUI() {
		_calendarPanel.setListenerModus(JCalendarPanel.FIRE_DAYCHANGES);
		
		_selected = (Calendar) _calendarPanel.getCalendar().clone();

		_calendarPanel.addChangeListener(this);
        _calendarPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		setLayout(new BorderLayout());

		_spinner = new JSpinner() {
			private static final long serialVersionUID = 1L;

			@Override
			public void commitEdit() throws ParseException {
				if (!_unset) {
					super.commitEdit();
				}
			}
		};
		_spinner.setModel(_model= new SpinnerDateModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public Object getNextValue() {
				if (_unset) {
					return new Date();
				}
				return super.getNextValue();
			}
			@Override
			public Object getPreviousValue() {
				if (_unset) {
					return new Date();
				}
				return super.getPreviousValue();
			}
		});
        _spinner.setEditor(_editor= new JSpinner.DateEditor(_spinner, ((SimpleDateFormat) _calendarPanel.getDateFormat()).toPattern()));
        _spinner.getModel().setValue(_selected.getTime());
        _spinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
                notifySpinnerChange();
			}
		});
        
        _editor.getTextField().setHorizontalAlignment(JTextField.CENTER);

       _button = new JToggleButton() {
		private static final long serialVersionUID = 1L;

		final int maxW= 25;
    	   Dimension prefSize;
    	   @Override
    	   public Dimension getPreferredSize() {
    		   if (prefSize!=null) {
    			   if ((prefSize.width<=maxW && getHeight()<=maxW && getHeight()!=prefSize.width) || (getHeight()>maxW && prefSize.width!=maxW)) {
    				   prefSize=null;
    			   }
    		   }
    		   if (prefSize==null) {
    			   int d= getHeight()>maxW ? maxW : getHeight();
    			   prefSize= new Dimension(d,d);
    		   }
   			   return prefSize;
    	   }
    	   @Override
    	public Dimension getMinimumSize() {
    		return getPreferredSize();
    	}
       };
       
       _button.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("de/wannawork/jcalendar/jcalendar-icon.png")));

		_button.addActionListener(this);
		_button.setEnabled(true);

		_button.addFocusListener(new FocusAdapter() {
			/**
			 * Invoked when a component gains the keyboard focus.
			 */
			@Override
			public void focusGained(FocusEvent e) {
				if (e.getOppositeComponent() != null) {
					if (e.getOppositeComponent() instanceof JComponent) {
						JComponent opposite = (JComponent) e.getOppositeComponent();
						if ((opposite.getTopLevelAncestor() != _calendarWindow) && (!_calendarWindowFocusLost))
							_calendarWindowFocusLost= false;
					}
				}
			}
		});

        addHierarchyListener(new HierarchyListener() {

            public void hierarchyChanged(HierarchyEvent e) {
                hideCalendar();
            }
        });

        add(_spinner, BorderLayout.CENTER);
        add(_button, BorderLayout.EAST);
	}

    /**
     * Fixes the Spinner-Date and returns it
     * @return Date
     */
    private Date getSpinnerDate() {
    	Calendar cal = Calendar.getInstance();

        cal.setTime((Date) _spinner.getModel().getValue());

        int year = cal.get(Calendar.YEAR);

        //System.out.println("YEAR:" + year);
        if (year < 1900) {
            //System.out.println(">" + cal.get(Calendar.YEAR));
            cal.set(Calendar.YEAR, 1900);
            _spinner.getModel().setValue(cal.getTime());
        } else if (year > 2100) {
            //System.out.println(">" + cal.get(Calendar.YEAR));
            cal.set(Calendar.YEAR, 2100);
            _spinner.getModel().setValue(cal.getTime());
        }
        //System.out.println(cal.get(Calendar.YEAR));
        //_selected.setTime(cal.getTime());
        //_calendarPanel.setCalendar(_selected);
        
        return cal.getTime();
    }
    
    /*
     * Spinner has changed
     */
    private void notifySpinnerChange() {
    	if (_changing) {
    		return;
    	}
    	if (_unset) {
    		_unset=false;
			_editor.getTextField().setForeground(_editor.getTextField().getForeground().darker().darker().darker());
    	}
    	Date s= getSpinnerDate();
    	if (!_selected.getTime().equals(s)) {
    		_selected.setTime(s);
    		fireChangeEvent();
    	}
    }

	/**
     * Override setFont method from JComponent.<br/>
     * Overridden method will take care of setting the font for underlaying spinner editor
     *
     * @param font font to set
     */
    @Override
	public void setFont(Font font) {
      // delegate method call to parent
      super.setFont(font);
      // set font for spinner when it isn't null
      if (null!=_spinner) {
        _spinner.setFont(font);
      }
    }

    /**
     * Override setBorder method from JComponent.<br/>
     * Overridden method will take care of setting the border for spinner editor.
     *
     * @param border
     */
    @Override
	public void setBorder(Border border) {
      // do not delegate setBorder to parent.
      // but set the border for spinner
      if (null!=_spinner) {
        _spinner.setBorder(border);
      }
    }

    /**
	 * Creates the CalendarWindow-Popup
	 */
	private void createCalendarWindow() {
		
		JInternalFrame internalFrame = (JInternalFrame)SwingUtilities.getAncestorOfClass(JInternalFrame.class, this);

        //Window ancestor = SwingUtilities.getWindowAncestor(this);
		Window ancestor = (Window) this.getTopLevelAncestor();

        _calendarWindow = new JWindow(ancestor);

		JPanel contentPanel = (JPanel) _calendarWindow.getContentPane();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(_calendarPanel);
		
		_calendarPanel.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
					hideCalendar();
					return;
				}
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					setCalendar(_calendarPanel.getCalendar());
					return;
				}
			}
			
		});

        ((JComponent) ((RootPaneContainer) ancestor).getContentPane()).addAncestorListener(new AncestorListener() {
			Point p;
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				hideCalendar();
			}
		
			@Override
			public void ancestorMoved(AncestorEvent event) {
				if (p!=null && !p.equals(event.getAncestor().getLocation())) {
					hideCalendar();
				}
				p= event.getAncestor().getLocation();
			}
		
			@Override
			public void ancestorAdded(AncestorEvent event) {
				hideCalendar();
			}
		});

		((JComponent) ((RootPaneContainer) ancestor).getContentPane()).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hideCalendar();
			}
		});


		_calendarWindow.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				if (_button.isSelected()) {
					_calendarWindowFocusLost = true;
				}
				
				hideCalendar();
			}
		});

		// Component listener for owner frame
        class ComponentL implements ComponentListener {
			Point p;

          public void componentResized(ComponentEvent e) {
            hideCalendar();
          }

          public void componentMoved(ComponentEvent e) {
				if (p!=null && !p.equals(e.getComponent().getLocation())) {
					hideCalendar();
				}
				p= e.getComponent().getLocation();
          }

          public void componentShown(ComponentEvent e) {
            hideCalendar();
          }

          public void componentHidden(ComponentEvent e) {
            hideCalendar();
          }
        };

        /*
         * TODO: We assume that if JInternalFrame found as an 
         * ancestor of this component that it is a proper owner frame
         * But in real world it isn't always true. We need to find a 
         * real world solution for it.
         */


        if (internalFrame != null) {
            internalFrame.addComponentListener(new ComponentL());
        }

		ancestor.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				hideCalendar();
			}
			@Override
			public void windowLostFocus(WindowEvent e) {
				hideCalendar();
			}
			@Override
			public void windowStateChanged(WindowEvent e) {
				hideCalendar();
			}
		});

		ancestor.addComponentListener(new ComponentL());
		
		_calendarWindow.pack();
	}

	/**
	 * Returns the current seleted Date as Calendar
     * 
	 * @return current selected Date
	 */
	public Calendar getCalendar() {
		if (_unset) {
			return null;
		}
		return (Calendar)_selected.clone();
	}

	/**
	 * Returns the current seleted Date
	 * @return current selected Date
	 */
	public Date getDate() {
		if (_unset) {
			return null;
		}
		return _selected.getTime();
	}

	/**
	 * Sets the current selected Date
     * 
	 * @param cal Date to select
	 */
	public void setCalendar(Calendar cal) {
		hideCalendar();
		if (_selected.equals(cal)) {
			return;
		}
    	if (_unset) {
    		_unset=false;
			_editor.getTextField().setForeground(_editor.getTextField().getForeground().darker().darker().darker());
    	}
		_unset=cal==null;
		_selected.setTime(cal!=null ? cal.getTime() : new Date(0));
		_changing=true;
		_spinner.getModel().setValue(cal!=null ? cal.getTime() : new Date(0));
		if (_unset) {
			_editor.getTextField().setText(_editor.getFormat().toPattern());
			_editor.getTextField().setForeground(_editor.getTextField().getForeground().brighter().brighter().brighter());
		}
		_changing=false;
		fireChangeEvent();
	}

	/**
	 * Sets the current selected Date
	 * @param date Date to select
	 */
	public void setDate(Date date) {
		hideCalendar();
    	if (_unset) {
    		_unset=false;
			_editor.getTextField().setForeground(_editor.getTextField().getForeground().darker().darker().darker());
    	}
		_unset=date==null;
		_selected.setTime(date!=null ? date : new Date());
		_changing=true;
		_spinner.getModel().setValue(date!=null ? _selected.getTime() : new Date());
		if (_unset) {
			_editor.getTextField().setText(_editor.getFormat().toPattern());
			_editor.getTextField().setForeground(_editor.getTextField().getForeground().brighter().brighter().brighter());
		}
		_changing=false;
		fireChangeEvent();
	}

	/**
	 * Returns the JCalendarPanel that is shown in the PopUp
     * 
	 * @return JCalendarPanel in the PopUp
	 */
	public JCalendarPanel getCalendarPanel() {
		return _calendarPanel;
	}

	/**
     * In earlier Versions it sets the vertical Position of the Text in the
     * Button, but it is not longer used!
     * 
     * @deprecated does nothing!!
     * @param value nothing
	 */
	@Deprecated
	public void setVerticalAlignment(int value) {
	}

	/**
	 * Sets the horizontal Position of the Text in the Button 
     * 
     * @param value RIGHT, LEFT, CENTER, LEADING, TRAILING for horizontal
     *                  Alignment
	 */
	public void setHorizontalAlignment(int value) {
        ((JSpinner.DefaultEditor) _spinner.getEditor()).getTextField().setHorizontalAlignment(value);
	}

	/**
	 * Handles the ToggleButton events for hiding / showing the PopUp
	 * 
	 * @param e the ActionEvent
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
        /*if ((_calendarWindow != null) && (_calendarWindow.isVisible())) {
            hideCalendar();
        } else {
            showCalender();
        }*/
		if (_button.isSelected()) {
			if (!_calendarWindowFocusLost) {
				showCalender();
			} else {
				_button.setSelected(false);
			}
			_calendarWindowFocusLost = false;
		} else {
			hideCalendar();
		}
	}

	/**
	 * Hides the Calendar PopUp and fires a 
	 * ChangeEvent if a change was made
	 */
	public void hideCalendar() {
		if (_calendarWindow!=null && _calendarWindow.isVisible()) {
			
			//Thread.dumpStack();
			
			_button.setSelected(false);
			_calendarWindow.setVisible(false);

			/*
			 * hiding alendar is not rigth triggr to registeer change
			if (!_calendarPanel.toString().equals(_button.getText())) {
				_changed = true;
			}

			if (_changed) {
				_button.setText(_calendarPanel.toString());
				_selected = (Calendar) _calendarPanel.getCalendar().clone();
				_changed = false;
				fireChangeEvent();
			}*/
		}
	}

	/**
	 * Shows the Calendar PopUp
	 */
	public void showCalender() {
		Window ancestor = (Window) this.getTopLevelAncestor();

		if ((_calendarWindow == null) || (ancestor != _calendarWindow.getOwner())) {
			createCalendarWindow();
		}

        //Update the datefrom the spinner model
		_changing=true;
		if (_unset) {
			_selected.setTime(new Date());
		}
        _calendarPanel.setCalendar(_selected);
        _changing=false;

        Point location = getLocationOnScreen();

		int x;

		if (_popupLocation == RIGHT) {
            x = (int) location.getX() + getSize().width - _calendarWindow.getSize().width;
		} else if (_popupLocation == CENTER) {
            x = (int) location.getX() + ((getSize().width - _calendarWindow.getSize().width) / 2);
		} else {
			x = (int) location.getX();
		}

		int y = (int) location.getY() + _button.getHeight();

		Rectangle screenSize = getDesktopBounds();

		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		if (x + _calendarWindow.getWidth() > screenSize.width) {
			x = screenSize.width - _calendarWindow.getWidth();
		}

		if (y + 30 + _calendarWindow.getHeight() > screenSize.height) {
			y = (int) location.getY() - _calendarWindow.getHeight();
		}

		_calendarWindow.setBounds(x, y, _calendarWindow.getWidth(), _calendarWindow.getHeight());
		_calendarWindow.setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                _calendarPanel.requestFocus();
	}

        });
    }

	/**
	 * Gets the screensize.  Takes into account multi-screen displays.
     * 
	 * @return a union of the bounds of the various screen devices present
	 */
	private Rectangle getDesktopBounds() {
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsDevice[] gd = ge.getScreenDevices();
		final Rectangle[] screenDeviceBounds = new Rectangle[gd.length];
		Rectangle desktopBounds = new Rectangle();
		for (int i = 0; i < gd.length; i++) {
			final GraphicsConfiguration gc = gd[i].getDefaultConfiguration();
			screenDeviceBounds[i] = gc.getBounds();
			desktopBounds = desktopBounds.union(screenDeviceBounds[i]);
		}

		return desktopBounds;
	}

	/**
     * Listens to ChangeEvents of the JCalendarPanel and rembers if something
     * was changed.
	 * 
	 * If the Day was changed, the PopUp is closed.
	 * 
	 * @param e ChangeEvent
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		if (_changing) {
			return;
		}
		hideCalendar();
		setCalendar(_calendarPanel.getCalendar());
	}

	/**
	 * Adds a Changelistener to this JCalendarComboBox.
	 * 
     * It will be called everytime the ComboBox is closed and the Date was
     * changed
	 * 
	 * @param listener ChangeListener
	 */
	public void addChangeListener(ChangeListener listener) {
		_changeListener.add(listener);
	}

	/**
	 * Removes a ChangeListener from this JCalendarComboBox
	 * 
	 * @param listener listener to remove
	 */
	public void removeChangeListener(ChangeListener listener) {
		_changeListener.remove(listener);
	}

	/**
	 * Gets all ChangeListeners
     * 
	 * @return all ChangeListeners
	 */
	public ChangeListener[] getChangeListener() {
		return (ChangeListener[]) _changeListener.toArray();
	}

	/**
	 * Fires the ChangeEvent
	 */
	protected void fireChangeEvent() {
		if (!_fireingChangeEvent) {
			_fireingChangeEvent = true;
			ChangeEvent event = new ChangeEvent(this);

			for (int i = 0; i < _changeListener.size(); i++) {
				_changeListener.get(i).stateChanged(event);
			}

			_fireingChangeEvent = false;
		}

	}

	/**
	 * Enables/Disables the ComboBox
     * 
	 * @param enabled Enabled ?
	 */
	@Override
	public void setEnabled(boolean enabled) {
        _spinner.setEnabled(enabled);
		_button.setEnabled(enabled);
	}

	/**
	 * Is the ComboBox enabled ?
     * 
	 * @return enabled ?
	 */
	@Override
	public boolean isEnabled() {
		return _button.isEnabled();
	}

	/**
	 * Gets the Popup Location
     * 
	 * @return location of the Popup
	 */
	public int getPopupLocation() {
		return _popupLocation;
	}

	/**
	 * Sets the Location of the Popup (LEFT, CENTER or RIGHT)
	 * 
	 * @param location Location of the PopUp
	 */
	public void setPopupLocation(int location) {
		_popupLocation = location;
	}
	
	/**
	 * Sets new format for displaying selected date as string. Must not be null.
	 * @param format new non-null format for displaying selected date as string
	 */
	public void setDateFormat(DateFormat format) {
		_calendarPanel.setDateFormat(format);
		Color c= _editor.getTextField().getForeground();
        _spinner.setEditor(_editor= new JSpinner.DateEditor(_spinner, ((SimpleDateFormat) _calendarPanel.getDateFormat()).toPattern()));
        _editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        _editor.getTextField().setForeground(c);
	}
	
	/**
	 * Returns format for displaying selected date as string.
	 * @return format for displaying selected date as string
	 */
	public DateFormat getDateFormat() {
		return _calendarPanel.getDateFormat();
	}

    /**
     * The spinner next and spinner previous actions
     * move the specified <code>Calendar</code> field forward or backward 
     * by one unit.
     * The <code>calendarField</code> parameter must be one of the 
     * <code>Calendar</code> field constants like <code>Calendar.MONTH</code> 
     * or <code>Calendar.MINUTE</code>.
     * 
     * @param calendarField one of 
     *  <ul>
     *    <li><code>Calendar.ERA</code>
     *    <li><code>Calendar.YEAR</code>
     *    <li><code>Calendar.MONTH</code>
     *    <li><code>Calendar.WEEK_OF_YEAR</code>
     *    <li><code>Calendar.WEEK_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_MONTH</code>
     *    <li><code>Calendar.DAY_OF_YEAR</code>
     *    <li><code>Calendar.DAY_OF_WEEK</code>
     *    <li><code>Calendar.DAY_OF_WEEK_IN_MONTH</code>
     *    <li><code>Calendar.AM_PM</code>
     *    <li><code>Calendar.HOUR</code>
     *    <li><code>Calendar.HOUR_OF_DAY</code>
     *    <li><code>Calendar.MINUTE</code>
     *    <li><code>Calendar.SECOND</code>
     *    <li><code>Calendar.MILLISECOND</code>
     *  </ul>
     * <p>
     * 
     * @see SpinnerDateModel#setCalendarField(int)
     */
	public void setSpiningCalendarField(int calendarField) {
		_model.setCalendarField(calendarField);
	}

    /**
     * Returns the <code>Calendar</code> field that is moved when with spinner 
     * next ans previous action.
     * 
     * @return the value of the <code>calendarField</code> property
     * 
     * @see #setSpiningCalendarField(int)
     */
	public int getSpiningCalendarField() {
		return _model.getCalendarField();
	}
}