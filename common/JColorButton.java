// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JColorButton.java

package common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Referenced classes of package common:
//			LicenseString

public class JColorButton extends JButton
	implements ActionListener, LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JColorChooser JCC;
	private ActionListener userListener;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public JColorButton()
	{
		super.addActionListener(this);
		userListener = null;
	}

	public void actionPerformed(ActionEvent actionEvent)
	{
		java.awt.Color current = getBackground();
		if (JCC == null)
			JCC = new JColorChooser(current);
		else
			JCC.setColor(current);
		String value = JOptionPane.showInputDialog(getParent(), JCC);
		if (value != null)
		{
			setBackground(JCC.getColor());
			if (userListener != null)
				userListener.actionPerformed(actionEvent);
		}
	}

	public void addActionListener(ActionListener l)
	{
		userListener = l;
	}
}
