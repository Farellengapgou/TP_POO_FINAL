package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.Evenement;
import org.example.GestionEvenements;

public class RechercherEvenementController {

    @FXML private TextField idField;
    @FXML private Label messageLabel;
    @FXML private TextArea detailsArea;

    @FXML
    private void rechercher() {
        try {
            String id = idField.getText();
            Evenement ev = GestionEvenements.getInstance().rechercherEvenement(id);
            if (ev != null) {
                detailsArea.setText(ev.toString());
                messageLabel.setText("");
            } else {
                detailsArea.clear();
                messageLabel.setText("Événement non trouvé.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("ID invalide.");
            detailsArea.clear();
        }
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }
}
