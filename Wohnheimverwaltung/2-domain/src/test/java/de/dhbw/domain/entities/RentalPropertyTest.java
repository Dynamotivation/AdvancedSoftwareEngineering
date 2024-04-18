package de.dhbw.domain.entities;

import static org.junit.Assert.*;

import de.dhbw.domain.aggregateRoots.RentalApartmentUnit;
import org.junit.Test;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class RentalPropertyTest {

    @Test
    public void testValidRentalApartmentUnitCreation() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size size = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int maxTenants = 4;

        RentalApartmentUnit unit = new RentalApartmentUnit(apartmentComplex, doorNumber, size, maxTenants);

        assertEquals(doorNumber, unit.getDoorNumber());
        assertEquals(apartmentComplex, unit.getParentApartmentComplex());
        assertNotNull(unit.getId());
        assertNull(unit.getLeaseAgreement());
        assertEquals(maxTenants, unit.getMaxTenants());
        assertEquals(size, unit.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaxTenants() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size size = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int invalidMaxTenants = 0;

        new RentalApartmentUnit(apartmentComplex, doorNumber, size, invalidMaxTenants);
    }

    @Test
    public void testRemodel() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size initialSize = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int maxTenants = 4;

        RentalApartmentUnit unit = new RentalApartmentUnit(apartmentComplex, doorNumber, initialSize, maxTenants);

        Size newSize = new Size(BigDecimal.valueOf(120), SizeUnit.SQUARE_METERS);
        int newMaxTenants = 5;

        unit.remodel(newSize, newMaxTenants);

        assertEquals(newSize, unit.getSize());
        assertEquals(newMaxTenants, unit.getMaxTenants());
        assertNull(unit.getLeaseAgreement());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemodelWhileRented() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size initialSize = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int maxTenants = 4;

        RentalApartmentUnit unit = new RentalApartmentUnit(apartmentComplex, doorNumber, initialSize, maxTenants);

        Size newSize = new Size(BigDecimal.valueOf(120), SizeUnit.SQUARE_METERS);
        int newMaxTenants = 5;

        // Simulate renting the unit
        List<Tenant> tenants = Collections.singletonList(new Tenant("John", "Doe", new ContactAvenueEmail(new Email("john@doe.com"))));
        LocalDate startDate = LocalDate.now();
        Rent rent = new Rent(1200);
        int monthlyDayOfPayment = 1;
        int monthsOfNotice = 3;

        unit.rentToTenants(tenants, startDate, rent, monthlyDayOfPayment, monthsOfNotice);

        // Attempt to remodel while rented (should throw IllegalStateException)
        unit.remodel(newSize, newMaxTenants);
    }

    @Test
    public void testRentToTenants() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size initialSize = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int maxTenants = 4;

        RentalApartmentUnit unit = new RentalApartmentUnit(apartmentComplex, doorNumber, initialSize, maxTenants);

        List<Tenant> tenants = Collections.singletonList(new Tenant("John", "Doe", new ContactAvenueEmail(new Email("john@doe.com"))));
        LocalDate startDate = LocalDate.now();
        Rent rent = new Rent(1200);
        int monthlyDayOfPayment = 1;
        int monthsOfNotice = 3;

        unit.rentToTenants(tenants, startDate, rent, monthlyDayOfPayment, monthsOfNotice);

        LeaseAgreement leaseAgreement = unit.getLeaseAgreement();
        assertNotNull(leaseAgreement);
        assertEquals(tenants, leaseAgreement.getTenants());
        assertEquals(startDate, leaseAgreement.getInclusiveStartDate());
        assertEquals(rent, leaseAgreement.getRent());
        assertEquals(monthlyDayOfPayment, leaseAgreement.getMonthlyDayOfPayment().getNthDay());
        assertEquals(monthsOfNotice, leaseAgreement.getMonthsOfNotice());
        assertEquals(unit.getId(), leaseAgreement.getAssociatedRentalId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDoorNumber() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        Size size = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int maxTenants = 4;

        new RentalApartmentUnit(apartmentComplex, null, size, maxTenants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSize() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        int maxTenants = 4;

        new RentalApartmentUnit(apartmentComplex, doorNumber, null, maxTenants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMaxTenants() {
        LocalDate dateOfConstruction = LocalDate.of(2000, 1, 1);
        ApartmentComplex apartmentComplex = new ApartmentComplex("Main Street", "123", "12345", "City", dateOfConstruction);
        DoorNumber doorNumber = new DoorNumber(2, 3);
        Size size = new Size(BigDecimal.valueOf(100), SizeUnit.SQUARE_METERS);
        int negativeMaxTenants = -1;

        new RentalApartmentUnit(apartmentComplex, doorNumber, size, negativeMaxTenants);
    }
}