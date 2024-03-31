module de.dhbw.application {
    // Import domain layer
    requires de.dhbw.domain;

    // Export outwards to presentation layer
    exports de.dhbw.application.services to de.dhbw.plugin.presentation;
    exports de.dhbw.application.transferObjects to de.dhbw.plugin.presentation;
}