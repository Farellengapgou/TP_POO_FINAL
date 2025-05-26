package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EvenementSupprimer extends JFrame {

    private JTextField idField;
    private JLabel messageLabel;

    public EvenementSupprimer() {
        setTitle("Supprimer Événement");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel = new JLabel("ID de l'événement :");
        idField = new JTextField(15);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        JButton supprimerBtn = new JButton("Supprimer");
        supprimerBtn.addActionListener(this::supprimer);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.addActionListener(e -> System.exit(0));

        // Positionnement
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(supprimerBtn, gbc);
        gbc.gridx = 1;
        panel.add(quitterBtn, gbc);

        add(panel);
    }

    private void supprimer(ActionEvent e) {
        try {
            String id = idField.getText().trim();
            boolean result = GestionEvenements.getInstance().supprimerEvenement(id);
            if (result) {
                messageLabel.setForeground(new Color(0, 128, 0)); // Vert
                messageLabel.setText("Événement supprimé.");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("ID invalide.");
        }
    }

    // Pour tester directement
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EvenementSupprimer frame = new EvenementSupprimer();
            frame.setVisible(true);
        });
    }
}
