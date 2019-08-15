package com.zstechtms.networklibrary.hmac;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.Key;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Hmac {

    private static final String MAC_NAME = "HmacMD5";
    private static final String ENCODING = "UTF-8";


    private static byte[] HAMCMD5(File file, Key pubKey) throws Exception {
        FileInputStream in = new FileInputStream(file);

        Mac md = Mac.getInstance(MAC_NAME);
        md.init(pubKey);
        byte[] contents = new byte[1024];
        int readSize;
        while ((readSize = in.read(contents)) != -1) {
            md.update(contents, 0, readSize);
        }
        return md.doFinal();
    }

    public static String HmacEncrypt(File file, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        byte[] encryptBytes = HAMCMD5(file, secretKey);
        BigInteger mac = new BigInteger(encryptBytes);
        return mac.toString(16);
    }

}
