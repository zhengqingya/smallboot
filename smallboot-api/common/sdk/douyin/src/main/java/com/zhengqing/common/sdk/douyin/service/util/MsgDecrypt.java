package com.zhengqing.common.sdk.douyin.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@Slf4j
public class MsgDecrypt {

    private Cipher cipher;
    static int RANDOM_BYTES_POS = 32;

    public MsgDecrypt(String encodingAesKey) throws Exception {
        //AES key长度固定
        if (encodingAesKey.length() != 43) {
            throw new Exception("AES key 长度不合法");
        }
        byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");

        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
        Cipher encCipher = Cipher.getInstance("AES/CBC/NoPadding");
        encCipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

        this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
        this.cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
    }

    //将四个byte解析为一个32位的数字，数字的值也就是消息体的String格式下的长度
    private int getLength(byte[] orderBytes) {
        ByteBuffer buf = ByteBuffer.wrap(orderBytes);
        buf.order(ByteOrder.BIG_ENDIAN);
        return buf.getInt();
    }

    private byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    public String decrypt(String text) throws Exception {
        byte[] original;
        try {
            byte[] encrypted = Base64.decodeBase64(text);
            original = this.cipher.doFinal(encrypted);
        } catch (Exception e) {
            log.error("抖音解码异常: " + e);
            throw new Exception("解码异常");
        }

        String Content;
        String AppId;
        try {
            byte[] bytes = this.decode(original);

            //byte数组的第32、33、34、35个元素代表了消息体的真实字符个数，也就是长度
            byte[] pos = Arrays.copyOfRange(bytes, RANDOM_BYTES_POS, RANDOM_BYTES_POS + 4);
            int msgLength = this.getLength(pos);

            //根据解析出来的消息体长度值，截取真实的消息体
            Content = new String(Arrays.copyOfRange(bytes, RANDOM_BYTES_POS + 4, RANDOM_BYTES_POS + 4 + msgLength), StandardCharsets.UTF_8);
            //byte数组截去真实消息后，末尾剩下的字符就是appid
            AppId = new String(Arrays.copyOfRange(bytes, RANDOM_BYTES_POS + 4 + msgLength, bytes.length), StandardCharsets.UTF_8);

            log.info("Content: " + Content);
            log.info("ThirdParty AppID: " + AppId);
            return Content;
        } catch (Exception e) {
            log.error("抖音解密异常: " + e);
            throw new Exception("抖音解密异常！");
        }
    }

    public static void main(String[] args) throws Exception {
        MsgDecrypt test = new MsgDecrypt("服务商-开发配置中拿到 xx");
        test.decrypt("Encrypt");
    }
}