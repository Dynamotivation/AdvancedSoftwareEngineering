<div align="center">
    <h1>Wohnheimverwaltung</h1>
    <img src="Documentation Ressources/ProjectLogo.png" width="100" height="100"></img>
    <p><strong>Ein Verwaltungswerkzeug für Vermieter von Wohnheimen</strong></p>
</div>



# 1. Domain Driven Design

## Ubiquitous Language

[//]: # (Sadly GitHub doesn't support Markdown includes, so I'm just gonna dump these tables here...)

| Begrifflichkeiten       |                                                                                                                                                                                                                                                 |
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Gebäude/Wohnheim/Objekt | Ein Gebäude, dass eine oder mehrere Mietswohnungen enthält über die ein Vermieter verfügt.                                                                                                                                                      |
| Miete                   | Ein pro Mietswohnung festgelegter Betrag, welcher von allen Mietern anteilig bezahlt wird.                                                                                                                                                      |
| Mieter                  | Mietet einer Mietswohnung von einem Vermieter in einem Gebäude.                                                                                                                                                                                 |
| Mietswohnung            | Ein an eine oder mehrere Mieter vermietbarer Bestandteil eines Gebäudes.                                                                                                                                                                        |
| Mietvertrag             | Ein Vertrag zwischen einem Vermieter und allen Mietern in einer Wohnung, welcher durch seine Eckdaten (Miete, Vertragsbeginn, Befristung, Vertragsende im Falle von Befristung, Kündigungsfristen falls keine Befristung) charakterisiert wird. |
| Vermieter               | Eine zur Verwaltung von Mietswohnungen in einem Gebäude berechtigte Person. Oftmals der Anwender der Software oder dessen Arbeitgeber.                                                                                                          |

| Prozesse  |                                                                                                                          |
|-----------|--------------------------------------------------------------------------------------------------------------------------|
| Ausziehen | Der Prozess des Verlassens eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen. |
| Einziehen | Der Prozess des beziehen eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen.   |
| Mieten    | Der Prozess des Bewohnen einer Mietswohnung durch einen Mieter unter Einhaltung der Bedingungen des Mietsvertrags.       |
| Vermieten | Der Prozess in dem ein Vermieter und ein oder mehrere Mieter sich auf einen Mietvertrag einigen und diesen Abschließen.  |

(Analysieren Sie die Fachlichkeit Ihrer Problemdomäne, indem Sie die relevanten Begriffe ✅ und deren fachliche Bedeutung ❔, Aufgaben ✅ und Regeln ❌ dokumentieren)


## Verwendete taktischer Muster des DDD
(Alle genannten Muster des DDD sind im Source Code zu verwenden (Value Objects, Entities, Aggregates, Repositories, Domain Services); Begründen Sie jedes einzelne, oben genannte Muster anhand von je einem konkreten Beispiel aus Ihrem Source Code; „XX ist als ValueObject implementiert, weil…")



# 2. Clean Architecture

## Geplante Schichtenarchitektur
(Welche Schichten umfasst ihre Anwendung und warum; Welche Aufgaben erfüllt welche Schicht; Welche Schichten sind umgesetzt)



# 3. Programming Principles

## Fokusierte Programmierprinzipien
(Begründen Sie für 5 (FÜNF) der vorgestellten Prinzipien aus SOLID, GRASP, DRY, … WO das jeweilige Prinzip in Ihrem Projekt berücksichtigt wird bzw. angewendet wird und nennen Sie ein Beispiel AUS IHREM SOURCE CODE dazu; „Das Single-Responsibility-Principle besagt, … Dies wird zum Beispiel in der Klasse XX berücksichtigt, weil…“)

<ol>
    <li></li>
    <dd></dd>
    <li></li>
    <dd></dd>
    <li></li>
    <dd></dd>
    <li></li>
    <dd></dd>
    <li></li>
    <dd></dd>
</ol>



# 5. Refactoring

## Identifizierte Code Smells
(mindestens 4 Code Smells; Auszug SonarQube o.ä.; Link zu Commit mit letzten smell (falls refactored); https://refactoring.guru/refactoring/smells)


## Durchgeführte Refactorings
(2 konkrete Refactorings; Commit des Refactorings verlinken; Begründen; https://refactoring.guru/refactoring/techniques)



# 6. Entwurfsmuster
(Nicht erlaubt: alle DDD-Muster, Singleton)

## Gewählte(s) Entwurfsmuster
- Warum setzen Sie dieses Muster an dieser Stelle ein
- Wie verbessert das Muster den Code
- Welche Vorteile/Nachteile gib es durch den Einsatz dieses Musters
- Welche Vorteile/Nachteile gäbe es ohne dieses Muster
- ...
- (Codestelle verlinken)