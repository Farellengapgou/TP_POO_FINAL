package org.example;


import javax.swing.*;
import java.awt.*;

public class CreerEvenementForm extends JFrame {
    public CreerEvenementForm() {
        setTitle("Créer un Événement");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2));

        JTextField nomField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField lieuField = new JTextField();
        JTextField capaciteField = new JTextField();

        // Exemples simples
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nomField);
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Lieu:"));
        formPanel.add(lieuField);
        formPanel.add(new JLabel("Capacité:"));
        formPanel.add(capaciteField);

        JButton validerBtn = new JButton("Valider");
        validerBtn.addActionListener(e -> {
            // vérifications, enregistrement, appel à GestionEvenements
        });

        add(formPanel, BorderLayout.CENTER);
        add(validerBtn, BorderLayout.SOUTH);

        setVisible(true);
    }
}
