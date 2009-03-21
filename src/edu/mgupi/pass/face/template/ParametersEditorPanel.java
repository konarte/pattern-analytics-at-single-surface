package edu.mgupi.pass.face.template;

import java.awt.GridBagLayout;
import java.util.Collection;

import javax.swing.JPanel;

import edu.mgupi.pass.filters.Param;

public class ParametersEditorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Collection<Param> parameters = null;

	/**
	 * This is the default constructor
	 */
	public ParametersEditorPanel(Collection<Param> editableParameters) {
		super();
		this.parameters = editableParameters;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setName("paramEditorPanel");
	}

}
