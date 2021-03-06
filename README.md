Code Kata Rock Paper Scissors
=============================
[![travis-ci](https://travis-ci.org/MehrCurry/RockPaperScissors.svg?branch=master)](https://travis-ci.org/MehrCurry/RockPaperScissors)
[![Circle CI](https://circleci.com/gh/MehrCurry/RockPaperScissors/tree/master.svg?style=svg)](https://circleci.com/gh/MehrCurry/RockPaperScissors/tree/master)


Getting started
---------------
Gebaut wird das Projekt mittels

    mvn clean package
    
Die Dokumentation wird mittels

    mvn -Pdoc javadoc:javadoc
    
gebaut. Dafür muss das Programm `dot` aus dem `graphviz` Paket im Pfad verfügbar sein, da `plantuml` in der JavaDoc Dokumentation
verwendet wird.

Gestartet wird das Programm mittels

    java -jar target/rockpaperscissors.jar

oder

    mvn spring-boot:run
    
Ein Spiel anlegen:

    curl -X POST http://localhost:8080/game
    
    {"choices":[{"name":"Schere"},{"name":"Stein"},{"name":"Papier"}],"id":"f0e4c473-e5c3-41af-8467-fec7d946eca6"}

Alternativ kann man auch ein Spiel mit erweiterten Regeln ("Brunnen") anlegen:

    curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d
      '{"type": "EXTENDED"}' 'http://localhost:8080/game'

    {"choices":[{"name":"Schere"},{"name":"Stein"},{"name":"Papier"},{"name":"Brunnen"}],"id":"95cb40ae-7b28-4726-9e9c-c301af551ded"}

Mittels der id kann man nun ein Match ausführen:
    
    curl -H "Content-Type: application/json" -X POST -d '{"gameId": "f0e4c473-e5c3-41af-8467-fec7d946eca6","choice":"stein"}' http://localhost:8080/match
    
    {"gameId":"f0e4c473-e5c3-41af-8467-fec7d946eca6","yourChoice":"Stein","opponentsChoice":"Papier","result":"LOOSE"}

Ansonsten steht auch das [Swagger-UI](http://localhost:8080/swagger-ui.html) zur Verfügung um die REST Services zu testen.

* [Create Game](http://localhost:8080/swagger-ui.html#!/game-resource/createGameUsingPOST)
* [Create Match](http://localhost:8080/swagger-ui.html#!/match-resource/singleMatchUsingPOST)

Javadoc
-------
Die Javadoc Kommentare verwenden teilweise Asciidoc und PlantUML. Daher muss zum erzeugen
der Dokumentation das Paket `Graphviz` auf dem Rechner verfügbar sein. Genauer gesagt
das `dot` Binary muss im Pfad vorhanden sein.

Die UML Dokumentation des Entwurfs befindet sich in der Klasse `Game`.

Statische Codeanalyse
---------------------
Das Projekt ist für die Verwendung von SonarQube vorbereitet.  Zur Messung der
Testabdeckung muss das `coverage` Profile aktiviert werden.

    mvn -Pcoverage -Dmaven.javadoc.skip clean verify sonar:sonar -Dsonar.host.url=http:<your-sonar-server>:9000
    
Die Testabdeckung für Unit- und Integrationstests wird mittels JaCoCo gemessen
und steht für die Auswertung mit SonarQube im `target` zur Auswertung bereit.

Alles in einer Zeile wäre dann:

    mvn -Pcoverage -Pdoc clean package sonar:sonar -Dsonar.host.url=http:<your-sonar-server>:9000
    
Stateful vs. Stateless
----------------------
In der aller einfachsten Variante hätte man den Service auch komplett Stateless bauen können,
indem man das erzeugen eines neuen Spiels und einen Zug in einem Aufruf zusammenfasst.

Ich habe mich für eine zustandsbehaftete Lösung entschieden, indem die erzeugen Spiele im
Speicher gehalten werden und zukünftig über ihre Id referenziert werden können. Die erschien
mir praxisnäher - könnte man aber kritisieren als Verstoß gegen das YAGNI Prinzip.

Einfache Lösung?
----------------
Ihr habt gesagt: "Wir mögen einfache Lösungen." Die Frage ist: "Was ist eine einfache Lösung?"

Ich habe die Lösung zunächste als reines Domänenmodell entwicklelt (DDD) und erst später mit
Spring Boot die REST Infrastruktur dazu gebaut.

Die Klasse GameController könnte man leicht weglassen, ich habe mich aber im Sinne einer hexagonalen
Architektur (oder auch Entity-Control-Boundary) dafür entschieden, die Klasse hinzuzunehmen.

Ich habe ganz bewusst versucht, durch den Einsatz von funktionaler Programmierung (`Stream`, `Optional`, ...)
die zyklomatische Komplexität gering zu halten (maximal Wert ist aktuell 2 für die Klasse `Choice`).
Für einen Entwickler, der mit den Möglichkeiten von Java 8 nicht vertraut ist, mag mein Code ungewohnt
oder aber sogar kompliziert aussehen. Ich habe "einfach" als kurz, mit wenigen Verzweigungen interpretiert.
 
Desweiteren habe ich versucht, den Boilerplate Code durch Einsatz von Lombok zu minimieren. Gerne können wir
beim nächsten Mal diskutieren, ob das nun einfach ist, oder nicht.

Neben der eigentlichen Lösung in den Java Klassen war auch wichtig, ein relalistisches Projekt Setup in der
`pom.xml` zu demonstrieren. Dazu gehört die nach Unit- und Intergrationstest getrennte Messung der Code
Coverage, Asciidoc und UML Support in den JavaDocs und für mich auch immer Docker Support. War zwar nicht
explizit gefordert, gehört für mich aber dazu.

Außerdem sind Definitionsdateien für diverse CI Systeme vorhanden: Travis, Circle, Gitlab und Jenkins. Das
`Jenkinsfile` ist relativ schlecht getestet. Die anderen drei System habe ich im Einsatz.