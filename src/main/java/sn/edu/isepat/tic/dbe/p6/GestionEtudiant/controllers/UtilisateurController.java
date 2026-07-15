package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Utilisateur;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services.JwtService;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services.UtilisateurService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UtilisateurController {

     private final UtilisateurService utilisateurService;
     private final PasswordEncoder passwordEncoder;
     private final JwtService jwtService;

     @PostMapping("/register")
     public ResponseEntity<?> register(@RequestBody Utilisateur utilisateur){
          if (utilisateur.getNom() == null || utilisateur.getNom().isBlank()){
               return ResponseEntity.badRequest().body("Le nom est obligatoire");
          }

          if (utilisateur.getEmail() == null || utilisateur.getEmail().isBlank()){
               return ResponseEntity.badRequest().body("L'email est obligatoire");
          }

          if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isBlank()){
               return ResponseEntity.badRequest().body("Le mot de passe est obligatoire");
          }

          if (utilisateurService.rechercheParEmail(utilisateur.getEmail()).isPresent()){
               return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà existant");
          }

          Utilisateur resultat = utilisateurService.incrire(utilisateur);

          return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
     }

     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody Utilisateur utilisateur){

          Utilisateur userDb = utilisateurService.authentifier(utilisateur.getEmail());

          if (userDb == null){
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email incorrect");
          }

          if (!passwordEncoder.matches(
                  utilisateur.getMotDePasse(),
                  userDb.getMotDePasse())){
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
          }
          String token =
                  jwtService.genererToken(
                          userDb.getEmail()
                  );


          return ResponseEntity.ok(token);
     }
}
