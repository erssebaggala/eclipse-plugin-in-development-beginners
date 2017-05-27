package com.bodastage.e4.clock.ui;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas{
	private final Color color;
	private ZoneId zone = ZoneId.systemDefault();
	
	public ClockWidget(Composite parent, int style, RGB rgb){
		super(parent,style);
		
		this.color = new Color(parent.getDisplay(), rgb);
		addDisposeListener(e->color.dispose());
		
		addPaintListener(this::drawClock);
		
		Runnable redraw = () -> {
			while (!this.isDisposed()) {
				this.getDisplay().asyncExec(()->this.redraw());
				try{
					Thread.sleep(1000);
				}catch(InterruptedException e){
					return;
				}
			}
		};
		new Thread(redraw, "TickTock").start();
	}
	
	private void drawClock(PaintEvent e){
		e.gc.drawArc(e.x, e.y, e.width-1, e.height-1, 0, 360);
		
		int seconds  = LocalTime.now().getSecond();
		int arc = (15-seconds) * 6 % 360;
		
		e.gc.setBackground(color);
		e.gc.fillArc(e.x, e.y, e.width-1, e.height-1, arc-1, 2);
		
		//Hour hand
		e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLACK));
		ZonedDateTime now = ZonedDateTime.now(zone);
		int hours = now.getHour();
		arc = (3-hours) * 30 % 360;
		e.gc.fillArc(e.x, e.y, e.width-1, e.height-1, arc-5, 10);
	}
	
	public Point computeSize(int w, int h, boolean changed){
		int size;
		if (w == SWT.DEFAULT){
			size = h;
		}else if(h == SWT.DEFAULT){ 
			size = w; 
		}else{
			size = Math.min(w, h);
		}
		
		if (size == SWT.DEFAULT){
			size = 50;
		}
		
		return new Point(size, size);
	}
	
	public void setZone(ZoneId zone){
		this.zone = zone;
	}

	public ZoneId getZone() {
		return zone;
	}
	
}
