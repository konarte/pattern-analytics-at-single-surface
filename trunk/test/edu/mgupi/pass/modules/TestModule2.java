package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.Param;

public class TestModule2 implements IModule {

	@Override
	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException, ModuleException {
	}

	@Override
	public double compare(Locuses graph1, Locuses graph2) throws IOException, ModuleException {
		return 0;
	}

	@Override
	public String getName() {
		return "Warning. Тестовый пустой";
	}

	@Override
	public Collection<Param> getParams() {
		return null;
	}

}
