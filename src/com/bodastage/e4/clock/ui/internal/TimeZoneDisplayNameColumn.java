package com.bodastage.e4.clock.ui.internal;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeZoneDisplayNameColumn extends TimeZoneColumn {
	@Override
	public String getText(Object element) {
		if (element instanceof ZoneId) {
			return ((ZoneId) element).getDisplayName(TextStyle.FULL, Locale.
					getDefault());
		} else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "Display Name";
	}
}