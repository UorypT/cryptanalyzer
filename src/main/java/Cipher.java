import javafx.scene.control.Alert;
import tools.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Cipher {
    private final Alphabet ALPHABET;
    private DictionaryManeger dictionaryManeger=DictionaryManeger.getInstance();
    private String fileName;
    private static Dictionary dictionary;

    public Cipher(Alphabet al) {
        if (al == null) {
            throw new IllegalArgumentException("Алфавит не найден");
        }
        ALPHABET = al;
//        DictionaryManeger.getInstance();
//        for (int i=0;i<DictionaryManeger.getInstance().Length();i++){
//            if(dictionaryManeger.getDictionaries(i).getNameAlphabet().equals(ALPHABET.getName())){
//                dictionary=dictionaryManeger.getDictionaries(i);
//            }
//        }
        fileName = "Dictionary_" + ALPHABET.getName() + ".json";

    }
    public String encrypt(String text, int key,boolean myFile) {
        String result;
        if(myFile){
            CONTROLLER_ENCRYPT controllerEncrypt = new CONTROLLER_ENCRYPT();
            String file;
            file=controllerEncrypt.pathInDirectory();
            result=process(text,key);
            inFileEncrypt(result,file);
//            addWord(text);

        }else {
            result =process(text,key);
            inFileEncrypt(result);
//            addWord(text);
        }
        return result;
    }


    public String decrypt(String text, int key,boolean myFile) {
        String result;
        if(myFile){
            CONTROLLER_ENCRYPT controllerEncrypt = new CONTROLLER_ENCRYPT();
            String file;
            file=controllerEncrypt.pathInDirectory();
            result=process(text,key);
            inFileDecrypt(result,file);

        }else {
            result =process(text,key);
            inFileDecrypt(result);
        }
        return result;
    }
    public String decryptByBruteForce(String text, int key) {
        String result;
        result=process(text,key);
        if (result.equals("_-_-Ошибка: символа нет в алфавите-_-_")) {
            return "_-_-Ошибка: символа нет в алфавите-_-_";
        }
        return result;
    }
    public String decryptByStatistical(String text, int key) {
        String result;
        result=process(text,key);
        if (result.equals("_-_-Ошибка: символа нет в алфавите-_-_")) {
            return "_-_-Ошибка: символа нет в алфавите-_-_";
        }
        return result;
    }
    private String process(String text, int key) {
        String a = text.toLowerCase();
        StringBuilder result = new StringBuilder();
        if(!ALPHABET.getName().equals("Русский")&&!ALPHABET.getName().equals("Английский")) {
            createFile(a);
        }
        char[] charArray = ALPHABET.getAlphabet();
        int alphabetLength = ALPHABET.Lengthget();

        for (int i = 0; i < a.length(); ++i) {
            char currentChar = a.charAt(i);
            int index = new String(charArray).indexOf(currentChar);
            if (index == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Символа нет в алфавите!");
                alert.setHeaderText("символа "+currentChar+" нет в алфавите");
                alert.showAndWait();
                return "_-_-Ошибка: символа нет в алфавите-_-_";
            } else {
                int value = (index + key) % alphabetLength;
                result.append(charArray[value]);
            }
        }
        return result.toString();
    }
    private void createFile(String text) {
        ObjectMapper mapper= new ObjectMapper();
        Path path = Paths.get("src", "main", "resources", "dictionaries", fileName);
        try {
            if(Files.exists(path)){
                for(int i = 3; i < dictionaryManeger.Length(); ++i) {
                    System.out.println(dictionaryManeger.Length());
                    if (Objects.equals(ALPHABET.getName(), dictionaryManeger.getDictionaries(i).getNameAlphabet())) {
                        String jsonContent = Files.readString(path);
                        Dictionary dictionary = mapper.readValue(jsonContent, Dictionary.class);
                        dictionary.addText(text);
                        mapper.writeValue(new File(path.toUri()),dictionary);
                        dictionaryManeger.addDictionary(dictionary);
                    }
                }
            }
            else {
                Dictionary dictionary=new Dictionary(ALPHABET,text);
                dictionary.addText(text);
                mapper.writeValue(new File(path.toUri()),dictionary);
                dictionaryManeger.addDictionary(dictionary);
            }

        } catch (Exception e){
            System.err.println("Ошибка загрузки файлов: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void addWord(String text) {
        dictionary.addText(text);
    }
    private void inFileDecrypt(String result) {
        FileManeger.writeFileDecrypt(result,ALPHABET);
    }
    private void inFileDecrypt(String result,String fileName) {
        FileManeger.writeFileDecrypt(fileName,result,ALPHABET);
    }
    private void inFileEncrypt(String result) {
        FileManeger.writeFileEncrypt(result,ALPHABET);
    }
    private void inFileEncrypt(String result,String fileName) {
        FileManeger.writeFileEncrypt(fileName,result,ALPHABET);
    }
}
