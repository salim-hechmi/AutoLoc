package tn.esprit.autoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.entities.Agence;
import tn.esprit.autoloc.entities.Employe;
import tn.esprit.autoloc.repositories.AgenceRepository;
import tn.esprit.autoloc.repositories.EmployeRepository;
import tn.esprit.autoloc.services.IEmployeServiceImp;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeServiceMutationTest {

    @Mock
    private EmployeRepository employeRepository;

    @Mock
    private AgenceRepository agenceRepository;

    @InjectMocks
    private IEmployeServiceImp employeService;

    private Agence agenceTunis;
    private Agence agenceSfax;
    private Employe employe;

    @BeforeEach
    void setUp() {
        agenceTunis = new Agence();
        agenceTunis.setIdAgence(1L);
        agenceTunis.setNom("AutoLoc Tunis");
        agenceTunis.setVille("Tunis");

        agenceSfax = new Agence();
        agenceSfax.setIdAgence(2L);
        agenceSfax.setNom("AutoLoc Sfax");
        agenceSfax.setVille("Sfax");

        employe = new Employe();
        employe.setIdEmploye(10L);
        employe.setNom("Trabelsi");
        employe.setPrenom("Karim");
        // Initialement affecté à l'agence de Sfax
        employe.setAgence(agenceSfax);
    }

    @Test
    @DisplayName("Scénario Mutation : Désaffecter l'employé de Sfax puis l'affecter à Tunis")
    void testMutationEmployeDeSfaxVersTunis() {
        when(employeRepository.findById(10L)).thenReturn(Optional.of(employe));
        when(employeRepository.save(any(Employe.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(agenceTunis));

        // 1. Vérification de l'état initial : affecté à Sfax
        assertNotNull(employe.getAgence());
        assertEquals("Sfax", employe.getAgence().getVille());

        // 2. Étape 1 : Désaffecter l'employé
        Employe employeDesaffecte = employeService.desaffecterEmploye(10L);
        assertNotNull(employeDesaffecte);
        assertNull(employeDesaffecte.getAgence(), "L'employé doit être désaffecté (agence = null)");

        // 3. Étape 2 : Affecter l'employé à la nouvelle agence (Tunis)
        Employe employeMute = employeService.affecterEmploye(10L, 1L);
        assertNotNull(employeMute);
        assertNotNull(employeMute.getAgence());
        assertEquals(1L, employeMute.getAgence().getIdAgence());
        assertEquals("Tunis", employeMute.getAgence().getVille(), "L'employé doit être affecté à Tunis");

        verify(employeRepository, times(2)).findById(10L);
        verify(agenceRepository, times(1)).findById(1L);
        verify(employeRepository, times(2)).save(any(Employe.class));
    }
}
