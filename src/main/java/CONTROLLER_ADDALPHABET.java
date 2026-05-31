import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CONTROLLER_ADDALPHABET {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField charsAlphabet;

    @FXML
    private Button createAlphabetBut;

    @FXML
    private TextField nameAlphabet;

    @FXML
    private CheckBox punctuation;

    @FXML
    void createAlphabet(ActionEvent event) {
        addAlphabet();
    }
    private void addAlphabet() {
        String alphabet = charsAlphabet.getText();
        String name = nameAlphabet.getText();
        boolean punct = punctuation.isSelected();
        if (hasDuplicateCharacters(alphabet)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка дубликатов");
            alert.setHeaderText("Алфавит содержит повторяющиеся символы");
            alert.showAndWait();
            return;
        }
        boolean b= AlphabetsManeger.getInstance().addAlphabet(name,alphabet,punct);
        if(b){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Алфавит добавлен");
            alert.setHeaderText("Ваш алфавит "+name+" был добавлен");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Алфавит не был добавлен");
            alert.setHeaderText("Ваш алфавит уже создан или он является пустым");
            alert.showAndWait();
        }

    }
    private boolean hasDuplicateCharacters(String str) {
        Set<Character> chars = new HashSet<>();
        for (char c : str.toCharArray()) {
            if (!chars.add(c)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void initialize() {
        assert charsAlphabet != null : "fx:id=\"charsAlphabet\" was not injected: check your FXML file 'InputDialogAddAlphabet.fxml'.";
        assert createAlphabetBut != null : "fx:id=\"createAlphabetBut\" was not injected: check your FXML file 'InputDialogAddAlphabet.fxml'.";
        assert nameAlphabet != null : "fx:id=\"nameAlphabet\" was not injected: check your FXML file 'InputDialogAddAlphabet.fxml'.";
        assert punctuation != null : "fx:id=\"punctuation\" was not injected: check your FXML file 'InputDialogAddAlphabet.fxml'.";

    }

}
