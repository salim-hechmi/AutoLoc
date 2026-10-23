package tn.esprit.autoloc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    private LocalDate dateSignature;

    private BigDecimal montantTotal;

    private Boolean valide;

    @OneToOne(mappedBy = "contrat")
    @JsonIgnore
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat")
    @JsonIgnore
    private List<Paiement> paiements;
}
