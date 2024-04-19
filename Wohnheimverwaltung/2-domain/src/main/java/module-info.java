module de.dhbw.domain {
    // Import core libraries
    requires org.apache.commons.validator;
    requires libphonenumber;
    requires com.fasterxml.jackson.annotation;
    requires static lombok;

    // Open packages only to required libraries
    opens de.dhbw.domain.aggregateRoots to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.valueObjects.ids to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.entities to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.valueObjects to com.fasterxml.jackson.databind;
    opens de.dhbw.domain.miscellaneous to com.fasterxml.jackson.databind;

    // Export packages
    exports de.dhbw.domain.aggregateRoots to de.dhbw.application, de.dhbw.plugin.persistence;
    exports de.dhbw.domain.entities to de.dhbw.application, de.dhbw.plugin.persistence;
    exports de.dhbw.domain.miscellaneous to de.dhbw.application, de.dhbw.plugin.persistence, de.dhbw.plugin.presentation;
    exports de.dhbw.domain.repositories to de.dhbw.application, de.dhbw.plugin.persistence, de.dhbw.plugin.presentation;
    exports de.dhbw.domain.valueObjects to de.dhbw.application, de.dhbw.plugin.persistence, de.dhbw.plugin.presentation;
    exports de.dhbw.domain.valueObjects.ids to de.dhbw.application, de.dhbw.plugin.persistence, de.dhbw.plugin.presentation;
}