package de.dhbw.domain.entities;

import static org.junit.Assert.*;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import de.dhbw.domain.miscellaneous.ContactAvenue;
import de.dhbw.domain.valueObjects.ids.RentalId;
import org.junit.Test;
import de.dhbw.domain.aggregateRoots.Tenant;
import de.dhbw.domain.valueObjects.*;
import de.dhbw.domain.valueObjects.ids.LeaseAgreementId;

import java.time.LocalDate;
import java.util.Collections;

public class TenantTest {

    @Test
    public void testValidTenantCreation() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);

        assertNotNull(tenant.getId());
        assertEquals(name, tenant.getName().getName());
        assertEquals(surname, tenant.getName().getSurname());
        assertEquals(name + " " + surname, tenant.getName().getFullName());
        assertEquals(contactAvenue, tenant.getContactAvenues().getFirst());
        assertEquals(0, tenant.getOutstandingBalanceHistory().size());
        assertEquals(0, tenant.getAssociatedLeaseAgreementIds().size());
    }

    @Test
    public void testTenantAddNewAndRemovePreferredContactAvenue() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);

        // Add new contact avenue
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(49);
        phoneNumber.setNationalNumber(7219735838L);
        ContactAvenuePhone newContactAvenue = new ContactAvenuePhone(phoneNumber);
        tenant.addContactAvenue(newContactAvenue);

        assertEquals(2, tenant.getContactAvenues().size());
        assertTrue(tenant.getContactAvenues().contains(contactAvenue));
        assertTrue(tenant.getContactAvenues().contains(newContactAvenue));

        // Remove existing contact avenue
        tenant.removeContactAvenue(contactAvenue);

        assertEquals(1, tenant.getContactAvenues().size());
        assertFalse(tenant.getContactAvenues().contains(contactAvenue));
        assertTrue(tenant.getContactAvenues().contains(newContactAvenue));
    }

    @Test
    public void testTenantAddNewAndRemoveContactAvenueAgain() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);

        // Add new contact avenue
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(49);
        phoneNumber.setNationalNumber(7219735838L);
        ContactAvenuePhone newContactAvenue = new ContactAvenuePhone(phoneNumber);
        tenant.addContactAvenue(newContactAvenue);

        assertEquals(2, tenant.getContactAvenues().size());
        assertTrue(tenant.getContactAvenues().contains(contactAvenue));
        assertTrue(tenant.getContactAvenues().contains(newContactAvenue));

        // Remove new contact avenue
        tenant.removeContactAvenue(newContactAvenue);

        assertEquals(1, tenant.getContactAvenues().size());
        assertFalse(tenant.getContactAvenues().contains(newContactAvenue));
        assertTrue(tenant.getContactAvenues().contains(contactAvenue));
    }

    @Test
    public void testTenantAddNewPreferredAndRemoveOldContactAvenue() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);

        // Add new contact avenue
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(49);
        phoneNumber.setNationalNumber(7219735838L);
        ContactAvenuePhone newContactAvenue = new ContactAvenuePhone(phoneNumber);
        tenant.addContactAvenue(newContactAvenue);
        tenant.setPreferredContactAvenue(newContactAvenue);

        assertEquals(2, tenant.getContactAvenues().size());
        assertTrue(tenant.getContactAvenues().contains(contactAvenue));
        assertTrue(tenant.getContactAvenues().contains(newContactAvenue));

        // Remove new contact avenue
        tenant.removeContactAvenue(newContactAvenue);

        assertEquals(1, tenant.getContactAvenues().size());
        assertFalse(tenant.getContactAvenues().contains(newContactAvenue));
        assertTrue(tenant.getContactAvenues().contains(contactAvenue));
    }

    @Test
    public void testRegisterAndDeregisterLeaseAgreementSubscription() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);
        LeaseAgreementId leaseAgreementId = new LeaseAgreementId();

        // Register lease agreement subscription
        tenant.registerLeaseAgreementSubscription(leaseAgreementId);
        assertEquals(1, tenant.getAssociatedLeaseAgreementIds().size());
        assertTrue(tenant.getAssociatedLeaseAgreementIds().contains(leaseAgreementId));

        // Deregister lease agreement subscription
        tenant.deregisterLeaseAgreementSubscription(leaseAgreementId, LocalDate.now());
        assertEquals(0, tenant.getAssociatedLeaseAgreementIds().size());
        assertFalse(tenant.getAssociatedLeaseAgreementIds().contains(leaseAgreementId));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChargeTenantWithNonSubscribedLeaseAgreement() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);
        LeaseAgreement leaseAgreement = new LeaseAgreement(Collections.emptyList(), LocalDate.now(), new Rent(1000), 1, 3, new RentalId());
        RentCharge rentCharge = new RentCharge(500, LocalDate.now(), leaseAgreement.getId());

        tenant.getCharged(leaseAgreement, rentCharge); // Should throw IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoubleChargeForTenant() {
        String name = "John";
        String surname = "Doe";
        ContactAvenueEmail contactAvenue = new ContactAvenueEmail(new Email("john@doe.com"));

        Tenant tenant = new Tenant(name, surname, contactAvenue);

        LeaseAgreement leaseAgreement = new LeaseAgreement(Collections.emptyList(), LocalDate.now(), new Rent(1000), 1, 3, new RentalId());
        RentCharge rentCharge = new RentCharge(500, LocalDate.now(), leaseAgreement.getId());

        tenant.getCharged(leaseAgreement, rentCharge);
        tenant.getCharged(leaseAgreement, rentCharge); // Should throw IllegalArgumentException
    }
}