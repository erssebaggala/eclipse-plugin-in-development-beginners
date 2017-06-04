package com.bodastage.e4.clock.ui.views;

import java.net.URL;
import java.time.ZoneId;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;

import com.bodastage.e4.clock.ui.internal.TimeZoneComparator;
import com.bodastage.e4.clock.ui.internal.TimeZoneContentProvider;
import com.bodastage.e4.clock.ui.internal.TimeZoneDialog;
import com.bodastage.e4.clock.ui.internal.TimeZoneLabelProvider;
import com.bodastage.e4.clock.ui.internal.TimeZoneViewerComparator;
import com.bodastage.e4.clock.ui.internal.TimeZoneViewerFilter;

public class TimeZoneTreeView {
	private TreeViewer treeViewer;

	int launchCount;

	@Inject
	private ISharedImages images;

	@Inject
	@Optional
	private ESelectionService selectionService;

	@PostConstruct
	public void create(Composite parent) {
		ResourceManager rm = JFaceResources.getResources();
		LocalResourceManager lrm = new LocalResourceManager(rm, parent);

		ImageRegistry ir = new ImageRegistry(lrm);
		URL sample = getClass().getResource("/icons/sample.gif");
		ir.put("sample", ImageDescriptor.createFromURL(sample));

		FontRegistry fr = JFaceResources.getFontRegistry();

		treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		treeViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new TimeZoneLabelProvider(images, ir, fr)));
		treeViewer.setContentProvider(new TimeZoneContentProvider());
		treeViewer.setInput(new Object[] { TimeZoneComparator.getTimeZones() });

		// Reverse sorting
		treeViewer.setData("REVERSE", Boolean.TRUE);
		treeViewer.setComparator(new TimeZoneViewerComparator());

		// Filtering
		treeViewer.setFilters(new ViewerFilter[] { new TimeZoneViewerFilter("GMT") });

		// Expand tree automatically
		treeViewer.setExpandPreCheckFilters(true);

		treeViewer.addDoubleClickListener(event -> {
			Viewer viewer = event.getViewer();
			Shell shell = viewer.getControl().getShell();

			ISelection sel = viewer.getSelection();
			Object selectedValue;

			if (!(sel instanceof IStructuredSelection) || sel.isEmpty()) {
				selectedValue = null;
			} else {
				selectedValue = ((IStructuredSelection) sel).getFirstElement();
			}

			if (selectedValue instanceof ZoneId) {
				ZoneId timeZone = (ZoneId) selectedValue;
				new TimeZoneDialog(shell, timeZone).open();
			}

		});

		treeViewer.addSelectionChangedListener(event -> {
			// forward selection
			Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
			if (selection != null && selectionService != null) {
				selectionService.setSelection(selection);
			}
		});

		// Launch count
		System.out.println("Launch count is: " + launchCount);
	}

	@Focus
	public void focus() {
		treeViewer.getControl().setFocus();
	}

	@Inject
	public void setLaunchCount(
			@Preference(nodePath = "com.bodastage.e4.clock.ui", value = "launchCount") int launchCount) {
		this.launchCount = launchCount;
	}
}
