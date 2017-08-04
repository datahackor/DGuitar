// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScrollText.java

package common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import dguitar.gui.DisplayOptions;

// Referenced classes of package common:
//			LicenseString, ScrollTextPanel

public class ScrollText extends JFrame
	implements LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String initial;
	private int width;
	private int height;
	private Dimension screenSize;
	private JPanel JPlogo;
	private JLabel logo;
	private ScrollTextPanel STP;
	private JLabel jLabel1;
	private JPanel jPanel1;
	protected DisplayOptions displayOptions;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public ScrollText()
	{
		width = 500;
		height = 500;
		initComponents();
	}

	public ScrollText(String init)
	{
		width = 500;
		height = 500;
		initComponents();
		initial = init;
		STP.setText(initial);
	}

	public void setDisabledColor()
	{
		STP.setDisabledColor();
	}

	public void setSTPColor(Color c)
	{
		STP.setBackground(c);
	}

	public void addLogo(String imageName)
	{
		JPlogo = new JPanel();
		logo = new JLabel();
		logo.setIcon(new ImageIcon(imageName));
		JPlogo.add(logo);
		getContentPane().add(JPlogo, "North");
		validate();
	}

	public void removeLogo()
	{
		getContentPane().remove(JPlogo);
		validate();
	}

	public void setEditable(boolean b)
	{
		STP.setEditable(b);
	}

	public void setText(String str)
	{
		STP.setText(str);
	}

	public void append(String str)
	{
		STP.append(str);
	}

	public void displayFile(String file)
	{
		STP.displayFile(file);
	}

	public void setWidth(int w)
	{
		if (w > 0)
		{
			width = w;
			setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
		}
	}

	public void setHeight(int h)
	{
		if (h > 0)
		{
			height = h;
			setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
		}
	}

	public void setClosable(boolean isClosable, boolean doExit)
	{
		if (isClosable)
		{
			if (doExit)
				setDefaultCloseOperation(3);
			else
				setDefaultCloseOperation(2);
		} else
		{
			setDefaultCloseOperation(0);
		}
	}

	public ScrollTextPanel getScrollTextPanel()
	{
		return STP;
	}

	public void addClearLog()
	{
		JPanel jPanel2 = new JPanel();
		JButton clear = new JButton();
		clear.setText("Clear Log");
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt)
			{
				clearActionPerformed(evt);
			}

		});
		jPanel2.add(clear);
		getContentPane().add(jPanel2, "South");
	}

	private void clearActionPerformed(ActionEvent e)
	{
		STP.setText(initial);
	}

	private void initComponents()
	{
		jPanel1 = new JPanel();
		jLabel1 = new JLabel();
		this.displayOptions = new DisplayOptions();
		STP = new ScrollTextPanel();
		setDefaultCloseOperation(0);
		setTitle("DGUITAR LOG");
		setFont(new Font("Yahei", 0, this.displayOptions.FontS));
		getContentPane().add(jPanel1, "South");
		getContentPane().add(jLabel1, "North");
		STP.setBackground(new Color(255, 255, 255));
		STP.setEditable(true);
		getContentPane().add(STP, "Center");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 625) / 2, (screenSize.height - 327) / 2, 625, 327);
	}

}
