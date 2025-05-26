package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class MenuEvenementController {

    @FXML private Button btnCreer;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnRechercher;

    @FXML
    private void initialize() {
        btnCreer.setOnAction(e -> chargerFenetre("/views/creer_evenement.fxml"));
        btnModifier.setOnAction(e -> chargerFenetre("/views/modifier_evenement.fxml"));
        btnSupprimer.setOnAction(e -> chargerFenetre("/views/supprimer_evenement.fxml"));
        btnRechercher.setOnAction(e -> chargerFenetre("/views/rechercher_evenement.fxml"));
    }

    private void chargerFenetre(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// MenuParticipantController.java
