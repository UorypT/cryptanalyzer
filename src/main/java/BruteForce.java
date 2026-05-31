import javafx.scene.control.Alert;

import java.util.HashSet;
import java.util.Set;

public class BruteForce {
    private static int best;
    public static String myFile(String encryptedText, Alphabet alphabet,boolean myFile){
        if(myFile){
            CONTROLLER_BRUTEFORCE controllerBruteforce = new CONTROLLER_BRUTEFORCE();
            String file;
            file= controllerBruteforce.pathInDirectory();
            String result= decryptByBruteForce(encryptedText,alphabet);
            FileManeger.writeFileBruteForce(file,result,alphabet);
            return result;
        }else{
            String result= decryptByBruteForce(encryptedText,alphabet);
            FileManeger.writeFileBruteForce(result,alphabet);
            return result;
        }
    }
    private static String decryptByBruteForce(String encryptedText, Alphabet alphabet) {
        Cipher cipher = new Cipher(alphabet);
        Set<String> dictionary = new HashSet<>(Dictionary.dictionaryInString(alphabet));
        String bestLine = "";
        int maxWordsFound = 0;
        int bestKey = -1;

        int alphabetSize = alphabet.Lengthget();

        for (int key = 0; key < alphabetSize; key++) {
            String decrypted = cipher.decryptByBruteForce(encryptedText, key);
            if (decrypted.equals("_-_-Ошибка: символа нет в алфавите-_-_")) {
                return "_-_-Ошибка: символа нет в алфавите-_-_";
            }
            String[] wordsInLine = decrypted.toLowerCase().split("\\s+");
            int matchingWords = 0;
            for (String word : wordsInLine) {
                if (!word.isEmpty() && dictionary.contains(word)) {
                    matchingWords++;
                }
            }

            if (matchingWords > maxWordsFound) {
                maxWordsFound = matchingWords;
                bestLine = decrypted;
                bestKey = key;
                if (matchingWords == wordsInLine.length) {
                    break;
                }
            }
        }
        if(bestKey==-1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка поиска!");
            alert.setHeaderText("brute force не смог найти слова из вашего текста!");
            alert.showAndWait();
            return "_-_-Ошибка: слова не найдены-_-_";
        }
        best=bestKey;
        return bestLine;
    }

    public static int getBest() {
        return best;
    }
}
