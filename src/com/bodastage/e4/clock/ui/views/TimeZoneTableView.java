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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionService;

import com.bodastage.e4.clock.ui.internal.TimeZoneDisplayNameColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneIDColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneOffsetColumn;
import com.bodastage.e4.clock.ui.internal.TimeZoneSummerTimeColumn;

public class TimeZoneTableView {
	private TableViewer tableViewer;

	@Inject
	private ISelectionService selectionService;

	@PostConstruct
	public void create(Composite parent) {

		tableViewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(ZoneId.getAvailableZoneIds());

		// Create column before calling setInput()
		new TimeZoneIDColumn().addColumnTo(tableViewer);
		new TimeZoneDisplayNameColumn().addColumnTo(tableViewer);
		new TimeZoneOffsetColumn().addColumnTo(tableViewer);
		new TimeZoneSummerTimeColumn().addColumnTo(tableViewer);
		tableViewer.setInput(ZoneId.getAvailableZoneIds() // get ids
				.stream().map(ZoneId::of).toArray());

		MenuManager manager = new MenuManager("#PopupMenu");
		Menu menu = manager.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);

		Action deprecated = new Action() {
			public void run() {
				MessageDialog.openInformation(null, "Hello", "World");
			}
		};
		deprecated.setText("Hello");
		manager.add(deprecated);

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
