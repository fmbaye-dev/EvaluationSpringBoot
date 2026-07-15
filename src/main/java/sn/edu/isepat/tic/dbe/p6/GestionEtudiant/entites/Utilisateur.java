package sn.edu.isepat.tic.dbe.p6.GestionEtudiant.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utilisateur {

     @Id @GeneratedValue
     private Long id;

     @Column
     private String nom;

     @Column(unique = true)
     private String email;

     @Column(nullable = false)
     private String motDePasse;

}
