package edu.mgupi.pass.modules;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.imageio.ImageIO;

import edu.mgupi.pass.db.locuses.LocusAppliedModule;
import edu.mgupi.pass.db.locuses.LocusModuleData;
import edu.mgupi.pass.db.locuses.LocusModuleDataFactory;
import edu.mgupi.pass.db.locuses.Locuses;

public class ModuleHelper {

	public static LocusModuleData getParameter(Locuses locus, String name) throws ModuleParamException {
		if (locus == null) {
			return null;
		}
		if (locus.getModule() == null) {
			return null;
		}
		return searchParameter(name, locus.getModule().getData(), true);
	}

	public final static int PARAM_TYPE_SERIALIZABLE = 1;
	public final static int PARAM_TYPE_IMAGE = 2;

	private final static String TMP_IMAGE_PARAM_NAME = "TEMPORARY_MODULE_IMAGE";

	public static void putTemporaryModuleImage(Locuses store, BufferedImage image) throws ModuleParamException,
			IOException {
		putParameterValue(store, TMP_IMAGE_PARAM_NAME, image);
	}

	public static BufferedImage getTemporaryModuleImage(Locuses store) throws ModuleParamException, IOException {
		return (BufferedImage) getParameterValue(store, TMP_IMAGE_PARAM_NAME, false);
	}

	public static void putParameterValue(Locuses store, String name, Object value) throws ModuleParamException {
		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store is null.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		if (value == null) {
			throw new IllegalArgumentException("Internal error. Value is null.");
		}

		LocusAppliedModule module = store.getModule();
		if (module == null) {
			throw new IllegalArgumentException("Internal error. Store.module is null.");
		}

		LocusModuleData param = searchParameter(name, module.getData(), false);
		if (param == null) {
			param = LocusModuleDataFactory.createLocusModuleData();
			param.setParamName(name);
			module.getData().add(param);
		}

		if (value instanceof BufferedImage) {
			param.setDataType(PARAM_TYPE_IMAGE);
		} else if (value instanceof Serializable) {
			param.setDataType(PARAM_TYPE_SERIALIZABLE);
		} else {
			throw new IllegalArgumentException("Internal error. Attempt to set non-serializable parameter '" + name
					+ "' " + " of class '" + value.getClass() + "'");
		}

		param.setObjectData(value);
	}

	public static Object getParameterValue(Locuses store, String name, boolean required) throws ModuleParamException,
			IOException {
		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store is null.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		if (store.getModule() == null) {
			throw new IllegalArgumentException("Internal error. Store.module is null.");
		}

		LocusModuleData param = searchParameter(name, store.getModule().getData(), required);
		if (param != null) {

			if (param.getObjectData() == null && param.getParamData() != null) {
				param.setObjectData(convertRawToObject(param.getParamData(), param.getDataType()));
			}

			return param.getObjectData();
		}
		return null;
	}

	protected static void finalyzeParams(Locuses store) throws IOException {
		if (store == null) {
			throw new IllegalArgumentException("Internal error. Store is null.");
		}
		if (store.getModule() == null) {
			throw new IllegalArgumentException("Internal error. Store.module is null.");
		}
		for (LocusModuleData param : store.getModule().getData()) {
			if (param.getObjectData() != null) {
				param.setParamData(convertObjectToRaw(param.getObjectData(), param.getDataType()));
				param.setObjectData(null);
			}
		}
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

	private static LocusModuleData searchParameter(String name, Collection<LocusModuleData> paramList, boolean mandatory)
			throws ModuleParamException {
		if (paramList == null) {
			return null;
		}

		if (name == null) {
			throw new IllegalArgumentException("Internal error. Name is null.");
		}
		for (LocusModuleData param : paramList) {
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

	public static byte[] convertImageToRaw(BufferedImage image) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "JPG", byteStream);
			return byteStream.toByteArray();
		} finally {
			byteStream.close();
		}

	}

	private static byte[] convertObjectToRaw(Object object, int type) throws IOException {

		if (type == PARAM_TYPE_IMAGE) {
			return convertImageToRaw((BufferedImage) object);
		}

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(byteStream);
		try {
			output.writeObject(object);
			return byteStream.toByteArray();
		} finally {
			output.close();
		}
	}

	public static BufferedImage covertRawToImage(byte[] data) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(data));
	}

	private static Object convertRawToObject(byte[] data, int type) throws IOException {
		if (type == PARAM_TYPE_IMAGE) {
			return covertRawToImage(data);
		}
		ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(data));
		try {
			return input.readObject();
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		} finally {
			input.close();
		}
	}

	//
	//	public static byte[] convertArrayToRaw(double[][] data) throws IOException {
	//		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	//		ObjectOutputStream objStream = new ObjectOutputStream(bytes);
	//		try {
	//			objStream.writeObject(data);
	//			return bytes.toByteArray();
	//		} finally {
	//			objStream.close();
	//		}
	//	}
	//
	//	public static double[][] convertRawToArray(byte[] data) throws IOException, ClassNotFoundException {
	//		ObjectInputStream objStream = new ObjectInputStream(new ByteArrayInputStream(data));
	//		try {
	//			return (double[][]) objStream.readObject();
	//		} finally {
	//			objStream.close();
	//		}
	//	}

}
