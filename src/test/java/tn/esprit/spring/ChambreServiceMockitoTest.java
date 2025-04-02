package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;



class ChambreServiceMockitoTest {

    @InjectMocks
    private ChambreService chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // GIVEN
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(303);

        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // WHEN
        Chambre result = chambreService.findById(1L);

        // THEN
        assertNotNull(result);
        assertEquals(303, result.getNumeroChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testAddOrUpdate() {
        // GIVEN
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(404);

        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // WHEN
        Chambre savedChambre = chambreService.addOrUpdate(chambre);

        // THEN
        assertNotNull(savedChambre);
        assertEquals(404, savedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).save(chambre);
    }
}
