import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CONTROLLER_STATISTICAL {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button StatisticalAnalysisTextBut;

    @FXML
    private Button StatisticalAnalysisTextBut1;

    @FXML
    private ComboBox<String> alphabetBut;

    @FXML
    private ComboBox<String> alphabetBut1;

    @FXML
    private Label bestKeyLabel;

    @FXML
    private Label bestKeyLabel1;

    @FXML
    private TextField filePathResultBut;

    @FXML
    private CheckBox pathInFileBut;

    @FXML
    private CheckBox pathInFileBut1;

    @FXML
    private TextField resultStatisticalAnalysisBut;

    @FXML
    private TextField resultStatisticalAnalysisBut1;

    @FXML
    private TextField textBut;

    @FXML
    private Button textInFileBut;

    @FXML
    void StatisticalAnalysisText(ActionEvent event) {
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
        String name=alphabetBut.getValue();
        if(!name.equals("Русский")&&!name.equals("Английский")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Созданный алфавит!");
            alert.setHeaderText("StatisticalAnalysis с созданным алфавитом не работает!");
            alert.showAndWait();
            return;
        }
        List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
        Alphabet alphabet = null;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getName().equals(name)) {
                alphabet=list.get(i);
            }
        }
        StatisticalAnalysis statisticalAnalysis=new StatisticalAnalysis(alphabet);
        resultStatisticalAnalysisBut.setText(statisticalAnalysis.myFile(textBut.getText(),alphabet,pathInFileBut.isSelected()));
        bestKeyLabel.setText(String.valueOf(statisticalAnalysis.getBest()));
    }

    @FXML
    void StatisticalAnalysisText1(ActionEvent event) {
        if(filePathResultBut.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Текст пустой!");
            alert.setHeaderText("прежде чем начать шифровку введите текст");
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
        String name=alphabetBut1.getValue();
        if(!name.equals("Русский")&&!name.equals("Английский")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Созданный алфавит!");
            alert.setHeaderText("StatisticalAnalysis с созданным алфавитом не работает!");
            alert.showAndWait();
            return;
        }
        List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
        Alphabet alphabet = null;
        for(int i=0;i<list.size();i++){
            if (list.get(i).getName().equals(name)) {
                alphabet=list.get(i);
            }
        }
        StatisticalAnalysis statisticalAnalysis=new StatisticalAnalysis(alphabet);
        resultStatisticalAnalysisBut1.setText(statisticalAnalysis.myFile(FileManeger.readFile(filePathResultBut.getText()),alphabet,pathInFileBut1.isSelected()));

        bestKeyLabel1.setText(String.valueOf(statisticalAnalysis.getBest()));
    }

    @FXML
    void allAlphabets() {
        if(alphabetBut.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetBut.getItems().add(alphabet.getName());
            }
            alphabetBut.getItems().addAll();
        }
    }

    @FXML
    void allAlphabets1() {
        if(alphabetBut1.getItems().isEmpty()) {
            AlphabetsManeger.getInstance().toString();
            List<Alphabet> list=AlphabetsManeger.getInstance().getAllAlphabets();
            for(Alphabet alphabet:list){
                alphabetBut1.getItems().add(alphabet.getName());
            }
            alphabetBut1.getItems().addAll();
        }
    }

    @FXML
    String textInFile() {
        Stage stage=new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
        fileChooser.setTitle("Выберите файл для чтения");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePathResultBut.setText(selectedFile.toString());
            String s = selectedFile.toString();
            return s;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Выберите файл!");
        alert.setHeaderText("Вы не выбрали нужный файл для чтения");
        alert.showAndWait();
        return null;
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
    void initialize() {
        allAlphabets();
        allAlphabets1();
        assert StatisticalAnalysisTextBut != null : "fx:id=\"StatisticalAnalysisTextBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert StatisticalAnalysisTextBut1 != null : "fx:id=\"StatisticalAnalysisTextBut1\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert alphabetBut != null : "fx:id=\"alphabetBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert alphabetBut1 != null : "fx:id=\"alphabetBut1\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert bestKeyLabel != null : "fx:id=\"bestKeyLabel\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert bestKeyLabel1 != null : "fx:id=\"bestKeyLabel1\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert filePathResultBut != null : "fx:id=\"filePathResultBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert pathInFileBut != null : "fx:id=\"pathInFileBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert pathInFileBut1 != null : "fx:id=\"pathInFileBut1\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert resultStatisticalAnalysisBut != null : "fx:id=\"resultStatisticalAnalysisBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert resultStatisticalAnalysisBut1 != null : "fx:id=\"resultStatisticalAnalysisBut1\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert textBut != null : "fx:id=\"textBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";
        assert textInFileBut != null : "fx:id=\"textInFileBut\" was not injected: check your FXML file 'InputDialogStatistical.fxml'.";

    }

}
