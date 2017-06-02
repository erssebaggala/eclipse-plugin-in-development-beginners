package com.bodastage.e4.clock.ui.views;

import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionService;

import com.bodastage.e4.clock.ui.internal.TimeZoneDisplayNameColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneIDColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneOffsetColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneSummerTimeColumn;

public class TimeZoneTableView {
	private TableViewer tableViewer;
	
	@Inject 
	private ESelectionService selectionService;
	
	@PostConstruct
	public void create(Composite parent, EMenuService menuService) {
		
		tableViewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(ZoneId.getAvailableZoneIds());
		
		menuService.registerContextMenu(tableViewer.getControl(), "com.bodastage.e4.clock.ui.popupmenu.0");
		tableViewer.addSelectionChangedListener(event -> {
			Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if(selection != null && selectionService != null ){
				selectionService.setSelection(selection);
			}
		});
		
		// Create column before calling setInput()
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

	@Inject
	@Optional
	public void setTimeZone(@Named(IServiceConstants.ACTIVE_SELECTION) ZoneId timeZone) {
		if (timeZone != null && tableViewer != null) {
			tableViewer.setSelection(new StructuredSelection(timeZone));
			tableViewer.reveal(timeZone);
		}
	}

}
