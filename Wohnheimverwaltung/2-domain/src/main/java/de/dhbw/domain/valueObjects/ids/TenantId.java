package de.dhbw.domain.valueObjects.ids;

import java.util.Objects;
import java.util.UUID;

public class TenantId {
    private final UUID id;

    public TenantId() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantId tenantId = (TenantId) o;
        return Objects.equals(id, tenantId.id);
    }
}
