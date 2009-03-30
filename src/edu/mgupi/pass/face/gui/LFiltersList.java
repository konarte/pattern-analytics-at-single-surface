package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.mgupi.pass.db.locuses.LFilters;
import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.gui.template.JTableReadOnly;

public class LFiltersList extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JScrollPane jScrollPaneData = null;
	private JTable jTableData = null;
	private JButton jButtonOK = null;
	private JButton jButtonCancel = null;

	/**
	 * @param owner
	 */
	public LFiltersList(Frame owner) {
		super(owner, true);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(400, 300);
		this.setName("lFiltersList");
		this.setMinimumSize(new Dimension(400, 300));
		this.setTitle(Messages.getString("LFiltersList.title"));
		this.setContentPane(getJContentPane());
	}

	private String selectedClass = null; //  @jve:decl-index=0:
	private JPanel jPanelPlace = null;

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}

		myDialogAdapter = new AbstractDialogAdapter(this, true) {

			@Override
			protected void cancelImpl() throws Exception {
				selectedClass = null;
			}

			@Override
			protected void openDialogImpl() throws Exception {
				jTableData.setRowSelectionInterval(0, 0);
				selectedClass = null;
			}

			@Override
			protected boolean saveImpl() throws Exception {
				selectedClass = null;
				int index = jTableData.getSelectedRow();
				if (index != -1) {
					selectedClass = (String) (jTableData.getModel().getValueAt(index,
							getDataColumnIndex()));
					return true;
				} else {
					return false;
				}
			}

		};

		return myDialogAdapter;
	}

	public String openDialog() {
		this.getDialogAdapter().openDialog();
		return selectedClass;
	}

	public void showDialogCancelOnly() {
		this.getDialogAdapter().showDialogCancelOnly();
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
			jContentPane.add(getJPanelPlace(), BorderLayout.CENTER);
			jContentPane.add(getJPanelButtons(), BorderLayout.SOUTH);
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
	 * This method initializes jScrollPaneData
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneData() {
		if (jScrollPaneData == null) {
			jScrollPaneData = new JScrollPane();
			jScrollPaneData.setViewportView(getJTableData());
		}
		return jScrollPaneData;
	}

	/**
	 * This method initializes jTableData
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableData() {
		if (jTableData == null) {

			jTableData = this.getTableDataImpl();
			jTableData.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						getDialogAdapter().save();
					}
				}
			});
			jTableData.setName("data"); //$NON-NLS-1$
		}

		return jTableData;
	}

	protected JTable getTableDataImpl() {
		LFilters filters[] = AppDataStorage.getInstance().listLFilters();

		String cells[][] = new String[filters.length][2];
		for (int i = 0; i < filters.length; i++) {
			cells[i][0] = filters[i].getName();
			cells[i][1] = filters[i].getCodename();
		}

		JTableReadOnly table = new JTableReadOnly(cells, new String[] {
				Messages.getString("LFiltersList.filterName"), //$NON-NLS-1$
				Messages.getString("LFiltersList.class") }); //$NON-NLS-1$
		return table;
	}

	protected int getDataColumnIndex() {
		return 1;
	}

	/**
	 * This method initializes jButtonOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonOK() {
		if (jButtonOK == null) {
			jButtonOK = new JButton();
			jButtonOK.setText("OK"); //$NON-NLS-1$
			getDialogAdapter().registerOKButton(jButtonOK);

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
			jButtonCancel.setText("cancel"); //$NON-NLS-1$
			getDialogAdapter().registerCancelButton(jButtonCancel);
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
			jPanelPlace.add(getJScrollPaneData(), gridBagConstraints);
		}
		return jPanelPlace;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
