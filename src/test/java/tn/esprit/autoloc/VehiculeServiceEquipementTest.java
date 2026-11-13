package tn.esprit.autoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.entities.Equipement;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.repositories.EquipementRepository;
import tn.esprit.autoloc.repositories.VehiculeRepository;
import tn.esprit.autoloc.services.IVehiculeServiceImp;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculeServiceEquipementTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private EquipementRepository equipementRepository;

    @InjectMocks
    private IVehiculeServiceImp vehiculeService;

    private Vehicule vehicule;
    private Equipement siegeBebe;

    @BeforeEach
    void setUp() {
        vehicule = new Vehicule();
        vehicule.setIdVehicule(1L);
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio 5");
        vehicule.setEquipements(new ArrayList<>());

        siegeBebe = new Equipement();
        siegeBebe.setIdEquipement(3L);
        siegeBebe.setLibelle("Siège Bébé Isofix");
    }

    @Test
    @DisplayName("Scénario 3 : Affecter puis désaffecter un équipement (Siège Bébé) à un véhicule")
    void testAffecterEtDesaffecterEquipement() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(equipementRepository.findById(3L)).thenReturn(Optional.of(siegeBebe));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 1. Affectation de l'équipement au départ
        Vehicule vApresAffectation = vehiculeService.affecterEquipement(1L, 3L);
        assertNotNull(vApresAffectation);
        assertEquals(1, vApresAffectation.getEquipements().size());
        assertEquals("Siège Bébé Isofix", vApresAffectation.getEquipements().get(0).getLibelle());

        // 2. Désaffectation de l'équipement au retour
        Vehicule vApresDesaffectation = vehiculeService.desaffecterEquipement(1L, 3L);
        assertNotNull(vApresDesaffectation);
        assertEquals(0, vApresDesaffectation.getEquipements().size(), "L'équipement doit être retiré de la liste");

        verify(vehiculeRepository, times(2)).findById(1L);
        verify(equipementRepository, times(2)).findById(3L);
        verify(vehiculeRepository, times(2)).save(any(Vehicule.class));
    }
}
