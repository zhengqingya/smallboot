package com.zhengqing.common.core.util;

import com.zhengqing.common.base.constant.AppConstant;
import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * <p>
 * DES 加密/解密工具类
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:40
 */
public class DesUtil {

    private static final String DES_ALGORITHM = "DES";

    public static void main(String[] args) {
        String helloWorld = encrypt("hello world", AppConstant.DES_KEY);
        System.out.println(helloWorld);
        System.out.println(decrypt(helloWorld, AppConstant.DES_KEY));
        System.out.println(decrypt("UhWnn6jPKf5a+b+fzS7BqQ==", AppConstant.DES_KEY));
    }

    /**
     * des加密
     *
     * @param data      需要加密的参数
     * @param secretKey 自定义密钥字符串
     */
    @SneakyThrows(Exception.class)
    public static String encrypt(String data, String secretKey) {
        //将自定义密钥转为字节型
        byte[] encryptKey = secretKey.getBytes();

        String encryptedData = null;
        //DES算法要求一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec deskey = new DESKeySpec(encryptKey);

        //创建一个密钥工厂，然后用它把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        SecretKey key = keyFactory.generateSecret(deskey);

        //加密对象
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);

        //加密，把字节数组编码成字符串
        encryptedData = new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));

        return encryptedData;
    }

    /**
     * 解密字符串
     *
     * @param secretData 需要解密的参数
     * @param secretKey  自定义密钥字符串
     */
    @SneakyThrows(Exception.class)
    public static String decrypt(String secretData, String secretKey) {
        //将自定义密钥转为字节型
        byte[] encryptKey = secretKey.getBytes();

        String decryptedData = null;

        //DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(encryptKey);

        //创建一个密钥工厂，把DESKeySpec转换成一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        SecretKey key = keyFactory.generateSecret(desKey);

        //解密对象
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, sr);

        //字符串解码 ——> 字节数组，并解密
        decryptedData = new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(secretData)));
        return decryptedData;
    }

}
