
package com.bodastage.e4.clock.ui.handlers;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.progress.IProgressConstants2;

import com.bodastage.e4.clock.ui.Activator;

public class HelloHandler {
	@Execute
	public void execute(final UISynchronize display, final ICommandService commandService, final StatusReporter statusReporter) {
		Job job = new Job("About to say hello") {
			@SuppressWarnings("deprecation")
			protected IStatus run(IProgressMonitor monitor) {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, "Preparing", 5000);
					subMonitor = null; // the bug
					for (int i = 0; i < 50 && !subMonitor.isCanceled(); i++) {
						if (i == 10) {
							subMonitor.subTask("Doing something");
						} else if (i == 12) {
							checkDozen(subMonitor.newChild(100));
							continue;
						} else if (i == 25) {
							subMonitor.subTask("Doing something else");
						} else if (i == 40) {
							subMonitor.subTask("Nearly there");
						}
						Thread.sleep(100);
						subMonitor.worked(100);
					}
				} catch (InterruptedException e) {
				} catch (RuntimeException e) {
					// return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					// "Programming bug?", e);
					Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Programming bug?", e);
					statusReporter.report(status, StatusReporter.LOG);
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

		Command command = commandService.getCommand("com.bodastage.e4.clock.ui.command.hello");
		if (command != null) {
			// job.setProperty(IProgressConstants2.COMMAND_PROPERTY, command);
			job.setProperty(IProgressConstants2.COMMAND_PROPERTY, ParameterizedCommand.generateCommand(command, null));

			job.setProperty(IProgressConstants2.ICON_PROPERTY,
					ImageDescriptor.createFromURL(HelloHandler.class.getResource("/icons/sample.gif")));
		}
		job.schedule();
		return;
	}

	private void checkDozen(IProgressMonitor monitor) {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
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