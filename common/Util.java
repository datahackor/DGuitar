// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package common;

import java.awt.*;
import java.io.*;
import javax.swing.*;

// Referenced classes of package common:
//			LicenseString, UtilErrors

public class Util
	implements LicenseString
{

	public static final String internetURLs[] = {
		"http://", "ftp://"
	};
	private static UtilErrors error;

	public Util()
	{
	}

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public static int nIndexOf(String str, String strToSearch, int whichOcurrence)
	{
		int index = -1;
		if (whichOcurrence >= 1)
		{
			int prevIndex = -1;
			int currentIndex = -1;
			int count = 1;
			boolean found;
			do
			{
				prevIndex = currentIndex;
				currentIndex = str.indexOf(strToSearch, currentIndex + 1);
				found = currentIndex >= 0;
				count++;
			} while (found && count <= whichOcurrence);
			if (!found && count > whichOcurrence && prevIndex >= 0)
				index = -1 * prevIndex;
			index = currentIndex;
		}
		return index;
	}

	public static int indexInternetURL(String str)
	{
		int res = -1;
		if (str != null && str.length() > 0)
		{
			int which = 0;
			do
			{
				String internetStr = internetURLs[which];
				if (str.startsWith(internetStr))
					res = internetStr.length();
				else
					which++;
			} while (which < internetURLs.length && res == -1);
		}
		return res;
	}

	public static String compactAndReadableURL(String url, int maxLength)
	{
		String res = "";
		String urlOrig = new String(url);
		int length = urlOrig.length();
		if (maxLength < length)
		{
			if (maxLength < 10)
				maxLength = 10;
			int firstCut = indexInternetURL(urlOrig);
			int ocurrence;
			if (firstCut == -1)
				ocurrence = 2;
			else
				ocurrence = 3;
			firstCut = nIndexOf(urlOrig, File.separator, ocurrence) + 1;
			res = urlOrig.substring(0, firstCut) + "...";
			res = res + urlOrig.substring(length - maxLength - firstCut - 3);
		} else
		{
			res = urlOrig;
		}
		return res;
	}

	public static boolean validFile(File f)
	{
		error.setDetails("File passed to method validFile()");
		boolean valid = false;
		if (f == null)
		{
			error = UtilErrors.PARAM_NULL;
		} else
		{
			if (!f.exists())
				error = UtilErrors.DOES_NOT_EXIST;
			else
			if (!f.isFile())
				error = UtilErrors.NOT_A_FILE;
			else
			if (!f.canRead())
				error = UtilErrors.NOT_READABLE;
			else
				valid = true;
			error.setDetails(f.getAbsolutePath());
		}
		return valid;
	}

	public static UtilErrors getError()
	{
		return error;
	}

	public static int pow2(int x)
	{
		int res = 1;
		for (int i = 0; i < x; i++)
			res *= 2;

		return res;
	}

	public static String tabs(int n)
	{
		int i = 0;
		String res = "";
		for (i = 0; i < n; i++)
			res = res + "\t";

		return res;
	}

	public static void showDialogOk(Component parent, String title, Object message)
	{
		JOptionPane.showMessageDialog(parent, message, title, 1);
	}

	public static JDialog createDialog(Frame parent, Object msgORcomp, String title, boolean modal)
	{
		JDialog jdialog = new JDialog(parent, title, modal);
		Container content = jdialog.getContentPane();
		content.setLayout(new BorderLayout());
		Component comp;
		if (msgORcomp instanceof Component)
		{
			comp = (Component)msgORcomp;
		} else
		{
			if (!(msgORcomp instanceof String))
				msgORcomp = "msgORcomp must be String or Component (or subclass of Component) ";
			JLabel jlabel = new JLabel((String)msgORcomp);
			jlabel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, jlabel.getBackground()));
			comp = jlabel;
		}
		content.add(comp, "Center");
		jdialog.pack();
		centerComponentToParent(jdialog, parent);
		jdialog.setResizable(false);
		return jdialog;
	}

	public static String closeIS(InputStream IS)
	{
		String res = null;
		if (IS != null)
			try
			{
				IS.close();
			}
			catch (IOException IOE)
			{
				res = IOE.toString();
			}
		return res;
	}

	public static void waitFor(long ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException interruptedexception) { }
	}

	public static String fieldFor(String fieldName, Object object, Boolean firstField)
	{
		String resp = "";
		if (object != null)
		{
			Boolean booleanField = new Boolean(false);
			if (!object.getClass().isInstance(booleanField))
			{
				resp = fieldName == null ? "" : fieldName + ": ";
				if (firstField.booleanValue())
					firstField = new Boolean(false);
				else
					resp = ", " + resp;
				resp = resp + object;
			} else
			{
				booleanField = (Boolean)object;
				if (booleanField.booleanValue())
				{
					firstField = new Boolean(false);
					resp = fieldName;
				}
			}
		}
		return resp;
	}

	public static String fieldFor(String fieldName, boolean aFlag, Boolean firstField)
	{
		return fieldFor(fieldName, new Boolean(aFlag), firstField);
	}

	public static void drawLine(Graphics g, Point a, Point b)
	{
		g.drawLine(a.x, a.y, b.x, b.y);
	}

	public static Point centerOfComponent(Component c)
	{
		Point p = null;
		if (c != null)
		{
			p = new Point();
			p.x = c.getX() + c.getWidth() / 2;
			p.y = c.getY() + c.getHeight() / 2;
		}
		return p;
	}

	public static void centerComponentToParent(Component c, Component Parent)
	{
		centerComponent(c, centerOfComponent(Parent), c.getSize());
	}

	public static void centerComponent(Component c, Point p, Dimension dim)
	{
		if (c != null && p != null && dim != null)
			c.setBounds(p.x - dim.width / 2, p.y - dim.height / 2, dim.width, dim.height);
	}

	public static void drawString(Graphics g, String text, Point p)
	{
		g.drawString(text, p.x, p.y);
	}

	public static Point centerString(Graphics g, String text, int x, int y)
	{
		Point p = null;
		if (g != null)
		{
			int dx = g.getFontMetrics().stringWidth(text);
			int fontAscent = g.getFontMetrics().getAscent();
			p = new Point();
			p.x = (x - dx / 2) + 1;
			p.y = y - fontAscent;
		}
		return p;
	}

	static 
	{
		error = UtilErrors.NO_ERROR;
	}
}
