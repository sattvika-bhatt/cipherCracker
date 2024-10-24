import java.math.BigInteger;

public class KeyPair {
    public BigInteger publicKey;
    public BigInteger privateKey;
    public BigInteger n;

    public KeyPair(BigInteger publicKey, BigInteger privateKey, BigInteger n){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.n = n;
    }
}
