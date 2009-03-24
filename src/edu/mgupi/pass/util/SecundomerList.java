package edu.mgupi.pass.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Register new Secundomer, print all registered to output stream.
 * 
 * @author raidan
 * 
 * @see Secundomer
 */
public class SecundomerList {

	private final static Collection<Secundomer> registredList = new ArrayList<Secundomer>();

	/**
	 * Create new instance of {@link Secundomer} with specified title and return
	 * 
	 * @param title
	 *            name of secundomer
	 * @return instance of {@link Secundomer} class
	 */
	public static Secundomer registerSecundomer(String title) {
		Secundomer sec = new Secundomer(title);
		registredList.add(sec);
		return sec;
	}

	/**
	 * Printing counters of all registered secundomers to stream.
	 * 
	 * @param stream
	 *            output stream
	 */
	public static void printToOutput(PrintStream stream) {
		stream.println(" == RESULT == ");
		for (Secundomer sec : registredList) {
			stream.println(sec.toString());
		}
	}

	/**
	 * Special reset method, clear only list of registered secundomers, not
	 * reseting actual instances!
	 */
	public static void reset() {
		registredList.clear();
	}
}
