import com.sap.it.api.ITApi;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.securestore.*;
import com.sap.it.api.keystore.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import javax.net.ssl.KeyManager;

def String EncryptData(String arg1){
    def body = arg1;
    String password;
    def service = ITApiFactory.getApi(SecureStoreService.class, null);
    def credential = service.getUserCredential("Security_Material");  // <<< SECURE PARAMETER NAME 
    if (credential == null) {
        throw new IllegalStateException("No credential found for alias 'Security_Material'");             
    } else {
        password = new String(credential.getPassword());
    }
    key = password.getBytes("UTF-8");
    sha = MessageDigest.getInstance("SHA-1");
    key = sha.digest(key);
    key = Arrays.copyOf(key, 16);
    secretKey = new SecretKeySpec(key, "AES");

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    def encryptedData =  Base64.getEncoder().encodeToString(cipher.doFinal(body.getBytes("UTF-8")));

    return encryptedData; 
}

def String DecryptData(String arg1){
    def body = arg1;
    String password;
    def service = ITApiFactory.getApi(SecureStoreService.class, null);
    def credential = service.getUserCredential("Security_Material");  // <<< SECURE PARAMETER NAME
    if (credential == null) {
        throw new IllegalStateException("No credential found for alias 'Security_Material'");             
    } else {
        password = new String(credential.getPassword());
    }
    key = password.getBytes("UTF-8");
    sha = MessageDigest.getInstance("SHA-1");
    key = sha.digest(key);
    key = Arrays.copyOf(key, 16);
    secretKey = new SecretKeySpec(key, "AES");	

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");				                
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    def decryptedData =  new String (cipher.doFinal(Base64.getDecoder().decode(body)));	

    return decryptedData; 
}
