package cn.fanchencloud.campus.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 2:43
 * Description:
 *
 * @author chen
 */
public class WechatUtil {
    private static final String TOKEN = "wj1012";
    private static final String EncodingAESKey = "3ojm31IbQg8rmsuG8EMARgZ4PL52hAx6OG1WeTXu7tX";



    public static boolean checkSignature(String signature, String timestamp, String nonce) {

        String[] arr = new String[]{TOKEN, timestamp, nonce};

        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }

        // sha1加密
        String temp = getSHA1String(content.toString());
        // 与微信传递过来的签名进行比较
        return temp.equals(signature);
    }

    private static String getSHA1String(String data) {
        // 使用commons codec生成sha1字符串
        return DigestUtils.sha1Hex(data);
    }
}
