package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class MenuParticipantController {

    @FXML private Button btnInscrire;
    @FXML private Button btnDesinscrire;

    @FXML
    private void initialize() {
        btnInscrire.setOnAction(e -> chargerFenetre("/views/inscrire_participant.fxml"));
        btnDesinscrire.setOnAction(e -> chargerFenetre("/views/desinscrire_participant.fxml"));
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
