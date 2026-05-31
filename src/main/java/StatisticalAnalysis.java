import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticalAnalysis {
    private static HashMap<Character,Double> map=new HashMap<>();
    private final Alphabet alphabet;
    private int best;

    public StatisticalAnalysis(Alphabet alphabet) {
        this.alphabet=alphabet;
        map = getCharPercentages();
    }
    public String myFile(String encryptedText, Alphabet alphabet,boolean myFile){
        if(myFile){
            CONTROLLER_STATISTICAL controllerStatistical = new CONTROLLER_STATISTICAL();
            String file;
            file= controllerStatistical.pathInDirectory();
            if(file==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Выберите директорию!");
                alert.setHeaderText("Вы не выбрали нужную директорию для загрузки файла");
                alert.showAndWait();
                return null;
            }
            boolean b= charInAlphabet(encryptedText,alphabet);
            if(b){
                String result= decryptByStatistical(encryptedText);
                FileManeger.writeFileStatistical(file,result,alphabet);
                return result;
            }
            return "_-_-Ошибка: символа нет в алфавите-_-_";
        }else{
            boolean b= charInAlphabet(encryptedText,alphabet);
            if(b){
                String result= decryptByStatistical(encryptedText);
                FileManeger.writeFileStatistical(result,alphabet);
                return result;
            }
            return "_-_-Ошибка: символа нет в алфавите-_-_";
        }
    }
    private boolean charInAlphabet(String encryptedText,Alphabet alphabet){
        String a = encryptedText.toLowerCase();
        char[] charArray = alphabet.getAlphabet();

        for (int i = 0; i < a.length(); ++i) {
            char currentChar = a.charAt(i);
            int index = new String(charArray).indexOf(currentChar);
            if (index == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Символа нет в алфавите!");
                alert.setHeaderText("символа "+currentChar+" нет в алфавите");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }
    private HashMap<Character, Double> getCharPercentages() {
        Path path=Path.of("src", "main", "resources", "dictionaries", "Dictionary_"+alphabet.getName()+".json");
        Map<Character, Integer> charCount = new HashMap<>();
        int totalChars = 0;

        try {
            String content = Files.readString(path);
            Set<Character> alphabetSet = new HashSet<>();
            char[] alphabetArray = alphabet.getAlphabet();

            for (char c : alphabetArray) {
                alphabetSet.add(c);
            }

            for (char c : content.toCharArray()) {
                if (alphabetSet.contains(c)) {
                    charCount.put(c, charCount.getOrDefault(c, 0) + 1);
                    totalChars++;
                }
            }
            HashMap<Character, Double> freqMap = new HashMap<>();
            for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
                freqMap.put(entry.getKey(), (double) entry.getValue() / totalChars);
            }
            return freqMap;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
            return new HashMap<>();
        }
    }
    public static List<Character> getTopLanguageChars(int n) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<Character, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .filter(Character::isLetter)
                .limit(n)
                .collect(Collectors.toList());
    }
    public String decryptByStatistical(String encryptedText) {
        Cipher cipher = new Cipher(alphabet);
        encryptedText=encryptedText.toLowerCase();
        char[]c=alphabet.getAlphabet();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < alphabet.Lengthget(); i++) {
            int maxValueChar = 0;
            for (int j = 0; j < encryptedText.length(); j++) {

                if (encryptedText.charAt(j) == c[i]) {
                    maxValueChar++;
                }
            }
            map.put(c[i], maxValueChar);
        }

        Map<Character, Integer> clearMap = new HashMap<>();
        for (int i = 0; i < alphabet.Lengthget(); i++) {
            int j =map.get(alphabet.getAlphabet()[i]);

            if(j>0){
                clearMap.put(alphabet.getAlphabet()[i],j);
            }
        }
        String bestLine = "";
        int maxWordsFound = 0;
        int bestKey = -1;
        List<Character> sortedChars = clearMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<String> words = Dictionary.dictionaryInString(alphabet);
        int key;
        List<Character> topLanguageChars = getTopLanguageChars(15);
        for (Character languageChar : topLanguageChars) {
            for (Character aChar : sortedChars) {
                int sortedChar = alphabet.getCharIndex(aChar);
                int topLanguageChar = alphabet.getCharIndex(languageChar);
                key = (sortedChar - topLanguageChar + alphabet.Lengthget()) % alphabet.Lengthget();

                String st = cipher.decryptByStatistical(encryptedText, key);
                String[] sArray = st.split("\\s+");
                int matchingWords = 0;
                for (String word : sArray) {
                    if (word.hashCode() == sArray[0].hashCode() && (words.contains(word))) {
                        matchingWords++;
                    }
                }
                if (matchingWords > maxWordsFound) {
                    maxWordsFound = matchingWords;
                    bestLine = st;
                    bestKey = key;
                }
            }
            if(bestKey != -1){
                break;
            }
        }
        if(bestKey==-1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка поиска!");
            alert.setHeaderText("statistical analysis не смог найти слова из вашего текста!");
            alert.showAndWait();
            return "_-_-Ошибка: слова не найдены-_-_";
        }
        best=bestKey;
        return bestLine;
    }
    public int getBest() {
        return best;
    }
}
