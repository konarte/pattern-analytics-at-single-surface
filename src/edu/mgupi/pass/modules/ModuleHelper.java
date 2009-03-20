package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

import javax.imageio.ImageIO;

import edu.mgupi.pass.db.locuses.LocusModuleParams;
import edu.mgupi.pass.db.locuses.LocusModuleParamsFactory;
import edu.mgupi.pass.db.locuses.Locuses;

public class ModuleHelper {

	public static LocusModuleParams getParameter(String name, Locuses locus) throws ModuleParamException {
		if (locus == null) {
			return null;
		}
		return searchParameter(name, locus.getParams(), true);
	}

	private final static String TMP_IMAGE_PARAM_NAME = "TEMPORARY_MODULE_IMAGE";

	public static void putTemporaryModuleImage(Locuses store, BufferedImage image) throws ModuleParamException,
			IOException {

		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store is null.");
		}
		if (image == null) {
			throw new IllegalArgumentException("Internal error. Image is null.");
		}

		LocusModuleParams param = searchParameter(TMP_IMAGE_PARAM_NAME, store.getParams(), false);
		if (param == null) {
			param = LocusModuleParamsFactory.createLocusModuleParams();
			param.setParamName(TMP_IMAGE_PARAM_NAME);
			store.getParams().add(param);
		}

		param.setParamData(convertImageToPNGRaw(image));
	}

	public static BufferedImage getTemporaryModuleImage(Locuses store) throws ModuleParamException, IOException {

		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store is null.");
		}

		LocusModuleParams param = searchParameter(TMP_IMAGE_PARAM_NAME, store.getParams(), false);
		if (param != null) {
			return covertPNGRawToImage(param.getParamData());
		}
		return null;
	}

	//
	// public static LocusModuleParams getParameter(String name,
	// Collection<LocusModuleParams> paramList)
	// throws ModuleParamException {
	// return searchParameter(name, paramList, true);
	// }
	//
	// public static LocusModuleParams searchParameter(String name,
	// Collection<LocusModuleParams> paramList)
	// throws ModuleParamException {
	// return searchParameter(name, paramList, false);
	// }

	private static LocusModuleParams searchParameter(String name, Collection<LocusModuleParams> paramList,
			boolean mandatory) throws ModuleParamException {
		if (paramList == null) {
			return null;
		}

		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		for (LocusModuleParams param : paramList) {
			if (name.equals(param.getParamName())) {
				return param;
			}
		}

		if (mandatory) {
			throw new ModuleParamException("Unable to find module parameter '" + name + "' into parameters list '"
					+ paramList + "'. This parameter is required.");
		}

		return null;
	}

	public static byte[] convertImageToPNGRaw(BufferedImage image) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", byteStream);
		byte[] data = byteStream.toByteArray();
		byteStream.close();
		return data;
	}

	public static BufferedImage covertPNGRawToImage(byte[] data) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(data));
	}

	public static byte[] convertArrayToRaw(double[][] data) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream objStream = new ObjectOutputStream(bytes);
		try {
			objStream.writeObject(data);
			return bytes.toByteArray();
		} finally {
			objStream.close();
		}
	}

	public static double[][] convertRawToArray(byte[] data) throws IOException, ClassNotFoundException {
		ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(data));
		try {
			return (double[][]) objStream.readObject();
		} finally {
			objStream.close();
		}
	}

}
