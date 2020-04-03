package cn.fanchencloud.campus.util;

import java.security.MessageDigest;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 8:44
 * Description:
 *
 * @author chen
 */
public class MD5Utils {
    /**
     * MD5加密算法
     *
     * @param ss 加密
     * @return 密文
     */
    public static String encryption(String ss) {
        //如果为空，则返回""
        String s = ss == null ? "" : ss;
        //字典
        char[] hexDigests = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};

        try {
            //获取二进制
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            //执行加密
            mdTemp.update(strTemp);
            //加密结果
            byte[] md = mdTemp.digest();
            //结果长度
            int j = md.length;
            // 字符数组
            char[] str = new char[j * 2];
            int k = 0;
            //将二进制加密结果转化为字符
            for (byte byte0 : md) {
                str[k++] = hexDigests[byte0 >>> 4 & 0xf];
                str[k++] = hexDigests[byte0 & 0xf];
            }
            //输出加密后的字符
            return new String(str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}