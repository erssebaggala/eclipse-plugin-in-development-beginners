package com.bodastage.e4.clock.ui.handlers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;

public class ShowTheTime {
	@Execute
	public void execute(ESelectionService selectionService) {
		Object selection = selectionService.getSelection();
		if (selection instanceof ZoneId) {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
			String theTime = ZonedDateTime.now((ZoneId) selection).format(formatter);
			MessageDialog.openInformation(null, "The time is", theTime);
		}
	}
}