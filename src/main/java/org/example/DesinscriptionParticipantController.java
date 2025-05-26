package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.GestionEvenements;
import org.example.Participant;

public class DesinscriptionParticipantController {

    @FXML private TextField idEvenementField;
    @FXML private TextField nomParticipantField;
    @FXML private TextField IdParticipantField;
    @FXML private Label messageLabel;

    @FXML
    private void desinscrire() {
        try {
            String idEvent = idEvenementField.getText();
            String nom = nomParticipantField.getText();
            String id = IdParticipantField.getText();

            if (nom.isEmpty()) {
                messageLabel.setText("Nom du participant est obligatoire.");
                return;
            }
            Evenement e = GestionEvenements.getInstance().rechercherEvenement(idEvent);
            Participant Adesincrir = new Participant();
            boolean success = false;
            if (e instanceof Concert){
            Adesincrir = Adesincrir.chercherParticipantParId(((Concert) e).getParticipants(),id);
             success = e.annuler(Adesincrir);
            }else if (e instanceof Conference){
                Adesincrir = Adesincrir.chercherParticipantParId(((Conference) e).getIntervenants(),id);
                success = e.annuler(Adesincrir);
            }

            if (success) {
                messageLabel.setText("Participant désinscrit avec succès.");
                clearFields();
            } else {
                messageLabel.setText("Événement ou participant non trouvé.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("ID événement invalide.");
        }
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }

    private void clearFields() {
        idEvenementField.clear();
        nomParticipantField.clear();
    }
}
