package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ParticipantNonInscription extends JFrame {

    private JTextField idEvenementField;
    private JTextField nomParticipantField;
    private JTextField idParticipantField;
    private JLabel messageLabel;

    public ParticipantNonInscription() {
        setTitle("Désinscription d'un participant");
        setSize(400, 300);
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
        JLabel nomLabel = new JLabel("Nom Participant :");
        JLabel idParticipantLabel = new JLabel("ID Participant :");

        idEvenementField = new JTextField(20);
        nomParticipantField = new JTextField(20);
        idParticipantField = new JTextField(20);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        JButton desinscrireBtn = new JButton("Désinscrire");
        desinscrireBtn.addActionListener(this::desinscrire);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.addActionListener(e -> System.exit(0));

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idEventLabel, gbc);
        gbc.gridx = 1;
        panel.add(idEvenementField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(nomLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomParticipantField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(idParticipantLabel, gbc);
        gbc.gridx = 1;
        panel.add(idParticipantField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridy = 4; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(desinscrireBtn, gbc);
        gbc.gridx = 1;
        panel.add(quitterBtn, gbc);

        add(panel);
    }

    private void desinscrire(ActionEvent e) {
        String idEvent = idEvenementField.getText().trim();
        String nom = nomParticipantField.getText().trim();
        String id = idParticipantField.getText().trim();

        if (nom.isEmpty()) {
            messageLabel.setText("Nom du participant est obligatoire.");
            return;
        }

        try {
            Evenement evenement = GestionEvenements.getInstance().rechercherEvenement(idEvent);
            Participant adesinscrire = new Participant();
            boolean success = false;

            if (evenement instanceof Concert) {
                adesinscrire = adesinscrire.chercherParticipantParId(((Concert) evenement).getParticipants(), id);
                success = evenement.annuler(adesinscrire);
            } else if (evenement instanceof Conference) {
                adesinscrire = adesinscrire.chercherParticipantParId(((Conference) evenement).getIntervenants(), id);
                success = evenement.annuler(adesinscrire);
            }

            if (success) {
                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Participant désinscrit avec succès.");
                clearFields();
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Événement ou participant non trouvé.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("ID événement invalide.");
        }
    }

    private void clearFields() {
        idEvenementField.setText("");
        nomParticipantField.setText("");
        idParticipantField.setText("");
    }

    // Pour tester
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParticipantNonInscription frame = new ParticipantNonInscription();
            frame.setVisible(true);
        });
    }
}
