// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WizardPanel.java

package common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;

// Referenced classes of package common:
//			LicenseString, WizardStepPanel

public class WizardPanel extends JPanel
	implements ActionListener, LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<WizardStepPanel> stepPanels;
	private int stepCurrent;
	private int stepLast;
	private JPanel stepButtons;
	private JButton stepBtns[];
	private JPanel currentPanel;
	public final int BTN_BACK = 0;
	public final int BTN_NEXT = 1;
	public final int BTN_FINISH = 2;
	public final int BTN_CANCEL = 3;
	private String stepBtnsStrings[] = {
		"Back", "Next", "Finish", "Cancel"
	};

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	public WizardPanel(WizardStepPanel firstStepPanel)
	{
		if (firstStepPanel != null)
		{
			stepPanels = new Vector<WizardStepPanel>(0, 1);
			stepCurrent = 0;
			currentPanel = firstStepPanel;
			stepBtns = new JButton[4];
			stepButtons = stepButtonsCreate();
			stepLast = -1;
			addStepPanel(firstStepPanel);
			init();
		} else
		{
			throw new NullPointerException("firstStepPanel is null");
		}
	}

	public final boolean addStepPanel(WizardStepPanel nextStepPanel)
	{
		boolean success = false;
		if (nextStepPanel != null)
		{
			stepPanels.add(nextStepPanel);
			stepLast++;
			stepButtonsUpdate();
			nextStepPanel.setWizardPanel(this);
		}
		return success;
	}

	public final void setNextEnable(boolean value)
	{
		stepBtns[1].setEnabled(value);
	}

	private final void init()
	{
		int numStepPanels = stepPanels.size();
		if (numStepPanels > 0)
		{
			setLayout(new BorderLayout());
			add(stepButtons, "South");
			stepCurrent = 0;
			showCurrentStep();
		} else
		{
			throw new IllegalStateException("call addStepPanel to add panels to show");
		}
	}

	private final void showCurrentStep()
	{
		stepButtonsUpdate();
		if (currentPanel != null)
		{
			currentPanel.setVisible(false);
			remove(currentPanel);
		}
		WizardStepPanel WSP = (WizardStepPanel)stepPanels.get(stepCurrent);
		WSP.setVisible(true);
		add(WSP, "Center");
		validate();
		currentPanel = WSP;
	}

	private void stepButtonsUpdate()
	{
		stepBtns[0].setEnabled(stepCurrent != 0);
		stepBtns[1].setEnabled(stepCurrent != stepLast);
	}

	private final JPanel stepButtonsCreate()
	{
		JPanel btnPanel = new JPanel(new FlowLayout());
		for (int i = 0; i < stepBtns.length; i++)
		{
			stepBtns[i] = new JButton();
			stepBtns[i].setActionCommand(stepBtnsStrings[i]);
			stepBtns[i].addActionListener(this);
			btnPanel.add(stepBtns[i]);
		}

		stepButtonsSetText();
		return btnPanel;
	}

	private final void stepButtonsSetText()
	{
		for (int i = 0; i < stepBtns.length; i++)
			stepBtns[i].setText(stepBtnsStrings[i]);

	}

	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource();
		if (obj.getClass().isInstance(stepBtns[0]))
		{
			JButton btnSrc = (JButton)obj;
			String actionCommand = btnSrc.getActionCommand();
			if (actionCommand.equals(stepBtns[0].getText()))
			{
				stepCurrent--;
				showCurrentStep();
			} else
			if (actionCommand.equals(stepBtns[1].getText()))
			{
				stepCurrent++;
				showCurrentStep();
			} else
			if (!actionCommand.equals(stepBtns[2].getText()))
				actionCommand.equals(stepBtns[3].getText());
		}
	}
}
