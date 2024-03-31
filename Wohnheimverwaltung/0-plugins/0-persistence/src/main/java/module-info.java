module de.dhbw.plugin.persistence {
    // Import domain layer
    requires de.dhbw.domain;

    // Import Jackson
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.annotation;

    // Export packages
    exports de.dhbw.plugin.persistence to de.dhbw.application, de.dhbw.plugins.presentation;
}