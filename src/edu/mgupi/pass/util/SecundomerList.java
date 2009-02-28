package edu.mgupi.pass.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;

public class SecundomerList {

	private final static Collection<Secundomer> registredList = new ArrayList<Secundomer>();

	public static Secundomer registerSecundomer(String title) {
		Secundomer sec = new Secundomer(title);
		registredList.add(sec);
		return sec;
	}

	public static void printToOutput(PrintStream stream) {
		stream.println(" == RESULT == ");
		for (Secundomer sec : registredList) {
			stream.println(sec.toString());
		}
	}
}
