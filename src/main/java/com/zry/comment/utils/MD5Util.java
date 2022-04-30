package com.zry.comment.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**MD5 工具类 用来对密码的二次加密
 * @author zry
 * @create 2022-04-09 20:58
 */
public class MD5Util {
    private static String salt = "1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5DigestAsHex(src.getBytes(StandardCharsets.UTF_8));
    }
    public static String inputPassToFromPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    // 我们需要调用的方法   返回二次加密后的密码
    public static String inputPassToDBPass(String inputPass, String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, salt);
        return  dbPass;
    }

    public static void main(String[] args) {
//        b5fb686c5752edd1c337ac7231c6cea5
        System.out.println(MD5Util.inputPassToDBPass("root", salt));
        //85779e93f9bd754d8b2f66cca44a0ab6
        System.out.println(MD5Util.inputPassToFromPass("root"));
        //b5fb686c5752edd1c337ac7231c6cea5
        System.out.println(MD5Util.formPassToDBPass("85779e93f9bd754d8b2f66cca44a0ab6", salt));
    }
}
