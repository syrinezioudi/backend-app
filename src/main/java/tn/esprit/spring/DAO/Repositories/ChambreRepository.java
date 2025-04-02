package tn.esprit.spring.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;

import java.time.LocalDate;
import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    Chambre findByNumeroChambre(long num);

    List<Chambre> findByBlocNomBloc(String nom);

    int countByTypeCAndBlocIdBloc(TypeChambre typeChambre, long idBloc);

    //********************* Ajouter Reservation *********************
    //SQL

    //Keyword
    int countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(long chambreId, LocalDate dateDebutAU, LocalDate dateFinAU);

    //*****************************************************************
    Chambre findByReservationsIdReservation(String idReservation);

    long count();

    long countChambreByTypeC(TypeChambre typeChambre);

    long countReservationsByIdChambreAndReservationsEstValideAndReservationsAnneeUniversitaireBetween(long idChambre, boolean estValide, LocalDate dateDebut, LocalDate dateFin);


}
