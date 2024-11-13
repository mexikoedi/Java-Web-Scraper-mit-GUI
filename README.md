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
Durch den Knopf „Abrufen“ werden die Informationen zu den davor ausgewählten Suchwörtern abgerufen, formatiert und entsprechend im Bereich „Daten“ angezeigt. <br>
Es gibt zudem ein Textfeld, in welchem Sie Schlagwörter eingeben können, um so schnell, durch das Betätigen des „Suchen“-Knopfes, zu den entsprechenden Informationen zu gelangen. <br>
TODO

### Leistung
TODO

### Beispiele
TODO

## Entwicklerdokumentation
### Informationen zum Java-Projekt
Dieses Java-Projekt wurde mithilfe der [Eclipse IDE](https://eclipseide.org/) mit der Version „2024-09“ erstellt. <br>
Zudem war die [Java JDK](https://www.oracle.com/java/technologies/downloads/) mit der Version „22.0.2“ im Einsatz. <br>
Dazu wurde eine Drittanbieterbibliothek namens [jsoup](https://jsoup.org/download) mit der Version „1.18.1“ verwendet, um verschiedene HTML-Operationen durchzuführen. <br>
Es wurde [Eclipse WindowBuilder](https://projects.eclipse.org/projects/tools.windowbuilder) genutzt, um die grafische Oberfläche zu realisieren. <br>
Aufgrund von Geschwindigkeitsverbesserungen wird das Abrufen von Informationen mithilfe von „Java Parallel Streams“ umgesetzt. <br>
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

TODO <br>
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
public Map<String, List<String>> getScrapedDataMap()

/**
 * Diese Methode wird verwendet, um zu überprüfen, ob die Schaltfläche "Abrufen"
 * gedrückt wurde. Zudem werden die letzten ausgewählten Suchwörter gespeichert.
 * Enthält verschiedene Überprüfungen, um Sonderfälle abzudecken.
 * 
 * @param list Die Liste der ausgewählten Suchwörter.
 * @return true, wenn die Schaltfläche gedrückt wurde und keiner der Sonderfälle
 *         aufgetreten ist, sonst false.
 */
public boolean checkButtonPressed(List<String> list)
```

Paket: jwsg <br>
Datei: JWSGScrapingConfig.java

```java
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
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und URLs zu
 * erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und URLs.
 */
public static Map<String, String> getKeywordUrlMap()

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
 * Elementklassen zu erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und Elementklassen.
 */
public static Map<String, String> getKeywordElementMap()

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
 * Containern zu erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und Containern.
 */
public static Map<String, String> getKeywordContainerMap()

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und IDs zu
 * erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und IDs.
 */
public static Map<String, String> getKeywordIdMap()

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und Tags zu
 * erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und Tags.
 */
public static Map<String, String> getKeywordTagMap()

/**
 * Diese Methode wird verwendet, um die gesamte Map mit Suchwörtern und
 * Selektoren für spezifische Links zu erhalten.
 * 
 * @return Die gesamte Map mit Suchwörtern und Selektoren für spezifische Links.
 */
public static Map<String, String> getKeywordSelectorMap()
```
</details>

<details>
<summary>private Methoden</summary>

TODO <br>
Paket: jwsg <br>
Datei: JWSGLayout.java

```java
/**
 * GUI-Inhalte vom Frame werden initialisiert und das Fenster wird nach dem OS
 * angepasst, ansonsten wird ein Fehler ausgegeben.
 */
private void initialize()

/**
 * JList wird mit den vordefinierten Suchwörtern initialisiert.
 * 
 * @return DefaultListModel<String> welche die Liste mit den initialen Inhalten
 *         enthält.
 */
private DefaultListModel<String> initList()
```

Paket: jwsg <br>
Datei: JWSGLogic.java

```java	
/**
 * Diese Methode wird verwendet, um die Daten von den ausgewählten Suchwörtern
 * mit ihren entsprechenden URLs parallel abzurufen.
 *
 * @param categories Die Liste der ausgewählten Suchwörter.
 */
private void scrapData(List<String> categories)

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
private List<String> processWebsiteData(Document website, String elementClass, String container, String id, String tag, String linkSelector)
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
