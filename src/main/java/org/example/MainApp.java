package org.example;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Chargement du fichier JSON avant d'afficher l'interface
            GestionEvenements.getInstance().chargerDepuisJson("/Users/farellengapgou/IdeaProjects/projet_final_poo/src/main/resources/evenements.json");
            new MenuPrincipal();
        });
    }
}
