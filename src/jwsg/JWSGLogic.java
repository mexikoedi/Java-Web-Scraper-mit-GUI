package jwsg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
	private static final Map<String, String> keywordUrlMap = new HashMap<>();
	static {
		keywordUrlMap.put("Studiengänge Bachelor", "https://www.h-ka.de/studieren/studienangebot/bachelor");
		keywordUrlMap.put("Studiengänge Master", "https://www.h-ka.de/studieren/studienangebot/master");
	}

	/**
	 * Diese Methode gibt eine Map zurück, die die Suchwörter und die zugehörigen
	 * Daten enthält.
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
	 */
	public void checkButtonPressed(List<String> list) {
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Bitte wählen Sie mindestens ein Suchwort aus!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		if (list.equals(lastSelectedCategories)) {
			JOptionPane.showMessageDialog(null, "Sie haben schon die Daten für diese Suchwörter!", "Hinweis",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		scrapData(list);
		lastSelectedCategories = new ArrayList<>(list);
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
			String url = keywordUrlMap.get(category);
			List<String> studyPrograms = new ArrayList<>();
			Document website = null;

			try {
				website = Jsoup.connect(url).get();
				studyPrograms.addAll(processWebsiteData(website));
				scrapedDataMap.put(category, studyPrograms);
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
	 * @param website Die Webseite, von der die Daten extrahiert werden sollen.
	 * @return Die Liste, die die extrahierten Daten enthält.
	 */
	private List<String> processWebsiteData(Document website) {
		Elements websiteElements = website.getElementsByClass("table__cell-title");
		Element linkElement = null;
		String scrapedName = null;
		List<String> scrapedData = new ArrayList<>();

		if (websiteElements.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Keine Daten gefunden! Website-Strukur aktualisiert?", "Fehler",
					JOptionPane.ERROR_MESSAGE);

			return null;
		}

		for (Element element : websiteElements) {
			linkElement = element.selectFirst("a.c-in2studyfinder__link");

			if (linkElement != null) {
				scrapedName = linkElement.ownText().trim();
				scrapedData.add(scrapedName);
			}
		}

		return scrapedData;
	}
}
