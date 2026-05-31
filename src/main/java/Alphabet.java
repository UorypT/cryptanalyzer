public class Alphabet {
    private String name;
    private char[] alphabet;

    public Alphabet(String name, String alphabet) {
        this.name = name;
        this.alphabet = alphabet.toCharArray();
    }

    public Alphabet() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlphabet(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public String getName() {
        return name;
    }
    public char[] getAlphabet() {
        return alphabet;
    }
    public int Lengthget() {
        return alphabet.length;
    }
    private String alphabetToString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (char ch : alphabet) {
            sb.append("[").append(ch).append("]");
            count++;
            if (count % 23 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Название алфавита: " + name + '\n' +
                "Символы алфавита в порядке возрастания: \n" + alphabetToString()+'\n'+
                "Длина алфавита: "+ Lengthget()+'\n';
    }

    public int getCharIndex(char character) {
        char[] alphabetArray = alphabet;
        for (int i = 0; i < alphabetArray.length; i++) {
            if (alphabetArray[i] == character) {
                return i;
            }
        }
        throw new IllegalArgumentException("Символ не найден");
    }
}
