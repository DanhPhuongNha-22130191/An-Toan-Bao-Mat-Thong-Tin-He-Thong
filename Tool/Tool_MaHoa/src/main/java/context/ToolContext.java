package context;

import util.Base64Utils;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ToolContext {
    private static ToolContext instance;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private KeyPair keyPair;
    private String algorithm = "RSA";
    private KeySize keySize = KeySize.SIZE_2048;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.SHA256_WITH_RSA;

    public static ToolContext getInstance() {
        if (instance == null) {
            instance = new ToolContext();
        }
        return instance;
    }

    public void setKeySize(KeySize keySize) {
        this.keySize = keySize;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
        generator.initialize(keySize.getSize());
        this.keyPair = generator.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public PublicKey createPublicKeyFromString(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64Utils.decodeToBytes(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    public PrivateKey createPrivateKeyFromString(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64Utils.decodeToBytes(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
    }

    public String sign(String data) throws Exception {
        try {
            Signature signature = Signature.getInstance(signatureAlgorithm.getAlgorithm());
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return Base64Utils.encode(signature.sign());
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("Vui lòng tạo khóa trước khi ký");
        } catch (Exception e) {
            throw new Exception("Lỗi không xác định khi ký dữ liệu");
        }
    }

    public boolean verify(String data, String signature) throws Exception {
        try {
            Signature signatureObject = Signature.getInstance(signatureAlgorithm.getAlgorithm());
            signatureObject.initVerify(publicKey);
            signatureObject.update(data.getBytes());
            return signatureObject.verify(Base64Utils.decodeToBytes(signature));
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("Vui lòng tạo khóa trước khi xác minh");
        } catch (Exception e) {
            throw new Exception("Lỗi không xác định khi xác minh chữ ký");
        }
    }
}