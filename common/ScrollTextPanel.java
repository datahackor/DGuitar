// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScrollTextPanel.java

package common;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;

import dguitar.gui.DisplayOptions;

// Referenced classes of package common:
//			LicenseString

public class ScrollTextPanel extends JPanel
	implements LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Font fontLog;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	protected DisplayOptions displayOptions;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public ScrollTextPanel()
	{
		this.displayOptions = new DisplayOptions();
		fontLog = new Font("Yahei", 0, this.displayOptions.FontS);
		initComponents();
	}

	public void setFont(Font f)
	{
		if (f != null && jTextArea1 != null)
			jTextArea1.setFont(f);
	}

	public void setDisabledColor()
	{
		jTextArea1.setBackground(new Color(168, 152, 144));
	}

	public void setEditable(boolean b)
	{
		jTextArea1.setEditable(b);
	}

	public void setText(String str)
	{
		jTextArea1.setText(str);
	}

	public void append(String str)
	{
		jTextArea1.append(str);
	}

	public boolean displayFile(String str)
	{
		boolean success = true;
		int contLines = 0;
		setEditable(false);
		if (str != null)
			try
			{
				FileReader FR = new FileReader(str);
				BufferedReader BR = new BufferedReader(FR);
				String line;
				do
				{
					line = BR.readLine();
					if (line != null)
					{
						append(line + "\n");
						contLines++;
					}
				} while (line != null);
				BR.close();
			}
			catch (Exception e)
			{
				success = false;
				e.printStackTrace();
			}
		JViewport JV = jScrollPane1.getViewport();
		JV.scrollRectToVisible(new Rectangle(0, 0, 10, 10));
		return success;
	}

	private void initComponents()
	{
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();
		
		setLayout(new BorderLayout());
		setAutoscrolls(true);
		setMaximumSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(200, 200));
		jScrollPane1.setAutoscrolls(true);
		jTextArea1.setFont(fontLog);
		jTextArea1.setTabSize(4);
		jScrollPane1.setViewportView(jTextArea1);
		add(jScrollPane1, "Center");
	}
}
