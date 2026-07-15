package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites.Etudiant;
import sn.edu.isepat.tic.dbe.p6.GestionEtudiant.services.EtudiantService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

     private final EtudiantService etudiantService;

     @GetMapping
     @SecurityRequirement(name = "bearerAuth")
     @Operation(
             summary = "Liste des étudiants",
             description = "Retourne la liste de tous les étudiants"
     )
     @ApiResponse(
             responseCode = "200",
             description = "Liste des étudiants récupérée avec succès",
             content = @Content(
                     schema = @Schema(implementation = Etudiant.class)
             )
     )
     public List<Etudiant> getEtudiantList (){
          return etudiantService.lister();
     }

     @GetMapping("/{id}")
     @SecurityRequirement(name = "bearerAuth")
     @Operation(
             summary = "Rechercher un étudiant",
             description = "Retourne un étudiant selon son identifiant"
     )
     @ApiResponse(
             responseCode = "200",
             description = "Liste des étudiants récupérée avec succès",
             content = @Content(
                     schema = @Schema(implementation = Etudiant.class)
             )
     )
     public ResponseEntity<?> getEtudiantByID(@PathVariable Long id){

          Optional<Etudiant> result = etudiantService.recherche(id);

          if (result.isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("Étudiant introuvable");
          }

          return ResponseEntity.ok(result.get());
     }

     @PostMapping
     @SecurityRequirement(name = "bearerAuth")
     @Operation(
             summary = "Ajouter un étudiant",
             description = "Permet de créer un nouvel étudiant"
     )
     @ApiResponse(
             responseCode = "201",
             description = "Étudiant créé avec succès"
     )
     public ResponseEntity<?> ajoutEtudiant(@RequestBody Etudiant etudiant){

          if (etudiant.getMatricule() == null || etudiant.getMatricule().isBlank()) {
               return ResponseEntity.badRequest().body("Le matricule est obligatoire");
          }

          if (etudiantService.rechercheParMatricule(etudiant.getMatricule()).isPresent()){
               return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body("Matricule déjà existant");
          }

          if (etudiant.getNom() == null || etudiant.getNom().isBlank()) {
               return ResponseEntity.badRequest().body("Le nom est obligatoire");
          }

          if (etudiant.getPrenom() == null || etudiant.getPrenom().isBlank()) {
               return ResponseEntity.badRequest().body("Le prénom est obligatoire");
          }

          if (etudiant.getEmail() == null || etudiant.getEmail().isBlank()){
               return ResponseEntity.badRequest().body("L'email est obligatoire");
          }

          if (etudiantService.rechercheParEmail(etudiant.getEmail()).isPresent()){
               return ResponseEntity.status(HttpStatus.CONFLICT).body("Email déjà existant");
          }

          Etudiant resultat = etudiantService.ajouter(etudiant);

          return ResponseEntity.status(HttpStatus.CREATED).body(resultat);
     }

     @PutMapping("/{id}")
     @SecurityRequirement(name = "bearerAuth")
     @Operation(
             summary = "Modifier un étudiant",
             description = "Met à jour les informations d'un étudiant existant"
     )
     @ApiResponse(
             responseCode = "200",
             description = "Étudiant modifié avec succès"
     )
     public ResponseEntity<?> modifierEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiant){
          if (etudiantService.recherche(id).isEmpty()) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("Étudiant introuvable");
          }

          if (etudiant.getMatricule() == null || etudiant.getMatricule().isBlank()) {
               return ResponseEntity.badRequest().body("Le matricule est obligatoire");
          }

          if (etudiant.getPrenom() == null || etudiant.getPrenom().isBlank()) {
               return ResponseEntity.badRequest().body("Le prénom est obligatoire");
          }

          if (etudiant.getNom() == null || etudiant.getNom().isBlank()) {
               return ResponseEntity.badRequest().body("Le nom est obligatoire");
          }

          if (etudiant.getEmail() == null || etudiant.getEmail().isBlank()) {
               return ResponseEntity.badRequest().body("L'email est obligatoire");
          }

          if (etudiant.getDateNaissance() == null ) {
               return ResponseEntity.badRequest().body("La date de naissance est obligatoire");
          }

          if (etudiant.getLieuNaissance() == null || etudiant.getLieuNaissance().isBlank()) {
               return ResponseEntity.badRequest().body("Le lieu de naissance est obligatoire");
          }

          if (etudiant.getNationalite() == null || etudiant.getNationalite().isBlank()) {
               return ResponseEntity.badRequest().body( "La nationalité est obligatoire");
          }

          Optional<Etudiant> etudiantMatricule = etudiantService.rechercheParMatricule(etudiant.getMatricule());

          if (etudiantMatricule.isPresent()
                  && !etudiantMatricule.get().getId().equals(id)) {
               return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body("Matricule déjà existant");
          }

          Optional<Etudiant> etudiantEmail = etudiantService.rechercheParEmail(etudiant.getEmail());

          if (etudiantEmail.isPresent()
                  && !etudiantEmail.get().getId().equals(id)) {
               return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body("Email déjà existant");
          }
          Etudiant resultat = etudiantService.modifier(etudiant, id);

          return ResponseEntity.ok(resultat);
     }

     @DeleteMapping("/{id}")
     @SecurityRequirement(name = "bearerAuth")
     @Operation(
             summary = "Supprimer un étudiant",
             description = "Supprime un étudiant selon son identifiant"
     )
     @ApiResponse(
             responseCode = "204",
             description = "Étudiant supprimé avec succès"
     )
     public ResponseEntity<?> supprimerEtudiant(@PathVariable Long id){
          if (etudiantService.recherche(id).isEmpty()){
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body("Étudiant introuvable");
          }

          etudiantService.supprimer(id);

          return ResponseEntity.noContent().build();
     }
}
