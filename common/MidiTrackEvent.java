// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MidiTrackEvent.java

package common;

import javax.sound.midi.MidiEvent;

// Referenced classes of package common:
//			LicenseString, Midi

public class MidiTrackEvent
	implements LicenseString
{

	protected int track;
	protected int event;
	protected MidiEvent midiEvent;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public MidiTrackEvent(int Track, int Event, MidiEvent midiEvent)
	{
		track = Track;
		event = Event;
		this.midiEvent = midiEvent;
	}

	public String toString()
	{
		String res = "Track: " + track + ", " + "event" + ": " + event + ", " + Midi.MidiEventToString(midiEvent);
		return res;
	}

	public int getTrack()
	{
		return track;
	}

	public int getEvent()
	{
		return event;
	}

	public MidiEvent getMidiEvent()
	{
		return midiEvent;
	}

	public boolean equals(Object obj)
	{
		boolean res = false;
		if (obj != null && obj.getClass().isInstance(this))
		{
			MidiTrackEvent MTE = (MidiTrackEvent)obj;
			res = MTE.event == event && MTE.track == track;
		}
		return res;
	}
}
