package de.dhbw.plugin.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;

import java.time.LocalDate;

public class Main {

    public static String test = "Hello from Domain";
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Serialize object to JSON
        RentalApartmentUnit apartmentUnit = new RentalApartmentUnit("Rheinstraße", "1", "68163", "Mannheim", LocalDate.of(2000, 10, 3), 1,1,1,1);

        String jsonString = null;

        try {
            jsonString = mapper.writeValueAsString(apartmentUnit);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(jsonString);

        RentalApartmentUnit apartmentUnit2 = null;

        try {
            apartmentUnit2 = mapper.readValue(jsonString, RentalApartmentUnit.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Comparing Complexes");
        System.out.println(apartmentUnit.getParentApartmentComplex().equals(apartmentUnit2.getParentApartmentComplex()));
        System.out.println("Comparing Tenants");
        System.out.println(apartmentUnit.getTenantIds().equals(apartmentUnit2.getTenantIds()));
        System.out.println("Comparing Units");
        System.out.println(apartmentUnit.equals(apartmentUnit2));
    }
}