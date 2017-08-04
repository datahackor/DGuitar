// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImageToolBar.java

package common;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;

// Referenced classes of package common:
//			LicenseString

public class ImageToolBar extends JToolBar
	implements Serializable, LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numButtons;
	private ImageIcon IconArray[];
	private ImageIcon IconDisabledArray[];
	private JButton JButtonArray[];

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public ImageToolBar()
	{
		numButtons = 0;
		IconArray = null;
		JButtonArray = null;
	}

	public boolean setImages(Image images[])
	{
		boolean success = false;
		if (images != null && images.length == numButtons)
		{
			if (IconArray == null)
				IconArray = new ImageIcon[images.length];
			for (int i = 0; i < images.length; i++)
				if (images[i] != null)
				{
					IconArray[i] = new ImageIcon(images[i]);
					JButtonArray[i].setIcon(IconArray[i]);
				}

			success = true;
		}
		return success;
	}

	public boolean setImageIcons(ImageIcon imageIcons[])
	{
		boolean success = false;
		if (imageIcons != null && imageIcons.length == numButtons)
		{
			IconArray = imageIcons;
			for (int i = 0; i < imageIcons.length; i++)
				if (imageIcons[i] != null)
					JButtonArray[i].setIcon(IconArray[i]);

			success = true;
		}
		return success;
	}

	public boolean setImageIconsDisabled(ImageIcon imageIcons[])
	{
		boolean success = false;
		if (imageIcons != null && imageIcons.length == numButtons)
		{
			IconDisabledArray = imageIcons;
			for (int i = 0; i < imageIcons.length; i++)
				if (imageIcons[i] != null)
					JButtonArray[i].setDisabledIcon(IconDisabledArray[i]);

			success = true;
		}
		return success;
	}

	public int getNumButtons()
	{
		return numButtons;
	}

	public void setNumButtons(int n)
	{
		if (n > 0)
		{
			numButtons = n;
			JButtonArray = new JButton[numButtons];
			for (int i = 0; i < numButtons; i++)
			{
				JButtonArray[i] = new JButton();
				JButtonArray[i].setDefaultCapable(false);
				//JButtonArray[i].setActionCommand(i);
				JButtonArray[i].setActionCommand( String.valueOf(i));
				add(JButtonArray[i]);
			}

		}
	}

	public void addActionListener(ActionListener listener)
	{
		if (listener != null)
		{
			for (int i = 0; i < numButtons; i++)
				JButtonArray[i].addActionListener(listener);

		}
	}

	private boolean validIndex(int i)
	{
		return i >= 0 && i < getNumButtons();
	}

	public void setButtonEnabled(int i, boolean flag)
	{
		if (validIndex(i))
			JButtonArray[i].setEnabled(flag);
	}

	public JButton getButtonAtIndex(int i)
	{
		JButton resp = null;
		if (validIndex(i))
			resp = JButtonArray[i];
		return resp;
	}
}
