package context;

public enum SignatureAlgorithm {
    SHA256_WITH_RSA("SHA256withRSA"),
    SHA384_WITH_RSA("SHA384withRSA"),
    SHA512_WITH_RSA("SHA512withRSA"),
    SHA1_WITH_RSA("SHA1withRSA");

    private final String algorithm;

    SignatureAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public String toString() {
        return algorithm;
    }
}
