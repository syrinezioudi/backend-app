package tn.esprit.spring;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = FoyerApplication.class)
@ActiveProfiles("test")
@Transactional
class ChambreServiceTest {


    @Autowired
    ChambreRepository chambreRepository;

    @Test
    void testSaveAndRetrieveChambre() {
        // GIVEN
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(103);

        // WHEN
        Chambre savedChambre = chambreRepository.save(chambre);
        Chambre foundChambre = chambreRepository.findById(savedChambre.getIdChambre()).orElse(null);

        // THEN
        assertNotNull(foundChambre);
        assertEquals(103, foundChambre.getNumeroChambre());
    }

    @Test
    void testFindAllChambres() {
        // GIVEN
        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(205);
        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(207);

        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        // WHEN
        List<Chambre> chambres = chambreRepository.findAll();

        // THEN
        assertTrue(chambres.size() >= 2);
    }



}
