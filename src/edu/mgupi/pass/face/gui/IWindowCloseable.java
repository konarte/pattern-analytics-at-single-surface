/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)IWindowCloseable.java 1.0 30.03.2009
 */

package edu.mgupi.pass.face.gui;

import javax.swing.JFileChooser;

/**
 * Special interface for windows we must force close before disposing. <br>
 * 
 * Main purpose -- is closing instance of {@link JFileChooser} before close
 * application. If we'll not to do this, we'll may receive huge amount of
 * exceptions like that:
 * 
 * <pre>
 * java.util.concurrent.RejectedExecutionException
 * 	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:1760)
 * 	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:767)
 * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:658)
 * 	at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:92)
 * 	at sun.awt.shell.Win32ShellFolderManager2$ComInvoker.invoke(Win32ShellFolderManager2.java:493)
 * 	at sun.awt.shell.Win32ShellFolder2$FolderDisposer.dispose(Win32ShellFolder2.java:170)
 * 	at sun.java2d.Disposer.run(Disposer.java:128)
 * 	at java.lang.Thread.run(Thread.java:619)
 * 
 * </pre>
 * 
 * 
 * @author raidan
 * 
 */
public interface IWindowCloseable {

	/**
	 * Close action.
	 */
	void close();
}
