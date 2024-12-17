# Java Web Scraper mit GUI

## Allgemeine Informationen
Dieses Java-Programm ermöglicht das unkomplizierte Abrufen von diversen [HKA (Hochschule Karlsruhe – University of Applied Sciences)](https://www.h-ka.de/) Informationen über eine grafische Benutzeroberfläche.

## Anwenderdokumentation
### Voraussetzungen
Es muss vor der Benutzung dieses Programms [Java JDK 22](https://www.oracle.com/de/java/technologies/downloads/) oder höher auf dem Computer installiert werden.

### Installation des Programms
Sie können das Programm herunterladen, indem Sie den grünen Knopf „Code“ und dann „ZIP herunterladen“ auswählen. <br> 
Alternativ können Sie die ZIP-Datei über die Registerkarte „Releases“ herunterladen. <br>
Anschließend entpacken/extrahieren Sie die Zip-Datei. <br>
Nun können Sie das Programm über die Datei namens „Java Web Scraper mit GUI.jar“ ausführen und benutzen.

### Verwendung des Programms
Im Programm können Sie aus einem Dropdown ein oder mehrere Suchwörter, zu welchen Sie Informationen benötigen, auswählen. <br>
Mehrere Suchwörter können durch die Nutzung der Steuerungs- oder Umschalttaste ausgewählt werden. <br>
Durch den Knopf „Abrufen“ werden die Informationen zu den davor ausgewählten Suchwörtern abgerufen, formatiert und entsprechend im Bereich „Daten“ angezeigt. <br>
Es gibt zudem ein Textfeld, in welchem Sie Schlagwörter eingeben können, um so schnell, durch das Betätigen des „Suchen“-Knopfes, zu den entsprechenden Informationen zu gelangen. <br>
Zudem wird im Programm nach dem Abrufen der Daten angezeigt, wie lange dies gedauert hat. <br>
Außerdem wird der Datenabruf durch eine Fortschrittsanzeige begleitet <br>
Die abgerufenen Daten können anschließend zu JSON-Dateien exportiert und im „jwsg_export“-Ordner betrachtet werden. <br>
Um die Leistung des Programms zu verbessern, wird Caching benutzt, um Daten, welche zuvor abgerufen wurden, innerhalb einer Stunde schnell wiederzuerhalten. <br>
Die Cache-Dateien befinden sich im „jwsg_cache“-Ordner.

### Leistung
Das Programm benötigt etwa 30 Sekunden, um über 100 Anfragen zu verarbeiten. <br>
Wenn alle Daten im Cache gespeichert sind, dauert das Laden der Daten ungefähr 50 Millisekunden. <br>
Folgende Faktoren beeinflussen die Leistung:
- Das eigene Netzwerk
- Die Auslastung des Servers
- Die Hardware des eigenen Endgeräts (vernachlässigbar)

Das Programm arbeitet effizient und erfordert daher keine leistungsstarke Hardware, wobei eine gute Netzanbindung von Vorteil ist. <br>
Beim Abrufen aller Daten wurden lediglich 300 MB RAM verwendet, während die Auslastung anderer Hardwarekomponenten (CPU/GPU/Speicher/ …) nicht messbar war.

### Beispiele
Die grafische Benutzeroberfläche:
![GUI](https://github.com/user-attachments/assets/bf4afb65-724c-49f0-b71f-0d068e18cc58 "GUI") <br>
Auswählen von Suchwörtern:
![Suchwortauswahl](https://github.com/user-attachments/assets/5ce48d6b-04b3-4487-a0c5-9dceb9c5848b "Suchwortauswahl") <br>
Die abgerufenen Daten:
![Daten](https://github.com/user-attachments/assets/b9a46843-80f3-42ad-9f25-62b69be827bc "Daten")

## Entwicklerdokumentation
### Informationen zum Java-Projekt
Dieses Java-Projekt wurde mithilfe der [Eclipse IDE](https://eclipseide.org/) mit der Version „2024-09“ und im späteren Verlauf auch mit der Version „2024-12“ erstellt. <br>
Zudem war die [Java JDK](https://www.oracle.com/java/technologies/downloads/) mit der Version „22.0.2“ im Einsatz. <br>
Dazu wurde eine Drittanbieterbibliothek namens [jsoup](https://jsoup.org/download) mit der Version „1.18.1“ verwendet, um verschiedene HTML-Operationen durchzuführen. <br>
Es wurde [Eclipse WindowBuilder](https://projects.eclipse.org/projects/tools.windowbuilder) genutzt, um die grafische Oberfläche zu realisieren. <br>
Aufgrund von Geschwindigkeitsverbesserungen wird das Abrufen von Informationen mithilfe von „Java Parallel Streams“ umgesetzt und für die weitere Reaktionsfähigkeit der grafischen Oberfläche wurde „SwingWorker“ genutzt. <br>
Das Java-Projekt wurde mit der Compiler-Konformitätsstufe „22“ kompiliert.

### Struktur des Projekts
Der Ordner namens „rsc“ beinhaltet das Icon des Programms, welches „Icon.png“ bezeichnet wird. <br>
Wobei der Ordner mit der Bezeichnung „src“ die jsoup-Bibliothek mit der Nennung „jsoup-1.18.1.jar“ und das Paket namens „jwsg“ beinhaltet. <br>
Im Paket „jwsg“ befindet sich die Datei mit dem Namen „JWSGLayout.java“, welche die grafische Oberfläche beherbergt. <br>
Die Datei namens „JWSGLogic.java“ ist für die Logik zuständig. <br>
Zudem existiert noch die Datei namens „JWSGScrapingConfig.java“, welche die für das Scrapen benötigten URLs, Suchwörter und Webseitenelemente enthält. (Diese können bei Bedarf angepasst werden.)

### Einrichtung des Projekts
Sie können das Projekt durch die Befolgung der [Installationsanleitung](#Installation-des-Programms) herunterladen oder durch das [Klonen](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) des Projekts in Eclipse einbinden. <br>
Beachten Sie zuvor die [Informationen zum Java-Projekt](#Installation-des-Programms). <br>
Anschließend können Sie in Eclipse das Projekt importieren beziehungsweise öffnen. <br>
Es werden zuerst Fehler angezeigt, da Eclipse zusätzliche Projektinformationen benötigt. <br>
Diese können durch das Ergänzen der folgenden zwei Dateien behoben werden: <br>
- „.project“-Datei mit folgendem Inhalt im Hauptverzeichnis des Projekts anlegen: <br>
```
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
	<name>Java Web Scraper mit GUI</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.jdt.core.javanature</nature>
	</natures>
</projectDescription>
```
- „.classpath“-Datei mit folgendem Inhalt im Hauptverzeichnis des Projekts anlegen: <br>
```
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER">
		<attributes>
			<attribute name="module" value="true"/>
		</attributes>
	</classpathentry>
	<classpathentry excluding="module-info.java" kind="src" path="src"/>
	<classpathentry kind="src" path="rsc"/>
	<classpathentry kind="lib" path="src/jsoup-1.18.1.jar"/>
	<classpathentry kind="output" path="bin"/>
</classpath>
```
Daraufhin sollte das Java-Projekt nun ordnungsgemäß eingerichtet sein. <br>
Falls Sie eine andere IDE wie zum Beispiel die [IntelliJ IDEA](https://www.jetbrains.com/de-de/idea/) verwenden sollten, könnte die Einrichtung leicht variieren.

### Codedokumentation
<details>
<summary>public Methoden</summary>

Paket: jwsg <br>
Datei: JWSGLayout.java

```java
/**
 * Start der Anwendung. Erzeugt das Fenster und ansonsten erscheint ein Fehler.
 */
public static void main(String[] args)

/**
 * Standardkonstruktur. Inititialisierung der Anwendung, um die GUI zu
 * erstellen.
 */
public JWSGLayout()

/**
 * Gescrapte Daten werden formatiert und in einer JTextArea angezeigt.
 * 
 * @param selectedCategories Die Liste der ausgewählten Kategorien.
 * @param scrapedData        Die gescrapten Daten.
 */
public void initData(List<String> selectedCategories, Map<String, List<String>> scrapedData)

/**
 * Hervorheben des spezifischen Ergebnisses im Text.
 * 
 * @param result      Das spezifische Ergebnis im Text.
 * @param resultIndex Der Index des spezifischen Ergebnisses im Text.
 */
public static void highlightResult(String result, int resultIndex)

/**
 * Diese Methode setzt den Fortschrittsbalken zurück.
 */
public static void resetProgressBar()

/**
 * Diese Methode setzt den Fortschrittsbalken auf sichtbar oder unsichtbar.
 * 
 * @param visible Der Wert, ob der Fortschrittsbalken sichtbar oder unsichtbar
 *                ist.
 */
public static void setProgressBarVisible(boolean visible)

/**
 * Diese Methode aktualisiert den Fortschrittsbalken.
 * 
 * @param progress Der Wert, um den der Fortschrittsbalken aktualisiert wird.
 */
public static void updateProgressBar(int progress)

/**
 * Diese Methode setzt die Status der interaktiven Komponenten.
 * 
 * @param status Der Wert, ob die interaktiven Komponenten aktiviert oder
 *               deaktiviert sind.
 */
public static void setStatusInteractiveComponents(boolean status)
```

Paket: jwsg <br>
Datei: JWSGLogic.java

```java
/**
 * Diese Methode wird verwendet, um die Daten zu erhalten, die von den
 * ausgewählten Suchwörtern abhängen.
 * 
 * @return Die Map, die die Suchwörter und die zugehörigen Daten enthält.
 */
public static Map<String, List<String>> getScrapedDataMap()

/**
 * Diese Methode wird verwendet, um die Verarbeitungsdauer für das Scrapen zu
 * erhalten.
 * 
 * @return Die Verarbeitungsdauer für das Scrapen.
 */
public static long getDuration()

/**
 * Diese Methode wird verwendet, um eine Dialogbox mit dem entsprechenden Titel,
 * dem passenden Text und dem dazugehörigen Typ asynchron anzuzeigen, um den
 * Thread nicht zu blockieren.
 * 
 * @param message     Der Text, der in der Dialogbox angezeigt werden soll.
 * @param title       Der Titel der Dialogbox.
 * @param messageType Der Typ der Dialogbox.
 */
public static void showDialog(String message, String title, int messageType)

/**
 * Diese Methode wird verwendet, um die Suche nach einem bestimmten Suchbegriff
 * in den gescrapten Daten zu starten.
 * 
 * @param keyword Der Suchbegriff, nach dem gesucht werden soll.
 */
public static void search(String keyword)

/**
 * Diese Methode wird verwendet, um das nächste Suchergebnis anzuzeigen.
 */
public static void showNextResult()

/**
 * Diese Methode wird verwendet, um die Daten in einer JSON-Datei alphabetisch
 * sortiert zu speichern.
 * 
 * @param scrapedData Die Map, die die Suchwörter und die zugehörigen Daten
 *                    enthält.
 */
public static void exportData(Map<String, List<String>> scrapedData)

/**
 * Diese Methode wird verwendet, um zu überprüfen, ob die Schaltfläche "Abrufen"
 * gedrückt wurde. Zudem werden die letzten ausgewählten Suchwörter gespeichert.
 * Enthält verschiedene Überprüfungen, um Sonderfälle abzudecken.
 * 
 * @param list Die Liste der ausgewählten Suchwörter.
 * @return true, wenn die Schaltfläche gedrückt wurde und keiner der Sonderfälle
 *         aufgetreten ist, sonst false.
 */
public static boolean checkButtonPressed(List<String> list)
```

Paket: jwsg <br>
Datei: JWSGScrapingConfig.java

```java
/**
 * Diese Methode wird verwendet, um den Typ für das angegebene Suchwort aus
 * einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Typ abgerufen werden soll.
 * @return Der Typ für das angegebene Suchwort.
 */
public static String getType(String keyword)

/**
 * Diese Methode wird verwendet, um die URL für das angegebene Suchwort aus
 * einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das die URL abgerufen werden soll.
 * @return Die URL für das angegebene Suchwort.
 */
public static String getUrl(String keyword)

/**
 * Diese Methode wird verwendet, um die Elementklasse für das angegebene
 * Suchwort aus einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das die Elementklasse abgerufen werden soll.
 * @return Die Elementklasse für das angegebene Suchwort.
 */
public static String getElementClass(String keyword)

/**
 * Diese Methode wird verwendet, um den Container für das angegebene Suchwort
 * aus einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Container abgerufen werden soll.
 * @return Der Container für das angegebene Suchwort.
 */
public static String getContainer(String keyword)

/**
 * Diese Methode wird verwendet, um die ID für das angegebene Suchwort aus einer
 * Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das die ID abgerufen werden soll.
 * @return Die ID für das angegebene Suchwort.
 */
public static String getId(String keyword)

/**
 * Diese Methode wird verwendet, um den Tag für das angegebene Suchwort aus
 * einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Tag abgerufen werden soll.
 * @return Der Tag für das angegebene Suchwort.
 */
public static String getTag(String keyword)

/**
 * Diese Methode wird verwendet, um den Selektor für spezifische Links für das
 * angegebene Suchwort aus einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Schwarzes Brett Titel Selektor für
 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
 *
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getBulletinBoardTitleSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Schwarzes Brett Datum Selektor für
 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
 *
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getBulletinBoardDateSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Schwarzes Brett Inhalt Selektor für
 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
 *
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getBulletinBoardContentSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Schwarzes Brett Unterinhalt Selektor für
 * spezifische Links für das angegebene Suchwort aus einer Map abzurufen.
 *
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getBulletinBoardSubcontentSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Personennamen Selektor für spezifische
 * Links für das angegebene Suchwort aus einer Map abzurufen.
 *
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getPersonNameSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Personengruppen Selektor für spezifische
 * Links für das angegebene Suchwort aus einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getPersonGroupSelector(String keyword)

/**
 * Diese Methode wird verwendet, um den Personenemail Selektor für spezifische
 * Links für das angegebene Suchwort aus einer Map abzurufen.
 * 
 * @param keyword Das Suchwort, für das der Selektor abgerufen werden soll.
 * @return Der Selektor für spezifische Links für das angegebene Suchwort.
 */
public static String getPersonEmailSelector(String keyword)

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und URLs zu
 * erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und URLs.
 */
public static Map<String, String> getKeywordUrlMap()

/**
 * Diese Methode wird verwendet, um die Elementklasse für Studiengänge zu
 * erhalten.
 * 
 * @return Die Elementklasse für Studiengänge.
 */
public static String getProgramElementClass()

/**
 * Diese Methode wird verwendet, um die Elementklasse für Semestertermine zu erhalten.
 * 
 * @return Die Elementklasse für Semestertermine.
 */
public static String getDateElementClass()

/**
 * Diese Methode wird verwendet, um die Elementklasse für Schwarze Bretter zu
 * erhalten.
 * 
 * @return Die Elementklasse für Schwarze Bretter.
 */
public static String getBulletinBoardElementClass()

/**
 * Diese Methode wird verwendet, um die Elementklasse für Personen zu erhalten.
 * 
 * @return Die Elementklasse für Personen.
 */
public static String getPersonElementClass()

/**
 * Diese Methode wird verwendet, um den Typ für Schwarze Bretter zu erhalten.
 * 
 * @return Der Typ für Schwarze Bretter.
 */
public static String getBulletinBoardType()

/**
 * Diese Methode wird verwendet, um den Typ für Personen zu erhalten.
 * 
 * @return Der Typ für Personen.
 */
public static String getPersonType()

/**
 * Diese Methode wird verwendet, um den Pagination Token für Personen zu
 * erhalten.
 * 
 * @return Der Pagination Token für Personen.
 */
public static String getPersonPaginationToken()

/**
 * Diese Methode wird verwendet, um das Format für die Pagination für Personen
 * zu erhalten.
 * 
 * @return Das Format für die Pagination für Personen.
 */
public static String getPersonPaginationFormat()
```
</details>

<details>
<summary>private Methoden</summary>

Paket: jwsg <br>
Datei: JWSGLayout.java

```java
/**
 * GUI-Inhalte vom Frame werden initialisiert und das Fenster wird nach dem OS
 * angepasst, ansonsten wird ein Fehler ausgegeben.
 */
private void initialize()

/**
 * JList wird mit den vordefinierten Suchwörtern alphabetisch sortiert
 * initialisiert.
 * 
 * @return DefaultListModel<String> welche die Liste mit den initialen
 *         alphabetisch sortierten Inhalten enthält.
 */
private DefaultListModel<String> initList()

/**
 * Durchführung der Suche nach einem Suchbegriff.
 */
private void performSearch()
```

Paket: jwsg <br>
Datei: JWSGLogic.java

```java	
/**
 * Diese Methode wird verwendet, um die Daten von den ausgewählten Suchwörtern
 * mit ihren entsprechenden URLs parallel abzurufen und in einer Map zu
 * speichern. Verzögerung für Anfragen, Cache, Fortschrittsbalken und
 * SwingWorker werden verwendet.
 *
 * @param categories Die Liste der ausgewählten Suchwörter.
 */
private static void scrapData(List<String> categories)

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
private static List<String> processProgramData(Document website, String category, String elementClass, String linkSelector)

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
private static List<String> processDateData(Document website, String category, String elementClass, String container, String id, String tag)

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
private static List<String> processBulletinBoardData(Document website, String category, String elementClass, String titleSelector, String dateSelector, String contentSelector, String subcontentSelector)

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
private static List<String> processPersonData(Document website, String category, String elementClass, String tag, String nameSelector, String groupSelector, String emailSelector)

/**
 * Diese Methode wird verwendet, um den Dateinamen für den Cache zu erstellen.
 * 
 * @param category Das Suchwort, für das der Cache erstellt werden soll.
 * @return Der Dateiname für den Cache.
 */
private static String getCacheFileName(String category)

/**
 * Diese Methode wird verwendet, um zu überprüfen, ob der Cache gültig ist.
 * 
 * @param cacheFileName       Der Dateiname des Caches.
 * @param cacheDurationMillis Die Dauer des Caches in Millisekunden.
 * @return true, wenn der Cache gültig ist, sonst false.
 */
private static boolean isCacheValid(String cacheFileName, long cacheDurationMillis)

/**
 * Diese Methode wird verwendet, um die Daten in den Cache zu speichern.
 * 
 * @param cacheFileName Der Dateiname des Caches.
 * @param data          Die Daten, die in den Cache gespeichert werden sollen.
 */
private static void saveToCache(String cacheFileName, List<String> data)

/**
 * Diese Methode wird verwendet, um die Daten aus dem Cache zu laden.
 * 
 * @param cacheFileName Der Dateiname des Caches.
 * @return Die Daten, die aus dem Cache geladen wurden.
 */
private static List<String> loadFromCache(String cacheFileName)
```
</details>

## Drittanbieterinformationen
```
jsoup License
The jsoup code-base (including source and compiled packages) are distributed under the open source MIT license as described below.

The MIT License
Copyright © 2009 - 2024 Jonathan Hedley (https://jsoup.org/)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

© 2024 mexikoedi 

Alle Rechte vorbehalten.
