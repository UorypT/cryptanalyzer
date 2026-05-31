import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CONTROLLER_ENCRYPT {
    private String path0;

    @FXML
    private ComboBox<Integer> KeyBut;

    @FXML
    private ComboBox<Integer> KeyBut1;

    @FXML
    private ComboBox<String> alphabetFileBut;

    @FXML
    private ComboBox<String> alphabetTextBut;

    @FXML
    private Button encryptedFileBut;

    @FXML
    private Button encryptedTextBut;

    @FXML
    private TextField fileEncryptedBut;

    @FXML
    private TextField filePathResultBut;

    @FXML
    private CheckBox pathInFileBut;

    @FXML
    private CheckBox pathInFileBut1;

    @FXML
    private TextField textBut;

    @FXML
    private TextField textEncryptedBut;

    @FXML
    void allFileAlphabet() {
        if(alphabetFileBut.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetFileBut.getItems().add(alphabet.getName());
            }
            alphabetFileBut.getItems().addAll();
        }
        KeyBut1.getItems().clear();
        keysAlphabet1(alphabetFileBut.getValue());
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
    void allTextAlphabets() {
        if(alphabetTextBut.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetTextBut.getItems().add(alphabet.getName());
            }
            alphabetTextBut.getItems().addAll();
        }
        KeyBut.getItems().clear();
        keysAlphabet(alphabetTextBut.getValue());

    }

    @FXML
    void encryptedFileBut(ActionEvent event) {
        if(filePathResultBut.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Нет файла!");
            alert.setHeaderText("прежде чем начать шифровку выберите файл");
            alert.showAndWait();
            return;
        }
        if(alphabetFileBut.getValue()==null){
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
        String name=alphabetFileBut.getValue();
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

        fileEncryptedBut.setText(cipher.encrypt(result,KeyBut1.getValue(),pathInFileBut1.isSelected()));
    }

    @FXML
    void encryptedTextBut(ActionEvent event) {
        if(textBut.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Текст пустой!");
            alert.setHeaderText("прежде чем начать шифровку введите текст");
            alert.showAndWait();
            return;
        }

        if(alphabetTextBut.getValue()==null){
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
        String name=alphabetTextBut.getValue();
        List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
        Cipher cipher = null;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getName().equals(name)) {
                cipher=new Cipher(list.get(i));
            }
        }
        textEncryptedBut.setText(cipher.encrypt(textBut.getText(),KeyBut.getValue(),pathInFileBut.isSelected()));
    }
    public String pathInDirectory() {
        Stage stage=new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите директорию для отправки файла");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDir = directoryChooser.showDialog(stage);
        if (selectedDir != null) {
            return selectedDir.toString();
        }
        return null;
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
        allTextAlphabets();
        allFileAlphabet();
        assert alphabetFileBut != null : "fx:id=\"alphabetFileBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert alphabetTextBut != null : "fx:id=\"alphabetTextBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert encryptedFileBut != null : "fx:id=\"encryptedFileBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert encryptedTextBut != null : "fx:id=\"encryptedTextBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert fileEncryptedBut != null : "fx:id=\"fileEncryptedBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert textBut != null : "fx:id=\"textBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";
        assert textEncryptedBut != null : "fx:id=\"textEncryptedBut\" was not injected: check your FXML file 'InputDialogEncrypt.fxml'.";

    }

}
