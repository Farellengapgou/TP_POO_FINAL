package org.example;

import javax.swing.*;
import java.util.Map;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Chargement du fichier JSON avant d'afficher l'interface
            Map<String, Evenement> donnees = GestionEvenements.getInstance().chargerDepuisJson("/Users/farellengapgou/IdeaProjects/projet_final_poo/src/main/resources/evenements.json");
            GestionEvenements.getInstance().setEvenements(donnees);
            GestionEvenements.getInstance().initialiserObservateursDepuisEvenements();

            new MenuPrincipal();
        });
    }
}
