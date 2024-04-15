<div align="center">
    <h1>Wohnheimverwaltung</h1>
    <img src="Documentation Ressources/ProjectLogo.png" width="100" height="100"></img>
    <p><strong>Ein Verwaltungswerkzeug für Vermieter von Wohnheimen</strong></p>
</div>



# 1. Domain Driven Design

## Ubiquitous Language

[//]: # (Sadly GitHub doesn't support Markdown includes, so I'm just gonna dump these tables here...)

| Begrifflichkeiten                           | Fachliche Bedeutung                                                                                                                                                                                                                             | Regeln                                                                                 |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| Mietobjekt (Rental)                     | Eine Mietobjekt ist ein Mietwohnhaus oder eine Mietwohnung in einem Miethaus. Sie kann an einen oder mehrere Mieter vermietet werden.                                                                                                       | - Obergrenze an Mietern muss mindestens 1 betragen<br/>- Fläche muss größer als 0 sein |
| Miethaus (Apartment Complex)                | Ein Gebäude, dass eine oder mehrere Mietwohnungen enthält über die ein Vermieter verfügt.                                                                                                                                                       |                                                                                        |
| Mietwohnhaus (Property)                     | Eine konkrete Mietobjekt.                                                                                                                                                                                                                   | siehe "Mietobjekt"                                                                 |
| Mietwohnung (Apartment)                     | Eine konkrete Form einer Mietobjekt, welche ein Bestandteil eines Miethauses ist.                                                                                                                                                           | - Genau ein zugehöriges Mietshaus<br/>- siehe Mietobjekt                           |
| Mieter (Tenant)                             | Mietet eine Mietobjekt unter den Bedingungen eines Mietvertrages.                                                                                                                                                                           | - Plausible Kontaktdaten                                                               |
| Miete (Rent)                                | Ein im Mietvertrag festgelegter Betrag, welcher von allen Mietern bezahlt wird.                                                                                                                                                                 | - Positiv                                                                              |
| Mietvertrag (Lease Agreement)               | Ein Vertrag zwischen einem Vermieter und allen Mietern in einer Wohnung, welcher durch seine Eckdaten (Miete, Vertragsbeginn, Befristung, Vertragsende im Falle von Befristung, Kündigungsfristen falls keine Befristung) charakterisiert wird. | - Mindestens ein Mieter                                                                |
| Transaktion (Transaction)                   | Ein Dachbegriff für Forderungen und Zahlungen, welche einem Mieter zur Last gelegt werden.                                                                                                                                                      |                                                                                        |
| Forderung (Charge)                          | Eine konkrete Transaktion, welche eine Forderung über einen bestimmten Betrag an einen Mieter darstellt.                                                                                                                                        | - Forderung muss kleiner als 0 sein                                                    |
| Mietforderung (Rent Charge)                 | Eine konkrete Forderung, welche periodische Zahlung in Höhe der Miete darstellt.                                                                                                                                                                | siehe "Forderung"                                                                      |
| Außerordentliche Forderung (Special Charge) | Eine konkrete Art der Forderung über einen Betrag, welche über die reguläre Mietsforderung hinaus entstanden ist.                                                                                                                               | siehe "Forderung"                                                                      |
| Einzahlung (Payment)                        | Eine konkrete Form der Transaktion über einen bestimmten Geldbetrag, welcher zur Deckung akkumulierter Forderungen dient.                                                                                                                       | - Zahlungsbetrag größer als 0 sein                                                     |
| Startguthaben (Starting Balance)            | Eine konkrete Art der Transaktion, welche das Guthaben eines Mieters darstellt, sollte das Mietsverhältnis erst nachträglich in das System eingepflegt werden. Somit müssen nicht alle Rückliegenden Transaktionen eingepflegt werden.          |                                                                                        |
| Vermieter                                   | Eine zur Verwaltung von Mietwohnungen in einem Gebäude berechtigte Person. Oftmals der Anwender der Software oder dessen Arbeitgeber.                                                                                                          |

| Prozesse  |                                                                                                                          |
|-----------|--------------------------------------------------------------------------------------------------------------------------|
| Ausziehen | Der Prozess des Verlassens eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen. |
| Einziehen | Der Prozess des beziehen eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen.   |
| Mieten    | Der Prozess des Bewohnen einer Mietwohnung durch einen Mieter unter Einhaltung der Bedingungen des Mietsvertrags.       |
| Vermieten | Der Prozess in dem ein Vermieter und ein oder mehrere Mieter sich auf einen Mietvertrag einigen und diesen Abschließen.  |

(Analysieren Sie die Fachlichkeit Ihrer Problemdomäne, indem Sie die relevanten Begriffe ✅ und deren fachliche Bedeutung ✅, Aufgaben ❔ und Regeln ✅ dokumentieren)


## Verwendete taktischer Muster des DDD
(Alle genannten Muster des DDD sind im Source Code zu verwenden (Value Objects, Entities, Aggregates, Repositories, Domain Services); Begründen Sie jedes einzelne, oben genannte Muster anhand von je einem konkreten Beispiel aus Ihrem Source Code; „XX ist als ValueObject implementiert, weil…")

### Value Objects

#### Size
Die Größe / Fläche einer Mietobjekt besteht aus einer Zahl und einer Einheit und ist als Value Object implementiert. Der Wert kann aus einer beliebigen Kombination der akzeptierten Werte erzeugt werden und umgerechnet werden. Ändert sich in einer künftigen Version durch z.B. renovieren eine Größenangabe, so ist das Resultat eine neue Größe.


### Entities

#### Lease Agreement
Der Mietvertrag ist als Entity implementiert, weil er neben finalen Attributen auch einige veränderbare, wie beispielsweise das End-Datum, beinhaltet. Außerdem besitzt der Vertrag eine Identität, um ihn eindeutig ausmachen zu können.


### Aggregates

#### Tenant Aggregate
Das Tenant Aggregate wurde so gewählt, dass ein Mieter als Aggregate Root auftritt, denn in ihm werden verschiedene Personalien (Value Objects) und eine Liste der Mietverträge (Entities) zusammengeführt.


### Repositories

#### Tenant Repository
Das Tenant Repository existiert als zentrale Sammelstelle für alle Mieter. Diese herangehensweise wurde gewählt, damit ein Mieter nicht aufhört zu existieren, sobald sein letzer Mietvertrag gekündigt ist. Bei der Persistierung wird das Repository alle Mieter serialisieren, welche nicht bereits durch Objektreferenzen von anderen Repositories serialisiert wurden.

### Domain Services

#### TODO



# 2. Clean Architecture

[//]: # (Welche Schichten umfasst ihre Anwendung und warum; Welche Aufgaben erfüllt welche Schicht; Welche Schichten sind umgesetzt)

## Geplante Schichtenarchitektur
Für einen anschaulichen Prototypen der Software sind die folgenden drei Schichten wichtig und bereits implementiert.

### Domain Layer
Implementiert: ✅\
In der Domain Layer befindet sich der logische Kern der Anwendung und dessen Regeln, eine Implementierung der Ubiquitous Language mit samt ihrer Regeln. Sie ist der Kern, denn ohne sie geht nichts. Änderungen treten nur bei der Feststellung von Bugs oder Veränderungen an der Definition der Ubiquitious Language auf.

### Application Layer
Implementiert: ✅\
Die Application Layer enthält eine andere Art Services als die Domänenschicht, denn sie behandeln nicht fachlische Aspekte, sondern technische. In der vorliegenden Implementation dienen die Services als Zugriffsweg auf die Repositories. Dieser Zwischenstopp ist notwendig, damit die Domain Layer sauber nur fachlich relevante Funktionen ihren Repository Interfaces implementieren muss, während der zugehörige Service weitere verbos-benannte Funktionen anbieten kann. Damit ist die Anbindung in die Presentation Logic einfacher und klar lesbar. Außerdem können künftig durch die map-Befehle auch sehr einfach Adapter eingesetzt werden. Im aktuellen Stadium werden Domain Entities 1-zu-1 auf Domain Entity DTO (Date Transfer Objects) gespiegelt, wodurch all ihre Variablen final und read only werden.

### Plugin Layer
Implementiert: ✅\
Die Plugin Layer repräsentiert die kurzlebige äußere Schicht im Domain Driven Design. Darunter fallen beispielsweise Implementierungen der Repository Interfaces der Domain Layer auf eine bestimmte Speichermethode (versch. DB-Driver, Filesystem, Serialization) und verschiedene die Nutzerschnittstelle (Frontends, CLI).

#### Presentation Layer
Die Presentation Layer Umfasst hier das JavaFX Frontend der Anwendung. Dies ist nötig, damit ein Nutzer das Programm verwenden kann.

#### Persistence Layer
Speicherung wird in der Persistence Layer gehandhabt. In der vorliegenden Anwendung wird dies durch Serialisierung in das JSON Format gehandhabt. Dafür wurde die Bibliothek Jackson verwendet.



# 3. Programming Principles

## Fokussierte Programmierprinzipien

[//]: # (Begründen Sie für 5 der vorgestellten Prinzipien aus SOLID, GRASP, DRY, … WO das jeweilige Prinzip in Ihrem Projekt berücksichtigt wird bzw. angewendet wird und nennen Sie ein Beispiel AUS IHREM SOURCE CODE dazu; „Das Single-Responsibility-Principle besagt, … Dies wird zum Beispiel in der Klasse XX berücksichtigt, weil…“)

<ol>
<li>SOLID - SRP - Single Responsibility Principle</li>
<dd>TODO anhand Lease Agreement
</dd>
<li>SOLID - DIP - Dependency Inversion Principle</li>
<dd>TODO anhand Repo</dd>
<li>GRASP - Pure Fabrication</li>
<dd>TODO anhand Repo</dd>
<li>GRASP - Protected Variations</li>
<dd>TODO anhand Repo</dd>
<li>YAGNI - You ain't gonna need it</li>
<dd>TODO anhand einer NotImplemented</dd>
</ol>



# 5. Refactoring

## Identifizierte Code Smells
(mindestens 4 Code Smells; Auszug SonarQube o.ä.; Link zu Commit mit letzten smell (falls refactored); https://refactoring.guru/refactoring/smells)

### Long Parameter List

### Primitive Obsession


### Alternative Classes with Different Interfaces
Das ContactAvenue (Kontaktmöglichkeit) Interface ist im aktuellen Zustand nicht sehr nützlich, da es leer ist.
```java
public interface ContactAvenue {
}
```
Somit dient es nur dem "gruppieren" verschiedener Kontaktmöglichkeiten. Schaut man nun auf die implementieren Klassen, wird die Problematik schnell erkenntlich: Die Klassen verhalten sich identisch, jedoch haben sie aus semantischen Gründen verschiedene Namen.

```java
public class ContactAvenueEmail implements ContactAvenue {
    public String getEmail() { return email; }
    ...

public class ContactAvenueMail implements ContactAvenue {
    public Address getAddress() { return address; }
    ...

public class ContactAvenuePhone implements ContactAvenue {
    public PhoneNumber getPhone() { return phone; }
    ...
```

Mit einem Refactoring könnte diese Funktionalität in eine Abstrakte Klasse gezogen werden und einen generischen Namen wie "getContact" bekommen. Da die Validierung, welche durch die verschiedenen Datentypen erzielt wurde, ist bereits geschehen, kann hier der Rückgabetyp auf String geändert werden.

### Shotgun Surgery
Betrachtet man die Implementation des Interface Rental, so fällt auf, dass keinerlei Funktionen im Bezug auf die Anschrift einer Mietobjekt vorgeschrieben werden. Bei genauerem hinsehen fällt jedoch auf, dass sowohl RentalApartmentUnit als auch RentalProperty diese Informationen auf verschiedene Arten hinterlegt haben. Im Falle von RentalProperty sind die Daten direkt hinterlegt.

```java
public class RentalProperty implements Rental {
    private final Address address;
    private final LocalDate dateOfConstruction;
    ...
```

RentalApartmentUnit hingegen überlässt die Speicherung der Adressdaten jedoch dem ApartmentComplex Entity. Außerdem führt es weitere Felder ein, welche im Kontext eines Wohnheims zutreffend sind.
```java
public class RentalApartmentUnit implements Rental {
    private int apartmentNumber;
    private final int floor;
    private final ApartmentComplex parentApartmentComplex;
    ...

public class ApartmentComplex {
    private final Address address;
    private final LocalDate dateOfConstruction;
    ...
```

Daraus folgt, dass bei einer Änderung an einem der Entities, mindestens auch das Gegenstück geändert werden muss. Betrachtet man die weiteren Auswirkungen durch diese nachteilige Anbindung, fällt auf, dass vermutlich noch weitere Stellen in anderen Ebenen als der Domäne betroffen sind.\
Eine mögliche Korrektur ist möglich, indem man delegierende Methoden einführt, welche die Aufrufe an ApartmentComplex weiterleiten. Diese sollte bei der nächsten Änderung anstelle der Shotgun Surgery durchgeführt werden.



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