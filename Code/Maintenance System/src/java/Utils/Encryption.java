/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.security.MessageDigest;
import org.apache.tomcat.util.codec.binary.Base64;



/**
 *
 * @author PC
 */
public class Encryption {
    public static String EncryptionPassword (String string ) {
        String encrytion = "1234567890qwertyuiopasdfghjklzxcvbnm!@#$%^&*";
        String result = null;
        string = string +  encrytion;
        try{
            byte [] dataByte = string.getBytes("UTF-8");
            MessageDigest md  =  MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(dataByte));
        } catch(Exception e) {
            
        }
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(EncryptionPassword("123456"));
    }
}
