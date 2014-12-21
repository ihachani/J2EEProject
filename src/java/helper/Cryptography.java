/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author faiez
 */
public class Cryptography {
    
    public static String MD5 (String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return s;
        
/**byte[] bytesOfMessage;
        bytesOfMessage = s.getBytes("UTF-8");
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        return thedigest.toString(); */
    }
}
