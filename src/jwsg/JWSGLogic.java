package jwsg;

// Importe
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JWSGLogic {
	// Datenstrukturen
	private static final Map<String, List<String>> scrapedDataMap = new ConcurrentHashMap<>();
	private static List<String> lastSelectedCategories = new ArrayList<>();

	/**
	 * Diese Methode wird verwendet, um die Daten zu erhalten, die von den
	 * ausgewählten Suchwörtern abhängen.
	 * 
	 * @return Die Map, die die Suchwörter und die zugehörigen Daten enthält.
	 */
	public Map<String, List<String>> getScrapedDataMap() {
		return scrapedDataMap;
	}

	/**
	 * Diese Methode wird verwendet, um zu überprüfen, ob die Schaltfläche "Abrufen"
	 * gedrückt wurde. Zudem werden die letzten ausgewählten Suchwörter gespeichert.
	 * Enthält verschiedene Überprüfungen, um Sonderfälle abzudecken.
	 * 
	 * @param list Die Liste der ausgewählten Suchwörter.
	 * @return true, wenn die Schaltfläche gedrückt wurde und keiner der Sonderfälle
	 *         aufgetreten ist, sonst false.
	 */
	public boolean checkButtonPressed(List<String> list) {
		if (list.isEmpty()) {
			showDialog("Bitte wählen Sie mindestens ein Suchwort aus!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		}

		if (list.equals(lastSelectedCategories) && list.size() == 1) {
			showDialog("Sie haben schon die Daten für dieses Suchwort!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		} else if (list.equals(lastSelectedCategories) && list.size() > 1) {
			showDialog("Sie haben schon die Daten für diese Suchwörter!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		}

		scrapData(list);
		lastSelectedCategories = new ArrayList<>(list);

		return true;
	}

	/**
	 * Diese Methode wird verwendet, um die Daten von den ausgewählten Suchwörtern
	 * mit ihren entsprechenden URLs parallel abzurufen.
	 *
	 * @param categories Die Liste der ausgewählten Suchwörter.
	 */
	private void scrapData(List<String> categories) {
		// Map leeren
		scrapedDataMap.clear();

		// Maximale Anzahl von Anfragen vor der Pause
		final int batchSize = 25;
		// Randomverzögerung zwischen 5 und 8 Sekunden für die Pause
		final int delayMillis = (int) Math.floor(Math.random() * (8000 - 5000 + 1) + 5000);
		/*
		 * AtomicInteger wird verwendet, um Anfragen sicher über mehrere Threads zu
		 * zählen. Dies stellt sicher, dass jeder Thread den Zähler ohne
		 * Wettlaufbedingungen erhöht.
		 */
		AtomicInteger requestCounter = new AtomicInteger(0);

		categories.parallelStream().forEach(category -> {
			/*
			 * Synchronieren der Verzögerung, um zu vermeiden, dass mehrere Threads
			 * gleichzeitig pausieren
			 */
			synchronized (requestCounter) {
				// Pause, wenn der Anforderungszähler die Batchgröße erreicht
				if (requestCounter.incrementAndGet() % batchSize == 0) {
					try {
						Thread.sleep(delayMillis);
					} catch (InterruptedException e) {
						// Wiederherstellen des unterbrochenen Status
						Thread.currentThread().interrupt();
					}
				}
			}

			String baseUrl = JWSGScrapingConfig.getUrl(category);
			String elementClass = JWSGScrapingConfig.getElementClass(category);
			String container = JWSGScrapingConfig.getContainer(category);
			String id = JWSGScrapingConfig.getId(category);
			String tag = JWSGScrapingConfig.getTag(category);
			String linkSelector = JWSGScrapingConfig.getSelector(category);
			String titleSelector = JWSGScrapingConfig.getBulletinBoardTitleSelector(category);
			String dateSelector = JWSGScrapingConfig.getBulletinBoardDateSelector(category);
			String contentSelector = JWSGScrapingConfig.getBulletinBoardContentSelector(category);
			String subcontentSelector = JWSGScrapingConfig.getBulletinBoardSubcontentSelector(category);
			String nameSelector = JWSGScrapingConfig.getPersonNameSelector(category);
			String groupSelector = JWSGScrapingConfig.getPersonGroupSelector(category);
			String emailSelector = JWSGScrapingConfig.getPersonEmailSelector(category);
			String programElementClass = JWSGScrapingConfig.getProgramElementClass();
			String dateElementClass = JWSGScrapingConfig.getDateElementClass();
			String bulletinBoardElementClass = JWSGScrapingConfig.getBulletinBoardElementClass();
			String personElementClass = JWSGScrapingConfig.getPersonElementClass();
			String personPaginationToken = JWSGScrapingConfig.getPersonPaginationToken();
			String personPaginationFormat = JWSGScrapingConfig.getPersonPaginationFormat();
			String url = null;
			Document website = null;
			Elements websiteElements = null;
			List<String> pageData = new ArrayList<>();
			List<String> allScrapedData = new ArrayList<>();
			List<String> scrapedData = new ArrayList<>();

			try {
				if (elementClass.equals(personElementClass)) {
					int currentPage = 1;

					while (true) {
						url = (currentPage == 1) ? baseUrl
								: baseUrl.replace(personPaginationToken,
										String.format(personPaginationFormat, currentPage));

						try {
							website = Jsoup.connect(url).get();
						} catch (IOException e) {
							showDialog(
									"Die URL " + url
											+ " ist nicht verfügbar! Tippfehler? Versuchen Sie es später erneut.",
									"Fehler", JOptionPane.ERROR_MESSAGE);

							break;
						}

						websiteElements = website.getElementsByClass(elementClass);

						if (websiteElements == null || websiteElements.isEmpty()) {
							break;
						}

						pageData = processPersonData(website, category, elementClass, tag, nameSelector, groupSelector,
								emailSelector);

						if (pageData.isEmpty()) {
							break;
						}

						allScrapedData.addAll(pageData);
						currentPage++;
					}

					scrapedDataMap.put(category, allScrapedData);
				} else {
					website = Jsoup.connect(baseUrl).get();

					if (elementClass.equals(programElementClass)) {
						scrapedData = processProgramData(website, category, elementClass, linkSelector);
					} else if (elementClass.equals(dateElementClass)) {
						scrapedData = processDateData(website, category, elementClass, container, id, tag);
					} else if (elementClass.equals(bulletinBoardElementClass)) {
						scrapedData = processBulletinBoardData(website, category, elementClass, titleSelector,
								dateSelector, contentSelector, subcontentSelector);
					} else {
						if (websiteElements == null || websiteElements.isEmpty()) {
							showDialog(
									"Keine Daten für " + category + " für das Element " + elementClass
											+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
									"Fehler", JOptionPane.ERROR_MESSAGE);

							return;
						}
					}

					scrapedDataMap.put(category, scrapedData);
				}
			} catch (IOException e) {
				showDialog("Fehler beim Abrufen der Daten für " + category
						+ "! Tippfehler? Versuchen Sie es später erneut.", "Fehler", JOptionPane.ERROR_MESSAGE);

				return;
			}
		});
	}

	/**
	 * Diese Methode wird verwendet, um die Daten der Studiengänge zu extrahieren
	 * und zu verarbeiten.
	 * 
	 * @param website      Die Webseite, von der die Daten extrahiert werden sollen.
	 * @param category     Die Kategorie des Suchworts (Studiengänge).
	 * @param elementClass Die Klasse der Elemente, die die Daten enthalten.
	 * @param linkSelector Der Selektor für spezifische Links innerhalb der
	 *                     Elemente.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processProgramData(Document website, String category, String elementClass,
			String linkSelector) {
		Elements websiteElements = website.getElementsByClass(elementClass);
		Element linkElement = null;
		List<String> scrapedData = new ArrayList<>();

		for (Element element : websiteElements) {
			if (linkSelector == null || linkSelector.isEmpty()) {
				showDialog("Kein Link Selector für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			linkElement = element.selectFirst(linkSelector);

			if (linkElement != null) {
				scrapedData.add(linkElement.ownText().trim());
			} else {
				showDialog(
						"Keine Daten für " + category + " für den Link Selector " + linkSelector
								+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
						"Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}
		}

		return scrapedData;
	}

	/**
	 * Diese Methode wird verwendet, um die Daten der Semestertermine zu extrahieren
	 * und zu verarbeiten.
	 * 
	 * @param website      Die Webseite, von der die Daten extrahiert werden sollen.
	 * @param category     Die Kategorie des Suchworts (Semestertermine).
	 * @param elementClass Die Klasse der Elemente, die die Daten enthalten.
	 * @param container    Der Container für die jeweiligen Suchwörter.
	 * @param id           Die ID für die jeweiligen Suchwörter.
	 * @param tag          Der Tag für die jeweiligen Suchwörter
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processDateData(Document website, String category, String elementClass, String container,
			String id, String tag) {
		Elements websiteElements = website.getElementsByClass(elementClass);
		Elements containerElements = null;
		Elements tagElements = null;
		List<String> scrapedData = new ArrayList<>();
		boolean idMatched = false;

		for (Element element : websiteElements) {
			if (container == null || container.isEmpty()) {
				showDialog("Kein Container für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			containerElements = element.getElementsByClass(container);

			if (containerElements == null || containerElements.isEmpty()) {
				showDialog(
						"Keine Daten für " + category + " für den Container " + container
								+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
						"Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			for (Element containerElement : containerElements) {
				if (id == null || id.isEmpty()) {
					showDialog("Keine ID für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

					return new ArrayList<>();
				}

				if (containerElement.id().equals(id)) {
					idMatched = true;

					if (tag == null || tag.isEmpty()) {
						showDialog("Kein Tag für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

						return new ArrayList<>();
					}

					tagElements = containerElement.getElementsByTag(tag);

					if (tagElements == null || tagElements.isEmpty()) {
						showDialog(
								"Keine Daten für " + category + " für den Tag " + tag
										+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
								"Fehler", JOptionPane.ERROR_MESSAGE);

						return new ArrayList<>();
					}

					for (Element tagElement : tagElements) {
						scrapedData.add(tagElement.text().trim());
					}
				}
			}
		}

		if (!idMatched) {
			showDialog(
					"Keine Daten für " + category + " für die ID " + id
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		return scrapedData;
	}

	/**
	 * Diese Methode wird verwendet, um die Daten der Schwarzen Bretter zu
	 * extrahieren und zu verarbeiten.
	 * 
	 * @param website            Die Webseite, von der die Daten extrahiert werden
	 *                           sollen.
	 * @param category           Die Kategorie des Suchworts (Schwarze Bretter).
	 * @param elementClass       Die Klasse der Elemente, die die Daten enthalten.
	 * @param titleSelector      Der Selektor für die Titel der Schwarzen Bretter.
	 * @param dateSelector       Der Selektor für die Daten der Schwarzen Bretter.
	 * @param contentSelector    Der Selektor für den Inhalt der Schwarzen Bretter.
	 * @param subcontentSelector Der Selektor für den Unterinhalt der Schwarzen
	 *                           Bretter.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processBulletinBoardData(Document website, String category, String elementClass,
			String titleSelector, String dateSelector, String contentSelector, String subcontentSelector) {
		Elements websiteElements = website.getElementsByClass(elementClass);
		Element titleElement = null;
		Element dateElement = null;
		Element contentElement = null;
		Element subcontentElement = null;
		List<String> scrapedData = new ArrayList<>();
		boolean foundValidTitle = false;
		boolean foundValidDate = false;
		boolean foundValidContent = false;
		boolean foundValidSubcontent = false;

		for (Element element : websiteElements) {
			if (titleSelector == null || titleSelector.isEmpty()) {
				showDialog("Kein Title Selector für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			titleElement = element.selectFirst(titleSelector);

			if (titleElement != null) {
				foundValidTitle = true;
			}

			if (dateSelector == null || dateSelector.isEmpty()) {
				showDialog("Kein Date Selector für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			dateElement = element.selectFirst(dateSelector);

			if (dateElement != null) {
				foundValidDate = true;
			}

			if (contentSelector == null || contentSelector.isEmpty()) {
				showDialog("Kein Content Selector für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			contentElement = element.selectFirst(contentSelector);

			if (contentElement != null) {
				foundValidContent = true;
			}

			if (subcontentSelector == null || subcontentSelector.isEmpty()) {
				showDialog("Kein Subcontent Selector für " + category + " gefunden!", "Fehler",
						JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			subcontentElement = element.selectFirst(subcontentSelector);

			if (subcontentElement != null) {
				foundValidSubcontent = true;
			}

			if (titleElement == null || dateElement == null || contentElement == null || subcontentElement == null) {
				continue;
			}

			if (titleElement != null) {
				scrapedData.add(titleElement.text().trim());
			}

			if (dateElement != null) {
				scrapedData.add(dateElement.text().trim());
			}

			if (contentElement != null) {
				scrapedData.add(contentElement.text().trim());
			}

			if (subcontentElement != null) {
				scrapedData.add(subcontentElement.text().trim());
			}
		}

		if (!foundValidTitle) {
			showDialog(
					"Keine Daten für " + category + " für den Title Selector " + titleSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		if (!foundValidDate) {
			showDialog(
					"Keine Daten für " + category + " für den Date Selector " + dateSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		if (!foundValidContent) {
			showDialog(
					"Keine Daten für " + category + " für den Content Selector " + contentSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		if (!foundValidSubcontent) {
			showDialog(
					"Keine Daten für " + category + " für den Subcontent Selector " + subcontentSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		return scrapedData;
	}

	/**
	 * Diese Methode wird verwendet, um die Daten der Personen zu extrahieren und zu
	 * verarbeiten.
	 * 
	 * @param website       Die Webseite, von der die Daten extrahiert werden
	 *                      sollen.
	 * @param category      Die Kategorie des Suchworts (Personen).
	 * @param elementClass  Die Klasse der Elemente, die die Daten enthalten.
	 * @param tag           Der Tag für die jeweiligen Suchwörter
	 * @param nameSelector  Der Selektor für die Namen der Personen.
	 * @param groupSelector Der Selektor für die Gruppen der Personen.
	 * @param emailSelector Der Selektor für die E-Mail-Adressen der Personen.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processPersonData(Document website, String category, String elementClass, String tag,
			String nameSelector, String groupSelector, String emailSelector) {
		Elements websiteElements = website.getElementsByClass(elementClass);
		Elements tagElements = null;
		Element nameElement = null;
		Element groupElement = null;
		Element emailElement = null;
		List<String> scrapedData = new ArrayList<>();
		String email = null;
		boolean foundValidName = false;
		boolean foundValidGroup = false;
		boolean foundValidEmail = false;

		for (Element element : websiteElements) {
			if (tag == null || tag.isEmpty()) {
				showDialog("Kein Tag für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			tagElements = element.getElementsByTag(tag);

			if (tagElements == null || tagElements.isEmpty()) {
				showDialog(
						"Keine Daten für " + category + " für den Tag " + tag
								+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
						"Fehler", JOptionPane.ERROR_MESSAGE);

				return new ArrayList<>();
			}

			for (Element tagElement : tagElements) {
				if (nameSelector == null || nameSelector.isEmpty()) {
					showDialog("Kein Name Selector für " + category + " gefunden!", "Fehler",
							JOptionPane.ERROR_MESSAGE);

					return new ArrayList<>();
				}

				nameElement = tagElement.selectFirst(nameSelector);

				if (nameElement != null) {
					foundValidName = true;
				}

				if (groupSelector == null || groupSelector.isEmpty()) {
					showDialog("Kein Group Selector für " + category + " gefunden!", "Fehler",
							JOptionPane.ERROR_MESSAGE);

					return new ArrayList<>();
				}

				groupElement = tagElement.selectFirst(groupSelector);

				if (groupElement != null) {
					foundValidGroup = true;
				}

				if (emailSelector == null || emailSelector.isEmpty()) {
					showDialog("Kein Email Selector für " + category + " gefunden!", "Fehler",
							JOptionPane.ERROR_MESSAGE);

					return new ArrayList<>();
				}

				emailElement = tagElement.selectFirst(emailSelector);

				if (emailElement != null) {
					foundValidEmail = true;
				}

				if (nameElement == null || groupElement == null || emailElement == null) {
					continue;
				}

				if (nameElement != null) {
					scrapedData.add(nameElement.text().trim());
				}

				if (groupElement != null) {
					scrapedData.add(groupElement.ownText().trim());
				}

				if (emailElement != null) {
					email = emailElement.ownText().trim();

					if (email != null) {
						scrapedData.add(email.trim());
					} else {
						showDialog(
								"Keine Daten für " + category + " für den Email Selector " + emailSelector
										+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
								"Fehler", JOptionPane.ERROR_MESSAGE);

						return new ArrayList<>();
					}
				}
			}
		}

		if (!foundValidName) {
			showDialog(
					"Keine Daten für " + category + " für den Name Selector " + nameSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		if (!foundValidGroup) {
			showDialog(
					"Keine Daten für " + category + " für den Group Selector " + groupSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		if (!foundValidEmail) {
			showDialog(
					"Keine Daten für " + category + " für den Email Selector " + emailSelector
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		return scrapedData;
	}

	/**
	 * Diese Methode wird verwendet, um eine Dialogbox mit dem entsprechenden Titel,
	 * dem passenden Text und dem dazugehörigen Typ asynchron anzuzeigen, um den
	 * Thread nicht zu blockieren.
	 * 
	 * @param message     Der Text, der in der Dialogbox angezeigt werden soll.
	 * @param title       Der Titel der Dialogbox.
	 * @param messageType Der Typ der Dialogbox.
	 */
	private void showDialog(String message, String title, int messageType) {
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, message, title, messageType));
	}
}
