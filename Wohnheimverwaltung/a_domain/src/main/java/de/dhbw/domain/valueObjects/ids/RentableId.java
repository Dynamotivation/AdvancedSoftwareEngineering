package de.dhbw.domain.valueObjects.ids;

import java.util.Objects;
import java.util.UUID;

public class RentableId {
    private final UUID id;

    public RentableId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentableId tenantId = (RentableId) o;
        return Objects.equals(id, tenantId.id);
    }
}
