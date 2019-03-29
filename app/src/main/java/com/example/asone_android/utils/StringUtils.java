package com.example.asone_android.utils;

public class StringUtils {
    /***
     * 解决特殊字符换行问题。
     ****/

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();

        for (int i = 0; i < c.length; i++) {
//            String b = String.valueOf(c[i]);
//            if ("。".equals(b)){
//               continue;
//            }
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
