// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UtilErrors.java

package common;


// Referenced classes of package common:
//			LicenseString

public class UtilErrors
	implements LicenseString
{

	private int errorCode;
	private String detail;
	public static final UtilErrors NO_ERROR;
	public static final UtilErrors UNKNOWN_ERROR;
	public static final UtilErrors PARAM_NULL;
	public static final UtilErrors DOES_NOT_EXIST;
	public static final UtilErrors NOT_A_FILE;
	public static final UtilErrors NOT_READABLE;
	private static final UtilErrors errors[];
	private String errorStrings[] = {
		"NO ERROR", "UNKNOWN ERROR", "PARAMETER IS NULL", "DOES NOT EXIST", "IS NOT A FILE", "IS NOT READABLE"
	};

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public UtilErrors(int error)
	{
		errorCode = error;
		detail = "NO MORE DETAILS - ";
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public String toString()
	{
		String resp = errorStrings[1];
		if (errorCode == 0 || errorCode > 1 && errorCode < errors.length)
			resp = errorStrings[errorCode];
		resp = detail + " " + resp;
		return resp;
	}

	public void setDetails(String s)
	{
		detail = s;
	}

	static 
	{
		NO_ERROR = new UtilErrors(0);
		UNKNOWN_ERROR = new UtilErrors(1);
		PARAM_NULL = new UtilErrors(2);
		DOES_NOT_EXIST = new UtilErrors(3);
		NOT_A_FILE = new UtilErrors(4);
		NOT_READABLE = new UtilErrors(5);
		errors = (new UtilErrors[] {
			NO_ERROR, UNKNOWN_ERROR, PARAM_NULL, DOES_NOT_EXIST, NOT_A_FILE, NOT_READABLE
		});
	}
}
