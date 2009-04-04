/*
 * Pattern Analytics at Single Surface
 * 
 * @(#)AboutDialog.java 1.0 16.03.2009
 */

package edu.mgupi.pass.face.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.gui.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.gui.template.JTableReadOnly;
import edu.mgupi.pass.util.Const;

/**
 * About dialog. Simple and easy.
 * 
 * @author raidan
 * 
 */
public class AboutDialog extends JDialog {

	private final static Logger logger = LoggerFactory.getLogger(AboutDialog.class);

	/**
	 * 
	 */

	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonClose = null;
	private JPanel jPanelData = null;
	private JLabel jLabelProgramTitle = null;

	/**
	 * @param owner
	 */
	public AboutDialog(Frame owner) {
		super(owner, true);
		initialize();
	}

	public void showDialog() {
		getDialogAdapter().openDialog();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(500, 550);
		this.setMinimumSize(new Dimension(500, 550));
		this.setName("aboutDialog");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle(Messages.getString("AboutDialog.title"));
		this.setContentPane(getJContentPane());
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
			jContentPane.add(getJPanelData(), BorderLayout.CENTER);
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
			jPanelButtons.add(getJButtonClose(), null);
		}
		return jPanelButtons;
	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}

		myDialogAdapter = new AbstractDialogAdapter(this) {
			@Override
			protected void cancelImpl() throws Exception {
				// do nothing
			}

			@Override
			protected void openDialogImpl() throws Exception {
				long maxMemory = Runtime.getRuntime().maxMemory();
				long totalMemory = Runtime.getRuntime().totalMemory();
				long freeMemory = Runtime.getRuntime().freeMemory();

				logger.debug("Max memory: {}, total memory: {}, free memory: {}.", new Object[] {
						maxMemory, totalMemory, freeMemory });

				/*
				 * Free memory = max memory - (total memory - free memory), i.e.
				 * free memory = max memory - current used memory.
				 */
				jLabelMemory.setText(Messages.getString("AboutDialog.memory",
						(maxMemory - (totalMemory - freeMemory)) / 1024 / 1024,
						maxMemory / 1024 / 1024));
			}

			@Override
			protected boolean saveImpl() throws Exception {
				// do nothing
				return false;
			}
		};

		return myDialogAdapter;
	}

	/**
	 * This method initializes jButtonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonClose() {
		if (jButtonClose == null) {
			jButtonClose = new JButton();
			jButtonClose.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonClose.setText("OK");
			getDialogAdapter().registerOKButton(jButtonClose);

		}
		return jButtonClose;
	}

	private JLabel jLabelAuthor = null;
	private JScrollPane jScrollPaneProps = null;
	private JTable jTableProps = null;
	private JScrollPane jScrollPaneLibraries = null;
	private JTable jTableLibraries = null;
	private JLabel jLabelProps = null;
	private JLabel jLabelLibraries = null;
	private JLabel jLabelMemory = null;

	private JLabel jLabelLink = null;

	/**
	 * This method initializes jPanelData
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelData() {
		if (jPanelData == null) {

			jLabelProgramTitle = new JLabel();
			jLabelProgramTitle.setText("<html><h3>" + Const.PROGRAM_NAME_FIRST + "<br>"
					+ Const.PROGRAM_NAME_LAST + "</h3>"
					+ "<b>Science content:</b> Konart<br><b>Code, design:</b> raidan</html>");

			jLabelLibraries = new JLabel();
			jLabelLibraries.setText(Messages.getString("AboutDialog.usedLibs"));
			jLabelLibraries.setFont(new Font("Dialog", Font.BOLD, 12));

			jLabelProps = new JLabel();
			jLabelProps.setText(Messages.getString("AboutDialog.sysProperties"));
			jLabelProps.setFont(new Font("Dialog", Font.BOLD, 12));

			jLabelMemory = new JLabel();

			jLabelAuthor = new JLabel();
			jLabelAuthor.setText("<html><hr>(c) raidan, Konart 2009</html>");
			jLabelAuthor.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelAuthor.setName("authors");

			jLabelLink = new JLabel();

			// Clicking on HTTP link
			// Direct to internet page with our project 
			jLabelLink.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					AppHelper.openLink(Const.WEB_PROJECT_PAGE);
				}

				@Override
				// Cursor with hand-point
				public void mouseEntered(MouseEvent e) {
					jLabelLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					jLabelLink.setCursor(Cursor.getDefaultCursor());
				}

			});
			jLabelLink.setText("<html><center><a href=\"" + Const.WEB_HELP_PAGE + "\">"
					+ Const.WEB_PROJECT_PAGE + "</a></html>");

			jPanelData = new JPanel();
			jPanelData.setLayout(new GridBagLayout());
			jPanelData.setEnabled(true);

			int y = 0;

			GridBagConstraints gbcProgramTitle = new GridBagConstraints();
			gbcProgramTitle.insets = new Insets(0, 15, 5, 15);
			gbcProgramTitle.weightx = 1.0D;
			gbcProgramTitle.anchor = GridBagConstraints.NORTHWEST;
			gbcProgramTitle.fill = GridBagConstraints.HORIZONTAL;
			gbcProgramTitle.gridx = 0;
			gbcProgramTitle.gridy = y++;
			jPanelData.add(jLabelProgramTitle, gbcProgramTitle);

			GridBagConstraints jbcLabelProps = new GridBagConstraints();
			jbcLabelProps.anchor = GridBagConstraints.CENTER;
			jbcLabelProps.insets = new Insets(0, 0, 0, 0);
			jbcLabelProps.gridx = 0;
			jbcLabelProps.gridy = y++;
			jPanelData.add(jLabelProps, jbcLabelProps);

			GridBagConstraints jbcPanelProps = new GridBagConstraints();
			jbcPanelProps.fill = GridBagConstraints.BOTH;
			jbcPanelProps.weightx = 1.0;
			jbcPanelProps.weighty = 2.0D;
			jbcPanelProps.insets = new Insets(0, 15, 0, 15);
			jbcPanelProps.gridx = 0;
			jbcPanelProps.gridy = y++;
			jPanelData.add(getJScrollPaneProps(), jbcPanelProps);

			GridBagConstraints jbcLibraries = new GridBagConstraints();
			jbcLibraries.anchor = GridBagConstraints.CENTER;
			jbcLibraries.insets = new Insets(5, 0, 0, 0);
			jbcLibraries.gridx = 0;
			jbcLibraries.gridy = y++;
			jPanelData.add(jLabelLibraries, jbcLibraries);

			GridBagConstraints jbcPanelLibraries = new GridBagConstraints();
			jbcPanelLibraries.fill = GridBagConstraints.BOTH;
			jbcPanelLibraries.weightx = 1.0;
			jbcPanelLibraries.weighty = 1.0D;
			jbcPanelLibraries.insets = new Insets(0, 15, 0, 15);
			jbcPanelLibraries.gridx = 0;
			jbcPanelLibraries.gridy = y++;
			jPanelData.add(getJScrollPaneLibraries(), jbcPanelLibraries);

			GridBagConstraints gbcMemory = new GridBagConstraints();
			gbcMemory.insets = new Insets(5, 15, 0, 15);
			gbcMemory.weightx = 1.0D;
			gbcMemory.anchor = GridBagConstraints.NORTHWEST;
			gbcMemory.fill = GridBagConstraints.HORIZONTAL;
			gbcMemory.gridx = 0;
			gbcMemory.gridy = y++;
			jPanelData.add(jLabelMemory, gbcMemory);

			GridBagConstraints jbcAuthor = new GridBagConstraints();
			jbcAuthor.insets = new Insets(10, 0, 0, 0);
			jbcAuthor.fill = GridBagConstraints.HORIZONTAL;
			jbcAuthor.weightx = 1.0D;
			jbcAuthor.anchor = GridBagConstraints.CENTER;
			jbcAuthor.gridwidth = 1;
			jbcAuthor.gridx = 0;
			jbcAuthor.gridy = y++;
			jPanelData.add(jLabelAuthor, jbcAuthor);

			GridBagConstraints jbcLink = new GridBagConstraints();
			jbcLink.insets = new Insets(0, 0, 10, 0);
			jbcLink.gridx = 0;
			jbcLink.gridy = y++;
			jPanelData.add(jLabelLink, jbcLink);

		}
		return jPanelData;
	}

	/**
	 * This method initializes jScrollPaneProps
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneProps() {
		if (jScrollPaneProps == null) {
			jScrollPaneProps = new JScrollPane();
			jScrollPaneProps.setViewportView(getJTableProps());
		}
		return jScrollPaneProps;
	}

	/**
	 * This method initializes jTableProps
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableProps() {
		if (jTableProps == null) {

			Properties props = System.getProperties();
			String list[] = props.keySet().toArray(new String[0]);
			Arrays.sort(list);

			String cells[][] = new String[props.size()][2];

			for (int i = 0; i < list.length; i++) {
				cells[i][0] = list[i];
				cells[i][1] = props.getProperty(cells[i][0]);
			}

			jTableProps = new JTableReadOnly(cells, new String[] {
					Messages.getString("AboutDialog.properties.prop"),
					Messages.getString("AboutDialog.properties.value") });
			jTableProps.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTableProps.setName("properties");
		}
		return jTableProps;
	}

	/**
	 * This method initializes jScrollPaneLibraries
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneLibraries() {
		if (jScrollPaneLibraries == null) {
			jScrollPaneLibraries = new JScrollPane();
			jScrollPaneLibraries.setViewportView(getJTableLibraries());
		}
		return jScrollPaneLibraries;
	}

	/**
	 * This method initializes jTableLibraries
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableLibraries() {
		if (jTableLibraries == null) {

			String libs[] = Const.USED_LIBRARIES.split(",");
			Arrays.sort(libs);

			String cells[][] = new String[libs.length][1];
			for (int i = 0; i < libs.length; i++) {
				cells[i][0] = libs[i];
			}

			jTableLibraries = new JTableReadOnly(cells, new String[] { Messages
					.getString("AboutDialog.libraries.lib") });
			jTableLibraries.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTableLibraries.setName("libraries");
		}
		return jTableLibraries;
	}
} //  @jve:decl-index=0:visual-constraint="7,1"
