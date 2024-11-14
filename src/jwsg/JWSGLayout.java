package jwsg;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.GridBagConstraints;
import java.awt.Image;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JSeparator;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class JWSGLayout {
	// GUI/Layout Elemente
	private JFrame frmJavaWebScraper;
	private Image icon;
	private JPanel panel;
	private GridBagLayout gridBagLayout;
	private JTextArea textAreaAppExplanation;
	private GridBagConstraints gbc_textAreaAppExplanation;
	private JSeparator separatorTop;
	private GridBagConstraints gbc_separatorTop;
	private JTextArea textAreaData;
	private GridBagConstraints gbc_textAreaData;
	private JButton btnDropdown;
	private GridBagConstraints gbc_btnDropdown;
	private JList<String> listData;
	private JPopupMenu popupMenu;
	private JScrollPane popupScrollPane;
	private JButton btnData;
	private GridBagConstraints gbc_btnData;
	private JSeparator separatorBottom;
	private GridBagConstraints gbc_separatorBottom;
	private JLabel lblData;
	private GridBagConstraints gbc_lblData;
	private JTextArea txtAreaDataSearchExplanation;
	private GridBagConstraints gbc_textAreaDataSearchExplanation;
	private JTextField textFieldDataSearch;
	private GridBagConstraints gbc_textFieldDataSearch;
	private JButton btnDataSearch;
	private GridBagConstraints gbc_btnDataSearch;
	private JScrollPane scrollPane;
	private JTextArea dataTextArea;
	private JScrollPane dataScrollPane;
	private GridBagConstraints gbc_dataTextArea;
	// Date
	private LocalDate currentDate = LocalDate.now();
	private int currentYear = currentDate.getYear();

	/**
	 * Start der Anwendung. Erzeugt das Fenster und ansonsten erscheint ein Fehler.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JWSGLayout window = new JWSGLayout();
					window.frmJavaWebScraper.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unerwarteter Startfehler! Bitte erneut versuchen.", "Fehler",
							JOptionPane.ERROR_MESSAGE);

					System.exit(1);
				}
			}
		});
	}

	/**
	 * Standardkonstruktur. Inititialisierung der Anwendung, um die GUI zu
	 * erstellen.
	 */
	public JWSGLayout() {
		initialize();
	}

	/**
	 * Gescrapte Daten werden formatiert und in einer JTextArea angezeigt.
	 * 
	 * @param selectedCategories Die Liste der ausgewählten Kategorien.
	 * @param scrapedData        Die gescrapten Daten.
	 */
	public void initData(List<String> selectedCategories, Map<String, List<String>> scrapedData) {
		StringBuilder dataBuilder = new StringBuilder();

		// Durch alle Kategorien durchgehen, formatieren und Daten hinzufügen
		for (String category : selectedCategories) {
			List<String> details = scrapedData.get(category);

			if (details != null && !details.isEmpty()) {
				dataBuilder.append("=".repeat(category.length() + 2)).append("\n").append(category.toUpperCase())
						.append("\n").append("=".repeat(category.length() + 2)).append("\n");

				for (String detail : details) {
					if (!detail.isEmpty()) {
						dataBuilder.append(detail).append("\n");
					}
				}

				dataBuilder.append("\n");
			}
		}

		// JTextArea dataTextArea für GridBagLayout anpassen, Scrollleiste hinzufügen,
		// zum Panel hinzufügen, Text setzen und panel aktualisieren
		dataTextArea = new JTextArea();
		dataTextArea.setWrapStyleWord(true);
		dataTextArea.setLineWrap(true);
		dataTextArea.setFont(new Font("Calibri", Font.BOLD, 14));
		dataTextArea.setEditable(false);
		dataTextArea.setEnabled(false);
		dataTextArea.setText(dataBuilder.toString().trim());
		dataScrollPane = new JScrollPane(dataTextArea);
		gbc_dataTextArea = new GridBagConstraints();
		gbc_dataTextArea.fill = GridBagConstraints.BOTH;
		gbc_dataTextArea.gridwidth = 4;
		gbc_dataTextArea.insets = new Insets(0, 5, 5, 5);
		gbc_dataTextArea.gridx = 0;
		gbc_dataTextArea.gridy = 7;
		gbc_dataTextArea.weighty = 10.0;
		panel.add(dataScrollPane, gbc_dataTextArea);
		panel.revalidate();
		panel.repaint();
	}

	/**
	 * GUI-Inhalte vom Frame werden initialisiert und das Fenster wird nach dem OS
	 * angepasst, ansonsten wird ein Fehler ausgegeben.
	 */
	private void initialize() {
		// Logic-Objekt erstellen für ActionListener
		JWSGLogic logic = new JWSGLogic();

		// Fensteraussehen soll sich nach Betriebssystem anpassen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unerwarteter Fehler der UI-Anpassung! Bitte erneut versuchen.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			System.exit(1);
		}

		// Frame erstellen und anpassen
		frmJavaWebScraper = new JFrame();
		frmJavaWebScraper.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.setTitle("Java Web Scraper mit GUI");
		frmJavaWebScraper.setBounds(100, 100, 640, 360);
		frmJavaWebScraper.setMinimumSize(new Dimension(1025, 500));
		frmJavaWebScraper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Icon setzen
		icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("Icon.png"));
		this.frmJavaWebScraper.setIconImage(icon);
		// Panel erzeugen und anpassen
		panel = new JPanel();
		panel.setFont(new Font("Calibri", Font.PLAIN, 14));
		panel.setPreferredSize(new Dimension(640, 360));
		// GridBagLayout verwenden, Spalten/Zeilen definieren und zum Panel hinzufügen
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 400, 0, 0, 100 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		panel.setLayout(gridBagLayout);
		// JTextArea textAreaAppExplanation für GridBagLayout anpassen und zum Panel
		// hinzufügen
		textAreaAppExplanation = new JTextArea();
		textAreaAppExplanation.setBackground(new Color(240, 240, 240));
		textAreaAppExplanation.setBorder(null);
		textAreaAppExplanation.setFont(new Font("Calibri", Font.BOLD, 14));
		textAreaAppExplanation.setEnabled(false);
		textAreaAppExplanation.setWrapStyleWord(true);
		textAreaAppExplanation.setLineWrap(true);
		textAreaAppExplanation.setText(
				"Dieses Programm ermöglicht das unkomplizierte Abrufen von diversen HKA (Hochschule Karlsruhe – University of Applied Sciences) Informationen. Nutzen Sie die Dropdown-Liste, um nach einer oder mehreren Informationen zu suchen."
						+ "\n© 2024-" + currentYear + " mexikoedi");
		textAreaAppExplanation.setEditable(false);
		gbc_textAreaAppExplanation = new GridBagConstraints();
		gbc_textAreaAppExplanation.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAreaAppExplanation.gridwidth = 4;
		gbc_textAreaAppExplanation.insets = new Insets(5, 5, 0, 0);
		gbc_textAreaAppExplanation.gridx = 0;
		gbc_textAreaAppExplanation.gridy = 0;
		panel.add(textAreaAppExplanation, gbc_textAreaAppExplanation);
		// JSeparator separatorTop für GridBagLayout anpassen und zum Panel hinzufügen
		separatorTop = new JSeparator();
		gbc_separatorTop = new GridBagConstraints();
		gbc_separatorTop.fill = GridBagConstraints.HORIZONTAL;
		gbc_separatorTop.gridwidth = 4;
		gbc_separatorTop.insets = new Insets(25, 0, 25, 0);
		gbc_separatorTop.gridx = 0;
		gbc_separatorTop.gridy = 1;
		panel.add(separatorTop, gbc_separatorTop);
		// JTextArea textAreaData für GridBagLayout anpassen und zum Panel hinzufügen
		textAreaData = new JTextArea();
		textAreaData.setWrapStyleWord(true);
		textAreaData.setText("Suchwort aus dem Dropdown auswählen (Mehrfachauswahl):");
		textAreaData.setLineWrap(true);
		textAreaData.setFont(new Font("Calibri", Font.BOLD, 14));
		textAreaData.setEnabled(false);
		textAreaData.setEditable(false);
		textAreaData.setBorder(null);
		textAreaData.setBackground(UIManager.getColor("Button.background"));
		gbc_textAreaData = new GridBagConstraints();
		gbc_textAreaData.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAreaData.insets = new Insets(0, 5, 5, 5);
		gbc_textAreaData.gridx = 0;
		gbc_textAreaData.gridy = 2;
		gbc_textAreaData.gridwidth = 1;
		panel.add(textAreaData, gbc_textAreaData);
		// JButton btnDropdown für GridBagLayout anpassen und zum Panel hinzufügen
		btnDropdown = new JButton("Suchwörter auswählen");
		btnDropdown.setFont(new Font("Calibri", Font.PLAIN, 14));
		gbc_btnDropdown = new GridBagConstraints();
		gbc_btnDropdown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDropdown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDropdown.gridx = 1;
		gbc_btnDropdown.gridy = 2;
		gbc_btnDropdown.gridwidth = 2;
		panel.add(btnDropdown, gbc_btnDropdown);
		// JList<String> listData befüllen und anpassen
		listData = new JList<>(this.initList());
		listData.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listData.setVisibleRowCount(5);
		listData.setFont(new Font("Calibri", Font.PLAIN, 14));
		// JPopupMenu popupMenu anpassen und Scrollleiste hinzufügen
		popupScrollPane = new JScrollPane(listData);
		popupMenu = new JPopupMenu();
		popupMenu.add(popupScrollPane);
		// ActionListener für JButton btnDropdown hinzufügen und JScrollPane
		// popupScrollPane anpassen
		btnDropdown.addActionListener(e -> {
			int buttonWidth = btnDropdown.getWidth() - 5;
			int rowHeight = listData.getCellBounds(0, 0).height;
			int visibleRowCount = listData.getModel().getSize();
			int popupHeight = visibleRowCount * rowHeight;
			popupScrollPane.setPreferredSize(new Dimension(buttonWidth, popupHeight));
			popupMenu.show(btnDropdown, 0, btnDropdown.getHeight());
		});
		// ListSelectionListener für JList<String> listData hinzufügen
		listData.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				List<String> selectedValues = listData.getSelectedValuesList();
				String selectedText = String.join(", ", selectedValues);
				btnDropdown.setText(selectedText.isEmpty() ? "Optionen auswählen" : selectedText);
			}
		});
		// JButton btnData für GridBagLayout anpassen, ActionListener einführen und zum
		// Panel hinzufügen
		btnData = new JButton("Abrufen");
		btnData.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnData.addActionListener(e -> {
			boolean scraped = logic.checkButtonPressed(listData.getSelectedValuesList());

			if (dataTextArea != null && scraped) {
				panel.remove(dataScrollPane);
			}

			if (scraped) {
				initData(listData.getSelectedValuesList(), logic.getScrapedDataMap());
			}
		});
		gbc_btnData = new GridBagConstraints();
		gbc_btnData.gridx = 3;
		gbc_btnData.gridy = 2;
		gbc_btnData.insets = new Insets(0, 0, 5, 0);
		panel.add(btnData, gbc_btnData);
		// JSeparator separatorBottom für GridBagLayout anpassen und zum Panel
		// hinzufügen
		separatorBottom = new JSeparator();
		gbc_separatorBottom = new GridBagConstraints();
		gbc_separatorBottom.fill = GridBagConstraints.HORIZONTAL;
		gbc_separatorBottom.gridwidth = 4;
		gbc_separatorBottom.insets = new Insets(25, 0, 25, 0);
		gbc_separatorBottom.gridx = 0;
		gbc_separatorBottom.gridy = 3;
		panel.add(separatorBottom, gbc_separatorBottom);
		// JLabel lblData für GridBagLayout anpassen und zum Panel hinzufügen
		lblData = new JLabel("Daten");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setFont(new Font("Calibri", Font.BOLD, 14));
		gbc_lblData = new GridBagConstraints();
		gbc_lblData.gridwidth = 4;
		gbc_lblData.insets = new Insets(0, 0, 5, 0);
		gbc_lblData.gridx = 0;
		gbc_lblData.gridy = 4;
		panel.add(lblData, gbc_lblData);
		// JTextArea txtAreaDataSearchExplanation für GridBagLayout anpassen und zum
		// Panel hinzufügen
		txtAreaDataSearchExplanation = new JTextArea();
		txtAreaDataSearchExplanation.setWrapStyleWord(true);
		txtAreaDataSearchExplanation.setText("Geben Sie ein Schlagwort ein, um die Daten zu durchsuchen:");
		txtAreaDataSearchExplanation.setLineWrap(true);
		txtAreaDataSearchExplanation.setFont(new Font("Calibri", Font.BOLD, 14));
		txtAreaDataSearchExplanation.setEnabled(false);
		txtAreaDataSearchExplanation.setEditable(false);
		txtAreaDataSearchExplanation.setBorder(null);
		txtAreaDataSearchExplanation.setBackground(UIManager.getColor("Button.background"));
		gbc_textAreaDataSearchExplanation = new GridBagConstraints();
		gbc_textAreaDataSearchExplanation.anchor = GridBagConstraints.NORTH;
		gbc_textAreaDataSearchExplanation.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAreaDataSearchExplanation.insets = new Insets(0, 5, 5, 5);
		gbc_textAreaDataSearchExplanation.gridx = 0;
		gbc_textAreaDataSearchExplanation.gridy = 6;
		gbc_textAreaDataSearchExplanation.gridwidth = 1;
		panel.add(txtAreaDataSearchExplanation, gbc_textAreaDataSearchExplanation);
		// JTextField textFieldDataSearch für GridBagLayout anpassen und zum Panel
		// hinzufügen
		textFieldDataSearch = new JTextField();
		textFieldDataSearch.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDataSearch.setFont(new Font("Calibri", Font.PLAIN, 15));
		gbc_textFieldDataSearch = new GridBagConstraints();
		gbc_textFieldDataSearch.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDataSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDataSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDataSearch.gridx = 1;
		gbc_textFieldDataSearch.gridy = 6;
		gbc_textFieldDataSearch.gridwidth = 2;
		panel.add(textFieldDataSearch, gbc_textFieldDataSearch);
		textFieldDataSearch.setColumns(10);
		// JButton btnDataSearch für GridBagLayout anpassen und zum Panel hinzufügen
		btnDataSearch = new JButton("Suchen");
		btnDataSearch.setFont(new Font("Calibri", Font.PLAIN, 14));
		gbc_btnDataSearch = new GridBagConstraints();
		gbc_btnDataSearch.anchor = GridBagConstraints.NORTH;
		gbc_btnDataSearch.gridx = 3;
		gbc_btnDataSearch.gridy = 6;
		panel.add(btnDataSearch, gbc_btnDataSearch);
		// JScrollPane scrollPane zum Frame hinzufügen
		scrollPane = new JScrollPane(panel);
		scrollPane.setFont(new Font("Calibri", Font.PLAIN, 14));
		frmJavaWebScraper.getContentPane().add(scrollPane);
	}

	/**
	 * JList wird mit den vordefinierten Suchwörtern initialisiert.
	 * 
	 * @return DefaultListModel<String> welche die Liste mit den initialen Inhalten
	 *         enthält.
	 */
	private DefaultListModel<String> initList() {
		DefaultListModel<String> listModel = new DefaultListModel<>();

		for (String keyword : JWSGScrapingConfig.getKeywordUrlMap().keySet()) {
			listModel.addElement(keyword);
		}

		return listModel;
	}
}