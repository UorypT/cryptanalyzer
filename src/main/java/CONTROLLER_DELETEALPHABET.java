import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class CONTROLLER_DELETEALPHABET {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> allAlphabetsBut;

    @FXML
    private Button deleteBut;

    @FXML
    void allAlphabets() {
        if(allAlphabetsBut.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet>list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                allAlphabetsBut.getItems().add(alphabet.getName());
            }
            allAlphabetsBut.getItems().addAll();
        }
    }

    @FXML
    void deleteAlphabets(ActionEvent event) {
        deleter();
    }
    private void deleter(){
        String name = allAlphabetsBut.getValue();
        if (name == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Сначала выберите алфавит из списка");
            alert.showAndWait();
            return;
        }
        if(name.equals("Русский")||name.equals("Английский")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Алфавит не может быть удалён");
            alert.setHeaderText("Вы не можете удалить базовый алфавит");
            alert.showAndWait();
            return;
        }

        allAlphabetsBut.getItems().remove(name);
        AlphabetsManeger.getInstance().deleteAlphabet(name);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Алфавит был удалён");
        alert.setHeaderText("Вы удалили алфавит "+name);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        allAlphabets();
        assert allAlphabetsBut != null : "fx:id=\"allAlphabetsBut\" was not injected: check your FXML file 'InputDialogDeleteAlphabet.fxml'.";
        assert deleteBut != null : "fx:id=\"deleteBut\" was not injected: check your FXML file 'InputDialogDeleteAlphabet.fxml'.";

    }

}
