package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class EvenementModifier extends JFrame {

    private JTextField idField;
    private JTextField nomField;
    private JTextField lieuField;
    private JTextField capaciteField;
    private JLabel messageLabel;

    public EvenementModifier() {
        setTitle("Modifier Événement");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Layout simple
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Labels + champs
        JLabel idLabel = new JLabel("ID :");
        idField = new JTextField(20);

        JLabel nomLabel = new JLabel("Nom :");
        nomField = new JTextField(20);

        JLabel lieuLabel = new JLabel("Lieu :");
        lieuField = new JTextField(20);

        JLabel capaciteLabel = new JLabel("Capacité :");
        capaciteField = new JTextField(20);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        // Boutons
        JButton modifierBtn = new JButton("Modifier");
        modifierBtn.addActionListener(this::modifier);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.addActionListener(e -> System.exit(0));

        // Positionnement dans le GridBagLayout
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nomLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lieuLabel, gbc);
        gbc.gridx = 1;
        panel.add(lieuField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(capaciteLabel, gbc);
        gbc.gridx = 1;
        panel.add(capaciteField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(modifierBtn, gbc);

        gbc.gridx = 1;
        panel.add(quitterBtn, gbc);

        add(panel);
    }

    private void modifier(ActionEvent e) {
        try {
            String id = idField.getText().trim();
            String nom = nomField.getText().trim();
            String lieu = lieuField.getText().trim();
            int capacite = Integer.parseInt(capaciteField.getText().trim());

            boolean result = GestionEvenements.getInstance().modifierEvenement(id, nom, lieu, capacite);
            boolean sauvegardeReussie = GestionEvenements.getInstance().SauvegardeEvenement( GestionEvenements.getInstance().getEvenements(),"src/main/resources/evenements.json");
            GestionEvenements.getInstance().initialiserObservateursDepuisEvenements();
            if (result || sauvegardeReussie) {
                messageLabel.setForeground(new Color(0, 128, 0)); // vert
                messageLabel.setText("Événement modifié avec succès.");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("ID et capacité doivent être des nombres valides.");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Pour tester rapidement cette fenêtre indépendamment
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EvenementModifier frame = new EvenementModifier();
            frame.setVisible(true);
        });
    }
}
