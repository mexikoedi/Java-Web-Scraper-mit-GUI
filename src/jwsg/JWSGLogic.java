package jwsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JWSGLogic {
	// Datenstrukturen
	private List<String> lastSelectedCategories = new ArrayList<>();
	private static final Map<String, List<String>> scrapedDataMap = new ConcurrentHashMap<>();

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

		categories.parallelStream().forEach(category -> {
			String url = JWSGScrapingConfig.getUrl(category);
			String elementClass = JWSGScrapingConfig.getElementClass(category);
			String container = JWSGScrapingConfig.getContainer(category);
			String id = JWSGScrapingConfig.getId(category);
			String tag = JWSGScrapingConfig.getTag(category);
			String linkSelector = JWSGScrapingConfig.getSelector(category);
			List<String> scrapedData = new ArrayList<>();
			Document website = null;

			try {
				if (url == null || url.isEmpty()) {
					showDialog("Keine URL für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

					return;
				}

				website = Jsoup.connect(url).get();
				scrapedData
						.addAll(processWebsiteData(website, category, elementClass, container, id, tag, linkSelector));
				scrapedDataMap.put(category, scrapedData);
			} catch (IOException e) {
				showDialog(
						"Die URL " + url
								+ " ist nicht verfügbar! Tippfehler? Ansonsten versuchen Sie es später erneut.",
						"Fehler", JOptionPane.ERROR_MESSAGE);

				return;
			}
		});
	}

	/**
	 * Diese Methode wird verwendet, um die Daten zu extrahieren und zu verarbeiten.
	 * Bei einem Fehler wird eine entsprechende Fehlermeldung angezeigt.
	 * 
	 * @param website      Die Webseite, von der die Daten extrahiert werden sollen.
	 * @param category     Die Kategorie, die das Suchwort repräsentiert.
	 * @param elementClass Die Klasse der Elemente, die die Daten enthalten.
	 * @param container    Der Container für die jeweiligen Suchwörter.
	 * @param id           Die ID für die jeweiligen Suchwörter.
	 * @param tag          Der Tag für die jeweiligen Suchwörter.
	 * @param linkSelector Der Selektor für spezifische Links innerhalb der
	 *                     Elemente.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processWebsiteData(Document website, String category, String elementClass, String container,
			String id, String tag, String linkSelector) {
		if (elementClass == null || elementClass.isEmpty()) {
			showDialog("Keine Element für " + category + " gefunden!", "Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		Elements websiteElements = website.getElementsByClass(elementClass);
		Elements containerElements = null;
		Elements tagElements = null;
		Element linkElement = null;
		List<String> scrapedData = new ArrayList<>();

		if (websiteElements == null || websiteElements.isEmpty()) {
			showDialog(
					"Keine Daten für " + category + " für das Element " + elementClass
							+ " gefunden! Website-Struktur aktualisiert oder Tippfehler?",
					"Fehler", JOptionPane.ERROR_MESSAGE);

			return new ArrayList<>();
		}

		boolean idMatched = false;

		for (Element element : websiteElements) {
			if (linkSelector == null) {
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
			} else {
				if (linkSelector == null || linkSelector.isEmpty()) {
					showDialog("Kein Link Selector für " + category + " gefunden!", "Fehler",
							JOptionPane.ERROR_MESSAGE);

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
		}

		if (!idMatched && linkSelector == null) {
			showDialog(
					"Keine Daten für " + category + " für die ID " + id
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
