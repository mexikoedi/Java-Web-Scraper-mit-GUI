package jwsg;

// Importe
import java.util.HashMap;
import java.util.Map;

public class JWSGScrapingConfig {
	// Map für Suchwörter und zugehörige Typen
	private static final Map<String, String> keywordTypeMap = new HashMap<>();
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
	// Map für Schwarzes Brett und Selektoren für spezifische Links für Schwarze
	// Bretter
	private static final Map<String, String> bulletinBoardTitleSelectorMap = new HashMap<>();
	private static final Map<String, String> bulletinBoardDateSelectorMap = new HashMap<>();
	private static final Map<String, String> bulletinBoardContentSelectorMap = new HashMap<>();
	// Map für Suchwörter und Selektoren für spezifische Links für Personen
	private static final Map<String, String> personNameSelectorMap = new HashMap<>();
	private static final Map<String, String> personGroupSelectorMap = new HashMap<>();
	private static final Map<String, String> personEmailSelectorMap = new HashMap<>();
	// Elementklasse für Studiengänge
	private static final String programElementClass = "table__cell-title";
	// Elementklasse für Semestertermine
	private static final String dateElementClass = "c-tabs__content";
	// Elementklasse für Personen
	private static final String personElementClass = "c-users__table";
	// Id für Schwarze Bretter
	private static final String bulletinBoardId = "div#news div.ui-tabs-panels > div.ui-tabs-panel > div.news-content";
	// Typ für Schwarze Bretter
	private static final String bulletinBoardType = "Schwarzes Brett";
	// Typ für Personen
	private static final String personType = "Personen";
	// Pagination Token für Personen
	private static final String personPaginationToken = "?tx_users_pi1";
	// Pagination Format für Personen
	private static final String personPaginationFormat = "/page-%d?tx_users_pi1";
	static {
		// Typen für die vordefinierten Suchwörter
		keywordTypeMap.put("Studiengänge Bachelor", "Studiengänge");
		keywordTypeMap.put("Studiengänge Master", "Studiengänge");
		keywordTypeMap.put("Terminübersicht - Wintersemester", "Semestertermine");
		keywordTypeMap.put("Terminübersicht - Sommersemester", "Semestertermine");
		keywordTypeMap.put("Schwarzes Brett Informatik (Bachelor)", "Schwarzes Brett");
		keywordTypeMap.put("Schwarzes Brett Medieninformatik (Bachelor)", "Schwarzes Brett");
		keywordTypeMap.put("Schwarzes Brett Informatik (Master)", "Schwarzes Brett");
		// URLs für die vordefinierten Suchwörter
		keywordUrlMap.put("Studiengänge Bachelor", "https://www.h-ka.de/studieren/studienangebot/bachelor");
		keywordUrlMap.put("Studiengänge Master", "https://www.h-ka.de/studieren/studienangebot/master");
		keywordUrlMap.put("Terminübersicht - Wintersemester",
				"https://www.h-ka.de/die-hochschule-karlsruhe/aktuelles/termine/semestertermine");
		keywordUrlMap.put("Terminübersicht - Sommersemester",
				"https://www.h-ka.de/die-hochschule-karlsruhe/aktuelles/termine/semestertermine");
		keywordUrlMap.put("Schwarzes Brett Informatik (Bachelor)",
				"https://raumzeit.hka-iwi.de/bulletinboard/IWI/INFB");
		keywordUrlMap.put("Schwarzes Brett Medieninformatik (Bachelor)",
				"https://raumzeit.hka-iwi.de/bulletinboard/IWI/MINB");
		keywordUrlMap.put("Schwarzes Brett Informatik (Master)", "https://raumzeit.hka-iwi.de/bulletinboard/IWI/INFM");
		// Verwendete Elementklassen für die jeweiligen Suchwörter
		keywordElementMap.put("Studiengänge Bachelor", "table__cell-title");
		keywordElementMap.put("Studiengänge Master", "table__cell-title");
		keywordElementMap.put("Terminübersicht - Wintersemester", "c-tabs__content");
		keywordElementMap.put("Terminübersicht - Sommersemester", "c-tabs__content");
		// Container für die jeweiligen Suchwörter
		keywordContainerMap.put("Terminübersicht - Wintersemester", "o-container o-container--spacing-y ");
		keywordContainerMap.put("Terminübersicht - Sommersemester", "o-container o-container--spacing-y ");
		// IDs für die jeweiligen Suchwörter
		keywordIdMap.put("Schwarzes Brett Informatik (Bachelor)", "div#news div.ui-tabs-panels > div.ui-tabs-panel > div.news-content");
		keywordIdMap.put("Schwarzes Brett Medieninformatik (Bachelor)", "div#news div.ui-tabs-panels > div.ui-tabs-panel > div.news-content");
		keywordIdMap.put("Schwarzes Brett Informatik (Master)", "div#news div.ui-tabs-panels > div.ui-tabs-panel > div.news-content");
		keywordIdMap.put("Terminübersicht - Wintersemester", "c36230");
		keywordIdMap.put("Terminübersicht - Sommersemester", "c36419");
		// Tags für die jeweiligen Suchwörter
		keywordTagMap.put("Terminübersicht - Wintersemester", "p");
		keywordTagMap.put("Terminübersicht - Sommersemester", "p");
		keywordTagMap.put("Schwarzes Brett Informatik (Bachelor)", "div");
		keywordTagMap.put("Schwarzes Brett Medieninformatik (Bachelor)", "div");
		keywordTagMap.put("Schwarzes Brett Informatik (Master)", "div");
		// Selektoren für spezifische Links innerhalb der Elemente
		keywordSelectorMap.put("Studiengänge Bachelor", "a.c-in2studyfinder__link");
		keywordSelectorMap.put("Studiengänge Master", "a.c-in2studyfinder__link");
		// Selektoren für spezifische Links innerhalb der Elemente für Schwarzes Brett
		bulletinBoardTitleSelectorMap.put("Schwarzes Brett Informatik (Bachelor)", "div.ui-card-title div:first-child");
		bulletinBoardDateSelectorMap.put("Schwarzes Brett Informatik (Bachelor)", "div.ui-card-title span");
		bulletinBoardContentSelectorMap.put("Schwarzes Brett Informatik (Bachelor)", "div.ui-card-content");
		bulletinBoardTitleSelectorMap.put("Schwarzes Brett Medieninformatik (Bachelor)",
				"div.ui-card-title div:first-child");
		bulletinBoardDateSelectorMap.put("Schwarzes Brett Medieninformatik (Bachelor)", "div.ui-card-title span");
		bulletinBoardContentSelectorMap.put("Schwarzes Brett Medieninformatik (Bachelor)", "div.ui-card-content");
		bulletinBoardTitleSelectorMap.put("Schwarzes Brett Informatik (Master)", "div.ui-card-title div:first-child");
		bulletinBoardDateSelectorMap.put("Schwarzes Brett Informatik (Master)", "div.ui-card-title span");
		bulletinBoardContentSelectorMap.put("Schwarzes Brett Informatik (Master)", "div.ui-card-content");
		// Alle Personen
		Map<String, String> personGroup = new HashMap<>();
		personGroup.put("Ansprechperson für Antidiskriminierung", "25");
		personGroup.put("Ansprechperson sexuelle Belästigung", "34");
		personGroup.put("Arbeitsicherheit", "98");
		personGroup.put("Beauftrage*r für Studierende mit Behinderung", "29");
		personGroup.put("Beschaffungsmanagement", "89");
		personGroup.put("Bibliothek", "71");
		personGroup.put("BW Institut für nachhaltige Mobilität", "66");
		personGroup.put("Center of Applied Research", "72");
		personGroup.put("Center of Competence", "73");
		personGroup.put("Controlling", "99");
		personGroup.put("Datenschutzbeauftragte*r", "23");
		personGroup.put("Dezernat Entwicklung, Bau, Infrastruktur", "83");
		personGroup.put("Dezernat für Akademische Angelegenheiten", "79");
		personGroup.put("Fakultät für Architektur und Bauwesen", "35");
		personGroup.put("Fakultät für Elektro- und Informationstechnik", "36");
		personGroup.put("Fakultät für Informatik und Wirtschaftsinformatik", "38");
		personGroup.put("Fakultät für Informationsmanagement und Medien", "37");
		personGroup.put("Fakultät für Maschinenbau und Mechatronik", "39");
		personGroup.put("Fakultät für Wirtschaftswissenschaften", "40");
		personGroup.put("Finanzen", "90");
		personGroup.put("Geschäftsstelle der Studienkommission für Hochschuldidaktik", "108");
		personGroup.put("Gleichstellung - Centrum für Chancengleichheit", "26");
		personGroup.put("Hochschulverwaltung", "153");
		personGroup.put("Infrastruktur - Gebäudemanagement", "86");
		personGroup.put("Institute of Materials and Processes", "56");
		personGroup.put("Institut für Angewandte Forschung", "114");
		personGroup.put("Institut für Digitale Materialforschung", "48");
		personGroup.put("Institut für Energieeffiziente Mobilität", "49");
		personGroup.put("Institut für Fremdsprachen", "62");
		personGroup.put("Institut für Kälte-, Klima- und Umwelttechnik", "50");
		personGroup.put("Institut für Lernen und Innovation in Netzwerken", "51");
		personGroup.put("Institut für Robotik und Autonome Systeme", "189");
		personGroup.put("Institut für Sensor- und Informationssysteme", "52");
		personGroup.put("Institut für Thermofluiddynamik", "155");
		personGroup.put("Institut für Ubiquitäre Mobilitätssysteme", "53");
		personGroup.put("Institut für Verkehr und Infrastruktur", "54");
		personGroup.put("Institut für Wissenschaftliche Weiterbildung", "64");
		personGroup.put("Institut Intelligent Systems Research Group", "55");
		personGroup.put("International Office", "91");
		personGroup.put("Koordinierungsstelle für die Praktischen Studiensemester", "109");
		personGroup.put("Personal", "92");
		personGroup.put("Personalrat", "21");
		personGroup.put("Presse und Kommunikation", "93");
		personGroup.put("Projektmanagement-Office", "193");
		personGroup.put("Qualitätsmanagement", "100");
		personGroup.put("Rechenzentrum", "75");
		personGroup.put("Referat für Technik- und Wissenschaftsethik", "110");
		personGroup.put("Referenten, Hochschulgremien", "104");
		personGroup.put("Rektorat", "13");
		personGroup.put("Schwerbehindertenvertretung", "28");
		personGroup.put("Senatsbeauftragte*r für ausländische Studierende", "31");
		personGroup.put("Senatsbeauftragte*r für Didaktik", "33");
		personGroup.put("Struktur und Organisation", "102");
		personGroup.put("Studierendenbüro", "81");
		personGroup.put("Zentrale Studienberatung", "80");
		personGroup.put("Zentrum für Lehrinnovation", "82");
		personGroup.put("Öffentliche Baustoffprüfstelle", "74");

		// Dynamisches Erstellen der URL- und Selektor-Zuordnungen
		for (Map.Entry<String, String> entry : personGroup.entrySet()) {
			String personKey = "Personen: " + entry.getKey();
			String facilityValue = entry.getValue();
			// URL
			String baseUrl = "https://www.h-ka.de/die-hochschule-karlsruhe/organisation-personen/personen-a-z?tx_users_pi1%5Bfilter%5D%5Bfacility%5D="
					+ facilityValue
					+ "&tx_users_pi1%5Bfilter%5D%5Bfunction%5D=&tx_users_pi1%5Bfilter%5D%5Bsearchterm%5D=&tx_users_pi1%5Bfilter%5D%5Babc%5D=#c18976";
			keywordUrlMap.put(personKey, baseUrl);
			// Typ für Personen
			keywordTypeMap.put(personKey, "Personen");
			// Verwendete Elementklasse für Personen
			keywordElementMap.put(personKey, "c-users__table");
			// Tag für Personen
			keywordTagMap.put(personKey, "tr");
			// Selektoren für spezifische Links innerhalb der Elemente für Personen
			personNameSelectorMap.put(personKey, "td.table__cell-title span.person__user-academic-title");
			personGroupSelectorMap.put(personKey, "td.table__cell-properties div.o-row__col-full");
			personEmailSelectorMap.put(personKey, "td.table__cell-properties a");
		}
	}

	/**
	 * Diese Methode wird verwendet, um den Typ für das angegebene Suchwort aus
	 * einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Typ abgerufen werden soll.
	 * @return Der Typ für das angegebene Suchwort.
	 */
	public static String getType(String keyword) {
		return keywordTypeMap.get(keyword);
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
	 * Diese Methode wird verwendet, um den Schwarzes Brett Titel Selektor für
	 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
	 *
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getBulletinBoardTitleSelector(String keyword) {
		return bulletinBoardTitleSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Schwarzes Brett Datum Selektor für
	 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
	 *
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getBulletinBoardDateSelector(String keyword) {
		return bulletinBoardDateSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Schwarzes Brett Inhalt Selektor für
	 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
	 *
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getBulletinBoardContentSelector(String keyword) {
		return bulletinBoardContentSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Personennamen Selektor für spezifische
	 * Links für das angegebene Suchwort aus einer Map abzurufen.
	 *
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getPersonNameSelector(String keyword) {
		return personNameSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Personengruppen Selektor für spezifische
	 * Links für das angegebene Suchwort aus einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getPersonGroupSelector(String keyword) {
		return personGroupSelectorMap.get(keyword);
	}

	/**
	 * Diese Methode wird verwendet, um den Personenemail Selektor für spezifische
	 * Links für das angegebene Suchwort aus einer Map abzurufen.
	 * 
	 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
	 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
	 */
	public static String getPersonEmailSelector(String keyword) {
		return personEmailSelectorMap.get(keyword);
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
	 * Diese Methode wird verwendet, um die Elementklasse für Studiengänge zu
	 * erhalten.
	 * 
	 * @return Die Elementklasse für Studiengänge.
	 */
	public static String getProgramElementClass() {
		return programElementClass;
	}

	/**
	 * Diese Methode wird verwendet, um die Elementklasse für Semestertermine zu
	 * erhalten.
	 * 
	 * @return Die Elementklasse für Semestertermine.
	 */
	public static String getDateElementClass() {
		return dateElementClass;
	}

	/**
	 * Diese Methode wird verwendet, um die Elementklasse für Personen zu erhalten.
	 * 
	 * @return Die Elementklasse für Personen.
	 */
	public static String getPersonElementClass() {
		return personElementClass;
	}

	/**
	 * Diese Methode wird verwendet, um die Id für Schwarze Bretter zu erhalten.
	 * 
	 * @return Die Id für Schwarze Bretter.
	 */
	public static String getBulletinBoardId() {
		return bulletinBoardId;
	}

	/**
	 * Diese Methode wird verwendet, um den Typ für Schwarze Bretter zu erhalten.
	 * 
	 * @return Der Typ für Schwarze Bretter.
	 */
	public static String getBulletinBoardType() {
		return bulletinBoardType;
	}

	/**
	 * Diese Methode wird verwendet, um den Typ für Personen zu erhalten.
	 * 
	 * @return Der Typ für Personen.
	 */
	public static String getPersonType() {
		return personType;
	}

	/**
	 * Diese Methode wird verwendet, um den Pagination Token für Personen zu
	 * erhalten.
	 * 
	 * @return Der Pagination Token für Personen.
	 */
	public static String getPersonPaginationToken() {
		return personPaginationToken;
	}

	/**
	 * Diese Methode wird verwendet, um das Format für die Pagination für Personen
	 * zu erhalten.
	 * 
	 * @return Das Format für die Pagination für Personen.
	 */
	public static String getPersonPaginationFormat() {
		return personPaginationFormat;
	}
}