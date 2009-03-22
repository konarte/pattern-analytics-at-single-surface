package edu.mgupi.pass.modules.basic;

import static org.junit.Assert.assertNotNull;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusAppliedModuleFactory;
import edu.mgupi.pass.db.locuses.LocusSources;
import edu.mgupi.pass.db.locuses.LocusSourcesFactory;
import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.db.locuses.LocusesFactory;
import edu.mgupi.pass.filters.FilterChainsaw;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.filters.service.PlaceImageFilter;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.sources.SourceStore;
import edu.mgupi.pass.sources.TestSourceImpl;
import edu.mgupi.pass.util.Const;
import edu.mgupi.pass.util.Secundomer;
import edu.mgupi.pass.util.SecundomerList;

public class SimpleMatrixModuleTest {

	private SimpleMatrixModule module = null;

	@Before
	public void setUp() throws Exception {
		module = new SimpleMatrixModule();
	}

	@After
	public void tearDown() throws Exception {
		if (module != null) {
			module = null;
		}
	}

	private DecimalFormat fmt = new DecimalFormat("0.0000");

	private void processModule(SourceStore sourceStore, BufferedImage alternativeImage, String desc) throws Exception {
		Locuses locus = LocusesFactory.createLocuses();
		LocusSources locusSource = LocusSourcesFactory.createLocusSources();
		locusSource.setFilename(sourceStore.getName());
		locusSource.setSourceImage(sourceStore.getFileData());

		locus.setLocusSource(locusSource);
		LocusAppliedModule appModule = LocusAppliedModuleFactory.createLocusAppliedModule();
		locus.setModule(appModule);

		BufferedImage sourceImage = alternativeImage == null ? sourceStore.getSourceImage() : alternativeImage;
		module.analyze(sourceImage, locus);

		BufferedImage moduleImage = ModuleHelper.getTemporaryModuleImage(locus);
		locus.setProcessed(true);

		assertNotNull(moduleImage);

		new File("tmp").mkdir();

		File matrixFile = new File("tmp/simple-matrix-module-matrixtrace-" + desc + ".txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(matrixFile, false));
		try {
			double[][] matrix = (double[][]) ModuleHelper.getParameterValue(locus, "matrix", true);
			for (int j = 0; j < matrix.length; j++) {
				for (int i = 0; i < matrix[j].length; i++) {
					out.write(fmt.format(matrix[i][j]));
					out.write(" ");
				}
				out.write("\n");
			}
		} finally {
			out.close();
		}

		ImageIO.write(ModuleHelper.getTemporaryModuleImage(locus), "PNG", new File("tmp/simple-matrix-module-" + desc
				+ ".png"));

		ImageIO.write(sourceImage, "PNG", new File("tmp/simple-matrix-module-" + desc + "-source.png"));

	}

	@Test
	public void testAnalyze() throws Exception {

		System.out.println("HINT = " + RenderingHints.VALUE_INTERPOLATION_BILINEAR + " ("
				+ RenderingHints.VALUE_INTERPOLATION_BILINEAR.getClass() + ")");

		TestSourceImpl source = new TestSourceImpl();
		FilterChainsaw mySaw = new FilterChainsaw(true);
		source.init();
		try {
			SecundomerList.reset();
			Secundomer METHOD_FAST = SecundomerList.registerSecundomer("methodFast");
			Secundomer METHOD_SLOW = SecundomerList.registerSecundomer("methodSlow");

			SourceStore sourceStore = source.getSingleSource();

			PlaceImageFilter place = (PlaceImageFilter) mySaw.appendFilter(PlaceImageFilter.class);
			place.getWIDTH().setValue(Const.MAIN_IMAGE_WIDTH);
			place.getHEIGHT().setValue(Const.MAIN_IMAGE_HEIGHT);
			place.getPLACE().setValue("topleft");

			mySaw.appendFilter(GrayScaleFilter.class);
			this.module.getRENREDING_METHOD().setValue("fast");
			METHOD_FAST.start();
			this.processModule(sourceStore, null, "common-fast");
			METHOD_FAST.stop();

			this.module.getRENREDING_METHOD().setValue("slow");
			METHOD_SLOW.start();
			this.processModule(sourceStore, null, "common-slow");
			METHOD_SLOW.stop();

			this.module.getRENREDING_METHOD().resetValue();
			this.module.getCELL_SIZE().setValue(5);
			this.processModule(sourceStore, null, "common-small5");

			this.module.getCELL_SIZE().resetValue();

			mySaw.attachImage(sourceStore.getSourceImage());
			this.processModule(sourceStore, mySaw.filterSaw(), "filtered");

			mySaw.removeFilter(PlaceImageFilter.class);
			this.processModule(sourceStore, mySaw.filterSaw(), "filtered_and_gray");

		} finally {
			source.close();
			mySaw.close();
		}

		SecundomerList.printToOutput(System.out);

	}
}
