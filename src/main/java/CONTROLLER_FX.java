import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CONTROLLER_FX {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane contentPane;

    @FXML
    void addAlphabet(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogAddAlphabet.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void allAlphebet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogAllAlphabet.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bruteForce(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogBruteForce.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void decrypt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogDecrypt.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteAlphabet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogDeleteAlphabet.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void encrypt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogEncrypt.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitProgect(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void Statistical(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InputDialogStatistical.fxml"));
            Parent newContent = loader.load();
            contentPane.setCenter(newContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
