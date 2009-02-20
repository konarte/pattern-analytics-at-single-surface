package edu.mgupi.pass.sources;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class SourceStore {
	private Map<String, Object> options = new HashMap<String, Object>();
	private Image mainImage;

	public SourceStore(Map<String, Object> options, Image mainImage) {
		this.options = options;
		this.mainImage = mainImage;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public Image getMainImage() {
		return mainImage;
	}
}
