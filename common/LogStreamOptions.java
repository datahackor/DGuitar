// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogStreamOptions.java

package common;


// Referenced classes of package common:
//			LicenseString

public class LogStreamOptions
	implements LicenseString
{

	public static final short NO_LOG = 0;
	public static final short LOG_TO_FILE_ONLY = 1;
	public static final short LOG_TO_FILE_AND_WINDOW = 2;
	public static final short LOG_TO_WINDOW_ONLY = 3;
	private short typeOfLog;
	private String initMsg;
	private String filePath;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	private void commonConstructor(short type, String initialMessage)
	{
		typeOfLog = type;
		initMsg = initialMessage;
	}

	public LogStreamOptions(short type, String initialMessage)
	{
		commonConstructor(type, initialMessage);
	}

	public LogStreamOptions(short type, String initialMessage, String path)
	{
		commonConstructor(type, initialMessage);
		setFilePath(path);
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getInitMsg()
	{
		return initMsg;
	}

	public void setInitMsg(String initMsg)
	{
		this.initMsg = initMsg;
	}

	public short getTypeOfLog()
	{
		return typeOfLog;
	}

	public void setTypeOfLog(short typeOfLog)
	{
		this.typeOfLog = typeOfLog;
	}

	public boolean isFileType()
	{
		return typeOfLog == 2 || typeOfLog == 1;
	}

	public static boolean isFileType(short type)
	{
		return type == 2 || type == 1;
	}

	public boolean isWindowType()
	{
		return typeOfLog == 2 || typeOfLog == 3;
	}

	public static boolean isWindowType(short type)
	{
		return type == 2 || type == 3;
	}
}
