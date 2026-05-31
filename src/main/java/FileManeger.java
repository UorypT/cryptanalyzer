import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileManeger {
    private static final HashMap<FileManegerHelper, HashMap<String, Integer>> counters = new HashMap<>() {{
        put(FileManegerHelper.ENCRYPT, new HashMap<>());
        put(FileManegerHelper.DECRYPT, new HashMap<>());
        put(FileManegerHelper.BRUTE_FORCE, new HashMap<>());
        put(FileManegerHelper.ANALYSIS, new HashMap<>());
    }};
    private static void saveCounters() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String path = Paths.get("src", "main", "resources", "data", "counters.json").toString();
            mapper.writeValue(new File(path), counters);
        } catch (Exception e) {
            System.err.println("Ошибка сохранения счётчиков: " + e.getMessage());
        }
    }

    private static void loadCounters() {
        try {
            String path = Paths.get("src", "main", "resources", "data", "counters.json").toString();
            File file = new File(path);
            if (file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                TypeFactory typeFactory = mapper.getTypeFactory();
                JavaType innerType = typeFactory.constructParametricType(HashMap.class, String.class, Integer.class);
                JavaType outerType = typeFactory.constructParametricType(
                        HashMap.class, new Class<?>[] { FileManegerHelper.class, innerType.getRawClass() }
                );
                HashMap<FileManegerHelper, HashMap<String, Integer>> loaded = mapper.readValue(file, outerType);
                counters.clear();
                counters.putAll(loaded);
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки счётчиков: " + e.getMessage());
        }
    }

    public static void writeFileEncrypt(String filePath, String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.ENCRYPT,alphabet.getName());
        Path path = Paths.get(filePath,"encryptedText_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }
    public static void writeFileEncrypt(String result, Alphabet alphabet){
        loadCounters();
        baseDirectory();
        int counter = getNextCounter(FileManegerHelper.ENCRYPT,alphabet.getName());
        Path path = Paths.get("src", "main", "resources", "outputData","encryptedText_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }


    public static void writeFileDecrypt(String filePath, String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.DECRYPT,alphabet.getName());
        Path path = Paths.get(filePath,"decryptedText_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }
    public static void writeFileDecrypt(String result, Alphabet alphabet){
        loadCounters();
        baseDirectory();
        int counter = getNextCounter(FileManegerHelper.DECRYPT,alphabet.getName());
        Path path = Paths.get("src", "main", "resources", "outputData","decryptedText_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }


    public static void writeFileBruteForce(String filePath, String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.BRUTE_FORCE,alphabet.getName());
        Path path = Paths.get(filePath,"bruteForce_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }
    public static void writeFileBruteForce(String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.BRUTE_FORCE,alphabet.getName());
        Path path = Paths.get("src", "main", "resources", "outputData","bruteForce_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }

    public static void writeFileStatistical(String filePath, String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.ANALYSIS,alphabet.getName());
        Path path = Paths.get(filePath,"statisticalAnalysis"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }
    public static void writeFileStatistical(String result, Alphabet alphabet){
        loadCounters();
        int counter = getNextCounter(FileManegerHelper.BRUTE_FORCE,alphabet.getName());
        Path path = Paths.get("src", "main", "resources", "outputData","Statistical_"+alphabet.getName()+counter+".txt");
        processWriteFile(result, path);
        saveCounters();
    }


    private static void processWriteFile(String result, Path path){
        try {
            Files.writeString(path,result);
        }catch (IOException e){
            System.err.println(e);
        }
    }
    public static String readFile(String filePath){
        String result = "";
        try {
            Path path = Paths.get(filePath);
            result= Files.readString(Path.of(path.toString()));
            System.out.println(result);
        }catch (IOException e){
            System.err.println(e);
        }
        return result;
    }

    private static int getNextCounter(FileManegerHelper operation, String alphabetName) {
        HashMap<String, Integer> alphabetCounters = counters.get(operation);
        int currentCount = alphabetCounters.getOrDefault(alphabetName, 0);
        alphabetCounters.put(alphabetName, currentCount + 1);
        return currentCount + 1;
    }
    private static void baseDirectory(){
        Path dir = Paths.get("src", "main", "resources", "outputData");
        if(!Files.exists(dir)){
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                System.err.println("Ошибка создания директории: " + e.getMessage());
            }
        }
    }
}