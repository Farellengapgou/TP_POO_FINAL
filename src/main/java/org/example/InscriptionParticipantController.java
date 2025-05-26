package org.example;



import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.GestionEvenements;

public class InscriptionParticipantController {

    @FXML private TextField idEvenementField;
    @FXML private TextField nomParticipantField;
    @FXML private TextField emailParticipantField;
    @FXML private TextField IdParticipantField;
    @FXML private Label messageLabel;

    @FXML
    private void inscrire() {
        try {
            String idEvent = idEvenementField.getText().trim();
            String nom = nomParticipantField.getText();
            String email = emailParticipantField.getText();
            String id = IdParticipantField.getText();

            if (nom.isEmpty() || email.isEmpty()) {
                messageLabel.setText("Nom et email et id du participant sont obligatoires.");
                return;
            }
            Participant Ainscrit = new Participant(id,nom,email);
             Evenement e = GestionEvenements.getInstance().rechercherEvenement(idEvent);
            boolean success = e.ajouterParticipant(Ainscrit);
            if (success) {
                messageLabel.setText("Participant inscrit avec succès.");
                clearFields();
            } else {
                messageLabel.setText("Événement non trouvé ou inscription impossible.");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("ID événement invalide.");
        } catch (CapaciteDepasseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void quitter() {
        System.exit(0);
    }

    private void clearFields() {
        idEvenementField.clear();
        nomParticipantField.clear();
        emailParticipantField.clear();
    }
}
