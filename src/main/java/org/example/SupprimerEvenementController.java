package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.GestionEvenements;

public class SupprimerEvenementController {

    @FXML private TextField idField;
    @FXML private Label messageLabel;

    @FXML
    private void supprimer() {
        try {
            String id = idField.getText();
            boolean result = GestionEvenements.getInstance().supprimerEvenement(id);
            if (result) {
                messageLabel.setText("Événement supprimé.");
            } else {
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("ID invalide.");
        }
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }
}
