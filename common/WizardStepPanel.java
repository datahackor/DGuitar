// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WizardStepPanel.java

package common;

import javax.swing.JPanel;

// Referenced classes of package common:
//			WizardStep, LicenseString, WizardPanel

public abstract class WizardStepPanel extends JPanel
	implements WizardStep, LicenseString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WizardPanel WP;

	public WizardStepPanel()
	{
	}

	public String getLicenseString()
	{
		return "This file is covered by the GNU GENERAL PUBLIC LICENSE, Version 2, June 1991";
	}

	protected final void setWizardPanel(WizardPanel aWizardPanel)
	{
		if (aWizardPanel != null)
			WP = aWizardPanel;
		else
			throw new NullPointerException("aWizardPanel is null");
	}

	public final WizardPanel getWizardPanel()
	{
		return WP;
	}
}
