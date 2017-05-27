package com.bodastage.e4.clock.ui.views;


import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import com.bodastage.e4.clock.ui.ClockWidget;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;

import java.time.LocalTime;
import java.time.ZoneId;

import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ClockView extends ViewPart {
	private org.eclipse.swt.widgets.Combo timeZones;
	
/**
	 * The constructor.
	 */
	public ClockView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		Object[] objects = parent.getDisplay().getDeviceData().objects;
		int count = 0;
		for(int i=0; i < objects.length; i++){
			if (objects[i] instanceof Color){
				count++;
			}
		}
		System.err.println("There are " + count + " Color instances." );
		
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		parent.setLayout(layout);
		
		final Canvas clock1 = new ClockWidget(parent, SWT.NONE, new RGB(255,0,0));
		final Canvas clock2 = new ClockWidget(parent, SWT.NONE, new RGB(0,255,0));
		final ClockWidget clock3 = new ClockWidget(parent, SWT.NONE, new RGB(0,0,255));
		
		clock1.setLayoutData(new RowData(20,20));
		clock2.setLayoutData(new RowData(50,50));
		clock3.setLayoutData(new RowData(100,100));
		
		timeZones = new org.eclipse.swt.widgets.Combo(parent, SWT.DROP_DOWN);
		timeZones.setVisibleItemCount(5);
		for(String zone: ZoneId.getAvailableZoneIds()){
			timeZones.add(zone);
		}
		
		timeZones.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = timeZones.getText();
				clock3.setZone(ZoneId.of(id));
				clock3.redraw();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				clock3.setZone(ZoneId.systemDefault());
				clock3.redraw();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		timeZones.setFocus();
	}
	

}
