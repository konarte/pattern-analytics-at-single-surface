package edu.mgupi.pass.util;

import org.orm.PersistentException;

public interface IRefreshable {
	int refresh() throws PersistentException;
}
