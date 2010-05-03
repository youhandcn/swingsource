/*
 * Open source project: Smart Calendar.
 * 
 * @(#)BorderPanel.java  2006/02/21
 *
 * Copyright 2006 Samuel Lee.
 * 
 * Source code is free, but must be marked with
 * "Powered by Samuel Lee" if be redistributed, or
 * "With contributions from Samuel Lee" if be modified.
 * The author of the following codelines will NOT be
 * responsible for legal liabilities caused by
 * any redistribution with or without any modification.
 * 
 * CAUTION: THIS CODE MUST NOT BE USED FOR ANY COMMERCIAL PURPOSE WITHOUT THE AUTHOR'S PERMISSION!
 * ANY VIOLATION MAY BE FACED WITH LITIGATION!
 */
package simpleclock;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A useful panel bean with border and a titled infomation.<br><br>
 * 
 * <center><table>
 * <tr ALIGN="CENTER">
 * <td><img SRC="../../../../resources/BorderPanel.jpg"
 *      alt="A titled border panel.">
 * </td>
 * </tr>
 * 
 * <tr ALIGN="CENTER">
 * <td>Figure 1: A titled border panel</td>
 * </tr>
 * </table></center>
 * 
 * @see javax.swing.border.Border
 * @see javax.swing.border.TitledBorder
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class BorderPanel extends JPanel
{
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Panel border.
	 */
	private TitledBorder myBorder = null;

	/**
	 * Default constructor:
   * Uses etched border and the title text is "Title".
   * Title is located in the middle of the north edge.
	 */
	public BorderPanel()
	{
		myBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Title");
		myBorder.setTitleJustification(TitledBorder.CENTER);
		myBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		initialize();
	}

	/**
   * Constructor:
	 * Creates panel with specific border.
	 * 
	 * @param border the border of this panel.
	 */
	public BorderPanel(TitledBorder border)
	{
		myBorder = border;
		initialize();
	}

	/**
   * Constructor:
	 * Creates panel with specific border and initial title.
	 * 
	 * @param border the border of this panel.
   * @param title the title of this panel.
	 */
	public BorderPanel(Border border, String title)
	{
		myBorder = BorderFactory.createTitledBorder(border, title);
		myBorder.setTitleJustification(TitledBorder.CENTER);
		myBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);
		initialize();
	}

	/**
	 * This method initializes the GUI.
	 */
	private void initialize()
	{
		setBorder(myBorder);
	}
	
	/**
   * Replaces the border of this panel.
   * 
	 * @param border the border to replace.
	 */
	public void setMyBorder(TitledBorder border)
	{
		myBorder = border;
		setBorder(border);
	}

	/**
   * Replaces the <code>Border</code> of this panel with a specific title.
   * 
   * @param border the border to replace.
   * @param title the title to replace.
	 */
	public void setMyBorder(Border border, String title)
	{
		setMyBorder(border, title,
				myBorder.getTitleJustification(),
				myBorder.getTitlePosition(),
				myBorder.getTitleFont(),
				myBorder.getTitleColor());
	}
	
	/**
   * Replaces the <code>Border</code> of this panel.
   * 
   * @param border the border to replace.
	 */
	public void setMyBorder(Border border)
	{
		setMyBorder(border,
				myBorder.getTitle(),
				myBorder.getTitleJustification(),
				myBorder.getTitlePosition(),
				myBorder.getTitleFont(),
				myBorder.getTitleColor());
	}
	
	/**
   * Replaces the border of this panel with a specific title,
   * and sets font for the title text and color for the border.
   * 
   * @param border the <code>Border</code> to replace.
   * @param title the title to replace.
	 * @param font the desired <code>Font</code> for the border.
	 * @param color the desired <code>Color</code> for the border.
	 */
	public void setMyBorder(Border border, String title, Font font, Color color)
	{
		setMyBorder(border, title,
				myBorder.getTitleJustification(),
				myBorder.getTitlePosition(), font, color);
	}
	
	/**
   * Replaces the border of this panel with a specific title,
   * and sets the position and justification for the border.
   * 
   * @param border the <code>Border</code> to replace.
   * @param title the title to replace.
	 * @param justification the desired justification for the border.
	 * @param position the desired position for the border.
	 */
	public void setMyBorder(Border border, String title, int justification, int position)
	{
		setMyBorder(border, title, justification, position,
				myBorder.getTitleFont(),
				myBorder.getTitleColor());
	}
	
	/**
   * Replaces the border of this panel with a specific title,
   * Sets the position, justification and color for the border.
   * Sets font for the title text.
   * 
   * @param border the <code>Border</code> to replace.
   * @param title the title to replace.
   * @param justification the desired justification for the border.
   * @param position the desired position for the border.
   * @param font the desired <code>Font</code> for the border.
   * @param color the desired <code>Color</code> for the border.
	 */
	public void setMyBorder(Border border, String title, int justification, int position, Font font, Color color)
	{
		myBorder = BorderFactory.createTitledBorder(border, title);
		myBorder.setTitleJustification(justification);
		myBorder.setTitlePosition(position);
		myBorder.setTitleFont(font);
		myBorder.setTitleColor(color);
		setBorder(border);
	}
	
	/**
   * Replaces the title text for this panel.
   * 
	 * @param title the title to replace.
	 */
	public void setTitle(String title)
	{
		myBorder.setTitle(title);
	}
	
	/**
   * Replaces the title of the border.
   * Sets color for the border.
   * Sets font for the title text.
   * 
   * @param title the title to replace.
   * @param font the desired <code>Font</code> for the border.
   * @param color the desired <code>Color</code> for the border.
	 */
	public void setTitle(String title, Font font, Color color)
	{
		myBorder.setTitle(title);
		myBorder.setTitleFont(font);
		myBorder.setTitleColor(color);
	}
	
	/**
   * Replaces the title of the border.
   * Sets the position and justification for the border.
   * 
   * @param title the title to replace.
   * @param justification the desired justification for the border.
   * @param position the desired position for the border.
	 */
	public void setTitle(String title, int justification, int position)
	{
		myBorder.setTitle(title);
		myBorder.setTitleJustification(justification);
		myBorder.setTitlePosition(position);
	}
	
	/**
   * Replaces the title of the border.
   * Sets the position, justification and color for the border.
   * Sets font for the title text.
   * 
   * @param title the title to replace.
   * @param justification the desired justification for the border.
   * @param position the desired position for the border.
   * @param font the desired <code>Font</code> for the border.
   * @param color the desired <code>Color</code> for the border.
	 */
	public void setTitle(String title, int justification, int position, Font font, Color color)
	{
		myBorder.setTitle(title);
		myBorder.setTitleJustification(justification);
		myBorder.setTitlePosition(position);
		myBorder.setTitleFont(font);
		myBorder.setTitleColor(color);
	}
}
