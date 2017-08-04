// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SwingWorker.java

package common;

import javax.swing.SwingUtilities;

public abstract class SwingWorker
{
	private static class ThreadVar
	{

		private Thread thread;

		synchronized Thread get()
		{
			return thread;
		}

		synchronized void clear()
		{
			thread = null;
		}

		ThreadVar(Thread t)
		{
			thread = t;
		}
	}


	private Object value;
	private ThreadVar threadVar;

	public String getLicenseString()
	{
		String res = "";
		res = res + "SwingWorder.java is released withe SUN Code sample License";
		res = res + "\n\n";
		res = res + "Copyright 1994-2005 Sun Microsystems, Inc. All Rights Reserved.";
		res = res + "\n\n";
		res = res + "see SwingWorker.java file for more details.";
		return res;
	}

	protected synchronized Object getValue()
	{
		return value;
	}

	private synchronized void setValue(Object x)
	{
		value = x;
	}

	public abstract Object construct();

	public void finished()
	{
	}

	public void interrupt()
	{
		Thread t = threadVar.get();
		if (t != null)
			t.interrupt();
		threadVar.clear();
	}

	public Object get()
	{
		do
		{
			Thread t = threadVar.get();
			if (t == null)
				return getValue();
			try
			{
				t.join();
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				return null;
			}
		} while (true);
	}

	public SwingWorker()
	{
		final Runnable doFinished = new Runnable() {

			public void run()
			{
				finished();
			}

		};
		Runnable doConstruct = new Runnable() {

			public void run()
			{
				try
				{
					setValue(construct());
				}
				finally
				{
					threadVar.clear();
				}
				SwingUtilities.invokeLater(doFinished);
				return;
			}

		};
		Thread t = new Thread(doConstruct);
		threadVar = new ThreadVar(t);
	}

	public void start()
	{
		Thread t = threadVar.get();
		if (t != null)
			t.start();
	}


}
