package com.bonc.rcp.common.CryptTest;


import java.io.UnsupportedEncodingException;

import cn.hutool.core.codec.Base64;

public class CryptTest {//Base64密码加密

    // 字符串编码
    private static final String UTF_8 = "UTF-8";

    /**
     * 加密字符串
     * @param inputData
     * @return
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decode(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    /**
     * 解密加密后的字符串
     * @param inputData
     * @return
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encode(inputData.getBytes(UTF_8)));
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(CryptTest.encodeData("我是中文"));
        String enStr = CryptTest.encodeData("我是中文");
        System.out.println(CryptTest.decodeData(enStr));
    }
}