// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FolderIteratorListener.java

package common;

import java.io.File;

// Referenced classes of package common:
//			FolderIteratorInfo

public interface FolderIteratorListener
{

	public abstract void actionForFile(File file, FolderIteratorInfo folderiteratorinfo);

	public abstract void actionForFolder(File file, File file1, FolderIteratorInfo folderiteratorinfo);

	public abstract void actionRunInit(FolderIteratorInfo folderiteratorinfo);

	public abstract void actionRunFinished(FolderIteratorInfo folderiteratorinfo);
}
