import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Dictionary{

    private String nameAlphabet;
    @JsonProperty("text")
    private Set<String> words;

    public Dictionary(){
        words=new HashSet<>();
    }
    public Dictionary(Alphabet alphabet, String text) {
        nameAlphabet = alphabet.getName();
        words = new HashSet<>();
        if (text != null && !text.trim().isEmpty()) {
            addText(text);
        }
    }
    public void addText(String text) {
        if (text == null || text.trim().isEmpty()) return;
        String[] tokens = text.split("\\s+");
        for (String token : tokens) {
            String word = token.trim();
            if (!word.isEmpty()) {
                words.add(word);
            }
        }
    }
    public static List<String> dictionaryInString(Alphabet alphabet) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> words = new ArrayList<>();
        Path path = Paths.get("src", "main", "resources", "dictionaries", "Dictionary_" + alphabet.getName() + ".json");
        try {
            File file = new File(path.toString());
            if(!file.exists()){
                file.createNewFile();
            }
            String jsonContent = Files.readString(path);
            JsonNode rootNode = mapper.readTree(jsonContent);
            if (rootNode.has("text")) {
                JsonNode textNode = rootNode.get("text");
                if (textNode.isArray()) {
                    for (JsonNode wordNode : textNode) {
                        words.add(wordNode.asText());
                    }
                } else {
                }
            }
            else if (rootNode.isArray()) {
                for (JsonNode wordNode : rootNode) {
                    words.add(wordNode.asText());
                }
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    public String getNameAlphabet() {
        return nameAlphabet;
    }

    public void setNameAlphabet(String nameAlphabet) {
        this.nameAlphabet = nameAlphabet;
    }

    @JsonProperty("text")
    public Set<String> getText() {
        return new HashSet<>(words);
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "nameAlphabet='" + nameAlphabet + '\'' +
                ", text='" + words.toString() + '\'' +
                '}';
    }
}