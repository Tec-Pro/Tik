/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author agustin
 */
public class Encryption {

    /**
     *
     *
     * @param toEncrypt a String to be encrypted
     * @return an encrypted word
     */
    public static byte[] encrypt(String toEncrypt) throws Exception {
        final byte[] bytes = toEncrypt.getBytes("UTF-8");
        final Cipher aes = obtainCipher(true);
        final byte[] encrypted = aes.doFinal(bytes);
        return encrypted;
    }

    /**
     *
     * @param encrypted an encrypted word
     * @return a decrypted string
     */
    public static String decrypt(byte[] encrypted) throws Exception {
        final Cipher aes = obtainCipher(false);
        final byte[] bytes = aes.doFinal(encrypted);
        final String decrypted = new String(bytes, "UTF-8");
        return decrypted;
    }

    private static Cipher obtainCipher(boolean paraCifrar) throws Exception {
        final String seed = "áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%|@~½¬ł€¶ŧ←↓→øĸŋđðßæ«»¢““””nµđŋ@";
        final MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(seed.getBytes("UTF-8"));
        final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

        final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if (paraCifrar) {
            aes.init(Cipher.ENCRYPT_MODE, key);
        } else {
            aes.init(Cipher.DECRYPT_MODE, key);
        }

        return aes;
    }

}
