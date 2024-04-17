package de.dhbw.domain.valueObjects.ids;

import java.util.UUID;

public abstract class Id {
    private final UUID id;

    public Id() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id1 = (Id) o;
        return id.equals(id1.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
