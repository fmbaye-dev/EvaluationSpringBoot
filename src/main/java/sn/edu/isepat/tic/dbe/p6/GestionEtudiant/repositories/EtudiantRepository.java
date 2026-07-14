package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Etudiant;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

     Optional<Etudiant> findByMatricule(String matricule);
     Optional<Etudiant> findByEmail (String email);
}
