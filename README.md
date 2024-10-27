# Java Web Scraper mit GUI

## Allgemeine Informationen
Dieses Java-Programm ermöglicht das unkomplizierte Abrufen von diversen [HKA (Hochschule Karlsruhe – University of Applied Sciences)](https://www.h-ka.de/) Informationen über eine grafische Benutzeroberfläche.

## Anwenderdokumentation
### Voraussetzungen
Es muss vor der Benutzung dieses Programms [Java](https://www.java.com/de/download/) auf dem Computer installiert werden.

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
Im Paket „jwsg“ befindet sich die Datei mit dem Namen „JWSGUI.java“, welche die grafische Oberfläche beherbergt. <br>
Zudem existiert noch die Datei namens „TODO“, welche für die Logik zuständig ist. <br>
TODO

### Einrichtung des Projekts
Sie können das Projekt durch die Befolgung der [Installationsanleitung](#Installation-des-Programms) herunterladen oder durch das [Klonen](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository) des Projekts in Eclipse einbinden. <br>
Beachten Sie zuvor die [Informationen zum Java-Projekt](#Installation-des-Programms).
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
TODO
</details>

<details>
<summary>private Methoden</summary>
TODO
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
