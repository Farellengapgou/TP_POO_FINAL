package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.Concert;
import org.example.Conference;
import org.example.Organisateur;
import org.example.Evenement;
import org.example.GestionEvenements;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreerEvenementController {

    @FXML private TextField orgIdField;
    @FXML private TextField orgNomField;
    @FXML private TextField orgEmailField;

    @FXML private ComboBox<String> typeEvenementComboBox;

    @FXML private TextField evenementIdField;
    @FXML private TextField nomEvenementField;

    @FXML private DatePicker datePicker;

    @FXML private ComboBox<Integer> heureComboBox;
    @FXML private ComboBox<Integer> minuteComboBox;

    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;

    // Champs spécifiques au concert
    @FXML private TextField artisteField;
    @FXML private TextField genreMusicalField;

    // Champs spécifiques à la conférence
    @FXML private TextField themeField;

    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        typeEvenementComboBox.getItems().addAll("Concert", "Conference");

        for (int i=0; i<=23; i++) heureComboBox.getItems().add(i);
        for (int i=0; i<=59; i++) minuteComboBox.getItems().add(i);

        heureComboBox.setValue(12);
        minuteComboBox.setValue(0);

        // Cacher les champs spécifiques au départ
        artisteField.setVisible(false);
        genreMusicalField.setVisible(false);
        themeField.setVisible(false);

        typeEvenementComboBox.setOnAction(e -> {
            String type = typeEvenementComboBox.getValue();
            artisteField.setVisible("Concert".equals(type));
            genreMusicalField.setVisible("Concert".equals(type));
            themeField.setVisible("Conference".equals(type));
        });
    }

    @FXML
    public void creerEvenement() {
        messageLabel.setText("");
        // Valider infos organisateur
        String orgId = orgIdField.getText();
        String orgNom = orgNomField.getText();
        String orgEmail = orgEmailField.getText();

        if (orgId.isEmpty() || orgNom.isEmpty() || orgEmail.isEmpty() || !orgEmail.matches(".+@.+\\..+")) {
            messageLabel.setText("Veuillez entrer des infos organisateur valides.");
            return;
        }

        // Créer l’organisateur
        Organisateur orga = new Organisateur(orgId, orgNom, orgEmail);

        // Valider infos événement
        String evenementId = evenementIdField.getText();
        String nomEvenement = nomEvenementField.getText();
        LocalDate date = datePicker.getValue();
        Integer heure = heureComboBox.getValue();
        Integer minute = minuteComboBox.getValue();
        String lieu = lieuField.getText();
        int capacite;

        try {
            capacite = Integer.parseInt(capaciteField.getText());
            if (capacite <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Capacité doit être un nombre positif.");
            return;
        }

        if (evenementId.isEmpty() || nomEvenement.isEmpty() || date == null || lieu.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        LocalDateTime dateTime = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), heure, minute);

        Evenement evenement;

        if ("Concert".equals(typeEvenementComboBox.getValue())) {
            String artiste = artisteField.getText();
            String genre = genreMusicalField.getText();
            if (artiste.isEmpty() || genre.isEmpty()) {
                messageLabel.setText("Veuillez remplir les infos spécifiques du concert.");
                return;
            }
            evenement = new Concert(evenementId, nomEvenement, dateTime, lieu, capacite, artiste, genre);
        } else if ("Conference".equals(typeEvenementComboBox.getValue())) {
            String theme = themeField.getText();
            if (theme.isEmpty()) {
                messageLabel.setText("Veuillez remplir le thème de la conférence.");
                return;
            }
            evenement = new Conference(evenementId, nomEvenement, dateTime, lieu, capacite, theme);
        } else {
            messageLabel.setText("Veuillez sélectionner un type d'événement.");
            return;
        }

        // Ajout automatique de l’organisateur comme participant
        try {
            evenement.ajouterParticipant(orga);
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout de l'organisateur comme participant.");
            return;
        }

        // Ajouter à la liste de l’organisateur
        orga.ajouterEvenementList(evenement);

        // Sauvegarde dans GestionEvenements
        GestionEvenements.getInstance().ajouterEvenement(evenement);

        // Sauvegarder dans fichier JSON
        GestionEvenements.getInstance().SauvegardeEvenement("src/main/resources/evenements.json");

        messageLabel.setText("Événement créé avec succès !");
        clearForm();
    }

    private void clearForm() {
        evenementIdField.clear();
        nomEvenementField.clear();
        datePicker.setValue(null);
        heureComboBox.setValue(12);
        minuteComboBox.setValue(0);
        lieuField.clear();
        capaciteField.clear();
        artisteField.clear();
        genreMusicalField.clear();
        themeField.clear();
        orgIdField.clear();
        orgNomField.clear();
        orgEmailField.clear();
        typeEvenementComboBox.setValue(null);
        artisteField.setVisible(false);
        genreMusicalField.setVisible(false);
        themeField.setVisible(false);
    }

    public void quitter(ActionEvent actionEvent) {

    }
}
