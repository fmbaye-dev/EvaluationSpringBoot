package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
     Optional<Utilisateur> findByEmail(String email);
}
