package com.bodastage.e4.clock.ui.internal;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TimeZoneSummerTimeColumn extends TimeZoneColumn {
	@Override
	public String getText(Object element) {
		if (element instanceof ZoneId) {
			ZoneId zone = (ZoneId) element;
			Boolean b =  TimeZone.getTimeZone(zone).useDaylightTime();
			return b.toString();
		} else {
			return "";
		}
	}

	@Override
	public String getTitle() {
		return "Offset";
	}
}