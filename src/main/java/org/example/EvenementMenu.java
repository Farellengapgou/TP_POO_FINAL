package org.example;


import javax.swing.*;
import java.awt.*;

public class EvenementMenu extends JFrame {
    public EvenementMenu() {
        setTitle("Menu Événements");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JButton creerBtn = new JButton("Créer un événement");
        creerBtn.addActionListener(e -> new org.example.Evenementcreer());

        JButton modifierBtn = new JButton("Modifier un événement");
        modifierBtn.addActionListener(e -> new org.example.EvenementModifier());

        JButton supprimerBtn = new JButton("Supprimer un événement");
        supprimerBtn.addActionListener(e -> new org.example.EvenementSupprimer());

        JButton rechercherBtn = new JButton("Rechercher un événement");
        rechercherBtn.addActionListener(e -> new org.example.EvenementRechercher());

        add(creerBtn);
        add(modifierBtn);
        add(supprimerBtn);
        add(rechercherBtn);

        setVisible(true);
    }
}
