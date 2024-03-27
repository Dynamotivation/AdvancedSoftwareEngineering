package de.dhbw.domain.valueObjects.ids;

import java.util.Objects;
import java.util.UUID;

public class RentalId {
    private final UUID id;

    public RentalId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalId tenantId = (RentalId) o;
        return Objects.equals(id, tenantId.id);
    }
}
