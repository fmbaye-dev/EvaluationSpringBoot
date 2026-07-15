package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Etudiant;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.repositories.EtudiantRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EtudiantService {

     private final EtudiantRepository etudiantRepository;

     public List<Etudiant> lister(){
          return etudiantRepository.findAll();
     }

     public Etudiant ajouter (Etudiant etudiant){
          return etudiantRepository.save(etudiant);
     }

     public Etudiant modifier (Etudiant etudiant, Long id){
          Etudiant etudiantModif = etudiantRepository.findById(id)
                  .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

          etudiantModif.setMatricule(etudiant.getMatricule());
          etudiantModif.setPrenom(etudiant.getPrenom());
          etudiantModif.setNom(etudiant.getNom());
          etudiantModif.setEmail(etudiant.getEmail());
          etudiantModif.setDateNaissance(etudiant.getDateNaissance());
          etudiantModif.setLieuNaissance(etudiant.getLieuNaissance());
          etudiantModif.setNationalite(etudiant.getNationalite());

          return etudiantRepository.save(etudiantModif);
     }

     public void supprimer(Long id){

          etudiantRepository.deleteById(id);
     }

     public Optional <Etudiant> recherche(Long id){

          return etudiantRepository.findById(id);
     }

     public Optional<Etudiant> rechercheParMatricule(String matricule){

          return etudiantRepository.findByMatricule(matricule);
     }


     public Optional<Etudiant> rechercheParEmail(String email){

          return etudiantRepository.findByEmail(email);
     }
}
