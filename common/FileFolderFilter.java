// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileFolderFilter.java

package common;

import java.io.File;
import java.io.FilenameFilter;

// Referenced classes of package common:
//			LicenseString

public class FileFolderFilter
	implements FilenameFilter, LicenseString
{

	private FilenameFilter filenameFilter;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public FileFolderFilter(FilenameFilter aFileNameFilter)
	{
		if (aFileNameFilter != null)
			filenameFilter = aFileNameFilter;
		else
			throw new NullPointerException("aFilenameFilter is null");
	}

	public final boolean accept(File dir, String name)
	{
		boolean resp = false;
		if (dir != null && name != null)
		{
			File f = new File(dir.getAbsolutePath() + File.separator + name);
			resp = f.isDirectory();
			resp = resp || filenameFilter.accept(null, name);
		}
		return resp;
	}

	public void setFilenameFilter(FilenameFilter aFilenameFilter)
	{
		if (aFilenameFilter != null)
			filenameFilter = aFilenameFilter;
	}

	public FilenameFilter getFilenameFilter()
	{
		return filenameFilter;
	}
}
