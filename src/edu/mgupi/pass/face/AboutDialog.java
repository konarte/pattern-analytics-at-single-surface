package edu.mgupi.pass.face;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mgupi.pass.face.template.AbstractDialogAdapter;
import edu.mgupi.pass.face.template.JTableReadOnly;
import edu.mgupi.pass.util.Config;
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
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JPanel jPanelButtons = null;
	private JButton jButtonCancel = null;
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
		getDialogAdapter().showDialogCancelOnly();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(462, 526);
		this.setName("aboutDialog");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("О программе...");
		this.setContentPane(getJContentPane());
		Config.getInstance().loadWindowPosition(this);
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
			jPanelButtons.add(getJButtonCancel(), null);
		}
		return jPanelButtons;
	}

	private AbstractDialogAdapter myDialogAdapter = null; //  @jve:decl-index=0:

	private AbstractDialogAdapter getDialogAdapter() {
		if (myDialogAdapter != null) {
			return myDialogAdapter;
		}

		myDialogAdapter = new AbstractDialogAdapter(this, true) {
			@Override
			protected void cancelImpl() throws Exception {
				// do nothing
			}

			@Override
			protected void openDialogImpl() throws Exception {
				// do nothing
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
	private JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton();
			jButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonCancel.setText("cancel");
			getDialogAdapter().registerCancelButton(jButtonCancel);

		}
		return jButtonCancel;
	}

	private JLabel jLabelAuthor = null;
	private JScrollPane jScrollPaneProps = null;
	private JTable jTableProps = null;
	private JScrollPane jScrollPaneLibraries = null;
	private JTable jTableLibraries = null;
	private JLabel jLabelProps = null;
	private JLabel jLabelLibraries = null;

	/**
	 * This method initializes jPanelData
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelData() {
		if (jPanelData == null) {
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.anchor = GridBagConstraints.CENTER;
			gridBagConstraints41.insets = new Insets(5, 0, 0, 0);
			gridBagConstraints41.gridy = 3;
			jLabelLibraries = new JLabel();
			jLabelLibraries.setText("Используемые библиотеки");
			jLabelLibraries.setFont(new Font("Dialog", Font.BOLD, 12));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.anchor = GridBagConstraints.CENTER;
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 1;
			jLabelProps = new JLabel();
			jLabelProps.setText("Системные переменные");
			jLabelProps.setFont(new Font("Dialog", Font.BOLD, 12));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 4;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.insets = new Insets(0, 15, 0, 15);
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 2.0D;
			gridBagConstraints4.insets = new Insets(0, 15, 0, 15);
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(10, 0, 10, 0);
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.weightx = 1.0D;
			gridBagConstraints11.anchor = GridBagConstraints.CENTER;
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.gridy = 5;
			jLabelAuthor = new JLabel();

			// Clicking on HTTP link
			// Direct to internet page with our project 
			jLabelAuthor.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(Const.WEB_PROJECT_PAGE));
					} catch (Exception e1) {
						logger.debug("Error when opening link", e1);
						AppHelper.showExceptionDialog("Unexpected eror when opening project link '"
								+ Const.WEB_PROJECT_PAGE + "'.", e1);
					}
				}

				// Cursor with hand-point
				public void mouseEntered(MouseEvent e) {
					jLabelAuthor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}

				public void mouseReleased(MouseEvent e) {
					jLabelAuthor.setCursor(Cursor.getDefaultCursor());
				}

			});

			jLabelAuthor.setText("<html><center><hr>(c) raidan, Konart 2009</center>См. <a href=\""
					+ Const.WEB_HELP_PAGE + "\">" + Const.WEB_PROJECT_PAGE + "</a></html>");
			jLabelAuthor.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelAuthor.setHorizontalTextPosition(SwingConstants.TRAILING);
			jLabelAuthor.setPreferredSize(new Dimension(200, 40));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 15, 5, 15);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0D;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridx = 0;
			jLabelProgramTitle = new JLabel();
			jLabelProgramTitle.setText("<html><h3>" + Const.PROGRAM_NAME_FIRST + "<br>" + Const.PROGRAM_NAME_LAST
					+ "</h3>" + "<b>Science content:</b> Konart<br><b>Code, design:</b> raidan</html>");

			jPanelData = new JPanel();
			jPanelData.setLayout(new GridBagLayout());
			jPanelData.setEnabled(true);
			jPanelData.add(jLabelProgramTitle, gridBagConstraints);
			jPanelData.add(jLabelProps, gridBagConstraints3);
			jPanelData.add(getJScrollPaneProps(), gridBagConstraints4);
			jPanelData.add(getJScrollPaneLibraries(), gridBagConstraints2);
			jPanelData.add(jLabelAuthor, gridBagConstraints11);
			jPanelData.add(jLabelLibraries, gridBagConstraints41);
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

			jTableProps = new JTableReadOnly(cells, new String[] { "Property", "Value" });
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

			jTableLibraries = new JTableReadOnly(cells, new String[] { "Used library" });
			jTableLibraries.setName("libraries");
		}
		return jTableLibraries;
	}
} //  @jve:decl-index=0:visual-constraint="7,1"
