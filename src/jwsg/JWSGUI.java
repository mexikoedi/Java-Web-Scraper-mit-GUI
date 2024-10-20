package jwsg;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Image;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.JButton;

public class JWSGUI {
	// GUI Elemente
	private JFrame frmJavaWebScraper;

	/**
	 * Start der Anwendung.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JWSGUI window = new JWSGUI();
					window.frmJavaWebScraper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Anwendung erstellen.
	 */
	public JWSGUI() {
		initialize();
	}

	/**
	 * Inhalte vom Frame initialisieren.
	 */
	private void initialize() {
		// Fensteraussehen soll sich nach Betriebssystem anpassen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Frame erstellen
		frmJavaWebScraper = new JFrame();
		frmJavaWebScraper.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.setTitle("Java Web Scraper mit GUI");
		frmJavaWebScraper.setBounds(100, 100, 640, 360);
		frmJavaWebScraper.setMinimumSize(new Dimension(1025, 500));
		frmJavaWebScraper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Icon setzen
		Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("Icon.png"));
		this.frmJavaWebScraper.setIconImage(icon);
		// Panel erzeugen
		JPanel panel = new JPanel();
		panel.setFont(new Font("Calibri", Font.PLAIN, 14));
		panel.setPreferredSize(new Dimension(640, 360));
		// GridBagLayout verwenden, Spalten/Zeilen definieren und zum Panel hinzufügen
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 400, 0, 0, 100 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 237 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		panel.setLayout(gridBagLayout);
		// JTextArea textAreaAppExplanation erstellen, für GridBagLayout anpassen und
		// zum
		// Panel hinzufügen
		JTextArea textAreaAppExplanation = new JTextArea();
		textAreaAppExplanation.setBackground(new Color(240, 240, 240));
		textAreaAppExplanation.setBorder(null);
		textAreaAppExplanation.setFont(new Font("Calibri", Font.BOLD, 14));
		textAreaAppExplanation.setEnabled(false);
		textAreaAppExplanation.setWrapStyleWord(true);
		textAreaAppExplanation.setLineWrap(true);
		textAreaAppExplanation.setText(
				"Dieses Programm ermöglicht das unkomplizierte Abrufen von diversen HKA (Hochschule Karlsruhe – University of Applied Sciences) Informationen. Nutzen Sie die Dropdown-Liste, um nach einer oder mehreren Informationen zu suchen.");
		textAreaAppExplanation.setEditable(false);
		GridBagConstraints gbc_textAreaAppExplanation = new GridBagConstraints();
		gbc_textAreaAppExplanation.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAreaAppExplanation.gridwidth = 4;
		gbc_textAreaAppExplanation.insets = new Insets(5, 5, 5, 0);
		gbc_textAreaAppExplanation.gridx = 0;
		gbc_textAreaAppExplanation.gridy = 0;
		panel.add(textAreaAppExplanation, gbc_textAreaAppExplanation);
		// JSeparator separatorTop erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JSeparator separatorTop = new JSeparator();
		GridBagConstraints gbc_separatorTop = new GridBagConstraints();
		gbc_separatorTop.fill = GridBagConstraints.HORIZONTAL;
		gbc_separatorTop.gridwidth = 4;
		gbc_separatorTop.insets = new Insets(25, 0, 25, 0);
		gbc_separatorTop.gridx = 0;
		gbc_separatorTop.gridy = 1;
		panel.add(separatorTop, gbc_separatorTop);
		// JTextArea textAreaData erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JTextArea textAreaData = new JTextArea();
		textAreaData.setWrapStyleWord(true);
		textAreaData.setText("Suchwort aus dem Dropdown auswählen (Mehrfachauswahl):");
		textAreaData.setLineWrap(true);
		textAreaData.setFont(new Font("Calibri", Font.BOLD, 14));
		textAreaData.setEnabled(false);
		textAreaData.setEditable(false);
		textAreaData.setBorder(null);
		textAreaData.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_textAreaData = new GridBagConstraints();
		gbc_textAreaData.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAreaData.insets = new Insets(0, 5, 5, 5);
		gbc_textAreaData.gridx = 0;
		gbc_textAreaData.gridy = 2;
		gbc_textAreaData.gridwidth = 1;
		panel.add(textAreaData, gbc_textAreaData);
		// JComboBox<String> comboBoxData erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JComboBox<String> comboBoxData = new JComboBox<>();
		comboBoxData.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxData = new GridBagConstraints();
		gbc_comboBoxData.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxData.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxData.gridx = 1;
		gbc_comboBoxData.gridy = 2;
		gbc_comboBoxData.gridwidth = 2;
		panel.add(comboBoxData, gbc_comboBoxData);
		// JButton btnData erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JButton btnData = new JButton("Abrufen");
		btnData.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_btnData = new GridBagConstraints();
		gbc_btnData.gridx = 3;
		gbc_btnData.gridy = 2;
		gbc_btnData.insets = new Insets(0, 0, 5, 0);
		panel.add(btnData, gbc_btnData);
		// JSeparator separatorBottom erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JSeparator separatorBottom = new JSeparator();
		GridBagConstraints gbc_separatorBottom = new GridBagConstraints();
		gbc_separatorBottom.fill = GridBagConstraints.HORIZONTAL;
		gbc_separatorBottom.gridwidth = 4;
		gbc_separatorBottom.insets = new Insets(25, 0, 25, 0);
		gbc_separatorBottom.gridx = 0;
		gbc_separatorBottom.gridy = 3;
		panel.add(separatorBottom, gbc_separatorBottom);
		// JLabel lblData erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JLabel lblData = new JLabel("Daten");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblData = new GridBagConstraints();
		gbc_lblData.gridwidth = 4;
		gbc_lblData.insets = new Insets(0, 0, 5, 0);
		gbc_lblData.gridx = 0;
		gbc_lblData.gridy = 4;
		panel.add(lblData, gbc_lblData);
		// JTextArea txtrDataSearchExplanation erstellen, für GridBagLayout anpassen und
		// zum Panel hinzufügen
		JTextArea txtrDataSearchExplanation = new JTextArea();
		txtrDataSearchExplanation.setWrapStyleWord(true);
		txtrDataSearchExplanation.setText("Geben Sie ein Schlagwort ein, um die Daten zu durchsuchen:");
		txtrDataSearchExplanation.setLineWrap(true);
		txtrDataSearchExplanation.setFont(new Font("Calibri", Font.BOLD, 14));
		txtrDataSearchExplanation.setEnabled(false);
		txtrDataSearchExplanation.setEditable(false);
		txtrDataSearchExplanation.setBorder(null);
		txtrDataSearchExplanation.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_txtrDataSearchExplanation = new GridBagConstraints();
		gbc_txtrDataSearchExplanation.anchor = GridBagConstraints.NORTH;
		gbc_txtrDataSearchExplanation.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtrDataSearchExplanation.insets = new Insets(0, 5, 5, 5);
		gbc_txtrDataSearchExplanation.gridx = 0;
		gbc_txtrDataSearchExplanation.gridy = 6;
		gbc_txtrDataSearchExplanation.gridwidth = 1;
		panel.add(txtrDataSearchExplanation, gbc_txtrDataSearchExplanation);
		// JTextField textFieldDataSearch erstellen, für GridBagLayout anpassen und
		// zum Panel hinzufügen
		JTextField textFieldDataSearch = new JTextField();
		textFieldDataSearch.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDataSearch.setFont(new Font("Calibri", Font.PLAIN, 15));
		GridBagConstraints gbc_textFieldDataSearch = new GridBagConstraints();
		gbc_textFieldDataSearch.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDataSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDataSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDataSearch.gridx = 1;
		gbc_textFieldDataSearch.gridy = 6;
		gbc_textFieldDataSearch.gridwidth = 2;
		panel.add(textFieldDataSearch, gbc_textFieldDataSearch);
		textFieldDataSearch.setColumns(10);
		// JButton btnDataSearch erstellen, für GridBagLayout anpassen und zum
		// Panel hinzufügen
		JButton btnDataSearch = new JButton("Suchen");
		btnDataSearch.setFont(new Font("Calibri", Font.PLAIN, 14));
		GridBagConstraints gbc_btnDataSearch = new GridBagConstraints();
		gbc_btnDataSearch.anchor = GridBagConstraints.NORTH;
		gbc_btnDataSearch.gridx = 3;
		gbc_btnDataSearch.gridy = 6;
		panel.add(btnDataSearch, gbc_btnDataSearch);
		// JScrollPane scrollPane erstellen und zum Frame hinzufügen
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.getContentPane().add(scrollPane);
	}
}
