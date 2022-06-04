package com.zry.simpleBlog.comment.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * MD5 工具类 用来对密码的二次加密
 *
 * 示例可用的
 *    用户名： root
 *    密码：b5fb686c5752edd1c337ac7231c6cea5
 *
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
/**
 * 功能描述: 生成密码
 *
 * @create 2022/5/16
 */
    public static void main(String[] args) {
//        b5fb686c5752edd1c337ac7231c6cea5
        System.out.println(MD5Util.inputPassToDBPass("1111", salt));
        //85779e93f9bd754d8b2f66cca44a0ab6
        System.out.println(MD5Util.inputPassToFromPass("asdw"));
        //b5fb686c5752edd1c337ac7231c6cea5
        System.out.println(MD5Util.formPassToDBPass("e14c663d3a0cdb0dc4739f0c80f5b5ad", salt));
    }
}
