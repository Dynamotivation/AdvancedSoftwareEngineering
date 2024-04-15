package de.dhbw.domain.repositories;

import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.util.List;

public interface ApartmentComplexRepository {
    List<ApartmentComplex> listAll();
    ApartmentComplex findByApartmentComplexId(ApartmentComplexId apartmentComplexId);
    void add(ApartmentComplex apartmentComplex);
    void save(ApartmentComplex apartmentComplex);
    void load();
}
