package com.von;

/**
 * @author xwx
 * @create 2020/6/23 20:32
 **/
public class KMP {
    public static int indexOf(String text, String pattern) {
        // 检测数据合法性
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (tlen == 0 || plen == 0 || tlen < plen) return -1;

        //next表
        int[] next = next(pattern);

        int pi=0, ti = 0;
        while(pi<plen && ti-pi<=tlen-plen){
            if(pi<0 || textChars[ti]==patternChars[pi]){
                ti++;
                pi++;
            } else{
                //失配，使用next表
                pi = next[pi];
            }
        }

        return (pi==plen) ? ti-pi:-1;
    }

    /**
     * next表构造
     * @param pattern
     * @return
     */
    private static int[] next(String pattern) {
        char[] chars = pattern.toCharArray();
        int[] next = new int[chars.length];

        next[0] = -1;
        int i = 0;
        int n = -1;  //next[0]=-1
        int iMax = chars.length - 1;

        while(i<iMax){
            if(n<0 || chars[i]==chars[n]){
                i++;
                n++;
                //如果char[i+1]与 char[n]仍然相同
                if(chars[i]==chars[n]){
                    next[i] = next[n];
                } else{
                    next[i] = n;
                }
            } else{
                n = next[n];
            }
        }
        return next;
    }
}
