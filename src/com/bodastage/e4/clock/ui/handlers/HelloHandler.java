
package com.bodastage.e4.clock.ui.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;

public class HelloHandler {
	@Execute
	public void execute(final UISynchronize display) {
		Job job = new Job("About to say hello") {
			@SuppressWarnings("deprecation")
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Preparing", 5000);
					for (int i = 0; i < 50 && !monitor.isCanceled(); i++) {
						if (i == 10) {
							monitor.subTask("Doing something");
						} else if (i == 25) {
							monitor.subTask("Doing something else");
						} else if (i == 40) {
							monitor.subTask("Nearly there");
						} else if (i == 12) {
							checkDozen(new SubProgressMonitor(monitor, 100));
							continue; //Note that the continue is used here to avoid calling monitor.worked(100) below.
						}
						Thread.sleep(100);
						monitor.worked(100);
					}
				} catch (InterruptedException e) {
				} finally {
					monitor.done();
				}

				if (!monitor.isCanceled()) {
					display.asyncExec(() -> {
						MessageDialog.openInformation(null, "Hello", "World");
					});
				}

				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return;
	}

	private void checkDozen(IProgressMonitor monitor) {
		try {
			monitor.beginTask("Checking a dozen", 12);
			for (int i = 0; i < 12; i++) {
				Thread.sleep(10);
				monitor.worked(1);
			}
		} catch (InterruptedException e) {
		} finally {
			monitor.done();
		}
	}

}