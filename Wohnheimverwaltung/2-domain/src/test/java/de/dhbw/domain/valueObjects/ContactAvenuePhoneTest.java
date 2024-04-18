package de.dhbw.domain.valueObjects;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class ContactAvenuePhoneTest {

    @Test
    public void testValidPhoneNumber() {
        PhoneNumber validNumber = new PhoneNumber();
        validNumber.setCountryCode(49);
        validNumber.setNationalNumber(7219735838L);

        // Mock PhoneNumberUtil
        PhoneNumberUtil phoneNumberUtilMock = Mockito.mock(PhoneNumberUtil.class);
        Mockito.when(phoneNumberUtilMock.isValidNumber(validNumber)).thenReturn(true);

        new ContactAvenuePhone(validNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneNumber() {
        PhoneNumber invalidNumber = new PhoneNumber();
        invalidNumber.setCountryCode(1);
        invalidNumber.setNationalNumber(12345L); // Too short, invalid number

        // Mock PhoneNumberUtil
        PhoneNumberUtil phoneNumberUtilMock = Mockito.mock(PhoneNumberUtil.class);
        Mockito.when(phoneNumberUtilMock.isValidNumber(invalidNumber)).thenReturn(false);

        new ContactAvenuePhone(invalidNumber);
    }

    @Test
    public void testContactAvenueEquality() {
        ContactAvenuePhone phone1 = new ContactAvenuePhone(createPhoneNumber(49, 17305649897L));
        ContactAvenuePhone phone2 = new ContactAvenuePhone(createPhoneNumber(49, 17305649897L));
        ContactAvenuePhone phone3 = new ContactAvenuePhone(createPhoneNumber(49, 63957452109L));

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
    }

    @Test
    public void testContactAvenueHashCode() {
        // Create two PhoneNumber instances representing valid phone numbers
        PhoneNumber phoneNumber1 = createPhoneNumber(49, 17305649897L); // Country code for Germany
        PhoneNumber phoneNumber2 = createPhoneNumber(49, 17305649897L);  // Country code for USA

        ContactAvenuePhone phone1 = new ContactAvenuePhone(phoneNumber1);
        ContactAvenuePhone phone2 = new ContactAvenuePhone(phoneNumber2);

        // Ensure that equal phone numbers produce the same hash code
        assertEquals(phone1.hashCode(), phone2.hashCode());
    }

    @Test
    public void testContactAvenueToString() {
        PhoneNumber phoneNumber = createPhoneNumber(49, 7219735838L);
        ContactAvenuePhone phone = new ContactAvenuePhone(phoneNumber);

        assertEquals("+497219735838", phone.toString());
    }

    // Helper method to create PhoneNumber object
    private PhoneNumber createPhoneNumber(int countryCode, long nationalNumber) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountryCode(countryCode);
        phoneNumber.setNationalNumber(nationalNumber);
        return phoneNumber;
    }
}
