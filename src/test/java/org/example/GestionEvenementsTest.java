package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GestionEvenementsTest {

    private GestionEvenements gestion;

    @BeforeEach
    public void setUp() {
        gestion = GestionEvenements.getInstance();
        gestion.setEvenements(new HashMap<>());
        gestion.setObservateurs(new HashMap<>());
    }

    @Test
    public void testAjouterEvenement() {
        Evenement e = new Conference(
                "CF003",
                "Innovation Médicale",
                LocalDateTime.of(2025, 12, 1, 14, 0),
                "Hôpital Universitaire",
                150,
                "L'IA au service du diagnostic"
        );
        gestion.ajouterEvenement(e);
        assertEquals(e, gestion.rechercherEvenement("CF003"));
    }


    @Test
    public void testSupprimerEvenement() {
        Evenement e = new Concert(
                "C001",
                "Soirée Jazz",
                LocalDateTime.of(2025, 6, 15, 20, 0),
                "Théâtre National",
                300,
                "Miles Davis Revival",
                "Jazz"
        );
        gestion.ajouterEvenement(e);
        boolean supprimé = gestion.supprimerEvenement("C001");
        assertTrue(supprimé);
        assertNull(gestion.rechercherEvenement("C001"));
    }

    @Test
    public void testModifierEvenement() {
        Evenement e =  new Conference(
                "CF001",
                "Tech & IA",
                LocalDateTime.of(2025, 10, 3, 9, 0),
                "Centre des Congrès",
                400,
                "Les enjeux éthiques de l'IA"
        );
        gestion.ajouterEvenement(e);
        boolean modifié = gestion.modifierEvenement("CF001", "Conférence Modifiée", "Salle C", 75);
        assertTrue(modifié);
        Evenement modifiéE = gestion.rechercherEvenement("CF001");
        assertEquals("Conférence Modifiée", modifiéE.getNom());
        assertEquals("Salle C", modifiéE.getLieu());
        assertEquals(75, modifiéE.getCapaciteMax());
    }

    @Test
    public void testAjouterEtSupprimerObservateur() {
        Evenement e =  new Conference(
                "CF002",
                "Sécurité Informatique",
                LocalDateTime.of(2025, 11, 15, 10, 30),
                "Université Polytech",
                250,
                "Sécurité des réseaux et cybersécurité"
        );
        gestion.ajouterEvenement(e);
        ParticipantObserver mockObserver = message -> System.out.println("Reçu : " + message);

        gestion.ajouterObservateur("Cf002", mockObserver);
        assertTrue(gestion.getObservateurs().get("CF002").contains(mockObserver));

        gestion.supprimerObservateur("CF002", mockObserver);
        assertFalse(gestion.getObservateurs().get("CF002").contains(mockObserver));
    }

    @Test
    public void testSauvegardeEtChargementEvenements() throws Exception {
        String fichierTest = "evenements_test.json";
        Evenement e1 = new Concert(
                "C002",
                "Festival Électro",
                LocalDateTime.of(2025, 7, 21, 22, 0),
                "Plage des Sables",
                1500,
                "DJ Nova",
                "Électro"
        );
        Map<String, Evenement> mapTest = new HashMap<>();
        mapTest.put(e1.getId(), e1);

        // Sauvegarde
        boolean sauvegardé = gestion.SauvegardeEvenement(mapTest, fichierTest);
        assertTrue(sauvegardé);
        File f = new File(fichierTest);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);

        // Chargement
        Map<String, Evenement> mapChargée = gestion.chargerDepuisJson(fichierTest);
        assertEquals(1, mapChargée.size());
        assertTrue(mapChargée.containsKey("C002"));

        // Nettoyage
        if (f.exists()) f.delete();
    }

    @Test
    public void testInitialiserObservateursDepuisEvenements() {
        Evenement e1 = new Concert(
                "C003",
                "Concert Classique",
                LocalDateTime.of(2025, 8, 5, 19, 30),
                "Opéra de la ville",
                500,
                "Orchestre Philharmonique",
                "Classique"
        );
        Evenement e2 = new Conference(
                "CF004",
                "Développement Durable",
                LocalDateTime.of(2025, 9, 22, 16, 0),
                "Maison de l'Environnement",
                300,
                "Technologies vertes et futur écologique"
        );

        gestion.ajouterEvenement(e1);
        gestion.ajouterEvenement(e2);

        gestion.initialiserObservateursDepuisEvenements();
        assertEquals(2, gestion.getObservateurs().size());
        assertTrue(gestion.getObservateurs().get("C003").isEmpty());
        assertTrue(gestion.getObservateurs().get("C004").isEmpty());
    }
}
