package com.bodastage.e4.clock.ui.internal;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TimeZoneViewerFilter extends ViewerFilter {
	private String pattern;

	public TimeZoneViewerFilter(String pattern) {
		this.pattern = pattern;
	}

	public boolean select(Viewer v, Object parent, Object element) {
		if (element instanceof ZoneId) {
			ZoneId zone = (ZoneId) element;
			String displayName = zone.getDisplayName(TextStyle.FULL, Locale.getDefault());
			return displayName.contains(pattern);
		} else {
			return true;
		}
	}
}
