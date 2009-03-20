package edu.mgupi.pass.modules.basic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.db.locuses.Locuses;
import edu.mgupi.pass.modules.IModule;
import edu.mgupi.pass.modules.ModuleHelper;
import edu.mgupi.pass.modules.ModuleParamException;

public class SimpleMatrixModule implements IModule {

	private final static Logger logger = LoggerFactory.getLogger(SimpleMatrixModule.class);

	public void init() {
		logger.debug("SMM initiated");
	}

	public void close() {
		logger.debug("SMM closed");
	}

	private final static int MATRIX_SIZE = 80;

	public void analyze(BufferedImage filteredImage, Locuses store) throws IOException {

//		final int bytesPerPixel = filteredImage.getColorModel().getPixelSize() / 8;
//		Raster raster = filteredImage.getRaster();
//		double[][] matrix = new double[MATRIX_SIZE][MATRIX_SIZE];
//
//		for (int f = 0; f < MATRIX_SIZE; f++) {
//
//			for (int g = 0; g < MATRIX_SIZE; g++) {
//				for (int i = 0; i < bytesPerPixel; i++) {
//					for (int j = 0; j < bytesPerPixel; j++) {
//						// matrix[f][g] = matrix[f][g] + (1.0 - )
//					}
//				}
//			}
//		}

		// TODO Auto-generated method stub
		// for g := 0 to MatrixSize-1 do begin
		// for i := 0 to 3 do
		// for j := 0 to 3 do
		// A[f,g] := A[f,g] + (1.0-Bitm.Pixels[ (f*4 + i)*DX + SymbolRect.Left,
		// (g*4 + j)*DY + SymbolRect.Top]) / 16;

	}

	public double compare(Locuses graph1, Locuses graph2) throws ModuleParamException, IOException {

		try {
			double[][] matrix1 = ModuleHelper.convertRawToArray(ModuleHelper.getParameter("matrix", graph1)
					.getParamData());
			double[][] matrix2 = ModuleHelper.convertRawToArray(ModuleHelper.getParameter("matrix", graph2)
					.getParamData());

			if (matrix1.length != MATRIX_SIZE || (matrix1.length > 0 && matrix1[0].length != MATRIX_SIZE)) {
				throw new ModuleParamException("Received invalid parameter 'matrix' for first locus '"
						+ graph1.getName() + "'. Length of matrix does not equals of " + MATRIX_SIZE);
			}

			if (matrix2.length != MATRIX_SIZE || (matrix2.length > 0 && matrix2[0].length != MATRIX_SIZE)) {
				throw new ModuleParamException("Received invalid parameter 'matrix' for second locus '"
						+ graph2.getName() + "'. Length of matrix does not equals of " + MATRIX_SIZE);
			}

			double result = 0.0;
			for (int i = 0; i < MATRIX_SIZE; i++) {
				for (int j = 0; j < MATRIX_SIZE; j++) {
					result = result + Math.pow(matrix2[i][j] - matrix1[i][j], 2);
				}
			}
			result = result / Math.pow(MATRIX_SIZE, 2);

			return result;
		} catch (ClassNotFoundException cnfe) {
			throw new ModuleParamException("Error when casting parameters 'matrix' data", cnfe);
		}
	}

	public String getName() {
		return "Анализ матриц";
	}

}
