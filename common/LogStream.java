// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogStream.java

package common;

import java.awt.Image;
import java.io.*;

// Referenced classes of package common:
//			LicenseString, LogStreamOptions, ScrollText

public class LogStream extends PrintStream
	implements LicenseString
{

	private ScrollText ST;
	private PrintWriter PW;
	private LogStreamOptions LSO;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public LogStream(OutputStream os, LogStreamOptions lso)
		throws FileNotFoundException
	{
		super(os);
		PW = null;
		ST = null;
		LSO = lso;
		if (lso == null)
			new NullPointerException("LogStreamOptions is null");
		if (LSO.getTypeOfLog() != 0)
		{
			if (LSO.getTypeOfLog() >= 2)
				windowInit(lso.getInitMsg());
			if (LSO.getTypeOfLog() <= 2 && LSO.getFilePath() != null)
			{
				PW = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LSO.getFilePath())));
				PW.println(lso.getInitMsg());
			}
		}
	}

	public short getTypeOfLog()
	{
		return LSO.getTypeOfLog();
	}

	public boolean isFileType()
	{
		return LSO.isFileType();
	}

	public boolean isWindowType()
	{
		return LSO.isWindowType();
	}

	public void switchTo(short type, String windowTitle, boolean isClosable, boolean doExit)
		throws FileNotFoundException
	{
		short prevType = LSO.getTypeOfLog();
		if (prevType == type)
			return;
		if (LogStreamOptions.isWindowType(prevType) && !LogStreamOptions.isWindowType(type))
		{
			closeWindow();
			ST = null;
		}
		if (LogStreamOptions.isFileType(prevType) && !LogStreamOptions.isFileType(type))
		{
			PW.close();
			PW = null;
		}
		LSO.setTypeOfLog(type);
		if (LSO.isWindowType() && !LogStreamOptions.isWindowType(prevType))
		{
			windowInit(LSO.getInitMsg());
			setTitle(windowTitle);
			setClosable(isClosable, doExit);
		}
		if (LSO.isFileType() && !LogStreamOptions.isFileType(prevType) && LSO.getFilePath() != null)
		{
			PW = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LSO.getFilePath())));
			PW.println(LSO.getInitMsg());
		}
	}

	private void windowInit(String initialMsg)
	{
		if (initialMsg != null)
			ST = new ScrollText(initialMsg);
		else
			ST = new ScrollText();
		ST.setEditable(false);
		ST.setDisabledColor();
		ST.setClosable(true, true);
		ST.addClearLog();
		ST.setVisible(true);
	}

	public void setTitle(String str)
	{
		if (LSO.getTypeOfLog() >= 2)
			ST.setTitle(str);
	}

	public void setClosable(boolean isClosable, boolean doExit)
	{
		if (LSO.getTypeOfLog() >= 2)
			ST.setClosable(isClosable, doExit);
	}

	public void println()
	{
		if (LSO.getTypeOfLog() >= 2)
		{
			ST.append("\n");
			ST.setVisible(true);
		}
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.println();
	}

	public void print(String s)
	{
		if (LSO.getTypeOfLog() >= 2)
			ST.append(s);
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.print(s);
		println();
	}

	public void println(String s)
	{
		print(s);
	}

	public void println(Object obj)
	{
		String aux = "null";
		if (obj != null)
			aux = obj.toString();
		println(aux);
	}

	public void print(Object obj)
	{
		println(obj);
	}

	public void log(String s)
	{
		if (LSO.getTypeOfLog() >= 2)
			ST.append(s + "\n");
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.println(s);
	}

	public void logNoNewLine(String s)
	{
		if (LSO.getTypeOfLog() >= 2)
			ST.append(s);
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.print(s);
	}

	public void close()
	{
		super.close();
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.close();
	}

	public void flush()
	{
		super.flush();
		if (LSO.getTypeOfLog() <= 2 && LSO.getTypeOfLog() != 0)
			PW.flush();
	}

	public void closeWindow()
	{
		if (ST != null)
		{
			ST.setVisible(false);
			ST.dispose();
		} else
		{
			print("Hidding window");
		}
	}

	public void setIconImage(Image imgIcon)
	{
		if (ST != null)
			ST.setIconImage(imgIcon);
	}
}
