// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilenameFilterAcceptAll.java

package common;

import java.io.File;
import java.io.FilenameFilter;

// Referenced classes of package common:
//			LicenseString

public class FilenameFilterAcceptAll
	implements FilenameFilter, LicenseString
{

	public FilenameFilterAcceptAll()
	{
	}

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public boolean accept(File dir, String name)
	{
		return true;
	}
}
