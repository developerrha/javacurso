package pkgdir.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

public class TextEncryption{

	private String ALGORITHM = "AES";
    	private String TRANSFORMATION = "AES";
	private int cipherMode = Cipher.ENCRYPT_MODE;
     private SecretKeySpec secretKey;
	private byte[] key;

	public TextEncryption(){
		
	}

	public boolean doCrypto( int cipherMode, String key, File inputFile ) {
		System.out.println("cipherMode: "+cipherMode);		
		boolean encok = true;	
		try {
			setKey(key);				
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			byte[] outputBytes = cipher.doFinal(inputBytes);
			FileOutputStream outputStream = new FileOutputStream(inputFile);//el mismo archivo
			outputStream.write(outputBytes);
			inputStream.close();
			outputStream.close();
		} catch ( Exception e) {
			encok = false;
		  	e.printStackTrace();
		}
		return encok;
	}

	public void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
