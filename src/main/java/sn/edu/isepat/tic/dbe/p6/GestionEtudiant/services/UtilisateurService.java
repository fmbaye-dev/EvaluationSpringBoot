package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Utilisateur;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.repositories.UtilisateurRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

     private final UtilisateurRepository utilisateurRepository;
     private final PasswordEncoder passwordEncoder;

     public Utilisateur incrire(Utilisateur utilisateur){
          utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
          return utilisateurRepository.save(utilisateur);
     }

     public Utilisateur authentifier(String email){
          return utilisateurRepository.findByEmail(email).orElse(null);
     }

     public Optional<Utilisateur> rechercheParEmail(String email){
          return utilisateurRepository.findByEmail(email);
     }

}
