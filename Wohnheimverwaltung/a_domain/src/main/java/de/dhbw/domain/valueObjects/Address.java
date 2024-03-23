package de.dhbw.domain.valueObjects;

public class Address {
    private final String streetName;
    private final String houseNumber;
    private final String postalCode;
    private final String city;

    public Address(String streetName, String houseNumber, String postalCode, String city) {
        // Validate street name
        streetName = streetName.trim();

        if (!streetName.matches("^[a-zA-ZäöüÄÖÜß\\s]+"))
            throw new IllegalArgumentException("Invalid street name");


        // Validate house number
        houseNumber = houseNumber.trim();
        houseNumber = houseNumber.toLowerCase();

        if (!houseNumber.matches("^[1-9][0-9]*[a-z]?"))
            throw new IllegalArgumentException("Invalid house number");


        // Validate postal code
        postalCode = postalCode.trim();

        if (!postalCode.matches("^[0-9]{5}"))
            throw new IllegalArgumentException("Invalid postal code");


        // Validate city
        city = city.trim();

        if (!city.matches("^[a-zA-ZäöüÄÖÜß\\s]+"))
            throw new IllegalArgumentException("Invalid city");


        // Assign values
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!streetName.equals(address.streetName)) return false;
        if (!houseNumber.equals(address.houseNumber)) return false;
        if (!postalCode.equals(address.postalCode)) return false;
        return city.equals(address.city);
    }
}
