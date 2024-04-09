package de.dhbw.plugin.persistence;

import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.util.ArrayList;
import java.util.List;

public class ApartmentComplexJacksonJsonRepository implements ApartmentComplexRepository {
    private final List<ApartmentComplex> apartmentComplexes = new ArrayList<>();


    @Override
    public List<ApartmentComplex> listAll() {
        return new ArrayList<>(apartmentComplexes);
    }

    @Override
    public ApartmentComplex findByApartmentComplexId(ApartmentComplexId apartmentComplexId) {
        return apartmentComplexes.stream()
                .filter(rental -> rental.getId().equals(apartmentComplexId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(ApartmentComplex apartmentComplex) {
        apartmentComplexes.add(apartmentComplex);
    }
}
