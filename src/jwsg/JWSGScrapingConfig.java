package jwsg;

import java.util.HashMap;
import java.util.Map;

public class JWSGScrapingConfig {
	// Map für Suchwörter und zugehörige URLs
	private static final Map<String, String> keywordUrlMap = new HashMap<>();
	// Map für Suchwörter und verwendete Elementklassen
	private static final Map<String, String> keywordElementMap = new HashMap<>();
	// Map für Suchwörter und zugehörige Container
	private static final Map<String, String> keywordContainerMap = new HashMap<>();
	// Map für Suchwörter und zugehörige IDs
	private static final Map<String, String> keywordIdMap = new HashMap<>();
	// Map für Suchwörter und zugehörige Tags
	private static final Map<String, String> keywordTagMap = new HashMap<>();
	// Map für Suchwörter und Selektoren für spezifische Links
	private static final Map<String, String> keywordSelectorMap = new HashMap<>();
	static {
		// URLs für die vordefinierten Suchwörter
		keywordUrlMap.put("Studiengänge Bachelor", "https://www.h-ka.de/studieren/studienangebot/bachelor");
		keywordUrlMap.put("Studiengänge Master", "https://www.h-ka.de/studieren/studienangebot/master");
		keywordUrlMap.put("Terminübersicht - Wintersemester",
				"https://www.h-ka.de/die-hochschule-karlsruhe/aktuelles/termine/semestertermine");
		keywordUrlMap.put("Terminübersicht - Sommersemester",
				"https://www.h-ka.de/die-hochschule-karlsruhe/aktuelles/termine/semestertermine");
		// Verwendete Elementklassen für die jeweiligen Suchwörter
		keywordElementMap.put("Studiengänge Bachelor", "table__cell-title");
		keywordElementMap.put("Studiengänge Master", "table__cell-title");
		keywordElementMap.put("Terminübersicht - Wintersemester", "c-tabs__content");
		keywordElementMap.put("Terminübersicht - Sommersemester", "c-tabs__content");
		// Container für die jeweiligen Suchwörter
		keywordContainerMap.put("Terminübersicht - Wintersemester", "o-container o-container--spacing-y ");
		keywordContainerMap.put("Terminübersicht - Sommersemester", "o-container o-container--spacing-y ");
		// IDs für die jeweiligen Suchwörter
		keywordIdMap.put("Terminübersicht - Wintersemester", "c36230");
		keywordIdMap.put("Terminübersicht - Sommersemester", "c36419");
		// Tags für die jeweiligen Suchwörter
		keywordTagMap.put("Terminübersicht - Wintersemester", "p");
		keywordTagMap.put("Terminübersicht - Sommersemester", "p");
		// Selektoren für spezifische Links innerhalb der Elemente
		keywordSelectorMap.put("Studiengänge Bachelor", "a.c-in2studyfinder__link");
		keywordSelectorMap.put("Studiengänge Master", "a.c-in2studyfinder__link");
	}

	/**
	 * Diese Methode wird verwendet, um die URL für das angegebene Suchwort aus
	 * einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das die URL abgerufen werden soll.
	 * @return Die URL für das angegebene Suchwort.
	 */
	public static String getUrl(String keyword) {
		return keywordUrlMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um die Elementklasse für das angegebene
	 * Suchwort aus einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das die Elementklasse abgerufen werden soll.
	 * @return Die Elementklasse für das angegebene Suchwort.
	 */
	public static String getElementClass(String keyword) {
		return keywordElementMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Container für das angegebene Suchwort
	 * aus einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Container abgerufen werden soll.
	 * @return Der Container für das angegebene Suchwort.
	 */
	public static String getContainer(String keyword) {
		return keywordContainerMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um die ID für das angegebene Suchwort aus einer
	 * Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das die ID abgerufen werden soll.
	 * @return Die ID für das angegebene Suchwort.
	 */
	public static String getId(String keyword) {
		return keywordIdMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Tag für das angegebene Suchwort aus
	 * einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Tag abgerufen werden soll.
	 * @return Der Tag für das angegebene Suchwort.
	 */
	public static String getTag(String keyword) {
		return keywordTagMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Selektor für spezifische Links für das
	 * angegebene Suchwort aus einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getSelector(String keyword) {
		return keywordSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und URLs zu
	 * erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und URLs.
	 */
	public static Map<String, String> getKeywordUrlMap() {
		return new HashMap<>(keywordUrlMap);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
	 * Elementklassen zu erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und Elementklassen.
	 */
	public static Map<String, String> getKeywordElementMap() {
		return new HashMap<>(keywordElementMap);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
	 * Containern zu erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und Containern.
	 */
	public static Map<String, String> getKeywordContainerMap() {
		return new HashMap<>(keywordContainerMap);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und IDs zu
	 * erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und IDs.
	 */
	public static Map<String, String> getKeywordIdMap() {
		return new HashMap<>(keywordIdMap);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und Tags zu
	 * erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und Tags.
	 */
	public static Map<String, String> getKeywordTagMap() {
		return new HashMap<>(keywordTagMap);
	}

	/**
	 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
	 * Selektoren für spezifische Links zu erhalten.
	 * 
	 * @return Die gesamte Map mit Suchwörtern und Selektoren für spezifische Links.
	 */
	public static Map<String, String> getKeywordSelectorMap() {
		return new HashMap<>(keywordSelectorMap);
	}
}
