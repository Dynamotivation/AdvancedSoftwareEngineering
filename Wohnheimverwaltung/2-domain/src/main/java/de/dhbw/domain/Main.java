package de.dhbw.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.entities.Name;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import de.dhbw.domain.valueObjects.Rent;
import de.dhbw.domain.valueObjects.ids.TenantId;

import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String test = "Hello from Domain";
    public static void main(String[] args) {
        /*Rent rent1 = new Rent(1000);
        Rent rent2 = new Rent(2000);
        Rent rent3 = new Rent(1000);

        System.out.println(rent1);
        System.out.println(rent2);
        System.out.println(rent1.equals(rent2));
        System.out.println(rent1.equals(rent3));

        Tenant tenant = new Tenant("John", "Doe", new ContactAvenueEmail("test@me.lol"));
        Tenant tenant2 = new Tenant("John", "Doe", new ContactAvenueEmail("test@me"));
        //Renter renter3 = new Renter("John", "Doe", new ContactAvenueEmail("test@.me"));

        // Test duplicate
        try {
            tenant.getContactInformation().addContactAvenue(new ContactAvenueEmail("test@me.lol"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test removing a non-existing contact avenue
        try {
            tenant.getContactInformation().removeContactAvenue(new ContactAvenueEmail("test@me.lol"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test removing the last remaining contact avenue
        try {
            tenant.getContactInformation().removeContactAvenue(tenant.getContactInformation().getPreferredContactAvenue());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }*/

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