package com.bodastage.e4.clock.ui.internal;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeZoneOffsetColumn extends TimeZoneColumn {
	@Override
	public String getText(Object element) {
		if (element instanceof ZoneId) {
			ZoneId zone = (ZoneId) element;
			ZoneOffset offset = ZonedDateTime.now(zone).getOffset();
			return offset.toString();
		} else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "Offset";
	}
}