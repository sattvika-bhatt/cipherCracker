public class CaesarCipher {
    public static String encrypt(String plainText, int shiftKey) {
        StringBuilder cipherText = new StringBuilder();
        char base;
        for (char letter : plainText.toCharArray()) {
            if (Character.isLetter(letter)) {
                base = Character.isUpperCase(letter) ? 'A' : 'a';
                cipherText.append((char) ((letter - base + shiftKey) % 26 + base));
            } else {
                cipherText.append(letter);
            }
        }
        return cipherText.toString();
    }

    public static String decrypt(String cipherText, int shiftKey) {
        StringBuilder decryptedText = new StringBuilder();
        char base;
        for (char letter : cipherText.toCharArray()) {
            if (Character.isLetter(letter)) {
                base = Character.isUpperCase(letter) ? 'A' : 'a';
                decryptedText.append((char) ((letter - base - shiftKey+26) % 26 + base));
            } else {
                decryptedText.append(letter);
            }
        }
        return decryptedText.toString();
    }

    public boolean isEnglishSentence(String sentence){
        String[] words = sentence.split("\\s+");
        for (String word : words){
            if(!isEnglishWord(word)){
                return false;
            }
        }
        return true;
    }

    public boolean isEnglishWord(String word){
        return word.matches("[a-zA-Z]{3,}");
    }
}