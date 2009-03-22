package edu.mgupi.pass.modules.basic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.filters.Param;
import edu.mgupi.pass.filters.Param.TYPES;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleCantProcessException;
import edu.mgupi.pass.modules.ModuleException;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.modules.ModuleParamException;

public class SimpleMatrixModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(SimpleMatrixModule.class);

	private final static String METHOD_FAST = "fast";
	private final static String METHOD_SLOW = "slow";

	private Param RENREDING_METHOD = new Param("RenderMethod", "Способ рендеринга", "fast", new Object[] { METHOD_FAST,
			METHOD_SLOW }, new String[] { "Быстрый", "Медленный, многослойный" });
	private Param CELL_SIZE = new Param("CellSize", "Размер ячейки", TYPES.INT, 10, 5, 15);

	private Collection<Param> paramList = null;

	public SimpleMatrixModule() {
		paramList = new ArrayList<Param>(2);
		paramList.add(RENREDING_METHOD);
		paramList.add(CELL_SIZE);
		paramList = Collections.unmodifiableCollection(paramList);
	}

	@Override
	public Collection<Param> getParams() {
		return paramList;
	}

	public String getName() {
		return "Матричный анализ";
	}

	private final static float MAX_LEVEL = 255.0f;
	private final static int MATRIX_SIZE = 80;
	private final static int MULTIPLIER = 4;

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

		int cellSize = (Integer) CELL_SIZE.getValue();
		String renredMethod = (String) RENREDING_METHOD.getValue();

		logger.debug("Analyzing image by matrixes");

		//final int bytesPerPixel = filteredImage.getColorModel().getPixelSize() / 8;
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

		// Warning!
		// Potentially dangerous situation!
		// Delta can change left and right -- 
		//  so left can be less then 0 and right is more than image width
		int delta = ((bottom - top) - (right - left)) / 2;
		if (Math.abs(delta) > sourceWidth / MULTIPLIER) {
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

		int fullSize = MATRIX_SIZE * cellSize;
		BufferedImage dest = new BufferedImage(fullSize, fullSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = dest.createGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(0, 0, dest.getWidth(), dest.getHeight());

		// Old method -- Konart, new method -- raidan
		boolean isOldMethod = METHOD_SLOW.equals(renredMethod);
		if (isOldMethod) {
			graph.setColor(Color.BLACK);
			graph.drawLine(0, fullSize, fullSize, fullSize);
			graph.drawLine(fullSize, fullSize, fullSize, 0);
		}

		for (int f = 0; f < MATRIX_SIZE; f++) {

			// Draw lines by old method
			if (isOldMethod) {
				graph.drawLine(0, f * cellSize, fullSize - 1, f * cellSize);
				graph.drawLine(f * cellSize, 0, f * cellSize, fullSize - 1);
			}

			for (int g = 0; g < MATRIX_SIZE; g++) { // #1
				for (int i = 0; i < MULTIPLIER; i++) {
					for (int j = 0; j < MULTIPLIER; j++) {

						int x = (int) ((f * MULTIPLIER + i) * dx + left);
						int y = (int) ((g * MULTIPLIER + j) * dy + top);

						matrix[f][g] = matrix[f][g] + (1.0 - raster.getSampleFloat(x, y, 0) / MAX_LEVEL) / 16;
						int color = (int) Math.round((1 - matrix[f][g]) * 255);

						graph.setColor(new Color(color, color, color));
						if (isOldMethod) {
							graph.fillRect(f * cellSize + 2, g * cellSize + 2, (f + 1) * cellSize - 1, (g + 1)
									* cellSize - 1);
						} else {
							graph.fillRect(f * cellSize, g * cellSize, cellSize - 1, cellSize - 1);
						}

					}
				}
			} // #1 for

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

	public Param getRENREDING_METHOD() {
		return RENREDING_METHOD;
	}

	public Param getCELL_SIZE() {
		return CELL_SIZE;
	}

}
