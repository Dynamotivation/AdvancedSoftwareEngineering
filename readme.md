<div align="center">
    <h1>Wohnheimverwaltung</h1>
    <img src="Documentation Ressources/ProjectLogo.png" width="100" height="100"></img>
    <p><strong>Ein Verwaltungswerkzeug für Vermieter von Wohnheimen</strong></p>
</div>



- [1. Domain Driven Design](#1-domain-driven-design)
  - [Ubiquitous Language](#ubiquitous-language)
  - [Verwendete taktischer Muster des DDD](#verwendete-taktischer-muster-des-ddd)
- [2. Clean Architecture](#2-clean-architecture)
  - [Geplante Schichtenarchitektur](#geplante-schichtenarchitektur)
- [3. Programming Principles](#3-programming-principles)
  - [Fokussierte Programmierprinzipien](#fokussierte-programmierprinzipien)
- [5. Refactoring](#5-refactoring)
  - [Identifizierte Code Smells](#identifizierte-code-smells)
  - [Durchgeführte Refactorings](#durchgeführte-refactorings)
- [6. Entwurfsmuster](#6-entwurfsmuster)
  - [Gewählte(s) Entwurfsmuster](#gewähltes-entwurfsmuster)


<div class="page"/>


# 1. Domain Driven Design

## Ubiquitous Language

[//]: # (Sadly GitHub doesn't support Markdown includes, so I'm just gonna dump these tables here...)

| Begrifflichkeiten                           | Fachliche Bedeutung                                                                                                                                                                                                                             | Regeln                                                                                 |
|---------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| Mietobjekt (Rental)                     | Eine Mietobjekt ist ein Mietwohnhaus oder eine Mietwohnung in einem Wohnheim. Sie kann an einen oder mehrere Mieter vermietet werden.                                                                                                       | - Obergrenze an Mietern muss mindestens 1 betragen<br/>- Fläche muss größer als 0 sein |
| Wohnheim (Apartment Complex)                | Ein Gebäude, dass eine oder mehrere Mietwohnungen enthält über die ein Vermieter verfügt.                                                                                                                                                       |                                                                                        |
| Mietwohnhaus (Property)                     | Eine konkrete Mietobjekt.                                                                                                                                                                                                                   | siehe "Mietobjekt"                                                                 |
| Mietwohnung (Apartment)                     | Eine konkrete Form einer Mietobjekt, welche ein Bestandteil eines Wohnheimes ist.                                                                                                                                                           | - Genau ein zugehöriges Mietshaus<br/>- siehe Mietobjekt                           |
| Mieter (Tenant)                             | Mietet eine Mietobjekt unter den Bedingungen eines Mietvertrages.                                                                                                                                                                           | - Plausible Kontaktdaten                                                               |
| Miete (Rent)                                | Ein im Mietvertrag festgelegter Betrag, welcher von allen Mietern bezahlt wird.                                                                                                                                                                 | - Positiv                                                                              |
| Mietvertrag (Lease Agreement)               | Ein Vertrag zwischen einem Vermieter und allen Mietern in einer Wohnung, welcher durch seine Eckdaten (Miete, Vertragsbeginn, Befristung, Vertragsende im Falle von Befristung, Kündigungsfristen falls keine Befristung) charakterisiert wird. | - Mindestens ein Mieter                                                                |
| Transaktion (Transaction)                   | Ein Dachbegriff für Forderungen und Zahlungen, welche einem Mieter zur Last gelegt werden.                                                                                                                                                      |                                                                                        |
| Forderung (Charge)                          | Eine konkrete Transaktion, welche eine Forderung über einen bestimmten Betrag an einen Mieter darstellt.                                                                                                                                        | - Forderung muss kleiner als 0 sein                                                    |
| Mietforderung (Rent Charge)                 | Eine konkrete Forderung, welche periodische Zahlung in Höhe der Miete darstellt.                                                                                                                                                                | siehe "Forderung"                                                                      |
| Außerordentliche Forderung (Special Charge) | Eine konkrete Art der Forderung über einen Betrag, welche über die reguläre Mietforderung hinaus entstanden ist.                                                                                                                               | siehe "Forderung"                                                                      |
| Einzahlung (Payment)                        | Eine konkrete Form der Transaktion über einen bestimmten Geldbetrag, welcher zur Deckung akkumulierter Forderungen dient.                                                                                                                       | - Zahlungsbetrag größer als 0 sein                                                     |
| Startguthaben (Starting Balance)            | Eine konkrete Art der Transaktion, welche das Guthaben eines Mieters darstellt, sollte das Mietverhältnis erst nachträglich in das System eingepflegt werden. Somit müssen nicht alle zurückliegenden Transaktionen eingepflegt werden.          |                                                                                        |
| Vermieter                                   | Eine zur Verwaltung von Mietwohnungen in einem Gebäude berechtigte Person. Oftmals der Anwender der Software oder dessen Arbeitgeber.                                                                                                          |


<div class="page"/>

| Prozesse  |                                                                                                                          |
|-----------|--------------------------------------------------------------------------------------------------------------------------|
| Ausziehen | Der Prozess des Verlassens eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen. |
| Einziehen | Der Prozess des beziehen eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen.   |
| Mieten    | Der Prozess des Bewohnen einer Mietwohnung durch einen Mieter unter Einhaltung der Bedingungen des Mietvertrags.       |
| Vermieten | Der Prozess in dem ein Vermieter und ein oder mehrere Mieter sich auf einen Mietvertrag einigen und diesen Abschließen.  |

[//]: # (Analysieren Sie die Fachlichkeit Ihrer Problemdomäne, indem Sie die relevanten Begriffe ✅ und deren fachliche Bedeutung ✅, Aufgaben ❔ und Regeln ✅ dokumentieren)


## Verwendete taktischer Muster des DDD
(Alle genannten Muster des DDD sind im Source Code zu verwenden (Value Objects, Entities, Aggregates, Repositories, Domain Services); Begründen Sie jedes einzelne, oben genannte Muster anhand von je einem konkreten Beispiel aus Ihrem Source Code; „XX ist als ValueObject implementiert, weil…")

### [**Value Objects**](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/)

#### [Size](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/Size.java)
Die Größe / Fläche einer Mietobjekt besteht aus einer Zahl und einer Einheit und ist als Value Object implementiert. Der Wert kann aus einer beliebigen Kombination der akzeptierten Werte erzeugt werden und umgerechnet werden. Ändert sich in einer künftigen Version durch z.B. renovieren eine Größenangabe, so ist das Resultat eine neue Größe.


---
### [**Entities**](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/)

#### [Lease Agreement](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/LeaseAgreement.java)
Der Mietvertrag ist als Entity implementiert, weil er neben finalen Feldern auch einige veränderbare, wie beispielsweise das End-Datum, beinhaltet. Außerdem besitzt der Vertrag eine Identität, um ihn eindeutig ausmachen zu können.


---
### [**Aggregates**](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/)

#### [Tenant Aggregate](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/Tenant.java)
Das Mieter Aggregate wurde so gewählt, dass ein Mieter als Aggregate Root auftritt, denn in ihm werden verschiedene Personalien (Value Objects (Kontaktdaten, Name und ID)) und eine Liste der Mietverträge (Entities) zusammengeführt. Außerdem führt es eine Liste der zugehörigen ausstehenden Transaktionen, die "lazy" anhand der Mietverträge, des Zahltags und des letzten Zahlungsdatums abgerufen.


---
### [**Repositories**](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/)

#### [Tenant Repository](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/TenantRepository.java)
Das Tenant Repository existiert als zentrale Sammelstelle für alle Mieter. Diese Herangehensweise wurde gewählt, damit ein Mieter nicht aufhört zu existieren, sobald sein letzter Mietvertrag gekündigt ist. Bei der Speicherung empfängt das Repository einzeln die zu serialisierenden Mieter. Dadurch wird es Application Services ermöglicht beispielsweise nur alle Mieter zu serialisieren, welche nicht bereits durch Objektreferenzen von anderen Repositories serialisiert wurden.


---
### [**Domain Services**](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/services/)

#### [Renting Service](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/services/RentingService.java)
Der Renting Service ermöglicht es in den jeweiligen Implementierungen des Rental Interface den Code nicht doppelt implementieren oder Rental zu einer abstrakten Klasse ändern zu müssen. So können die Implementierungen auf die Validierung der Felder und ausgeübten Aktionen fokussiert bleiben. Namentlich kann so der "[Large Class](https://refactoring.guru/smells/large-class)" Code Smell verhindert werden. 


<div class="page"/>


# 2. Clean Architecture

[//]: # (Welche Schichten umfasst ihre Anwendung und warum; Welche Aufgaben erfüllt welche Schicht; Welche Schichten sind umgesetzt)

## Geplante Schichtenarchitektur
Für einen anschaulichen Prototypen der Software sind die folgenden drei Schichten wichtig und bereits implementiert.

### [**Domain Layer**](/Wohnheimverwaltung/2-domain/)
Implementiert: ✅\
In der Domain Layer befindet sich der logische Kern der Anwendung und dessen Regeln, eine Implementierung der Ubiquitous Language mit samt ihrer Regeln. Sie ist der Kern, denn ohne sie geht nichts. Änderungen treten nur bei der Feststellung von Bugs oder Veränderungen an der Definition der Ubiquitious Language auf.


---
### [**Application Layer**](/Wohnheimverwaltung/1-application/)
Implementiert: ✅\
Die Application Layer enthält eine andere Art Services als die Domänenschicht, denn sie behandeln nicht fachlische Aspekte, sondern technische. In der vorliegenden Implementation dienen die Services als Zugriffsweg auf die Repositories. Dieser Zwischenstopp ist notwendig, damit die Domain Layer sauber nur fachlich relevante Funktionen ihren Repository Interfaces implementieren muss, während der zugehörige Service weitere verbos-benannte Funktionen anbieten kann. Damit ist die Anbindung in die Presentation Logic einfacher und klar lesbar. Außerdem können künftig durch die map-Befehle auch sehr einfach Adapter eingesetzt werden. Im aktuellen Stadium werden Domain Entities 1-zu-1 auf Domain Entity DTO (Date Transfer Objects) gespiegelt, wodurch all ihre Felder final und read only werden.


---
### [**Plugin Layer**](/Wohnheimverwaltung/0-plugins/)
Implementiert: ✅\
Die Plugin Layer repräsentiert die kurzlebige äußere Schicht im Domain Driven Design. Darunter fallen beispielsweise Implementierungen der Repository Interfaces der Domain Layer auf eine bestimmte Speichermethode (versch. DB-Driver, Filesystem, Serialization) und verschiedene die Nutzerschnittstelle (Frontends, CLI).

#### [Presentation Layer](/Wohnheimverwaltung/0-plugins/0-presentation/)
Implementiert: 〰️ (Einige Use Cases sind exklusiv programmatisch Aufrufbar)\
Die Presentation Layer Umfasst hier das JavaFX Frontend der Anwendung. Dies ist nötig, damit ein Nutzer das Programm verwenden kann.

#### [Persistence Layer](/Wohnheimverwaltung/0-plugins/0-persistence/)
Implementiert: ✅\
Speicherung wird in der Persistence Layer gehandhabt. In der vorliegenden Anwendung wird dies durch Serialisierung in das JSON Format gehandhabt. Dafür wurde die Bibliothek Jackson verwendet.


<div class="page"/>


# 3. Programming Principles

## Fokussierte Programmierprinzipien

[//]: # (Begründen Sie für 5 der vorgestellten Prinzipien aus SOLID, GRASP, DRY, … WO das jeweilige Prinzip in Ihrem Projekt berücksichtigt wird bzw. angewendet wird und nennen Sie ein Beispiel AUS IHREM SOURCE CODE dazu; „Das Single-Responsibility-Principle besagt, … Dies wird zum Beispiel in der Klasse XX berücksichtigt, weil…“)

### **1. SOLID - SRP - Single Responsibility Principle**
Das Single Responsibility Principle besagt, dass eine Klasse nur eine Aufgabe und somit nur einen Grund zur Änderung hat. Dieses Prinzip wird in diesem Projekt an der Klasse [Lease Agreement](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/LeaseAgreement.java) demonstriert. Augenscheinlich hat die Klasse zwar ebenfalls Methoden, um den zugehörigen Mietern Miete zu berechnen, jedoch delegiert diese nur an eine Implementierung des Interface [Rent Charger](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/miscellaneous/RentCharger.java) um die Kapselung der Felder zu wahren. Durch diese Separation of Concerns werden die meisten Änderungen nur den Code einer der Klassen betreffen.

<details>
    <summary>Code Beispiel</summary>

```java
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class LeaseAgreement {
    private final LocalDate inclusiveStartDate;
    private final NthDayOfMonthAdjuster monthlyDayOfPayment;
    private final Rent rent;
    private final List<Tenant> tenants;
    private final LeaseAgreementId id;
    private final RentalId associatedRentalId;
    private final RentCharger rentCharger;
    private LocalDate inclusiveEndDate;
    private LocalDate nextPaymentDate;

    public LeaseAgreement(List<Tenant> tenants, LocalDate inclusiveStartDate,
                          Rent rent, int monthlyDayOfPayment, RentalId associatedRentalId) {
        this(
                inclusiveStartDate,
                null,
                monthlyDayOfPayment,
                inclusiveStartDate,
                rent,
                tenants,
                new LeaseAgreementId(),
                associatedRentalId,
                new DefaultRentCharger()
        );
    }

    @JsonCreator
    private LeaseAgreement(
            @JsonProperty("inclusiveStartDate") LocalDate inclusiveStartDate,
            @JsonProperty("inclusiveEndDate") LocalDate inclusiveEndDate,
            @JsonProperty("monthlyDayOfPayment") int monthlyDayOfPayment,
            @JsonProperty("nextPaymentDate") LocalDate nextPaymentDate,
            @JsonProperty("rent") Rent rent,
            @JsonProperty("tenants") List<Tenant> tenants,
            @JsonProperty("id") LeaseAgreementId id,
            @JsonProperty("associatedRentalId") RentalId associatedRentalId,
            @JsonProperty("rentCharger") RentCharger rentCharger
    ) {
        this.inclusiveStartDate = inclusiveStartDate;
        this.nextPaymentDate = inclusiveStartDate;
        this.monthlyDayOfPayment = new NthDayOfMonthAdjuster(monthlyDayOfPayment);
        this.tenants = tenants;
        this.id = id;
        this.rent = rent;
        this.associatedRentalId = associatedRentalId;
        this.rentCharger = rentCharger;

        // Notifies the tenants of the new lease agreement
        tenants.forEach(tenant -> tenant.addLeaseAgreement(this));
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public void setInclusiveEndDate(LocalDate inclusiveEndDate) {
        // TODO Minimum rental period

        // Validate that the end date is after the start date
        if (inclusiveEndDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public List<Tenant> getTenants() {
        return new ArrayList<>(tenants);
    }

    public NthDayOfMonthAdjuster getMonthlyDayOfPayment() {
        return monthlyDayOfPayment;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public Rent getRent() {
        return rent;
    }

    public LeaseAgreementId getId() {
        return id;
    }

    public RentalId getAssociatedRentalId() {
        return associatedRentalId;
    }

    public void chargeRent() {
        nextPaymentDate = rentCharger.chargeRent(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseAgreement that = (LeaseAgreement) o;
        return monthlyDayOfPayment == that.monthlyDayOfPayment && Objects.equals(inclusiveStartDate, that.inclusiveStartDate) && Objects.equals(inclusiveEndDate, that.inclusiveEndDate) && Objects.equals(tenants, that.tenants) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inclusiveStartDate, monthlyDayOfPayment, inclusiveEndDate, tenants, id);
    }
}
```
*Auszug aus LeaseAgreement.java*
</details>

---
### **2. SOLID - DIP - Dependency Inversion Principle**
Das Dependency Inversion Principle besagt, dass Module höherer Ebenen anstelle von Detail behafteten Objekten aus niedrigeren Schichten von generalisierenden Interfaces abhängig sein sollen. Diese werden Abstraktionen genannt. Die Details selbst hängen von der Abstraktion ab.

Die Abhängigkeit zu Abstraktionen lässt sich durch die Extraktion von Merkmalen in ein Interface oder eine Abstrakte Klasse durchführen. Dadurch entsteht ein Vertrag / Protokoll. Innerhalb einer Ebene und in allen Ebenen darüber kann man sich nun auf den Vertrag verlassen, die Details der Implementierung spielen keine Rolle.

Diese Prinzip wird in diesem Projekt von beispielsweise dem [Jackson Tenant Repository](/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/TenantJacksonJsonRepository.java) verwirklicht. Statt alle für die Use Cases benötigten Verwaltungsfunktionen in Services und Repositories der Domain Layer zu implementieren, wird hier im Falle der Repositories bloß die [Abstraktion](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/TenantRepository.java) mit den "Protokolldetails" festgehalten. Die wahren Details, wie genau das zwischenspeicher, abspeichern und laden abläuft, passieren in der erwähnten Jackson spezifischen Implementierung.

Hierzu kommt, dass das die Application Layer ebenfalls nur von der Abstraktion abhängig ist und gar nichts über die Details wissen muss. Also kann sie, ohne dass sie hierfür eine Abhängigkeit geschaffen werden muss, über Dependency Injection im Konstruktor dennoch mit einer über das Protokoll hinaus fremden Instanz umgehen.

*Persistence Layer - Die Details beruhen auf der Abstraktion*
```java
public class TenantJacksonJsonRepository implements TenantRepository {
    ...
```

*Presentation Layer (Einstiegspunkt) - Selbstverständlich kann eine Klasse die von Abstraktion und den Details abhängt mit beidem umgehen*
```java
public class MainApp {
     public static void main(String[] args) {
        // Create repositories
        ...
        TenantRepository tenantRepository = new TenantJacksonJsonRepository();

        // Inject Repositories into Application Layer
        ...
        tenantManagementService = new TenantManagementService(tenantRepository);
        ...
```

*Application Layer - Trotz der fehlenden Abhängigkeit zu den Details kann auch die Application Layer dank Vertrag bis zu dem vordefinierten Grad mit den Details umgehen*
```java
public class TenantManagementService {
    private final TenantRepository tenantRepository;

    public TenantManagementService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }
    ...

```

*Domain Layer - Der Vertrag wurde ohne jeglichen Bezug auf Jackson definiert und ist frei von Abhängigkeiten*
```java
public interface TenantRepository {
    List<Tenant> listAll();
    Tenant findById(TenantId tenantId);
    List<Tenant> findByRentalId(RentalId rentalId);
    List<Tenant> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId);
    void add(Tenant tenant);
    void save(Tenant tenant);
    void load();
}
```

Somit werden durch die Separation of Concerns die Regeln des Domain Driven Design zur Richtung von Abhängigkeiten gewahrt und Flexibilität geschaffen.


---
### **3. GRASP - Pure Fabrication**
Bei Pure Fabrication handelt es sich um eine eine Trennung von technischem und Domänenwissen. Das bedeutet, dass die Klasse keinen Bezug zu der Problemdomäne hat, sondern der technischen Umsetzung einen Dienst anbietet.

Auch hierbei können wieder die [Jackson Repository Implementierungen](/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/) betrachtet werden. Wie bereits bei dem [Dependency Inversion Principle](#2-solid---dip---dependency-inversion-principle) beschrieben, sind die Details von der Abstraktion getrennt. Die Details stellen hierbei das technische Wissen da.

<details>
    <summary>Code Beispiel</summary>

```java
public class RentalJacksonJsonRepository implements RentalRepository {
    private final List<Rental> rentals = new ArrayList<>();
    ...

    @Override
    public void save(Rental rental) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var writer = new FileWriter("rental.save", true)) {
            String jsonString = mapper.writeValueAsString(rental) + "\n";

            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var reader = new BufferedReader(new FileReader("rental.save"))) {
            while (reader.ready()) {
                String jsonString = reader.readLine();
                Rental rental = mapper.readValue(jsonString, Rental.class);
                rentals.add(rental);
            }

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```
*Auszug aus RentalJacksonJsonRepository.java*
</details>


---
### **4. KISS - Keep it Simple, Stupid**
Wie der Name verrät zielt das KISS Prinzip darauf ab, Dinge auf eine einfache Weiße zu implementieren, um unnötige Komplexität zu vermeiden.

Beispielweise beim Persistieren im [Jackson Rental Repository](/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/RentalJacksonJsonRepository.java) wird jede Entität einzeln als Zeile in eine Datei geschrieben. Selbstverständlich entsteht dadurch auch kein valide JSON Datei, da nur jede Zeile in sich selbst valide ist. Dabei wird allerdings das Problem vermieden, dass es nicht zu [Type Erasure](https://docs.oracle.com/javase/tutorial/java/generics/genTypes.html) wie beim Serialisieren einer ```List<Rental>``` kommt, was die Deserialisierung erheblich erleichtert. Als Konsequenz wird die gespeicherte Datei dann nicht ```.json``` sondern ```.save``` genannt, somit *erwartet* auch niemand korrekten JSON Syntax.\
Die ```load()``` Methode deserialisiert dann ebenfalls Zeile für Zeile.

KISS trifft hier eben zu, da diese vermeidlich "dumme" Lösung sehr einfach zu implementieren ist und weder ein eigener, komplexer Serialisierer oder Deserialisierer geschrieben werden muss.

<details>
    <summary>Code Beispiel</summary>

```java
public class RentalJacksonJsonRepository implements RentalRepository {
    private final List<Rental> rentals = new ArrayList<>();
    ...

    @Override
    public void save(Rental rental) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var writer = new FileWriter("rental.save", true)) {
            String jsonString = mapper.writeValueAsString(rental) + "\n";

            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var reader = new BufferedReader(new FileReader("rental.save"))) {
            while (reader.ready()) {
                String jsonString = reader.readLine();
                Rental rental = mapper.readValue(jsonString, Rental.class);
                rentals.add(rental);
            }

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```
*Auszug aus RentalJacksonJsonRepository.java*
</details>


---
### **5. YAGNI - You ain't gonna need it**
Das YAGNI Prinzip beschreibt die Abwesenheit von zurzeit nicht verwendeten Methoden, welche sonst durch Gedanken wie "So etwas können wir bestimmt einmal gebrauchen!" entstehen. Es ist ein wenig wie der Unterschied zwischen dem Einkaufen mit und ohne Einkaufsliste, daher ist es auch genau so schwer zu demonstrieren _was genau_ nun weggelassen wurde.

In dieser Software wird das YAGNI Prinzip unter anderem im [Rental Repository](/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/RentalRepository.java) angewandt.
```java
public interface RentalRepository {
    List<Rental> listAll();
    Rental findById(RentalId rentalId);
    List<Rental> findByTenantId(TenantId tenantId);
    List<Rental> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId);
    List<Rental> findByApartmentComplexId(ApartmentComplexId apartmentComplexId);
    List<RentalApartmentUnit> listAllRentalApartmentUnits();
    List<RentalProperty> listAllRentalProperties();
    void add(Rental rental);
    void save(Rental rental);
    void load();
}
```
Wo _genau_ fehlt hier etwas "fehlt" ist natürlich schwer zu sagen. Eine Möglichkeit wäre, ähnlich zum Überladen von Konstruktoren, mehrere ```save(...)``` Methoden zu implementieren. So könnte man z.B. eine "Export" Funktion mit der vorhanden Methode ```save(Rental rental)``` implementieren, jedoch das Speichern des Programmzustandes mit ```Save(List<Rental> rental)```. Das _könnte_ den Vorteil haben, dass man beispielsweise nur einen offenen File Handle hat beim speichern. Da es sich hierbei jedoch um eine kleine Prototyp-Anwendung für Kleinvermieter in Deutschland handelt, ist eine etwaige Beschleunigung durch das aufteilen auf zwei solcher Methoden äußerst unausgewogen gegenüber der zusätzlich eingeführten Komplexität.

Im Anbetracht des Prototypen-Stadiums der Software, wäre eine der Implementierungen wahrscheinlich ohnehin nur mit ```throw new UnsupportedOperationException("Not implemented yet");``` verendet.

</dd>
</ol>


<div class="page"/>


# 5. Refactoring

## Identifizierte Code Smells
[//]: # (mindestens 4 Code Smells; Auszug SonarQube o.ä.; Link zu Commit mit letzten smell falls refactored; https://refactoring.guru/refactoring/smells)

### **Long Parameter List**
TODO


---
### **Primitive Obsession**
Todo

---
### **Alternative Classes with Different Interfaces**
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


---
### **Shotgun Surgery**
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

Daraus folgt, dass bei einer Änderung an einem der Entities, mindestens auch das Gegenstück geändert werden muss. Betrachtet man die weiteren Auswirkungen durch diese nachteilige Anbindung, fällt auf, dass vermutlich noch weitere Stellen in anderen Ebenen als der Domäne betroffen sind.

Eine mögliche Korrektur ist möglich, indem man delegierende Methoden einführt, welche die Aufrufe an ApartmentComplex weiterleiten. Diese sollte bei der nächsten Änderung anstelle der Shotgun Surgery durchgeführt werden.



## Durchgeführte Refactorings
(2 konkrete Refactorings; Commit des Refactorings verlinken; Begründen; https://refactoring.guru/refactoring/techniques)

### **Todo**


---
### **Todo**


<div class="page"/>


# 6. Entwurfsmuster
(Nicht erlaubt: alle DDD-Muster, Singleton)

## Gewählte(s) Entwurfsmuster
- Warum setzen Sie dieses Muster an dieser Stelle ein
- Wie verbessert das Muster den Code
- Welche Vorteile/Nachteile gib es durch den Einsatz dieses Musters
- Welche Vorteile/Nachteile gäbe es ohne dieses Muster
- ...
- (Codestelle verlinken)