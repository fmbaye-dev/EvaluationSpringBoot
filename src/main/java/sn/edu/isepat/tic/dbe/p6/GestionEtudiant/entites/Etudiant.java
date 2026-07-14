package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Etudiant {
     @Id @GeneratedValue
     private Long id;

     @Column(nullable = false, unique = true)
     private String matricule;

     @Column(nullable = false)
     private String prenom;

     @Column(nullable = false)
     private String nom;

     @Column(nullable = false, unique = true)
     private String email;

     @Column(nullable = false)
     private LocalDate dateNaissance;

     @Column(nullable = false)
     private String lieuNaissance;

     @Column(nullable = false)
     private String nationalite;
}
