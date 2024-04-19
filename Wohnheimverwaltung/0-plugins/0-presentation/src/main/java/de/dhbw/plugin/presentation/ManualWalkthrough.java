package de.dhbw.plugin.presentation;

import de.dhbw.application.services.ApartmentComplexManagementService;
import de.dhbw.application.services.RentalManagementService;
import de.dhbw.application.services.TenantManagementService;
import de.dhbw.application.snapshotObjects.ApartmentComplexSnapshotDTO;
import de.dhbw.application.snapshotObjects.TenantSnapshotDTO;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.repositories.TenantRepository;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import de.dhbw.domain.valueObjects.Email;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.SizeUnit;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;
import de.dhbw.plugin.persistence.ApartmentComplexJacksonJsonRepository;
import de.dhbw.plugin.persistence.RentalJacksonJsonRepository;
import de.dhbw.plugin.persistence.TenantJacksonJsonRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManualWalkthrough {
    public static void main(String[] args) {
        System.out.println("Cleaning up existing files");

        new java.io.File("complexOrphaned.save").delete();
        new java.io.File("rental.save").delete();
        new java.io.File("tenantOrphaned.save").delete();

        System.out.println("Beginning Manual Walkthrough");
        System.out.println("First we create the repositories and inject them into the application layer services");

        // Create repositories
        RentalRepository rentalRepository = new RentalJacksonJsonRepository();
        TenantRepository tenantRepository = new TenantJacksonJsonRepository();
        ApartmentComplexRepository apartmentComplexRepository = new ApartmentComplexJacksonJsonRepository();

        // Inject Repositories into Application Layer
        RentalManagementService rentalManagementService = new RentalManagementService(rentalRepository, tenantRepository, apartmentComplexRepository);
        TenantManagementService tenantManagementService = new TenantManagementService(tenantRepository);
        ApartmentComplexManagementService apartmentComplexManagementService = new ApartmentComplexManagementService(apartmentComplexRepository);

        System.out.println("Now we create 5 Tenants");

        List<TenantId> tenantIds = new ArrayList<>();

        tenantIds.add(tenantManagementService.createTenant(
                "Max",
                "Mustermann",
                new ContactAvenueEmail(new Email("max.mustermann1704@gmail.com"))
        ));

        tenantIds.add(tenantManagementService.createTenant(
                "Maja",
                "Musterfrau",
                new ContactAvenueEmail(new Email("Maja.Mueller-Musterfrau@web.de"))
        ));

        tenantIds.add(tenantManagementService.createTenant(
                "Hans",
                "Peter",
                new ContactAvenueEmail(new Email("P_Hans@hotmail.com"))
        ));

        tenantIds.add(tenantManagementService.createTenant(
                "Peter",
                "Hans",
                new ContactAvenueEmail(new Email("AvengersFan420@gmx.de"))
        ));

        tenantIds.add(tenantManagementService.createTenant(
                "Bill",
                "Gates",
                new ContactAvenueEmail(new Email("bill.gates@microsoft.com"))
        ));

        System.out.println("Our Tenants are:");

        for (TenantId tenantId : tenantIds) {
            TenantSnapshotDTO tenantSnapshotDTO = tenantManagementService.getTenantById(tenantId);
            System.out.printf("%s, %s\n",
                    tenantSnapshotDTO.getName().getFullName(),
                    tenantSnapshotDTO.getPreferredContactAvenue()
            );
        }

        System.out.println("Lets create two apartment complexes");

        ApartmentComplexId apartmentComplexId = apartmentComplexManagementService.createApartmentComplex(
                "Musterstraße",
                "1",
                "12345",
                "Deutschland",
                LocalDate.of(2021, 1, 1)
        );

        ApartmentComplexId placeholderApartmentComplexId = apartmentComplexManagementService.createApartmentComplex(
                "Musterstraße",
                "2",
                "12345",
                "Deutschland",
                LocalDate.of(2021, 1, 1)
        );

        System.out.println("Our Apartment Complexes are:");
        System.out.printf("%s, %s, %s, %s, %s\n",
                apartmentComplexManagementService.getApartmentComplexById(apartmentComplexId).getAddress().getStreetName(),
                apartmentComplexManagementService.getApartmentComplexById(apartmentComplexId).getAddress().getHouseNumber(),
                apartmentComplexManagementService.getApartmentComplexById(apartmentComplexId).getAddress().getPostalCode(),
                apartmentComplexManagementService.getApartmentComplexById(apartmentComplexId).getAddress().getCity(),
                apartmentComplexManagementService.getApartmentComplexById(apartmentComplexId).getDateOfConstruction()
        );
        System.out.printf("%s, %s, %s, %s, %s\n",
                apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId).getAddress().getStreetName(),
                apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId).getAddress().getHouseNumber(),
                apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId).getAddress().getPostalCode(),
                apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId).getAddress().getCity(),
                apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId).getDateOfConstruction()
        );

        System.out.println("And lets add 2 apartments to first");

        List<RentalId> rentalApartmentIds = new ArrayList<>();

        rentalApartmentIds.add(rentalManagementService.createRentalApartmentUnit(
                apartmentComplexId,
                1,
                1,
                new BigDecimal(100),
                1
        ));

        rentalApartmentIds.add(rentalManagementService.createRentalApartmentUnit(
                apartmentComplexId,
                2,
                1,
                new BigDecimal(200),
                3
        ));

        System.out.println("Our Apartments are:");

        for (RentalId rentalId : rentalApartmentIds) {
            System.out.printf("Apartment %s has a size of %s square meters and can house %s tenants\n",
                    rentalManagementService.getRentalApartmentUnitSnapshotById(rentalId).getDoorNumber().getApartmentNumber(),
                    rentalManagementService.getRentalApartmentUnitSnapshotById(rentalId).getSize().getValueInPreferredUnit(SizeUnit.SQUARE_METERS),
                    rentalManagementService.getRentalApartmentUnitSnapshotById(rentalId).getMaxTenants()
            );
        }

        System.out.println("Now lets create a rental property");

        RentalId rentalPropertyId = rentalManagementService.createRentalProperty(
                "Musterstraße",
                "2",
                "12345",
                "Stadthausen",
                LocalDate.of(2021, 1, 1),
                new BigDecimal(500),
                1
        );

        System.out.println("Our Rental Property is:");
        System.out.printf("%s, %s, %s, %s, %s\n",
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getAddress().getStreetName(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getAddress().getHouseNumber(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getAddress().getPostalCode(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getAddress().getCity(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getDateOfConstruction()
        );

        System.out.println("Now lets try to overbook the rental property with tenants");

        try {
            rentalManagementService.rentRentalToTenants(
                    rentalPropertyId,
                    tenantIds,
                    LocalDate.of(2021, 1, 1),
                    new Rent(1000),
                    1,
                    3
            );
        } catch (IllegalArgumentException e) {
            System.out.println("We caught an IllegalArgumentException, as expected");
        }

        System.out.println("Now lets properly rent the rental property to a tenant");

        rentalManagementService.rentRentalToTenants(
                rentalPropertyId,
                List.of(tenantIds.get(0)),
                LocalDate.of(2024, 1, 1),
                new Rent(1000),
                15,
                1
        );

        System.out.println("Our Rental Property is now rented to:");
        System.out.printf("%s, %s since %s\n",
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getLeaseAgreement().getTenants().get(0).getName().getFullName(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getLeaseAgreement().getTenants().get(0).getPreferredContactAvenue(),
                rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).getLeaseAgreement().getInclusiveStartDate()
        );

        System.out.println("Now lets try to double book the rental property");

        try {
            rentalManagementService.rentRentalToTenants(
                    rentalPropertyId,
                    List.of(tenantIds.get(1)),
                    LocalDate.of(2024, 1, 1),
                    new Rent(1000),
                    1,
                    3
            );
        } catch (IllegalArgumentException e) {
            System.out.println("We caught an IllegalArgumentException, as expected");
        }

        System.out.println("Lets instead fill the remaining apartment with tenants");

        rentalManagementService.rentRentalToTenants(
                rentalApartmentIds.get(1),
                List.of(tenantIds.get(0), tenantIds.get(1), tenantIds.get(2)),
                LocalDate.of(2024, 1, 1),
                new Rent(200),
                1,
                3
        );

        rentalManagementService.rentRentalToTenants(
                rentalApartmentIds.get(0),
                List.of(tenantIds.get(3)),
                LocalDate.of(2024, 1, 1),
                new Rent(100),
                1,
                3
        );

        System.out.println("Our first Rental Apartment is now rented to:");

        for (TenantSnapshotDTO tenant : rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(0)).getLeaseAgreement().getTenants()) {
            System.out.printf("%s, %s since %s\n",
                    tenant.getName().getFullName(),
                    tenant.getPreferredContactAvenue(),
                    rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(0)).getLeaseAgreement().getInclusiveStartDate()
            );
        }

        System.out.println("Our second Rental Apartment is now rented to:");

        for (TenantSnapshotDTO tenant : rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(1)).getLeaseAgreement().getTenants()) {
            System.out.printf("%s, %s since %s\n",
                    tenant.getName().getFullName(),
                    tenant.getPreferredContactAvenue(),
                    rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(1)).getLeaseAgreement().getInclusiveStartDate()
            );
        }

        System.out.println("Lets see Max's balance");

        System.out.printf("Max's balance is %s\n",
                tenantManagementService.getTenantById(tenantIds.get(0)).getBalance()
        );

        System.out.println("After checking our bank account, we see he has paid 4000€. Lets credit his account");

        tenantManagementService.creditTenant(tenantIds.get(0), 4000, LocalDate.now());

        System.out.printf("Max's balance is now %s\n",
                tenantManagementService.getTenantById(tenantIds.get(0)).getBalance()
        );

        System.out.println("Now lets see his transaction history");

        for (var transaction : tenantManagementService.getTenantById(tenantIds.get(0)).getOutstandingBalanceHistory()) {
            System.out.printf("Transaction of %s€ on %s\n",
                    transaction.getAmount(),
                    transaction.getDate()
            );
        }

        System.out.println("Oh no, looks like we forgot Max ended his property lease on the 1st of February 2024 with 1 month notice");

        rentalManagementService.endLeaseAgreement(rentalPropertyId, LocalDate.of(2024, 2, 1), LocalDate.of(2024, 3, 1));

        System.out.println("Lets check his balance again, his wrongfully paid rent should be refunded");

        System.out.printf("Max's balance is now %s\n",
                tenantManagementService.getTenantById(tenantIds.get(0)).getBalance()
        );

        System.out.println("And his transaction history");

        for (var transaction : tenantManagementService.getTenantById(tenantIds.get(0)).getOutstandingBalanceHistory()) {
            System.out.printf("Transaction of %s€ on %s\n",
                    transaction.getAmount(),
                    transaction.getDate()
            );
        }

        System.out.println("Lets serialise the empty apartment complex to a file");

        // You should be using these methods, but that would mess up your existing save files
        // apartmentComplexManagementService.saveAllOrphanApartmentComplexes();
        // rentalManagementService.saveAllRentals();
        // tenantManagementService.saveAllOrphanTenants();

        apartmentComplexManagementService.exportApartmentComplexById(placeholderApartmentComplexId);

        System.out.println("Lets load the apartment complexes from the file");

        ApartmentComplexSnapshotDTO newApartmentComplex = apartmentComplexManagementService.importApartmentComplexes().getFirst();

        System.out.println("Lets compare the two apartment complexes");

        if (newApartmentComplex.equals(apartmentComplexManagementService.getApartmentComplexById(placeholderApartmentComplexId))) {
            System.out.println("The two apartment complexes are the same");
        } else {
            System.out.println("The two apartment complexes are different");
        }

        System.out.println("Next lets do the same to just the individual rental properties and property");

        for (RentalId rentalId : rentalApartmentIds) {
            rentalManagementService.exportRentalById(rentalId);
        }

        rentalManagementService.exportRentalById(rentalPropertyId);

        System.out.println("And load them back in");

        List<RentalId> newRentalIds = rentalManagementService.importRentals();

        System.out.println("Lets compare the rental apartments");

        if (rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(0)).equals(rentalManagementService.getRentalApartmentUnitSnapshotById(newRentalIds.get(0)))
                && rentalManagementService.getRentalApartmentUnitSnapshotById(rentalApartmentIds.get(1)).equals(rentalManagementService.getRentalApartmentUnitSnapshotById(newRentalIds.get(1)))
                && rentalManagementService.getRentalPropertySnapshotById(rentalPropertyId).equals(rentalManagementService.getRentalPropertySnapshotById(newRentalIds.get(2))) ){
            System.out.println("The rental apartments and property are the same");
        } else {
            System.out.println("The rental apartments and property are different");
        }

        System.out.println("Lets do the same for the lone tenant without a rental");

        tenantManagementService.exportTenantById(tenantIds.get(4));

        System.out.println("And load them again");

        TenantId newTenant = tenantManagementService.importTenants().getFirst();

        System.out.println("And compare them");

        if (tenantManagementService.getTenantById(tenantIds.get(4)).equals(tenantManagementService.getTenantById(newTenant))) {
            System.out.println("The tenants are the same");
        } else {
            System.out.println("The tenants are different");
        }

        System.out.println("End of Manual Walkthrough");
        System.out.println("Launch the GUI from the same path to view the data you just created");
    }
}
