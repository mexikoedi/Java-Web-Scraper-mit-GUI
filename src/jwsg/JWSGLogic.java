package jwsg;

// Importe
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JWSGLogic {
	// Datenstrukturen
	private static final Map<String, List<String>> scrapedDataMap = new ConcurrentHashMap<>();
	private static List<String> lastSelectedCategories = new ArrayList<>();
	private static final List<String> searchResults = new ArrayList<>();
	// Verarbeitungsdauer für das Scrapen
	private static long startTime = 0;
	private static long duration = 0;
	// Datenexport
	private static final String EXPORT_DIR = "jwsg_export";
	// Suche
	private static int currentResultIndex = -1;
	private static String lastSearchKeyword = null;
	// Cache
	private static final long cachedTime = 60 * 60 * 1000;
	private static final String CACHE_DIR = "jwsg_cache";

	/**
	 * Diese Methode wird verwendet, um die Daten zu erhalten, die von den
	 * ausgewählten Suchwörtern abhängen.
	 * 
	 * @return Die Map, die die Suchwörter und die zugehörigen Daten enthält.
	 */
	public static Map<String, List<String>> getScrapedDataMap() {
		return scrapedDataMap;
	}

	/**
	 * Diese Methode wird verwendet, um die Verarbeitungsdauer für das Scrapen zu
	 * erhalten.
	 * 
	 * @return Die Verarbeitungsdauer für das Scrapen.
	 */
	public static long getDuration() {
		return duration;
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
	public static void showDialog(String message, String title, int messageType) {
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, message, title, messageType));
	}

	/**
	 * Diese Methode wird verwendet, um die Suche nach einem bestimmten Suchbegriff
	 * in den gescrapten Daten zu starten.
	 * 
	 * @param keyword Der Suchbegriff, nach dem gesucht werden soll.
	 */
	public static void search(String keyword) {
		if (!keyword.equalsIgnoreCase(lastSearchKeyword)) {
			searchResults.clear();
			currentResultIndex = -1;

			for (List<String> details : scrapedDataMap.values()) {
				for (String detail : details) {
					if (detail.toLowerCase().contains(keyword.toLowerCase())) {
						searchResults.add(detail);
					}
				}
			}

			lastSearchKeyword = keyword;

			if (searchResults.isEmpty()) {
				showDialog("Keine Ergebnisse gefunden!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

				return;
			}
		}

		currentResultIndex++;

		if (currentResultIndex >= searchResults.size()) {
			showDialog("Alle Suchergebnisse wurden angezeigt!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
			currentResultIndex = 0;
		}

		JWSGLayout.highlightResult(keyword, currentResultIndex);
	}

	/**
	 * Diese Methode wird verwendet, um das nächste Suchergebnis anzuzeigen.
	 */
	public static void showNextResult() {
		if (searchResults.isEmpty()) {
			showDialog("Keine Ergebnisse gefunden!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		currentResultIndex++;

		if (currentResultIndex >= searchResults.size()) {
			showDialog("Alle Suchergebnisse wurden angezeigt!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
			currentResultIndex = 0;
		}

		String result = searchResults.get(currentResultIndex);
		JWSGLayout.highlightResult(result, currentResultIndex);
	}

	/**
	 * Diese Methode wird verwendet, um die Daten in einer JSON-Datei alphabetisch
	 * sortiert zu speichern.
	 * 
	 * @param scrapedData Die Map, die die Suchwörter und die zugehörigen Daten
	 *                    enthält.
	 */
	public static void exportData(Map<String, List<String>> scrapedData) {
		File exportDir = new File(EXPORT_DIR);

		if (!exportDir.exists()) {
			exportDir.mkdir();
		}

		boolean cacheValid = false;

		for (String category : scrapedData.keySet()) {
			String cacheFileName = getCacheFileName(category);

			if (isCacheValid(cacheFileName, cachedTime)) {
				cacheValid = true;

				break;
			}
		}

		if (!cacheValid) {
			showDialog("Der Cache ist abgelaufen. Bitte scrapen Sie die Daten erneut, bevor Sie exportieren!",
					"Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		boolean exportSuccess = false;
		boolean exportFailed = false;
		boolean alreadyExported = false;

		for (String category : scrapedData.keySet()) {
			List<String> details = scrapedData.get(category);
			StringBuilder jsonBuilder = new StringBuilder();
			jsonBuilder.append("{\n");
			jsonBuilder.append("  \"").append(category).append("\": [\n");

			for (int i = 0; i < details.size(); i++) {
				jsonBuilder.append("    ").append("\"").append(details.get(i).replace("\"", "\\\"")).append("\"");

				if (i < details.size() - 1) {
					jsonBuilder.append(",");
				}

				jsonBuilder.append("\n");
			}

			jsonBuilder.append("  ]\n");
			jsonBuilder.append("}\n");
			String jsonData = jsonBuilder.toString();
			String exportFileName = EXPORT_DIR + "/" + category.replaceAll("[^a-zA-Z0-9]", "_") + ".json";
			File exportFile = new File(exportFileName);

			if (exportFile.exists()) {
				try {
					String existingData = new String(Files.readAllBytes(Paths.get(exportFileName)));

					if (jsonData.equals(existingData)) {
						alreadyExported = true;

						continue;
					}
				} catch (IOException e) {
					exportFailed = true;

					continue;
				}
			}

			try (FileWriter writer = new FileWriter(exportFileName)) {
				writer.write(jsonData);
				exportSuccess = true;
			} catch (IOException e) {
				exportFailed = true;
			}
		}

		if (exportSuccess) {
			showDialog("Daten erfolgreich exportiert!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
		}

		if (exportFailed) {
			showDialog("Beim Exportieren der Daten ist ein Fehler aufgetreten!", "Fehler", JOptionPane.ERROR_MESSAGE);
		}

		if (alreadyExported) {
			showDialog("Die Daten wurden bereits exportiert und haben sich nicht verändert!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
	public static boolean checkButtonPressed(List<String> list) {
		if (list.isEmpty()) {
			showDialog("Bitte wählen Sie mindestens ein Suchwort aus!", "Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		}

		boolean cacheValid = false;

		for (String category : list) {
			String cacheFileName = getCacheFileName(category);

			if (isCacheValid(cacheFileName, cachedTime)) {
				cacheValid = true;

				break;
			}
		}

		if (list.equals(lastSelectedCategories) && list.size() == 1 && cacheValid) {
			showDialog("Sie haben schon die Daten für dieses Suchwort und der Cache ist noch nicht abgelaufen!",
					"Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		} else if (list.equals(lastSelectedCategories) && list.size() > 1 && cacheValid) {
			showDialog("Sie haben schon die Daten für diese Suchwörter und der Cache ist noch nicht abgelaufen!",
					"Hinweis", JOptionPane.INFORMATION_MESSAGE);

			return false;
		}

		scrapData(list);
		lastSelectedCategories = new ArrayList<>(list);

		return true;
	}

	/**
	 * Diese Methode wird verwendet, um die Daten von den ausgewählten Suchwörtern
	 * mit ihren entsprechenden URLs parallel abzurufen und in einer Map zu
	 * speichern. Verzögerung für Anfragen, Cache, Fortschrittsbalken und
	 * SwingWorker werden verwendet.
	 *
	 * @param categories Die Liste der ausgewählten Suchwörter.
	 */
	private static void scrapData(List<String> categories) {
		/*
		 * SwingWorker ist eine abstrakte Klasse zur Verarbeitung langer Aufgaben im
		 * Hintergrund und Veröffentlichung der Ergebnisse in Swing. Sie ist generisch
		 * mit zwei Typen: Rückgabewert und Zwischenergebnisse
		 * (Fortschrittsaktualisierungen). SwingWorker implementiert doInBackground()
		 * für die Hintergrundaufgabe und done() für die Ergebnisveröffentlichung. Sie
		 * verwendet den Event Dispatch Thread (EDT) zur sicheren Aktualisierung der
		 * Swing-Komponenten.
		 */
		SwingWorker<Void, Integer> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() {
				// Startzeit für die Verarbeitungsdauer
				startTime = System.currentTimeMillis();
				// Setze die interaktiven Komponenten auf inaktiv
				SwingUtilities.invokeLater(() -> {
					JWSGLayout.setStatusInteractiveComponents(false);
				});
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
				AtomicInteger progress = new AtomicInteger(0);
				int totalCategories = categories.size();
				JWSGLayout.resetProgressBar();
				JWSGLayout.setProgressBarVisible(true);

				// Parallele Verarbeitung der ausgewählten Suchwörter mit ihren URLs (Streams)
				categories.parallelStream().forEach(category -> {
					String baseUrl = JWSGScrapingConfig.getUrl(category);
					String cacheFileName = getCacheFileName(category);
					boolean cached = isCacheValid(cacheFileName, cachedTime);

					/*
					 * Synchronieren der Verzögerung, um zu vermeiden, dass mehrere Threads
					 * gleichzeitig pausieren
					 */
					synchronized (requestCounter) {
						/*
						 * Pause, wenn der Anforderungszähler die Batchgröße erreicht und es nicht
						 * gecached ist
						 */
						if (requestCounter.incrementAndGet() % batchSize == 0 && !cached) {
							try {
								Thread.sleep(delayMillis);
							} catch (InterruptedException e) {
								// Wiederherstellen des unterbrochenen Status
								Thread.currentThread().interrupt();
							}
						}
					}

					try {
						if (cached) {
							scrapedDataMap.put(category, loadFromCache(cacheFileName));
						} else {
							String elementClass = JWSGScrapingConfig.getElementClass(category);
							String container = JWSGScrapingConfig.getContainer(category);
							String id = JWSGScrapingConfig.getId(category);
							String tag = JWSGScrapingConfig.getTag(category);
							String linkSelector = JWSGScrapingConfig.getSelector(category);
							String titleSelector = JWSGScrapingConfig.getBulletinBoardTitleSelector(category);
							String dateSelector = JWSGScrapingConfig.getBulletinBoardDateSelector(category);
							String contentSelector = JWSGScrapingConfig.getBulletinBoardContentSelector(category);
							String nameSelector = JWSGScrapingConfig.getPersonNameSelector(category);
							String groupSelector = JWSGScrapingConfig.getPersonGroupSelector(category);
							String emailSelector = JWSGScrapingConfig.getPersonEmailSelector(category);
							String programElementClass = JWSGScrapingConfig.getProgramElementClass();
							String dateElementClass = JWSGScrapingConfig.getDateElementClass();
							String personElementClass = JWSGScrapingConfig.getPersonElementClass();
							String bulletinBoardId = JWSGScrapingConfig.getBulletinBoardId();
							String personPaginationToken = JWSGScrapingConfig.getPersonPaginationToken();
							String personPaginationFormat = JWSGScrapingConfig.getPersonPaginationFormat();
							String url = null;
							Document website = null;
							Elements websiteElements = null;
							List<String> currentPageData = new ArrayList<>();
							List<String> previousPageData = new ArrayList<>();
							List<String> allScrapedData = new ArrayList<>();
							List<String> scrapedData = new ArrayList<>();

							if (elementClass != null && !elementClass.isEmpty()
									&& elementClass.equals(personElementClass)) {
								int currentPage = 1;

								while (true) {
									url = (currentPage == 1) ? baseUrl
											: baseUrl.replace(personPaginationToken,
													String.format(personPaginationFormat, currentPage));

									try {
										website = Jsoup.connect(url).get();
									} catch (IOException e) {
										showDialog("Die URL " + url
												+ " ist nicht verfügbar! Tippfehler? Versuchen Sie es später erneut!",
												"Fehler", JOptionPane.ERROR_MESSAGE);

										break;
									}

									websiteElements = website.getElementsByClass(elementClass);

									if (websiteElements == null || websiteElements.isEmpty()) {
										break;
									}

									currentPageData = processPersonData(website, category, elementClass, tag,
											nameSelector, groupSelector, emailSelector);

									if (currentPageData == null || currentPageData.isEmpty()
											|| currentPageData.equals(previousPageData)) {
										break;
									}

									allScrapedData.addAll(currentPageData);
									previousPageData = new ArrayList<>(currentPageData);
									currentPage++;
								}

								scrapedDataMap.put(category, allScrapedData);
								saveToCache(cacheFileName, allScrapedData);
							} else {
								website = Jsoup.connect(baseUrl).get();
								if (elementClass != null && !elementClass.isEmpty()
										&& elementClass.equals(programElementClass)) {
									scrapedData = processProgramData(website, category, elementClass, linkSelector);
								} else if (elementClass != null && !elementClass.isEmpty()
										&& elementClass.equals(dateElementClass)) {
									scrapedData = processDateData(website, category, elementClass, container, id, tag);
								} else if (id != null && !id.isEmpty() && id.equals(bulletinBoardId)) {
									scrapedData = processBulletinBoardData(website, category, id, titleSelector,
											dateSelector, contentSelector);
								} else {
									if (websiteElements == null || websiteElements.isEmpty()) {
										if (elementClass != null && !elementClass.isEmpty()) {
											showDialog("Keine Daten für " + category + " für das Element "
													+ elementClass
													+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
													"Fehler", JOptionPane.ERROR_MESSAGE);

											return;
										} else if (id != null && !id.isEmpty()) {
											showDialog("Keine Daten für " + category + " für die Id " + id
													+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
													"Fehler", JOptionPane.ERROR_MESSAGE);

											return;
										} else {
											showDialog("Keine Daten für " + category
													+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
													"Fehler", JOptionPane.ERROR_MESSAGE);

											return;
										}
									}
								}

								scrapedDataMap.put(category, scrapedData);
								saveToCache(cacheFileName, scrapedData);
							}
						}
					} catch (IOException e) {
						showDialog(
								"Fehler beim Abrufen der Daten für " + category
										+ "! Tippfehler? Versuchen Sie es später erneut!",
								"Fehler", JOptionPane.ERROR_MESSAGE);

						return;
					}

					// Fortschritt aktualisieren und in Prozent umwandeln
					int currentProgress = progress.incrementAndGet();
					int progressPercentage = (int) ((currentProgress / (double) totalCategories) * 100);
					// Fortschritt veröffentlichen und an GUI senden (SwingWorker)
					publish(progressPercentage);
				});

				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				// Aktualisiere den Fortschrittsbalken
				for (int progress : chunks) {
					JWSGLayout.updateProgressBar(progress);
				}
			}

			@Override
			protected void done() {
				// Aktualisiere die GUI mit den gescrapten Daten und setze die interaktiven
				// Komponenten auf aktiv
				SwingUtilities.invokeLater(() -> {
					JWSGLayout.initData(lastSelectedCategories, scrapedDataMap);
					JWSGLayout.setStatusInteractiveComponents(true);
				});

				// Endzeit für die Verarbeitungsdauer
				long endTime = System.currentTimeMillis();
				duration = endTime - startTime;
			}
		};

		worker.execute();
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
	private static List<String> processProgramData(Document website, String category, String elementClass,
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
	private static List<String> processDateData(Document website, String category, String elementClass,
			String container, String id, String tag) {
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
	 * @param website         Die Webseite, von der die Daten extrahiert werden
	 *                        sollen.
	 * @param category        Die Kategorie des Suchworts (Schwarze Bretter).
	 * @param id              Die Id welche die Elemente eines Schwarzen Bretts
	 *                        enthält.
	 * @param titleSelector   Der Selektor für die Titel der Schwarzen Bretter.
	 * @param dateSelector    Der Selektor für die Daten der Schwarzen Bretter.
	 * @param contentSelector Der Selektor für den Inhalt der Schwarzen Bretter.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private static List<String> processBulletinBoardData(Document website, String category, String id,
			String titleSelector, String dateSelector, String contentSelector) {
		Elements websiteElements = website.select(id);
		Element titleElement = null;
		Element dateElement = null;
		Elements contentElements = null;
		List<String> scrapedData = new ArrayList<>();
		boolean foundValidTitle = false;
		boolean foundValidDate = false;
		boolean foundValidContent = false;

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

			contentElements = element.select(contentSelector);

			if (contentElements != null) {
				foundValidContent = true;
			}

			if (titleElement == null || dateElement == null || contentElements == null) {
				continue;
			}

			if (titleElement != null) {
				scrapedData.add(titleElement.text().trim());
			}

			if (dateElement != null) {
				String date = dateElement.text().trim();

				if (date.endsWith(")")) {
					date = date.substring(0, date.length() - 1);
				}

				scrapedData.add(date);
			}

			if (contentElements != null) {
				for (Element contentElement : contentElements) {
					scrapedData.add(contentElement.text().trim());
				}
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
	private static List<String> processPersonData(Document website, String category, String elementClass, String tag,
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
	 * Diese Methode wird verwendet, um den Dateinamen für den Cache zu erstellen.
	 * 
	 * @param category Das Suchwort, für das der Cache erstellt werden soll.
	 * @return Der Dateiname für den Cache.
	 */
	private static String getCacheFileName(String category) {
		return CACHE_DIR + "/" + category.replaceAll("[^a-zA-Z0-9]", "_") + ".cache";
	}

	/**
	 * Diese Methode wird verwendet, um zu überprüfen, ob der Cache gültig ist.
	 * 
	 * @param cacheFileName       Der Dateiname des Caches.
	 * @param cacheDurationMillis Die Dauer des Caches in Millisekunden.
	 * @return true, wenn der Cache gültig ist, sonst false.
	 */
	private static boolean isCacheValid(String cacheFileName, long cacheDurationMillis) {
		File cacheFile = new File(cacheFileName);

		if (!cacheFile.exists()) {
			return false;
		}

		try {
			List<String> lines = Files.readAllLines(Paths.get(cacheFileName));
			long timestamp = Long.parseLong(lines.get(0));
			long currentTime = System.currentTimeMillis();

			return (currentTime - timestamp) < cacheDurationMillis;
		} catch (IOException | NumberFormatException e) {
			showDialog("Fehler beim Überprüfen des Caches für " + cacheFileName + "!", "Fehler",
					JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}

	/**
	 * Diese Methode wird verwendet, um die Daten in den Cache zu speichern.
	 * 
	 * @param cacheFileName Der Dateiname des Caches.
	 * @param data          Die Daten, die in den Cache gespeichert werden sollen.
	 */
	private static void saveToCache(String cacheFileName, List<String> data) {
		File cacheDir = new File(CACHE_DIR);

		if (!cacheDir.exists()) {
			cacheDir.mkdir();
		}

		try (FileWriter writer = new FileWriter(cacheFileName)) {
			writer.write(System.currentTimeMillis() + System.lineSeparator());

			for (String line : data) {
				writer.write(line + System.lineSeparator());
			}
		} catch (IOException e) {
			showDialog("Fehler beim Speichern der Daten in den Cache für " + cacheFileName + "!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Diese Methode wird verwendet, um die Daten aus dem Cache zu laden.
	 * 
	 * @param cacheFileName Der Dateiname des Caches.
	 * @return Die Daten, die aus dem Cache geladen wurden.
	 */
	private static List<String> loadFromCache(String cacheFileName) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(cacheFileName));
			lines.remove(0);

			return lines;
		} catch (IOException e) {
			showDialog("Fehler beim Laden der Daten aus dem Cache für " + cacheFileName + "!", "Fehler",
					JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}
	}
}