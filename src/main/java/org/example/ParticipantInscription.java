package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ParticipantInscription extends JFrame {

    private JTextField idEvenementField;
    private JTextField nomParticipantField;
    private JTextField emailParticipantField;
    private JTextField idParticipantField;
    private JLabel messageLabel;

    public ParticipantInscription() {
        setTitle("Inscription Participant");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idEventLabel = new JLabel("ID Événement :");
        JLabel nomLabel = new JLabel("Nom :");
        JLabel emailLabel = new JLabel("Email :");
        JLabel idParticipantLabel = new JLabel("ID Participant :");

        idEvenementField = new JTextField(20);
        nomParticipantField = new JTextField(20);
        emailParticipantField = new JTextField(20);
        idParticipantField = new JTextField(20);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        JButton inscrireBtn = new JButton("Inscrire");
        inscrireBtn.addActionListener(this::inscrire);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.addActionListener(e -> System.exit(0));

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idEventLabel, gbc);
        gbc.gridx = 1;
        panel.add(idEvenementField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idParticipantLabel, gbc);
        gbc.gridx = 1;
        panel.add(idParticipantField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(nomLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomParticipantField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailParticipantField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridy = 5; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(inscrireBtn, gbc);
        gbc.gridx = 1;
        panel.add(quitterBtn, gbc);

        add(panel);
    }

    private void inscrire(ActionEvent e) {
        String idEvent = idEvenementField.getText().trim();
        String id = idParticipantField.getText().trim();
        String nom = nomParticipantField.getText().trim();
        String email = emailParticipantField.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || id.isEmpty()) {
            messageLabel.setText("Nom, email et ID sont obligatoires.");
            return;
        }

        try {
            Participant participant = new Participant(id, nom, email);

            Evenement evenement = GestionEvenements.getInstance().rechercherEvenement(idEvent);


            if (evenement == null) {
                messageLabel.setText("Événement non trouvé.");
                return;
            }

            boolean success = evenement.ajouterParticipant(participant);
            boolean sauvegardeReussie = GestionEvenements.getInstance().SauvegardeEvenement( GestionEvenements.getInstance().getEvenements(),"src/main/resources/evenements.json");
            if (success || sauvegardeReussie) {

                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Participant inscrit avec succès.");
                clearFields();
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Inscription impossible.");
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText("ID événement invalide.");
        } catch (CapaciteDepasseException ex) {
            messageLabel.setText("Capacité dépassée !");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void clearFields() {
        idEvenementField.setText("");
        idParticipantField.setText("");
        nomParticipantField.setText("");
        emailParticipantField.setText("");
    }

    // Pour tester indépendamment
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParticipantInscription frame = new ParticipantInscription();
            frame.setVisible(true);
        });
    }
}
