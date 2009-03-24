package edu.mgupi.pass.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for Swing testing. Base idea is delayed execution (into another
 * thread) and waiting until its done.
 * 
 * @author raidan
 * 
 */
public class SwingHelper {

	private final static Logger logger = LoggerFactory.getLogger(SwingHelper.class);

	public static void addListenerSafed(AbstractButton button, ActionListener listener) {
		ActionListener[] listeners = button.getActionListeners();
		if (listeners != null) {
			for (ActionListener listen : listeners) {
				if (listen == listener) {
					throw new RuntimeException("Internal error. Listener " + listener + " already registered for "
							+ button);
				}
			}
		}
		button.addActionListener(listener);
	}

	/**
	 * Method (c) Ichiro Suzuki
	 * 
	 * @param parent
	 * @param name
	 * @return found Component with this name or null, if component with this
	 *         name is not found
	 */
	public static Component getChildNamed(Component parent, String name) {

		if (parent == null) {
			return null;
		}
		if (name == null) {
			return null;
		}

		// Debug line
		if (logger.isTraceEnabled()) {
			logger.trace("Search: " + name + " Class: " + parent.getClass() + " Name: " + parent.getName());
		}

		if (name.equals(parent.getName())) {
			return parent;
		}

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				Component child = getChildNamed(children[i], name);
				if (child != null) {
					return child;
				}
			}
		}

		return null;
	}

	public static JButton getButtonByActionCommand(Component parent, String actionCommand) {

		if (parent == null) {
			return null;
		}
		if (actionCommand == null) {
			return null;
		}

		// Debug line
		if (logger.isTraceEnabled()) {
			logger.trace("Class: " + parent.getClass() + " Name: " + parent.getName());
		}

		if (parent instanceof JButton) {
			if (actionCommand.equals(((JButton) parent).getActionCommand())) {
				return (JButton) parent;
			}
		}

		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				JButton child = getButtonByActionCommand(children[i], actionCommand);
				if (child != null) {
					return child;
				}
			}
		}

		return null;
	}

	public static void printChildHierarchy(Component parent) {
		printChildHierarchy(parent, 0);
	}

	private static void printChildHierarchy(Component parent, int level) {
		StringBuilder prefix = new StringBuilder("");
		for (int i = 0; i <= level; i++) {
			prefix.append(" ");
		}
		if (parent instanceof Container) {
			Component[] children = (parent instanceof JMenu) ? ((JMenu) parent).getMenuComponents()
					: ((Container) parent).getComponents();

			for (int i = 0; i < children.length; ++i) {
				System.out.println(prefix + " " + children[i].getName() + " = " + children[i]);
				printChildHierarchy(children[i], level + 1);
			}
		}
	}

	public static Object getReflectedFieldAccess(Object object, String name) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(name);

		boolean returnNoAccess = false;
		if (!field.isAccessible()) {
			returnNoAccess = true;
			field.setAccessible(true);
		}

		try {
			return field.get(object);
		} finally {
			if (returnNoAccess) {
				field.setAccessible(false);
			}
		}

	}
}
