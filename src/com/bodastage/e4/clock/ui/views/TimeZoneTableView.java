package com.bodastage.e4.clock.ui.views;

import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.bodastage.e4.clock.ui.internal.TimeZoneDisplayNameColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneIDColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneOffsetColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneSummerTimeColumn;

public class TimeZoneTableView {
	private TableViewer tableViewer;

	@PostConstruct
	public void create(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(ZoneId.getAvailableZoneIds());
		
		//Create column before calling setInput()
		new TimeZoneIDColumn().addColumnTo(tableViewer);
		new TimeZoneDisplayNameColumn().addColumnTo(tableViewer);
		new TimeZoneOffsetColumn().addColumnTo(tableViewer);
		new TimeZoneSummerTimeColumn().addColumnTo(tableViewer);
		tableViewer.setInput(ZoneId.getAvailableZoneIds() // get ids
				.stream().map(ZoneId::of).toArray());
		
		
	}

	@Focus
	public void focus() {
		tableViewer.getControl().setFocus();
	}

}
