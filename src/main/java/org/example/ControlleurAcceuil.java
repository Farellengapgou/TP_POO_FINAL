package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControlleurAcceuil {


        public void ouvrirMenuEvenement(ActionEvent event) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu_evenement.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }

        public void ouvrirMenuParticipant(ActionEvent event) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu_participant.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }

        public void quitter(ActionEvent event) {
            System.exit(0);
        }


}
