// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MidiEventComparator.java

package common;

import java.util.Comparator;
import javax.sound.midi.MidiEvent;

// Referenced classes of package common:
//			LicenseString

public class MidiEventComparator
	implements Comparator<Object>, LicenseString
{

	public MidiEventComparator()
	{
	}

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public int compare(Object arg0, Object arg1)
	{
		int resp = 0;
		long t0 = 0L;
		long t1 = 0L;
		if (arg0 != null && arg1 != null)
		{
			MidiEvent m0 = new MidiEvent(null, 0L);
			if (arg0.getClass().isInstance(m0) && arg1.getClass().isInstance(m0))
			{
				m0 = (MidiEvent)arg0;
				MidiEvent m1 = (MidiEvent)arg1;
				t0 = m0.getTick();
				t1 = m1.getTick();
			}
			if (t0 < t1)
				resp = -1;
			if (t0 > t1)
				resp = 1;
		}
		return resp;
	}
}
