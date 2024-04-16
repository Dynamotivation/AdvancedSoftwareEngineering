package de.dhbw.plugin.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.miscellaneous.Rental;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    @Override
    public void remove(ApartmentComplex apartmentComplex) {
        apartmentComplexes.remove(apartmentComplex);
    }

    @Override
    public void save(ApartmentComplex apartmentComplex) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var writer = new FileWriter("complex.save", true)) {
            String jsonString = mapper.writeValueAsString(apartmentComplex) + "\n";

            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (var reader = new BufferedReader(new FileReader("complex.save"))) {
            while (reader.ready()) {
                String jsonString = reader.readLine();
                ApartmentComplex apartmentComplex = mapper.readValue(jsonString, ApartmentComplex.class);
                apartmentComplexes.add(apartmentComplex);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
