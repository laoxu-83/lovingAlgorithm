package com.xwx;

/**暴力算法
 * @author xwx
 * @create 2020/6/23 19:43
 **/
public class BrutalForce {


    public static int indexOf(String text,String pattern){
        if (text==null || pattern==null) return -1;
        char[] textChars = text.toCharArray();
        char[] patternChars = pattern.toCharArray();
        int tlen = textChars.length;
        int plen = patternChars.length;

        if(tlen==0 || plen==0 || tlen<plen) return -1;

        int pi=0;
        int ti=0;
        //ti,pi下标都不能越界
        while(pi < plen && ti-pi <= tlen-plen){
            if(patternChars[pi]==textChars[ti]){
                ti++;
                pi++;
            }else {
                //pattern向前前进一个: ti回溯并加1
                ti = ti-pi+1;
                pi=0;
            }
        }
        return (pi==plen) ? ti-pi : -1;
    }

}
