
package common;

import java.io.File;
//import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;

// Referenced classes of package common:
//			LicenseString, FolderIteratorInfo, FileFolderFilter, FilenameFilterAcceptAll, 
//			FolderIteratorListener

public class FolderIterator
	implements Runnable, LicenseString
{

	private FolderIteratorInfo FII;
	private FolderIteratorListener folderIteratorListener;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public FolderIterator()
	{
		folderIteratorListener = null;
		commonConstructor();
		FII.setStartigPath(new File(""));
	}

	public FolderIterator(FolderIteratorListener aFolderIteratorListener)
	{
		setFolderIteratorListener(aFolderIteratorListener);
		commonConstructor();
		FII.setStartigPath(new File(""));
	}

	public FolderIterator(String pathName)
	{
		folderIteratorListener = null;
		setStartingPath(pathName);
		commonConstructor();
	}

	public FolderIterator(File path)
	{
		folderIteratorListener = null;
		setStartingPath(path);
		commonConstructor();
	}

	private void commonConstructor()
	{
		FII = new FolderIteratorInfo();
		FII.setFileFolderFilter(new FileFolderFilter(new FilenameFilterAcceptAll()));
		FII.setCountFolders(0);
		FII.setCountFiles(0);
		FII.currentDeep = 0;
		setHowDeep(0);
	}

	public void setFolderIteratorListener(FolderIteratorListener aFolderIteratorListener)
	{
		if (aFolderIteratorListener != null)
			folderIteratorListener = aFolderIteratorListener;
		else
			throw new NullPointerException("FolderIteratorListener is null");
	}

	public void setStartingPath(String pathName)
	{
		File path = new File(pathName);
		setStartingPath(path);
	}

	public void setStartingPath(File path)
	{
		if (path != null && path.isDirectory())
			FII.setStartigPath(path);
	}

	public void setHowDeep(int howDeep)
	{
		if (howDeep > 0)
			FII.setHowDeep(howDeep);
	}

	public int getHowDeep()
	{
		return FII.getHowDeep();
	}

	private Vector<File> stringsToFiles(File path, String list[])
	{
		Vector<File> files = null;
		if (list != null)
		{
			files = new Vector<File>(0, 1);
			//int j = 0;
			for (int i = 0; i < list.length; i++)
			{
				File f = new File(path + File.separator + list[i]);
				files.add(f);
				//j++;
			}

		}
		return files;
	}

	private void runImpl(File path)
	{
		String list[];
		if (FII.getFileFolderFilter() != null)
			list = path.list(FII.fileFolderFilter);
		else
			list = path.list();
		if (list == null)
		{
			System.err.println("path >" + path + "< is not a Directory !!");
		} else
		{
			Vector<File> files = stringsToFiles(path, list);
			if (files != null)
			{
				for (Iterator<File> it = files.iterator(); it.hasNext();)
				{
					File f = (File)it.next();
					if (!f.isDirectory())
						actionForFile(f);
					else
						actionForFolder(f, path);
				}

			}
		}
	}

	private void actionForFile(File file)
	{
		FII.countFiles++;
		if (folderIteratorListener != null)
			folderIteratorListener.actionForFile(file, FII);
	}

	private void actionForFolder(File folder, File path)
	{
		FII.countFolders++;
		if (folderIteratorListener != null)
			folderIteratorListener.actionForFolder(folder, path, FII);
		if (FII.currentDeep < getHowDeep())
		{
			FII.currentDeep++;
			runImpl(folder);
			FII.currentDeep--;
		} else
		{
			String aux = "path >" + path;
			aux = aux + "< had more folders but \"howDeep\" prevented from descending more";
			System.err.println(aux);
		}
	}

	private void actionRunInit()
	{
		if (folderIteratorListener != null)
			folderIteratorListener.actionRunInit(FII);
	}

	private void actionRunFinished()
	{
		if (folderIteratorListener != null)
			folderIteratorListener.actionRunFinished(FII);
	}

	public final void run()
	{
		actionRunInit();
		runImpl(FII.getStartigPath());
		actionRunFinished();
	}

	public FolderIteratorListener getFolderIteratorListener()
	{
		return folderIteratorListener;
	}

	public void setFileFolderFilter(FileFolderFilter filter)
	{
		if (filter != null)
			FII.setFileFolderFilter(filter);
	}
}
