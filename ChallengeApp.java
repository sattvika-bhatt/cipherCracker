import java.util.Scanner;

public class ChallengeApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Challenge challenge = new Challenge();

        System.out.println("Enter the path to the input file:");
        String inputFile = scanner.nextLine();
        String encryptedFile = "encryptedFile.txt";
        challenge.startChallenge(inputFile, encryptedFile);

        String encryptedText = challenge.getEncryptedText(encryptedFile);
        System.out.println("\nThe file has been encrypted! Here is the encrypted text:\n");
        System.out.println(encryptedText);

        int attempts = 0;
        boolean isCorrect = false;

        while (attempts < 2) {
            System.out.println("\nGuess the encryption algorithm (CAESAR, VIGENERE, RSA):");
            String guessedAlgorithm = scanner.nextLine();

            if (challenge.checkGuess(guessedAlgorithm)) {
                System.out.println("Correct! Now decrypting the file.");
                String outputFile = "decryptedFile.txt";
                challenge.decryptFile(encryptedFile, outputFile);
                System.out.println("Decrypted file saved as: " + outputFile);
                isCorrect = true;
                break;
            } else {
                attempts++;
                if (attempts < 2) {
                    System.out.println("Incorrect guess. You have " + (2 - attempts) + " guess(es) left.");
                } else {
                    System.out.println("Incorrect guess. You've used all your guesses.");
                }
            }
        }

        if (!isCorrect) {
            System.out.println("The correct algorithm was: " + challenge.getCorrectAlgorithm());
            System.out.println("Decrypting the file for you.");
            String outputFile = "decryptedFile.txt";
            challenge.decryptFile(encryptedFile, outputFile);
            System.out.println("Decrypted file saved as: " + outputFile);
        }

        scanner.close();
    }
}
