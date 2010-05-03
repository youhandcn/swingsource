package de.wannawork.jcalendar;
/*
 * Copyright (c) 2003, Bodo Tasche (http://www.wannawork.de)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this 
 *       list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this 
 *       list of conditions and the following disclaimer in the documentation and/or other 
 *       materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

/**
 * This Class represents a Day in the MonthLabel
 * 
 * @author Bodo Tasche, Igor Kriznar
 */
public class DayLabel extends FlatButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the DayLabel
	 * @param day The Day of the Month for this Label
	 * @param monthPanel The MonthPanel using this
	 */
	public DayLabel(int day, boolean today, JMonthPanel monthPanel) {
		super(Integer.toString(day));

		_today = today;
		
		if (_today) {
			setOpaque(true);
			setBackground(JMonthPanel.BACKGROUND_COLOR.darker());
		} else {
			setBackground(JMonthPanel.BACKGROUND_COLOR);
		}

		
		addActionListener(this);

		_day = day;
		_monthPanel = monthPanel;

		setHorizontalAlignment(JLabel.RIGHT);
	}

	/**
	 * Sets the Border. If the Day
	 * is today, the Border isn't set.
	 * 
	 * @param border the Border to use 
	 */
	/*public void setBorder(Border border) {
		if (_today) {
			super.setBorder(BorderFactory.createEtchedBorder());
		} else {
			super.setBorder(border);
		}
	}*/

	/**
	 * Sets the selction of this DayLabel
	 * @param selected Selected ?
	 */
	@Override
	public void setSelected(boolean selected) {
		_selected = selected;

		if (_selected) {
			setOpaque(true);
			setBackground(JMonthPanel.SELECTED_BACKGROUND_COLOR);
			setForeground(JMonthPanel.SELECTED_FONT_COLOR);
		} else {
			//setOpaque(false);
			if (_today) {
				setOpaque(true);
				setBackground(JMonthPanel.BACKGROUND_COLOR.darker());
			} else {
				setOpaque(false);
				setBackground(JMonthPanel.BACKGROUND_COLOR);
			}
			setForeground(JMonthPanel.FONT_COLOR);
		}
	}

	/**
	 * Is this DayLabel selected ?
	 * @return Selected
	 */
	@Override
	public boolean isSelected() {
		return _selected;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		_monthPanel.setSelectedDayOfMonth(_day);
		requestFocus();
	}

	/**
	 * Is the showing Day the current day ?
	 */
	private boolean _today;

	/**
	 * The MonthPanel for this DayLabel
	 */
	private JMonthPanel _monthPanel;
	/**
	 * The Day this DayLabel is representing
	 */
	private int _day;
	/**
	 * Is this DayLabel selected
	 */
	private boolean _selected = false;

}