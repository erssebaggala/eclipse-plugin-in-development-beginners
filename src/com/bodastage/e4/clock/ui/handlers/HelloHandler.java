
package com.bodastage.e4.clock.ui.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;

public class HelloHandler {
	@Execute
	public void execute(final UISynchronize display) {
		Job job = new Job("About to say hello") {
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Preparing", 5000);
					for (int i = 0; i < 50; i++) {
						Thread.sleep(100);
						monitor.worked(100);
					}
				} catch (InterruptedException e) {
				} finally {
					monitor.done();
				}

				display.asyncExec(() -> {
					MessageDialog.openInformation(null, "Hello", "World");
				});

				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return;
	}

}