import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Crypto {

    private static final String ALG = "AES/ECB/PKCS5Padding";

    public static String encrypt(String data, String mpw) throws Exception {
        SecretKeySpec key = new SecretKeySpec(normalizeKey(mpw), "AES");
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String base64, String mpw) throws Exception {
        SecretKeySpec key = new SecretKeySpec(normalizeKey(mpw), "AES");
        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(base64);
        return new String(cipher.doFinal(decoded));
    }

    private static byte[] normalizeKey(String mpw) {
        byte[] key = new byte[16];
        byte[] pwBytes = mpw.getBytes();
        for (int i = 0; i < key.length; i++) {
            key[i] = i < pwBytes.length ? pwBytes[i] : 0;
        }
        return key;
    }

}
