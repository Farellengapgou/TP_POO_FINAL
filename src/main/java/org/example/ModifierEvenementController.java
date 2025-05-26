package org.example;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.GestionEvenements;

public class ModifierEvenementController {

    @FXML private TextField idField;
    @FXML private TextField nomField;
    @FXML private TextField lieuField;
    @FXML private TextField capaciteField;
    @FXML private Label messageLabel;

    @FXML
    private void modifier() {
        try {
            String id = idField.getText();
            String nom = nomField.getText();
            String lieu = lieuField.getText();
            int capacite = Integer.parseInt(capaciteField.getText());

            boolean result = GestionEvenements.getInstance().modifierEvenement(id, nom, lieu, capacite);
            if (result) {
                messageLabel.setText("Événement modifié avec succès.");
            } else {
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("ID et capacité doivent être des nombres valides.");
        }
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }
}
