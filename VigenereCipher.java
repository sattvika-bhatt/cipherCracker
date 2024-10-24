import java.util.Random;

public class VigenereCipher {
    public static String encrypt(String plainText, String key) {
        StringBuilder cipherText = new StringBuilder();
        int keyIndex = 0;
        char base;
        for (char letter : plainText.toCharArray()) {
            if (Character.isLetter(letter)) {
                base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyChar = key.charAt(keyIndex % key.length());
                int shiftKey = Character.toUpperCase(keyChar) - 'A';
                cipherText.append((char) ((letter - base + shiftKey) % 26 + base));
                keyIndex++;
            } else {
                cipherText.append(letter);
            }
        }
        return cipherText.toString();
    }

    public static String decrypt(String cipherText, String key) {
        StringBuilder plainText = new StringBuilder();
        int keyIndex = 0;
        char base;

        for (char letter : cipherText.toCharArray()) {
            if (Character.isLetter(letter)) {
                base = Character.isUpperCase(letter) ? 'A' : 'a';
                char keyChar = key.charAt(keyIndex % key.length());
                int shiftKey = Character.toUpperCase(keyChar) - 'A';
                plainText.append((char) (((letter - base - shiftKey)+26) % 26 + base));
                keyIndex++;
            } else {
                plainText.append(letter);
            }
        }
        return plainText.toString();
    }


    public static String generateKey(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder randomKey = new StringBuilder();

        for (int i = 0; i < length; i++) {
            randomKey.append(chars.charAt(random.nextInt(chars.length())));
        }

        return randomKey.toString();
    }
}