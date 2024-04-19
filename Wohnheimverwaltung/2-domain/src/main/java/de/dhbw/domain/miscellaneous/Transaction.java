package de.dhbw.domain.miscellaneous;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.dhbw.domain.entities.Deposit;
import de.dhbw.domain.entities.RentCharge;
import de.dhbw.domain.valueObjects.ids.TransactionId;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Deposit.class),
        @JsonSubTypes.Type(value = RentCharge.class),
})
public interface Transaction {
    int getAmount();
    LocalDate getDate();
    TransactionId getTransactionId();
}
