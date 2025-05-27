package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EvenementRechercher extends JFrame {

    private JTextField idField;
    private JLabel messageLabel;
    private JTextArea detailsArea;

    public EvenementRechercher() {
        setTitle("Rechercher Événement");
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID de l'événement :");
        idField = new JTextField(20);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);

        detailsArea = new JTextArea(8, 30);
        detailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        JButton rechercherBtn = new JButton("Rechercher");
        rechercherBtn.addActionListener(this::rechercher);

        JButton quitterBtn = new JButton("Quitter");
        quitterBtn.addActionListener(e -> System.exit(0));

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridy = 2;
        panel.add(scrollPane, gbc);

        gbc.gridy = 3; gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(rechercherBtn, gbc);
        gbc.gridx = 1;
        panel.add(quitterBtn, gbc);

        add(panel);
    }

    private void rechercher(ActionEvent e) {
        String id = idField.getText().trim();
        try {
            Evenement ev = GestionEvenements.getInstance().rechercherEvenement(id);
            if (ev != null) {
                detailsArea.setText(ev.toString());
                messageLabel.setText("");
            } else {
                detailsArea.setText("");
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("ID invalide.");
            detailsArea.setText("");
        }
    }

    // Pour tester directement
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EvenementRechercher frame = new EvenementRechercher();
            frame.setVisible(true);
        });
    }
}
