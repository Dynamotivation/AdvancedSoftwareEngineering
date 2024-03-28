package de.dhbw.domain.valueObjects.ids;

import java.util.Objects;
import java.util.UUID;

public class LeaseAgreementId {
    private final UUID id;

    public LeaseAgreementId() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseAgreementId tenantId = (LeaseAgreementId) o;
        return Objects.equals(id, tenantId.id);
    }
}
