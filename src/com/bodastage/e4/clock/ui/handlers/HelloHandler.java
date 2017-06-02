
package com.bodastage.e4.clock.ui.handlers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.progress.UIJob;

public class HelloHandler {
	@Execute
	public void execute(final UISynchronize display) {
		UIJob job = new UIJob("About to say hello") {
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
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