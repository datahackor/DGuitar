// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilenameFilterAcceptExtension.java

package common;

import java.io.File;
import java.io.FilenameFilter;

// Referenced classes of package common:
//			LicenseString

public class FilenameFilterAcceptExtension
	implements FilenameFilter, LicenseString
{

	private String extension;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public FilenameFilterAcceptExtension(String aExtension)
	{
		if (aExtension != null)
		{
			extension = aExtension.toUpperCase();
			if (!aExtension.startsWith("."))
				extension = "." + extension;
		} else
		{
			throw new NullPointerException("extension is null");
		}
	}

	public boolean accept(File dir, String name)
	{
		boolean resp = false;
		if (name != null)
			resp = name.toUpperCase().endsWith(extension);
		return resp;
	}
}
