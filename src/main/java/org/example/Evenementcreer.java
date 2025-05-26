package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Evenementcreer extends JFrame {

    private JTextField orgIdField = new JTextField(15);
    private JTextField orgNomField = new JTextField(15);
    private JTextField orgEmailField = new JTextField(15);

    private JComboBox<String> typeEvenementComboBox = new JComboBox<>(new String[]{"Concert", "Conference"});

    private JTextField evenementIdField = new JTextField(15);
    private JTextField nomEvenementField = new JTextField(15);
    private JSpinner dateSpinner;
    private JComboBox<Integer> heureComboBox = new JComboBox<>();
    private JComboBox<Integer> minuteComboBox = new JComboBox<>();
    private JTextField lieuField = new JTextField(15);
    private JTextField capaciteField = new JTextField(5);

    private JLabel artisteLabel = new JLabel("Artiste (Concert):");
    private JTextField artisteField = new JTextField(15);

    private JLabel genreLabel = new JLabel("Genre Musical:");
    private JTextField genreMusicalField = new JTextField(15);

    private JLabel themeLabel = new JLabel("Thème (Conférence):");
    private JTextField themeField = new JTextField(15);

    private JLabel messageLabel = new JLabel();

    public Evenementcreer() {
        setTitle("Création d'Événement");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        addLabelAndField("ID Organisateur:", orgIdField, gbc, y++);
        addLabelAndField("Nom Organisateur:", orgNomField, gbc, y++);
        addLabelAndField("Email Organisateur:", orgEmailField, gbc, y++);

        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Type d'événement:"), gbc);
        gbc.gridx = 1; add(typeEvenementComboBox, gbc); y++;

        addLabelAndField("ID Événement:", evenementIdField, gbc, y++);
        addLabelAndField("Nom Événement:", nomEvenementField, gbc, y++);

        // Date picker
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Date de l'événement:"), gbc);
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        gbc.gridx = 1; add(dateSpinner, gbc); y++;

        // Heure
        for (int i = 0; i < 24; i++) heureComboBox.addItem(i);
        for (int i = 0; i < 60; i++) minuteComboBox.addItem(i);

        gbc.gridx = 0; gbc.gridy = y; add(new JLabel("Heure:"), gbc);
        JPanel timePanel = new JPanel();
        timePanel.add(heureComboBox); timePanel.add(new JLabel("h"));
        timePanel.add(minuteComboBox); timePanel.add(new JLabel("min"));
        gbc.gridx = 1; add(timePanel, gbc); y++;

        addLabelAndField("Lieu:", lieuField, gbc, y++);
        addLabelAndField("Capacité:", capaciteField, gbc, y++);

        // Champs spécifiques (ajoutés même s'ils sont cachés au départ)
        gbc.gridx = 0; gbc.gridy = y; add(artisteLabel, gbc);
        gbc.gridx = 1; add(artisteField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(genreLabel, gbc);
        gbc.gridx = 1; add(genreMusicalField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(themeLabel, gbc);
        gbc.gridx = 1; add(themeField, gbc); y++;

        // On les cache au départ
        artisteLabel.setVisible(false);
        artisteField.setVisible(false);
        genreLabel.setVisible(false);
        genreMusicalField.setVisible(false);
        themeLabel.setVisible(false);
        themeField.setVisible(false);

        typeEvenementComboBox.addActionListener(e -> toggleSpecificFields());

        JButton creerBtn = new JButton("Créer Événement");
        creerBtn.addActionListener(e -> creerEvenement());

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        add(creerBtn, gbc); y++;

        gbc.gridy = y; messageLabel.setForeground(Color.BLUE);
        add(messageLabel, gbc);

        setVisible(true);
    }

    private void toggleSpecificFields() {
        boolean isConcert = "Concert".equals(typeEvenementComboBox.getSelectedItem());
        boolean isConference = "Conference".equals(typeEvenementComboBox.getSelectedItem());

        artisteLabel.setVisible(isConcert);
        artisteField.setVisible(isConcert);
        genreLabel.setVisible(isConcert);
        genreMusicalField.setVisible(isConcert);

        themeLabel.setVisible(isConference);
        themeField.setVisible(isConference);

        revalidate();
        repaint();
    }

    private void addLabelAndField(String label, JComponent field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0; gbc.gridy = y; add(new JLabel(label), gbc);
        gbc.gridx = 1; add(field, gbc);
    }

    private void creerEvenement() {
        messageLabel.setText("");

        String orgId = orgIdField.getText().trim();
        String orgNom = orgNomField.getText().trim();
        String orgEmail = orgEmailField.getText().trim();
        if (orgId.isEmpty() || orgNom.isEmpty() || !orgEmail.matches(".+@.+\\..+")) {
            messageLabel.setText("Infos organisateur invalides.");
            messageLabel.setForeground(Color.RED);
            return;
        }

        String evenementId = evenementIdField.getText().trim();
        String nomEvenement = nomEvenementField.getText().trim();
        Date selectedDate = (Date) dateSpinner.getValue();
        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int heure = (int) heureComboBox.getSelectedItem();
        int minute = (int) minuteComboBox.getSelectedItem();

        String lieu = lieuField.getText().trim();
        int capacite;
        try {
            capacite = Integer.parseInt(capaciteField.getText());
            if (capacite <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            messageLabel.setText("Capacité invalide.");
            messageLabel.setForeground(Color.RED);
            return;
        }

        if (evenementId.isEmpty() || nomEvenement.isEmpty() || lieu.isEmpty()) {
            messageLabel.setText("Champs obligatoires manquants.");
            messageLabel.setForeground(Color.RED);
            return;
        }

        // Vérifier si un événement avec cet ID existe déjà



        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), heure, minute);

        Evenement evenement;
        String type = (String) typeEvenementComboBox.getSelectedItem();

        if ("Concert".equals(type)) {
            String artiste = artisteField.getText().trim();
            String genre = genreMusicalField.getText().trim();
            if (artiste.isEmpty() || genre.isEmpty()) {
                messageLabel.setText("Champs concert manquants.");
                messageLabel.setForeground(Color.RED);
                return;
            }
            evenement = new Concert(evenementId, nomEvenement, dateTime, lieu, capacite, artiste, genre);
        } else if ("Conference".equals(type)) {
            String theme = themeField.getText().trim();
            if (theme.isEmpty()) {
                messageLabel.setText("Thème manquant.");
                messageLabel.setForeground(Color.RED);
                return;
            }
            evenement = new Conference(evenementId, nomEvenement, dateTime, lieu, capacite, theme);
        } else {
            messageLabel.setText("Type d'événement invalide.");
            messageLabel.setForeground(Color.RED);
            return;
        }

        Organisateur organisateur = new Organisateur(orgId, orgNom, orgEmail);

        try {
            evenement.ajouterParticipant(organisateur);
            organisateur.ajouterEvenementList(evenement);

            // Ajouter l'événement au gestionnaire
            GestionEvenements.getInstance().ajouterEvenement(evenement);

            // Sauvegarder dans le fichier JSON
            boolean sauvegardeReussie = GestionEvenements.getInstance().SauvegardeEvenement("src/main/resources/evenements.json");

            if (sauvegardeReussie) {
                messageLabel.setText("Événement créé et sauvegardé avec succès !");
                messageLabel.setForeground(Color.GREEN);
                // Réinitialiser les champs après création réussie
                clearFields();
            } else {
                messageLabel.setText("Événement créé mais erreur lors de la sauvegarde.");
                messageLabel.setForeground(Color.ORANGE);
            }

        } catch (CapaciteDepasseException e) {
            messageLabel.setText("Capacité maximale dépassée pour cet événement.");
            messageLabel.setForeground(Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la création : " + e.getMessage());
            messageLabel.setForeground(Color.RED);
        }
    }

    private void clearFields() {
        orgIdField.setText("");
        orgNomField.setText("");
        orgEmailField.setText("");
        evenementIdField.setText("");
        nomEvenementField.setText("");
        lieuField.setText("");
        capaciteField.setText("");
        artisteField.setText("");
        genreMusicalField.setText("");
        themeField.setText("");
        dateSpinner.setValue(new Date());
        heureComboBox.setSelectedIndex(0);
        minuteComboBox.setSelectedIndex(0);
        typeEvenementComboBox.setSelectedIndex(0);
        toggleSpecificFields();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Evenementcreer::new);
    }
}