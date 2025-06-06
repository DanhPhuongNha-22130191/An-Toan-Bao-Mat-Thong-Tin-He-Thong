package context;

import util.Base64Util;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ToolContext {
    private static ToolContext instance;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private KeyPair keyPair;
    private final String algorithm = "RSA";


    public static ToolContext getInstance() {
        if (instance == null) {
            instance = new ToolContext();
        }
        return instance;
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
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        this.keyPair = generator.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }


    public PublicKey createPublicKeyFromString(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64Util.decodeToBytes(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(new X509EncodedKeySpec(publicBytes));
    }

    public PrivateKey createPrivateKeyFromString(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] privateBytes = Base64Util.decodeToBytes(privateKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
    }

    public String sign(String data) throws Exception {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return Base64Util.encode(signature.sign());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new InvalidKeyException("Vui lòng tạo khóa trước khi ký");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi không xác định");
        }
    }

    public boolean verify(String data, String signature) throws Exception {
        try {
            Signature signatureObject = Signature.getInstance("SHA256withRSA");
            signatureObject.initVerify(publicKey);
            signatureObject.update(data.getBytes());
            return signatureObject.verify(Base64Util.decodeToBytes(signature));
        } catch (InvalidKeyException e) {
            throw new InvalidKeyException("Vui lòng tạo khóa trước khi ký");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi không xác định");
        }
    }
}
