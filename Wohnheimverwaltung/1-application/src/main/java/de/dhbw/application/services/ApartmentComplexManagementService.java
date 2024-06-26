package de.dhbw.application.services;

import de.dhbw.application.snapshotObjects.ApartmentComplexSnapshotDTO;
import de.dhbw.domain.entities.ApartmentComplex;
import de.dhbw.domain.repositories.ApartmentComplexRepository;
import de.dhbw.domain.valueObjects.ids.ApartmentComplexId;

import java.time.LocalDate;
import java.util.List;

public class ApartmentComplexManagementService {
    private final ApartmentComplexRepository apartmentComplexRepository;

    public ApartmentComplexManagementService(ApartmentComplexRepository apartmentComplexRepository) {
        this.apartmentComplexRepository = apartmentComplexRepository;
    }

    public ApartmentComplexSnapshotDTO getApartmentComplexById(ApartmentComplexId apartmentComplexId) {
        return new ApartmentComplexSnapshotDTO(apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId));
    }

    public List<ApartmentComplexSnapshotDTO> getAllApartmentComplexes() {
        return apartmentComplexRepository.listAll().stream().map(ApartmentComplexSnapshotDTO::new).toList();
    }

    public ApartmentComplexId createApartmentComplex(String streetName, String houseNumber, String postalCode, String city, LocalDate dateOfConstruction) {
        ApartmentComplex apartmentComplex = new ApartmentComplex(streetName, houseNumber, postalCode, city, dateOfConstruction);
        apartmentComplexRepository.add(apartmentComplex);

        return apartmentComplex.getId();
    }

    public List<ApartmentComplexSnapshotDTO> listAllApartmentComplexSnapshots() {
        return apartmentComplexRepository.listAll().stream()
                .map(ApartmentComplexSnapshotDTO::new)
                .toList();
    }

    public ApartmentComplexSnapshotDTO findByApartmentId(ApartmentComplexId apartmentComplexId) {
        return new ApartmentComplexSnapshotDTO(apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId));
    }

    public void saveAllOrphanApartmentComplexes() {
        for (ApartmentComplex apartmentComplex : apartmentComplexRepository.listAll()) {
            if (apartmentComplex.getRentalApartmentUnits().isEmpty()) {
                apartmentComplexRepository.save(apartmentComplex);
            }
        }
    }

    public void exportApartmentComplexById(ApartmentComplexId apartmentComplexId) {
        ApartmentComplex apartmentComplex = apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId);
        apartmentComplexRepository.save(apartmentComplex);
    }

    public void loadApartmentComplexes() {
        apartmentComplexRepository.load();
    }

    public void deleteApartmentComplex(ApartmentComplexId apartmentComplexId) {
        ApartmentComplex apartmentComplex = apartmentComplexRepository.findByApartmentComplexId(apartmentComplexId);
        apartmentComplexRepository.remove(apartmentComplex);
    }

    public List<ApartmentComplexSnapshotDTO> importApartmentComplexes() {
        return apartmentComplexRepository.load().stream()
                .map(ApartmentComplexSnapshotDTO::new)
                .toList();
    }
}
