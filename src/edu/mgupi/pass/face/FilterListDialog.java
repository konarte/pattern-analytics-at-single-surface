package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import edu.mgupi.pass.db.locuses.LFilters;

public class FilterListDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPaneFilters = null;
	private JTable jTableFilters = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	public FilterListDialog() {
		this(null);
	}

	/**
	 * @param owner
	 */
	public FilterListDialog(Frame owner) {
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(457, 356);
		this.setTitle("Список фильтров");
		this.setContentPane(getJContentPane());
	}

	private String selectedClass = null; //  @jve:decl-index=0:
	private JPanel jPanelPlace = null;

	public String open() {
		this.setVisible(true);
		return selectedClass;
	}

	private void cancel() {
		selectedClass = null;
		this.setVisible(false);
	}

	private void pick() {
		selectedClass = String.valueOf(jTableFilters.getModel().getValueAt(jTableFilters.getSelectedRow(), 1));
		this.setVisible(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
			jContentPane.add(getJPanelPlace(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanelButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new FlowLayout());
			jPanelButtons.add(getJButtonOK(), null);
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	/**
	 * This method initializes jScrollPaneFilters
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneFilters() {
		if (jScrollPaneFilters == null) {
			jScrollPaneFilters = new JScrollPane();
			jScrollPaneFilters.setViewportView(getJTableFilters());
		}
		return jScrollPaneFilters;
	}

	/**
	 * This method initializes jTableFilters
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableFilters() {
		if (jTableFilters == null) {

			LFilters filters[] = MainFrameDataStorage.getInstance().listLFiltersIface();

			String cells[][] = new String[filters.length][2];
			for (int i = 0; i < filters.length; i++) {
				cells[i][0] = filters[i].getName();
				cells[i][1] = filters[i].getCodename();
			}

			jTableFilters = new JTable(cells, new String[] { "Название фильтра", "Используемый класс" }) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			jTableFilters.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		return jTableFilters;
	}

	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					FilterListDialog.this.pick();
				}
			});
			jButtonOK.setName("ok");
			jButtonOK.setText("OK");
		}
		return jButtonOK;
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setAction(new AbstractAction() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					FilterListDialog.this.cancel();
				}
			});
			jButtonCancel.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButtonCancel.setText("Отмена");
			jButtonCancel.setName("cancel");
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jPanelPlace
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelPlace() {
		if (jPanelPlace == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints.weightx = 1.0;
			jPanelPlace = new JPanel();
			jPanelPlace.setLayout(new GridBagLayout());
			jPanelPlace.add(getJScrollPaneFilters(), gridBagConstraints);
		}
		return jPanelPlace;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
