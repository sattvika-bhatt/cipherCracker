import java.math.BigInteger;
import java.security.SecureRandom;

public class RSACipher {
    public static KeyPair generateKey(int keySize) {
        SecureRandom random = new SecureRandom();

        BigInteger p = BigInteger.probablePrime(keySize / 2, random);
        BigInteger q = BigInteger.probablePrime(keySize / 2, random);

        BigInteger n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(65537);

        BigInteger d = e.modInverse(phi);

        KeyPair keyPair = new KeyPair(e, d, n);

        return keyPair;
    }

    public static BigInteger encrypt(BigInteger message, KeyPair keyPair) {
        return message.modPow(keyPair.publicKey, keyPair.n);
    }

    public static BigInteger decrypt(BigInteger cipherText, KeyPair keyPair) {
        if (keyPair.privateKey == null) {
            throw new IllegalArgumentException("Private key cannot be null for decryption.");
        }
        return cipherText.modPow(keyPair.privateKey, keyPair.n);
    }

    public static BigInteger stringToBigInteger(String message) {
        byte[] bytes = message.getBytes();
        return new BigInteger(1, bytes);
    }

    public static String bigIntegerToString(BigInteger bigInteger) {
        byte[] bytes = bigInteger.toByteArray();

        if(bytes[0] == 0){
            byte[] trimmedBytes = new byte[bytes.length -1];
            System.arraycopy(bytes, 1, trimmedBytes, 0, trimmedBytes.length);
            return new String(trimmedBytes);
        }
        return new String(bytes);
    }
}
