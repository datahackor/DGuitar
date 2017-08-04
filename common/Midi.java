
package common;

import java.io.File;
import java.io.IOException;
//import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

// Referenced classes of package common:
//			LicenseString, Util, MidiEventComparator, MidiTrackEvent

public class Midi
	implements LicenseString
{
	class timerListener
		implements java.awt.event.ActionListener
	{

		long eventClick;
		MidiEvent ME;
		boolean processed;
		long currentTime;
		Iterator<MidiEvent> it;
		long eventTime;
		double millisecondsPerPulse;
		int delay;

		public void actionPerformed(java.awt.event.ActionEvent e)
		{
			if (it.hasNext())
			{
				if (processed)
				{
					ME = (MidiEvent)it.next();
					eventClick = ME.getTick();
					eventTime = (long)((double)eventClick * millisecondsPerPulse);
				}
				if (eventTime <= currentTime)
				{
					currentReceiver.send(ME.getMessage(), -1L);
					processed = true;
				} else
				{
					processed = false;
				}
				currentTime += delay;
			}
		}

		public timerListener(List<MidiEvent> eventList, double MSPP, int Delay)
		{
			processed = true;
			currentTime = 0L;
			it = eventList.iterator();
			millisecondsPerPulse = MSPP;
			delay = Delay;
		}
	}


	private Sequencer sequencer;
	protected Sequence sequence;
	Synthesizer synthesizer;
	Instrument instruments[];
	int channel;
	ShortMessage msg;
	int tick;
	int count;
	static javax.sound.midi.MidiDevice.Info allMidiDevices[] = null;
	int currentDeviceIndex;
	Transmitter transmitter;
	MidiDevice currentDevice;
	private List<MidiEvent> eventList;
	Receiver currentReceiver;
	Track track;
	double ppq;
	double tempo;
	boolean deviceAlreadyOpened;
	boolean playing;
	public int DELTA;
	public final int VELOCITY_ON = 100;
	public final int VELOCITY_OFF = 0;
	//private static final boolean testing = false;
	private static int midiCommandsStatus[] = {
		254, 208, 251, 176, 247, 241, 128, 144, 224, 160, 
		192, 242, 243, 250, 252, 255, 248, 246
	};
	private static String midiCommandsStatusStrings[] = {
		"ACTIVE_SENSING", "CHANNEL_PRESSURE", "CONTINUE", "CONTROL_CHANGE", "END_OF_EXCLUSIVE", "MIDI_TIME_CODE", "NOTE_OFF", "NOTE_ON", "PITCH_BEND", "POLY_PRESSURE", 
		"PROGRAM_CHANGE", "SONG_POSITION_POINTER", "SONG_SELECT", "START", "STOP", "SYSTEM_RESET", "TIMING_CLOCK", "TUNE_REQUEST"
	};
	private String errorMsg;
	private String doesNotHaveAnyReceivers;

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public Midi(String msgDoesNotHaveAnyReceivers)
	{
		channel = 0;
		DELTA = 1;
		errorMsg = null;
		doesNotHaveAnyReceivers = msgDoesNotHaveAnyReceivers;
		playing = false;
		tick = 0;
		currentDevice = null;
		currentDeviceIndex = -1;
		currentReceiver = null;
		ppq = 0.0D;
		eventList = null;
		transmitter = null;
		getMidiDevices();
	}

	public int getCurrentDeviceIndex()
	{
		return currentDeviceIndex;
	}

	public MidiDevice getCurrentDevice()
	{
		return currentDevice;
	}

	public boolean isValidDeviceIndex(int i)
	{
		boolean resp = false;
		if (allMidiDevices != null)
			resp = i >= 0 && i < allMidiDevices.length;
		return resp;
	}

	public boolean setCurrentDevice(int i, String msgDoesNotHaveAnyReceivers)
	{
		boolean resp = false;
		if (isValidDeviceIndex(i))
			try
			{
				String deviceName = allMidiDevices[i].getName();
				MidiDevice testDevice = MidiSystem.getMidiDevice(allMidiDevices[i]);
				int numReceivers = testDevice.getMaxReceivers();
				if (numReceivers != 0)
				{
					currentDevice = testDevice;
					currentDeviceIndex = i;
					currentReceiver = currentDevice.getReceiver();
					resp = true;
				} else
				{
					errorMsg = "Midi Device:" + deviceName + " ";
					if (msgDoesNotHaveAnyReceivers != null)
						errorMsg += msgDoesNotHaveAnyReceivers;
					else
						errorMsg += "does not have any receivers, try with a different one";
					errorMsg += ": 0 -" + allMidiDevices.length;
				}
			}
			catch (MidiUnavailableException e)
			{
				e.printStackTrace();
			}
		return resp;
	}

	public int getNumberOfMidiDevices()
	{
		if (allMidiDevices == null)
			allMidiDevices = MidiSystem.getMidiDeviceInfo();
		return allMidiDevices.length;
	}

	public javax.sound.midi.MidiDevice.Info[] getMidiDevices()
	{
		if (allMidiDevices == null)
			allMidiDevices = MidiSystem.getMidiDeviceInfo();
		return allMidiDevices;
	}

	public boolean play()
	{
		boolean success = false;
		success = standardPlay();
		return success;
	}

	private boolean openSequencer()
	{
		boolean success = false;
		try
		{
			if (sequencer == null)
				sequencer = MidiSystem.getSequencer();
			if (sequencer == null)
			{
				System.err.println("getSequencer() failed!");
				success = false;
			} else
			if (!sequencer.isOpen())
			{
				sequencer.open();
				success = true;
			}
		}
		catch (MidiUnavailableException MUE)
		{
			MUE.printStackTrace();
		}
		return success;
	}

	public void prepareToPlay()
	{
		openSequencer();
		openSynthesizer();
		if (transmitter == null)
			try
			{
				transmitter = sequencer.getTransmitter();
				transmitter.setReceiver(synthesizer.getReceiver());
			}
			catch (MidiUnavailableException e)
			{
				e.printStackTrace();
			}
	}

	private boolean standardPlay()
	{
		boolean success = true;
		try
		{
			prepareToPlay();
			sequencer.setSequence(sequence);
			sequencer.start();
			playing = true;
			while (sequencer.isRunning()) ;
			playing = false;
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			success = false;
		}
		return success;
	}
/* pumo 2017-7-22 commented
	private boolean developmentPlay()
	{
		boolean success = false;
		if (currentDevice == null)
			setCurrentDevice(0, doesNotHaveAnyReceivers);
		if (currentReceiver != null)
		{
			if (ppq == 0.0D)
				ppq = 16D;
			deviceAlreadyOpened = currentDevice.isOpen();
			if (!deviceAlreadyOpened)
				try
				{
					currentDevice.open();
				}
				catch (MidiUnavailableException e1)
				{
					e1.printStackTrace();
				}
			playing = true;
			tempo = 120D;
			double millisecondsPerPulse = 60000D / (tempo * ppq);
			Iterator<MidiEvent> it = eventList.iterator();
			long startTime = System.currentTimeMillis();
			while (playing && it.hasNext()) 
			{
				MidiEvent e = (MidiEvent)it.next();
				long eventClick = e.getTick();
				long eventOffset = (long)((double)eventClick * millisecondsPerPulse);
				long currentTime = System.currentTimeMillis() - startTime;
				if (currentTime < eventOffset)
					try
					{
						Thread.sleep(eventOffset - currentTime);
					}
					catch (InterruptedException e1)
					{
						e1.printStackTrace();
					}
				try
				{
					currentReceiver.send(e.getMessage(), -1L);
				}
				catch (IllegalStateException ISE)
				{
					Util.showDialogOk(null, "IllegalStateException", ISE.getLocalizedMessage());
					playing = false;
				}
			}
			success = true;
			stop();
		} else
		{
			System.err.println("current Devices is null");
		}
		return success;
	}
*/
	public void stop()
	{
		if (sequencer != null && sequencer.isRunning())
			sequencer.stop();
	}

	private boolean openSynthesizer()
	{
		boolean success = false;
		try
		{
			if (synthesizer == null)
				synthesizer = MidiSystem.getSynthesizer();
			if (synthesizer == null)
			{
				System.err.println("getSynthesizer() failed!");
				success = false;
			} else
			if (!synthesizer.isOpen())
				synthesizer.open();
		}
		catch (MidiUnavailableException MUE)
		{
			MUE.printStackTrace();
		}
		return success;
	}

	private boolean loadInstruments()
	{
		boolean success = false;
		openSynthesizer();
		Soundbank sb = synthesizer.getDefaultSoundbank();
		if (sb != null)
		{
			instruments = synthesizer.getDefaultSoundbank().getInstruments();
			success = synthesizer.loadInstrument(instruments[23]);
			if (!success)
			{
				System.err.println("loadInstrument() failed!");
				return success;
			}
		}
		synthesizer.close();
		return success;
	}

	public String[] getInstrumentStrings()
	{
		String res[] = (String[])null;
		if (instruments == null)
			loadInstruments();
		if (instruments != null)
		{
			int max = instruments.length;
			res = new String[max];
			for (int i = 0; i < instruments.length; i++)
			{
				String aux = instruments[i].toString();
				int pos = aux.indexOf("Instrument");
				res[i] = new String(aux.substring(pos + "Instrument".length()));
			}

		}
		return res;
	}

	public boolean setSequencePPQ(int value)
	{
		boolean success = false;
		if (value > 0)
			try
			{
				sequence = new Sequence(0.0F, 16, 1);
				success = true;
			}
			catch (InvalidMidiDataException e)
			{
				e.printStackTrace();
			}
		return success;
	}

	public boolean setInstrument(int instrumentNumber)
	{
		boolean success = true;
		tick = 0;
		if (sequence == null)
			System.err.println("Sequence is null, make sure you call setSequencePPQ or loadMID");
		else
			try
			{
				msg = new ShortMessage();
				channel = instrumentNumber / 128;
				int inst = instrumentNumber % 128;
				msg.setMessage(192, channel, inst, 0);
				MidiEvent event = new MidiEvent(msg, 0L);
				track = sequence.createTrack();
				track.add(event);
			}
			catch (InvalidMidiDataException e)
			{
				e.printStackTrace();
				success = false;
			}
		return success;
	}

	public boolean addNote(int note)
	{
		boolean success = true;
		try
		{
			msg = new ShortMessage();
			msg.setMessage(144, channel, note, 100);
			MidiEvent event = new MidiEvent(msg, tick);
			track.add(event);
			msg = new ShortMessage();
			msg.setMessage(128, channel, note, 0);
			event = new MidiEvent(msg, tick + DELTA);
			track.add(event);
			tick += DELTA;
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	public boolean loadMID(String song)
	{
		boolean success = false;
		try
		{
			File myMidiFile = new File(song);
			MidiFileFormat mff = MidiSystem.getMidiFileFormat(myMidiFile);
			ppq = mff.getResolution();
			sequence = MidiSystem.getSequence(myMidiFile);
			prepare();
			success = true;
		}
		catch (IOException IOE)
		{
			IOE.printStackTrace();
		}
		catch (InvalidMidiDataException e)
		{
			e.printStackTrace();
		}
		return success;
	}

	public boolean selectFirstAvailableMidiDevice()
	{
		int j = 0;
		boolean success;
		for (success = false; !success && j < getNumberOfMidiDevices(); j++)
			success = setCurrentDevice(j, doesNotHaveAnyReceivers);

		return success;
	}

	public int nextDeviceIndex(int i)
	{
		int j = i + 1;
		if (j >= getNumberOfMidiDevices())
			j = 0;
		return j;
	}

	public boolean selectNextAvailableMidiDevice()
	{
		int orig = currentDeviceIndex;
		int j = nextDeviceIndex(currentDeviceIndex);
		boolean success;
		for (success = false; !success && j != orig;)
		{
			success = setCurrentDevice(j, doesNotHaveAnyReceivers);
			if (!success)
				j = nextDeviceIndex(j);
		}

		return success;
	}

	public String getCurrentDeviceName()
	{
		return getCurrentDevice().getDeviceInfo().getName();
	}

	public String getErrorMsg()
	{
		return errorMsg;
	}

	public String deviceMessage(String midiDevice, String isNowPreferredMidi)
	{
		String res;
		if (midiDevice != null)
			res = midiDevice;
		else
			res = "Midi Device";
		res = res + ":" + getCurrentDeviceName() + " ";
		if (isNowPreferredMidi != null)
			res = res + isNowPreferredMidi;
		else
			res = res + "is now the preferred device";
		return res;
	}

	private void prepare()
	{
		Track tracks[] = sequence.getTracks();
		int sizeNeeded = 0;
		for (int t = 0; t < tracks.length; t++)
			sizeNeeded += tracks[t].size();

		eventList = new ArrayList<MidiEvent>(sizeNeeded);
		for (int t = 0; t < tracks.length; t++)
		{
			Track trackAux = tracks[t];
			int size = trackAux.size();
			for (int j = 0; j < size; j++)
			{
				MidiEvent e = trackAux.get(j);
				eventList.add(e);
			}

		}

		Collections.sort(eventList, new MidiEventComparator());
	}

	private static String CommandStatusToString(int value)
	{
		String res = "UNKNOWN (" + value + ")";
		boolean match = false;
		for (int i = 0; !match && i < midiCommandsStatus.length; i++)
		{
			match = value == midiCommandsStatus[i];
			if (match)
				res = midiCommandsStatusStrings[i] + "(" + value + ")";
		}

		return res;
	}

	public static int convert(byte b)
	{
		return b & 0xff;
	}

	private static int midiMessageLength(int status)
	{
		switch (status & 0xf0)
		{
		case 192: 
		case 208: 
			return 2;

		case 240: 
			if (status == 243)
				return 2;
			if (status >= 246)
				return 1;
			break;
		}
		return 3;
	}

	private static String MidiMessageToString(MidiMessage MM)
	{
		int status = MM.getStatus();
		byte msg[];
		int length;
		if (MM instanceof ShortMessage)
		{
			ShortMessage sm = (ShortMessage)MM;
			length = midiMessageLength(status);
			msg = new byte[3];
			msg[0] = (byte)status;
			if (length > 1)
				msg[1] = (byte)sm.getData1();
			if (length > 2)
				msg[2] = (byte)sm.getData2();
		} else
		{
			msg = MM.getMessage();
			length = MM.getLength();
		}
		String res = "";
		if (status == 255)
		{
			res = res + "Meta message = {";
			for (int i = 0; i < length; i++)
			{
				res = res + convert(msg[i]);
				if (i < length - 1)
					res = res + ",";
			}

			res = res + " }";
		} else
		if (length == 1)
		{
			res = res + "System Real-Time message = { ";
			res = res + CommandStatusToString(status);
			res = res + " }";
		} else
		if (length == 2 || length == 3)
		{
			res = res + "Channel Message = { ";
			int cmd = status & 0xf0;
			res = res + "Command: " + CommandStatusToString(cmd);
			int chn = status & 0xf;
			res = res + ", Channel: " + chn;
			int data1 = convert(msg[1]);
			int data2 = -1;
			if (length == 3)
				data2 = convert(msg[2]);
			if (cmd == 144 || cmd == 128)
			{
				res = res + ", Note: " + data1;
				res = res + ", Velocity: " + data2;
			} else
			{
				res = res + ", Data1: " + data1;
				if (length == 3)
					res = res + ", Data2: " + data2;
			}
			res = res + " }";
		} else
		{
			res = res + "Meta message = {";
			for (int i = 0; i < length; i++)
			{
				res = res + convert(msg[i]);
				if (i < length - 1)
					res = res + ",";
			}

			res = res + " }";
		}
		return res;
	}

	public static String MidiEventToString(MidiEvent ME)
	{
		String res = "";
		res = res + "Tick: " + ME.getTick();
		MidiMessage MM = ME.getMessage();
		res = res + ", " + MidiMessageToString(MM);
		return res;
	}

	public Vector<MidiTrackEvent> SequenceToMidiTrackEvents()
	{
		Vector<MidiTrackEvent> V = new Vector<MidiTrackEvent>(0, 1);
		Track tracks[] = sequence.getTracks();
		int maxEvents = 0;
		for (int t = 0; t < tracks.length; t++)
			maxEvents = Math.max(maxEvents, tracks[t].size());

		for (int e = 0; e < maxEvents; e++)
		{
			for (int t = 0; t < tracks.length; t++)
				if (e < tracks[t].size())
				{
					MidiEvent ME = tracks[t].get(e);
					MidiTrackEvent MTE = new MidiTrackEvent(t, e, ME);
					V.add(MTE);
				}

		}

		return V;
	}

	public boolean isPlaying()
	{
		return playing;
	}

}
