package edu.mgupi.pass.modules.basic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.IFilter;
import edu.mgupi.pass.filters.java.GrayScaleFilter;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleCantProcessException;
import edu.mgupi.pass.modules.ModuleException;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.modules.ModuleParamException;

public class SimpleMatrixModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(SimpleMatrixModule.class);

	private Collection<Class<? extends IFilter>> requiredFilters = null;

	public SimpleMatrixModule() {
		requiredFilters = new ArrayList<Class<? extends IFilter>>();
		requiredFilters.add(GrayScaleFilter.class);
	}

	public String getName() {
		return "Матричный анализ (Ч/Б)";
	}

	public Collection<Class<? extends IFilter>> getRequiredFilters() {
		return requiredFilters;
	}

	private final static float MAX_LEVEL = 255.0f;
	private final static int CELL_SIZE = 10;
	private final static int MATRIX_SIZE = 80;

	private int getBitMapLineSum(int x1, int y1, int x2, int y2, Raster source) {
		int result = 0;
		if (x1 == x2) {
			for (int i = y1; i <= y2; i++) {
				if (source.getSampleFloat(x1, i, 0) / MAX_LEVEL < 0.5) {
					result++;
				}
			}
		} else {
			for (int i = x1; i <= x2; i++) {
				if (source.getSampleFloat(i, y1, 0) / MAX_LEVEL < 0.5) {
					result++;
				}
			}
		}
		return result;
	}

	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException, ModuleException {

		logger.debug("Analyzing image by matrixes");

		final int bytesPerPixel = filteredImage.getColorModel().getPixelSize() / 8;
		Raster raster = filteredImage.getRaster();
		double[][] matrix = new double[MATRIX_SIZE][MATRIX_SIZE];

		int sourceWidth = filteredImage.getWidth();
		int sourceHeight = filteredImage.getHeight();

		int left = 0;
		int top = 0;

		int right = sourceWidth - 1;
		int bottom = sourceHeight - 1;

		// определяем верхнюю границу
		while (top < bottom && (getBitMapLineSum(left, top, right, top, raster) == 0)) {
			top = top + 2;
		}
		// определяем нижнюю границу
		while (top < bottom && (getBitMapLineSum(left, bottom, right, bottom, raster) == 0)) {
			bottom = bottom - 2;
		}
		// определяем левую границу
		while (left < right && (getBitMapLineSum(left, top, left, bottom, raster) == 0)) {
			left = left + 2;
		}
		// определяем правую границу
		while (left < right && (getBitMapLineSum(right, top, right, bottom, raster) == 0)) {
			right = right - 2;
		}
		if (right - left < 2 || bottom - top < 2) {
			// cant't analyze
			throw new ModuleCantProcessException("Unable to build matrix from image. Right-left = " + (right - left)
					+ " and bottom-top = " + (bottom - top));
		}

		int delta = ((bottom - top) - (right - left)) / 2;
		if (Math.abs(delta) > sourceWidth / 4) {
			if (delta > 0) {
				right = right + delta;
				left = left - delta;
			} else {
				bottom = bottom - delta;
				top = top + delta;
			}
		}

		float dx = (float) (0.25 * (right - left) / (float) MATRIX_SIZE);
		float dy = (float) (0.25 * (bottom - top) / (float) MATRIX_SIZE);

		if (logger.isTraceEnabled()) {
			logger.trace("dx = {}", dx);
			logger.trace("dy = {}", dy);
			logger.trace("delta = {}", delta);
			logger.trace("left = {}", left);
			logger.trace("right = {}", right);
			logger.trace("top = {}", top);
			logger.trace("bottom = {}", bottom);
		}

		int destSize = MATRIX_SIZE * CELL_SIZE;
		BufferedImage dest = new BufferedImage(destSize, destSize, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D graph = dest.createGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(0, 0, dest.getWidth(), dest.getHeight());

		graph.setColor(Color.BLACK);
		graph.drawLine(0, destSize, destSize, destSize);
		graph.drawLine(destSize, destSize, destSize, 0);

		for (int f = 0; f < MATRIX_SIZE; f++) {

			graph.drawLine(0, f * CELL_SIZE, destSize, f * CELL_SIZE);
			graph.drawLine(f * CELL_SIZE, 0, f * CELL_SIZE, destSize);

			for (int g = 0; g < MATRIX_SIZE; g++) {
				for (int i = 0; i < bytesPerPixel; i++) {
					for (int j = 0; j < bytesPerPixel; j++) {

						int x = (int) ((f * 4 + i) * dx + left);
						int y = (int) ((g * 4 + j) * dy + top);

						matrix[f][g] = matrix[f][g] + (1.0 - raster.getSampleFloat(x, y, 0) / MAX_LEVEL) / 16;
						int color = (int) Math.round((1 - matrix[f][g]) * 255);

						graph.setColor(new Color(color, color, color));
						graph.fillRect(f * CELL_SIZE + 2, g * CELL_SIZE + 2, (f + 1) * CELL_SIZE - 1, (g + 1)
								* CELL_SIZE - 1);

					}
				}
			}
		}

		graph.dispose();

		ModuleHelper.putParameterValue(store, "matrix", matrix);
		ModuleHelper.putTemporaryModuleImage(store, dest);

	}

	public double compare(Locuses graph1, Locuses graph2) throws ModuleParamException, IOException {

		double[][] matrix1 = (double[][]) ModuleHelper.getParameterValue(graph1, "matrix", true);
		double[][] matrix2 = (double[][]) ModuleHelper.getParameterValue(graph2, "matrix", true);

		if (matrix1.length != MATRIX_SIZE || (matrix1.length > 0 && matrix1[0].length != MATRIX_SIZE)) {
			throw new ModuleParamException("Received invalid parameter 'matrix' for first locus '" + graph1.getName()
					+ "'. Length of matrix does not equals of " + MATRIX_SIZE);
		}

		if (matrix2.length != MATRIX_SIZE || (matrix2.length > 0 && matrix2[0].length != MATRIX_SIZE)) {
			throw new ModuleParamException("Received invalid parameter 'matrix' for second locus '" + graph2.getName()
					+ "'. Length of matrix does not equals of " + MATRIX_SIZE);
		}

		double result = 0.0;
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				result = result + Math.pow(matrix2[i][j] - matrix1[i][j], 2);
			}
		}
		result = result / Math.pow(MATRIX_SIZE, 2);

		return result;

	}

}
