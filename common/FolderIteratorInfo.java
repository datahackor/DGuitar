// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FolderIteratorInfo.java

package common;

import java.io.File;
import java.io.FilenameFilter;

// Referenced classes of package common:
//			LicenseString, FileFolderFilter

public class FolderIteratorInfo
	implements LicenseString
{

	private File startigPath;
	protected FileFolderFilter fileFolderFilter;
	protected int countFolders;
	protected int countFiles;
	private int howDeep;
	protected int currentDeep;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public FolderIteratorInfo()
	{
	}

	protected void setStartigPath(File startigPath)
	{
		this.startigPath = startigPath;
	}

	public File getStartigPath()
	{
		return startigPath;
	}

	protected void setCountFolders(int countFolders)
	{
		this.countFolders = countFolders;
	}

	public int getCountFolders()
	{
		return countFolders;
	}

	protected void setCountFiles(int countFiles)
	{
		this.countFiles = countFiles;
	}

	public int getCountFiles()
	{
		return countFiles;
	}

	protected void setFileFolderFilter(FileFolderFilter aFileFolderFilter)
	{
		fileFolderFilter = aFileFolderFilter;
	}

	public FilenameFilter getFileFolderFilter()
	{
		return fileFolderFilter;
	}

	public FilenameFilter getFilenameFilter()
	{
		return fileFolderFilter.getFilenameFilter();
	}

	public int getCurrentDeep()
	{
		return currentDeep;
	}

	protected void setHowDeep(int howDeep)
	{
		this.howDeep = howDeep;
	}

	public int getHowDeep()
	{
		return howDeep;
	}
}
