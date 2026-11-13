package tn.esprit.autoloc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.entities.Agence;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.entities.enums.CategorieVehicule;
import tn.esprit.autoloc.entities.enums.StatutVehicule;
import tn.esprit.autoloc.repositories.AgenceRepository;
import tn.esprit.autoloc.repositories.VehiculeRepository;
import tn.esprit.autoloc.services.IAgenceServiceImp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgenceServiceVehiculesTest {

    @Mock
    private AgenceRepository agenceRepository;

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private IAgenceServiceImp agenceService;

    @Test
    @DisplayName("Scénario 2 : Ajout d'une agence avec deux véhicules et affectation")
    void testAjouterAgenceEtAffecterVehicules() {
        Agence agence = new Agence();
        agence.setNom("AutoLoc Sousse");
        agence.setVille("Sousse");
        agence.setAdresse("Boulevard 14 Janvier, Sousse");
        agence.setTelephone("+216 73 111 222");

        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("260-TN-1122");
        v1.setMarque("Peugeot");
        v1.setModele("208");
        v1.setCategorie(CategorieVehicule.CITADINE);
        v1.setTarifJournalier(new BigDecimal("90.00"));
        v1.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("261-TN-3344");
        v2.setMarque("Nissan");
        v2.setModele("Qashqai");
        v2.setCategorie(CategorieVehicule.SUV);
        v2.setTarifJournalier(new BigDecimal("200.00"));
        v2.setStatut(StatutVehicule.DISPONIBLE);

        List<Vehicule> vehicules = new ArrayList<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        when(agenceRepository.save(any(Agence.class))).thenAnswer(invocation -> {
            Agence a = invocation.getArgument(0);
            a.setIdAgence(10L);
            return a;
        });
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Agence saved = agenceService.ajouterAgenceEtAffecterVehicules(agence);

        assertNotNull(saved);
        assertEquals(10L, saved.getIdAgence());
        assertEquals("Sousse", saved.getVille());
        assertNotNull(saved.getVehicules());
        assertEquals(2, saved.getVehicules().size());
        assertEquals(saved, v1.getAgence());
        assertEquals(saved, v2.getAgence());

        verify(agenceRepository, times(1)).save(agence);
        verify(vehiculeRepository, times(2)).save(any(Vehicule.class));
    }
}
