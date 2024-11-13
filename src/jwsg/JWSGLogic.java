package jwsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;
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
			JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestens ein Suchwort aus!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);

			return false;
		}

		if (list.equals(lastSelectedCategories) && list.size() == 1) {
			JOptionPane.showMessageDialog(null, "Sie haben schon die Daten für dieses Suchwort!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);

			return false;
		} else if (list.equals(lastSelectedCategories) && list.size() > 1) {
			JOptionPane.showMessageDialog(null, "Sie haben schon die Daten für diese Suchwörter!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);

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
				website = Jsoup.connect(url).get();
				scrapedData.addAll(processWebsiteData(website, elementClass, container, id, tag, linkSelector));
				scrapedDataMap.put(category, scrapedData);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fehler",
						"URL: " + url + " nicht verfügbar! Versuchen Sie es später erneut.", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();

				return;
			}
		});
	}

	/**
	 * Diese Methode wird verwendet, um die Daten zu extrahieren und zu verarbeiten.
	 * 
	 * @param website      Die Webseite, von der die Daten extrahiert werden sollen.
	 * @param elementClass Die Klasse der Elemente, die die Daten enthalten.
	 * @param container    Der Container für die jeweiligen Suchwörter.
	 * @param id           Die ID für die jeweiligen Suchwörter.
	 * @param tag          Der Tag für die jeweiligen Suchwörter.
	 * @param linkSelector Der Selektor für spezifische Links innerhalb der
	 *                     Elemente.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processWebsiteData(Document website, String elementClass, String container, String id,
			String tag, String linkSelector) {
		Elements websiteElements = website.getElementsByClass(elementClass);
		Elements containerElements = null;
		Elements tagElements = null;
		Element linkElement = null;
		List<String> scrapedData = new ArrayList<>();

		if (websiteElements.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Keine Daten gefunden! Website-Strukur aktualisiert?", "Fehler",
					JOptionPane.ERROR_MESSAGE);

			return null;
		}

		for (Element element : websiteElements) {
			if (linkSelector == null) {
				containerElements = element.getElementsByClass(container);

				for (Element containerElement : containerElements) {
					if (containerElement.id().equals(id)) {
						tagElements = containerElement.getElementsByTag(tag);

						for (Element tagElement : tagElements) {
							scrapedData.add(tagElement.text().trim());
						}
					}
				}
			} else {
				linkElement = element.selectFirst(linkSelector);

				if (linkElement != null) {
					scrapedData.add(linkElement.ownText().trim());
				}
			}
		}

		return scrapedData;
	}
}
