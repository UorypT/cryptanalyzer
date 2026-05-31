import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlphabetsManeger {
    private static ArrayList<Alphabet> alphabets=new ArrayList<>();
    private static AlphabetsManeger alphabetsManeger;
    private final String punctuation="1234567890.,!?:'-+_ ";

    private static void saveCounters() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String path = Paths.get("src", "main", "resources", "data", "alphabet.json").toString();
            mapper.writeValue(new File(path), alphabets);
        } catch (Exception e) {
            System.err.println("Ошибка сохранения алфавитов: " + e.getMessage());
        }
    }
    private static void loadCounters() {

        try {
            String path = Paths.get("src", "main", "resources", "data", "alphabet.json").toString();
            File file = new File(path);
            if (file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                List<Alphabet> loadedAlphabets = mapper.readValue(file, new TypeReference<List<Alphabet>>() {});
                alphabets.clear();
                alphabets.addAll(loadedAlphabets);
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки алфавитов: " + e.getMessage());
        }
    }

    private AlphabetsManeger() {
        baseAlphabets();
    }
    public static AlphabetsManeger getInstance() {
        if (alphabetsManeger == null) {
            alphabetsManeger = new AlphabetsManeger();
        }
        return alphabetsManeger;
    }

    private void baseAlphabets(){
        loadCounters();
        if(alphabets.size()<1){
            Alphabet alphabets1=new Alphabet("Русский","абвгдеёжзийклмнопрстуфхцчшщъыьэюя"+punctuation);
            Alphabet alphabets2=new Alphabet("Английский","abcdefghijklmnopqrstuvwxyz"+punctuation);
            alphabets.add(alphabets1);
            alphabets.add(alphabets2);
            saveCounters();
        }
    }

    public boolean addAlphabet(String name,String alphabet,boolean punct) {
        loadCounters();
        for (Alphabet value : alphabets) {
            if (value.getName().equals(name)) {
                return false;
            }
        }
        if(Objects.equals(name, "") ||Objects.equals(alphabet, "")){
            return false;
        }
        if(punct){
            alphabets.add(new Alphabet(name,alphabet+punctuation));
        }else {
            alphabets.add(new Alphabet(name, alphabet));
        }
        saveCounters();
        return true;
    }
    public void deleteAlphabet(String name) {
        loadCounters();
        for (int i=2; i<alphabets.size(); i++) {
            if (alphabets.get(i).getName().equals(name)) {
                alphabets.remove(i);
            }
        }
        saveCounters();
    }

    public Alphabet getAlphabets(int i) {
        loadCounters();
        return alphabets.get(i-1);
    }
    public List<Alphabet> getAllAlphabets() {
        return alphabets;
    }

    @Override
    public String toString() {
        loadCounters();
        String s="";
        for(int i=0;i<alphabets.size();i++){
            s +=((i + 1)+")-> ")+alphabets.get(i).toString()+'\n';
        }
        return s;
    }
}
