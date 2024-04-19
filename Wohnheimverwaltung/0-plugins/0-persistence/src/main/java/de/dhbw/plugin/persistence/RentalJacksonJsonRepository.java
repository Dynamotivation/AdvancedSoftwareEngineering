package de.dhbw.plugin.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.RentalProperty;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.RentalRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;
import de.dhbw.domain.valueObjects.ids.RentalId;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentalJacksonJsonRepository implements RentalRepository {
    private final List<Rental> rentals = new ArrayList<>();

    @Override
    public List<Rental> listAll() {
        return new ArrayList<>(rentals);
    }

    @Override
    public Rental findById(RentalId rentalId) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(rentalId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Rental> findByTenantId(TenantId tenantId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rental> findByLeaseAgreementId(LeaseAgreementId leaseAgreementId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rental> findByApartmentComplexId(ApartmentComplexId apartmentComplexId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<RentalApartmentUnit> listAllRentalApartmentUnits() {
        List<RentalApartmentUnit> rentalApartmentUnits = new ArrayList<>();

        rentals.stream().filter(rental -> rental instanceof RentalApartmentUnit)
                .map(rental -> (RentalApartmentUnit) rental)
                .forEach(rentalApartmentUnits::add);

        return rentalApartmentUnits;
    }

    @Override
    public List<RentalProperty> listAllRentalProperties() {
        List<RentalProperty> rentalProperties = new ArrayList<>();

        rentals.stream().filter(rental -> rental instanceof RentalProperty)
                .map(rental -> (RentalProperty) rental)
                .forEach(rentalProperties::add);

        return rentalProperties;
    }

    @Override
    public void add(Rental rental) {
        rentals.add(rental);
    }

    @Override
    public void remove(Rental rental) {
        if (rental.getLeaseAgreement() != null)
            throw new IllegalArgumentException("Rental has an active lease agreement");

        rentals.remove(rental);
    }

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
    public List<Rental> load() {
        List<Rental> newRentals = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var reader = new BufferedReader(new FileReader("rental.save"))) {
            while (reader.ready()) {
                String jsonString = reader.readLine();
                Rental rental = mapper.readValue(jsonString, Rental.class);
                newRentals.add(rental);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        rentals.addAll(newRentals);
        return newRentals;
    }
}
