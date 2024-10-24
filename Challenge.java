import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Challenge {
    private String correctAlgorithm;
    private String encryptionKey;  // To store the key used during encryption

    public void startChallenge(String inputFile, String encryptedFile) {
        FileEncryption fileEncryption = new FileEncryption();
        Algorithm randomAlgorithm = Algorithm.values()[new Random().nextInt(Algorithm.values().length)];
        correctAlgorithm = randomAlgorithm.name();
        encryptionKey = fileEncryption.encryptFileWithKey(randomAlgorithm.name(), inputFile, encryptedFile);
    }

    public boolean checkGuess(String guessedAlgorithm) {
        return guessedAlgorithm.equalsIgnoreCase(correctAlgorithm);
    }

    public String getCorrectAlgorithm() {
        return correctAlgorithm;
    }

    public String getEncryptedText(String encryptedFile) {
        try {
            return Files.readString(Paths.get(encryptedFile));
        } catch (IOException e) {
            throw new RuntimeException("Error reading the encrypted file", e);
        }
    }

    public void decryptFile(String encryptedFile, String outputFile) {
        FileEncryption fileEncryption = new FileEncryption();
        fileEncryption.decryptFile(correctAlgorithm, encryptedFile, outputFile, encryptionKey);  // Now pass the correct key
    }
}
