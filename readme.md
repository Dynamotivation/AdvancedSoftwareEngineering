<div align="center">
    <h1>Wohnheimverwaltung</h1>
    <img src="Documentation Ressources/ProjectLogo.png" width="100" height="100"></img>
    <p><strong>Ein Verwaltungswerkzeug für Vermieter von Wohnheimen</strong></p>
</div>



- [0. Inbetriebnahme](#0-inbetriebnahme)
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
  - [Gewähltes Entwurfsmuster](#gewähltes-entwurfsmuster)


<div class="page"/>

# 0. Inbetriebnahme
1. OpenJDK 22 oder vergleichbar und GIT sollten auf dem Zielgerät installiert sein
2. Die Umgebungsvariable "JAVA_HOME" sollte auf den Pfad der JDK Installation verweisen (z.B. "C:\Program Files\Java\jdk-22")\
```setx JAVA_HOME "PFAD ZUR JDK"``` - Windows\
```export JAVA_HOME=/usr/bin/java``` - Linux
3. Das Repository mit ```git clone https://github.com/Dynamotivation/AdvancedSoftwareEngineering.git``` in einen Ordner der Wahl clonen (Der Ordner benötigt Schreibrechte; Sonderzeichen und lange übergeordnete Ordnernamen können Probleme bereiten)
4. Ein(e) Eingabeaufforderung / Terminal im Ordner "Wohnheimverwaltung" öffnen
5. Je nach Eingabeaufforderung / Terminal einen der Befehle zur Initialisierung ausführen:\
```mvnw clean install``` - Windows CMD\
```./mvnw clean install``` - PowerShell, Linux, etc.\
Nun wurde die Tests bereits aufgeführt. Diese können beliebig wiederholt werden.\
```mvnw test``` - Windows CMD\
```./mvnw test``` - PowerShell, Linux, etc.
6. Das automatisierte Demoskript starten:\
```mvnw exec:java -f ./0-plugins/0-presentation/``` - Windows CMD\
```./mvnw exec:java -f ./0-plugins/0-presentation/``` - PowerShell, Linux, etc.\
Durch die Ausführung werden im aktuellen Ausführungsverzeichnis drei .save Dateien erzeugt.\
Der Befehlsoutput gibt Aufklärung über die implementierten Use Cases des Programms.
7. Das Nutzerinterface starten:\
```mvnw javafx:run -f ./0-plugins/0-presentation/```\
```./mvnw javafx:run -f ./0-plugins/0-presentation/```

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
| Mietforderung (Rent Charge)                 | Eine konkrete Forderung, welche periodische Zahlung in Höhe der Miete darstellt.                                                                                                                                                                | siehe "Forderung"                                                                      |                               |
| Einzahlung (Deposit)                        | Eine konkrete Form der Transaktion über einen bestimmten Geldbetrag, welcher zur Deckung akkumulierter Forderungen dient.                                                                                                                       | - Zahlungsbetrag größer als 0 sein                                                     |                                                                                   |
| Vermieter                                   | Eine zur Verwaltung von Mietwohnungen in einem Gebäude berechtigte Person. Oftmals der Anwender der Software oder dessen Arbeitgeber.                                                                                                          |

---

| Prozesse  |                                                                                                                          |
|-----------|--------------------------------------------------------------------------------------------------------------------------|
| Ausziehen | Der Prozess des Verlassens eines Mietobjektes durch einen Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen. |
| Mieten | Der Prozess des vermieten eines Mietobjektes an einen oder mehrere Mieter unter Einhaltung der im Mietvertrag bestimmten Fristen und Miethöhen.   |
| Mieter Anlegen | Der Prozess in dem ein Mieter der Mieterkartei hinzugefügt wird. |
| Mietobjekt Anlegen | Der Prozess in dem ein Mietobjekt angelegt wird. |
| Speichern | Der Prozess in dem alle Mietobjekte und Mieter gespeichert werden. |
| Laden | Der Prozess in dem die gespeicherten Daten wieder eingelesen werden und ein identischer Zustand zur Speicherzeitpunkt entsteht. |

[//]: # (Analysieren Sie die Fachlichkeit Ihrer Problemdomäne, indem Sie die relevanten Begriffe ✅ und deren fachliche Bedeutung ✅, Aufgaben ❔ und Regeln ✅ dokumentieren)


<div class="page"/>


## Verwendete taktischer Muster des DDD
[//]: # (Alle genannten Muster des DDD sind im Source Code zu verwenden Value Objects, Entities, Aggregates, Repositories, Domain Services; Begründen Sie jedes einzelne, oben genannte Muster anhand von je einem konkreten Beispiel aus Ihrem Source Code; „XX ist als ValueObject implementiert, weil…")

### [**Value Objects**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/)

#### [Size](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/Size.java)
Die Fläche eines Mietobjekt ist als Value Object implementiert, weil sie lediglich aus einer Zahl und einer Einheit besteht. Der Wert kann aus einer beliebigen Kombination der akzeptierten Werte erzeugt werden und umgerechnet werden. Ändert sich in einer künftigen Version durch z.B. renovieren eine Größenangabe, so ist das Resultat eine neuer neue Instanz der Klasse.

---


### [**Entities**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/)

#### [Lease Agreement](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/LeaseAgreement.java)
Der Mietvertrag ist als Entity implementiert, weil er neben finalen Feldern auch veränderbare Attribute hat, wie beispielsweise das End-Datum des Vertrags. Den Mietvertrag bei jeder Änderung ersetzen zu müssen wäre eine große Einschränkung, da somit auch Objektreferenzen auf ihn geändert werden müssen. Dafür besitzt jeder Vertrag eine Identität, um ihn auch nach Änderungen eindeutig ausmachen zu können.

---


### [**Aggregates**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/)

#### [Tenant Aggregate](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/Tenant.java)
Das Mieter Aggregate wurde so gewählt, dass ein Tenant als Aggregate Root auftritt, denn in ihm werden verschiedene Personalien (Value Objects (Kontaktdaten, Name und ID)) und eine Liste der Mietverträge (Entities) zusammengeführt. Außerdem führt es eine Liste der zugehörigen Transaktionen, also Forderungen und Einzahlungen im Zuge des mit ihm verbundenen Mietverhältnisses. Diese Verbindung stellt die Schnittstelle aus dem Rental Aggregate da.

---


### [**Repositories**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/)

#### [Tenant Repository](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/TenantRepository.java)
Das Tenant Repository existiert als zentrale Sammelstelle für alle Tenants. Diese Herangehensweise wurde gewählt, damit Tenant Instanzen nicht aufhört zu existieren, sobald sein letzter Mietvertrag gekündigt ist. Es wird weiterhin eine Objektreferenz in einer Art Mietkartei gehalten. Später bei der Speicherung der Liste empfängt das Repository einzeln die zu serialisierenden Mieter. Dadurch wird es Application Services ermöglicht beispielsweise nur alle Mieter zu serialisieren, welche nicht bereits durch Objektreferenzen von anderen Repositories serialisiert wurden. Ebenso wird es so ermöglicht Objektreferenzen endgültig fallen zu lassen oder wahlweise auch nur nicht zu speichern.

---


<div class="page"/>

### [**Domain Services**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/services/)

#### [Default Rent Charger](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/services/DefaultRentCharger.java)
Der Default Rent Charger ist ein Service, welcher das Rent Charger Interface implementiert. Es handelt sich hierbei nicht um einen technischen Service, sondern einen Domänen Service, da er eine Aufgabe innerhalb der Domäne übernimmt. Die Extraktion der Logik in dieses separaten Service erhöht die Separation of Concerns und verhindert beispielsweise den Code Smell "[Large Class](https://refactoring.guru/smells/large-class)".


<div class="page"/>


# 2. Clean Architecture

[//]: # (Welche Schichten umfasst ihre Anwendung und warum; Welche Aufgaben erfüllt welche Schicht; Welche Schichten sind umgesetzt)

## Geplante Schichtenarchitektur
Für einen anschaulichen Prototypen der Software sind die folgenden drei Schichten wichtig und bereits implementiert.

### [**Domain Layer**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/)
Implementiert: ✅\
In der Domain Layer befindet sich der logische Kern der Anwendung und dessen Regeln, eine Implementierung der Ubiquitous Language mit samt ihrer Regeln. Sie ist der Kern, denn ohne sie geht nichts. Nach ihrer einmaligen Fertigstellung treten Änderungen nur bei der Feststellung von Bugs oder Veränderungen an der Definition der Ubiquitous Language auf.


---
### [**Application Layer**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/1-application/)
Implementiert: ✅\
Die Application Layer enthält eine andere Art Services als die Domain Layer, denn sie behandeln nicht fachliche Aspekte, sondern technische. In der vorliegenden Implementation dienen die Services als Zugriffsweg auf die Repositories, damit die Domain Layer nur fachlich relevante Funktionen ihrer Repository Interfaces definieren muss. Der Service mappt weitere verbos-benannte Funktionen auf eine Reihe an Repository Funktionen für die Plugin Schicht. Außerdem können künftig durch die map-Befehle auch sehr einfach Adapter eingesetzt werden. Im aktuellen Stadium werden Domain Entities 1-zu-1 auf Domain Entity DTO (Data Transfer Objects) gespiegelt, wodurch all ihre Felder final und read only werden.


---
### [**Plugin Layer**](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/)
Implementiert: ✅\
Die Plugin Layer repräsentiert die kurzlebige äußere Schicht im Domain Driven Design. Darunter fallen beispielsweise Implementierungen der Repository Interfaces der Domain Layer auf eine bestimmte Speichermethode (versch. DB-Driver, Filesystem, Serialization) und verschiedene die Nutzerschnittstelle (Frontends, CLI).

#### [Presentation Layer](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/0-presentation/)
Implementiert: 〰️ (Einige Use Cases sind exklusiv programmatisch Aufrufbar)\
Die Presentation Layer Umfasst hier das JavaFX Frontend der Anwendung. Dies ist nötig, damit ein Nutzer das Programm verwenden kann.

#### [Persistence Layer](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/0-persistence/)
Implementiert: ✅\
Speicherung wird in der Persistence Layer gehandhabt. In der vorliegenden Anwendung wird dies durch Serialisierung in das JSON Format gehandhabt. Dafür wurde die Bibliothek Jackson verwendet.


<div class="page"/>


# 3. Programming Principles

## Fokussierte Programmierprinzipien

[//]: # (Begründen Sie für 5 der vorgestellten Prinzipien aus SOLID, GRASP, DRY, … WO das jeweilige Prinzip in Ihrem Projekt berücksichtigt wird bzw. angewendet wird und nennen Sie ein Beispiel AUS IHREM SOURCE CODE dazu; „Das Single-Responsibility-Principle besagt, … Dies wird zum Beispiel in der Klasse XX berücksichtigt, weil…“)

### **3.1 SOLID - SRP - Single Responsibility Principle**
Das Single Responsibility Principle besagt, dass eine Klasse nur eine Aufgabe und somit nur einen Grund zur Änderung hat. Dieses Prinzip wird in diesem Projekt an der Klasse [Lease Agreement](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/LeaseAgreement.java) demonstriert. Augenscheinlich hat die Klasse zwar ebenfalls Methoden, um den zugehörigen Mietern Miete zu berechnen, jedoch delegiert diese nur an eine Implementierung des Interface [Rent Charger](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/miscellaneous/RentCharger.java), um die Kapselung der Felder zu wahren. Durch diese Separation of Concerns werden die meisten Änderungen nur den Code einer der Klassen betreffen.

<details>
    <summary>Code Beispiel</summary>

```java
package de.dhbw.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.miscellaneous.NthDayOfMonthAdjuster;
import de.dhbw.domain.miscellaneous.RentCharger;
import de.dhbw.domain.services.DefaultRentCharger;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class LeaseAgreement {
    private final LocalDate inclusiveStartDate;
    private final NthDayOfMonthAdjuster monthlyDayOfPayment;
    private final int monthsOfNotice;
    private final Rent rent;
    private final List<Tenant> tenants; // Subscribers
    private final LeaseAgreementId id;
    private final RentalId associatedRentalId;
    private final RentCharger rentCharger;
    private LocalDate inclusiveEndDate;
    private LocalDate nextPaymentDate;

    public LeaseAgreement(List<Tenant> tenants, LocalDate inclusiveStartDate,
                          Rent rent, int monthlyDayOfPayment, int monthsOfNotice, RentalId associatedRentalId) {
        this(
                inclusiveStartDate,
                null,
                new NthDayOfMonthAdjuster(monthlyDayOfPayment),
                monthsOfNotice,
                inclusiveStartDate,
                rent,
                tenants,
                new LeaseAgreementId(),
                associatedRentalId,
                new DefaultRentCharger()
        );

        // Notifies the tenants of the new lease agreement
        tenants.forEach(tenant -> tenant.registerLeaseAgreementSubscription(this.id));

        // Notify subscribers of events since saved state
        update();
    }

    @JsonCreator
    private LeaseAgreement(
            @JsonProperty("inclusiveStartDate") LocalDate inclusiveStartDate,
            @JsonProperty("inclusiveEndDate") LocalDate inclusiveEndDate,
            @JsonProperty("monthlyDayOfPayment") NthDayOfMonthAdjuster monthlyDayOfPayment,
            @JsonProperty("monthsOfNotice") int monthsOfNotice,
            @JsonProperty("nextPaymentDate") LocalDate nextPaymentDate,
            @JsonProperty("rent") Rent rent,
            @JsonProperty("tenants") List<Tenant> tenants,
            @JsonProperty("id") LeaseAgreementId id,
            @JsonProperty("associatedRentalId") RentalId associatedRentalId,
            @JsonProperty("rentCharger") RentCharger rentCharger
    ) {
        this.inclusiveStartDate = inclusiveStartDate;
        this.monthlyDayOfPayment = monthlyDayOfPayment;
        this.monthsOfNotice = monthsOfNotice;
        this.nextPaymentDate = nextPaymentDate;
        this.inclusiveEndDate = inclusiveEndDate;
        this.tenants = new ArrayList<>(tenants); // Register subscribers defensively
        this.id = id;
        this.rent = rent;
        this.associatedRentalId = associatedRentalId;
        this.rentCharger = rentCharger;
    }

    public LocalDate getInclusiveStartDate() {
        return inclusiveStartDate;
    }

    public LocalDate getInclusiveEndDate() {
        return inclusiveEndDate;
    }

    public void setInclusiveEndDate(LocalDate submissionDate, LocalDate inclusiveEndDate) {
        if (submissionDate.isBefore(inclusiveStartDate))
            throw new IllegalArgumentException("End date must be after start date");

        if (submissionDate.plusMonths(monthsOfNotice).isAfter(inclusiveEndDate))
            throw new IllegalArgumentException("First possible end date is " + submissionDate.plusMonths(monthsOfNotice));

        this.inclusiveEndDate = inclusiveEndDate;
    }

    public List<Tenant> getTenants() {
        return new ArrayList<>(tenants);
    }

    public NthDayOfMonthAdjuster getMonthlyDayOfPayment() {
        return monthlyDayOfPayment;
    }

    public int getMonthsOfNotice() {
        return monthsOfNotice;
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

    // Notify subscribers of new due payment
    public void chargeRent() {
        nextPaymentDate = rentCharger.chargeRent(this);
    }

    public void update() {
        chargeRent();

        if (inclusiveEndDate != null && inclusiveEndDate.isBefore(LocalDate.now())) {
            tenants.forEach(tenant -> tenant.deregisterLeaseAgreementSubscription(id, inclusiveEndDate));
        }
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

<div class="page"/>

### **3.2 SOLID - DIP - Dependency Inversion Principle**
Das Dependency Inversion Principle besagt, dass Module höherer Ebenen anstelle von detailbehafteten Objekten aus niedrigeren Schichten von generalisierenden Interfaces abhängig sein sollen. Diese werden Abstraktionen genannt. Die Details selbst hängen von der Abstraktion ab.

Die Abhängigkeit zu Abstraktionen lässt sich durch die Extraktion von Merkmalen in ein Interface oder eine abstrakte Klasse durchführen. Dadurch entsteht ein Vertrag / Protokoll. Innerhalb einer Ebene und in allen Ebenen darüber kann man sich nun auf den Vertrag verlassen, die Details der Implementierung spielen keine Rolle.

Diese Prinzip wird in diesem Projekt von beispielsweise dem [Jackson Tenant Repository](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/TenantJacksonJsonRepository.java) verwirklicht. Statt alle für die Use Cases benötigten Verwaltungsfunktionen in Services und Repositories der Domain Layer zu implementieren, wird hier im Falle der Repositories bloß die [Abstraktion](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/TenantRepository.java) mit den "Protokolldetails" festgehalten. Die wahren Details, wie genau das Zwischenspeichern, Abspeichern und Laden abläuft, passieren in der erwähnten Jackson spezifischen Implementierung.

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

<div class="page"/>

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
    void remove(Tenant tenant);
    void save(Tenant tenant);
    List<Tenant> load();
}
```

Somit werden durch die Separation of Concerns die Regeln des Domain Driven Design zur Richtung von Abhängigkeiten gewahrt und Flexibilität geschaffen.

---

<div class="page"/>

### **3.3 GRASP - Pure Fabrication**
Bei Pure Fabrication handelt es sich um eine eine Trennung von technischem und Domänenwissen. Das bedeutet, dass die Klasse keinen Bezug zu der Problemdomäne hat, sondern der technischen Umsetzung einen Dienst anbietet.

Auch hierbei können wieder die [Jackson Repository Implementierungen](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/) betrachtet werden. Wie bereits bei dem [Dependency Inversion Principle](#32-solid---dip---dependency-inversion-principle) beschrieben, sind die Details von der Abstraktion getrennt. Die Details stellen hierbei das technische Wissen da.

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

<div class="page"/>

### **3.4 KISS - Keep it Simple, Stupid**
Wie der Name verrät zielt das KISS Prinzip darauf ab, Dinge auf eine einfache Weiße zu implementieren, um unnötige Komplexität zu vermeiden.

Beispielweise beim Persistieren im [Jackson Rental Repository](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/0-plugins/0-persistence/src/main/java/de/dhbw/plugin/persistence/RentalJacksonJsonRepository.java) wird jede Entität einzeln als Zeile in eine Datei geschrieben. Selbstverständlich entsteht dadurch auch kein valide JSON Datei, da nur jede Zeile in sich selbst valide ist. Dabei wird allerdings das Problem vermieden, dass es nicht zu [Type Erasure](https://docs.oracle.com/javase/tutorial/java/generics/genTypes.html) wie beim Serialisieren einer ```List<Rental>``` kommt, was die Deserialisierung erheblich erleichtert. Als Konsequenz wird die gespeicherte Datei dann nicht ```.json``` sondern ```.save``` genannt, somit *erwartet* auch niemand korrekten JSON Syntax.\
Die ```load()``` Methode deserialisiert dann ebenfalls Zeile für Zeile.

KISS trifft hier eben zu, da diese vermeidlich "dumme" Lösung sehr einfach zu implementieren ist und weder ein eigener, komplexer Serialisierer oder Deserialisierer geschrieben werden muss : )

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
```
*Auszug aus RentalJacksonJsonRepository.java*
</details>

---

<div class="page"/>

### **3.5 YAGNI - You ain't gonna need it**
Das YAGNI Prinzip beschreibt die Abwesenheit von zurzeit nicht verwendeten Methoden, welche sonst durch Gedanken wie "So etwas können wir bestimmt einmal gebrauchen!" entstehen. Es ist ein wenig wie der Unterschied zwischen dem Einkaufen mit und ohne Einkaufsliste, daher ist es auch genau so schwer zu demonstrieren _was genau_ nun weggelassen wurde.

In dieser Software wird das YAGNI Prinzip unter anderem im [Rental Repository](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/repositories/RentalRepository.java) angewandt.
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
    void remove(Rental rental);
    void save(Rental rental);
    List<Rental> load();
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

### **[5.1.1 Long Parameter List](https://refactoring.guru/smells/long-parameter-list)**
Die Klasse [Rental Property](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/RentalProperty.java) weist den Code Smell "Long Parameter List" zum einen in ihrem öffentlichen, als auch in ihrem privaten Konstruktor für die Deserialisierung auf.

Während der private Konstruktor unbedingt seine jetzige Form behalten muss, könnte der öffentliche optimiert werden. ```streetName, houseNumber, postalCode, city``` könnten stattdessen direkt als [Address](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/Address.java) Value Object übergeben werden.

```java
    ...
    public RentalProperty(String streetName, String houseNumber,
        String postalCode, String city, LocalDate dateOfConstruction,
        Size size, int maxTenants
    ) {
        this(new Address(streetName, houseNumber, postalCode, city),
                dateOfConstruction, new RentalId(), null, maxTenants, size);
    }

    @JsonCreator
    private RentalProperty(
            @JsonProperty("address") @NonNull Address address,
            @JsonProperty("dateOfConstruction") @NonNull LocalDate dateOfConstruction,
            @JsonProperty("id") @NonNull RentalId id,
            @JsonProperty("leaseAgreement") LeaseAgreement leaseAgreement,
            @JsonProperty("maxTenants") int maxTenants,
            @JsonProperty("size") @NonNull Size size
    ) {
        ...
```
*Auszug aus RentalProperty.java*

---

<div class="page"/>

### **[5.1.2 Primitive Obsession](https://refactoring.guru/smells/primitive-obsession)**
Der Commit *"Implemented Rent Charging"* (b1dfa8c) weißt in seiner Version von [Rental Apartment Unit](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/blob/b1dfa8c78c4202e90ed803ffa89d3006797d31d8/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/RentalApartmentUnit.java) (Link zur Version) Primitive Obsession auf. 

```java
public class RentalApartmentUnit implements Rental {
    ...
    private int apartmentNumber; // TODO make into a value object or annotate verifications
    private final int floor; // TODO make into a value object or annotate verifications
    private final ApartmentComplex parentApartmentComplex;

    // Required variables
    private final RentalId id;
    private LeaseAgreement leaseAgreement;
    private int maxTenants; // TODO move into RentalInformation
    private double size; // TODO move into RentalInformation
    ...
```
*Auszug aus RentalApartmentUnit.java (Commit "Implemented Rent Charging")*

```apartmentNumber```, ```floor``` und ```size``` sind lediglich integers. Die ersten beiden könnten gut zu einem gemeinsamen Value Object kombiniert werden, während letzteres zusammen mit einer Einheit zu einem eigenen Value Object werden sollte. Somit kann die Validierung ausgelagert werden und künftig deren ```.equals()``` Methoden verwendet werden.

---

<div class="page"/>

### **[5.1.3 Alternative Classes with Different Interfaces](https://refactoring.guru/smells/alternative-classes-with-different-interfaces)**
Das ContactAvenue (Kontaktmöglichkeit) Interface war in seinem Zustand bis zum Commit *"Finished Switch to Notification Based Rent Charging"* (df05014) nicht sehr nützlich, da es leer war.

```java
public interface ContactAvenue {
}
```
*Auszug aus ContactAvenue.java (Commit "Finished Switch to Notification Based Rent Charging")*

Somit diente es nur dem "gruppieren" verschiedener Kontaktmöglichkeiten. Schaut man nun auf die implementieren Klassen, wird die Problematik schnell erkenntlich: Die Klassen verhalten sich identisch, jedoch haben sie aus semantischen Gründen verschiedene Namen.

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

Mit einem Refactoring könnte diese Funktionalität in eine Abstrakte Klasse gezogen werden und einen generischen Namen wie ```getContactDetails()``` bekommen. Da die Validierung, welche durch die verschiedenen Datentypen erzielt wurde, ist bereits geschehen, kann hier der Rückgabetyp auf String geändert werden.

---

<div class="page"/>

### **[5.1.4 Shotgun Surgery](https://refactoring.guru/smells/shotgun-surgery)**
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

<div class="page"/>

## Durchgeführte Refactorings
[//]: # (2 konkrete Refactorings; Commit des Refactorings verlinken; Begründen; https://refactoring.guru/refactoring/techniques)

### **[5.2.1 Introduce Parameter Object](https://refactoring.guru/introduce-parameter-object)**

Um den Identifizierten Code Smell ["Primitive Obsession"](#512-primitive-obsession) in [Rental Apartment Unit](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/blob/b1dfa8c78c4202e90ed803ffa89d3006797d31d8/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/RentalApartmentUnit.java) (Link zur Version) zu beheben, wurden in zwei verschiedenen Commits jeweils ein neues Parameter Object in Form eines neuen Value Objects angelegt.

Commit *"Implemented Rent Charging"* (b1dfa8c) verringert zwar nicht direkt die Anzahl der Parameter, sondern verhindert direkt das Hinzukommen des neuen Parameters ```sizeUnit``` indem er mit ```int size``` zum neuen Value Object [Size](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/Size.java) wird.

Commit *"Extracted DoorNumber from RentalApartmentUnit"* (35851bc) kombiniert die verbleibenden zwei Primitives ```int floor``` und ```int apartmentNumber``` in das neue Value Object [DoorNumber](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/DoorNumber.java). Hierdurch wird die endgültige Parameterzahl von möglichen 6 auf 4 reduziert. 

Refactored Version:
```java
public class RentalApartmentUnit implements Rental {
    ...
    private DoorNumber doorNumber;
    ...
    private Size size;

    public RentalApartmentUnit(ApartmentComplex parentApartmentComplex, DoorNumber doorNumber, Size size, int maxTenants) {
        ...
    }
    ...
```
*Auszug RentalApartmentUnit.java*

---

<div class="page"/>

### **[5.2.2 Rename Method](https://refactoring.guru/rename-method)**

Ein andere identifizierter Code Smell, [Alternative Classes with Different Interfaces](#513-alternative-classes-with-different-interfaces) in beispielsweise [Contact Avenue Email](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/blob/df05014208a2be898529369293247853ab0ff39e/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/valueObjects/ContactAvenueEmail.java) (Link zur Version), lässt sich durch das Refactoring Rename Method minimieren. Die ehemals verschiedenen Methodennamen, werden zu der neuen Methode ```public String getContactDetails()``` zusammengelegt, wodurch es möglich wird diese in das Interface zu extrahieren. Darüber hinaus, da die Methode sich für alle Implementierungen von Contact Avenues gleich verhält, lässt sich das Interface zu einer abstrakten Klasse wandeln.
Dieses Refactoring wurde in dem Commit *"Streamlined Contact Avenue Implementation"* (05f7f97)

Refactored Version:
```java
public abstract class ContactAvenue {
    private final String contactDetails;

    @JsonCreator
    public ContactAvenue(
            @JsonProperty("contactDetails") String contactDetails
    ) {
        this.contactDetails = contactDetails;
    }

    public String getContactDetails() {
        return contactDetails;
    }
    ...
```
*Auszug aus ContactAvenue.java*

```java
public class ContactAvenueEmail extends ContactAvenue {
    public ContactAvenueEmail(Email contactDetails) {
        super(contactDetails.getEmail());
    }
}
```
*Auszug aus ContactAvenueEmail.java*


<div class="page"/>


# 6. Entwurfsmuster
[//]: # (Nicht erlaubt: alle DDD-Muster, Singleton)

## Gewähltes Entwurfsmuster
[//]: # (- Warum setzen Sie dieses Muster an dieser Stelle ein)
[//]: # (- Wie verbessert das Muster den Code)
[//]: # (- Welche Vorteile/Nachteile gib es durch den Einsatz dieses Musters)
[//]: # (- Welche Vorteile/Nachteile gäbe es ohne dieses Muster)
[//]: # (- Codestelle verlinken)

### Observer Pattern
Beim Observer Pattern wird das Anfragen von aktuellen Werten eines Objekts durch eine Mitteilung aus anderer Richtung ersetzt. Der Empfänger wird zum Observer / Subscriber von Mitteilungen eines Observables.

#### Einsatzorte
Das Observer Pattern ist eins der gewählten Entwurfsmuster für diese Anwendung. Am stärksten Ausgeprägt ist es in der Interaktion der Klassen [Lease Agreement](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/entities/LeaseAgreement.java) und [Tenant](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/aggregateRoots/Tenant.java).

Hierbei handelt es sich bei Lease Agreement um das Observable und Tenant um den Observer, damit die Berechnung der Miete effizient umgesetzt werden kann. Da sich die Menge an Tenants in einem Lease Agreement nach Vertragsabschluss nicht mehr ändert, wird die Registrierung der Abonnierenden bereits im Konstruktor von Lease Agreement abgeschlossen. Da dieser nicht von den jeweiligen Abonnenten aufgerufen wurden, werden diese im Umkehrschluss ebenfalls über die Registrierung benachrichtigt.

```java
    private LeaseAgreement(...) {
        ...
        this.tenants = new ArrayList<>(tenants); // Register subscribers defensively
        ...
        // Notifies the tenants of the new lease agreement
        tenants.forEach(tenant -> tenant.registerLeaseAgreementSubscription(this.id));
    }
```
*Auszug aus LeaseAgreement.java*

In dem Gegenstück Tenant existiert die zugehörige Registrierungsmethode ```registerLeaseAgreementSubscription(LeaseAgreement leaseAgreement)```, als auch deren Update Methode ```getCharged(LeaseAgreement leaseAgreement, RentCharge rentCharge)```.

```java
public class Tenant {
    ...
    public void registerLeaseAgreementSubscription(LeaseAgreementId leaseAgreementId) {
        // Validate that the lease agreement id is not already in the list
        if (associatedLeaseAgreementIds.contains(leaseAgreementId))
            throw new IllegalArgumentException("Lease agreement id registered");

        associatedLeaseAgreementIds.add(leaseAgreementId);
    }
    ...
    public void getCharged(LeaseAgreement leaseAgreement, RentCharge rentCharge) {
        if (!associatedLeaseAgreementIds.contains(leaseAgreement))
            throw new IllegalArgumentException("Tenant does not rent the property");

        if (outstandingBalanceHistory.contains(rentCharge))
            throw new IllegalArgumentException("Double charge");

        outstandingBalanceHistory.add(rentCharge);
    }
    ...
}
```
*Auszug aus Tenant.java*

---

#### Feststellbare Verbesserungen der Code Qualität
Durch die Anwendung dieses Patterns ist es möglich den Grad an Kopplung zwischen dem Rental Aggregate und dem Tenant Aggregate zu senken. In dieser Konstellation war Lease Agreement teil des Rental Aggregates, gleichzeitig aber auch die Schnittstelle zum Tenant Aggregate für Referenzierungen.

**Vorher** musste der Tenant seine Mietzahlungen vom Lease Agreement abholen, um einen akkuraten Kontostand anzeigen zu können. Er benötigte also eine Objektreferenz zumindest auf das Aggregate Root Rental.

**Nach** der Implementierung des Entwurfsmusters wird dieser nun vom Lease Agreement benachrichtigt, wodurch es möglich ist, die Referenzen in eine Richtung mit weichen Referenzen (Speicherung der Lease Agreement ID für Validation) zu ersetzen. Da Tenant ein Aggregate Root ist, ist es kein Problem eine Objektreferenz auf ihn außerhalb zu halten.

---

#### Weitere Vorteile
Neben der Verbesserung der Code Qualität gab es noch Effizienzvorteile.

Im alten System musste jeder Mieter seine Mietzahlungen einsammeln, wodurch es zu doppelten Anfragen kam, da ein Mietvertrag aus mehreren Menschen bestehen kann. Um diese Problem teilweise zu revidieren wurde das System "lazy" implementiert. Ein Mietvertrag updatete auf Anfrage alle seine Mieter und speicherte den letzten Update Zeitpunkt. Somit wurde nur die nötige Arbeit ausgeführt und andere profitierten schon vor ihrer eigenen Anfrage davon, jedoch ging diese Herangehensweise auf kosten von erhöhter technischer Komplexität in der Domäne.

Das neue System ist in dieser Weise bei weitem Überlegen.

---

<div class="page"/>

#### Nachteile
Da sich der Anwendungsfall hier in der Domäne bewegt, ist es vielleicht nicht direkt ersichtlich, dass das Muster angewendet wird, denn weder Observer noch Observable implementieren ein entsprechend benanntes Interface. Da es sich um Abbildungen von Entitäten aus der echten Welt handelt sind die Methoden auf eine entsprechend treffende Art benannt, anstatt generisch ```register(...)``` oder ```update()```. Durch Code Kommentare kann dieses Hindernis jedoch reduziert werden.

---

#### Alternativen

Bei der Umsetzung der Mietberechnung gab es insgesamt drei verschiedene Iterationen:
1. Ausstehende Forderung von jedem Lease Agreement anfordern (lazy)
2. Version 1. erweitert um Propagation und Caching
3. Observer Pattern mit Abonnements

Während Option 1 am ehesten den KISS-Prinzip entsprochen hat, war sie nicht elegant implementierbar, da jeder Mieter das letzte Aktualisierungsdatum festhalten musste. Dadurch gab es deutliche Eingeständnisse in Anbetracht der Separation of Concerns.
Der Vorteil war wie einfach diese Option zu implementieren war. Anhand der Ubiquitous Language war dieses herangehen jedoch nicht nachvollziehbar.

Option 2 baut auf Option 1 auf und verlegt das Speichern von benötigten Metadaten in Lease Agreement. Da es sich hierbei jedoch nicht um das Aggregate Root des zugehörigen Aggregates handelt, ist das halten einer Objektreferenz auf Lease Agreement nicht einwandfrei vertretbar. Die Referenzkette jedes mal vom zugehörige Rental an abzulaufen würde noch mehr Klassen in diese Interaktion verstricken und weitere unnötige Komplexität einführen.

Option 3, das Observer Pattern kombiniert mit der Auslagerung der Forderungslogik in [Rent Charger](https://github.com/Dynamotivation/AdvancedSoftwareEngineering/tree/main/Wohnheimverwaltung/2-domain/src/main/java/de/dhbw/domain/miscellaneous/RentCharger.java) nach dem SRP-Prinzip, löste die Notwendigkeit der bidirektionalen Objektreferenz auf. Caching Logik wird auf das minimale Speichern eines Time Stamps reduziert und dennoch werden redundante Prüfungen verhindert. Es werden also alle Verbesserungen aus Option 2 mitgenommen während die Komplexität auf ein vergleichbares Niveau als Option 1 zurückkehrt.

Somit ersetzt Option 3 die Option 2 vollständig. Option 1 ist nur in der Prototyping Phase vertretbar. Option 3 geht somit insgesamt als Gewinner hervor.