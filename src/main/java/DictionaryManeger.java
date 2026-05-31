import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DictionaryManeger {
    private static ArrayList<Dictionary> dictionaries=new ArrayList<>();
    private static DictionaryManeger dictionaryManeger;
    private DictionaryManeger() {
        englishOrRussian();
    }
    private static void createDictionariesDirectory() {
        Path dictDir = Paths.get("src", "main", "resources", "dictionaries");
        try {
            Files.createDirectories(dictDir);
        } catch (IOException e) {
            System.err.println("Ошибка создания директории: " + e.getMessage());
        }
    }
    public static DictionaryManeger getInstance() {
        createDictionariesDirectory();
        if (dictionaryManeger == null) {
            dictionaryManeger = new DictionaryManeger();
        }
        return dictionaryManeger;
    }
    private void englishOrRussian() {
        if(!Files.exists(Paths.get("src", "main", "resources", "dictionaries","Dictionary_"+ AlphabetsManeger.getInstance().getAlphabets(1).getName()+".json"))){
            baseDictionaries("russian.txt",1);
        }
        if(!Files.exists(Paths.get("src", "main", "resources", "dictionaries","Dictionary_"+AlphabetsManeger.getInstance().getAlphabets(2).getName()+".json"))){
            baseDictionaries("english.txt",2);
        }
    }
    private void baseDictionaries(String fileLanguage, int key) {
        String fileName = Paths.get("src", "main", "resources", "data", fileLanguage).toString();
        ObjectMapper mapper= new ObjectMapper();
        if (!Files.exists(Paths.get(fileName))) {
            System.err.println("Файл словаря не найден: " + fileName);
            return;
        }
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            if (fileContent != null && !fileContent.isEmpty()) {
                Dictionary dictionary1 = new Dictionary(AlphabetsManeger.getInstance().getAlphabets(key), fileContent);
                dictionaries.add(dictionary1);
                fileName = Paths.get("src", "main", "resources", "dictionaries","Dictionary_"+AlphabetsManeger.getInstance().getAlphabets(key).getName()+".json").toString();
                mapper.writeValue(new File(fileName),dictionary1);
            } else {
                System.err.println("Файл пуст или не удалось прочитать: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке базового словаря: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void addDictionary(Dictionary dictionary) {
        dictionaries.add(dictionary);
    }
    public Dictionary getDictionaries(int i) {
        return dictionaries.get(i-1);
    }
    public int Length() {
        return dictionaries.size();
    }
    @Override
    public String toString() {
        String s= "";
        for(int i=0;i<dictionaries.size();i++){
            s+=(i+1)+"]--> "+dictionaries.get(i).toString();
        }
        return s;
    }
}