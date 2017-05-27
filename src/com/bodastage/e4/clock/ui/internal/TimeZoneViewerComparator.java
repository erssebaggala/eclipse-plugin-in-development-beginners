package com.bodastage.e4.clock.ui.internal;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class TimeZoneViewerComparator extends ViewerComparator {
	int compare;

	public int compare(Viewer viewer, Object z1, Object z2) {
		if (z1 instanceof ZoneId && z2 instanceof ZoneId) {
			Instant now = Instant.now();
			ZonedDateTime zdt1 = ZonedDateTime.ofInstant(now, (ZoneId) z1);
			ZonedDateTime zdt2 = ZonedDateTime.ofInstant(now, (ZoneId) z2);
			compare = zdt1.compareTo(zdt2);
		} else {
			compare = z1.toString().compareTo(z2.toString());
		}
		boolean reverse = Boolean.parseBoolean(String.valueOf(viewer.getData("REVERSE")));
		return reverse ? -compare : compare;
	}

}
