// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileLoader.java

package common;

import java.io.*;

// Referenced classes of package common:
//			LicenseString, SwingWorker

public class FileLoader
	implements LicenseString
{
	class Loader
	{

		String errorStr;

		public Loader(File file)
		{
			byte mem[] = (byte[])null;
			FileInputStream fis = null;
			try
			{
				fis = new FileInputStream(file);
				long size = file.length();
				if (size > 0L)
				{
					mem = new byte[BUFFER];
					resp = new byte[(int)size];
					int j = 0;
					int leyo;
					do
					{
						leyo = fis.read(mem);
						if (leyo > 0)
						{
							for (int i = 0; i < leyo && !canceled; i++)
							{
								resp[j] = mem[i];
								j++;
								current = j;
								statMessage = "Loading byte " + j + "/" + size;
							}

						}
					} while (leyo > 0);
					if (!canceled)
					{
						done = true;
						current = lengthOfTask;
					}
				}
			}
			catch (IOException e)
			{
				errorStr = e.toString();
				success = false;
			}
			finally
			{
				if (fis != null)
					try
					{
						fis.close();
						success = true;
					}
					catch (IOException e)
					{
						errorStr = e.toString();
					}
			}
			return;
		}
	}


	public int sizeLoader;
	public int BUFFER;
	public boolean success;
	public String errorCode;
	public byte resp[];
	private File file;
	private int lengthOfTask;
	private int current;
	private boolean done;
	private boolean canceled;
	private String statMessage;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public FileLoader(String fileName)
	{
		BUFFER = 512;
		success = false;
		errorCode = "NO ERROR";
		resp = null;
		current = 0;
		done = false;
		canceled = false;
		file = new File(fileName);
		if (file != null && file.isFile())
			sizeLoader = lengthOfTask = (int)file.length();
		else
			errorCode = "FILE OBJECT COULD NOT BE CREATED";
	}

	public FileLoader(File userFile)
	{
		BUFFER = 512;
		success = false;
		errorCode = "NO ERROR";
		resp = null;
		current = 0;
		done = false;
		canceled = false;
		file = userFile;
		if (file != null && file.isFile())
			sizeLoader = lengthOfTask = (int)file.length();
		else
			errorCode = "FILE OBJECT COULD NOT BE CREATED";
	}

	public void loadFile()
	{
		SwingWorker worker = new SwingWorker() {

			public Object construct()
			{
				Object respB = null;
				current = 0;
				done = false;
				canceled = false;
				statMessage = null;
				respB = new Loader(file);
				return respB;
			}

		};
		worker.start();
	}

	public int getLengthOfTask()
	{
		return lengthOfTask;
	}

	public int getCurrent()
	{
		return current;
	}

	public void stop()
	{
		canceled = true;
		statMessage = null;
	}

	public boolean isDone()
	{
		return done;
	}

	public String getMessage()
	{
		return statMessage;
	}







}
