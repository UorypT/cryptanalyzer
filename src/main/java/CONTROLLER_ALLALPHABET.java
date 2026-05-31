import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CONTROLLER_ALLALPHABET {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea panelAlphabet;

    @FXML
    void outputALLAlphabet() {
        AlphabetsManeger.getInstance().toString();
        panelAlphabet.appendText(AlphabetsManeger.getInstance().toString());
    }

    @FXML
    void initialize() {
        assert panelAlphabet != null : "fx:id=\"panelAlphabet\" was not injected: check your FXML file 'InputDialogAllAlphabet.fxml'.";
        outputALLAlphabet();
    }

}
