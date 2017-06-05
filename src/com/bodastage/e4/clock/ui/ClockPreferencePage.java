package com.bodastage.e4.clock.ui;

import java.time.ZoneId;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class ClockPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public ClockPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "com.bodastage.e4.clock.ui"));
	}

	protected void createFieldEditors() {
		addField(new IntegerFieldEditor("launchCount", "Number of times it has been launched", getFieldEditorParent()));

		IntegerFieldEditor offset = new IntegerFieldEditor("offset", "Current offset from GMT", getFieldEditorParent());
		offset.setValidRange(-14, +12);
		addField(offset);

		String[][] data = ZoneId.getAvailableZoneIds().stream().sorted().map(s -> new String[] { s, s })
				.collect(Collectors.toList()).toArray(new String[][] {});
		addField(new ComboFieldEditor("favorite", "Favorite time zone", data, getFieldEditorParent()));
	}
}
