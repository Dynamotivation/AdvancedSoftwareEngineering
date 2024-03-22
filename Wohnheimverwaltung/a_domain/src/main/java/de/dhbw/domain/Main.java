package de.dhbw.domain;

import de.dhbw.domain.entities.Renter;
import de.dhbw.domain.valueObjects.ContactAvenueEmail;
import de.dhbw.domain.valueObjects.Rent;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String test = "Hello from Domain";
    public static void main(String[] args) {
        Rent rent1 = new Rent(1000);
        Rent rent2 = new Rent(2000);
        Rent rent3 = new Rent(1000);

        System.out.println(rent1);
        System.out.println(rent2);
        System.out.println(rent1.equals(rent2));
        System.out.println(rent1.equals(rent3));

        Renter renter = new Renter("John", "Doe", new ContactAvenueEmail("test@me.lol"));
        Renter renter2 = new Renter("John", "Doe", new ContactAvenueEmail("test@me"));
        //Renter renter3 = new Renter("John", "Doe", new ContactAvenueEmail("test@.me"));

        // Test duplicate
        try {
            renter.getContactInformation().addContactAvenue(new ContactAvenueEmail("test@me.lol"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test removing a non-existing contact avenue
        try {
            renter.getContactInformation().removeContactAvenue(new ContactAvenueEmail("test@me.lol"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test removing the last remaining contact avenue
        try {
            renter.getContactInformation().removeContactAvenue(renter.getContactInformation().getPreferedContactAvenue());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}