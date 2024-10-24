import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class FileEncryption {

    // Method to encrypt the file and return the encryption key used
    public String encryptFileWithKey(String algorithm, String inputFile, String outputFile) {
        try {
            String fileContents = Files.readString(Paths.get(inputFile));
            String encryptionKey = generateEncryptionKey(Algorithm.valueOf(algorithm), fileContents);
            String encryptedText = encryptText(Algorithm.valueOf(algorithm), fileContents, encryptionKey);
            Files.writeString(Paths.get(outputFile), encryptedText);
            return encryptionKey;  // Return the key used during encryption
        } catch (IOException e) {
            throw new RuntimeException("Error encrypting file", e);
        }
    }

    private String generateEncryptionKey(Algorithm algorithm, String plainText) {
        Random random = new Random();
        switch (algorithm) {
            case CAESAR:
                int shiftKey = random.nextInt(26);
                return Integer.toString(shiftKey);  // Return the shift key as a string
            case VIGENERE:
                return VigenereCipher.generateKey(plainText.length());  // Return the generated key for Vigenère
            case RSA:
                KeyPair keyPair = RSACipher.generateKey(2048);
                return keyPair.privateKey.toString();  // Return the private key for RSA
            default:
                throw new UnsupportedOperationException("Algorithm not supported.");
        }
    }

    public void decryptFile(String algorithm, String inputFile, String outputFile, String key) {
        try {
            String fileContents = Files.readString(Paths.get(inputFile));
            String decryptedText = decryptText(Algorithm.valueOf(algorithm), fileContents, key);
            Files.writeString(Paths.get(outputFile), decryptedText);
        } catch (IOException e) {
            throw new RuntimeException("Error decrypting file", e);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }

    public String encryptText(Algorithm algorithm, String plainText, String key) {
        switch (algorithm) {
            case CAESAR:
                int shiftKey = Integer.parseInt(key);
                return CaesarCipher.encrypt(plainText, shiftKey);
            case VIGENERE:
                return VigenereCipher.encrypt(plainText, key);
            case RSA:
                KeyPair keyPair = RSACipher.generateKey(2048);
                BigInteger message = RSACipher.stringToBigInteger(plainText);
                BigInteger encryptedMessage = RSACipher.encrypt(message, keyPair);
                return encryptedMessage.toString() + "," + keyPair.privateKey.toString() + "," + keyPair.n.toString();
        }
        throw new UnsupportedOperationException("Algorithm not supported.");
    }

    public String decryptText(Algorithm algorithm, String encryptedText, String key) {
        switch (algorithm) {
            case CAESAR:
                int shiftKey;
                try {
                    shiftKey = Integer.parseInt(key);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid key for Caesar cipher");
                }
                return CaesarCipher.decrypt(encryptedText, shiftKey);
            case VIGENERE:
                return VigenereCipher.decrypt(encryptedText, key);
            case RSA:
                //System.out.println(key);
                //System.out.println(keyPair.n);
                String[] keyParts = key.split(",");
                if (keyParts.length != 3) {
                    throw new IllegalArgumentException("Invalid key format for RSA. Must contain privateKey, modulus.");
                }

                KeyPair keyPair = new KeyPair(null, new BigInteger(keyParts[0].trim()),  new BigInteger(keyParts[1].trim()));

                BigInteger encryptedMessage = new BigInteger(encryptedText.trim());
                return RSACipher.bigIntegerToString(RSACipher.decrypt(encryptedMessage, keyPair));
            default:
                throw new UnsupportedOperationException("Algorithm not supported.");
        }
    }
}
