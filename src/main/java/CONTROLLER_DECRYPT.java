import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CONTROLLER_DECRYPT {
    private String path0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> KeyBut;

    @FXML
    private ComboBox<Integer> KeyBut1;

    @FXML
    private ComboBox<String> alphabetBut;

    @FXML
    private ComboBox<String> alphabetBut1;

    @FXML
    private Button decryptBut;

    @FXML
    private Button decryptBut1;

    @FXML
    private TextField filePathResultBut;

    @FXML
    private CheckBox pathInFileBut;

    @FXML
    private CheckBox pathInFileBut1;

    @FXML
    private TextField resultDecryptBut;

    @FXML
    private TextField resultDecryptBut1;

    @FXML
    private TextField textBut;

    @FXML
    private Button textInFileBut;

    @FXML
    void allAlphabet1() {
        if(alphabetBut1.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetBut1.getItems().add(alphabet.getName());
            }
            alphabetBut1.getItems().addAll();
        }
        KeyBut1.getItems().clear();
        keysAlphabet1(alphabetBut1.getValue());
    }
    void keysAlphabet1(String s) {
        if(KeyBut1.getItems().isEmpty()) {
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(int i=0;i<list.size();i++){
                if (list.get(i).getName().equals(s)) {
                    int lenght=list.get(i).Lengthget();
                    for(int j=0;j<lenght;j++){
                        KeyBut1.getItems().add(j+1);
                    }

                }
            }
        }
    }

    @FXML
    void allAlphabet() {
        if(alphabetBut.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetBut.getItems().add(alphabet.getName());
            }
            alphabetBut.getItems().addAll();
        }
        KeyBut.getItems().clear();
        keysAlphabet(alphabetBut.getValue());
    }
    void keysAlphabet(String s) {
        if(KeyBut.getItems().isEmpty()) {
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(int i=0;i<list.size();i++){
                if (list.get(i).getName().equals(s)) {
                    int lenght=list.get(i).Lengthget();
                    for(int j=0;j<lenght;j++){
                        KeyBut.getItems().add(j+1);
                    }

                }
            }
        }
    }

    @FXML
    void decrypt(ActionEvent event) {
        if(textBut.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Текст пустой!");
            alert.setHeaderText("прежде чем начать шифровку введите текст");
            alert.showAndWait();
            return;
        }

        if(alphabetBut.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Выберите алфавит!");
            alert.setHeaderText("прежде чем начать шифровку выберите алфавит");
            alert.showAndWait();
            return;
        }
        if(KeyBut.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Выберите ключ!");
            alert.setHeaderText("прежде чем начать шифровку выберите ключ");
            alert.showAndWait();
            return;
        }
        String name=alphabetBut.getValue();
        List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
        Cipher cipher = null;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getName().equals(name)) {
                cipher=new Cipher(list.get(i));
            }
        }
        resultDecryptBut.setText(cipher.decrypt(textBut.getText(),KeyBut.getValue(),pathInFileBut.isSelected()));
    }

    @FXML
    void decrypt1(ActionEvent event) {
        if(filePathResultBut.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Нет файла!");
            alert.setHeaderText("прежде чем начать шифровку выберите файл");
            alert.showAndWait();
            return;
        }
        if(alphabetBut1.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Выберите алфавит!");
            alert.setHeaderText("прежде чем начать шифровку выберите алфавит");
            alert.showAndWait();
            return;
        }
        if(KeyBut1.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Выберите ключ!");
            alert.setHeaderText("прежде чем начать шифровку выберите ключ");
            alert.showAndWait();
            return;
        }
        String name=alphabetBut1.getValue();
        List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
        Cipher cipher = null;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getName().equals(name)) {
                cipher=new Cipher(list.get(i));
            }
        }
        String selectedFile = path0;
        String result = "";
        try {
            result = Files.readString(Path.of(selectedFile));

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            filePathResultBut.setText("Ошибка чтения файла: " + e.getMessage());

        }

        resultDecryptBut1.setText(cipher.encrypt(result,KeyBut1.getValue(),pathInFileBut1.isSelected()));
    }

    @FXML
    public String textInFile() {
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
        fileChooser.setTitle("Выберите файл для чтения");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePathResultBut.setText(selectedFile.toString());
            path0= selectedFile.toString();
            return path0;
        }
        return null;
    }

    @FXML
    void initialize() {
        allAlphabet1();
        allAlphabet();
        assert KeyBut != null : "fx:id=\"KeyBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert KeyBut1 != null : "fx:id=\"KeyBut1\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert alphabetBut != null : "fx:id=\"alphabetBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert alphabetBut1 != null : "fx:id=\"alphabetBut1\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert decryptBut != null : "fx:id=\"decryptBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert decryptBut1 != null : "fx:id=\"decryptBut1\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert filePathResultBut != null : "fx:id=\"filePathResultBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert pathInFileBut != null : "fx:id=\"pathInFileBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert pathInFileBut1 != null : "fx:id=\"pathInFileBut1\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert resultDecryptBut != null : "fx:id=\"resultDecryptBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert resultDecryptBut1 != null : "fx:id=\"resultDecryptBut1\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert textBut != null : "fx:id=\"textBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";
        assert textInFileBut != null : "fx:id=\"textInFileBut\" was not injected: check your FXML file 'InputDialogDecrypt.fxml'.";

    }

}
