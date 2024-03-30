module de.dhbw.domain {
    requires org.apache.commons.validator;
    requires libphonenumber;
    requires com.fasterxml.jackson.annotation;

    opens de.dhbw.domain.aggregateRoots to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.valueObjects.ids to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.entities to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.valueObjects to com.fasterxml.jackson.databind;

    exports de.dhbw.domain.aggregateRoots;
    exports de.dhbw.domain.entities;
    exports de.dhbw.domain.miscellaneous;
    exports de.dhbw.domain.repositories;
    exports de.dhbw.domain.valueObjects;
    exports de.dhbw.domain.valueObjects.ids;
}