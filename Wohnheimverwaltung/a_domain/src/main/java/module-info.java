module de.dhbw.domain {
    requires org.apache.commons.validator;
    requires libphonenumber;

    exports de.dhbw.domain;
    exports de.dhbw.domain.aggregates;
    exports de.dhbw.domain.entities;
}