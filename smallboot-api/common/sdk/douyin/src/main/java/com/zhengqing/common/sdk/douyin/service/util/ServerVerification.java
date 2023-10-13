package com.zhengqing.common.sdk.douyin.service.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class ServerVerification {

    private String getMsgSignature(String tpToken, String timestamp, String nonce, String encrypt) throws Exception {
        String[] values = new String[]{tpToken, timestamp, nonce, encrypt};
        Arrays.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
        }

        String str = sb.toString();

        try {
            //指定sha1算法
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());

            //获取字节数组
            byte[] messageDigestByte = messageDigest.digest();

            StringBuilder hexStr = new StringBuilder();
            // 字节数组转换为十六进制数
            for (byte b : messageDigestByte) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }

            return hexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("不能生成签名");
        }
    }

    private void verify(String msgSignature, String newMsgSignature) {
        boolean res = msgSignature.equals(newMsgSignature);
        System.out.println(res);
    }

    public static void main(String[] args) throws Exception {
        ServerVerification test = new ServerVerification();
        String msgSignature = "XXX";
        String newMsgSignature = test.getMsgSignature("XXX", "XXX", "XXX", "XXX");
        test.verify(msgSignature, newMsgSignature);
    }
}
